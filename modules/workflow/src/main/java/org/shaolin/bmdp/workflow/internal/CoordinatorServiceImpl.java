/*
* Copyright 2015 The UIMaster Project
*
* The UIMaster Project licenses this file to you under the Apache License,
* version 2.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at:
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/
package org.shaolin.bmdp.workflow.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.shaolin.bmdp.persistence.HibernateUtil;
import org.shaolin.bmdp.runtime.AppContext;
import org.shaolin.bmdp.runtime.spi.IAppServiceManager;
import org.shaolin.bmdp.runtime.spi.ILifeCycleProvider;
import org.shaolin.bmdp.runtime.spi.IServerServiceManager;
import org.shaolin.bmdp.runtime.spi.IServiceProvider;
import org.shaolin.bmdp.workflow.be.INotification;
import org.shaolin.bmdp.workflow.be.ITask;
import org.shaolin.bmdp.workflow.be.ITaskHistory;
import org.shaolin.bmdp.workflow.be.NotificationImpl;
import org.shaolin.bmdp.workflow.be.TaskHistoryImpl;
import org.shaolin.bmdp.workflow.be.TaskImpl;
import org.shaolin.bmdp.workflow.ce.PeriodicType;
import org.shaolin.bmdp.workflow.ce.TaskStatusType;
import org.shaolin.bmdp.workflow.coordinator.ICoordinatorService;
import org.shaolin.bmdp.workflow.coordinator.IResourceManager;
import org.shaolin.bmdp.workflow.coordinator.ITaskListener;
import org.shaolin.bmdp.workflow.dao.CoordinatorModel;
import org.shaolin.bmdp.workflow.internal.FlowContainer.MissionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoordinatorServiceImpl implements ILifeCycleProvider, ICoordinatorService, IServiceProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(CoordinatorServiceImpl.class);
	
	// make this for whole system, not only for one application instance.
	private ScheduledExecutorService pool;
	
	private final Map<ITask, ScheduledFuture<?>> futures = new HashMap<ITask, ScheduledFuture<?>>();
	
	private final ConcurrentHashMap<Long, ITask> workingTasks = new ConcurrentHashMap<Long, ITask>();
	
	private final ConcurrentHashMap<Long, List<INotification>> allNotifications 
		= new ConcurrentHashMap<Long, List<INotification>>();
	
	private boolean testCaseFlag = false;
	
	private IAppServiceManager appService;
	
	public void setAppService(IAppServiceManager appService) {
		this.appService = appService;
	}

	void markAsTestCaseFlag() {
		testCaseFlag = true;
	}
	
	@Override
	public void reload() {
		
	}
	
	@Override
	public List<ITask> getAllTasks() {
		List<ITask> allTasks = new ArrayList<ITask>();
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			allTasks.add(t);
		}
		return allTasks;
	}
	
	@Override
	public int getTaskSize() {
		return workingTasks.size();
	}
	
	public List<Long> getAllTaskOnwers() {
		List<Long> onwers = new ArrayList<Long>();
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			onwers.add(t.getPartyId());
		}
		return onwers;
	}
	
	@Override
	public List<ITaskHistory> getHistoryTasks(TaskStatusType status) {
		TaskHistoryImpl condition = new TaskHistoryImpl();
		condition.setStatus(TaskStatusType.EXPIRED);
		return CoordinatorModel.INSTANCE.searchTasksHistory(condition, null, 0, -1);
	}
	
	@Override
	public List<ITask> getTasks(TaskStatusType status) {
		if (status == TaskStatusType.EXPIRED) {
			TaskImpl condition = new TaskImpl();
			condition.setStatus(TaskStatusType.EXPIRED);
			return CoordinatorModel.INSTANCE.searchTasks(condition, null, 0, -1);
		}
		
		List<ITask> partyTasks = new ArrayList<ITask>();
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			if (t.getStatus() == status) {
				partyTasks.add(t);
			}
		}
		return partyTasks;
	}
	
	@Override
	public List<ITask> getTasksBySessionId(String sessionId) {
		if (sessionId == null || sessionId.trim().length() == 0) {
			throw new IllegalArgumentException("Session Id can't be empty.");
		}
		
		List<ITask> sessionTasks = new ArrayList<ITask>();
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			if (t.getSessionId().equals(sessionId)) {
				sessionTasks.add(t);
			}
		}
		
		TaskHistoryImpl historyCriteria = new TaskHistoryImpl();
		historyCriteria.setSessionId(sessionId);
		List<ITaskHistory> list = CoordinatorModel.INSTANCE.searchTasksHistory(historyCriteria, null, 0, -1);
		if (list != null) {
			for (ITaskHistory h: list) {
				sessionTasks.add(moveToTask(h));
			}
		}
		return sessionTasks;
	}
	
	public String getSessionId(long taskId) {
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			if (t.getId() == taskId) {
				return t.getSessionId();
			}
		}
		
		TaskHistoryImpl historyCriteria = new TaskHistoryImpl();
		historyCriteria.setTaskId(taskId);
		List<ITaskHistory> list = CoordinatorModel.INSTANCE.searchTasksHistory(historyCriteria, null, 0, -1);
		if (list != null && list.size() > 0) {
			return list.get(0).getSessionId();
		}
		throw new IllegalArgumentException("Session Id can't be found by this task id: " + taskId);
	}
	
	@Override
	public List<ITask> getAllExpiredTasks() {
		return getTasks(TaskStatusType.EXPIRED);
	}
	
	@Override
	public List<ITask> getPartyTasks(long partyId) {
		List<ITask> partyTasks = new ArrayList<ITask>();
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			if (t.getPartyId() == partyId) {
				partyTasks.add(t);
			}
		}
		return partyTasks;
	}
	
	@Override
	public ITask getTask(long taskId) {
		Collection<ITask> tasks= workingTasks.values();
		for (ITask t : tasks) {
			if (t.getId() == taskId) {
				return t;
			}
		}
		return null;
	}
	
	@Override
	public boolean isPendingTask(long taskId) {
		if (taskId == 0) {
			return true; // new created.
		}
		ITask task = getTask(taskId);
		if (task == null) {
			return false;
		}
		return task.getStatus() == TaskStatusType.NOTSTARTED 
				|| task.getStatus() == TaskStatusType.INPROGRESS;
	}

	@Override
	public void addTask(final ITask task) {
		if (task.getStatus() == TaskStatusType.COMPLETED
				|| task.getStatus() == TaskStatusType.CANCELLED) {
			throw new IllegalArgumentException("Task is finished: " + task.getStatus());
		}
		
		task.setStatus(TaskStatusType.NOTSTARTED);
		
		if (!testCaseFlag) {
			if (task.getId() == 0) {
				CoordinatorModel.INSTANCE.create(task);
			}
		}
		if (workingTasks.putIfAbsent(task.getId(), task) == null) {
			schedule(task);
		}
		if (!testCaseFlag) {
			CoordinatorModel.INSTANCE.update(task);
		}
	}

	private void schedule(final ITask task) {
		if (task.getStatus() == TaskStatusType.COMPLETED
				|| task.getStatus() == TaskStatusType.CANCELLED) {
			workingTasks.remove(task.getId());
			throw new IllegalArgumentException("Task must be not in started state: " + task.getStatus());
		}
		AppContext.get().getService(IResourceManager.class).assignOnwer(task);
		
		if (task.getPeriodicJob() != null) {
			long initialDelay;
			long period;
			long nowGap = 24 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (task.getPeriodicValue() >= nowGap) {
				long v = task.getPeriodicValue() - nowGap;
				initialDelay = v == 0 ? 1 : v;
				period = v == 0 ? 1 : v;
			} else {
				initialDelay = 24 + task.getPeriodicValue() - nowGap;
				period = 24 + task.getPeriodicValue() - nowGap;
			}
			
			if (task.getPeriodicType() == PeriodicType.WEEKLY) {
				initialDelay = initialDelay + (7 * 12);
			} else if (task.getPeriodicType() == PeriodicType.MONTHLY) {
				//TODO: Bug on the accuracy
				initialDelay = initialDelay + (30 * 12);
			}
			ScheduledFuture<?> f = pool.scheduleAtFixedRate(task.getPeriodicJob(), initialDelay, period, TimeUnit.HOURS);
			futures.put(task, f);
			logger.debug("Scheduled a periodic job: " + task);
			return;
		}
		
		long delay = task.getExpiredTime().getTime() - System.currentTimeMillis();
		if (delay <= 0) {
			expireTask(task);
			return;
		}
		ScheduledFuture<?> future = pool.schedule(new Runnable() {
			@Override
			public void run() {
				expireTask(task);
			}
		}, delay, TimeUnit.MILLISECONDS);
		
		task.setStatus(TaskStatusType.INPROGRESS);
		futures.put(task, future);
		
		taskToNotification(task);
	}
	
	public void postponeTask(ITask task, Date date) {
		if (task.getExpiredTime() != null) {
			if (task.getExpiredTime().getTime() >= date.getTime()) {
				throw new IllegalArgumentException("Current task expired time is greater than the given date.");
			}
		}
		task.setExpiredTime(date);
		task.setStatus(TaskStatusType.NOTSTARTED);
				
		updateTask(task);
	}
	
	@Override
	public void updateTask(ITask task) {
		if (task.getId() <= 0) {
			throw new IllegalArgumentException("The created task can't be updated!");
		}
		
		ScheduledFuture<?> future = futures.remove(task);
		if (future != null && !future.isDone()) {
			future.cancel(true);
		}
		if (!workingTasks.containsKey(task.getId())) {
			workingTasks.put(task.getId(), task);
		}
		
		if (task.getStatus() == TaskStatusType.NOTSTARTED) {
			schedule(task);
		}
		if (!testCaseFlag) {
			CoordinatorModel.INSTANCE.update(task);
		}
	}
	
	private void expireTask(ITask task) {
		if (logger.isDebugEnabled()) {
			logger.debug("Task is expired!  {}", task.toString());
		}
		if (task.getStatus() == TaskStatusType.EXPIRED) {
			return;
		}
		AppContext.register(appService);
		
		ScheduledFuture<?> future = futures.remove(task); 
		if (future != null && !future.isDone()) {
			future.cancel(true);
		}
		workingTasks.remove(task.getId());
		
		task.setStatus(TaskStatusType.EXPIRED);
		task.setCompleteRate(0);
		
		if (!testCaseFlag) {
			// only update the task that give the customer change to make the decision.
			CoordinatorModel.INSTANCE.update(task);
		}
		
		if (task.getListener() != null) {
			ITaskListener listener = (ITaskListener)task.getListener();
			listener.notifyExpired();
		}
		
		taskToNotification(task);
	}
	
	@Override
	public void completeTask(ITask task) {
		AppContext.register(appService);
		if (logger.isTraceEnabled()) {
			logger.trace("Task is completed.  {}", task.toString());
		}
		
		ScheduledFuture<?> future = futures.remove(task); 
		if (future != null && !future.isDone()) {
			future.cancel(true);
		}
		workingTasks.remove(task.getId());
		
		task.setStatus(TaskStatusType.COMPLETED);
		task.setCompleteRate(100);
		
		if (!testCaseFlag) {
			moveToHistory(task, HibernateUtil.getSession());
		}
		
		if (task.getListener() != null) {
			ITaskListener listener = (ITaskListener)task.getListener();
			listener.notifyCompleted();
		}
		taskToNotification(task);
	}
	
	@Override
	public void cancelTask(ITask task) {
		AppContext.register(appService);
		if (logger.isTraceEnabled()) {
			logger.trace("Task is cancelled.  {}", task.toString());
		}
		
		ScheduledFuture<?> future = futures.remove(task);
		if (future != null && !future.isDone()) {
			future.cancel(true);
			
			workingTasks.remove(task.getId());
			
			task.setStatus(TaskStatusType.CANCELLED);
			
			if (!testCaseFlag) {
				moveToHistory(task, HibernateUtil.getSession());
			}
			
			if (task.getListener() != null) {
				ITaskListener listener = (ITaskListener)task.getListener();
				listener.notifyCancelled();
			}
			
			taskToNotification(task);
		}
	}
	
	private void moveToHistory(ITask task, Session session) {
		if (task.getId() == 0) {
			return;
		}
		TaskHistoryImpl history = new TaskHistoryImpl();
		history.setTaskId(task.getId());
		history.setCompleteRate(task.getCompleteRate());
		history.setDescription(task.getDescription());
		history.setEnabled(task.isEnabled());
		history.setExpiredTime(task.getExpiredTime());
		history.setPartyId(task.getPartyId());
		history.setPartyType(task.getPartyType());
		history.setPriority(task.getPriority());
		history.setSendEmail(task.getSendEmail());
		history.setSendSMS(task.getSendSMS());
		history.setStatus(task.getStatus());
		history.setSubject(task.getSubject());
		history.setComments(task.getComments());
		history.setSessionId(task.getSessionId());
		history.setCreateTime(task.getCreateTime());
		
		session.save(history);
		session.delete(task);
	}
	
	private ITask moveToTask(ITaskHistory hTask) {
		TaskImpl task = new TaskImpl();
		task.setId(hTask.getTaskId());
		task.setCompleteRate(hTask.getCompleteRate());
		task.setDescription(hTask.getDescription());
		task.setEnabled(hTask.isEnabled());
		task.setExpiredTime(hTask.getExpiredTime());
		task.setPartyId(hTask.getPartyId());
		task.setPartyType(hTask.getPartyType());
		task.setPriority(hTask.getPriority());
		task.setSendEmail(hTask.getSendEmail());
		task.setSendSMS(hTask.getSendSMS());
		task.setStatus(hTask.getStatus());
		task.setSubject(hTask.getSubject());
		task.setComments(hTask.getComments());
		task.setSessionId(hTask.getSessionId());
		task.setCreateTime(hTask.getCreateTime());
		return task;
	}
	
	@Override
	public void startService() {
		this.workingTasks.clear();
		this.futures.clear();
		this.setAppService(AppContext.get());
		// make this shared
		this.pool = IServerServiceManager.INSTANCE.getSchedulerService()
				.createScheduler("system", "wf-coordinator", Runtime.getRuntime().availableProcessors() * 2);
		
		if (testCaseFlag) {
			return;
		}
		// load all pending tasks when system up.
		TaskImpl condition = new TaskImpl();
		List<ITask> tasks = CoordinatorModel.INSTANCE.searchTasks(condition, null, 0, -1);
		for (ITask t : tasks) {
			workingTasks.put(t.getId(), t);
			t.setListener(new MissionListener(t));
			
			if (t.getStatus() == TaskStatusType.NOTSTARTED || t.getStatus() == TaskStatusType.INPROGRESS) {
				if (System.currentTimeMillis() < t.getExpiredTime().getTime()) {
					schedule(t);
				} else {
					//trigger it directly.
					expireTask(t);
				}
			}
		}
	}

	@Override
	public boolean readyToStop() {
		return true;
	}

	@Override
	public void stopService() {
		pool.shutdown();
		workingTasks.clear();
		futures.clear();
	}

	@Override
	public int getRunLevel() {
		return 20;
	}
	
	private void taskToNotification(ITask t) {
		NotificationImpl notifier = new NotificationImpl();
		notifier.setSubject("[" + t.getExpiredTime().toString() 
				+ "/" + t.getStatus().getDisplayName() + "] " + t.getSubject());
		notifier.setDescription(t.getDescription());
		notifier.setPartyId(t.getPartyId());
		addNotification(notifier);
	}
	
	@Override
	public void addNotification(INotification message) {
		long partyId = message.getPartyId();
		if (partyId <= 0) {
			throw new IllegalArgumentException("Please specify the party id.");
		}
		
		if (!allNotifications.containsKey(partyId)) {
			allNotifications.put(partyId, new ArrayList<INotification>());
		}
		allNotifications.get(partyId).add(message);
	}
	
	@Override
	public List<INotification> pullNotification(long partyId) {
		return allNotifications.remove(partyId);
	}

	@Override
	public Class getServiceInterface() {
		return ICoordinatorService.class;
	}
	
}
