package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.Event;

public class EventSafewalk implements Event {

    public static final int EaZy = 44;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean shouldWalkSafely;

    public EventSafewalk(final boolean shouldWalkSafely) {
        this.shouldWalkSafely = shouldWalkSafely;
    }

    public boolean getShouldWalkSafely() {
        return shouldWalkSafely;
    }

    public void setShouldWalkSafely(final boolean shouldWalkSafely) {
        this.shouldWalkSafely = shouldWalkSafely;
    }
}
