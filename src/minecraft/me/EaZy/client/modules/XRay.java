package me.EaZy.client.modules;

import java.util.ArrayList;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class XRay extends Module {

public static XRay mod;


    public static final int EaZy = 196;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public XRay() {
        super("XRay", 25, "xr", Category.RENDER, "See through walls.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "RöntgenStrahler";
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
        super.onUpdate();
    }

    @Override
    public void onEnable() {
        Minecraft.gameSettings.gammaSetting = 103370f;
        mc.renderGlobal.loadRenderers();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Minecraft.gameSettings.gammaSetting = 1.0f;
        mc.renderGlobal.loadRenderers();
        super.onDisable();
    }
}
