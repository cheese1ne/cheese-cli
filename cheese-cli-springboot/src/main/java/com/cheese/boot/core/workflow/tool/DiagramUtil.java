package com.cheese.boot.core.workflow.tool;

import com.cheese.boot.core.workflow.common.constant.WorkflowMapKeyConstant;
import com.cheese.boot.core.workflow.common.enums.BpmnActivityTypeEnum;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.history.HistoricActivityInstance;

import java.util.*;

/**
 * 流程工具
 *
 * @author sobann
 */
public class DiagramUtil {

    private DiagramUtil() {}

    public static List<String> getExecutedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 流转线ID集合
        List<String> flowIdList = new ArrayList<>();
        // 全部活动实例
        List<FlowNode> historicFlowNodeList = new LinkedList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstanceList = new LinkedList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            historicFlowNodeList.add((FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true));
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstanceList.add(historicActivityInstance);
            }
        }
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        FlowNode currentFlowNode = null;
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstanceList) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlowList = currentFlowNode.getOutgoingFlows();
            /**
             * 遍历outgoingFlows并找到已已流转的
             * 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或包含网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最近的流转节点视为有效流转
             */
            FlowNode targetFlowNode = null;
            if (BpmnActivityTypeEnum.PARALLEL_GATEWAY.getType().equals(currentActivityInstance.getActivityType())
                    || BpmnActivityTypeEnum.INCLUSIVE_GATEWAY.getType().equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicFlowNodeList.contains(targetFlowNode)) {
                        flowIdList.add(sequenceFlow.getId());
                    }
                }
            } else {
                List<Map<String, String>> tempMapList = new LinkedList<Map<String, String>>();
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlowList) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, String> tempMap = new HashMap<>(8);
                            tempMap.put(WorkflowMapKeyConstant.FLOW_NODE_ID, sequenceFlow.getId());
                            tempMap.put(WorkflowMapKeyConstant.FLOW_NODE_START_TIME, String.valueOf(historicActivityInstance.getStartTime().getTime()));
                            tempMapList.add(tempMap);
                        }
                    }
                }
                // 遍历匹配的集合，取得开始时间最早的一个
                long currentTime = currentActivityInstance.getStartTime().getTime();
                long earliestStamp = 0L;
                String nodeId = null;
                for (Map<String, String> map : tempMapList) {
                    long nodeStartTime = Long.parseLong(map.get(WorkflowMapKeyConstant.FLOW_NODE_START_TIME));
                    boolean early = earliestStamp == 0L || earliestStamp >= nodeStartTime;
                    if (nodeStartTime > currentTime && early) {
                        earliestStamp = nodeStartTime;
                        nodeId = map.get(WorkflowMapKeyConstant.FLOW_NODE_ID);
                    }
                }
                flowIdList.add(nodeId);
            }
        }
        return flowIdList;
    }
}
