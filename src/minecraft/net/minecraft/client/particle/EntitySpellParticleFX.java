package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySpellParticleFX extends EntityFX {

public static final int EaZy = 666;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Random field_174848_a = new Random();

	/** Base spell texture index */
	private int baseSpellTextureIndex = 128;
	// private static final String __OBFID = "http://https://fuckuskid00000926";

	protected EntitySpellParticleFX(final World worldIn, final double p_i1229_2_, final double p_i1229_4_,
			final double p_i1229_6_, final double p_i1229_8_, final double p_i1229_10_, final double p_i1229_12_) {
		super(worldIn, p_i1229_2_, p_i1229_4_, p_i1229_6_, 0.5D - field_174848_a.nextDouble(), p_i1229_10_,
				0.5D - field_174848_a.nextDouble());
		motionY *= 0.20000000298023224D;

		if (p_i1229_8_ == 0.0D && p_i1229_12_ == 0.0D) {
			motionX *= 0.10000000149011612D;
			motionZ *= 0.10000000149011612D;
		}

		particleScale *= 0.75F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		noClip = false;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (particleAge + p_180434_3_) / particleMaxAge * 32.0F;
		var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
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

		setParticleTextureIndex(baseSpellTextureIndex + 7 - particleAge * 8 / particleMaxAge);
		motionY += 0.004D;
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

	/**
	 * Sets the base spell texture index
	 */
	public void setBaseSpellTextureIndex(final int p_70589_1_) {
		baseSpellTextureIndex = p_70589_1_;
	}

	public static class AmbientMobFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002585";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_,
					p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
			var16.setAlphaF(0.15F);
			var16.setRBGColorF((float) p_178902_9_, (float) p_178902_11_, (float) p_178902_13_);
			return var16;
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002582";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}

	public static class InstantFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002584";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_,
					p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
			var16.setBaseSpellTextureIndex(144);
			return var16;
		}
	}

	public static class MobFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002583";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_,
					p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
			var16.setRBGColorF((float) p_178902_9_, (float) p_178902_11_, (float) p_178902_13_);
			return var16;
		}
	}

	public static class WitchFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002581";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final EntitySpellParticleFX var16 = new EntitySpellParticleFX(worldIn, p_178902_3_, p_178902_5_,
					p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
			var16.setBaseSpellTextureIndex(144);
			final float var17 = worldIn.rand.nextFloat() * 0.5F + 0.35F;
			var16.setRBGColorF(1.0F * var17, 0.0F * var17, 1.0F * var17);
			return var16;
		}
	}
}
