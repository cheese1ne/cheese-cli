package com.cheese.boot.test;

import com.cheese.boot.CheeseBootApplication;
import com.cheese.boot.test.extend.CompositeService;
import com.cheese.boot.test.lifecycle.LifeCycle;
import com.cheese.boot.test.lifecycle.User;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试
 *
 * @author sobann
 */
@SpringBootTest(classes = {CheeseBootApplication.class})
@RunWith(SpringRunner.class)
@Profile("dev")
public class CheeseBootApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(CheeseBootApplicationTest.class, args);
    }

    @Configuration
    public static class BeanLifeCycleConfiguration {
        @Bean
        public User getUser() {
            return new User();
        }

        @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
        public LifeCycle getLifeCycle() {
            return new LifeCycle();
        }
    }

    /**
     * 拓展的注解不仅仅能基于Spring原生注解拓展属性，也能够作为判断条件来对某些Bean进行实例化
     */
    @AutoConfigureOrder
    @ConditionalOnMissingBean(annotation = { CompositeService.class })
    public static class ExtendAnnotationConfiguration {
        // ...other bean definition
    }
}
