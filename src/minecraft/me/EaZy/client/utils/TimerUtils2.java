package me.EaZy.client.utils;

public class TimerUtils2 {

    public static final int EaZy = 251;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private long currentMS;
    private long lastMS;

    public final void updateMS() {
        currentMS = System.currentTimeMillis();
    }

    public final void updateLastMS() {
        lastMS = System.currentTimeMillis();
    }

    public final boolean hasTimePassedM(final long MS) {
        return currentMS >= lastMS + MS;
    }
}
