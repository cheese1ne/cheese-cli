package com.cheese.boot.core.aop.advisor;

import com.cheese.boot.common.anno.Auth;
import com.cheese.boot.core.aop.interceptor.AnnotationsAuthorizingMethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 自定义切面 接入spring-aop
 *
 * @author sobann
 */
public class AuthorizationAttributeSourceAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private static final Class<? extends Annotation>[] AUTH_ANNOTATION_CLASSES = new Class[]{Auth.class};

    public AuthorizationAttributeSourceAdvisor() {
        setAdvice(new AnnotationsAuthorizingMethodInterceptor());
    }

    /**
     * 此方法在注入spring容器时对切面匹配时候被调用
     *
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (isAuthAnnotationPresent(method)) {
            return true;
        }
        try {
            method = targetClass.getMethod(method.getName(), method.getParameterTypes());
            return isAuthAnnotationPresent(method) || isAuthAnnotationPresent(targetClass);
        } catch (NoSuchMethodException ignored) {
        }
        return false;
    }

    /**
     * 如果类上标注了注解同样视为需要处理的方法
     *
     * @param targetClazz
     * @return
     */
    private boolean isAuthAnnotationPresent(Class<?> targetClazz) {
        for (Class<? extends Annotation> annClass : AUTH_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(targetClazz, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法上标注的方法若配置了权限注解则视为需要aop处理
     *
     * @param method
     * @return
     */
    private boolean isAuthAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : AUTH_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }
}
