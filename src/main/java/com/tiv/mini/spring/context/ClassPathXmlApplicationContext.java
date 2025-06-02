package com.tiv.mini.spring.context;

import com.apple.eawt.ApplicationEvent;
import com.tiv.mini.spring.beans.factory.exception.BeansException;
import com.tiv.mini.spring.beans.factory.BeanFactory;
import com.tiv.mini.spring.beans.factory.support.SimpleBeanFactory;
import com.tiv.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.tiv.mini.spring.context.event.ApplicationEventPublisher;
import com.tiv.mini.spring.core.ClassPathXmlResource;
import com.tiv.mini.spring.core.Resource;

/**
 * 类路径xml应用上下文
 * 上下文负责整合容器的启动过程,读取外部配置,解析并构建bean定义,创建bean工厂
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(simpleBeanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = simpleBeanFactory;
        if (isRefresh) {
            this.beanFactory.refresh();
        }
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
    public boolean isSingleton(String beanName) {
        return false;
    }

    @Override
    public boolean isPrototype(String beanName) {
        return false;
    }

    @Override
    public Class<?> getType(String beanName) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
