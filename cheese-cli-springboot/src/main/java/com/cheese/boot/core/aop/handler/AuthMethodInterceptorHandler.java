package com.cheese.boot.core.aop.handler;

import com.cheese.boot.common.anno.Auth;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Auth注解拦截处理器
 *
 * @author sobann
 */
public class AuthMethodInterceptorHandler extends MethodInterceptorHandler {

    private static final Class<Auth> HANDLE_ANNOTATION = Auth.class;

    @Override
    public Annotation getAnnotation(MethodInvocation methodInvocation) {
        Auth auth = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), HANDLE_ANNOTATION);
        if (auth == null) {
            Class<?> targetClass = methodInvocation.getThis().getClass();
            Method method = ClassUtils.getMostSpecificMethod(methodInvocation.getMethod(), targetClass);
            auth = AnnotationUtils.findAnnotation(method, HANDLE_ANNOTATION);
        }
        return auth;
    }

    @Override
    public boolean supports(MethodInvocation methodInvocation) {
        Annotation annotation = this.getAnnotation(methodInvocation);
        return annotation != null;
    }

    @Override
    public void assertAuthorized(Annotation annotation) throws Exception {
        if (!(annotation instanceof Auth)) {
            return;
        }
        Auth auth = (Auth) annotation;
        String identifier = auth.identifier();
        String instanceId = auth.instanceId();
        boolean login = auth.login();

        StringBuilder logBuilder = new StringBuilder();
        List<Object> logArgs = new ArrayList<>();
        logBuilder.append("\n\n=============Auth Interceptor============\n");
        logBuilder.append("===> identifier: {}\n");
        logBuilder.append("===> instanceId: {}\n");
        logBuilder.append("===> login: {}\n");
        logArgs.add(identifier);
        logArgs.add(instanceId);
        logArgs.add(login);
        log.info(logBuilder.toString(), logArgs.toArray());
    }
}
