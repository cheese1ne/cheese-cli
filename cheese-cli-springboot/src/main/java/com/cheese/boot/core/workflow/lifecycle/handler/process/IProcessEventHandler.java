package com.cheese.boot.core.workflow.lifecycle.handler.process;

import com.cheese.boot.common.Order;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

/**
 * 流程管理器顶层接口
 * 默认实现与流程的生命周期相关
 *
 * @author sobann
 */
public interface IProcessEventHandler extends Order {
    /**
     * 流程实例业务处理
     *
     * @param executionEntity
     */
    void handle(ExecutionEntity executionEntity);

    /**
     * 匹配规则
     *
     * @param activitiEvent
     * @return
     */
    boolean match(ActivitiEntityEvent activitiEvent);
}
