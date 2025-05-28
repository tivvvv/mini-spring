package com.tiv.minispring.bean;

import com.tiv.minispring.bean.exception.BeansException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单bean工厂
 */
@NoArgsConstructor
public class SimpleBeanFactory implements BeanFactory {

    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private final List<String> beanNames = new ArrayList<>();

    private final Map<String, Object> singletons = new HashMap<>();

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试获取bean实例
        Object bean = singletons.get(beanName);
        if (bean == null) {
            int index = beanNames.indexOf(beanName);
            if (index == -1) {
                throw new BeansException("bean not found");
            } else {
                // 获取bean定义
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    // 创建bean实例
                    bean = Class.forName(beanDefinition.getClassName()).newInstance();
                    // 注册bean实例
                    singletons.put(beanDefinition.getId(), bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    /**
     * 注册bean定义
     *
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
