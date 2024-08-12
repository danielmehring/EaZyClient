package me.EaZy.client.utils;

public class TimeHelper2 {

    public static final int EaZy = 249;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private long lastMS = 0;

    public boolean isDelayComplete(final float f) {
        return System.currentTimeMillis() - lastMS >= f;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }
}
