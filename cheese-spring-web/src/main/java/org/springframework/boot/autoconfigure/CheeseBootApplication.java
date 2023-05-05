package org.springframework.boot.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebServerAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模拟springboot-web启动注解
 *
 *
 * @author sobann
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({WebServerAutoConfiguration.class})
@ComponentScan
public @interface CheeseBootApplication {
}
