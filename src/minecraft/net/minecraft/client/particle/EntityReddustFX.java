package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityReddustFX extends EntityFX {

public static final int EaZy = 663;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	float reddustParticleScale;
	// private static final String __OBFID = "http://https://fuckuskid00000923";

	protected EntityReddustFX(final World worldIn, final double p_i46349_2_, final double p_i46349_4_,
			final double p_i46349_6_, final float p_i46349_8_, final float p_i46349_9_, final float p_i46349_10_) {
		this(worldIn, p_i46349_2_, p_i46349_4_, p_i46349_6_, 1.0F, p_i46349_8_, p_i46349_9_, p_i46349_10_);
	}

	protected EntityReddustFX(final World worldIn, final double p_i46350_2_, final double p_i46350_4_,
			final double p_i46350_6_, final float p_i46350_8_, float p_i46350_9_, final float p_i46350_10_,
			final float p_i46350_11_) {
		super(worldIn, p_i46350_2_, p_i46350_4_, p_i46350_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;

		if (p_i46350_9_ == 0.0F) {
			p_i46350_9_ = 1.0F;
		}

		final float var12 = (float) Math.random() * 0.4F + 0.6F;
		particleRed = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_9_ * var12;
		particleGreen = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_10_ * var12;
		particleBlue = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_11_ * var12;
		particleScale *= 0.75F;
		particleScale *= p_i46350_8_;
		reddustParticleScale = particleScale;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		particleMaxAge = (int) (particleMaxAge * p_i46350_8_);
		noClip = false;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (particleAge + p_180434_3_) / particleMaxAge * 32.0F;
		var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
		particleScale = reddustParticleScale * var9;
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

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		moveEntity(motionX, motionY, motionZ);

		if (posY == prevPosY) {
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}

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
		// "http://https://fuckuskid00002589";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityReddustFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, (float) p_178902_9_,
					(float) p_178902_11_, (float) p_178902_13_);
		}
	}
}
