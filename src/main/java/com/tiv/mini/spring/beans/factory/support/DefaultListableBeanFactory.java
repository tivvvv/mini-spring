package com.tiv.mini.spring.beans.factory.support;

import com.tiv.mini.spring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.tiv.mini.spring.beans.factory.config.BeanDefinition;
import com.tiv.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.tiv.mini.spring.beans.factory.exception.BeansException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 可枚举的bean工厂默认实现
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            BeanDefinition beanDefinitionToMatch = this.getBeanDefinition(beanName);
            Class<?> classToMatch = beanDefinitionToMatch.getClass();
            boolean matchFound = type.isAssignableFrom(classToMatch);
            if (matchFound) {
                result.add(beanName);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            result.put(beanName, (T) getBean(beanName));
        }
        return result;
    }

}
