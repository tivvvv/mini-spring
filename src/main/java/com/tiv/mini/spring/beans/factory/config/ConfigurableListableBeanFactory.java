package com.tiv.mini.spring.beans.factory.config;

import com.tiv.mini.spring.beans.factory.ListableBeanFactory;

/**
 * 可配置可枚举的bean工厂接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {
}
