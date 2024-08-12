package me.EaZy.client.modules.SpeedModes;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class MiniHop extends Module {

public static MiniHop mod;


    public static final int EaZy = 174;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public MiniHop() {
        super(new String(new byte[]{0b1001101, 0b1101001, 0b1101110, 0b1101001, 0b1001000, 0b1101111, 0b1110000}), 0, "", Category.SPEED);
        mod = this;
    }

    private int delay;

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KleinerHüpfer";
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

        delay++;
        if (!Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionX *= 1.001F;
            Minecraft.thePlayer.motionZ *= 1.001F;
        }

        if (delay > 5 && Minecraft.thePlayer.onGround && !(Minecraft.thePlayer.motionX == 0)
                && !(Minecraft.thePlayer.motionZ == 0)) {
            Minecraft.thePlayer.motionY = 0.2202F;
            Minecraft.thePlayer.jump();
            Minecraft.thePlayer.motionY -= 0.25;
            delay = 0;

        }

    }

}
