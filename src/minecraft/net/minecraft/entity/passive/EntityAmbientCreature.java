package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals {

public static final int EaZy = 1174;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001636";

	public EntityAmbientCreature(final World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	protected boolean interact(final EntityPlayer p_70085_1_) {
		return false;
	}
}
