package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.MouseEvent;
import me.EaZy.client.utils.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;

public class MidClickFriends extends Module {

public static MidClickFriends mod;


    public static final int EaZy = 137;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public MidClickFriends() {
        super("MidClickFriends", 0, "mcf", Category.OTHER, "Middle-Click on\nPlayers to\nFriend/Unfriend.");
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

        super.onUpdate();
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "MittelKlickFreunde";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    public EventTarget onMouseClick(final MouseEvent event) {
        if (event.key == 2 && mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null
                && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().objectMouseOver.entityHit;
            final String playerName = player.getName();
            if (Friends.containsName(playerName)) {
                Friends.remove(playerName);
                msg("Removed: " + playerName);
            } else {
                Friends.add(playerName);
                msg("Added: " + playerName);
            }
        }
        return null;
    }
}
