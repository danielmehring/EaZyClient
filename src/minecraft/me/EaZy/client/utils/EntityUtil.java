package me.EaZy.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.Reflector;

public class EntityUtil {

	public static final int EaZy = 209;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static MovingObjectPosition rayTraceBlocks(final double blockReachDistance) {
		final Vec3 var4 = Minecraft.thePlayer.func_174824_e(Minecraft.timer.renderPartialTicks);
		final Vec3 var5 = Minecraft.thePlayer.getLook(Minecraft.timer.renderPartialTicks);
		final Vec3 var6 = var4.addVector(var5.xCoord * blockReachDistance, var5.yCoord * blockReachDistance,
				var5.zCoord * blockReachDistance);
		return Minecraft.theWorld.rayTraceBlocks(var4, var6, false, false, true);
	}

	public static Entity rayTraceEntity(final double blockReachDistance) {
		Entity pointedEntity = null;
		final double reachDistFromPlyController = blockReachDistance;
		final Vec3 var7 = Minecraft.thePlayer.func_174824_e(Minecraft.timer.renderPartialTicks);

		float idk1 = (float) (Minecraft.thePlayer.prevRotationPitch
				+ (Minecraft.thePlayer.rotationPitch - Minecraft.thePlayer.prevRotationPitch)
						* Minecraft.timer.renderPartialTicks);
		final float idk2 = (float) (Minecraft.thePlayer.prevRotationYaw
				+ (Minecraft.thePlayer.rotationYaw - Minecraft.thePlayer.prevRotationYaw)
						* Minecraft.timer.renderPartialTicks);

		if (Minecraft.gameSettings.thirdPersonView == 2) {
			idk1 += 180.0F;
		}

		final float var3 = MathHelper.cos(-idk2 * 0.017453292F - (float) Math.PI);
		final float var4 = MathHelper.sin(-idk2 * 0.017453292F - (float) Math.PI);
		final float var5 = -MathHelper.cos(-idk1 * 0.017453292F);
		final float var6 = MathHelper.sin(-idk1 * 0.017453292F);
		final Vec3 var8 = new Vec3(var4 * var5, var6, var3 * var5);

		final Vec3 var9 = var7.addVector(var8.xCoord * reachDistFromPlyController,
				var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController);
		final float var11 = 1.0F;
		final List var12 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.thePlayer,
				Minecraft.thePlayer
						.getEntityBoundingBox().addCoord(var8.xCoord * reachDistFromPlyController,
								var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController)
						.expand(var11, var11, var11));
		double var13 = reachDistFromPlyController;
		for (int var15 = 0; var15 < var12.size(); ++var15) {
			final Entity var16 = (Entity) var12.get(var15);

			if (var16.canBeCollidedWith()) {
				final float var17 = var16.getCollisionBorderSize();
				final AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
				final MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);

				if (var18.isVecInside(var7)) {
					if (0.0D < var13 || var13 == 0.0D) {
						pointedEntity = var16;
						var13 = 0.0D;
					}
				} else if (var19 != null) {
					final double var20 = var7.distanceTo(var19.hitVec);

					if (var20 < var13 || var13 == 0.0D) {
						boolean canRiderInteract = false;

						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							canRiderInteract = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract,
									new Object[0]);
						}

						if (var16 == Minecraft.thePlayer.ridingEntity && !canRiderInteract) {
							if (var13 == 0.0D) {
								pointedEntity = var16;
							}
						} else {
							pointedEntity = var16;
							var13 = var20;
						}
					}
				}
			}
		}

		if (pointedEntity != null && (var13 < reachDistFromPlyController)) {
			if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
				return pointedEntity;
			}
		}
		return null;
	}

	public static double[] getEntityRenderPos(final Entity e) {
		final float p_147936_2_ = Minecraft.timer.renderPartialTicks;
		final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * p_147936_2_
				- Minecraft.getRenderManager().viewerPosX;
		final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * p_147936_2_
				- Minecraft.getRenderManager().viewerPosY;
		final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * p_147936_2_
				- Minecraft.getRenderManager().viewerPosZ;
		return new double[] { x, y, z };
	}

}
