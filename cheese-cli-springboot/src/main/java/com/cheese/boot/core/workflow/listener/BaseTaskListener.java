package com.cheese.boot.core.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.Map;

/**
 * 参考 countersign-demo 中并行任务任务配置，在此处对数据进行检查
 *
 * @author sobann
 */
public abstract class BaseTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> variables = delegateTask.getVariables();
    }

    /**
     * 参数检查
     * @param variables
     */
    protected abstract void checkVariables(Map<String, Object> variables);
}
