package com.cheese.boot.test.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解用于属性变化的监听
 * 可以借助aop来拓展记录对象变化的过程
 * todo
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AttributeChangeRecord {

    String[] value() default {};

    @AliasFor("value")
    String[] recordFields() default {};
}
