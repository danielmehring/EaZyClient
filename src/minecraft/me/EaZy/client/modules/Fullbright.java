package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Fullbright extends Module {

public static Fullbright mod;


    public static final int EaZy = 116;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Fullbright() {
        super("Fullbright", 0, "bright", Category.RENDER, "Makes all brigther.");
        mod = this;
    }

    @Override
    public void onEnable() {
        Minecraft.gameSettings.gammaSetting = 10000.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Minecraft.gameSettings.gammaSetting = 1.0f;
        super.onDisable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "VolleHällichkeit";
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
        Minecraft.gameSettings.gammaSetting = 10000.0f;
        super.onUpdate();
    }
}
