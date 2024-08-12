package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastBow extends Module {

public static FastBow mod;


    public static final int EaZy = 108;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public FastBow() {
        super("FastBow", 0, "fb", Category.COMBAT, "Let's you shoot\nfaster with a bow.");
        Client.setmgr.rSetting(new Setting("Delay", this, 2.0f, 0.0f, 5.0f, true));
        Client.setmgr.rSetting(new Setting("Pull", this, 7.0f, 0.0f, 20.0f, true));
        mod = this;
    }

    private int delay = 0;
    private boolean next = true;

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "FlitzeBogen";
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
        delay++;
        if (YesCheat.enabled && YesCheat.mode == Mode.Gomme) {
            if (Minecraft.thePlayer.getHealth() > 0.0f
                    && (Minecraft.thePlayer.onGround || Minecraft.thePlayer.capabilities.isCreativeMode)
                    && Minecraft.thePlayer.inventory.getCurrentItem() != null
                    && Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
                    && Minecraft.gameSettings.keyBindUseItem.pressed) {
                if (next) {
                    new Thread(() -> {
                        try {
                            next = false;
                            Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld,
                                    Minecraft.thePlayer.inventory.getCurrentItem());
                            Minecraft.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(
                                    Minecraft.thePlayer.inventory.getCurrentItem(), Minecraft.theWorld,
                                    Minecraft.thePlayer);
                            int thcount = 0;
                            while (thcount < 20) {
                                try {
                                    Thread.sleep(40l);
                                } catch (final InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thcount++;
                                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
                            }
                            Minecraft.getNetHandler()
                                    .addToSendQueue(new C07PacketPlayerDigging(
                                            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0),
                                            EnumFacing.DOWN));
                            Minecraft.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(
                                    Minecraft.thePlayer.inventory.getCurrentItem(), Minecraft.theWorld,
                                    Minecraft.thePlayer, 10);
                            Minecraft.thePlayer.stopUsingItem();
                            delay = 0;
                            next = true;
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        next = true;
                    }).start();
                }
            }
        } else {
            if (Minecraft.thePlayer.getHealth() > 0.0f
                    && (Minecraft.thePlayer.onGround || Minecraft.thePlayer.capabilities.isCreativeMode)
                    && Minecraft.thePlayer.inventory.getCurrentItem() != null
                    && Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
                    && Minecraft.gameSettings.keyBindUseItem.pressed
                    && delay >= Client.setmgr.getSettingByName(this, "Delay").getValFloat() && Minecraft.thePlayer
                    .getItemInUseCount() < 72000 - Client.setmgr.getSettingByName(this, "Pull").getValFloat()) {
                Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld,
                        Minecraft.thePlayer.inventory.getCurrentItem());
                Minecraft.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(
                        Minecraft.thePlayer.inventory.getCurrentItem(), Minecraft.theWorld, Minecraft.thePlayer);
                int i = 0;
                while (i < 20) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
                    ++i;
                }
                Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                Minecraft.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(
                        Minecraft.thePlayer.inventory.getCurrentItem(), Minecraft.theWorld, Minecraft.thePlayer, 10);
                Minecraft.thePlayer.stopUsingItem();
                delay = 0;
            }
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        next = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    public EventTarget onReceivePacket(final EventReceivePacket e) {
        if (e.getPacket() instanceof S09PacketHeldItemChange && Minecraft.thePlayer.getCurrentEquippedItem() != null
                && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            Minecraft.thePlayer.sendQueue
                    .addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
            Minecraft.playerController.updateController();
            e.setCancelled(true);
        }
        if (e.getPacket() instanceof S08PacketPlayerPosLook && Minecraft.thePlayer.getCurrentEquippedItem() != null
                && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) e.getPacket();
            packet.rotationYawPacket = Minecraft.thePlayer.rotationYaw;
            packet.rotationPitchPacket = Minecraft.thePlayer.rotationPitch;
        }
        return null;
    }
}
