package com.tiv.minispring.bean.injection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 构造器参数值
 */
@Getter
@Setter
@AllArgsConstructor
public class ConstructorArgumentValue {

    private String name;

    private String type;

    private Object value;

    public ConstructorArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

}
