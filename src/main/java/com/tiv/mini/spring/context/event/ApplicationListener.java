package com.tiv.mini.spring.context.event;

import java.util.EventListener;

public class ApplicationListener implements EventListener {

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件" + event.toString());
    }

}
