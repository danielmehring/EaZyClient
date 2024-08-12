package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecartRiding extends MovingSound {

public static final int EaZy = 437;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityPlayer player;
	private final EntityMinecart minecart;
	// private static final String __OBFID = "http://https://fuckuskid00001119";

	public MovingSoundMinecartRiding(final EntityPlayer p_i45106_1_, final EntityMinecart minecart) {
		super(new ResourceLocation("minecraft:minecart.inside"));
		player = p_i45106_1_;
		this.minecart = minecart;
		attenuationType = ISound.AttenuationType.NONE;
		repeat = true;
		repeatDelay = 0;
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		if (!minecart.isDead && player.isRiding() && player.ridingEntity == minecart) {
			final float var1 = MathHelper
					.sqrt_double(minecart.motionX * minecart.motionX + minecart.motionZ * minecart.motionZ);

			if (var1 >= 0.01D) {
				volume = 0.0F + MathHelper.clamp_float(var1, 0.0F, 1.0F) * 0.75F;
			} else {
				volume = 0.0F;
			}
		} else {
			donePlaying = true;
		}
	}
}
