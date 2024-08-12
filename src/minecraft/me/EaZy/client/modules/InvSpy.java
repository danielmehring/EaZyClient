package me.EaZy.client.modules;

import java.lang.reflect.Field;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.events.MouseEvent;
import me.EaZy.client.utils.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class InvSpy extends Module {

public static InvSpy mod;


	public static final int EaZy = 2041;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public InvSpy() {
		super("InvSpy", 0, "", Category.OTHER);
		mod = this;
	}
	
	public EventTarget onMouseClick(final MouseEvent event) {
        if (event.key == 1 && mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null
                && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().objectMouseOver.entityHit;
            mc.displayGuiScreen(new GuiInventory(player));
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
        super.onDisable();
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
	public void onRender() {
		if (!isToggled()) {
			return;
		}
		super.onRender();
	}
}
