package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {

public static final int EaZy = 1147;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int minecartTNTFuse = -1;
	// private static final String __OBFID = "http://https://fuckuskid00001680";

	public EntityMinecartTNT(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartTNT(final World worldIn, final double p_i1728_2_, final double p_i1728_4_,
			final double p_i1728_6_) {
		super(worldIn, p_i1728_2_, p_i1728_4_, p_i1728_6_);
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.TNT;
	}

	@Override
	public IBlockState func_180457_u() {
		return Blocks.tnt.getDefaultState();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (minecartTNTFuse > 0) {
			--minecartTNTFuse;
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D,
					new int[0]);
		} else if (minecartTNTFuse == 0) {
			explodeCart(motionX * motionX + motionZ * motionZ);
		}

		if (isCollidedHorizontally) {
			final double var1 = motionX * motionX + motionZ * motionZ;

			if (var1 >= 0.009999999776482582D) {
				explodeCart(var1);
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		final Entity var3 = source.getSourceOfDamage();

		if (var3 instanceof EntityArrow) {
			final EntityArrow var4 = (EntityArrow) var3;

			if (var4.isBurning()) {
				explodeCart(var4.motionX * var4.motionX + var4.motionY * var4.motionY + var4.motionZ * var4.motionZ);
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void killMinecart(final DamageSource p_94095_1_) {
		super.killMinecart(p_94095_1_);
		final double var2 = motionX * motionX + motionZ * motionZ;

		if (!p_94095_1_.isExplosion()) {
			entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
		}

		if (p_94095_1_.isFireDamage() || p_94095_1_.isExplosion() || var2 >= 0.009999999776482582D) {
			explodeCart(var2);
		}
	}

	/**
	 * Makes the minecart explode.
	 */
	protected void explodeCart(final double p_94103_1_) {
		if (!worldObj.isRemote) {
			double var3 = Math.sqrt(p_94103_1_);

			if (var3 > 5.0D) {
				var3 = 5.0D;
			}

			worldObj.createExplosion(this, posX, posY, posZ, (float) (4.0D + rand.nextDouble() * 1.5D * var3), true);
			setDead();
		}
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		if (distance >= 3.0F) {
			final float var3 = distance / 10.0F;
			explodeCart(var3 * var3);
		}

		super.fall(distance, damageMultiplier);
	}

	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is
	 * the rail receiving power
	 */
	@Override
	public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_,
			final boolean p_96095_4_) {
		if (p_96095_4_ && minecartTNTFuse < 0) {
			ignite();
		}
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 10) {
			ignite();
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	/**
	 * Ignites this TNT cart.
	 */
	public void ignite() {
		minecartTNTFuse = 80;

		if (!worldObj.isRemote) {
			worldObj.setEntityState(this, (byte) 10);

			if (!isSlient()) {
				worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
			}
		}
	}

	public int func_94104_d() {
		return minecartTNTFuse;
	}

	/**
	 * Returns true if the TNT minecart is ignited.
	 */
	public boolean isIgnited() {
		return minecartTNTFuse > -1;
	}

	/**
	 * Explosion resistance of a block relative to this entity
	 */
	@Override
	public float getExplosionResistance(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_,
			final IBlockState p_180428_4_) {
		return isIgnited() && (BlockRailBase.func_176563_d(p_180428_4_)
				|| BlockRailBase.func_176562_d(worldIn, p_180428_3_.offsetUp())) ? 0.0F
						: super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);
	}

	@Override
	public boolean func_174816_a(final Explosion p_174816_1_, final World worldIn, final BlockPos p_174816_3_,
			final IBlockState p_174816_4_, final float p_174816_5_) {
		return isIgnited() && (BlockRailBase.func_176563_d(p_174816_4_)
				|| BlockRailBase.func_176562_d(worldIn, p_174816_3_.offsetUp())) ? false
						: super.func_174816_a(p_174816_1_, worldIn, p_174816_3_, p_174816_4_, p_174816_5_);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("TNTFuse", 99)) {
			minecartTNTFuse = tagCompund.getInteger("TNTFuse");
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("TNTFuse", minecartTNTFuse);
	}
}
