package org.springframework.web;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author sobann
 */
public class TomcatWebServer implements WebServer, ApplicationContextAware {

    private WebApplicationContext webApplicationContext;
    private final Tomcat tomcat;

    public TomcatWebServer() {
        this.tomcat = new Tomcat();
    }

    @Override
    public void start() throws Exception {
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(8081);

        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");
        Host host = new StandardHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "disPatcherServlet", new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*", "disPatcherServlet");

        try {
            tomcat.start();
        }catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        this.tomcat.stop();
    }

    @Override
    public int getPort() {
        return tomcat.getConnector().getPort();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webApplicationContext = (WebApplicationContext) applicationContext;
    }
}
