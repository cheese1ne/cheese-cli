package com.cheese.boot.core.aop.handler;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * @author sobann
 */
public abstract class MethodInterceptorHandler {

    protected static final Logger log = LoggerFactory.getLogger(MethodInterceptorHandler.class);

    /**
     * 处理器是否支持方法的拦截
     *
     * @param methodInvocation
     * @return
     */
    public abstract boolean supports(MethodInvocation methodInvocation);

    /**
     * 获取权限注解
     *
     * @param methodInvocation
     * @return
     */
    public abstract Annotation getAnnotation(MethodInvocation methodInvocation);


    /**
     * 对方法进行拦截处理
     *
     * @param annotation
     * @throws Exception 不符合条件时候抛出异常
     */
    public abstract void assertAuthorized(Annotation annotation) throws Exception;

}
