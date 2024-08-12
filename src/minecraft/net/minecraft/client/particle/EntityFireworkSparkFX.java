package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFireworkSparkFX extends EntityFX {

public static final int EaZy = 647;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final int baseTextureIndex = 160;
	private boolean field_92054_ax;
	private boolean field_92048_ay;
	private final EffectRenderer field_92047_az;
	private float fadeColourRed;
	private float fadeColourGreen;
	private float fadeColourBlue;
	private boolean hasFadeColour;
	// private static final String __OBFID = "http://https://fuckuskid00000905";

	public EntityFireworkSparkFX(final World worldIn, final double p_i46356_2_, final double p_i46356_4_,
			final double p_i46356_6_, final double p_i46356_8_, final double p_i46356_10_, final double p_i46356_12_,
			final EffectRenderer p_i46356_14_) {
		super(worldIn, p_i46356_2_, p_i46356_4_, p_i46356_6_);
		motionX = p_i46356_8_;
		motionY = p_i46356_10_;
		motionZ = p_i46356_12_;
		field_92047_az = p_i46356_14_;
		particleScale *= 0.75F;
		particleMaxAge = 48 + rand.nextInt(12);
		noClip = false;
	}

	public void setTrail(final boolean p_92045_1_) {
		field_92054_ax = p_92045_1_;
	}

	public void setTwinkle(final boolean p_92043_1_) {
		field_92048_ay = p_92043_1_;
	}

	public void setColour(final int p_92044_1_) {
		final float var2 = ((p_92044_1_ & 16711680) >> 16) / 255.0F;
		final float var3 = ((p_92044_1_ & 65280) >> 8) / 255.0F;
		final float var4 = ((p_92044_1_ & 255) >> 0) / 255.0F;
		final float var5 = 1.0F;
		setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
	}

	public void setFadeColour(final int p_92046_1_) {
		fadeColourRed = ((p_92046_1_ & 16711680) >> 16) / 255.0F;
		fadeColourGreen = ((p_92046_1_ & 65280) >> 8) / 255.0F;
		fadeColourBlue = ((p_92046_1_ & 255) >> 0) / 255.0F;
		hasFadeColour = true;
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		if (!field_92048_ay || particleAge < particleMaxAge / 3 || (particleAge + particleMaxAge) / 3 % 2 == 0) {
			super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_,
					p_180434_7_, p_180434_8_);
		}
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

		if (particleAge > particleMaxAge / 2) {
			setAlphaF(1.0F - ((float) particleAge - (float) (particleMaxAge / 2)) / particleMaxAge);

			if (hasFadeColour) {
				particleRed += (fadeColourRed - particleRed) * 0.2F;
				particleGreen += (fadeColourGreen - particleGreen) * 0.2F;
				particleBlue += (fadeColourBlue - particleBlue) * 0.2F;
			}
		}

		setParticleTextureIndex(baseTextureIndex + 7 - particleAge * 8 / particleMaxAge);
		motionY -= 0.004D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9100000262260437D;
		motionY *= 0.9100000262260437D;
		motionZ *= 0.9100000262260437D;

		if (onGround) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}

		if (field_92054_ax && particleAge < particleMaxAge / 2 && (particleAge + particleMaxAge) % 2 == 0) {
			final EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(worldObj, posX, posY, posZ, 0.0D, 0.0D, 0.0D,
					field_92047_az);
			var1.setAlphaF(0.99F);
			var1.setRBGColorF(particleRed, particleGreen, particleBlue);
			var1.particleAge = var1.particleMaxAge / 2;

			if (hasFadeColour) {
				var1.hasFadeColour = true;
				var1.fadeColourRed = fadeColourRed;
				var1.fadeColourGreen = fadeColourGreen;
				var1.fadeColourBlue = fadeColourBlue;
			}

			var1.field_92048_ay = field_92048_ay;
			field_92047_az.addEffect(var1);
		}
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return 1.0F;
	}
}
