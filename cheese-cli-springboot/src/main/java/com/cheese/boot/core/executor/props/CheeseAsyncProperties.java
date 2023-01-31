package com.cheese.boot.core.executor.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池核心属性配置
 *
 * @author sobann
 */
@Data
@ConfigurationProperties(prefix = "cheese.async")
public class CheeseAsyncProperties {

    @Value("${corePoolSize}")
    private int corePoolSize;

    @Value("${maxPoolSize}")
    private int maxPoolSize;

    @Value("${queueCapacity}")
    private int queueCapacity;

    @Value("${keepAliveSeconds}")
    private int keepAliveSeconds;

    @Override
    public String toString() {
        return "CheeseAsyncProperties{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueCapacity=" + queueCapacity +
                ", keepAliveSeconds=" + keepAliveSeconds +
                '}';
    }
}
