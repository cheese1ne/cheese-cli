package com.cheese.boot.core.workflow.domain;

/**
 * 流程业务主体元数据 在创建流程前是必须的
 * 分发规则解释器中会使用到这里的内容，流程发起者、资源所属部门等
 * tip:一个实体可以创建多个流程
 *
 * @author sobann
 */
public interface IObjectMeta {

    /**
     * 业务类型
     *
     * @return 获取业务类型
     */
    String getObjectType();

    /**
     * 业务id
     *
     * @return 获取业务id
     */
    Long getObjectId();

    /**
     * 流程发起人
     *
     * @return 获取流程发起人id
     */
    Long operateUserId();

    /**
     * 资源所属部门
     *
     * @return 获取资源所属部门id
     */
    Long getDeptId();
}
