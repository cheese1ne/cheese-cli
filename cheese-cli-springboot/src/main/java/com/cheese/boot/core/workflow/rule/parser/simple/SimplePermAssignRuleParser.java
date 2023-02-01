package com.cheese.boot.core.workflow.rule.parser.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取权限对应用户作为分派对象
 * <p>
 * 借助cheese-shiro权限设计
 * 例如：simplePerm#process:approve 在permission中配置的具有process:approve权限的用户
 *
 * @author sobann
 */
public class SimplePermAssignRuleParser implements ISimpleAssignRuleParser {

    @Override
    public boolean match(String assignRule) {
        return assignRule.startsWith("simplePerm#");
    }

    @Override
    public Set<Long> parse(String assignRule, String processId, String taskId) {
        String[] split = assignRule.split("#");
        String permissionIdentifier = split[1];
        Set<Long> assigns = new HashSet<>();
        //todo
        return assigns;
    }
}
