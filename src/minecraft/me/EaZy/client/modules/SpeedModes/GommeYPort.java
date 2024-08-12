package me.EaZy.client.modules.SpeedModes;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.utils.PlayerUtil;

public class GommeYPort extends Module {

	public static GommeYPort mod;

	public static final int EaZy = 2070;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public GommeYPort() {
		super("GommeYPort", 0, "", Category.SPEED);
		mod = this;
	}

	int stage;

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

		if (PlayerUtil.isPlayerMoving() && !mc.gameSettings.keyBindJump.pressed) {
			if (mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.42;
				mc.thePlayer.speedInAir = 0.03f;
				stage = 0;
			} else {
				if (stage < 2) {
					mc.thePlayer.motionY = -0.21;
					mc.thePlayer.speedInAir = 0.04f;
					stage++;
				}
			}
		} else {
			mc.thePlayer.speedInAir = 0.02f;
		}

	}

	@Override
	public void onDisable() {
		mc.thePlayer.speedInAir = 0.02f;
		stage = 0;
		super.onDisable();
	}

}
