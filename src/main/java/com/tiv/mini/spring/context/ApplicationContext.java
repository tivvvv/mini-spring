package com.tiv.mini.spring.context;

import com.tiv.mini.spring.beans.factory.ListableBeanFactory;
import com.tiv.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.tiv.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import com.tiv.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.tiv.mini.spring.context.event.ApplicationEventPublisher;
import com.tiv.mini.spring.core.env.Environment;
import com.tiv.mini.spring.core.env.EnvironmentCapable;

/**
 * 应用上下文接口,支持bean获取,上下文环境和事件发布
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory();

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh();

    void close();

    boolean isActive();

}
