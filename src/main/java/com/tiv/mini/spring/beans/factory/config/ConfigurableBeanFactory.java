package com.tiv.mini.spring.beans.factory.config;

import com.tiv.mini.spring.beans.factory.BeanFactory;

/**
 * 可配置的Bean工厂接口
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    /**
     * 添加Bean后置处理器
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取Bean后置处理器的数量
     *
     * @return
     */
    int getBeanPostProcessorCount();

}
