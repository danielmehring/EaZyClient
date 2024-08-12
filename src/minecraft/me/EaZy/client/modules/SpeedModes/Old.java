package me.EaZy.client.modules.SpeedModes;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Old extends Module {

public static Old mod;


    public static final int EaZy = 177;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Old() {
        super("Old", 0, "", Category.SPEED);
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Old";
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
        if (Minecraft.thePlayer.capabilities.isFlying) {
            return;
        }

        if (Minecraft.thePlayer.onGround) {
            if (Minecraft.thePlayer.ticksExisted % 4 == 0) {
                Minecraft.thePlayer.motionX *= 3.51f;
                Minecraft.thePlayer.motionZ *= 3.51f;

            } else {
                Minecraft.thePlayer.motionX /= 1.3f;
                Minecraft.thePlayer.motionZ /= 1.3f;

            }
        } else {
            Minecraft.thePlayer.motionX *= 1.1f;
            Minecraft.thePlayer.motionZ *= 1.1f;
            Minecraft.thePlayer.motionX /= 1.1f;
            Minecraft.thePlayer.motionZ /= 1.1f;
        }
        super.onUpdate();
    }

}
