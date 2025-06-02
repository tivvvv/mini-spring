package com.tiv.mini.spring.bean.injection;

import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 构造器参数值集合
 */
@NoArgsConstructor
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.genericConstructorArgumentValues.get(index);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue constructorArgumentValue : this.genericConstructorArgumentValues) {
            if (constructorArgumentValue.getName() == null || (requiredName != null && requiredName.equals(constructorArgumentValue.getName()))) {
                return constructorArgumentValue;
            }
        }
        return null;
    }

    public int getArgumentValueCount() {
        return this.genericConstructorArgumentValues.size();
    }

    public void addGenericArgumentValue(String type, Object value) {
        this.genericConstructorArgumentValues.add(new ConstructorArgumentValue(type, value));
    }

    public void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ConstructorArgumentValue> iterator = this.genericConstructorArgumentValues.iterator(); iterator.hasNext(); ) {
                ConstructorArgumentValue currentValue = iterator.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    iterator.remove();
                }
            }
        }
        this.genericConstructorArgumentValues.add(newValue);
    }

    public boolean isEmpty() {
        return this.genericConstructorArgumentValues.isEmpty();
    }

}
