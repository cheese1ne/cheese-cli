package com.cheese.boot.core.workflow.expore;

import com.cheese.boot.core.workflow.common.constant.WorkflowUriConstant;
import com.cheese.boot.core.workflow.domain.ProcessTask;
import com.cheese.boot.core.workflow.service.IActivitiService;
import com.cheese.core.tool.util.Func;
import com.cheese.core.tool.util.IoUtil;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
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
    public Response createProcessExecution(@QueryParam("process") String process, Map<String, Object> params) {
        String processExecution = activitiService.createProcessExecution(process, params);
        return Response.ok(processExecution).build();
    }


    /**
     * 获取流程活动任务
     *
     * @param processId 流程实例id
     * @return 流程任务
     */
    @GET
    @Path(WorkflowUriConstant.ACTIVE_TASK)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessActiveTask(@QueryParam("processId") String processId) {
        List<Task> activeTasks = activitiService.getActiveTasks(processId);
        if (Func.isEmpty(activeTasks)) return Response.ok(Collections.emptyList()).build();
        return Response.ok(activeTasks.stream().map(ProcessTask::assemble).collect(Collectors.toList())).build();
    }

    /**
     * 获取流程历史任务
     *
     * @param processId 流程实例id
     * @return 流程任务
     */
    @GET
    @Path(WorkflowUriConstant.HIS_TASK)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessHisTask(@QueryParam("processId") String processId) {
        List<HistoricTaskInstance> hisTasks = activitiService.getHisTasks(processId);
        if (Func.isEmpty(hisTasks)) return Response.ok(Collections.emptyList()).build();
        return Response.ok(hisTasks.stream().map(ProcessTask::assemble).collect(Collectors.toList())).build();
    }

    /**
     * 提交任务
     *
     * tip: 一般来说，提交任务需要对提交人和提交参数进行业务上的校验
     * todo 任务提交之前需要对任务参数进行校验，对任务操作人进行校验 校验参数内容可以通过配置的形式获取(上层抽象参数获取方式)
     *
     * @param taskId 任务id
     * @param params 任务提交参数
     * @return 流程任务
     */
    @POST
    @Path(WorkflowUriConstant.SUBMIT_TASK)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitTask(@QueryParam("taskId") String taskId, Map<String, Object> params) {
        activitiService.handleTask(taskId, params);
        return Response.ok(Boolean.TRUE).build();
    }

    /**
     * 获取活动任务参数
     *
     * @param taskId 活动任务id
     * @return 流程任务
     */
    @GET
    @Path(WorkflowUriConstant.ACTIVE_TASK_VARIABLE)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveTaskVariables(@QueryParam("taskId") String taskId) {
        Map<String, Object> taskVariables = activitiService.getTaskVariables(taskId);
        return Response.ok(taskVariables).build();
    }

    /**
     * 获取部署最新的流程图
     *
     * @param response jersey中可以使用@javax.ws.rs.core.Context注入response对象
     * @param process 流程key
     * @return 流程任务svg流
     */
    @GET
    @Path(WorkflowUriConstant.DEPLOY_PROCESS_DIAGRAM)
    @Produces("image/svg+xml")
    public Response getProcessDeployDiagram(@QueryParam("process") String process, @Context HttpServletResponse response) {
        InputStream diagram = activitiService.getProcessDeployDiagram(process);
        StreamingOutput output = outputStream -> IoUtil.copy(diagram, outputStream);
        return Response.ok(output).build();
    }


    /**
     * 获取流程实例运行流程图
     *
     * @param response jersey中可以使用@javax.ws.rs.core.Context注入response对象
     * @param processId 流程实例id
     * @return 流程任务svg流
     */
    @GET
    @Path(WorkflowUriConstant.INS_PROCESS_DIAGRAM)
    @Produces("image/svg+xml")
    public Response getProcessInsDiagram(@QueryParam("processId") String processId, @Context HttpServletResponse response) {
        InputStream diagram = activitiService.getProcessInsDiagram(processId);
        StreamingOutput output = outputStream -> IoUtil.copy(diagram, outputStream);
        return Response.ok(output).build();
    }
}
