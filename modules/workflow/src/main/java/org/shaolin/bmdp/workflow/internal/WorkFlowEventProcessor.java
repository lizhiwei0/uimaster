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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.shaolin.bmdp.runtime.AppContext;
import org.shaolin.bmdp.runtime.be.ITaskEntity;
import org.shaolin.bmdp.runtime.spi.Event;
import org.shaolin.bmdp.runtime.spi.EventProcessor;
import org.shaolin.bmdp.runtime.spi.IServiceProvider;
import org.shaolin.bmdp.workflow.be.ITask;
import org.shaolin.bmdp.workflow.coordinator.ICoordinatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unified event processor which registered to one event producer.
 * 
 * THe event processor will pass event to real app, an event producer 
 * can be handled by multiple APPs. Each app will be one event consumer
 * instance.
 */
public final class WorkFlowEventProcessor implements EventProcessor, IServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowEventProcessor.class);
    
//    private final ExecutorService pool;
    
    private final Map<String, EventConsumer> allConsumers;
    
    private static final ThreadLocal<StringBuilder> idBuilder = new ThreadLocal<StringBuilder>() {
        @Override
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };
    
    private final AtomicLong seq = new AtomicLong(0);
    
    public WorkFlowEventProcessor(Map<String, EventConsumer> allConsumers) {
        this.allConsumers = allConsumers;
        // make this shared for all application instances.
//        this.pool = IServerServiceManager.INSTANCE.getSchedulerService()
//    			.createExecutorService("system", "wf-processor", Runtime.getRuntime().availableProcessors() * 2);
    }
    
    Map<String, EventConsumer> getConsumers() {
    	return allConsumers;
    }
    
    
    @Override
	public void process(final Event event) {
		// Generate a unique id for the event.
		if (event.getId() == null) {
			StringBuilder sb = idBuilder.get();
			sb.setLength(0);
			event.setId(sb.append(event.getEventConsumer())
					.append('[').append(seq.getAndIncrement())
					.append(']').toString());
		}
		event.setAttribute(BuiltInAttributeConstant.KEY_ORIGINAL_EVENT, event);
		Collection<String> keys = event.getAttributeKeys();
        for (String key: keys) {
        	Object var = event.getAttribute(key);
        	// get the previous flow context from the first task entity with task id.
        	if (var instanceof ITaskEntity && ((ITaskEntity)var).getTaskId() > 0) {
        		try {
        			// find the previous context.
	        		ICoordinatorService coordinator = AppContext.get().getService(ICoordinatorService.class);
	        		ITask task = coordinator.getTask(((ITaskEntity)var).getTaskId());
	        		FlowRuntimeContext flowContext = FlowRuntimeContext.unmarshall(task.getFlowState());
	        		flowContext.changeEvent(event);
	        		flowContext.getFlowContextInfo().setWaitingNode(flowContext.getCurrentNode());
					flowContext.getEvent().setFlowContext(flowContext.getFlowContextInfo());
					event.setFlowContext(flowContext.getFlowContextInfo());
        		} catch (Exception e) {
        			logger.warn(e.getMessage(), e);
        		}
        		break;
        	}
        }
		if (logger.isTraceEnabled()) {
			logger.trace("Assign Id {} to event {}", event.getId(), event);
			logger.trace("Receive a event {}", event);
		}
		EventConsumer consumer = allConsumers.get(event.getEventConsumer());
		if (logger.isTraceEnabled()) {
			logger.trace("Trigger event {} on {}", event.getId(),
					consumer);
		}
		if (!consumer.accept(event, null)) {
			if (logger.isTraceEnabled()) {
				logger.trace("No matched node for event {} from {}",
						event.getId(), event.getEventConsumer());
			}
		}
	}
    
	@Override
	public Class getServiceInterface() {
		return WorkFlowEventProcessor.class;
	}

}
