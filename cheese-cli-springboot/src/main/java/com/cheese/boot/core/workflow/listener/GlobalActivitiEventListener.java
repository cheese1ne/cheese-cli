package com.cheese.boot.core.workflow.listener;

import com.cheese.boot.core.workflow.lifecycle.manager.IProcessTaskEventHandlerManager;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.stereotype.Component;

/**
 * 全局的activity工作流事件监听器
 *
 * @author sobann
 */
@Component
public class GlobalActivitiEventListener implements ActivitiEventListener {

    private final IProcessTaskEventHandlerManager processTaskEventHandlerManager;


    public GlobalActivitiEventListener(IProcessTaskEventHandlerManager processTaskEventHandlerManager) {
        this.processTaskEventHandlerManager = processTaskEventHandlerManager;
    }


    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        processTaskEventHandlerManager.eventHandler(activitiEvent);
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
