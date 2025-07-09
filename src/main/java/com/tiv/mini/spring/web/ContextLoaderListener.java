package com.tiv.mini.spring.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@NoArgsConstructor
@AllArgsConstructor
public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    private WebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String contextConfigLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        WebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext(contextConfigLocation);
        webApplicationContext.setServletContext(servletContext);
        this.context = webApplicationContext;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }

}
