package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {

public static final int EaZy = 1135;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001725";

	public EntityEnderPearl(final World worldIn, final EntityLivingBase p_i1783_2_) {
		super(worldIn, p_i1783_2_);
	}

	public EntityEnderPearl(final World worldIn, final double p_i1784_2_, final double p_i1784_4_,
			final double p_i1784_6_) {
		super(worldIn, p_i1784_2_, p_i1784_4_, p_i1784_6_);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(final MovingObjectPosition p_70184_1_) {
		final EntityLivingBase var2 = getThrower();

		if (p_70184_1_.entityHit != null) {
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, var2), 0.0F);
		}

		for (int var3 = 0; var3 < 32; ++var3) {
			worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX, posY + rand.nextDouble() * 2.0D, posZ,
					rand.nextGaussian(), 0.0D, rand.nextGaussian(), new int[0]);
		}

		if (!worldObj.isRemote) {
			if (var2 instanceof EntityPlayerMP) {
				final EntityPlayerMP var5 = (EntityPlayerMP) var2;

				if (var5.playerNetServerHandler.getNetworkManager().isChannelOpen() && var5.worldObj == worldObj
						&& !var5.isPlayerSleeping()) {
					if (rand.nextFloat() < 0.05F && worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
						final EntityEndermite var4 = new EntityEndermite(worldObj);
						var4.setSpawnedByPlayer(true);
						var4.setLocationAndAngles(var2.posX, var2.posY, var2.posZ, var2.rotationYaw,
								var2.rotationPitch);
						worldObj.spawnEntityInWorld(var4);
					}

					if (var2.isRiding()) {
						var2.mountEntity((Entity) null);
					}

					var2.setPositionAndUpdate(posX, posY, posZ);
					var2.fallDistance = 0.0F;
					var2.attackEntityFrom(DamageSource.fall, 5.0F);
				}
			}

			setDead();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		final EntityLivingBase var1 = getThrower();

		if (var1 != null && var1 instanceof EntityPlayer && !var1.isEntityAlive()) {
			setDead();
		} else {
			super.onUpdate();
		}
	}
}
