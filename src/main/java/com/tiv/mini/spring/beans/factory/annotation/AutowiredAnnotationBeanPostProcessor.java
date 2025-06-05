package com.tiv.mini.spring.beans.factory.annotation;

import com.tiv.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.tiv.mini.spring.beans.factory.config.BeanPostProcessor;
import com.tiv.mini.spring.beans.factory.exception.BeansException;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * 自动注入注解bean实例化后置处理器
 */
@Setter
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                // 标注了@Autowired的字段
                if (isAutowired) {
                    String fieldName = field.getName();
                    // 查找同名bean实例
                    Object autowiredObject = this.getBeanFactory().getBean(fieldName);
                    try {
                        // 设置属性值
                        field.setAccessible(true);
                        field.set(bean, autowiredObject);
                        System.out.printf("Autowire %s for bean %s%n", fieldName, beanName);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
