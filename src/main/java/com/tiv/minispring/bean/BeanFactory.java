package com.tiv.minispring.bean;

import com.tiv.minispring.bean.exception.BeansException;

/**
 * bean工厂接口
 */
public interface BeanFactory {

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 注册bean定义
     *
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

}
