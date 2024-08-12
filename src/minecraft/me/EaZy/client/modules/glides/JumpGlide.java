package me.EaZy.client.modules.glides;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class JumpGlide extends Module {

public static JumpGlide mod;


    public static final int EaZy = 120;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public JumpGlide() {
        super(new String(new byte[]{0b1001010, 0b1110101, 0b1101101, 0b1110000, 0b1000111, 0b1101100, 0b1101001,
            0b1100100, 0b1100101}), 0, "", Category.GLIDE, "Glide for testing.");

        Client.setmgr.rSetting(new Setting("Speed", this, 0.3f, 0.0f, 2.0f, false));
        Client.setmgr.rSetting(new Setting("FallDistance", this, 2.0f, 0.0f, 5.0f, false));
        Client.setmgr.rSetting(new Setting("MotionY", this, 0.5f, 0.0f, 2.0f, false));
        mod = this;

    }

    @Override
    public void onEnable() {
        Client.disable(Category.GLIDE, this.getClass());
        super.onEnable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "SprungGleitgel";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        if (!Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionX = 0;
            Minecraft.thePlayer.motionZ = 0;
        }
        super.onDisable();
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

        if (!Minecraft.thePlayer.onGround && !Minecraft.thePlayer.movementInput.sneak) {
            Minecraft.thePlayer.motionX = 0;
            Minecraft.thePlayer.motionZ = 0;
            Minecraft.thePlayer.jumpMovementFactor = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
            if (Minecraft.thePlayer.fallDistance > Client.setmgr.getSettingByName(this, "FallDistance").getValFloat()) {
                Minecraft.thePlayer.motionY = Client.setmgr.getSettingByName(this, "MotionY").getValFloat();
                Minecraft.thePlayer.fallDistance = 0f;
            }
        }

        super.onUpdate();
    }
}
