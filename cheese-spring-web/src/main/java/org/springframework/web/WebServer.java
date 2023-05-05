package org.springframework.web;

/**
 * 一个web服务接口
 *
 * @author sobann
 */
public interface WebServer {
    void start() throws Exception;

    void stop() throws Exception;

    int getPort();
}
