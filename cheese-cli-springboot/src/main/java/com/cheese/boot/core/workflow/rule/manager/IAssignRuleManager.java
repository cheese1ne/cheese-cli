package com.cheese.boot.core.workflow.rule.manager;

import com.cheese.boot.core.workflow.rule.parser.IAssignRuleParser;

/**
 * 任务分派规则管理器
 *
 * @author sobann
 */
public interface IAssignRuleManager<T extends IAssignRuleParser> {
    /**
     * 添加一个解释器
     *
     * @param assignRuleParser
     */
    void addParser(T assignRuleParser);

    /**
     * 未指定人员分发
     * 需要通过流程解释器 + 流程任务配置解析出符合条件的人然后完成分发操作
     *
     * @param process   流程唯一key
     * @param processId 流程实例id
     * @param task      任务唯一key
     * @param taskId    任务id
     * @return
     */
    Long assign(String process, String processId, String task, String taskId);

    /**
     * 指定人员分发
     * 当assginUser不为null时，代表任务直接分发给指定用户(一般为首个任务以及用户移交任务时选择分发人的情况)
     *
     * @param process    流程唯一key
     * @param processId  流程实例id
     * @param task       任务唯一key
     * @param taskId     任务id
     * @param assginUser 分发给谁
     * @return
     */
    Long assign(String process, String processId, String task, String taskId, Long assginUser);
}
