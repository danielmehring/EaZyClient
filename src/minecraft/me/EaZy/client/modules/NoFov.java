package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class NoFov extends Module {

public static NoFov mod;


    public static final int EaZy = 143;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoFov() {
        super("NoFov", 0, "NF", Category.RENDER);
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KeinFeldDerSichtbarkeit";
        } else {
            return super.getRenderName();
        }
    }

}
