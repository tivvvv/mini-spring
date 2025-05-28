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
     * 注册bean
     *
     * @param beanName
     * @param obj
     */
    void registerBean(String beanName, Object obj);

    /**
     * 判断bean是否存在
     *
     * @param beanName
     * @return
     */
    Boolean containsBean(String beanName);

}
