package com.tiv.mini.spring.beans.factory.config;

import com.tiv.mini.spring.beans.factory.exception.BeansException;

/**
 * bean实例化后置处理器
 */
public interface BeanPostProcessor {

    /**
     * bean初始化之前处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化之后处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
