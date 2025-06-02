package com.tiv.mini.spring.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * setter属性值
 */
@Getter
@AllArgsConstructor
public class PropertyValue {

    private final String name;

    private final String type;

    private final Object value;

    private final boolean isRef;

}
