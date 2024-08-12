package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class NoHurtcam extends Module {

public static NoHurtcam mod;


    public static final int EaZy = 145;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoHurtcam() {
        super("NoHurtcam", 0, "", Category.RENDER, "Removes hurtcam effect.");
        mod = this;
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
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KeineAuaKamera";
        } else {
            return super.getRenderName();
        }
    }
}
