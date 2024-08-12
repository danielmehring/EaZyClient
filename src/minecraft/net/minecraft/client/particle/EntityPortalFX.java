package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX {

public static final int EaZy = 661;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float portalParticleScale;
	private final double portalPosX;
	private final double portalPosY;
	private final double portalPosZ;
	// private static final String __OBFID = "http://https://fuckuskid00000921";

	protected EntityPortalFX(final World worldIn, final double p_i46351_2_, final double p_i46351_4_,
			final double p_i46351_6_, final double p_i46351_8_, final double p_i46351_10_, final double p_i46351_12_) {
		super(worldIn, p_i46351_2_, p_i46351_4_, p_i46351_6_, p_i46351_8_, p_i46351_10_, p_i46351_12_);
		motionX = p_i46351_8_;
		motionY = p_i46351_10_;
		motionZ = p_i46351_12_;
		portalPosX = posX = p_i46351_2_;
		portalPosY = posY = p_i46351_4_;
		portalPosZ = posZ = p_i46351_6_;
		final float var14 = rand.nextFloat() * 0.6F + 0.4F;
		portalParticleScale = particleScale = rand.nextFloat() * 0.2F + 0.5F;
		particleRed = particleGreen = particleBlue = 1.0F * var14;
		particleGreen *= 0.3F;
		particleRed *= 0.9F;
		particleMaxAge = (int) (Math.random() * 10.0D) + 40;
		noClip = true;
		setParticleTextureIndex((int) (Math.random() * 8.0D));
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (particleAge + p_180434_3_) / particleMaxAge;
		var9 = 1.0F - var9;
		var9 *= var9;
		var9 = 1.0F - var9;
		particleScale = portalParticleScale * var9;
		super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_,
				p_180434_8_);
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		final int var2 = super.getBrightnessForRender(p_70070_1_);
		float var3 = (float) particleAge / (float) particleMaxAge;
		var3 *= var3;
		var3 *= var3;
		final int var4 = var2 & 255;
		int var5 = var2 >> 16 & 255;
		var5 += (int) (var3 * 15.0F * 16.0F);

		if (var5 > 240) {
			var5 = 240;
		}

		return var4 | var5 << 16;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		final float var2 = super.getBrightness(p_70013_1_);
		float var3 = (float) particleAge / (float) particleMaxAge;
		var3 = var3 * var3 * var3 * var3;
		return var2 * (1.0F - var3) + var3;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float var1 = (float) particleAge / (float) particleMaxAge;
		final float var2 = var1;
		var1 = -var1 + var1 * var1 * 2.0F;
		var1 = 1.0F - var1;
		posX = portalPosX + motionX * var1;
		posY = portalPosY + motionY * var1 + (1.0F - var2);
		posZ = portalPosZ + motionZ * var1;

		if (particleAge++ >= particleMaxAge) {
			setDead();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002590";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityPortalFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
