package com.cheese.boot.core.workflow.rule.parser.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门角色分发规则：分发给当前流程资源所属部门相关的某个角色
 * <p>
 * 例如：
 * simpleDeptRole#self#50 获取流程资源所属部门本级中角色id为50的用户
 * simpleDeptRole#parent:1#9 获取流程资源所属部门的上一级部门角色id为9的用户
 * simpleDeptRole#child:2#52 获取流程资源所属部门的下两级部门角色id为52的用户
 *
 * @author sobann
 */
public class SimpleDeptRoleAssignRuleParser implements ISimpleAssignRuleParser {

    @Override
    public boolean match(String assignRule) {
        return assignRule.startsWith("simpleDeptRole#");
    }

    @Override
    public Set<Long> parse(String assignRule, String processId, String taskId) {
        String[] split = assignRule.split("#");
        String dataRange = split[1];
        String roleId = split[2];
        Set<Long> assigns = new HashSet<>();
        //todo
        return assigns;
    }
}
