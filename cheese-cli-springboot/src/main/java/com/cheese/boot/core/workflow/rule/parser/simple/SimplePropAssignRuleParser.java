package com.cheese.boot.core.workflow.rule.parser.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * 实体属性分发规则：借助cheese-shiro中权限实体对应的查询内容，以当前uid作为唯一条件，通过 user-entity的关联属性作为限制条件获取符合条件的数据列表
 * <p>
 * 例如：
 * simpleProp#project#leaderId，任务分发给当前线程内uid与project实体关联的leaderId字段的数据
 * simpleProp#apply#createUser，任务分发给当前线程内uid与apply实体的createUser字段的数据
 * 注意：当entity不为user时候，需要在perm_link中配置user与当前entity的规则
 *
 * @author sobann
 */
public class SimplePropAssignRuleParser implements ISimpleAssignRuleParser {

    @Override
    public boolean match(String assignRule) {
        return assignRule.startsWith("simpleProp#");
    }

    @Override
    public Set<Long> parse(String assignRule, String processId, String taskId) {
        String[] split = assignRule.split("#");
        String entity = split[1];
        Set<Long> assigns = new HashSet<>();
        //todo
        return assigns;
    }
}
