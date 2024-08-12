package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class Criticals extends Module {

public static Criticals mod;


    public static final int EaZy = 104;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Criticals() {
        super("Criticals", 0, "crit", Category.COMBAT);
        final ArrayList<String> modes = new ArrayList<>();
        modes.add("Jump");
        modes.add("MiniJump");
        modes.add("MicroJump");
        modes.add("Packets");
        Client.setmgr.rSetting(new Setting("Mode", this, "MicroJump", modes));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KirtischeTreffer";
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
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY && Minecraft.gameSettings.keyBindAttack.pressed
                || KillAura.target != null && !(KillAura.delay <= Client.setmgr
                        .getSettingByName(KillAura.mod, "Delay").getValFloat())) {
            doCritical();
        }
    }

    private void doCritical() {

        if (!Minecraft.thePlayer.isInWater() && !Minecraft.thePlayer.isInsideOfMaterial(Material.lava)
                && Minecraft.thePlayer.onGround) {
            final String mode = Client.setmgr.getSettingByName(this, "Mode").getValString();
            switch (mode) {
                case "Jump":
                    Minecraft.thePlayer.jump();
                    break;
                case "MiniJump":
                    Minecraft.thePlayer.motionY = 0.15;
                    Minecraft.thePlayer.onGround = false;
                    break;
                case "MicroJump":
                    Minecraft.thePlayer.motionY = 0.08;
                    Minecraft.thePlayer.onGround = false;
                    break;
                case "Packets":
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                            Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.05, Minecraft.thePlayer.posZ, false));
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                            Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                    Minecraft.getNetHandler()
                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX,
                                    Minecraft.thePlayer.posY + 0.012511, Minecraft.thePlayer.posZ, false));
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                            Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                    break;
                default:
                    break;
            }
        }
    }
}
