package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNoteFX extends EntityFX {

public static final int EaZy = 658;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	float noteParticleScale;
	// private static final String __OBFID = "http://https://fuckuskid00000913";

	protected EntityNoteFX(final World worldIn, final double p_i46353_2_, final double p_i46353_4_,
			final double p_i46353_6_, final double p_i46353_8_, final double p_i46353_10_, final double p_i46353_12_) {
		this(worldIn, p_i46353_2_, p_i46353_4_, p_i46353_6_, p_i46353_8_, p_i46353_10_, p_i46353_12_, 2.0F);
	}

	protected EntityNoteFX(final World worldIn, final double p_i1217_2_, final double p_i1217_4_,
			final double p_i1217_6_, final double p_i1217_8_, final double p_i1217_10_, final double p_i1217_12_,
			final float p_i1217_14_) {
		super(worldIn, p_i1217_2_, p_i1217_4_, p_i1217_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.009999999776482582D;
		motionY *= 0.009999999776482582D;
		motionZ *= 0.009999999776482582D;
		motionY += 0.2D;
		particleRed = MathHelper.sin(((float) p_i1217_8_ + 0.0F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleGreen = MathHelper.sin(((float) p_i1217_8_ + 0.33333334F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleBlue = MathHelper.sin(((float) p_i1217_8_ + 0.6666667F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleScale *= 0.75F;
		particleScale *= p_i1217_14_;
		noteParticleScale = particleScale;
		particleMaxAge = 6;
		noClip = false;
		setParticleTextureIndex(64);
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (particleAge + p_180434_3_) / particleMaxAge * 32.0F;
		var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
		particleScale = noteParticleScale * var9;
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

		moveEntity(motionX, motionY, motionZ);

		if (posY == prevPosY) {
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}

		motionX *= 0.6600000262260437D;
		motionY *= 0.6600000262260437D;
		motionZ *= 0.6600000262260437D;

		if (onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002592";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityNoteFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
