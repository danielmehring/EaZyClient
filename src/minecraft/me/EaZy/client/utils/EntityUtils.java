package me.EaZy.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class EntityUtils {

	public static final int EaZy = 210;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public static boolean lookChanged;
	public static boolean changedlookChanged = false;
	public static float yaw;
	public static float pitch;
	public static float prevYaw;
	public static float prevPitch;

	public static BlockPos getBlockPosReallyBeneathEntity(final EntityLivingBase entity) {
		return new BlockPos(entity.posX, entity.posY, entity.posZ).offsetDown();
	}

	public static void setLookChanged(final boolean changed) {
		if (lookChanged && !changed) {
			lookChanged = changed;
			changedlookChanged = true;
		} else if (!lookChanged && changed) {
			lookChanged = changed;
			changedlookChanged = true;
		}
	}

	public static float[] getRotations(final Entity ent) {
		final double x2 = ent.posX;
		final double z2 = ent.posZ;
		final double y2 = ent.boundingBox.maxY - 4.5;
		return EntityUtils.getRotationFromPosition(x2, z2, y2);
	}

	private static float[] getRotationFromPosition(final double x2, final double z2, final double y2) {
		final double xDiff = x2 - Minecraft.thePlayer.posX;
		final double zDiff = z2 - Minecraft.thePlayer.posZ;
		final double yDiff = y2 - Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight();
		final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		final float _yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
		final float _pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
		return new float[] { _yaw, _pitch };
	}

	public static float[] getAnglesToEntity(final Entity temp) {
		final double posX = temp.posX - Minecraft.thePlayer.posX;
		final double posY = temp.posY + temp.getEyeHeight()
				- (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
		final double posZ = temp.posZ - Minecraft.thePlayer.posZ;
		final double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		final float _yaw = (float) (Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
		final float _pitch = (float) (-Math.atan2(posY, var14) * 180.0 / 3.141592653589793);
		return new float[] { _yaw, _pitch };
	}

	public static synchronized void faceEntityPacket(final EntityLivingBase entity) {
		final float[] rotations = EntityUtils.getRotationsNeeded(entity);
		if (rotations != null) {
			EntityUtils.prevPitch = EntityUtils.pitch;
			EntityUtils.prevYaw = EntityUtils.yaw;
			yaw = rotations[0];
			pitch = rotations[1];
			setLookChanged(true);
		}
	}

	public static void faceEntityPacket(final EntityLivingBase entity, final float yawChange, final float pitchChange) {
		final float[] rotations = EntityUtils.getRotationsNeeded(entity);
		if (rotations != null) {
			EntityUtils.prevPitch = EntityUtils.pitch;
			EntityUtils.prevYaw = EntityUtils.yaw;
			yaw = limitAngleChange(Minecraft.thePlayer.prevRotationYaw, rotations[0], yawChange);
			pitch = limitAngleChange(Minecraft.thePlayer.prevCameraPitch, rotations[1], pitchChange);
			setLookChanged(true);
		}
	}

	public static void facePacket(final float yaw, final float pitch) {
		EntityUtils.prevPitch = EntityUtils.pitch;
		EntityUtils.prevYaw = EntityUtils.yaw;
		EntityUtils.yaw = yaw;
		EntityUtils.pitch = pitch;
		setLookChanged(true);
	}

	public static float[] getRotationsNeeded(final Entity entity) {
		double diffY;
		if (entity == null) {
			return null;
		}

		final double diffX = entity.posX - Minecraft.thePlayer.posX;
		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9
					- (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
		} else {

			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0
					- (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
		}

		final double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);

		return new float[] {
				Minecraft.thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw),
				Minecraft.thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch) };
	}

	public static float limitAngleChange(final float current, final float intended, final float maxChange) {
		float change = intended - current;
		if (change > maxChange) {
			change = maxChange;
		} else if (change < -maxChange) {
			change = -maxChange;
		}
		return current + change;
	}

	public static final boolean isinAngle(final float current, final float intended, final float maxChange) {
		final float change = intended - current;
		if (change > maxChange) {
			return false;
		} else if (change < -maxChange) {
			return false;
		}
		return true;
	}

	public static EntityLivingBase getClosestPlayerNotFriended() {
		try {
			EntityLivingBase closestEntity = null;

			for (final Object o : Minecraft.theWorld.loadedEntityList) {
				if (!(o instanceof EntityPlayer)) {
					continue;
				}
				final EntityPlayer en = (EntityPlayer) o;

				if (o instanceof EntityPlayerSP || en.isDead || en.getHealth() <= 0.0f
						|| !Minecraft.thePlayer.canEntityBeSeen(en)
						|| en.getName().equals(Minecraft.thePlayer.getName())
						|| closestEntity != null && Minecraft.thePlayer.getDistanceToEntity(en) >= Minecraft.thePlayer
								.getDistanceToEntity(closestEntity)
						|| Friends.contains(en)) {
					continue;
				}
				closestEntity = en;
			}
			return closestEntity;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
