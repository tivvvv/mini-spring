package com.tiv.minispring.context.event;

import com.apple.eawt.ApplicationEvent;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
