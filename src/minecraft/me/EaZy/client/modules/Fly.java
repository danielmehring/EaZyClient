package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.NoFlyKick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {

public static Fly mod;


    public static final int EaZy = 114;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean wasStrafe = false;
    private boolean wasAirJump = false;

    private String suffix;

    public Fly() {
        super("Fly", 27, "", Category.MOVEMENT, "Vanilla fly.");
        Client.setmgr.rSetting(new Setting("Speed", this, 0.3f, 0.0f, 10.0f, false));
        Client.setmgr.rSetting(new Setting("KickSafe", this, true));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "Fliege [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "Fliege";
            }
        } else {
            if (Configs.suffix) {
                suffix = "Fly [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "Fly";
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
        if (Strafe.mod.isToggled()) {
            Client.toggle("Strafe");
            Module.msg("§4§lStrafe isn\'t working with Fly!");
        }
        if (AirJump.mod.isToggled()) {
            Client.toggle("AirJump");
            Module.msg("§4§lAirJump isn\'t working with Fly!");
        }

        if (!Minecraft.thePlayer.isInWater()) {
            updateMS();
            Minecraft.thePlayer.capabilities.isFlying = false;
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionY = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
            Minecraft.thePlayer.jumpMovementFactor = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
            if (Minecraft.gameSettings.keyBindSneak.pressed) {
                Minecraft.thePlayer.motionY -= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
            }
            if (Minecraft.gameSettings.keyBindJump.pressed) {
                Minecraft.thePlayer.motionY += Client.setmgr.getSettingByName(this, "Speed").getValFloat();
            }

            if (Client.setmgr.getSettingByName(this, "KickSafe").getValBoolean()) {
                NoFlyKick.updateFlyHeight();
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));

                if (NoFlyKick.flyHeight <= 290 && hasTimePassedM(500)
                        || NoFlyKick.flyHeight > 290 && hasTimePassedM(100)) {
                    NoFlyKick.goToGround();
                    updateLastMS();
                }
            }
        } else {
            Minecraft.thePlayer.capabilities.isFlying = true;
        }

        super.onUpdate();
    }

    @Override
    public void onEnable() {
        if (Strafe.mod.isToggled()) {
            Client.toggle("Strafe");
            Module.msg("§4§lStrafe isn\'t working with Fly!");
            wasStrafe = true;
        }
        if (AirJump.mod.isToggled()) {
            Client.toggle("AirJump");
            Module.msg("§4§lAirJump isn\'t working with Fly!");
            wasAirJump = true;
        }
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        if (wasStrafe) {
            Client.toggle("Strafe");
            wasStrafe = false;
        }
        if (wasAirJump) {
            Client.toggle("AirJump");
            wasAirJump = false;
        }
        Minecraft.thePlayer.capabilities.isFlying = false;
        Minecraft.thePlayer.motionX = 0.0;
        Minecraft.thePlayer.motionY = 0.0;
        Minecraft.thePlayer.motionZ = 0.0;
        super.onDisable();
    }
}
