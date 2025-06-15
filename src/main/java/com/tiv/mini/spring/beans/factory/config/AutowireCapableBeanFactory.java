package com.tiv.mini.spring.beans.factory.config;

import com.tiv.mini.spring.beans.factory.exception.BeansException;

/**
 * 自动装配bean工厂接口
 */
public interface AutowireCapableBeanFactory {

    /**
     * 不自动装配
     */
    int AUTOWIRE_NO = 0;

    /**
     * 根据bean名称自动装配
     */
    int AUTOWIRE_BY_NAME = 1;

    /**
     * 根据bean类型自动装配
     */
    int AUTOWIRE_BY_TYPE = 2;

    /**
     * 在bean初始化之前应用bean后置处理器
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 在bean初始化之后应用bean后置处理器
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
