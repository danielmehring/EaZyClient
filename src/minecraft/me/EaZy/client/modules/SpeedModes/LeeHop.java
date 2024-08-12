package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Timer;

public class LeeHop extends Module {

	public static LeeHop mod;

	public static final int EaZy = 2044;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public LeeHop() {
		super("LeeHop", 0, "", Category.SPEED);
		mod = this;
	}

	private double GraundROFL;

	@Override
	public void onEnable() {
		EventManager.register(this);
		Timer.timerSpeed = 1.4f;
		super.onEnable();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "L33H0p";
		} else {
			return super.getRenderName();
		}
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		Timer.timerSpeed = 1.0f;
		super.onDisable();
	}

	public EventTarget onPlayerMove(final EventMovePlayer e) {
		if (!Minecraft.thePlayer.isInWater() && !Minecraft.thePlayer.capabilities.isFlying) {
			Timer.timerSpeed = 1.4f;
		} else {
			Timer.timerSpeed = 1.0f;
		}
		return null;
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
		if (!(Minecraft.thePlayer.onGround || Minecraft.thePlayer.isInWater()
				|| Minecraft.thePlayer.capabilities.isFlying)) {
			Minecraft.thePlayer.motionX = 0.0;
			Minecraft.thePlayer.motionZ = 0.0;
		}
		Minecraft.thePlayer.jumpMovementFactor = Minecraft.thePlayer.isSneaking() ? 1.0f : 0.3f;

		if (!PlayerUtil.isPlayerMoving()) {
			Timer.timerSpeed = 1.0f;
		} else {
			System.out.println("asd");
			if (Minecraft.thePlayer.onGround) {
				Minecraft.thePlayer.jump();
			}
		}
	}

}
