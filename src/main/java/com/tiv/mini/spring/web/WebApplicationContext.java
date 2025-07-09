package com.tiv.mini.spring.web;

import com.tiv.mini.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * web应用上下文接口
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

}
