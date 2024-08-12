package me.EaZy.client.utils;

public class TimeHelper {

    public static final int EaZy = 248;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private long prevMS = 0;

    public boolean delay(final float milliSec) {
        return getTime() - prevMS >= milliSec;
    }

    public void reset() {
        prevMS = getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public long getDifference() {
        return getTime() - prevMS;
    }

    public boolean hasReached(final long milliseconds) {
        return getDifference() >= milliseconds;
    }
}
