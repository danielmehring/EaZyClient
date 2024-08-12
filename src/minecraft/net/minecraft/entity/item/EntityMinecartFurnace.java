package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {

public static final int EaZy = 1145;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int fuel;
	public double pushX;
	public double pushZ;
	// private static final String __OBFID = "http://https://fuckuskid00001675";

	public EntityMinecartFurnace(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartFurnace(final World worldIn, final double p_i1719_2_, final double p_i1719_4_,
			final double p_i1719_6_) {
		super(worldIn, p_i1719_2_, p_i1719_4_, p_i1719_6_);
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.FURNACE;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (fuel > 0) {
			--fuel;
		}

		if (fuel <= 0) {
			pushX = pushZ = 0.0D;
		}

		setMinecartPowered(fuel > 0);

		if (isMinecartPowered() && rand.nextInt(4) == 0) {
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 0.8D, posZ, 0.0D, 0.0D, 0.0D,
					new int[0]);
		}
	}

	@Override
	protected double func_174898_m() {
		return 0.2D;
	}

	@Override
	public void killMinecart(final DamageSource p_94095_1_) {
		super.killMinecart(p_94095_1_);

		if (!p_94095_1_.isExplosion()) {
			entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
		}
	}

	@Override
	protected void func_180460_a(final BlockPos p_180460_1_, final IBlockState p_180460_2_) {
		super.func_180460_a(p_180460_1_, p_180460_2_);
		double var3 = pushX * pushX + pushZ * pushZ;

		if (var3 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D) {
			var3 = MathHelper.sqrt_double(var3);
			pushX /= var3;
			pushZ /= var3;

			if (pushX * motionX + pushZ * motionZ < 0.0D) {
				pushX = 0.0D;
				pushZ = 0.0D;
			} else {
				final double var5 = var3 / func_174898_m();
				pushX *= var5;
				pushZ *= var5;
			}
		}
	}

	@Override
	protected void applyDrag() {
		double var1 = pushX * pushX + pushZ * pushZ;

		if (var1 > 1.0E-4D) {
			var1 = MathHelper.sqrt_double(var1);
			pushX /= var1;
			pushZ /= var1;
			final double var3 = 1.0D;
			motionX *= 0.800000011920929D;
			motionY *= 0.0D;
			motionZ *= 0.800000011920929D;
			motionX += pushX * var3;
			motionZ += pushZ * var3;
		} else {
			motionX *= 0.9800000190734863D;
			motionY *= 0.0D;
			motionZ *= 0.9800000190734863D;
		}

		super.applyDrag();
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		final ItemStack var2 = playerIn.inventory.getCurrentItem();

		if (var2 != null && var2.getItem() == Items.coal) {
			if (!playerIn.capabilities.isCreativeMode && --var2.stackSize == 0) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
			}

			fuel += 3600;
		}

		pushX = posX - playerIn.posX;
		pushZ = posZ - playerIn.posZ;
		return true;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setDouble("PushX", pushX);
		tagCompound.setDouble("PushZ", pushZ);
		tagCompound.setShort("Fuel", (short) fuel);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		pushX = tagCompund.getDouble("PushX");
		pushZ = tagCompund.getDouble("PushZ");
		fuel = tagCompund.getShort("Fuel");
	}

	protected boolean isMinecartPowered() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	protected void setMinecartPowered(final boolean p_94107_1_) {
		if (p_94107_1_) {
			dataWatcher.updateObject(16, (byte) (dataWatcher.getWatchableObjectByte(16) | 1));
		} else {
			dataWatcher.updateObject(16, (byte) (dataWatcher.getWatchableObjectByte(16) & -2));
		}
	}

	@Override
	public IBlockState func_180457_u() {
		return (isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState()
				.withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
	}
}
