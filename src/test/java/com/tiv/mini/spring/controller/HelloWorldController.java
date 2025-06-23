package com.tiv.mini.spring.controller;

import com.tiv.mini.spring.web.RequestMapping;

public class HelloWorldController {

    @RequestMapping("/test")
    public String doTest() {
        return "Hello World Test!";
    }

}
