package com.tiv.mini.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseService {

    private BaseBaseService bbs;

    public void hello() {
        System.out.println("baseService hello!");
        bbs.hello();
    }

}
