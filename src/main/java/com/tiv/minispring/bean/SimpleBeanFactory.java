package com.tiv.minispring.bean;

import com.tiv.minispring.bean.exception.BeansException;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单bean工厂
 */
@NoArgsConstructor
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试获取bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            // 获取bean定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("bean not found");
            }
            try {
                // 创建bean实例
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 注册bean实例
            this.registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }


    @Override
    public Boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    /**
     * 注册bean定义
     *
     * @param beanDefinition
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

}
