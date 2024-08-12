package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {

public static final int EaZy = 1144;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001677";

	public EntityMinecartEmpty(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartEmpty(final World worldIn, final double p_i1723_2_, final double p_i1723_4_,
			final double p_i1723_6_) {
		super(worldIn, p_i1723_2_, p_i1723_4_, p_i1723_6_);
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != playerIn) {
			return true;
		} else if (riddenByEntity != null && riddenByEntity != playerIn) {
			return false;
		} else {
			if (!worldObj.isRemote) {
				playerIn.mountEntity(this);
			}

			return true;
		}
	}

	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is
	 * the rail receiving power
	 */
	@Override
	public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_,
			final boolean p_96095_4_) {
		if (p_96095_4_) {
			if (riddenByEntity != null) {
				riddenByEntity.mountEntity((Entity) null);
			}

			if (getRollingAmplitude() == 0) {
				setRollingDirection(-getRollingDirection());
				setRollingAmplitude(10);
				setDamage(50.0F);
				setBeenAttacked();
			}
		}
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.RIDEABLE;
	}
}
