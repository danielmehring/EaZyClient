package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;

public class NoScoreboard extends Module {

public static NoScoreboard mod;


    public static final int EaZy = 149;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoScoreboard() {
        super("NoScoreboard", 0, "score", Category.OTHER, "Disables the\nscoreboard.");
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
