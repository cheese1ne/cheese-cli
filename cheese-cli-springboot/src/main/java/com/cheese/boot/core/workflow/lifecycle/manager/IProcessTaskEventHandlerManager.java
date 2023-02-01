package com.cheese.boot.core.workflow.lifecycle.manager;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * 流程以及任务事件处理的管理器
 *
 * @author sobann
 */
public interface IProcessTaskEventHandlerManager {

    void eventHandler(ActivitiEvent event);
}
