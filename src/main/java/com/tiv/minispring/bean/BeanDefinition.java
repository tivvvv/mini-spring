package com.tiv.minispring.bean;

import com.tiv.minispring.bean.injection.ArgumentValues;
import com.tiv.minispring.bean.injection.PropertyValues;
import lombok.Getter;
import lombok.Setter;

/**
 * bean定义类
 */
@Getter
@Setter
public class BeanDefinition {

    private final String SCOPE_SINGLETON = "singleton";

    private final String SCOPE_PROTOTYPE = "prototype";

    private String id;

    private String className;

    private ArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;

    private String initMethodName;

    private boolean lazyInit = false;

    private String[] dependsOn;

    private volatile Object beanClass;

    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }
}
