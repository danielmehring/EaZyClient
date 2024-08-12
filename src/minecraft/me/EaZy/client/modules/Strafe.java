package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Strafe extends Module {

public static Strafe mod;


    public static final int EaZy = 186;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean wasAirJump = false;

    private String suffix;

    public Strafe() {
        super("Strafe", 0, "str", Category.MOVEMENT, "Better movement.");
        Client.setmgr.rSetting(new Setting("Speed", this, 0.295f, 0.0f, 10.0f, false));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "BessereBewegung [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "BessereBewegung";
            }
        } else {

            if (Configs.suffix) {
                suffix = "Strafe [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "Strafe";
            }

        }

        return suffix;
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

        if (AirJump.mod.isToggled()) {
            Client.toggle("AirJump");
            msg("§4§lAirJump isn\'t working with Strafe!");
        }
        if (!(Minecraft.thePlayer.onGround || Minecraft.thePlayer.isInWater()
                || Minecraft.thePlayer.capabilities.isFlying)) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        }
        Minecraft.thePlayer.jumpMovementFactor = Minecraft.thePlayer.isSneaking()
                ? Client.setmgr.getSettingByName(this, "Speed").getValFloat() * 3.0f
                : Client.setmgr.getSettingByName(this, "Speed").getValFloat();
        super.onUpdate();
    }

    @Override
    public void onEnable() {
        if (AirJump.mod.isToggled()) {
            Client.toggle("AirJump");
            msg("§4§lAirJump isn\'t working with Strafe!");
            wasAirJump = true;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (wasAirJump) {
            Client.toggle("AirJump");
            wasAirJump = false;
        }
        super.onDisable();
    }
}
