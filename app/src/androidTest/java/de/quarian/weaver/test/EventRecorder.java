package de.quarian.weaver.test;

import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

public class EventRecorder {

    private final List<Object> events = new LinkedList<>();

    @Subscribe
    public void recordEvent(final Object event) {
        events.add(event);
    }

    public List<Object> pullEvents() {
        final List<Object> events = new LinkedList<>(this.events);
        reset();
        return events;
    }

    public void reset() {
        events.clear();
    }
}
