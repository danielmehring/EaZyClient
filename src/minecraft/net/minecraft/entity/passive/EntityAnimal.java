package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {

public static final int EaZy = 1175;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected Block field_175506_bl;
	private int inLove;
	private EntityPlayer playerInLove;
	// private static final String __OBFID = "http://https://fuckuskid00001638";

	public EntityAnimal(final World worldIn) {
		super(worldIn);
		field_175506_bl = Blocks.grass;
	}

	@Override
	protected void updateAITasks() {
		if (getGrowingAge() != 0) {
			inLove = 0;
		}

		super.updateAITasks();
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (getGrowingAge() != 0) {
			inLove = 0;
		}

		if (inLove > 0) {
			--inLove;

			if (inLove % 10 == 0) {
				final double var1 = rand.nextGaussian() * 0.02D;
				final double var3 = rand.nextGaussian() * 0.02D;
				final double var5 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle(EnumParticleTypes.HEART, posX + rand.nextFloat() * width * 2.0F - width,
						posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var1,
						var3, var5, new int[0]);
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			inLove = 0;
			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public float func_180484_a(final BlockPos p_180484_1_) {
		return worldObj.getBlockState(p_180484_1_.offsetDown()).getBlock() == Blocks.grass ? 10.0F
				: worldObj.getLightBrightness(p_180484_1_) - 0.5F;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("InLove", inLove);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		inLove = tagCompund.getInteger("InLove");
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		final int var1 = MathHelper.floor_double(posX);
		final int var2 = MathHelper.floor_double(getEntityBoundingBox().minY);
		final int var3 = MathHelper.floor_double(posZ);
		final BlockPos var4 = new BlockPos(var1, var2, var3);
		return worldObj.getBlockState(var4.offsetDown()).getBlock() == field_175506_bl && worldObj.getLight(var4) > 8
				&& super.getCanSpawnHere();
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	@Override
	public int getTalkInterval() {
		return 120;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return false;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
		return 1 + worldObj.rand.nextInt(3);
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	public boolean isBreedingItem(final ItemStack p_70877_1_) {
		return p_70877_1_ == null ? false : p_70877_1_.getItem() == Items.wheat;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (var2 != null) {
			if (isBreedingItem(var2) && getGrowingAge() == 0 && inLove <= 0) {
				func_175505_a(p_70085_1_, var2);
				setInLove(p_70085_1_);
				return true;
			}

			if (isChild() && isBreedingItem(var2)) {
				func_175505_a(p_70085_1_, var2);
				func_175501_a((int) (-getGrowingAge() / 20 * 0.1F), true);
				return true;
			}
		}

		return super.interact(p_70085_1_);
	}

	protected void func_175505_a(final EntityPlayer p_175505_1_, final ItemStack p_175505_2_) {
		if (!p_175505_1_.capabilities.isCreativeMode) {
			--p_175505_2_.stackSize;

			if (p_175505_2_.stackSize <= 0) {
				p_175505_1_.inventory.setInventorySlotContents(p_175505_1_.inventory.currentItem, (ItemStack) null);
			}
		}
	}

	public void setInLove(final EntityPlayer p_146082_1_) {
		inLove = 600;
		playerInLove = p_146082_1_;
		worldObj.setEntityState(this, (byte) 18);
	}

	public EntityPlayer func_146083_cb() {
		return playerInLove;
	}

	/**
	 * Returns if the entity is currently in 'love mode'.
	 */
	public boolean isInLove() {
		return inLove > 0;
	}

	public void resetInLove() {
		inLove = 0;
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	public boolean canMateWith(final EntityAnimal p_70878_1_) {
		return p_70878_1_ == this ? false
				: p_70878_1_.getClass() != this.getClass() ? false : isInLove() && p_70878_1_.isInLove();
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 18) {
			for (int var2 = 0; var2 < 7; ++var2) {
				final double var3 = rand.nextGaussian() * 0.02D;
				final double var5 = rand.nextGaussian() * 0.02D;
				final double var7 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle(EnumParticleTypes.HEART, posX + rand.nextFloat() * width * 2.0F - width,
						posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var3,
						var5, var7, new int[0]);
			}
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}
}
