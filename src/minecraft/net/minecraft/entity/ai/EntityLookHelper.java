package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityLookHelper {

public static final int EaZy = 1095;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityLiving entity;

	/**
	 * The amount of change that is made each update for an entity facing a
	 * direction.
	 */
	private float deltaLookYaw;

	/**
	 * The amount of change that is made each update for an entity facing a
	 * direction.
	 */
	private float deltaLookPitch;

	/** Whether or not the entity is trying to look at something. */
	private boolean isLooking;
	private double posX;
	private double posY;
	private double posZ;
	// private static final String __OBFID = "http://https://fuckuskid00001572";

	public EntityLookHelper(final EntityLiving p_i1613_1_) {
		entity = p_i1613_1_;
	}

	/**
	 * Sets position to look at using entity
	 */
	public void setLookPositionWithEntity(final Entity p_75651_1_, final float p_75651_2_, final float p_75651_3_) {
		posX = p_75651_1_.posX;

		if (p_75651_1_ instanceof EntityLivingBase) {
			posY = p_75651_1_.posY + p_75651_1_.getEyeHeight();
		} else {
			posY = (p_75651_1_.getEntityBoundingBox().minY + p_75651_1_.getEntityBoundingBox().maxY) / 2.0D;
		}

		posZ = p_75651_1_.posZ;
		deltaLookYaw = p_75651_2_;
		deltaLookPitch = p_75651_3_;
		isLooking = true;
	}

	/**
	 * Sets position to look at
	 */
	public void setLookPosition(final double p_75650_1_, final double p_75650_3_, final double p_75650_5_,
			final float p_75650_7_, final float p_75650_8_) {
		posX = p_75650_1_;
		posY = p_75650_3_;
		posZ = p_75650_5_;
		deltaLookYaw = p_75650_7_;
		deltaLookPitch = p_75650_8_;
		isLooking = true;
	}

	/**
	 * Updates look
	 */
	public void onUpdateLook() {
		entity.rotationPitch = 0.0F;

		if (isLooking) {
			isLooking = false;
			final double var1 = posX - entity.posX;
			final double var3 = posY - (entity.posY + entity.getEyeHeight());
			final double var5 = posZ - entity.posZ;
			final double var7 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
			final float var9 = (float) (Math.atan2(var5, var1) * 180.0D / Math.PI) - 90.0F;
			final float var10 = (float) -(Math.atan2(var3, var7) * 180.0D / Math.PI);
			entity.rotationPitch = updateRotation(entity.rotationPitch, var10, deltaLookPitch);
			entity.rotationYawHead = updateRotation(entity.rotationYawHead, var9, deltaLookYaw);
		} else {
			entity.rotationYawHead = updateRotation(entity.rotationYawHead, entity.renderYawOffset, 10.0F);
		}

		final float var11 = MathHelper.wrapAngleTo180_float(entity.rotationYawHead - entity.renderYawOffset);

		if (!entity.getNavigator().noPath()) {
			if (var11 < -75.0F) {
				entity.rotationYawHead = entity.renderYawOffset - 75.0F;
			}

			if (var11 > 75.0F) {
				entity.rotationYawHead = entity.renderYawOffset + 75.0F;
			}
		}
	}

	private float updateRotation(final float p_75652_1_, final float p_75652_2_, final float p_75652_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);

		if (var4 > p_75652_3_) {
			var4 = p_75652_3_;
		}

		if (var4 < -p_75652_3_) {
			var4 = -p_75652_3_;
		}

		return p_75652_1_ + var4;
	}

	public boolean func_180424_b() {
		return isLooking;
	}

	public double func_180423_e() {
		return posX;
	}

	public double func_180422_f() {
		return posY;
	}

	public double func_180421_g() {
		return posZ;
	}
}
