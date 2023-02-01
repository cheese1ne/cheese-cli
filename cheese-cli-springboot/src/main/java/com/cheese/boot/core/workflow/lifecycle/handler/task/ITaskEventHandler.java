package com.cheese.boot.core.workflow.lifecycle.handler.task;

import com.cheese.boot.common.Order;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.task.Task;

/**
 * 流程任务处理器顶层接口
 * 默认实现与任务的生命周期相关
 *
 * @author sobann
 */
public interface ITaskEventHandler extends Order {
    /**
     * 业务处理
     *
     * @param task
     */
    void handle(Task task);

    /**
     * 匹配规则
     * 事件中有
     *
     * @param activitiEvent
     * @return
     */
    boolean match(ActivitiEntityEvent activitiEvent);

}
