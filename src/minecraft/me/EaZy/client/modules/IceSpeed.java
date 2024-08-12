package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.init.Blocks;

public class IceSpeed extends Module {

public static IceSpeed mod;


    public static final int EaZy = 126;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public IceSpeed() {
        super("IceSpeed", 0, "ice", Category.MOVEMENT, "Go faster on Ice.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "EisSchnelligkeit";
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
        Blocks.ice.slipperiness = 0.405f;
        Blocks.packed_ice.slipperiness = 0.405F;
    }

    @Override
    public void onDisable() {
        Blocks.ice.slipperiness = 0.89F;
        Blocks.packed_ice.slipperiness = 0.89F;
    }
}
