package me.EaZy.client.events;

public class EventPreMotionUpdates extends PlayerUpdateEvent {

    public static final int EaZy = 42;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final float yaw;
    private final float pitch;

    public double x;
    public double y;
    public double z;
    private final double oldX;
    private final double oldY;
    private final double oldZ;
    private final float oldYaw;
    private final float oldPitch;
    private boolean sprinting;
    private final boolean wasSprinting;
    private final boolean wasSneaking;

    public EventPreMotionUpdates(final double x2, final double y2, final double z2, final double oldX,
            final double oldY, final double oldZ, final float yaw, final float pitch, final float oldYaw,
            final float oldPitch, final boolean sprinting, final boolean wasSprinting, final boolean sneaking,
            final boolean wasSneaking, final boolean onGround) {
        x = x2;
        y = y2;
        z = z2;
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldZ = oldZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.oldYaw = oldYaw;
        this.oldPitch = oldPitch;
        this.sprinting = sprinting;
        this.wasSprinting = wasSprinting;
        this.wasSneaking = wasSneaking;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x2) {
        x = x2;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y2) {
        y = y2;
    }

    public double getZ() {
        return z;
    }

    public void setZ(final double z2) {
        z = z2;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public double getOldZ() {
        return oldZ;
    }

    public double getOldYaw() {
        return oldYaw;
    }

    public double getOldPitch() {
        return oldPitch;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void setSprinting(final boolean sprinting) {
        this.sprinting = sprinting;
    }

    public boolean wasSprinting() {
        return wasSprinting;
    }

    public boolean wasSneaking() {
        return wasSneaking;
    }

    public boolean isMoving() {
        final double x2 = x - oldX;
        final double y2 = y - oldY;
        final double z2 = z - oldZ;
        return x2 * x2 + y2 * y2 + z2 * z2 > 9.0E-4;
    }

    public boolean isRotating() {
        final double yaw = this.yaw - oldYaw;
        final double pitch = this.pitch - oldPitch;
        return !(yaw == 0.0 && pitch == 0.0);
    }
}
