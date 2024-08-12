/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.input.Keyboard
 */
package me.EaZy.client.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

public class InventoryWalk extends Module {

public static InventoryWalk mod;


    public static final int EaZy = 129;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public InventoryWalk() {
        super("InventoryWalk", 72, "inv", Category.MOVEMENT, "Move while you\nare in the inventory.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Inventarlauf";
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
        try {
            updateKeys();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (Minecraft.currentScreen instanceof GuiChat || Minecraft.currentScreen == null
                || Minecraft.currentScreen instanceof GuiCommandBlock) {
            return;
        }
        try {
            if (Keyboard.isKeyDown(200)) {
                Minecraft.thePlayer.rotationPitch -= 10.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                Minecraft.thePlayer.rotationPitch += 10.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                Minecraft.thePlayer.rotationYaw -= 10.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                Minecraft.thePlayer.rotationYaw += 10.0f;
            }
        } catch (final Exception e) {

        }
        super.onUpdate();
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    public EventTarget onReceive(final EventReceivePacket e) {
        if (e.getPacket() instanceof S2EPacketCloseWindow && Minecraft.currentScreen instanceof GuiInventory) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow());
            e.setCancelled(true);
            Minecraft.thePlayer.sendQueue
                    .addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        }
        return null;
    }

    private void updateKeys() {
        if (!(Minecraft.currentScreen instanceof GuiChat) && !(Minecraft.currentScreen instanceof GuiCommandBlock)) {
            final KeyBinding[] moveKeys = Minecraft.currentScreen instanceof ClickGUI
                    ? new KeyBinding[]{Minecraft.gameSettings.keyBindForward, Minecraft.gameSettings.keyBindBack,
                        Minecraft.gameSettings.keyBindLeft, Minecraft.gameSettings.keyBindRight,
                        Minecraft.gameSettings.keyBindJump, Minecraft.gameSettings.keyBindSprint,
                        Minecraft.gameSettings.keyBindSneak}
                    : new KeyBinding[]{Minecraft.gameSettings.keyBindForward, Minecraft.gameSettings.keyBindBack,
                        Minecraft.gameSettings.keyBindLeft, Minecraft.gameSettings.keyBindRight,
                        Minecraft.gameSettings.keyBindJump, Minecraft.gameSettings.keyBindSprint};
            final KeyBinding[] arrkeyBinding = moveKeys;
            final int n = arrkeyBinding.length;
            int n2 = 0;
            for (int i = 0; i < n && arrkeyBinding[i] != null; i++) {
                try {
                    final KeyBinding bind = arrkeyBinding[i];
                    bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
