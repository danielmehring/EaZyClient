package me.EaZy.client.modules.SpeedModes;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.utils.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class GommeBHop extends Module {

	public static GommeBHop mod;

	public static final int EaZy = 166;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public GommeBHop() {
		super("GommeBHop", 0, "", Category.SPEED);
		mod = this;
	}

	double strafetarget;

	double strafewfloat;

	private int speedTimer;

	public EventTarget asd(EventMovePlayer e) {

		if (mc.thePlayer.motionY < 0) {
			e.y *= 1.19;
		}

		return null;
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		speedTimer = 0;
		Minecraft.thePlayer.speedInAir = 0.02f;
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "GammelHazenH�pfer";
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

		if (!MovementUtil.areWalkingKeysDown())
			return;

		if (mc.thePlayer.onGround) {
			final float var1 = mc.thePlayer.rotationYaw * 0.017453292F;
			mc.thePlayer.motionX -= MathHelper.sin(var1) * 0.205F;
			mc.thePlayer.motionZ += MathHelper.cos(var1) * 0.205F;
			speedTimer++;

			if (speedTimer == 1) {
				mc.thePlayer.motionY = 0.386;
				Minecraft.thePlayer.speedInAir = 0.0218f;
			}
		} else {
			speedTimer = 0;
			Minecraft.thePlayer.speedInAir = 0.02f;
		}

	}
}
