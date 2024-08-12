package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;

public class FastUse extends Module {

public static FastUse mod;


    public static final int EaZy = 112;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private String suffix;

    public FastUse() {
        super("FastUse", 0, "fu", Category.COMBAT, "Use food and\npotions faster.");
        Client.setmgr.rSetting(new Setting("Instant", this, false));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "SchnellesBenutzen ["
                        + (Client.setmgr.getSettingByName(this, "Instant").getValBoolean() ? "INSTANT" : "FAST") + "]";
            } else {
                suffix = "SchnellesBenutzen";
            }
        } else {

            if (Configs.suffix) {
                suffix = "FastUse ["
                        + (Client.setmgr.getSettingByName(this, "Instant").getValBoolean() ? "INSTANT" : "FAST") + "]";
            } else {
                suffix = "FastUse";
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
        if (!Minecraft.thePlayer.isBlocking()) {
            if (Client.setmgr.getSettingByName(this, "Instant").getValBoolean()) {
                if (Minecraft.thePlayer.isUsingItem() && Minecraft.thePlayer.getHeldItem().getItem() != Items.bow) {
                    Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1),
                            -1, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                    Minecraft.getNetHandler()
                            .addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
                    int x = 0;
                    while (x < 32) {
                        Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer(Minecraft.thePlayer.onGround));
                        Minecraft.getNetHandler()
                                .addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
                        ++x;
                    }
                    Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    Minecraft.thePlayer.stopUsingItem();
                }
            } else if (Minecraft.thePlayer.getItemInUseDuration() == 16
                    && Minecraft.thePlayer.getHeldItem().getItem() != Items.bow) {
                Timer.timerSpeed = 1.0f;
                int i = 0;
                while (i < 32) {
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer(false));
                    ++i;
                }
                Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                Minecraft.playerController.updateController();
            }
        }
        super.onUpdate();
    }
}
