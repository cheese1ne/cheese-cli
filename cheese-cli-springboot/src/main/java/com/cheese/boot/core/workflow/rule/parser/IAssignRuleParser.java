package com.cheese.boot.core.workflow.rule.parser;

import java.util.Set;

/**
 * 任务分派规则解析器
 *
 * @author sobann
 */
public interface IAssignRuleParser {
    /**
     * 是否匹配
     *
     * @param assignRule
     * @return
     */
    boolean match(String assignRule);

    /**
     * 获取可分派人员的集合
     *
     * @param assignRule
     * @return
     */
    Set<Long> parse(String assignRule, String processId, String taskId);
}
