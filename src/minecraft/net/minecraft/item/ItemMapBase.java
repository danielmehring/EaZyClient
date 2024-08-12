package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase extends Item {

public static final int EaZy = 1307;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000004";

	/**
	 * false for all Items except sub-classes of ItemMapBase
	 */
	@Override
	public boolean isMap() {
		return true;
	}

	public Packet createMapDataPacket(final ItemStack p_150911_1_, final World worldIn,
			final EntityPlayer p_150911_3_) {
		return null;
	}
}
