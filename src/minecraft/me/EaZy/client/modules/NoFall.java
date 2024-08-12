package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventSendPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

public static NoFall mod;


    public static final int EaZy = 141;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoFall() {
        super("NoFall", 49, "nf", Category.PLAYER, "Avoid fall damage.");
        mod = this;
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Keinfall";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
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

        super.onUpdate();
    }

    public EventTarget onPacket(final EventSendPacket event) {
        if (event.getPacket() instanceof C03PacketPlayer && Minecraft.thePlayer.posY > 0) {
            final C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
            if (Minecraft.thePlayer.fallDistance >= 2.0f) {
                player.onGround = true;
                Minecraft.thePlayer.fallDistance = 0.0f;
            }
        }
        return null;
    }
}
