package com.cheese.boot.core.workflow.cmd;

import com.cheese.boot.common.constant.NumberConstant;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import java.util.List;

/**
 * 流程跳转命令
 *
 * @author sobann
 */
public class SetFLowNodeAndGoCmd implements Command<Void> {

    private FlowNode flowElement;
    private String executionId;

    public SetFLowNodeAndGoCmd(FlowNode flowElement, String executionId) {
        this.flowElement = flowElement;
        this.executionId = executionId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        // 获取目标节点的来源连线
        List<SequenceFlow> flows = flowElement.getIncomingFlows();
        if (flows == null || flows.size() < NumberConstant.ONE) {
            throw new ActivitiException("The target node cannot be reached");
        }
        // 随便选一条连线来执行，当前执行计划为，从连线流转到目标节点，实现跳转
        ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);
        executionEntity.setCurrentFlowElement(flows.get(NumberConstant.ZERO));
        commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
        return null;
    }
}
