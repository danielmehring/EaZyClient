package me.EaZy.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;

public class MoveUtils {

    public static final int EaZy = 228;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static void toFwd(final double amount) {
        double yaw = Minecraft.thePlayer.rotationYawHead;
        yaw = Math.toRadians(yaw);
        final double dX = -Math.sin(yaw) * amount;
        final double dZ = Math.cos(yaw) * amount;
        Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + dX, Minecraft.thePlayer.posY,
                Minecraft.thePlayer.posZ + dZ);
    }

    

}
