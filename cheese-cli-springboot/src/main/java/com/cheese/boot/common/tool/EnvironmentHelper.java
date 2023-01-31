package com.cheese.boot.common.tool;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 环境属性提供工具类
 *
 * @author sobann
 */
@Component
public class EnvironmentHelper implements EnvironmentAware {

    private static Environment environment;

    /**
     * 对外提供配置信息对象
     *
     * @return
     */
    public static Environment getEnvironment() {
        return EnvironmentHelper.environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        EnvironmentHelper.environment = environment;
    }
}
