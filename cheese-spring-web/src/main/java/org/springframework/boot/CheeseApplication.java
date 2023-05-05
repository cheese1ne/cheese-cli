package org.springframework.boot;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.WebServer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Map;

/**
 * @author sobann
 */
public class CheeseApplication {

    public static ConfigurableApplicationContext run(Class<?> mainClass, String... args) {
        // 创建一个web的上下文
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(mainClass);
        // 刷新容器
        webApplicationContext.refresh();

        try {
            WebServer webServer = getWebServer(webApplicationContext);
            webServer.start();
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 创建一个webServer
        return webApplicationContext;
    }

    private static WebServer getWebServer(WebApplicationContext webApplicationContext) {
        Map<String, WebServer> webServers = webApplicationContext.getBeansOfType(WebServer.class);
        if (webServers.isEmpty()) {
            throw new NullPointerException();
        }
        if (webServers.size() > 1) {
            throw new IllegalStateException();
        }
        return webServers.values().stream().findFirst().get();
    }
}
