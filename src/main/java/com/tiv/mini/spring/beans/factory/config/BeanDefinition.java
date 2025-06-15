package com.tiv.mini.spring.beans.factory.config;

import com.tiv.mini.spring.beans.PropertyValues;
import com.tiv.mini.spring.beans.factory.support.BeanConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * bean定义信息类
 */
@Getter
@Setter
public class BeanDefinition {

    private String id;

    private String className;

    private ConstructorArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;

    private String initMethodName;

    private boolean lazyInit = true;

    private String[] dependsOn;

    private volatile Object beanClass;

    private String scope = BeanConstants.SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return BeanConstants.SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return BeanConstants.SCOPE_PROTOTYPE.equals(scope);
    }
}
