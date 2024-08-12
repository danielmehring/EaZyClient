package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class WaterSpeed extends Module {

public static WaterSpeed mod;


    public static final int EaZy = 195;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public WaterSpeed() {
        super("WaterSpeed", 0, "", Category.MOVEMENT, "Speeds up in water.");
        Client.setmgr.rSetting(new Setting("Speed", this, 1, 2, 15, false));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "WasserGeschindigkeitserhöher";
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

    public EventTarget onPlayerMove(final EventMovePlayer e) {
        if (Minecraft.thePlayer.isInWater()) {
            e.x *= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
            e.z *= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
        }
        return null;
    }
}
