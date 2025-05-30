package com.tiv.minispring.context;

import com.apple.eawt.ApplicationEvent;
import com.tiv.minispring.bean.BeanFactory;
import com.tiv.minispring.bean.SimpleBeanFactory;
import com.tiv.minispring.bean.exception.BeansException;
import com.tiv.minispring.bean.xml.XmlBeanDefinitionReader;
import com.tiv.minispring.context.event.ApplicationEventPublisher;
import com.tiv.minispring.core.ClassPathXmlResource;
import com.tiv.minispring.core.Resource;

/**
 * 类路径xml应用上下文
 * 上下文负责整合容器的启动过程,读取外部配置,解析并构建bean定义,创建bean工厂
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this.beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.beanFactory);
        Resource resource = new ClassPathXmlResource(fileName);
        reader.loadBeanDefinitions(resource);
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
