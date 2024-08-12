package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Spider extends Module {

public static Spider mod;


    public static final int EaZy = 183;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Spider() {
        super("Spider", 0, "sp", Category.MOVEMENT, "Climb up walls.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Kreuzschlüssel";
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

        if (Minecraft.thePlayer.isCollidedHorizontally) {
            if (Minecraft.thePlayer.isSneaking()) {
                Minecraft.thePlayer.motionY = 0;
            } else {
                Minecraft.thePlayer.motionY = 0.2;
            }
        }
        super.onUpdate();
    }
}
