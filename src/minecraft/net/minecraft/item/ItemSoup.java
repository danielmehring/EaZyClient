package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemSoup extends ItemFood {

public static final int EaZy = 1328;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001778";

	public ItemSoup(final int p_i45330_1_) {
		super(p_i45330_1_, false);
		setMaxStackSize(1);
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Override
	public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
		super.onItemUseFinish(stack, worldIn, playerIn);
		return new ItemStack(Items.bowl);
	}
}
