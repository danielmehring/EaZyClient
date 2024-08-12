package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {

public static final int EaZy = 1202;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001721";

	public EntitySmallFireball(final World worldIn) {
		super(worldIn);
		setSize(0.3125F, 0.3125F);
	}

	public EntitySmallFireball(final World worldIn, final EntityLivingBase p_i1771_2_, final double p_i1771_3_,
			final double p_i1771_5_, final double p_i1771_7_) {
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		setSize(0.3125F, 0.3125F);
	}

	public EntitySmallFireball(final World worldIn, final double p_i1772_2_, final double p_i1772_4_,
			final double p_i1772_6_, final double p_i1772_8_, final double p_i1772_10_, final double p_i1772_12_) {
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
		setSize(0.3125F, 0.3125F);
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70227_1_) {
		if (!worldObj.isRemote) {
			boolean var2;

			if (p_70227_1_.entityHit != null) {
				var2 = p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity),
						5.0F);

				if (var2) {
					func_174815_a(shootingEntity, p_70227_1_.entityHit);

					if (!p_70227_1_.entityHit.isImmuneToFire()) {
						p_70227_1_.entityHit.setFire(5);
					}
				}
			} else {
				var2 = true;

				if (shootingEntity != null && shootingEntity instanceof EntityLiving) {
					var2 = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
				}

				if (var2) {
					final BlockPos var3 = p_70227_1_.getBlockPos().offset(p_70227_1_.facing);

					if (worldObj.isAirBlock(var3)) {
						worldObj.setBlockState(var3, Blocks.fire.getDefaultState());
					}
				}
			}

			setDead();
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		return false;
	}
}
