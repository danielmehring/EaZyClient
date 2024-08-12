package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {

public static final int EaZy = 1197;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001724";

	public EntityEgg(final World worldIn) {
		super(worldIn);
	}

	public EntityEgg(final World worldIn, final EntityLivingBase p_i1780_2_) {
		super(worldIn, p_i1780_2_);
	}

	public EntityEgg(final World worldIn, final double p_i1781_2_, final double p_i1781_4_, final double p_i1781_6_) {
		super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70184_1_) {
		if (p_70184_1_.entityHit != null) {
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}

		if (!worldObj.isRemote && rand.nextInt(8) == 0) {
			byte var2 = 1;

			if (rand.nextInt(32) == 0) {
				var2 = 4;
			}

			for (int var3 = 0; var3 < var2; ++var3) {
				final EntityChicken var4 = new EntityChicken(worldObj);
				var4.setGrowingAge(-24000);
				var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				worldObj.spawnEntityInWorld(var4);
			}
		}

		for (int var6 = 0; var6 < 8; ++var6) {
			worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, posX, posY, posZ, (rand.nextFloat() - 0.5D) * 0.08D,
					(rand.nextFloat() - 0.5D) * 0.08D, (rand.nextFloat() - 0.5D) * 0.08D,
					new int[] { Item.getIdFromItem(Items.egg) });
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}
}
