package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {

public static final int EaZy = 1640;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GameSettings gameSettings;
	// private static final String __OBFID = "http://https://fuckuskid00000937";

	public MovementInputFromOptions(final GameSettings p_i1237_1_) {
		gameSettings = p_i1237_1_;
	}

	@Override
	public void updatePlayerMoveState() {
		moveStrafe = 0.0F;
		moveForward = 0.0F;

		if (gameSettings.keyBindForward.getIsKeyPressed()) {
			++moveForward;
		}

		if (gameSettings.keyBindBack.getIsKeyPressed()) {
			--moveForward;
		}

		if (gameSettings.keyBindLeft.getIsKeyPressed()) {
			++moveStrafe;
		}

		if (gameSettings.keyBindRight.getIsKeyPressed()) {
			--moveStrafe;
		}

		jump = gameSettings.keyBindJump.getIsKeyPressed();
		sneak = gameSettings.keyBindSneak.getIsKeyPressed();

		if (sneak) {
			moveStrafe = (float) (moveStrafe * 0.3D);
			moveForward = (float) (moveForward * 0.3D);
		}
	}
}
