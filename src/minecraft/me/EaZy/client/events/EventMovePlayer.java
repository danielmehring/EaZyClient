package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.Event;

public class EventMovePlayer implements Event {

    public static final int EaZy = 40;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public double x;
    public double y;
    public double z;

    public EventMovePlayer(final double x2, final double y2, final double z2) {
        x = x2;
        y = y2;
        z = z2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(final double x2) {
        x = x2;
    }

    public void setY(final double y2) {
        y = y2;
    }

    public void setZ(final double z2) {
        z = z2;
    }

    public double getMotionX() {
        return x;
    }

    public double getMotionY() {
        return y;
    }

    public double getMotionZ() {
        return z;
    }

    public void setMotionX(final double motionX) {
        x = motionX;
    }

    public void setMotionY(final double motionY) {
        y = motionY;
    }

    public void setMotionZ(final double motionZ) {
        z = motionZ;
    }
}
