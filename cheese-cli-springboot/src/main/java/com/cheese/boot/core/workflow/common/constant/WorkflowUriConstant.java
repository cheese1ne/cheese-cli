package com.cheese.boot.core.workflow.common.constant;

/**
 * 流程uri资源常量维护类
 *
 * @author sobann
 */
public interface WorkflowUriConstant {

    String BASE_URI = "/api";
    String WORK_FLOW = "/workflow";
    String CREATE_PROCESS_EXECUTION = "/process/create";
    String ACTIVE_TASK = "/task/active";
    String HIS_TASK = "/task/completed";
    String SUBMIT_TASK = "/task/submit";
    String ACTIVE_TASK_VARIABLE = "/active/task/variables";
    String DEPLOY_PROCESS_DIAGRAM = "/process/deploy/diagram";
    String INS_PROCESS_DIAGRAM = "/process/ins/diagram";
}
