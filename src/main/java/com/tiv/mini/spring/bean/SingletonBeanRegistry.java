package com.tiv.mini.spring.bean;

/**
 * 单例bean注册接口
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例bean
     *
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例bean
     *
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 判断单例bean是否存在
     *
     * @param beanName
     * @return
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例bean的名称
     *
     * @return
     */
    String[] getSingletonNames();

}
