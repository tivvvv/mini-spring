package com.tiv.mini.spring.web;

import com.tiv.mini.spring.context.ClassPathXmlApplicationContext;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletContext;

@Getter
@Setter
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

}
