package com.cheese.boot.core.workflow.lifecycle.handler.task;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.task.Task;

/**
 * 默认的任务创建处理器
 * 后续业务在具体的业务模块中实现
 *
 * @author sobann
 */
public abstract class BaseTaskCreateEventHandler implements ITaskEventHandler {

    @Override
    public boolean match(ActivitiEntityEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        if (!ActivitiEventType.TASK_CREATED.equals(type)) {
            return false;
        }
        Task task = (Task) activitiEvent.getEntity();
        //根据流程定义bpmn中的id以及任务的key来确认具体的任务
        String processDefinitionId = task.getProcessDefinitionId();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        return this.match(processDefinitionId, taskDefinitionKey);
    }

    /**
     * 流程的定义的ID，就是bpmn中process标签中的id
     * 流程任务定义的key就是流程任务节点中任务标签的id
     *
     * @param processDefinitionId
     * @param taskDefinitionKey
     * @return
     */
    protected abstract boolean match(String processDefinitionId, String taskDefinitionKey);


}
