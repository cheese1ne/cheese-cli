package com.cheese.boot.core.workflow.domain;

/**
 * 流程配置接口
 *
 * @author sobann
 */
public interface IProcessConfig {
    /**
     * 流程唯一标识
     *
     * @return
     */
    String getKey();

    /**
     * 流程名称
     *
     * @return
     */
    String getName();

    /**
     * 前置流程 如果无 设为null
     *
     * @return
     */
    String getPreProcessKey();

    /**
     * 流程类型
     *
     * @return
     */
    String getType();

    /**
     * 排序字段
     *
     * @return
     */
    String getSort();
}
