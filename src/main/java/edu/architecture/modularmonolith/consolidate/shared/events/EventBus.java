package edu.architecture.modularmonolith.consolidate.shared.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventBus implements Publishing {
    private final ApplicationEventPublisher publisher;

    public EventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(Publishable event) {
        publisher.publishEvent(event);
    }
}
