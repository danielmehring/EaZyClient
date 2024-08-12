package net.minecraft.entity.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {

public static final int EaZy = 1146;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Whether this hopper minecart is being blocked by an activator rail. */
	private boolean isBlocked = true;
	private int transferTicker = -1;
	private final BlockPos field_174900_c;
	// private static final String __OBFID = "http://https://fuckuskid00001676";

	public EntityMinecartHopper(final World worldIn) {
		super(worldIn);
		field_174900_c = BlockPos.ORIGIN;
	}

	public EntityMinecartHopper(final World worldIn, final double p_i1721_2_, final double p_i1721_4_,
			final double p_i1721_6_) {
		super(worldIn, p_i1721_2_, p_i1721_4_, p_i1721_6_);
		field_174900_c = BlockPos.ORIGIN;
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.HOPPER;
	}

	@Override
	public IBlockState func_180457_u() {
		return Blocks.hopper.getDefaultState();
	}

	@Override
	public int getDefaultDisplayTileOffset() {
		return 1;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 5;
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		if (!worldObj.isRemote) {
			playerIn.displayGUIChest(this);
		}

		return true;
	}

	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is
	 * the rail receiving power
	 */
	@Override
	public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_,
			final boolean p_96095_4_) {
		final boolean var5 = !p_96095_4_;

		if (var5 != getBlocked()) {
			setBlocked(var5);
		}
	}

	/**
	 * Get whether this hopper minecart is being blocked by an activator rail.
	 */
	public boolean getBlocked() {
		return isBlocked;
	}

	/**
	 * Set whether this hopper minecart is being blocked by an activator rail.
	 */
	public void setBlocked(final boolean p_96110_1_) {
		isBlocked = p_96110_1_;
	}

	/**
	 * Returns the worldObj for this tileEntity.
	 */
	@Override
	public World getWorld() {
		return worldObj;
	}

	/**
	 * Gets the world X position for this hopper entity.
	 */
	@Override
	public double getXPos() {
		return posX;
	}

	/**
	 * Gets the world Y position for this hopper entity.
	 */
	@Override
	public double getYPos() {
		return posY;
	}

	/**
	 * Gets the world Z position for this hopper entity.
	 */
	@Override
	public double getZPos() {
		return posZ;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote && isEntityAlive() && getBlocked()) {
			final BlockPos var1 = new BlockPos(this);

			if (var1.equals(field_174900_c)) {
				--transferTicker;
			} else {
				setTransferTicker(0);
			}

			if (!canTransfer()) {
				setTransferTicker(0);

				if (func_96112_aD()) {
					setTransferTicker(4);
					markDirty();
				}
			}
		}
	}

	public boolean func_96112_aD() {
		if (TileEntityHopper.func_145891_a(this)) {
			return true;
		} else {
			final List var1 = worldObj.func_175647_a(EntityItem.class,
					getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), IEntitySelector.selectAnything);

			if (var1.size() > 0) {
				TileEntityHopper.func_145898_a(this, (EntityItem) var1.get(0));
			}

			return false;
		}
	}

	@Override
	public void killMinecart(final DamageSource p_94095_1_) {
		super.killMinecart(p_94095_1_);
		dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0F);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("TransferCooldown", transferTicker);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		transferTicker = tagCompund.getInteger("TransferCooldown");
	}

	/**
	 * Sets the transfer ticker, used to determine the delay between transfers.
	 */
	public void setTransferTicker(final int p_98042_1_) {
		transferTicker = p_98042_1_;
	}

	/**
	 * Returns whether the hopper cart can currently transfer an item.
	 */
	public boolean canTransfer() {
		return transferTicker > 0;
	}

	@Override
	public String getGuiID() {
		return "minecraft:hopper";
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
		return new ContainerHopper(playerInventory, this, playerIn);
	}
}
