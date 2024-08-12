package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityAuraFX extends EntityFX {

public static final int EaZy = 635;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000929";

	protected EntityAuraFX(final World worldIn, final double p_i1232_2_, final double p_i1232_4_,
			final double p_i1232_6_, final double p_i1232_8_, final double p_i1232_10_, final double p_i1232_12_) {
		super(worldIn, p_i1232_2_, p_i1232_4_, p_i1232_6_, p_i1232_8_, p_i1232_10_, p_i1232_12_);
		final float var14 = rand.nextFloat() * 0.1F + 0.2F;
		particleRed = var14;
		particleGreen = var14;
		particleBlue = var14;
		setParticleTextureIndex(0);
		setSize(0.02F, 0.02F);
		particleScale *= rand.nextFloat() * 0.6F + 0.5F;
		motionX *= 0.019999999552965164D;
		motionY *= 0.019999999552965164D;
		motionZ *= 0.019999999552965164D;
		particleMaxAge = (int) (20.0D / (Math.random() * 0.8D + 0.2D));
		noClip = true;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.99D;
		motionY *= 0.99D;
		motionZ *= 0.99D;

		if (particleMaxAge-- <= 0) {
			setDead();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002577";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityAuraFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}

	public static class HappyVillagerFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002578";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final EntityAuraFX var16 = new EntityAuraFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_,
					p_178902_11_, p_178902_13_);
			var16.setParticleTextureIndex(82);
			var16.setRBGColorF(1.0F, 1.0F, 1.0F);
			return var16;
		}
	}
}
