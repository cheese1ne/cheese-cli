package com.cheese.boot.core.workflow.rule.parser.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * 元数据属性分发规则，在创建流程之前，会在某处储存需要创建流程实体元数据的一张表，此表中包含了流程创建的元数据，包括但不限于以下属性(可以根据业务自由拓展)
 * 1.业务实体类型
 * 2.业务实体id
 * 3.流程发起者id
 * 4.实体所属部门id
 * ...其他相关人id
 * <p>
 * 例如：
 * simpleMeta#operateUserId，任务分发给当前流程创建者
 * simpleMeta#approver，任务分发给元数据中配置的检查者 (元数据表根据业务自行设计)
 *
 * @author sobann
 */
public class SimpleMetaAssignRuleParser implements ISimpleAssignRuleParser {

    @Override
    public boolean match(String assignRule) {
        return assignRule.startsWith("simpleMeta#");
    }

    @Override
    public Set<Long> parse(String assignRule, String processId, String taskId) {
        String[] split = assignRule.split("#");
        //元数据字段
        String metaField = split[1];
        Set<Long> assigns = new HashSet<>();
        //todo
        return assigns;
    }
}
