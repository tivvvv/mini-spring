package com.tiv.mini.spring.beans.factory;

import com.tiv.mini.spring.beans.factory.exception.BeansException;

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
     * 判断bean是否存在
     *
     * @param beanName
     * @return
     */
    boolean containsBean(String beanName);

    /**
     * 判断bean是否是单例模式
     *
     * @param beanName
     * @return
     */
    boolean isSingleton(String beanName);

    /**
     * 判断bean是否是原型模式
     *
     * @param beanName
     * @return
     */
    boolean isPrototype(String beanName);

    /**
     * 获取bean的类型
     *
     * @param beanName
     * @return
     */
    Class<?> getType(String beanName);

}
