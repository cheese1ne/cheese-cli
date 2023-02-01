package com.cheese.boot.core.workflow.service;

import com.cheese.boot.core.workflow.domain.IObjectMeta;

import java.util.List;

/**
 * 流程业务主体元数据接口
 *
 * @author sobann
 */
public interface IObjectMetaService {

    /**
     * 查询全部流程业务元数据
     *
     * @return
     */
    List<IObjectMeta> getObjectMetas();

    /**
     * 查询具体业务类型的流程业务元数据
     *
     * @return
     */
    List<IObjectMeta> getObjectMetas(String objectType);


    /**
     * 根据业务类型和业务id确认唯一元数据
     *
     * @param objectType
     * @param objectId
     * @return
     */
    IObjectMeta getObjectMeta(String objectType, Long objectId);

    /**
     * 保存或修改流程业务主体元数据
     *
     * @param objectMeta
     */
    void saveOrUpdate(IObjectMeta objectMeta);
}
