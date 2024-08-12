package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public abstract class PlayerUpdateEvent extends EventCancellable {

    public static final int EaZy = 49;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean sneaking;

    public float getYaw() {
        return yaw;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(final boolean sneaking) {
        this.sneaking = sneaking;
    }
}
