package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEnderCrystal extends Entity {

public static final int EaZy = 1133;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Used to create the rotation animation when rendering the crystal. */
	public int innerRotation;
	public int health;
	// private static final String __OBFID = "http://https://fuckuskid00001658";

	public EntityEnderCrystal(final World worldIn) {
		super(worldIn);
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
		health = 5;
		innerRotation = rand.nextInt(100000);
	}

	public EntityEnderCrystal(final World worldIn, final double p_i1699_2_, final double p_i1699_4_,
			final double p_i1699_6_) {
		this(worldIn);
		setPosition(p_i1699_2_, p_i1699_4_, p_i1699_6_);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(8, health);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++innerRotation;
		dataWatcher.updateObject(8, health);
		final int var1 = MathHelper.floor_double(posX);
		final int var2 = MathHelper.floor_double(posY);
		final int var3 = MathHelper.floor_double(posZ);

		if (worldObj.provider instanceof WorldProviderEnd
				&& worldObj.getBlockState(new BlockPos(var1, var2, var3)).getBlock() != Blocks.fire) {
			worldObj.setBlockState(new BlockPos(var1, var2, var3), Blocks.fire.getDefaultState());
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			if (!isDead && !worldObj.isRemote) {
				health = 0;

				if (health <= 0) {
					setDead();

					if (!worldObj.isRemote) {
						worldObj.createExplosion((Entity) null, posX, posY, posZ, 6.0F, true);
					}
				}
			}

			return true;
		}
	}
}
