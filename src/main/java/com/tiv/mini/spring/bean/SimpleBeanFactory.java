package com.tiv.mini.spring.bean;

import com.tiv.mini.spring.bean.exception.BeansException;
import com.tiv.mini.spring.bean.injection.ConstructorArgumentValue;
import com.tiv.mini.spring.bean.injection.ConstructorArgumentValues;
import com.tiv.mini.spring.bean.injection.PropertyValue;
import com.tiv.mini.spring.bean.injection.PropertyValues;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单bean工厂
 */
@NoArgsConstructor
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();

    private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

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
                // 获取bean定义
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("bean not found");
                }
                // 创建bean实例
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);
            }
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return containsSingleton(beanName);
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

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    protected Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        Object obj = createEarlySingleton(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handleProperties(beanDefinition, clazz, obj);
        return obj;
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

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

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clazz, Object object) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
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

}
