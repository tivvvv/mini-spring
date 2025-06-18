package com.tiv.mini.spring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        String webAppDirLocation = "src/main/WebApp";
        StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(webAppDirLocation).getAbsolutePath());
        Connector connector = new Connector();
        connector.setPort(8000);
        tomcat.setConnector(connector);
        tomcat.start();
        tomcat.getServer().await();
    }

}
