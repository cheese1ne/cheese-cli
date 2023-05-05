package org.springframework.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author sobann
 */
@Configuration
public class WebServerAutoConfiguration {

    @Bean
    @Conditional(TomcatWebServerCondition.class)
    public TomcatWebServer tomcatWebServer() {
        return new TomcatWebServer();
    }

    @Bean
    @Conditional(JettyWebServerCondition.class)
    public JettyWebServer jettyWebServer() {
        return new JettyWebServer();
    }
}
