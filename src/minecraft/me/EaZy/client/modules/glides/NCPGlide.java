/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.input.Keyboard
 */
package me.EaZy.client.modules.glides;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.YesCheat;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;

public class NCPGlide extends Module {

public static NCPGlide mod;


    public static final int EaZy = 121;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean damaged = false;

    public NCPGlide() {
        super(new String(
                new byte[]{0b1001110, 0b1000011, 0b1010000, 0b1000111, 0b1101100, 0b1101001, 0b1100100, 0b1100101}),
                34, "", Category.GLIDE, "Glide for old NCPs.");
        mod = this;
    }

    public EventTarget onPostMotionUpdates(final EventPostMotionUpdates event) {
        if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
            Minecraft.thePlayer.cameraYaw = 0.1f;
        }
        return null;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "NeinBetrügenPlusGleitgel";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        if (Minecraft.thePlayer != null) {
            Timer.timerSpeed = 1.0f;
            Minecraft.thePlayer.motionY = -0.5;
        }
        damaged = false;
        EventManager.unregister(this);
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
        if (Minecraft.thePlayer.hurtTime > 0 || damaged) {
            damaged = true;
            if (!Minecraft.thePlayer.capabilities.isFlying && !Minecraft.thePlayer.isSneaking()) {
                Minecraft.thePlayer.onGround = true;
                Minecraft.thePlayer.motionY = -0.0;
                Minecraft.gameSettings.keyBindSprint.pressed = true;
                System.currentTimeMillis();

            }
        }
        super.onUpdate();
    }

    @Override
    public void onEnable() {
        if (!(YesCheat.mode == Mode.NCP || YesCheat.mode == Mode.ALL)) {
            msg("§cThis only works with ByPass Mode set to NCP!");
            Client.disable(getName());
            return;
        }
        Client.disable(Category.GLIDE, this.getClass());
        EventManager.register(this);
        damaged = false;
        if (Keyboard.isKeyDown(157)) {
            return;
        }
        for (int i = 0; i < 90; i++) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.039, Minecraft.thePlayer.posZ, false));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.001, Minecraft.thePlayer.posZ, false));

        }

        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
    }

    public EventTarget onPacket(final EventSendPacket e) {
        if (Minecraft.thePlayer.motionX == 0.0 && Minecraft.thePlayer.motionY == 0.0
                && Minecraft.thePlayer.motionZ == 0.0 && !Minecraft.thePlayer.isEating()
                && e.getPacket() instanceof C03PacketPlayer) {
            e.setCancelled(true);
        }
        return null;
    }
}
