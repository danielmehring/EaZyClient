package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class BackPort extends Module {

public static BackPort mod;


    public static final int EaZy = 96;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private String suffix;

    public BackPort() {
        super(new String(
                new byte[]{0b1000010, 0b1100001, 0b1100011, 0b1101011, 0b1010000, 0b1101111, 0b1110010, 0b1110100}),
                0, new String(new byte[]{0b1100010, 0b1100001, 0b1100011, 0b1101011}), Category.PLAYER,
                new String(new byte[]{0b1010000, 0b1110010, 0b1101111, 0b1110100, 0b1100101, 0b1100011, 0b1110100,
                    0b1110011, 0b100000, 0b1111001, 0b1101111, 0b1110101, 0b100000, 0b1100110, 0b1110010, 0b1101111,
                    0b1101101})
                + "\n"
                + new String(new byte[]{0b1100110, 0b1100001, 0b1101100, 0b1101100, 0b1101001, 0b1101110,
                    0b1100111, 0b100000, 0b1100100, 0b1101111, 0b1110111, 0b1101110, 0b101110})
                + "\n"
                + new String(new byte[]{0b11111111111111111111111110100111, 0b110100, 0b1001111, 0b1101110,
                    0b1101100, 0b1111001, 0b100000, 0b1110111, 0b1101111, 0b1110010, 0b1101011, 0b1110011,
                    0b100000, 0b1101111, 0b1101110, 0b100000, 0b1001110, 0b1000011, 0b1010000, 0b100001}));
        Client.setmgr.rSetting(new Setting("maxFallDistance", this, 3, 0.0f, 20.0f, false));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = new String(new byte[]{0b1000010, 0b1100001, 0b1100011, 0b1101011, 0b1010000, 0b1101111,
                    0b1110010, 0b1110100, 0b100000, 0b1011011})
                        + Client.setmgr.getSettingByName(this, "maxFallDistance").getValFloat()
                        + new String(new byte[]{0b1101101, 0b1011101});
            } else {
                suffix = "zurückTeleportation";
            }

            return suffix;
        } else {

            if (Configs.suffix) {
                suffix = new String(new byte[]{0b1000010, 0b1100001, 0b1100011, 0b1101011, 0b1010000, 0b1101111,
                    0b1110010, 0b1110100, 0b100000, 0b1011011})
                        + Client.setmgr.getSettingByName(this, "maxFallDistance").getValFloat()
                        + new String(new byte[]{0b1101101, 0b1011101});
            } else {
                suffix = "BackPort";
            }

            return suffix;

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

        if (Minecraft.thePlayer.fallDistance >= Client.setmgr.getSettingByName(this, "maxFallDistance").getValFloat()) {
            Minecraft.thePlayer.motionY = 1;
            Minecraft.thePlayer.fallDistance = 0;
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
