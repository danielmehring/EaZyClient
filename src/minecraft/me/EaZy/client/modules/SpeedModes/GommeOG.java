package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Timer;

public class GommeOG extends Module {

	public static GommeOG mod;

	public static final int EaZy = 2044;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public GommeOG() {
		super("GommeOG", 0, "", Category.SPEED);
		mod = this;
	}

	private double GraundROFL;

	@Override
	public void onEnable() {
		EventManager.register(this);
		Timer.timerSpeed = 1.2f;
		GraundROFL = Minecraft.thePlayer.posY;
		super.onEnable();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "GammelBoden";
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

	public EventTarget onPostMotionUpdates(final EventPostMotionUpdates event) {
		if (!Minecraft.thePlayer.movementInput.jump) {
			if (Minecraft.thePlayer.onGround) {
				GraundROFL = Minecraft.thePlayer.posY;
			} else if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
				Minecraft.thePlayer.posY = GraundROFL;
			}
		}
		return null;
	}

	public EventTarget onPlayerMove(final EventMovePlayer e) {
		if (!Minecraft.thePlayer.isInWater() && !Minecraft.thePlayer.capabilities.isFlying) {
			Timer.timerSpeed = 1.1f;
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
		}
	}

}
