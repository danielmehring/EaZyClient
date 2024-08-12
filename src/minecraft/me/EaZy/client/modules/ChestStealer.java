package me.EaZy.client.modules;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;

public class ChestStealer extends Module {

public static ChestStealer mod;


    public static final int EaZy = 103;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private S30PacketWindowItems packet;

    private int delay = 0;

    private final ArrayList<BlockPos> blocks = new ArrayList();

    @Override
    public void onEnable() {
        EventManager.register(this);
        blocks.clear();
        super.onEnable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KistenPole";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    public ChestStealer() {
        super("ChestStealer", 75, "cs", Category.WORLD, "Empties the opened\nchest.");
        Client.setmgr.rSetting(new Setting("Delay", this, 2, 0, 5, true));
        mod = this;
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

        final KeyBinding[] keys = new KeyBinding[]{Minecraft.gameSettings.keyBindForward,
            Minecraft.gameSettings.keyBindBack, Minecraft.gameSettings.keyBindLeft,
            Minecraft.gameSettings.keyBindRight, Minecraft.gameSettings.keyBindJump};
        if (mc.inGameHasFocus || packet == null || Minecraft.thePlayer.openContainer.windowId != packet.func_148911_c()
                || !(Minecraft.currentScreen instanceof GuiChest)) {
            return;
        }
        if (!isContainerEmpty(Minecraft.thePlayer.openContainer)) {
            final int slotId = getNextSlot(Minecraft.thePlayer.openContainer);
            if (delay >= Client.setmgr.getSettingByName(this, "Delay").getValFloat()) {
                Minecraft.playerController.windowClick(Minecraft.thePlayer.openContainer.windowId, slotId, 0, 1,
                        Minecraft.thePlayer);
                delay = 0;
            }
            ++delay;
        } else {
            Minecraft.thePlayer.closeScreen();
            final KeyBinding[] arrkeyBinding = keys;
            final int n = arrkeyBinding.length;
            int n2 = 0;
            while (n2 < n) {
                final KeyBinding bind = arrkeyBinding[n2];
                bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
                ++n2;
            }
            packet = null;
        }
    }

    public EventTarget onPacketRecieve(final EventReceivePacket event) {
        if (event.getPacket() instanceof S30PacketWindowItems) {
            packet = (S30PacketWindowItems) event.getPacket();
        }
        return null;
    }

    private int getNextSlot(final Container container) {
        int i = 0;
        final int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        while (i < slotAmount) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    private boolean isContainerEmpty(final Container container) {
        boolean temp = true;
        int i = 0;
        final int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        while (i < slotAmount) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
            ++i;
        }
        return temp;
    }
}
