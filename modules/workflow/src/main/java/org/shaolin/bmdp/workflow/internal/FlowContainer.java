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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import org.shaolin.bmdp.datamodel.workflow.MissionNodeType;
import org.shaolin.bmdp.datamodel.workflow.Workflow;
import org.shaolin.bmdp.runtime.AppContext;
import org.shaolin.bmdp.runtime.be.ITaskEntity;
import org.shaolin.bmdp.runtime.cache.CacheManager;
import org.shaolin.bmdp.runtime.cache.ICache;
import org.shaolin.bmdp.workflow.be.ITask;
import org.shaolin.bmdp.workflow.be.TaskImpl;
import org.shaolin.bmdp.workflow.coordinator.ICoordinatorService;
import org.shaolin.bmdp.workflow.coordinator.ITaskListener;
import org.shaolin.bmdp.workflow.dao.CoordinatorModel;
import org.shaolin.bmdp.workflow.exception.ConfigException;
import org.shaolin.bmdp.workflow.internal.cache.FlowObject;
import org.shaolin.bmdp.workflow.internal.type.AppInfo;
import org.shaolin.bmdp.workflow.internal.type.NodeInfo;
import org.shaolin.bmdp.workflow.spi.LogicalTransactionService;
import org.shaolin.bmdp.workflow.spi.TimeoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FlowContainer is the core object that maintains all the flow engines and the event consumers.
 * 
 */
public class FlowContainer {
	
    private static final Logger logger = LoggerFactory.getLogger(FlowContainer.class);

    private ExecutorService executorService;
    private LogicalTransactionService transactionService;
    
    private long defaultWorkflowTimeout = 5000;

    // local variable
    private final ConcurrentMap<String, FlowEngine> allEngines = new ConcurrentHashMap<String, FlowEngine>();

	private ICache<String, FlowObject> appflowCache;

	public FlowContainer(String appName) {
		appflowCache = CacheManager.getInstance().getCache(appName + "_workflow_cache", String.class,
				FlowObject.class);
	}
	
	public FlowObject getFlowObject(String flowName) {
		return appflowCache.get(flowName);
	}
	
    public List<String> getActiveEngines() {
        return new ArrayList<String>(allEngines.keySet());
    }
    
    public FlowEngine getFlowEngine(String key) {
        return allEngines.get(key);
    }

    void restartService(Workflow appInfo) {
    	FlowObject flowInfo = new FlowObject(new AppInfo(appInfo));
    	if (appInfo.getConf().isBootable() != null && appInfo.getConf().isBootable()) {
    		WorkFlowEventProcessor processor = AppContext.get().getService(WorkFlowEventProcessor.class);
    		
    		logger.info("Recreate workflow engine: {}",  flowInfo.getAppInfo().getName());
    		FlowEngine engine = initFlowEngine(flowInfo, false);
    		logger.info("Start workflow engine: {}", engine.getEngineName());
    		engine.start(processor.getConsumers());
    		allEngines.put(engine.getEngineName(), engine);
    	}
    	appflowCache.put(appInfo.getEntityName(), flowInfo);
    }
    
    void startService(List<Workflow> appInfos) {
    	allEngines.clear();
    	
        Map<String, FlowEngine> engineMap = new HashMap<String, FlowEngine>();
        List<FlowObject> activeFlows = new ArrayList<FlowObject>();
        for (Workflow flow : appInfos) {
        	FlowObject flowInfo = new FlowObject(new AppInfo(flow));
        	// add to cache, but not initialized.
        	appflowCache.putIfAbsent(flow.getEntityName(), flowInfo);
            
            if (flow.getConf().isBootable() != null && flow.getConf().isBootable()) {
            	activeFlows.add(flowInfo);
            }
        }
        
        for (FlowObject flowInfo: activeFlows) {
        	logger.info("Create workflow engine: {}",  flowInfo.getAppInfo().getName());
        	FlowEngine engine = initFlowEngine(flowInfo, false);
        	engineMap.put(flowInfo.getAppInfo().getName(), engine);
        }

        startFlowEngines(engineMap);
    }
    
    void stopService() {
    	allEngines.clear();
    	appflowCache.clear();
    }

    /**
     * All flow engines read for work. This is the most important step for FlowContainer.
     * After this, all flow engines are able to handle the incoming event.
     * 
     * @param engineMap
     */
    private void startFlowEngines(Map<String, FlowEngine> engineMap) {
        Map<String, EventConsumer> tempProcessors = new HashMap<String, EventConsumer>();

        // init all EventConsumers.
        for (FlowEngine engine : engineMap.values()) {
            logger.info("Start workflow engine: {}", engine.getEngineName());
            engine.start(tempProcessors);
            allEngines.put(engine.getEngineName(), engine);
        }

        WorkFlowEventProcessor eventProcessor = new WorkFlowEventProcessor(tempProcessors);
        AppContext.get().register(eventProcessor);
    }

    public void runTask(final WorkFlowEventProcessor timeoutEventProcessor, final TimeoutEvent event) {
        //executorService.submit(new TimeoutEventTask(timeoutEventProcessor, event));
    }

    public void startTransaction() {
    	if (transactionService == null) {
    		return;
    	}
        transactionService.begin();
    }

    public void rollbackTransaction() {
    	if (transactionService == null) {
    		return;
    	}
        transactionService.rollback();
    }

    public void commitTransaction() {
    	if (transactionService == null) {
    		return;
    	}
        transactionService.commit();
    }

    public Object pauseTransaction() {
    	if (transactionService == null) {
    		return null;
    	}
        return transactionService.pause();
    }

    public void resumeTransaction(Object obj) {
    	if (transactionService == null) {
    		return;
    	}
        transactionService.resume(obj);
    }

    public ITask scheduleTask(Date timeout, final FlowRuntimeContext flowContext, final FlowEngine engine, 
    		final NodeInfo currentNode, final MissionNodeType mission) throws Exception {
    	if (logger.isTraceEnabled()) {
            logger.trace("Schedule timer on {}, dealy time is {}", 
            		currentNode.toString(), timeout.toString());
        }
    	
        //Notify the parties
        ICoordinatorService coordinator = AppContext.get().getService(ICoordinatorService.class);
        ITask task = new TaskImpl();
        task.setSessionId(flowContext.getSession().getID());
        task.setSubject("Task: " + mission.getName());
        task.setDescription(mission.getDescription());
        task.setPartyType(mission.getParticipant().getPartyType());
        task.setExpiredTime(timeout);
        task.setEnabled(true);
        task.setListener(new MissionListener(task));
        task.setCreateTime(new Date());
        
    	CoordinatorModel.INSTANCE.create(task);
        
    	List<ITaskEntity> taskEntities = flowContext.getAllNewTaskEntities();
    	if (taskEntities != null) {
    		for (ITaskEntity entity: taskEntities) {
    			entity.setTaskId(task.getId());
    			CoordinatorModel.INSTANCE.update(entity);
    		}
			flowContext.getAllNewTaskEntities().clear();
    	}
    	
        List<ITaskEntity> taskRelatedEntities = new ArrayList<ITaskEntity>();
        Collection<String> keys = flowContext.getEvent().getAttributeKeys();
        for (String key: keys) {
        	Object var = flowContext.getEvent().getAttribute(key);
        	if (var instanceof ITaskEntity) {
        		taskRelatedEntities.add((ITaskEntity)var);
        		flowContext.getEvent().setAttribute(key, null);
        	}
        }
        flowContext.setTaskId(task.getId());
        task.setFlowState(FlowRuntimeContext.marshall(flowContext));
        
        coordinator.addTask(task);
        
        for (ITaskEntity entity: taskRelatedEntities) {
    		entity.setTaskId(task.getId());
        }
        return task;
    }
    
    /**
     * Create FlowEngines by all defined templates. Each flow template will have
     * a flow engine created if success.
     * 
     * @param entityFlows
     * @return
     */
    public FlowEngine initFlowEngine(FlowObject flowInfo, boolean isManagedTransaction) throws ConfigException {
        FlowEngine engine = new FlowEngine(flowInfo.getAppInfo().getName(), this, isManagedTransaction);
        if (isManagedTransaction && transactionService == null) {
            throw new IllegalStateException("Transaction service does not exist.");
        }
        engine.init(flowInfo);
        return engine;
    }
    
    private static final class TimeoutEventTask implements Runnable {
        private final WorkFlowEventProcessor timeoutEventProcessor;
        private final TimeoutEvent event;

        private TimeoutEventTask(WorkFlowEventProcessor timeoutEventProcessor, TimeoutEvent event) {
            this.timeoutEventProcessor = timeoutEventProcessor;
            this.event = event;
        }

        @Override
        public void run() {
            timeoutEventProcessor.process(event);
        }
    }

    public static final class MissionListener implements ITaskListener {
        private final ITask task;
        public MissionListener(ITask task) {
        	this.task = task;
        }

		@Override
		public void notifyCompleted() {
		}

		@Override
		public void notifyExpired() {
			try {
				FlowRuntimeContext flowContext = FlowRuntimeContext.unmarshall(task.getFlowState());
				flowContext.getEngine().timeout(flowContext.getFlowContextInfo());
			} catch (Exception e) {
				logger.error("Continue processing task error: " + e.getMessage(), e);
			}
		}

		@Override
		public void notifyCancelled() {
			try {
				FlowRuntimeContext flowContext = FlowRuntimeContext.unmarshall(task.getFlowState());
				flowContext.getFlowContextInfo().setWaitingNode(flowContext.getCurrentNode());
				flowContext.getEvent().setFlowContext(flowContext.getFlowContextInfo());
				flowContext.engine.destroySession(flowContext);
			} catch (Exception e) {
				logger.error("Continue processing task error: " + e.getMessage(), e);
			}
		}

		@Override
		public void notifyCreated() {
			
		}
    }

}
