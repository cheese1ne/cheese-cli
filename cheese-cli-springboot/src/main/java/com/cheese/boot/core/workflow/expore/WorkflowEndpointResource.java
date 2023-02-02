package com.cheese.boot.core.workflow.expore;

import com.cheese.boot.core.workflow.common.constant.WorkflowUriConstant;
import com.cheese.boot.core.workflow.domain.ProcessTask;
import com.cheese.boot.core.workflow.service.IActivitiService;
import com.cheese.core.base.domain.R;
import com.cheese.core.tool.util.Func;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 提供 http 服务 用于暴露流程相关接口
 *
 * @author sobann
 */
@Singleton
@Path(WorkflowUriConstant.WORK_FLOW)
public class WorkflowEndpointResource {

    @Autowired
    private IActivitiService activitiService;


    /**
     * 创建流程实例
     *
     * @param process 流程定义的id bpmn中process节点的id
     * @param params  流程创建参数
     * @return 流程实例id
     */
    @POST
    @Path(WorkflowUriConstant.CREATE_PROCESS_EXECUTION)
    @Produces(MediaType.APPLICATION_JSON)
    public R<String> createProcessExecution(@QueryParam("process") String process, Map<String, Object> params) {
        String processExecution = activitiService.createProcessExecution(process, params);
        return R.ok(processExecution);
    }


    /**
     * 获取流程活动任务
     *
     * @param processId 流程实例id
     * @return 流程任务
     */
    @GET
    @Path(WorkflowUriConstant.GET_ACTIVE_TASK)
    @Produces(MediaType.APPLICATION_JSON)
    public R<List<ProcessTask>> getProcessActiveTask(@QueryParam("processId") String processId) {
        List<Task> activeTasks = activitiService.getActiveTasks(processId);
        if (Func.isEmpty(activeTasks)) return R.ok(Collections.emptyList());
        return R.ok(activeTasks.stream().map(ProcessTask::assemble).collect(Collectors.toList()));
    }

    /**
     * 提交任务
     *
     * @param taskId 任务id
     * @param params 任务提交参数
     * @return 流程任务
     */
    @POST
    @Path(WorkflowUriConstant.SUBMIT_TASK)
    @Produces(MediaType.APPLICATION_JSON)
    public R<Boolean> getProcessActiveTask(@QueryParam("taskId") String taskId, Map<String, Object> params) {
        activitiService.handleTask(taskId, params);
        return R.ok(Boolean.TRUE);
    }
}
