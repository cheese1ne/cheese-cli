package com.cheese.boot.core.aop.config;

import com.cheese.boot.core.aop.advisor.AuthorizationAttributeSourceAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 权限配置
 *
 * @author sobann
 */
@Slf4j
@Configuration
public class AuthConfig {

    /**
     * 自定义权限注解处理切面
     *
     * @return
     */
    @Bean
    @Lazy
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        log.info("prepare to initialize authorizationAttributeSourceAdvisor.");
        return new AuthorizationAttributeSourceAdvisor();
    }
}
