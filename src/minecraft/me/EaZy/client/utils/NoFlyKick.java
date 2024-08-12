package me.EaZy.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;

public class NoFlyKick {

    public static final int EaZy = 230;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static double flyHeight;

    public static void updateFlyHeight() {
        double h = 1;
        final AxisAlignedBB box = Minecraft.thePlayer.getEntityBoundingBox().expand(0.0625, 0.0625, 0.0625);
        for (flyHeight = 0; flyHeight < Minecraft.thePlayer.posY; flyHeight += h) {
            final AxisAlignedBB nextBox = box.offset(0, -flyHeight, 0);

            if (Minecraft.theWorld.checkBlockCollision(nextBox)) {
                if (h < 0.0625) {
                    break;
                }

                flyHeight -= h;
                h /= 2;
            }
        }
    }

    public static void goToGround() {
        if (flyHeight > 300) {
            return;
        }

        final double minY = Minecraft.thePlayer.posY - flyHeight;

        if (minY <= 0) {
            return;
        }

        for (double y = Minecraft.thePlayer.posY; y > minY;) {
            y -= 8;
            if (y < minY) {
                y = minY;
            }

            final C04PacketPlayerPosition packet = new C04PacketPlayerPosition(Minecraft.thePlayer.posX, y,
                    Minecraft.thePlayer.posZ, true);
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }

        for (double y = minY; y < Minecraft.thePlayer.posY;) {
            y += 8;
            if (y > Minecraft.thePlayer.posY) {
                y = Minecraft.thePlayer.posY;
            }

            final C04PacketPlayerPosition packet = new C04PacketPlayerPosition(Minecraft.thePlayer.posX, y,
                    Minecraft.thePlayer.posZ, true);
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }
    }

}
