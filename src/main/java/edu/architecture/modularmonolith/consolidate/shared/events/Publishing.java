package edu.architecture.modularmonolith.consolidate.shared.events;

public interface Publishing<P extends Publishable> {

    void publish(P publishable);
}
