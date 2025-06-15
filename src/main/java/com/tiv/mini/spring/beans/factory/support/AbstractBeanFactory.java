package com.tiv.mini.spring.beans.factory.support;

import com.tiv.mini.spring.beans.PropertyValue;
import com.tiv.mini.spring.beans.PropertyValues;
import com.tiv.mini.spring.beans.factory.config.BeanDefinition;
import com.tiv.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import com.tiv.mini.spring.beans.factory.config.ConstructorArgumentValue;
import com.tiv.mini.spring.beans.factory.config.ConstructorArgumentValues;
import com.tiv.mini.spring.beans.factory.exception.BeansException;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象bean工厂
 */
@NoArgsConstructor
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    /**
     * bean定义信息map
     */
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * bean定义名称列表
     */
    protected List<String> beanDefinitionNames = new ArrayList<>();

    /**
     * 毛胚bean实例map
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    abstract protected Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract protected Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 刷新bean实例
     */
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册bean实例
     *
     * @param beanName
     * @param obj
     */
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

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
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            // 尝试从毛胚中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                // 获取bean定义信息
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("beans not found");
                }
                // 创建bean实例
                singleton = createBean(beanDefinition);
                // 注册bean实例
                this.registerBean(beanName, singleton);
                // 初始化前置处理bean
                applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                // 调用初始化方法
                if (beanDefinition.getInitMethodName() != null && !beanDefinition.getInitMethodName().isEmpty()) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // 初始化后置处理bean
                applyBeanPostProcessorsAfterInitialization(singleton, beanName);
            }
        }
        return singleton;
    }

    /**
     * bean实例初始化
     *
     * @param beanDefinition
     * @param object
     */
    private void invokeInitMethod(BeanDefinition beanDefinition, Object object) {
        Class<?> clazz = beanDefinition.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(beanDefinition.getInitMethodName());
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建bean实例
     *
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        // 创建bean毛胚
        Object obj = createEarlySingleton(beanDefinition);
        // 缓存毛胚
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 处理bean属性
        populateBean(beanDefinition, clazz, obj);
        return obj;
    }

    /**
     * 创建毛胚实例
     *
     * @param beanDefinition
     * @return
     */
    private Object createEarlySingleton(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        Object object = null;
        Constructor<?> constructor = null;
        try {
            clazz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentValueCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentValueCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentValueCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.parseInt((String) constructorArgumentValue.getValue());
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                try {
                    constructor = clazz.getConstructor(paramTypes);
                    object = constructor.newInstance(paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                object = clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 处理bean属性
     *
     * @param beanDefinition
     * @param clazz
     * @param object
     */
    private void handleProperties(BeanDefinition beanDefinition, Class<?> clazz, Object object) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        // 处理属性参数
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                } else {
                    try {
                        // 获取引用的类
                        paramTypes[0] = Class.forName(pType);
                        // 获取引用的bean
                        paramValues[0] = getBean((String) pValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clazz.getMethod(methodName, paramTypes);
                    // 调用setter方法
                    method.invoke(object, paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void populateBean(BeanDefinition beanDefinition, Class clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    @Override
    public boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return getBeanDefinition(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return getBeanDefinition(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return getBeanDefinition(beanName).getClass();
    }

}
