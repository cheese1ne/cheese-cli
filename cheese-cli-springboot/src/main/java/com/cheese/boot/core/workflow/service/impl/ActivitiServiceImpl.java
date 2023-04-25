package com.cheese.boot.core.workflow.service.impl;

import com.cheese.boot.core.workflow.cmd.DeleteTaskCmd;
import com.cheese.boot.core.workflow.cmd.SetFLowNodeAndGoCmd;
import com.cheese.boot.core.workflow.service.IActivitiService;
import com.cheese.boot.core.workflow.tool.DiagramUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * activity流程/任务处理默认实现
 *
 * @author sobann
 */
@Slf4j
@Service
public class ActivitiServiceImpl implements IActivitiService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final ManagementService managementService;
    private final HistoryService historyService;

    public ActivitiServiceImpl(RuntimeService runtimeService, TaskService taskService,
                               RepositoryService repositoryService, ManagementService managementService,
                               HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
        this.managementService = managementService;
        this.historyService = historyService;
    }

    @Override
    public String createProcessExecution(String process, Map<String, Object> param) {
        ProcessInstance processInstance = CollectionUtils.isEmpty(param) ? runtimeService.startProcessInstanceByKey(process) : runtimeService.startProcessInstanceByKey(process, param);
        String processInstanceId = processInstance.getProcessInstanceId();
        log.info("Start Process ={},id ={}", process, processInstanceId);
        return processInstanceId;
    }

    @Override
    public void handleTask(String taskId, Map<String, Object> params) {
        if (!CollectionUtils.isEmpty(params)) {
            taskService.setVariables(taskId, params);
        }
        taskService.complete(taskId);
    }

    @Override
    public List<Task> getActiveTasks(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).list();
    }

    @Override
    public List<HistoricTaskInstance> getHisTasks(String processId) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).finished().list();
    }

    @Override
    public Task getActiveTask(String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).singleResult();
    }

    @Override
    public void jump(String taskId, String targetTask) {
        //当前任务
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程定义
        Process process = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        //获取目标节点定义
        FlowNode targetNode = (FlowNode) process.getFlowElement(targetTask);
        //删除当前运行任务
        String executionEntityId = managementService.executeCommand(new DeleteTaskCmd(currentTask.getId()));
        //流程执行到来源节点
        managementService.executeCommand(new SetFLowNodeAndGoCmd(targetNode, executionEntityId));
    }

    @Override
    public void deleteTaskHistory(List<String> taskIds) {
        if (CollectionUtils.isEmpty(taskIds)) {
            return;
        }
        for (String taskId : taskIds) {
            historyService.deleteHistoricTaskInstance(taskId);
        }
    }

    @Override
    public void deleteRunProcess(String processId) {
        runtimeService.deleteProcessInstance(processId, "finish");
    }

    @Override
    public Map<String, Object> getProcessVariables(String processId) {
        return runtimeService.getVariables(processId);
    }

    @Override
    public Map<String, Object> getTaskVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    @Override
    public InputStream getProcessDeployDiagram(String process) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(process).orderByProcessDefinitionVersion().desc().list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ProcessDefinition processDefinition = list.get(0);
        BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());
        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        return generator.generateDiagram(model, "宋体", "微软雅黑", "黑体");
    }

    @Override
    public InputStream getProcessInsDiagram(String processInstanceId) {
        // 获取历史流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // 获取流程中已经执行的节点，按照执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
        // 构造已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }
        // 获取bpmnModel
        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        List<String> nodeIds = DiagramUtil.getExecutedFlows(bpmnModel, historicActivityInstanceList);
        // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        return processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, nodeIds, "宋体", "微软雅黑", "黑体");
    }
}
