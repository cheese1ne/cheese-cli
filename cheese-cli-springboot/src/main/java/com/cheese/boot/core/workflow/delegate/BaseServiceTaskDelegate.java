package com.cheese.boot.core.workflow.delegate;

import com.cheese.boot.core.workflow.service.IProcessConfigService;
import com.cheese.boot.core.workflow.service.ITaskConfigService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 在activity中serviceTask任务可以通过指定spring实例进行处理，
 * 通过activiti:delegateExpression="${beanName}" 来指定
 * 在服务任务中 可以完成非用户操作任务的信息的收集、处理、分发和归档，**凡事系统触发动作都可以通过服务任务来完成**
 * 例如：数据同步；初始化业务主体；信息同步等
 *
 * @author sobann
 */
public abstract class BaseServiceTaskDelegate implements JavaDelegate {

    private final IProcessConfigService processConfigService;
    private final ITaskConfigService taskConfigService;

    public BaseServiceTaskDelegate(IProcessConfigService processConfigService,
                                   ITaskConfigService taskConfigService) {
        this.processConfigService = processConfigService;
        this.taskConfigService = taskConfigService;
    }


    @Override
    public void execute(DelegateExecution execution) {
        //获取流程示例id
        String processId = execution.getProcessInstanceId();
        this.sync(execution);
        this.init(execution);
        this.log(execution);
        this.extra(execution);
    }

    /**
     * 信息同步功能
     *
     * @param execution
     */
    protected abstract void sync(DelegateExecution execution);

    /**
     * 业务实体初始化
     *
     * @param execution
     */
    protected abstract void init(DelegateExecution execution);

    /**
     * 日志记录功能
     *
     * @param execution
     */
    protected abstract void log(DelegateExecution execution);

    /**
     * 其他业务
     *
     * @param execution
     */
    protected abstract void extra(DelegateExecution execution);
}
