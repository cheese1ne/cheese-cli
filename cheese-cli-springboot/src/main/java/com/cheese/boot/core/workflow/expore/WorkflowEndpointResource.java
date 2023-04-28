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
     * todo 创建流程之前需要进行检查，例如流程文件是否部署，流程是否满足创建条件等
     * 检查行为分类：
     *  1.系统级别的检查 就是bpmn文件部署的检查
     *  2.业务相关检查 n+ 的状态 此处是一个通用接口，每个具体的检查业务在设计时就被确定，要做的仅仅是将其封装好后提供给上层模块调用
     * 为了满足上层调用的设计，检查业务抽象出 检查器和检查器上下文(为一组，每一组对应一个流程)，上层通过流程管理器获取对应的流程检查器上下文完成具体检查业务的调用
     * 在处理任务时也有相应的检查动作，可以向上抽象检查器、上下文大类
     *
     * 明确一点：流程是为了管理数据而存在，所以每个流程一定会关联最少一个业务，设计一个流程业务元数据表 object_meta来维护, 所以流程创建时候需要检查元数据是否注册?
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
     * 处理任务的检查：任务id是否存在(系统级)、参数是否完整(业务级)
     * 不仅仅是检查，在关于流程的任何写操作(流程或流程任务创建和编辑) 的输入参数处理和归档 (activiti的获取流程参数有时并不很方便使用，考虑通过第三方的数据存储完成保存)
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
