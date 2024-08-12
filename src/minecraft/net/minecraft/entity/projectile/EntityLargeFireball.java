package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {

public static final int EaZy = 1200;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int field_92057_e = 1;
	// private static final String __OBFID = "http://https://fuckuskid00001719";

	public EntityLargeFireball(final World worldIn) {
		super(worldIn);
	}

	public EntityLargeFireball(final World worldIn, final double p_i1768_2_, final double p_i1768_4_,
			final double p_i1768_6_, final double p_i1768_8_, final double p_i1768_10_, final double p_i1768_12_) {
		super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
	}

	public EntityLargeFireball(final World worldIn, final EntityLivingBase p_i1769_2_, final double p_i1769_3_,
			final double p_i1769_5_, final double p_i1769_7_) {
		super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70227_1_) {
		if (!worldObj.isRemote) {
			if (p_70227_1_.entityHit != null) {
				p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 6.0F);
				func_174815_a(shootingEntity, p_70227_1_.entityHit);
			}

			final boolean var2 = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.newExplosion((Entity) null, posX, posY, posZ, field_92057_e, var2, var2);
			setDead();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", field_92057_e);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("ExplosionPower", 99)) {
			field_92057_e = tagCompund.getInteger("ExplosionPower");
		}
	}
}
