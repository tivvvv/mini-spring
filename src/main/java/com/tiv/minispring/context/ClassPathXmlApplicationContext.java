package com.tiv.minispring.context;

import com.tiv.minispring.bean.BeanFactory;
import com.tiv.minispring.bean.SimpleBeanFactory;
import com.tiv.minispring.bean.XmlBeanDefinitionReader;
import com.tiv.minispring.bean.exception.BeansException;
import com.tiv.minispring.core.ClassPathXmlResource;
import com.tiv.minispring.core.Resource;

/**
 * 类路径xml应用上下文
 * 上下文负责整合容器的启动过程,读取外部配置,解析并构建bean定义,创建bean工厂
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

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
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

}
