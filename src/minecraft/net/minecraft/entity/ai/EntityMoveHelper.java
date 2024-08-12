package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;

public class EntityMoveHelper {

public static final int EaZy = 1097;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The EntityLiving that is being moved */
	protected EntityLiving entity;
	protected double posX;
	protected double posY;
	protected double posZ;

	/** The speed at which the entity should move */
	protected double speed;
	protected boolean update;
	// private static final String __OBFID = "http://https://fuckuskid00001573";

	public EntityMoveHelper(final EntityLiving p_i1614_1_) {
		entity = p_i1614_1_;
		posX = p_i1614_1_.posX;
		posY = p_i1614_1_.posY;
		posZ = p_i1614_1_.posZ;
	}

	public boolean isUpdating() {
		return update;
	}

	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed and location to move to
	 */
	public void setMoveTo(final double p_75642_1_, final double p_75642_3_, final double p_75642_5_,
			final double p_75642_7_) {
		posX = p_75642_1_;
		posY = p_75642_3_;
		posZ = p_75642_5_;
		speed = p_75642_7_;
		update = true;
	}

	public void onUpdateMoveHelper() {
		entity.setMoveForward(0.0F);

		if (update) {
			update = false;
			final int var1 = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5D);
			final double var2 = posX - entity.posX;
			final double var4 = posZ - entity.posZ;
			final double var6 = posY - var1;
			final double var8 = var2 * var2 + var6 * var6 + var4 * var4;

			if (var8 >= 2.500000277905201E-7D) {
				final float var10 = (float) (Math.atan2(var4, var2) * 180.0D / Math.PI) - 90.0F;
				entity.rotationYaw = limitAngle(entity.rotationYaw, var10, 30.0F);
				entity.setAIMoveSpeed((float) (speed
						* entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));

				if (var6 > 0.0D && var2 * var2 + var4 * var4 < 1.0D) {
					entity.getJumpHelper().setJumping();
				}
			}
		}
	}

	/**
	 * Limits the given angle to a upper and lower limit.
	 */
	protected float limitAngle(final float p_75639_1_, final float p_75639_2_, final float p_75639_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_75639_2_ - p_75639_1_);

		if (var4 > p_75639_3_) {
			var4 = p_75639_3_;
		}

		if (var4 < -p_75639_3_) {
			var4 = -p_75639_3_;
		}

		float var5 = p_75639_1_ + var4;

		if (var5 < 0.0F) {
			var5 += 360.0F;
		} else if (var5 > 360.0F) {
			var5 -= 360.0F;
		}

		return var5;
	}

	public double func_179917_d() {
		return posX;
	}

	public double func_179919_e() {
		return posY;
	}

	public double func_179918_f() {
		return posZ;
	}
}
