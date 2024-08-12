package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {

public static final int EaZy = 650;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000933";

	protected EntityFishWakeFX(final World worldIn, final double p_i45073_2_, final double p_i45073_4_,
			final double p_i45073_6_, final double p_i45073_8_, final double p_i45073_10_, final double p_i45073_12_) {
		super(worldIn, p_i45073_2_, p_i45073_4_, p_i45073_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.30000001192092896D;
		motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
		motionZ *= 0.30000001192092896D;
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(19);
		setSize(0.01F, 0.01F);
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		particleGravity = 0.0F;
		motionX = p_i45073_8_;
		motionY = p_i45073_10_;
		motionZ = p_i45073_12_;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= particleGravity;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;
		final int var1 = 60 - particleMaxAge;
		final float var2 = var1 * 0.001F;
		setSize(var2, var2);
		setParticleTextureIndex(19 + var1 % 4);

		if (particleMaxAge-- <= 0) {
			setDead();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002573";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityFishWakeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
