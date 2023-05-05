package org.springframework.web;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author sobann
 */
public class JettyWebServerCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ClassLoader classLoader = context.getClassLoader();
        try {
            classLoader.loadClass("org.eclipse.jetty.server.Server");
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
