package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {

public static final int EaZy = 645;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000903";

	protected EntityExplodeFX(final World worldIn, final double p_i1205_2_, final double p_i1205_4_,
			final double p_i1205_6_, final double p_i1205_8_, final double p_i1205_10_, final double p_i1205_12_) {
		super(worldIn, p_i1205_2_, p_i1205_4_, p_i1205_6_, p_i1205_8_, p_i1205_10_, p_i1205_12_);
		motionX = p_i1205_8_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		motionY = p_i1205_10_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		motionZ = p_i1205_12_ + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
		particleRed = particleGreen = particleBlue = rand.nextFloat() * 0.3F + 0.7F;
		particleScale = rand.nextFloat() * rand.nextFloat() * 6.0F + 1.0F;
		particleMaxAge = (int) (16.0D / (rand.nextFloat() * 0.8D + 0.2D)) + 2;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge) {
			setDead();
		}

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		motionY += 0.004D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.8999999761581421D;
		motionY *= 0.8999999761581421D;
		motionZ *= 0.8999999761581421D;

		if (onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002604";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityExplodeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
