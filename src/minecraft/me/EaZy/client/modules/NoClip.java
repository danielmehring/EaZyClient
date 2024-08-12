package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class NoClip extends Module {

public static NoClip mod;


    public static final int EaZy = 140;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoClip() {
        super("NoClip", 0, "", Category.MOVEMENT, "NoClip through walls.");
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

        if (PlayerUtil.isPlayerInsideBlock()) {
            Minecraft.thePlayer.capabilities.isFlying = true;
            Minecraft.thePlayer.noClip = true;
        } else {
            Minecraft.thePlayer.capabilities.isFlying = false;
            Minecraft.thePlayer.noClip = false;
        }

        super.onUpdate();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KeinGrip";
        } else {
            return super.getRenderName();
        }
    }

    public EventTarget onReceivePacket(final EventReceivePacket event) {
        if (PlayerUtil.isPlayerInsideBlock() && event.getPacket() instanceof S12PacketEntityVelocity
                && Minecraft.theWorld.getEntityByID(((S12PacketEntityVelocity) event.getPacket()).entityID())
                        .equals(Minecraft.thePlayer)
                || event.getPacket() instanceof S27PacketExplosion) {
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                event.setCancelled(true);
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
                event.setCancelled(true);
            }
        }
        return null;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Minecraft.thePlayer.noClip = false;
        Minecraft.thePlayer.capabilities.isFlying = false;
        super.onDisable();
    }
}
