package com.tiv.mini.spring.beans.factory.support;

import com.tiv.mini.spring.beans.factory.config.BeanDefinition;

/**
 * bean定义信息仓库接口
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册bean定义信息
     *
     * @param name
     * @param beanDefinition
     */
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    /**
     * 移除bean定义信息
     *
     * @param name
     */
    void removeBeanDefinition(String name);

    /**
     * 获取bean定义信息
     *
     * @param name
     * @return
     */
    BeanDefinition getBeanDefinition(String name);

    /**
     * 判断是否包含指定的bean定义信息
     *
     * @param name
     * @return
     */
    boolean containsBeanDefinition(String name);

}
