package com.cheese.boot.core.log;

import com.cheese.boot.core.log.domain.ILogContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关日志默认输出方式：通过Spring自带的日志记录方式进行输出
 *
 * @author sobann
 */
@Slf4j
public class DefaultLogWriter implements ILogWriter {

    private final ObjectMapper objectMapper;

    public DefaultLogWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(ILogContent logContent) throws Exception {
        log.info(objectMapper.writeValueAsString(logContent));
    }
}
