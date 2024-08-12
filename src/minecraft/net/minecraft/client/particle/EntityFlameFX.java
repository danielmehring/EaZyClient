package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {

public static final int EaZy = 651;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** the scale of the flame FX */
	private final float flameScale;
	// private static final String __OBFID = "http://https://fuckuskid00000907";

	protected EntityFlameFX(final World worldIn, final double p_i1209_2_, double p_i1209_4_, double p_i1209_6_,
			final double p_i1209_8_, final double p_i1209_10_, final double p_i1209_12_) {
		super(worldIn, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
		motionX = motionX * 0.009999999776482582D + p_i1209_8_;
		motionY = motionY * 0.009999999776482582D + p_i1209_10_;
		motionZ = motionZ * 0.009999999776482582D + p_i1209_12_;
		rand.nextFloat();
		rand.nextFloat();
		p_i1209_4_ += (rand.nextFloat() - rand.nextFloat()) * 0.05F;
		p_i1209_6_ += (rand.nextFloat() - rand.nextFloat()) * 0.05F;
		flameScale = particleScale;
		particleRed = particleGreen = particleBlue = 1.0F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
		noClip = true;
		setParticleTextureIndex(48);
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		final float var9 = (particleAge + p_180434_3_) / particleMaxAge;
		particleScale = flameScale * (1.0F - var9 * var9 * 0.5F);
		super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_,
				p_180434_8_);
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		float var2 = (particleAge + p_70070_1_) / particleMaxAge;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		final int var3 = super.getBrightnessForRender(p_70070_1_);
		int var4 = var3 & 255;
		final int var5 = var3 >> 16 & 255;
		var4 += (int) (var2 * 15.0F * 16.0F);

		if (var4 > 240) {
			var4 = 240;
		}

		return var4 | var5 << 16;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		float var2 = (particleAge + p_70013_1_) / particleMaxAge;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		final float var3 = super.getBrightness(p_70013_1_);
		return var3 * var2 + (1.0F - var2);
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

		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;

		if (onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002602";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityFlameFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
