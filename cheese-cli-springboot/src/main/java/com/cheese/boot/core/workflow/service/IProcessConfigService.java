package com.cheese.boot.core.workflow.service;

import com.cheese.boot.core.workflow.domain.IProcessConfig;

import java.util.List;

/**
 * 流程配置业务查询接口
 *
 * @author sobann
 */
public interface IProcessConfigService {

    /**
     * 根据流程key获取流程配置
     *
     * @param process
     * @return
     */
    IProcessConfig getConfig(String process);

    /**
     * 获取全部流程配置
     *
     * @return
     */
    List<IProcessConfig> getConfigs();


    /**
     * 重载：获取指定类型的流程配置
     *
     * @param type
     * @return
     */
    List<IProcessConfig> getConfigs(String type);

    /**
     * 保存或修改流程配置
     *
     * @param processConfig
     */
    void saveOrUpdate(IProcessConfig processConfig);
}
