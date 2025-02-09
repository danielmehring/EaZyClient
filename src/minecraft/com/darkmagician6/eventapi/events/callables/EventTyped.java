package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.Typed;

/**
 * Abstract example implementation of the Typed interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class EventTyped implements Event, Typed {

    public static final int EaZy = 4;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final byte type;

    /**
     * Sets the type of the event when it's called.
     *
     * @param eventType The type ID of the event.
     */
    protected EventTyped(final byte eventType) {
        type = eventType;
    }

    /**
     * @see com.darkmagician6.eventapi.events.Typed.getType
     */
    @Override
    public byte getType() {
        return type;
    }

}
