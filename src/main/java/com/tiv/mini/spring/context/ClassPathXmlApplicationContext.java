package com.tiv.mini.spring.context;

import com.tiv.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.tiv.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.tiv.mini.spring.beans.factory.exception.BeansException;
import com.tiv.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import com.tiv.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.tiv.mini.spring.context.event.ApplicationEvent;
import com.tiv.mini.spring.context.event.ApplicationListener;
import com.tiv.mini.spring.context.event.ContextRefreshEvent;
import com.tiv.mini.spring.context.event.SimpleApplicationEventPublisher;
import com.tiv.mini.spring.core.ClassPathXmlResource;
import com.tiv.mini.spring.core.Resource;

/**
 * 类路径xml应用上下文
 * 上下文负责整合容器的启动过程,读取外部配置,解析并构建bean定义,创建bean工厂
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            refresh();
        }
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void initApplicationEventPublisher() {
        SimpleApplicationEventPublisher simpleApplicationEventPublisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(simpleApplicationEventPublisher);
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("context refresh finished."));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

}
