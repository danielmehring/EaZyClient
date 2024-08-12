package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Timer;

public class FastLadder extends Module {

public static FastLadder mod;


    public static final int EaZy = 110;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static boolean canFastLadder = false;

    public FastLadder() {
        super("FastLadder", 0, "ladder", Category.MOVEMENT, "Let's you go up\nfaster on Ladders.\n§4AAC/Gomme!");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "SchnelleLeitern";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onUpdate() {
        if (!isToggled()) {
            if (togglecmd) {
                setToggled(true);
                onEnable();
            }
            return;
        }
        if (isToggled() && !togglecmd) {
            setToggled(false);
            onDisable();
            return;
        }

        if (canFastLadder && Minecraft.thePlayer.movementInput.jump) {
            Minecraft.thePlayer.motionY = 0.0;
            Minecraft.thePlayer.motionY += 0.55D;
        }

        if (YesCheat.mode == Mode.Gomme && Minecraft.thePlayer.isOnLadder()
                && Minecraft.gameSettings.keyBindForward.pressed) {

        }

        if (!Minecraft.thePlayer.isOnLadder()) {
            Timer.timerSpeed = 1;
        }

        super.onUpdate();
    }
}
