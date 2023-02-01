package com.cheese.boot.core.workflow.lifecycle.handler.process;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

/**
 * 默认的流程完成处理器
 * 后续业务在具体的业务模块中实现
 *
 * @author sobann
 */
public abstract class BaseProcessCompleteEventHandler implements IProcessEventHandler {

    @Override
    public boolean match(ActivitiEntityEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        if (!ActivitiEventType.PROCESS_COMPLETED.equals(type) && !ActivitiEventType.PROCESS_COMPLETED_WITH_ERROR_END_EVENT.equals(type)) {
            return false;
        }
        ExecutionEntity executionEntity = (ExecutionEntity) activitiEvent.getEntity();
        String processDefinitionId = executionEntity.getProcessDefinitionId();
        return this.match(processDefinitionId);
    }

    /**
     * 流程的定义的ID，就是bpmn中process标签中的id
     *
     * @param processDefinitionId
     * @return
     */
    protected abstract boolean match(String processDefinitionId);
}
