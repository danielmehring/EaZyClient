package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {

public static final int EaZy = 1203;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001722";

	public EntitySnowball(final World worldIn) {
		super(worldIn);
	}

	public EntitySnowball(final World worldIn, final EntityLivingBase p_i1774_2_) {
		super(worldIn, p_i1774_2_);
	}

	public EntitySnowball(final World worldIn, final double p_i1775_2_, final double p_i1775_4_,
			final double p_i1775_6_) {
		super(worldIn, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70184_1_) {
		if (p_70184_1_.entityHit != null) {
			byte var2 = 0;

			if (p_70184_1_.entityHit instanceof EntityBlaze) {
				var2 = 3;
			}

			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
		}

		for (int var3 = 0; var3 < 8; ++var3) {
			worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}
}
