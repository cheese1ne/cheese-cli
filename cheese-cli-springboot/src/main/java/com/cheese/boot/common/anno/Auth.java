package com.cheese.boot.common.anno;

import java.lang.annotation.*;

/**
 * 接口权限控制标识
 *
 * @author sobann
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    /**
     * 操作标识符号
     */
    String identifier();

    /**
     * 实例id, 使用默认则代表不检查实例仅检查方法
     */
    String instanceId() default "_";

    /**
     * 是否检查登录 默认检查
     */
    boolean login() default true;
}
