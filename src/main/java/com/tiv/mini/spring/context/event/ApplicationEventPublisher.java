package com.tiv.mini.spring.context.event;

import com.apple.eawt.ApplicationEvent;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
