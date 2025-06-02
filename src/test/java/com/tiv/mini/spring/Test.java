package com.tiv.mini.spring;

import com.tiv.mini.spring.bean.exception.BeansException;
import com.tiv.mini.spring.context.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        TestService testService = (TestService) ctx.getBean("testService");
        testService.hello();
    }

}
