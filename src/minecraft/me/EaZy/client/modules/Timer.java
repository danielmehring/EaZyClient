package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.gui.GuiMainMenu;

public class Timer extends Module {

public static Timer mod;


    public static final int EaZy = 188;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Timer() {
        super("Timer", 0, "", Category.OTHER, "Makes the game faster.");
        Client.setmgr.rSetting(new Setting("Speed", this, 1.0f, 0.1f, 10.0f, false));
        mod = this;
    }

    private String suffix;

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "Zeiter [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "Zeiter";
            }
        } else {

            if (Configs.suffix) {
                suffix = "Timer [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "Timer";
            }

        }

        return suffix;
    }

    @Override
    public void onDisable() {
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.onDisable();
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

        net.minecraft.util.Timer.timerSpeed = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
        super.onUpdate();
    }
}
