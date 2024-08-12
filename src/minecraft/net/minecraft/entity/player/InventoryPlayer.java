package net.minecraft.entity.player;

import net.minecraft.block.Block;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;

import java.util.concurrent.Callable;

public class InventoryPlayer implements IInventory {

	public static final int EaZy = 1194;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/**
	 * An array of 36 item stacks indicating the main player inventory
	 * (including the visible bar).
	 */
	public ItemStack[] mainInventory = new ItemStack[36];

	/** An array of 4 item stacks containing the currently worn armor pieces. */
	public ItemStack[] armorInventory = new ItemStack[4];

	/** The index of the currently held item (0-8). */
	public int currentItem;

	/** The player whose inventory this is. */
	public EntityPlayer player;
	private ItemStack itemStack;

	/**
	 * Set true whenever the inventory changes. Nothing sets it false so you
	 * will have to write your own code to check it and reset the value.
	 */
	public boolean inventoryChanged;
	// private static final String __OBFID = "http://https://fuckuskid00001709";

	public InventoryPlayer(final EntityPlayer p_i1750_1_) {
		player = p_i1750_1_;
	}

	/**
	 * Returns the item stack currently held by the player.
	 */
	public ItemStack getCurrentItem() {
		return currentItem < 9 && currentItem >= 0 ? mainInventory[currentItem] : null;
	}

	/**
	 * Get the size of the player hotbar inventory
	 */
	public static int getHotbarSize() {
		return 9;
	}

	private int getInventorySlotContainItem(final Item itemIn) {
		for (int var2 = 0; var2 < mainInventory.length; ++var2) {
			if (mainInventory[var2] != null && mainInventory[var2].getItem() == itemIn) {
				return var2;
			}
		}

		return -1;
	}

	private int getInventorySlotContainItemAndDamage(final Item p_146024_1_, final int p_146024_2_) {
		for (int var3 = 0; var3 < mainInventory.length; ++var3) {
			if (mainInventory[var3] != null && mainInventory[var3].getItem() == p_146024_1_
					&& mainInventory[var3].getMetadata() == p_146024_2_) {
				return var3;
			}
		}

		return -1;
	}

	/**
	 * stores an itemstack in the users inventory
	 */
	private int storeItemStack(final ItemStack p_70432_1_) {
		for (int var2 = 0; var2 < mainInventory.length; ++var2) {
			if (mainInventory[var2] != null && mainInventory[var2].getItem() == p_70432_1_.getItem()
					&& mainInventory[var2].isStackable()
					&& mainInventory[var2].stackSize < mainInventory[var2].getMaxStackSize()
					&& mainInventory[var2].stackSize < getInventoryStackLimit()
					&& (!mainInventory[var2].getHasSubtypes()
							|| mainInventory[var2].getMetadata() == p_70432_1_.getMetadata())
					&& ItemStack.areItemStackTagsEqual(mainInventory[var2], p_70432_1_)) {
				return var2;
			}
		}

		return -1;
	}

	/**
	 * Returns the first item stack that is empty.
	 */
	public int getFirstEmptyStack() {
		for (int var1 = 0; var1 < mainInventory.length; ++var1) {
			if (mainInventory[var1] == null) {
				return var1;
			}
		}

		return -1;
	}

	/**
	 * Self coded, returns with +36
	 * @return
	 */
	public int getFirstEmptyStackReal() {
		for (int var1 = 0; var1 < mainInventory.length; ++var1) {
			if (mainInventory[var1] == null) {
				if (var1 < 9) {
					var1 += 36;
				}
				
				return var1;
			}
		}

		return -1;
	}

	public void setCurrentItem(final Item p_146030_1_, final int p_146030_2_, final boolean p_146030_3_,
			final boolean p_146030_4_) {
		final ItemStack var5 = getCurrentItem();
		final int var6 = p_146030_3_ ? getInventorySlotContainItemAndDamage(p_146030_1_, p_146030_2_)
				: getInventorySlotContainItem(p_146030_1_);

		if (var6 >= 0 && var6 < 9) {
			currentItem = var6;
		} else if (p_146030_4_ && p_146030_1_ != null) {
			final int var7 = getFirstEmptyStack();

			if (var7 >= 0 && var7 < 9) {
				currentItem = var7;
			}

			if (var5 == null || !var5.isItemEnchantable()
					|| getInventorySlotContainItemAndDamage(var5.getItem(), var5.getItemDamage()) != currentItem) {
				final int var8 = getInventorySlotContainItemAndDamage(p_146030_1_, p_146030_2_);
				int var9;

				if (var8 >= 0) {
					var9 = mainInventory[var8].stackSize;
					mainInventory[var8] = mainInventory[currentItem];
				} else {
					var9 = 1;
				}

				mainInventory[currentItem] = new ItemStack(p_146030_1_, var9, p_146030_2_);
			}
		}
	}

	/**
	 * Switch the current item to the next one or the previous one
	 */
	public void changeCurrentItem(int p_70453_1_) {
		if (p_70453_1_ > 0) {
			p_70453_1_ = 1;
		}

		if (p_70453_1_ < 0) {
			p_70453_1_ = -1;
		}

		for (currentItem -= p_70453_1_; currentItem < 0; currentItem += 9) {}

		while (currentItem >= 9) {
			currentItem -= 9;
		}
	}

	public int func_174925_a(final Item p_174925_1_, final int p_174925_2_, final int p_174925_3_,
			final NBTTagCompound p_174925_4_) {
		int var5 = 0;
		int var6;
		ItemStack var7;
		int var8;

		for (var6 = 0; var6 < mainInventory.length; ++var6) {
			var7 = mainInventory[var6];

			if (var7 != null && (p_174925_1_ == null || var7.getItem() == p_174925_1_)
					&& (p_174925_2_ <= -1 || var7.getMetadata() == p_174925_2_) && (p_174925_4_ == null
							|| CommandTestForBlock.func_175775_a(p_174925_4_, var7.getTagCompound(), true))) {
				var8 = p_174925_3_ <= 0 ? var7.stackSize : Math.min(p_174925_3_ - var5, var7.stackSize);
				var5 += var8;

				if (p_174925_3_ != 0) {
					mainInventory[var6].stackSize -= var8;

					if (mainInventory[var6].stackSize == 0) {
						mainInventory[var6] = null;
					}

					if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
						return var5;
					}
				}
			}
		}

		for (var6 = 0; var6 < armorInventory.length; ++var6) {
			var7 = armorInventory[var6];

			if (var7 != null && (p_174925_1_ == null || var7.getItem() == p_174925_1_)
					&& (p_174925_2_ <= -1 || var7.getMetadata() == p_174925_2_) && (p_174925_4_ == null
							|| CommandTestForBlock.func_175775_a(p_174925_4_, var7.getTagCompound(), false))) {
				var8 = p_174925_3_ <= 0 ? var7.stackSize : Math.min(p_174925_3_ - var5, var7.stackSize);
				var5 += var8;

				if (p_174925_3_ != 0) {
					armorInventory[var6].stackSize -= var8;

					if (armorInventory[var6].stackSize == 0) {
						armorInventory[var6] = null;
					}

					if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
						return var5;
					}
				}
			}
		}

		if (itemStack != null) {
			if (p_174925_1_ != null && itemStack.getItem() != p_174925_1_) {
				return var5;
			}

			if (p_174925_2_ > -1 && itemStack.getMetadata() != p_174925_2_) {
				return var5;
			}

			if (p_174925_4_ != null
					&& !CommandTestForBlock.func_175775_a(p_174925_4_, itemStack.getTagCompound(), false)) {
				return var5;
			}

			var6 = p_174925_3_ <= 0 ? itemStack.stackSize : Math.min(p_174925_3_ - var5, itemStack.stackSize);
			var5 += var6;

			if (p_174925_3_ != 0) {
				itemStack.stackSize -= var6;

				if (itemStack.stackSize == 0) {
					itemStack = null;
				}

				if (p_174925_3_ > 0 && var5 >= p_174925_3_) {
					return var5;
				}
			}
		}

		return var5;
	}

	/**
	 * This function stores as many items of an ItemStack as possible in a
	 * matching slot and returns the quantity of left over items.
	 */
	private int storePartialItemStack(final ItemStack p_70452_1_) {
		final Item var2 = p_70452_1_.getItem();
		int var3 = p_70452_1_.stackSize;
		int var4 = storeItemStack(p_70452_1_);

		if (var4 < 0) {
			var4 = getFirstEmptyStack();
		}

		if (var4 < 0) {
			return var3;
		} else {
			if (mainInventory[var4] == null) {
				mainInventory[var4] = new ItemStack(var2, 0, p_70452_1_.getMetadata());

				if (p_70452_1_.hasTagCompound()) {
					mainInventory[var4].setTagCompound((NBTTagCompound) p_70452_1_.getTagCompound().copy());
				}
			}

			int var5 = var3;

			if (var3 > mainInventory[var4].getMaxStackSize() - mainInventory[var4].stackSize) {
				var5 = mainInventory[var4].getMaxStackSize() - mainInventory[var4].stackSize;
			}

			if (var5 > getInventoryStackLimit() - mainInventory[var4].stackSize) {
				var5 = getInventoryStackLimit() - mainInventory[var4].stackSize;
			}

			if (var5 == 0) {
				return var3;
			} else {
				var3 -= var5;
				mainInventory[var4].stackSize += var5;
				mainInventory[var4].animationsToGo = 5;
				return var3;
			}
		}
	}

	/**
	 * Decrement the number of animations remaining. Only called on client side.
	 * This is used to handle the animation of receiving a block.
	 */
	public void decrementAnimations() {
		for (int var1 = 0; var1 < mainInventory.length; ++var1) {
			if (mainInventory[var1] != null) {
				mainInventory[var1].updateAnimation(player.worldObj, player, var1, currentItem == var1);
			}
		}
	}

	/**
	 * removed one item of specified Item from inventory (if it is in a stack,
	 * the stack size will reduce with 1)
	 */
	public boolean consumeInventoryItem(final Item p_146026_1_) {
		final int var2 = getInventorySlotContainItem(p_146026_1_);

		if (var2 < 0) {
			return false;
		} else {
			if (--mainInventory[var2].stackSize <= 0) {
				mainInventory[var2] = null;
			}

			return true;
		}
	}

	/**
	 * Checks if a specified Item is inside the inventory
	 */
	public boolean hasItem(final Item p_146028_1_) {
		final int var2 = getInventorySlotContainItem(p_146028_1_);
		return var2 >= 0;
	}

	/**
	 * Adds the item stack to the inventory, returns false if it is impossible.
	 */
	public boolean addItemStackToInventory(final ItemStack p_70441_1_) {
		if (p_70441_1_ != null && p_70441_1_.stackSize != 0 && p_70441_1_.getItem() != null) {
			try {
				int var2;

				if (p_70441_1_.isItemDamaged()) {
					var2 = getFirstEmptyStack();

					if (var2 >= 0) {
						mainInventory[var2] = ItemStack.copyItemStack(p_70441_1_);
						mainInventory[var2].animationsToGo = 5;
						p_70441_1_.stackSize = 0;
						return true;
					} else if (player.capabilities.isCreativeMode) {
						p_70441_1_.stackSize = 0;
						return true;
					} else {
						return false;
					}
				} else {
					do {
						var2 = p_70441_1_.stackSize;
						p_70441_1_.stackSize = storePartialItemStack(p_70441_1_);
					}
					while (p_70441_1_.stackSize > 0 && p_70441_1_.stackSize < var2);

					if (p_70441_1_.stackSize == var2 && player.capabilities.isCreativeMode) {
						p_70441_1_.stackSize = 0;
						return true;
					} else {
						return p_70441_1_.stackSize < var2;
					}
				}
			} catch (final Throwable var5) {
				final CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
				final CrashReportCategory var4 = var3.makeCategory("Item being added");
				var4.addCrashSection("Item ID", Item.getIdFromItem(p_70441_1_.getItem()));
				var4.addCrashSection("Item data", p_70441_1_.getMetadata());
				var4.addCrashSectionCallable("Item name", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001710";
					@Override
					public String call() {
						return p_70441_1_.getDisplayName();
					}
				});
				throw new ReportedException(var3);
			}
		} else {
			return false;
		}
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, final int count) {
		ItemStack[] var3 = mainInventory;

		if (index >= mainInventory.length) {
			var3 = armorInventory;
			index -= mainInventory.length;
		}

		if (var3[index] != null) {
			ItemStack var4;

			if (var3[index].stackSize <= count) {
				var4 = var3[index];
				var3[index] = null;
				return var4;
			} else {
				var4 = var3[index].splitStack(count);

				if (var3[index].stackSize == 0) {
					var3[index] = null;
				}

				return var4;
			}
		} else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack[] var2 = mainInventory;

		if (index >= mainInventory.length) {
			var2 = armorInventory;
			index -= mainInventory.length;
		}

		if (var2[index] != null) {
			final ItemStack var3 = var2[index];
			var2[index] = null;
			return var3;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, final ItemStack stack) {
		ItemStack[] var3 = mainInventory;

		if (index >= var3.length) {
			index -= var3.length;
			var3 = armorInventory;
		}

		var3[index] = stack;
	}

	public float getStrVsBlock(final Block p_146023_1_) {
		float var2 = 1.0F;

		if (mainInventory[currentItem] != null) {
			var2 *= mainInventory[currentItem].getStrVsBlock(p_146023_1_);
		}

		return var2;
	}

	/**
	 * Writes the inventory out as a list of compound tags. This is where the
	 * slot indices are used (+100 for armor, +80 for crafting).
	 */
	public NBTTagList writeToNBT(final NBTTagList p_70442_1_) {
		int var2;
		NBTTagCompound var3;

		for (var2 = 0; var2 < mainInventory.length; ++var2) {
			if (mainInventory[var2] != null) {
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte) var2);
				mainInventory[var2].writeToNBT(var3);
				p_70442_1_.appendTag(var3);
			}
		}

		for (var2 = 0; var2 < armorInventory.length; ++var2) {
			if (armorInventory[var2] != null) {
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte) (var2 + 100));
				armorInventory[var2].writeToNBT(var3);
				p_70442_1_.appendTag(var3);
			}
		}

		return p_70442_1_;
	}

	/**
	 * Reads from the given tag list and fills the slots in the inventory with
	 * the correct items.
	 */
	public void readFromNBT(final NBTTagList p_70443_1_) {
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[4];

		for (int var2 = 0; var2 < p_70443_1_.tagCount(); ++var2) {
			final NBTTagCompound var3 = p_70443_1_.getCompoundTagAt(var2);
			final int var4 = var3.getByte("Slot") & 255;
			final ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);

			if (var5 != null) {
				if (var4 >= 0 && var4 < mainInventory.length) {
					mainInventory[var4] = var5;
				}

				if (var4 >= 100 && var4 < armorInventory.length + 100) {
					armorInventory[var4 - 100] = var5;
				}
			}
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return mainInventory.length + 4;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int slotIn) {
		ItemStack[] var2 = mainInventory;

		if (slotIn >= var2.length) {
			slotIn -= var2.length;
			var2 = armorInventory;
		}

		return var2[slotIn];
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return "container.inventory";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName())
				: new ChatComponentTranslation(getName(), new Object[0]);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended. *Isn't this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean func_146025_b(final Block p_146025_1_) {
		if (p_146025_1_.getMaterial().isToolNotRequired()) {
			return true;
		} else {
			final ItemStack var2 = getStackInSlot(currentItem);
			return var2 != null ? var2.canHarvestBlock(p_146025_1_) : false;
		}
	}

	/**
	 * returns a player armor item (as itemstack) contained in specified armor
	 * slot.
	 */
	public ItemStack armorItemInSlot(final int p_70440_1_) {
		return armorInventory[p_70440_1_];
	}

	/**
	 * Based on the damage values and maximum damage values of each armor item,
	 * returns the current armor value.
	 */
	public int getTotalArmorValue() {
		int var1 = 0;

		for (final ItemStack element : armorInventory) {
			if (element != null && element.getItem() instanceof ItemArmor) {
				final int var3 = ((ItemArmor) element.getItem()).damageReduceAmount;
				var1 += var3;
			}
		}

		return var1;
	}

	/**
	 * Damages armor in each slot by the specified amount.
	 */
	public void damageArmor(float p_70449_1_) {
		p_70449_1_ /= 4.0F;

		if (p_70449_1_ < 1.0F) {
			p_70449_1_ = 1.0F;
		}

		for (int var2 = 0; var2 < armorInventory.length; ++var2) {
			if (armorInventory[var2] != null && armorInventory[var2].getItem() instanceof ItemArmor) {
				armorInventory[var2].damageItem((int) p_70449_1_, player);

				if (armorInventory[var2].stackSize == 0) {
					armorInventory[var2] = null;
				}
			}
		}
	}

	/**
	 * Drop all armor and main inventory items.
	 */
	public void dropAllItems() {
		int var1;

		for (var1 = 0; var1 < mainInventory.length; ++var1) {
			if (mainInventory[var1] != null) {
				player.func_146097_a(mainInventory[var1], true, false);
				mainInventory[var1] = null;
			}
		}

		for (var1 = 0; var1 < armorInventory.length; ++var1) {
			if (armorInventory[var1] != null) {
				player.func_146097_a(armorInventory[var1], true, false);
				armorInventory[var1] = null;
			}
		}
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {
		inventoryChanged = true;
	}

	/**
	 * Set the stack helds by mouse, used in GUI/Container
	 */
	public void setItemStack(final ItemStack p_70437_1_) {
		itemStack = p_70437_1_;
	}

	/**
	 * Stack helds by mouse, used in GUI and Containers
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(final EntityPlayer playerIn) {
		return player.isDead ? false : playerIn.getDistanceSqToEntity(player) <= 64.0D;
	}

	/**
	 * Returns true if the specified ItemStack exists in the inventory.
	 */
	public boolean hasItemStack(final ItemStack p_70431_1_) {
		int var2;

		for (var2 = 0; var2 < armorInventory.length; ++var2) {
			if (armorInventory[var2] != null && armorInventory[var2].isItemEqual(p_70431_1_)) {
				return true;
			}
		}

		for (var2 = 0; var2 < mainInventory.length; ++var2) {
			if (mainInventory[var2] != null && mainInventory[var2].isItemEqual(p_70431_1_)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void openInventory(final EntityPlayer playerIn) {}

	@Override
	public void closeInventory(final EntityPlayer playerIn) {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		return true;
	}

	/**
	 * Copy the ItemStack contents from another InventoryPlayer instance
	 */
	public void copyInventory(final InventoryPlayer p_70455_1_) {
		int var2;

		for (var2 = 0; var2 < mainInventory.length; ++var2) {
			mainInventory[var2] = ItemStack.copyItemStack(p_70455_1_.mainInventory[var2]);
		}

		for (var2 = 0; var2 < armorInventory.length; ++var2) {
			armorInventory[var2] = ItemStack.copyItemStack(p_70455_1_.armorInventory[var2]);
		}

		currentItem = p_70455_1_.currentItem;
	}

	@Override
	public int getField(final int id) {
		return 0;
	}

	@Override
	public void setField(final int id, final int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clearInventory() {
		int var1;

		for (var1 = 0; var1 < mainInventory.length; ++var1) {
			mainInventory[var1] = null;
		}

		for (var1 = 0; var1 < armorInventory.length; ++var1) {
			armorInventory[var1] = null;
		}
	}
}
