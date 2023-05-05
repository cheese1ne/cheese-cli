package org.springframework.web;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * tomcat web-server bean检查
 *
 * @author sobann
 */
public class TomcatWebServerCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ClassLoader classLoader = context.getClassLoader();
        try {
            classLoader.loadClass("org.apache.catalina.startup.Tomcat");
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
