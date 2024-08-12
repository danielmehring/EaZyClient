package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.gui.GuiMainMenu;

public class NoIRC extends Module {

public static NoIRC mod;


    public static final int EaZy = 146;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoIRC() {
        super("NoIRC", 0, "", Category.OTHER, "Hides messages\nfrom EaZy-Chat!");
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
            return "KeinInternetzRelaisUnterhaltung";
        } else {
            return super.getRenderName();
        }
    }
}
