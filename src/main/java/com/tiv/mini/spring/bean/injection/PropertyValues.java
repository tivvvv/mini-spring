package com.tiv.mini.spring.bean.injection;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * setter属性值集合
 */
@NoArgsConstructor
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public void removePropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.remove(propertyValue);
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    public Object get(String propertyName) {
        PropertyValue propertyValue = getPropertyValue(propertyName);
        return propertyValue == null ? null : propertyValue.getValue();
    }

    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

}
