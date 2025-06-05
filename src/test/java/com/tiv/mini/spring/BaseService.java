package com.tiv.mini.spring;

import com.tiv.mini.spring.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseService {

    // 成员名必须和bean名一致才能注入
    @Autowired
    private BaseBaseService baseBaseService;

    public void hello() {
        System.out.println("baseService hello!");
        baseBaseService.hello();
    }

}
