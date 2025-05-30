package com.tiv.minispring.bean.injection;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * setter属性值
 */
@Getter
@AllArgsConstructor
public class PropertyValue {

    private String name;

    private String type;

    private Object value;

}
