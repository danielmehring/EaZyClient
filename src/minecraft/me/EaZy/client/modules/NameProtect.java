package me.EaZy.client.modules;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.Friend;
import me.EaZy.client.utils.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;

public class NameProtect extends Module {

public static NameProtect mod;


    public static final int EaZy = 138;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NameProtect() {
        super("NameProtect", 0, "name", Category.RENDER, "Protects your name.");
        final ArrayList<String> npName = new ArrayList<>();
        Client.setmgr.rSetting(new Setting("Name", this, "EaZy", npName));
        mod = this;
    }

    private String suffix;

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "NamensSchützer [" + Client.setmgr.getSettingByName(this, "Name").getValString() + "]";
            } else {
                suffix = "NamensSchützer";
            }
        } else {

            if (Configs.suffix) {
                suffix = "NameProtect [" + Client.setmgr.getSettingByName(this, "Name").getValString() + "]";
            } else {
                suffix = "NameProtect";
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

    public EventTarget onReceivePacket(final EventReceivePacket e) {
        if (e.getPacket() instanceof S45PacketTitle) {
            final S45PacketTitle packet = (S45PacketTitle) e.getPacket();

            String newtext = packet.textComponent.getFormattedText();

            if (Minecraft.getNetHandler() != null) {
                if (newtext.contains(Minecraft.session.getUsername())) {
                    newtext = newtext.replace(Minecraft.session.getUsername(),
                            Client.setmgr.getSettingByName(NameProtect.mod, "Name").getValString()
                            + "§r");
                }
                try {
                    for (final Friend f : Friends.getFriends()) {
                        if (newtext.contains(f.getUsername())) {
                            newtext = newtext.replace(f.getUsername(), f.getNick() + "§r");
                        }
                    }
                } catch (final Exception ex) {
                }
            }
            packet.textComponent = new ChatComponentText(newtext);
        }
        return null;
    }
}
