package net.minecraft.entity.projectile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {

public static final int EaZy = 1205;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001728";

	public EntityWitherSkull(final World worldIn) {
		super(worldIn);
		setSize(0.3125F, 0.3125F);
	}

	public EntityWitherSkull(final World worldIn, final EntityLivingBase p_i1794_2_, final double p_i1794_3_,
			final double p_i1794_5_, final double p_i1794_7_) {
		super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
		setSize(0.3125F, 0.3125F);
	}

	/**
	 * Return the motion factor for this projectile. The factor is multiplied by
	 * the original motion.
	 */
	@Override
	protected float getMotionFactor() {
		return isInvulnerable() ? 0.73F : super.getMotionFactor();
	}

	public EntityWitherSkull(final World worldIn, final double p_i1795_2_, final double p_i1795_4_,
			final double p_i1795_6_, final double p_i1795_8_, final double p_i1795_10_, final double p_i1795_12_) {
		super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
		setSize(0.3125F, 0.3125F);
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire
	 * effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return false;
	}

	/**
	 * Explosion resistance of a block relative to this entity
	 */
	@Override
	public float getExplosionResistance(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_,
			final IBlockState p_180428_4_) {
		float var5 = super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);

		if (isInvulnerable() && p_180428_4_.getBlock() != Blocks.bedrock && p_180428_4_.getBlock() != Blocks.end_portal
				&& p_180428_4_.getBlock() != Blocks.end_portal_frame
				&& p_180428_4_.getBlock() != Blocks.command_block) {
			var5 = Math.min(0.8F, var5);
		}

		return var5;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70227_1_) {
		if (!worldObj.isRemote) {
			if (p_70227_1_.entityHit != null) {
				if (shootingEntity != null) {
					if (p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeMobDamage(shootingEntity), 8.0F)) {
						if (!p_70227_1_.entityHit.isEntityAlive()) {
							shootingEntity.heal(5.0F);
						} else {
							func_174815_a(shootingEntity, p_70227_1_.entityHit);
						}
					}
				} else {
					p_70227_1_.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
				}

				if (p_70227_1_.entityHit instanceof EntityLivingBase) {
					byte var2 = 0;

					if (worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
						var2 = 10;
					} else if (worldObj.getDifficulty() == EnumDifficulty.HARD) {
						var2 = 40;
					}

					if (var2 > 0) {
						((EntityLivingBase) p_70227_1_.entityHit)
								.addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
					}
				}
			}

			worldObj.newExplosion(this, posX, posY, posZ, 1.0F, false,
					worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
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

	@Override
	protected void entityInit() {
		dataWatcher.addObject(10, (byte) 0);
	}

	/**
	 * Return whether this skull comes from an invulnerable (aura) wither boss.
	 */
	public boolean isInvulnerable() {
		return dataWatcher.getWatchableObjectByte(10) == 1;
	}

	/**
	 * Set whether this skull comes from an invulnerable (aura) wither boss.
	 */
	public void setInvulnerable(final boolean p_82343_1_) {
		dataWatcher.updateObject(10, (byte) (p_82343_1_ ? 1 : 0));
	}
}
