package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventSafewalk;
import net.minecraft.client.gui.GuiMainMenu;

public class SafeWalk extends Module {

public static SafeWalk mod;


    public static final int EaZy = 157;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public SafeWalk() {
        super("SafeWalk", 81, "sw", Category.MOVEMENT, "Don't fall from blocks.");
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

    public EventTarget onWalking(final EventSafewalk event) {
        event.setShouldWalkSafely(true);
        return null;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "SichererSpaziergang";
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
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }
}
