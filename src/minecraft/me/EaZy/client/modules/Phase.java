package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Phase extends Module {

public static Phase mod;


    public static final int EaZy = 153;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean collided = false;

    private String suffix;

    public Phase() {
        super("Phase", 73, "", Category.MOVEMENT, "Go through walls.");
        final ArrayList<String> modes = new ArrayList<>();
        modes.add("NEW");
        modes.add("SKIP");
        Client.setmgr.rSetting(new Setting("Mode", this, "SKIP", modes));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "Stadium [" + Client.setmgr.getSettingByName(this, "Mode").getValString() + "]";
            } else {
                suffix = "Stadium";
            }
        } else {

            if (Configs.suffix) {
                suffix = "Phase [" + Client.setmgr.getSettingByName(this, "Mode").getValString() + "]";
            } else {
                suffix = "Phase";
            }

        }
        return suffix;
    }

    @Override
    public void onDisable() {
        collided = false;
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
        if (!Minecraft.thePlayer.isCollidedHorizontally && collided) {
            collided = false;
        }
        float dir = Minecraft.thePlayer.rotationYaw;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            dir += 180.0f;
        }
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            dir -= 90.0f * (Minecraft.thePlayer.moveForward < 0.0f ? -0.5f
                    : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f);
        }
        if (Minecraft.thePlayer.moveStrafing < 0.0f) {
            dir += 90.0f * (Minecraft.thePlayer.moveForward < 0.0f ? -0.5f
                    : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f);
        }
        final float xD = (float) Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0);
        final float zD = (float) Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0);
        boolean moving = Minecraft.thePlayer.movementInput.moveForward != 0.0f;
        final boolean strafing = Minecraft.thePlayer.movementInput.moveStrafe != 0.0f;
        moving = moving || strafing;
        if (Minecraft.thePlayer.isCollidedHorizontally && !collided && Minecraft.thePlayer.onGround
                && !BlockUtils.isInsideBlock(Minecraft.thePlayer) && moving) {
            Minecraft.getNetHandler().addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
            if (Client.setmgr.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("SKIP")) {
                final float[] offset = new float[]{xD * 0.25f, 1.0f, zD * 0.25f};
                final double[] movements = new double[]{0.025000000372529, 0.02857142899717604, 0.0333333338300387,
                    0.04000000059604645};
                int i = 0;
                while (i < movements.length) {
                    Minecraft.getNetHandler()
                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX,
                                    Minecraft.thePlayer.posY - movements[i] + 0.02500000037252903,
                                    Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
                    Minecraft.getNetHandler()
                            .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                                    Minecraft.thePlayer.posX + offset[0] * i, Minecraft.thePlayer.posY,
                                    Minecraft.thePlayer.posZ + offset[2] * i, Minecraft.thePlayer.onGround));
                    ++i;
                }
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + offset[0] * 0.05f, Minecraft.thePlayer.posY,
                        Minecraft.thePlayer.posZ + offset[2] * 0.05f);
                if (Minecraft.thePlayer.isCollidedHorizontally) {
                    collided = true;
                }
            } else if (Client.setmgr.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("NEW")) {
                final float[] offset = new float[]{xD * 0.75f, 1.0f, zD * 0.75f};
                Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                        Minecraft.thePlayer.posX + offset[0] * 0.65f, Minecraft.thePlayer.posY + 9.999999747378752E-5,
                        Minecraft.thePlayer.posZ + offset[2] * 0.65f, Minecraft.thePlayer.onGround));
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + offset[0] * 0.5f,
                        Minecraft.thePlayer.posY - 0.10000000149011612, Minecraft.thePlayer.posZ + offset[2] * 0.5f);
                if (Minecraft.thePlayer.isCollidedHorizontally) {
                    collided = true;
                }
                int i = 0;
                while (i < 3) {
                    Minecraft.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer,
                            C0BPacketEntityAction.Action.START_SNEAKING));
                    Minecraft.getNetHandler().addToSendQueue(
                            new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                    ++i;
                }
            }
            Minecraft.getNetHandler().addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        super.onUpdate();
    }

}
