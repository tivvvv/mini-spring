package com.tiv.minispring;

import com.tiv.minispring.bean.exception.BeansException;
import com.tiv.minispring.context.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        TestService testService = (TestService) ctx.getBean("testService");
        testService.hello();
    }

}
