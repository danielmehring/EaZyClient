package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak extends Module {

public static Sneak mod;


    public static final int EaZy = 160;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Sneak() {
        super("Sneak", 0, "", Category.PLAYER, "Sneak Server sided.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Schleicherei";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
        super.onEnable();
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

        if (Minecraft.thePlayer.ticksExisted % 2 == 0) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(
                    new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }

        super.onUpdate();
    }

    @Override
    public void onDisable() {
        Minecraft.thePlayer.sendQueue.addToSendQueue(
                new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        super.onDisable();
    }
}
