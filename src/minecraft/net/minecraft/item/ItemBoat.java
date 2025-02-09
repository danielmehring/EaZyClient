package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class ItemBoat extends Item {

public static final int EaZy = 1274;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001774";

	public ItemBoat() {
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabTransport);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		final float var4 = 1.0F;
		final float var5 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * var4;
		final float var6 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * var4;
		final double var7 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * var4;
		final double var9 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * var4 + playerIn.getEyeHeight();
		final double var11 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * var4;
		final Vec3 var13 = new Vec3(var7, var9, var11);
		final float var14 = MathHelper.cos(-var6 * 0.017453292F - (float) Math.PI);
		final float var15 = MathHelper.sin(-var6 * 0.017453292F - (float) Math.PI);
		final float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		final float var17 = MathHelper.sin(-var5 * 0.017453292F);
		final float var18 = var15 * var16;
		final float var20 = var14 * var16;
		final double var21 = 5.0D;
		final Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
		final MovingObjectPosition var24 = worldIn.rayTraceBlocks(var13, var23, true);

		if (var24 == null) {
			return itemStackIn;
		} else {
			final Vec3 var25 = playerIn.getLook(var4);
			boolean var26 = false;
			final float var27 = 1.0F;
			final List var28 = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn,
					playerIn.getEntityBoundingBox()
							.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21)
							.expand(var27, var27, var27));

			for (int var29 = 0; var29 < var28.size(); ++var29) {
				final Entity var30 = (Entity) var28.get(var29);

				if (var30.canBeCollidedWith()) {
					final float var31 = var30.getCollisionBorderSize();
					final AxisAlignedBB var32 = var30.getEntityBoundingBox().expand(var31, var31, var31);

					if (var32.isVecInside(var13)) {
						var26 = true;
					}
				}
			}

			if (var26) {
				return itemStackIn;
			} else {
				if (var24.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					BlockPos var33 = var24.getBlockPos();

					if (worldIn.getBlockState(var33).getBlock() == Blocks.snow_layer) {
						var33 = var33.offsetDown();
					}

					final EntityBoat var34 = new EntityBoat(worldIn, var33.getX() + 0.5F, var33.getY() + 1.0F,
							var33.getZ() + 0.5F);
					var34.rotationYaw = ((MathHelper.floor_double(playerIn.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1)
							* 90;

					if (!worldIn
							.getCollidingBoundingBoxes(var34, var34.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D))
							.isEmpty()) {
						return itemStackIn;
					}

					if (!worldIn.isRemote) {
						worldIn.spawnEntityInWorld(var34);
					}

					if (!playerIn.capabilities.isCreativeMode) {
						--itemStackIn.stackSize;
					}

					playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
				}

				return itemStackIn;
			}
		}
	}
}
