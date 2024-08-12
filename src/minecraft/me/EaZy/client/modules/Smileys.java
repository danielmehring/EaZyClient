package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;

public class Smileys extends Module {

public static Smileys mod;


    public static final int EaZy = 159;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static boolean active;

    public Smileys() {
        super("Smileys", 0, "fc", Category.OTHER, "Better chat.");
        mod = this;
    }

    @Override
    public void onDisable() {
        active = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        active = true;
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
