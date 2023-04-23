package com.cheese.boot.core.workflow.domain;

import com.cheese.core.tool.util.Func;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

import java.io.Serializable;
import java.util.Date;

/**
 * 由于activity工作流懒加载的机制
 * org.activiti.engine.task.Task 无法直接作为视图进行响应
 * 任务视图通过此类进行响应
 * <p>
 * 参照org.activiti.engine.impl.persistence.entity.TaskEntityImpl#getPersistentState()
 *
 * @author sobann
 */
@Data
public class ProcessTask implements Serializable {
    private static final long serialVersionUID = 6776419940342489400L;

    private String id;
    private String key;
    private String processId;
    private String assignee;
    private String owner;
    private String name;
    private int priority;
    private String executionId;
    private String processDefinitionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dueDate;
    private String parentTaskId;
    private DelegationState delegationState;
    private int suspensionState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date claimTime;

    /**
     * 懒加载实体的转换
     *
     * @param task
     * @return
     */
    public static ProcessTask assemble(Task task) {
        ProcessTask processTask = new ProcessTask();
        if (Func.isNotEmpty(task) && task instanceof TaskEntityImpl) {
            TaskEntityImpl entity = (TaskEntityImpl) task;
            processTask.setId(entity.getId());
            processTask.setKey(entity.getTaskDefinitionKey());
            processTask.setName(entity.getName());
            processTask.setProcessId(entity.getProcessInstanceId());
            processTask.setAssignee(entity.getAssignee());
            processTask.setOwner(entity.getOwner());
            processTask.setPriority(entity.getPriority());
            processTask.setExecutionId(entity.getExecutionId());
            processTask.setProcessDefinitionId(entity.getProcessDefinitionId());
            processTask.setCreateTime(entity.getCreateTime());
            processTask.setDescription(entity.getDescription());
            processTask.setDueDate(entity.getDueDate());
            processTask.setParentTaskId(entity.getParentTaskId());
            processTask.setDelegationState(entity.getDelegationState());
            processTask.setSuspensionState(entity.getSuspensionState());
            processTask.setClaimTime(entity.getClaimTime());
        }
        return processTask;
    }
}
