package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.gui.GuiMainMenu;

public class FastBreak extends Module {

public static FastBreak mod;


    public static final int EaZy = 109;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public FastBreak() {
        super("FastBreak", 0, "", Category.WORLD, "Breaks blocks faster.");
        Client.setmgr.rSetting(new Setting("Speed", this, 0.7f, 0, 1, false));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "SchnellesZerbrechen";
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
}
