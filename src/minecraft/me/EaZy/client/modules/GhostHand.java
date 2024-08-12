package me.EaZy.client.modules;

import java.util.ArrayList;
import java.util.List;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class GhostHand extends Module {

public static GhostHand mod;


    public static final int EaZy = 117;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static List<Integer> ids = new ArrayList<>();

    public GhostHand() {
        super("GhostHand", 22, "ghost", Category.WORLD, "Interact through blocks.");
        ids.add(54);
        ids.add(130);
        ids.add(146);
        ids.add(58);
        ids.add(145);
        ids.add(154);
        ids.add(26);
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "GeisterndeHand";
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
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
