package com.tiv.minispring.bean.injection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArgumentValue {

    private String type;

    private String name;

    private Object value;

    public ArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

}
