package com.cheese.boot.core.workflow.service;

import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * activity流程/任务处理顶层接口
 *
 * @author sobann
 */
public interface IActivitiService {

    /**
     * 初始化流程实例
     *
     * @param process
     * @param param
     * @return 流程实例id
     **/
    String createProcessExecution(String process, Map<String, Object> param);

    /**
     * 提交任务
     *
     * @param taskId
     * @param params
     **/
    void handleTask(String taskId, Map<String, Object> params);

    /**
     * 获取流程下待执行任务
     * tip：此方法返回的是懒加载的对象
     *
     * @param processId 流程id
     * @return 活跃任务列表
     **/
    List<Task> getActiveTasks(String processId);

    /**
     * 获取流程下第一个待执行任务
     * tip：此方法返回的是懒加载的对象
     *
     * @param processId
     * @return 流程活跃任务
     **/
    Task getActiveTask(String processId);

    /**
     * 跳转到目标任务
     *
     * @param taskId 任务id
     * @param targetTask 目标任务标识
     **/
    void jump(String taskId, String targetTask);

    /**
     * 删除流程任务
     *
     * @param taskIds
     **/
    void deleteTaskHistory(List<String> taskIds);

    /**
     * 删除正在执行的流程
     *
     * @param processId
     **/
    void deleteRunProcess(String processId);
}
