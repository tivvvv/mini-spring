package com.tiv.minispring.context.event;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public ApplicationEvent(Object source) {
        super(source);
    }

}
