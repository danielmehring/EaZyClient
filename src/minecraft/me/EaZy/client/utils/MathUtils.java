package me.EaZy.client.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class MathUtils {

    public static final int EaZy = 224;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }
}
