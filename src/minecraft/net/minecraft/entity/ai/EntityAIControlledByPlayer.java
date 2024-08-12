package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class EntityAIControlledByPlayer extends EntityAIBase {

public static final int EaZy = 1048;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityLiving thisEntity;
	private final float maxSpeed;
	private float currentSpeed;

	/** Whether the entity's speed is boosted. */
	private boolean speedBoosted;

	/**
	 * Counter for speed boosting, upon reaching maxSpeedBoostTime the speed
	 * boost will be disabled
	 */
	private int speedBoostTime;

	/** Maximum time the entity's speed should be boosted for. */
	private int maxSpeedBoostTime;
	// private static final String __OBFID = "http://https://fuckuskid00001580";

	public EntityAIControlledByPlayer(final EntityLiving p_i1620_1_, final float p_i1620_2_) {
		thisEntity = p_i1620_1_;
		maxSpeed = p_i1620_2_;
		setMutexBits(7);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		currentSpeed = 0.0F;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		speedBoosted = false;
		currentSpeed = 0.0F;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return thisEntity.isEntityAlive() && thisEntity.riddenByEntity != null
				&& thisEntity.riddenByEntity instanceof EntityPlayer && (speedBoosted || thisEntity.canBeSteered());
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final EntityPlayer var1 = (EntityPlayer) thisEntity.riddenByEntity;
		final EntityCreature var2 = (EntityCreature) thisEntity;
		float var3 = MathHelper.wrapAngleTo180_float(var1.rotationYaw - thisEntity.rotationYaw) * 0.5F;

		if (var3 > 5.0F) {
			var3 = 5.0F;
		}

		if (var3 < -5.0F) {
			var3 = -5.0F;
		}

		thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(thisEntity.rotationYaw + var3);

		if (currentSpeed < maxSpeed) {
			currentSpeed += (maxSpeed - currentSpeed) * 0.01F;
		}

		if (currentSpeed > maxSpeed) {
			currentSpeed = maxSpeed;
		}

		final int var4 = MathHelper.floor_double(thisEntity.posX);
		final int var5 = MathHelper.floor_double(thisEntity.posY);
		final int var6 = MathHelper.floor_double(thisEntity.posZ);
		float var7 = currentSpeed;

		if (speedBoosted) {
			if (speedBoostTime++ > maxSpeedBoostTime) {
				speedBoosted = false;
			}

			var7 += var7 * 1.15F * MathHelper.sin((float) speedBoostTime / (float) maxSpeedBoostTime * (float) Math.PI);
		}

		float var8 = 0.91F;

		if (thisEntity.onGround) {
			var8 = thisEntity.worldObj.getBlockState(new BlockPos(MathHelper.floor_float(var4),
					MathHelper.floor_float(var5) - 1, MathHelper.floor_float(var6))).getBlock().slipperiness * 0.91F;
		}

		final float var9 = 0.16277136F / (var8 * var8 * var8);
		final float var10 = MathHelper.sin(var2.rotationYaw * (float) Math.PI / 180.0F);
		final float var11 = MathHelper.cos(var2.rotationYaw * (float) Math.PI / 180.0F);
		final float var12 = var2.getAIMoveSpeed() * var9;
		float var13 = Math.max(var7, 1.0F);
		var13 = var12 / var13;
		final float var14 = var7 * var13;
		float var15 = -(var14 * var10);
		float var16 = var14 * var11;

		if (MathHelper.abs(var15) > MathHelper.abs(var16)) {
			if (var15 < 0.0F) {
				var15 -= thisEntity.width / 2.0F;
			}

			if (var15 > 0.0F) {
				var15 += thisEntity.width / 2.0F;
			}

			var16 = 0.0F;
		} else {
			var15 = 0.0F;

			if (var16 < 0.0F) {
				var16 -= thisEntity.width / 2.0F;
			}

			if (var16 > 0.0F) {
				var16 += thisEntity.width / 2.0F;
			}
		}

		final int var17 = MathHelper.floor_double(thisEntity.posX + var15);
		final int var18 = MathHelper.floor_double(thisEntity.posZ + var16);
		final int var19 = MathHelper.floor_float(thisEntity.width + 1.0F);
		final int var20 = MathHelper.floor_float(thisEntity.height + var1.height + 1.0F);
		final int var21 = MathHelper.floor_float(thisEntity.width + 1.0F);

		if (var4 != var17 || var6 != var18) {
			final Block var22 = thisEntity.worldObj.getBlockState(new BlockPos(var4, var5, var6)).getBlock();
			final boolean var23 = !isStairOrSlab(var22) && (var22.getMaterial() != Material.air || !isStairOrSlab(
					thisEntity.worldObj.getBlockState(new BlockPos(var4, var5 - 1, var6)).getBlock()));

			if (var23
					&& 0 == WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var17, var5, var18, var19,
							var20, var21, false, false, true)
					&& 1 == WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var4, var5 + 1, var6,
							var19, var20, var21, false, false, true)
					&& 1 == WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var17, var5 + 1, var18,
							var19, var20, var21, false, false, true)) {
				var2.getJumpHelper().setJumping();
			}
		}

		if (!var1.capabilities.isCreativeMode && currentSpeed >= maxSpeed * 0.5F
				&& thisEntity.getRNG().nextFloat() < 0.006F && !speedBoosted) {
			final ItemStack var24 = var1.getHeldItem();

			if (var24 != null && var24.getItem() == Items.carrot_on_a_stick) {
				var24.damageItem(1, var1);

				if (var24.stackSize == 0) {
					final ItemStack var25 = new ItemStack(Items.fishing_rod);
					var25.setTagCompound(var24.getTagCompound());
					var1.inventory.mainInventory[var1.inventory.currentItem] = var25;
				}
			}
		}

		thisEntity.moveEntityWithHeading(0.0F, var7);
	}

	/**
	 * True if the block is a stair block or a slab block
	 */
	private boolean isStairOrSlab(final Block p_151498_1_) {
		return p_151498_1_ instanceof BlockStairs || p_151498_1_ instanceof BlockSlab;
	}

	/**
	 * Return whether the entity's speed is boosted.
	 */
	public boolean isSpeedBoosted() {
		return speedBoosted;
	}

	/**
	 * Boost the entity's movement speed.
	 */
	public void boostSpeed() {
		speedBoosted = true;
		speedBoostTime = 0;
		maxSpeedBoostTime = thisEntity.getRNG().nextInt(841) + 140;
	}

	/**
	 * Return whether the entity is being controlled by a player.
	 */
	public boolean isControlledByPlayer() {
		return !isSpeedBoosted() && currentSpeed > maxSpeed * 0.3F;
	}
}
