package com.cheese.boot.core.aop.interceptor;

import com.cheese.boot.core.aop.handler.AuthMethodInterceptorHandler;
import com.cheese.boot.core.aop.handler.MethodInterceptorHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author sobann
 */
public class AnnotationsAuthorizingMethodInterceptor implements MethodInterceptor {

    private Collection<MethodInterceptorHandler> collections;

    public AnnotationsAuthorizingMethodInterceptor() {
        this.collections = new ArrayList<>();
        collections.add(new AuthMethodInterceptorHandler());
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    /**
     * 认证
     *
     * @param methodInvocation
     * @throws Exception
     */
    protected void assertAuthorized(MethodInvocation methodInvocation) throws Exception {
        if (this.collections != null && !this.collections.isEmpty()) {
            for (MethodInterceptorHandler handler : this.collections) {
                if (handler.supports(methodInvocation)) {
                    handler.assertAuthorized(handler.getAnnotation(methodInvocation));
                }
            }
        }
    }
}
