package edu.architecture.modularmonolith.consolidate.shared.events;

public interface Subscribing<P extends Publishable>{
    void onEvent(P publishable);
}
