package me.EaZy.client.utils;

import net.minecraft.client.Minecraft;

public class MovementUtil {

    public static final int EaZy = 227;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    

    public static boolean areWalkingKeysDown() {
        return Minecraft.gameSettings.keyBindLeft.pressed || Minecraft.gameSettings.keyBindRight.pressed
                || Minecraft.gameSettings.keyBindForward.pressed || Minecraft.gameSettings.keyBindBack.pressed;
    }

}
