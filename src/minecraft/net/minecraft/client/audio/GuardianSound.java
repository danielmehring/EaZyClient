package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class GuardianSound extends MovingSound {

public static final int EaZy = 431;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityGuardian guardian;
	// private static final String __OBFID = "http://https://fuckuskid00002381";

	public GuardianSound(final EntityGuardian guardian) {
		super(new ResourceLocation("minecraft:mob.guardian.attack"));
		this.guardian = guardian;
		attenuationType = ISound.AttenuationType.NONE;
		repeat = true;
		repeatDelay = 0;
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		if (!guardian.isDead && guardian.func_175474_cn()) {
			xPosF = (float) guardian.posX;
			yPosF = (float) guardian.posY;
			zPosF = (float) guardian.posZ;
			final float var1 = guardian.func_175477_p(0.0F);
			volume = 0.0F + 1.0F * var1 * var1;
			pitch = 0.7F + 0.5F * var1;
		} else {
			donePlaying = true;
		}
	}
}
