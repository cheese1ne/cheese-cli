package com.cheese.boot.core.log;

import com.cheese.boot.core.log.domain.ILogContent;

/**
 * 日志输出 顶层接口
 * 1.默认打印
 * 2.关系型数据库存储
 * 3.时序数据库存储elasticsearch/mongodb
 *
 * @author sobann
 */
public interface ILogWriter {

    /**
     * 写日志
     *
     * @param logContent
     * @throws Exception
     */
    void write(ILogContent logContent) throws Exception;
}
