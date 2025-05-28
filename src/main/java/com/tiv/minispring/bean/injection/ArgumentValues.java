package com.tiv.minispring.bean.injection;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class ArgumentValues {

    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>();

    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    private void addArgumentValue(int key, ArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue argumentValue : this.genericArgumentValues) {
            if (argumentValue.getName() == null || (requiredName != null && requiredName.equals(argumentValue.getName()))) {
                return argumentValue;
            }
        }
        return null;
    }

    public int getArgumentValueCount() {
        return this.genericArgumentValues.size();
    }

    public void addGenericArgumentValue(String type, Object value) {
        this.genericArgumentValues.add(new ArgumentValue(type, value));
    }

    private void addGenericArgumentValue(ArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ArgumentValue> iterator = this.genericArgumentValues.iterator(); iterator.hasNext(); ) {
                ArgumentValue currentValue = iterator.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    iterator.remove();
                }
            }
        }
        this.genericArgumentValues.add(newValue);
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }

}
