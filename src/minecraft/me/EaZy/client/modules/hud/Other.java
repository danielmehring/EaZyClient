package me.EaZy.client.modules.hud;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;

import de.Hero.settings.Setting;

public class Other extends Module {

public static Other mod;


    public static final int EaZy = 125;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Other() {
        super(new String(new byte[]{0b1001111, 0b1110100, 0b1101000, 0b1100101, 0b1110010}), 0, "",
                Category.HUD, false);
        Client.setmgr.rSetting(new Setting("TabGui", this, true));
        Client.setmgr.rSetting(new Setting("Coords", this, false));
        Client.setmgr.rSetting(new Setting("IGN", this, false));
        Client.setmgr.rSetting(new Setting("FPS", this, false));
        Client.setmgr.rSetting(new Setting("ServerIP", this, false));
        Client.setmgr.rSetting(new Setting("Ping", this, false));
        Client.setmgr.rSetting(new Setting("Time", this, false));
        Client.setmgr.rSetting(new Setting("ArrayList", this, true));
        Client.setmgr.rSetting(new Setting("Potions", this, false));
        Client.setmgr.rSetting(new Setting("Armor", this, false));
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
}
