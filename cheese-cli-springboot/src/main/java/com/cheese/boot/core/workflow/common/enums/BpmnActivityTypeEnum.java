package com.cheese.boot.core.workflow.common.enums;

/**
 * activiti bpmn中节点处理枚举
 *
 * @author sobann
 */
public enum BpmnActivityTypeEnum {

    /**
     * 开始事件
     */
    START_EVENT("startEvent", "开始事件"),
    /**
     * 结束事件
     */
    END_EVENT("endEvent", "结束事件"),
    /**
     * 用户任务
     */
    USER_TASK("userTask", "用户任务"),
    /**
     * 排他网关
     */
    EXCLUSIVE_GATEWAY("exclusiveGateway", "排他网关"),
    /**
     * 并行网关
     */
    PARALLEL_GATEWAY("parallelGateway", "并行网关"),
    /**
     * 包含网关
     */
    INCLUSIVE_GATEWAY("inclusiveGateway", "包含网关");

    private String type;
    private String name;

    BpmnActivityTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
