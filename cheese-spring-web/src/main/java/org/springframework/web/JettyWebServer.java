package org.springframework.web;

/**
 * @author sobann
 */
public class JettyWebServer implements WebServer{

    @Override
    public void start() throws Exception {
        System.out.println("jetty server up...");
    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public int getPort() {
        return 0;
    }
}
