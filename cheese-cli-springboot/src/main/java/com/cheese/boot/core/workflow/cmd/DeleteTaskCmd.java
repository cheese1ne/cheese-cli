package com.cheese.boot.core.workflow.cmd;

import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;

/**
 * 任务删除命令
 *
 * @author sobann
 */
public class DeleteTaskCmd extends NeedsActiveTaskCmd<String> {

    public DeleteTaskCmd(String taskId) {
        super(taskId);
    }

    @Override
    public String execute(CommandContext commandContext, TaskEntity currentTask) {
        //获取所需服务
        TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl) commandContext.getTaskEntityManager();
        //获取当前任务的来源任务及来源节点信息
        ExecutionEntity executionEntity = currentTask.getExecution();
        //删除当前任务,来源任务
        taskEntityManager.deleteTask(currentTask, "revocation process", false, false);
        return executionEntity.getId();
    }

    @Override
    public String getSuspendedTaskException() {
        return "Pending tasks cannot jump!";
    }

}
