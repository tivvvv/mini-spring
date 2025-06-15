package com.tiv.mini.spring.context.event;

import lombok.ToString;

@ToString
public class ContextRefreshEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object source) {
        super(source);
    }

}
