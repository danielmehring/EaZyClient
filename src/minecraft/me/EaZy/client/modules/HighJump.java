package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class HighJump extends Module {

public static HighJump mod;


    public static final int EaZy = 123;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public HighJump() {
        super("HighJump", 35, "hj", Category.PLAYER, "Jump high!");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "HocherHüpfer";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onEnable() {
        Minecraft.thePlayer.motionY = 1;
        setToggled(false);
        togglecmd = false;
        onDisable();
        super.onEnable();
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
        super.onUpdate();
    }
}
