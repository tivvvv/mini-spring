package com.tiv.mini.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestServiceImpl implements TestService {

    private String name;

    private int level;

    private String property1;

    private String property2;

    private BaseService ref1;

    public TestServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void hello() {
        System.out.println(this.property1 + " " + this.property2);
        ref1.hello();
    }

}
