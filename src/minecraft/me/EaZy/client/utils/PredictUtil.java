package me.EaZy.client.utils;

import net.minecraft.entity.Entity;

public class PredictUtil {

    public static final int EaZy = 237;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static Location predictEntityLocation(final Entity e, final double milliseconds) {
        if (e != null) {
            if (e.posX == e.lastTickPosX && e.posY == e.lastTickPosY && e.posZ == e.lastTickPosZ) {
                return new Location(e.posX, e.posY, e.posZ);
            }
            double ticks = milliseconds / 1000.0;
            return PredictUtil.interp(new Location(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ),
                    new Location(e.posX + e.motionX, e.posY + e.motionY, e.posZ + e.motionZ), ticks *= 20.0);
        }
        return null;
    }

    private static Location interp(final Location from, final Location to, final double pct) {
        final double x = from.x + (to.x - from.x) * pct;
        final double y = from.y + (to.y - from.y);
        final double z = from.z + (to.z - from.z) * pct;
        return new Location(x, y, z);
    }
}
