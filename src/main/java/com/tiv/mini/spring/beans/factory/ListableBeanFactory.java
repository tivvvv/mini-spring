package com.tiv.mini.spring.beans.factory;

import com.tiv.mini.spring.beans.factory.exception.BeansException;

import java.util.Map;

/**
 * 可枚举的bean工厂接口
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 判断是否包含指定的bean定义信息
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取bean定义信息数量
     *
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 获取bean定义信息名称
     *
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据类型获取bean定义信息名称
     *
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据类型获取bean定义信息
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}
