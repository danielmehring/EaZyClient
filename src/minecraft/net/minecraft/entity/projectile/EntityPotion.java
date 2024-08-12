package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable {

public static final int EaZy = 1201;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * The damage value of the thrown potion that this EntityPotion represents.
	 */
	private ItemStack potionDamage;
	// private static final String __OBFID = "http://https://fuckuskid00001727";

	public EntityPotion(final World worldIn) {
		super(worldIn);
	}

	public EntityPotion(final World worldIn, final EntityLivingBase p_i1789_2_, final int p_i1789_3_) {
		this(worldIn, p_i1789_2_, new ItemStack(Items.potionitem, 1, p_i1789_3_));
	}

	public EntityPotion(final World worldIn, final EntityLivingBase p_i1790_2_, final ItemStack p_i1790_3_) {
		super(worldIn, p_i1790_2_);
		potionDamage = p_i1790_3_;
	}

	public EntityPotion(final World worldIn, final double p_i1791_2_, final double p_i1791_4_, final double p_i1791_6_,
			final int p_i1791_8_) {
		this(worldIn, p_i1791_2_, p_i1791_4_, p_i1791_6_, new ItemStack(Items.potionitem, 1, p_i1791_8_));
	}

	public EntityPotion(final World worldIn, final double p_i1792_2_, final double p_i1792_4_, final double p_i1792_6_,
			final ItemStack p_i1792_8_) {
		super(worldIn, p_i1792_2_, p_i1792_4_, p_i1792_6_);
		potionDamage = p_i1792_8_;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	public void setPotionDamage(final int p_82340_1_) {
		if (potionDamage == null) {
			potionDamage = new ItemStack(Items.potionitem, 1, 0);
		}

		potionDamage.setItemDamage(p_82340_1_);
	}

	/**
	 * Returns the damage value of the thrown potion that this EntityPotion
	 * represents.
	 */
	public int getPotionDamage() {
		if (potionDamage == null) {
			potionDamage = new ItemStack(Items.potionitem, 1, 0);
		}

		return potionDamage.getMetadata();
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70184_1_) {
		if (!worldObj.isRemote) {
			final List var2 = Items.potionitem.getEffects(potionDamage);

			if (var2 != null && !var2.isEmpty()) {
				final AxisAlignedBB var3 = getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
				final List var4 = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);

				if (!var4.isEmpty()) {
					final Iterator var5 = var4.iterator();

					while (var5.hasNext()) {
						final EntityLivingBase var6 = (EntityLivingBase) var5.next();
						final double var7 = getDistanceSqToEntity(var6);

						if (var7 < 16.0D) {
							double var9 = 1.0D - Math.sqrt(var7) / 4.0D;

							if (var6 == p_70184_1_.entityHit) {
								var9 = 1.0D;
							}

							final Iterator var11 = var2.iterator();

							while (var11.hasNext()) {
								final PotionEffect var12 = (PotionEffect) var11.next();
								final int var13 = var12.getPotionID();

								if (Potion.potionTypes[var13].isInstant()) {
									Potion.potionTypes[var13].func_180793_a(this, getThrower(), var6,
											var12.getAmplifier(), var9);
								} else {
									final int var14 = (int) (var9 * var12.getDuration() + 0.5D);

									if (var14 > 20) {
										var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
									}
								}
							}
						}
					}
				}
			}

			worldObj.playAuxSFX(2002, new BlockPos(this), getPotionDamage());
			setDead();
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("Potion", 10)) {
			potionDamage = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("Potion"));
		} else {
			setPotionDamage(tagCompund.getInteger("potionValue"));
		}

		if (potionDamage == null) {
			setDead();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		if (potionDamage != null) {
			tagCompound.setTag("Potion", potionDamage.writeToNBT(new NBTTagCompound()));
		}
	}
}
