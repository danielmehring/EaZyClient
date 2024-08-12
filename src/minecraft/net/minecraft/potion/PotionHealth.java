package net.minecraft.potion;

import net.minecraft.util.ResourceLocation;

public class PotionHealth extends Potion {

public static final int EaZy = 1494;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001527";

	public PotionHealth(final int p_i45898_1_, final ResourceLocation p_i45898_2_, final boolean p_i45898_3_,
			final int p_i45898_4_) {
		super(p_i45898_1_, p_i45898_2_, p_i45898_3_, p_i45898_4_);
	}

	/**
	 * Returns true if the potion has an instant effect instead of a continuous
	 * one (eg Harming)
	 */
	@Override
	public boolean isInstant() {
		return true;
	}

	/**
	 * checks if Potion effect is ready to be applied this tick.
	 */
	@Override
	public boolean isReady(final int p_76397_1_, final int p_76397_2_) {
		return p_76397_1_ >= 1;
	}
}
