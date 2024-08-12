package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;

public class Reach extends Module {

public static Reach mod;


    public static final int EaZy = 155;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Reach() {
        super("Reach", 0, "", Category.COMBAT, "Hit further entities!");
        Client.setmgr.rSetting(new Setting("Range", this, 3.0f, 3.0f, 6.0f, false));
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
