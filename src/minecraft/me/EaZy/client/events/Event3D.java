package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.Event;

public class Event3D implements Event {

    public static final int EaZy = 38;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static float entityT;

    public Event3D(final float entityT) {
        Event3D.entityT = entityT;
    }

    public static float getPartialTicks() {
        return entityT;
    }
}
