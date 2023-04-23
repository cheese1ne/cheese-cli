package com.cheese.boot.core.workflow.domain;

/**
 * 任务配置接口
 *
 * @author sobann
 */
public interface ITaskConfig {

    /**
     * 任务唯一标识
     *
     * @return 获取任务唯一标识
     */
    String getKey();

    /**
     * 任务名称
     *
     * @return 获取任务名称
     */
    String getName();

    /**
     * 关联流程的标识
     *
     * @return 获取所属流程的标识
     */
    String getProcessKey();

    /**
     * 排序字段
     *
     * @return 获取任务的顺序
     */
    String getSort();

    /**
     * 任务分发规则，需要建立响应的流程规则解释器
     * 一般情况下，流程移交需要签发给下一个任务节点的处理者，但可能存在如下业务，流程分发通过系统规则默认处理
     * 提供规则的一些思路：
     * 1.分发给流程创建者(首个任务以及正常任务回退可以通过此规则分发给流程创建者，规则可以写为：initiator)
     * 2.分发给部门其他角色(部门内部流程，分发给流程创建者所在部门的某一角色或多个角色，规则可以写为：deptrole#25,50.51)
     * 3.在cheese-shiro中，可以指定具有某种特定权限的用户作为分发对象，如：perm#process#approve#1,2,3 (tip：在cheese-shiro框架中，具有process:approve:1,2,3权限的用户可以对权限实体为process表中id为1，2，3的流程进行审查)
     * 4.在cheese-shiro中，分发给指定实体，例如：link#role#self#5
     *
     * @return 获取任务分发规则
     */
    String getAssignRule();

    /**
     * 流程移交参数，以","隔开，如果配置在提交前检查，必填
     *
     * @return 获取流程任务移交参数
     */
    String taskParams();
}
