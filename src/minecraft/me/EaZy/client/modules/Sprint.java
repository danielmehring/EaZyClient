package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Sprint extends Module {

public static Sprint mod;


    public static final int EaZy = 184;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Sprint() {
        super("Sprint", 0, "", Category.PLAYER, "Automatically sprint.");
        Client.setmgr.rSetting(new Setting("All Dirs", this, false));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Endspurt";
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

        if (Minecraft.gameSettings.keyBindForward.pressed) {
            Minecraft.thePlayer.setSprinting(true);
        }

        if (YesCheat.mode == Mode.Gomme && Scaffold.mod.isToggled()) {
            return;
        }

        if (!Minecraft.thePlayer.isSneaking() && !Minecraft.thePlayer.isCollidedHorizontally
                && !Minecraft.thePlayer.isBlocking() && Minecraft.thePlayer.moveForward > 0.0F
                && !Minecraft.thePlayer.isEating()) {
            Minecraft.thePlayer.setSprinting(true);
        }

        if (!(Minecraft.thePlayer.moveForward > 0) && !(Minecraft.thePlayer.motionX == 0)
                && !(Minecraft.thePlayer.motionZ == 0) && !Minecraft.thePlayer.isSneaking()
                && !Minecraft.thePlayer.isCollidedHorizontally && !Minecraft.thePlayer.isBlocking()
                && Minecraft.thePlayer.onGround && Client.setmgr.getSettingByName(this, "All Dirs").getValBoolean()) {
            Minecraft.thePlayer.setSprinting(true);
            Minecraft.thePlayer.motionX *= 1.2f;
            Minecraft.thePlayer.motionZ *= 1.2f;

        }

        super.onUpdate();
    }
}
