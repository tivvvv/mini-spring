package com.tiv.mini.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseBaseService {

    private TestServiceImpl ts;

    public void hello() {
        System.out.println("basebaseService hello!");
    }

}
