package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX {

public static final int EaZy = 657;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float lavaParticleScale;
	// private static final String __OBFID = "http://https://fuckuskid00000912";

	protected EntityLavaFX(final World worldIn, final double p_i1215_2_, final double p_i1215_4_,
			final double p_i1215_6_) {
		super(worldIn, p_i1215_2_, p_i1215_4_, p_i1215_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.800000011920929D;
		motionY *= 0.800000011920929D;
		motionZ *= 0.800000011920929D;
		motionY = rand.nextFloat() * 0.4F + 0.05F;
		particleRed = particleGreen = particleBlue = 1.0F;
		particleScale *= rand.nextFloat() * 2.0F + 0.2F;
		lavaParticleScale = particleScale;
		particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		noClip = false;
		setParticleTextureIndex(49);
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		float var2 = (particleAge + p_70070_1_) / particleMaxAge;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		final int var3 = super.getBrightnessForRender(p_70070_1_);
		final short var4 = 240;
		final int var5 = var3 >> 16 & 255;
		return var4 | var5 << 16;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return 1.0F;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		final float var9 = (particleAge + p_180434_3_) / particleMaxAge;
		particleScale = lavaParticleScale * (1.0F - var9 * var9);
		super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_,
				p_180434_8_);
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

		final float var1 = (float) particleAge / (float) particleMaxAge;

		if (rand.nextFloat() > var1) {
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, motionX, motionY, motionZ,
					new int[0]);
		}

		motionY -= 0.03D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9990000128746033D;
		motionY *= 0.9990000128746033D;
		motionZ *= 0.9990000128746033D;

		if (onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002595";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityLavaFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
		}
	}
}
