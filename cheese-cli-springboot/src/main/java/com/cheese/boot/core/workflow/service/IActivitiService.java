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
     * @param processId
     **/
    List<Task> getActiveTasks(String processId);

    /**
     * 获取流程下第一个待执行任务
     * tip：此方法返回的是懒加载的对象
     *
     * @param processId
     **/
    Task getActiveTask(String processId);

    /**
     * 跳转到目标任务
     *
     * @param taskId
     * @param targetTask
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
