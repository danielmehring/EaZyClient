package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class AirJump extends Module {

public static AirJump mod;


    public static final int EaZy = 92;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public AirJump() {
        super(new String(new byte[]{0b1000001, 0b1101001, 0b1110010, 0b1001010, 0b1110101, 0b1101101, 0b1110000}), 0, new String(new byte[]{0b1100001, 0b1101001, 0b1110010}),
                Category.PLAYER, new String(new byte[]{0b1001010, 0b1110101, 0b1101101, 0b1110000, 0b100000, 0b1101001, 0b1101110,
                    0b100000, 0b1101101, 0b1101001, 0b1100100, 0b1100001, 0b1101001, 0b1110010, 0b101110}));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "LuftHüpfer";
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
        if (Minecraft.thePlayer.capabilities.isFlying || Fly.mod.isToggled()) {
            return;
        }
        Minecraft.thePlayer.onGround = true;
    }
}
