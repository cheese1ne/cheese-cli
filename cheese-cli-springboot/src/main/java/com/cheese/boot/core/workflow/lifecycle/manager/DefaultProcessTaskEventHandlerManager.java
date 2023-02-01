package com.cheese.boot.core.workflow.lifecycle.manager;

import com.cheese.boot.core.workflow.lifecycle.handler.process.IProcessEventHandler;
import com.cheese.boot.core.workflow.lifecycle.handler.task.ITaskEventHandler;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 默认的流程及任务业务处理管理器
 *
 * @author sobann
 */
@Component
public class DefaultProcessTaskEventHandlerManager implements IProcessTaskEventHandlerManager {

    private final ITaskEventHandler[] taskHandlers;
    private final IProcessEventHandler[] processHandlers;

    public DefaultProcessTaskEventHandlerManager(ObjectProvider<ITaskEventHandler[]> taskHandlerProvider,
                                                 ObjectProvider<IProcessEventHandler[]> processHandlerProvider) {
        this.taskHandlers = Optional.ofNullable(taskHandlerProvider.getIfAvailable()).orElse(new ITaskEventHandler[0]);
        this.processHandlers = Optional.ofNullable(processHandlerProvider.getIfAvailable()).orElse(new IProcessEventHandler[0]);
    }

    @Override
    public void eventHandler(ActivitiEvent event) {
        //流程及任务事件都从属于 ActivitiEntityEvent 下
        if (event instanceof ActivitiEntityEvent) {
            ActivitiEntityEvent activitiEntityEvent = (ActivitiEntityEvent) event;
            Object entity = activitiEntityEvent.getEntity();
            //任务类型的实例事件
            if (entity instanceof Task) {
                for (ITaskEventHandler taskHandler : taskHandlers) {
                    if (taskHandler.match(activitiEntityEvent)) {
                        taskHandler.handle((Task) entity);
                    }
                }
            }

            //流程实例类型的实例事件
            if (entity instanceof ExecutionEntity) {
                for (IProcessEventHandler processHandler : processHandlers) {
                    if (processHandler.match(activitiEntityEvent)) {
                        processHandler.handle((ExecutionEntity) entity);
                    }
                }
            }
        }
    }
}
