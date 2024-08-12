package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Timer;

public class MySpeed extends Module {

public static MySpeed mod;


    public static final int EaZy = 175;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public MySpeed() {
        super(new String(new byte[]{0b1001101, 0b1111001, 0b1010011, 0b1110000, 0b1100101, 0b1100101, 0b1100100}), 0, "", Category.SPEED);
        Client.setmgr.rSetting(new Setting("Timer", this, 1.0f, 0.1f, 3.0f, false));
        Client.setmgr.rSetting(new Setting("YPortUp", this, 0.0f, 0.0f, 1.0f, false));
        Client.setmgr.rSetting(new Setting("YPortDown", this, 0.0f, 0.0f, 1.0f, false));
        Client.setmgr.rSetting(new Setting("Horizontal", this, 1.0f, 0.0f, 10.0f, false));
        Client.setmgr.rSetting(new Setting("Vertical", this, 1.0f, 0.0f, 10.0f, false));
        Client.setmgr.rSetting(new Setting("StrafeSpeed", this, 0.1f, 0.0f, 1.0f, false));
        Client.setmgr.rSetting(new Setting("FakeOnGround", this, false));
        Client.setmgr.rSetting(new Setting("YPort", this, false));
        Client.setmgr.rSetting(new Setting("JumpOnYPort", this, false));
        Client.setmgr.rSetting(new Setting("Strafe", this, false));
        Client.setmgr.rSetting(new Setting("AutoJump", this, false));
        mod = this;
    }

    private double GraundROFL;

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = Client.setmgr.getSettingByName(this, "Timer").getValFloat();
        super.onEnable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "MeineGeschwindigkeit";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    public EventTarget onPostMotionUpdates(final EventPostMotionUpdates event) {
        if (Client.setmgr.getSettingByName(this, "FakeOnGround").getValBoolean()
                & !Minecraft.thePlayer.movementInput.jump) {
            if (Minecraft.thePlayer.onGround) {
                GraundROFL = Minecraft.thePlayer.posY;
            } else if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                Minecraft.thePlayer.posY = GraundROFL;
            }
        }
        return null;
    }

    public EventTarget onPlayerMove(final EventMovePlayer e) {
        if (!Minecraft.thePlayer.isInWater() && !Minecraft.thePlayer.capabilities.isFlying) {
            e.x *= Client.setmgr.getSettingByName(this, "Horizontal").getValFloat();
            e.z *= Client.setmgr.getSettingByName(this, "Horizontal").getValFloat();
            if (!Minecraft.thePlayer.movementInput.jump && e.y >= 0) {
                e.y *= Client.setmgr.getSettingByName(this, "Vertical").getValFloat();
            }
            Timer.timerSpeed = Client.setmgr.getSettingByName(this, "Timer").getValFloat();
        } else {
            Timer.timerSpeed = 1.0f;
        }
        return null;
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

        if (Client.setmgr.getSettingByName(this, "YPort").getValBoolean()) {
            if (Minecraft.thePlayer.onGround & Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                if (Client.setmgr.getSettingByName(this, "JumpOnYPort").getValBoolean()) {
                    Minecraft.thePlayer.jump();
                }
                Minecraft.thePlayer.motionY = Client.setmgr.getSettingByName(this, "YPortUp").getValFloat();
            } else if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                Minecraft.thePlayer.motionY = -Client.setmgr.getSettingByName(this, "YPortDown").getValFloat();
            }
        } else {

            if (Client.setmgr.getSettingByName(this, "Strafe").getValBoolean()) {
                if (!(Minecraft.thePlayer.onGround || Minecraft.thePlayer.isInWater()
                        || Minecraft.thePlayer.capabilities.isFlying)) {
                    Minecraft.thePlayer.motionX = 0.0;
                    Minecraft.thePlayer.motionZ = 0.0;
                }
                Minecraft.thePlayer.jumpMovementFactor = Minecraft.thePlayer.isSneaking()
                        ? Client.setmgr.getSettingByName(this, "StrafeSpeed").getValFloat() * 3.0f
                        : Client.setmgr.getSettingByName(this, "StrafeSpeed").getValFloat();
            }

            if (!PlayerUtil.isPlayerMoving()) {
                Timer.timerSpeed = 1.0f;
            } else {
                if (Minecraft.thePlayer.onGround && Client.setmgr.getSettingByName(this, "AutoJump").getValBoolean()) {
                    Minecraft.thePlayer.jump();
                }
            }

        }
    }

}
