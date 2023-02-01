package com.cheese.boot.core.workflow.service;

import com.cheese.boot.core.workflow.domain.ITaskConfig;

import java.util.List;

/**
 * 流程任务配置业务查询接口
 *
 * @author sobann
 */
public interface ITaskConfigService {

    /**
     * 根据 流程唯一标识和任务唯一标识查询任务配置
     *
     * @param processKey
     * @param taskKey
     * @return
     */
    ITaskConfig getTaskConfig(String processKey, String taskKey);

    /**
     * 获取某个流程下的全部任务配置
     *
     * @param processKey
     * @return
     */
    List<ITaskConfig> getTaskConfigs(String processKey);

    /**
     * 保存或修改任务配置
     *
     * @param taskConfig
     */
    void saveOrUpdate(ITaskConfig taskConfig);
}
