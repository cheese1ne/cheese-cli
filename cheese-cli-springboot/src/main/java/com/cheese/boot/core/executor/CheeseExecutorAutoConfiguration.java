package com.cheese.boot.core.executor;

import com.cheese.boot.core.executor.props.CheeseAsyncProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池bean配置类，已使用META-INF/spring.factories进行装配
 *
 * @author sobann
 */
@Slf4j
@EnableAsync
@EnableScheduling
@PropertySource("classpath:cheese-async.yml")
@EnableConfigurationProperties({CheeseAsyncProperties.class})
public class CheeseExecutorAutoConfiguration extends AsyncConfigurerSupport {

    private final CheeseAsyncProperties cheeseAsyncProperties;

    public CheeseExecutorAutoConfiguration(CheeseAsyncProperties cheeseAsyncProperties) {
        this.cheeseAsyncProperties = cheeseAsyncProperties;
    }

    @Bean(name = {"async-Executor"})
    @Override
    public Executor getAsyncExecutor() {
        log.info("loading thread properties: {}", cheeseAsyncProperties);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cheeseAsyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(cheeseAsyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(cheeseAsyncProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(cheeseAsyncProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("async-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

