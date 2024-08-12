package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {

public static final int EaZy = 1136;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001726";

	public EntityExpBottle(final World worldIn) {
		super(worldIn);
	}

	public EntityExpBottle(final World worldIn, final EntityLivingBase p_i1786_2_) {
		super(worldIn, p_i1786_2_);
	}

	public EntityExpBottle(final World worldIn, final double p_i1787_2_, final double p_i1787_4_,
			final double p_i1787_6_) {
		super(worldIn, p_i1787_2_, p_i1787_4_, p_i1787_6_);
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.07F;
	}

	@Override
	protected float func_70182_d() {
		return 0.7F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70184_1_) {
		if (!worldObj.isRemote) {
			worldObj.playAuxSFX(2002, new BlockPos(this), 0);
			int var2 = 3 + worldObj.rand.nextInt(5) + worldObj.rand.nextInt(5);

			while (var2 > 0) {
				final int var3 = EntityXPOrb.getXPSplit(var2);
				var2 -= var3;
				worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var3));
			}

			setDead();
		}
	}
}
