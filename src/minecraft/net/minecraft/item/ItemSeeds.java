package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {

public static final int EaZy = 1320;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150925_a;

	/** BlockID of the block the seeds can be planted on. */
	private final Block soilBlockID;
	// private static final String __OBFID = "http://https://fuckuskid00000061";

	public ItemSeeds(final Block p_i45352_1_, final Block p_i45352_2_) {
		field_150925_a = p_i45352_1_;
		soilBlockID = p_i45352_2_;
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn,
			final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (side != EnumFacing.UP) {
			return false;
		} else if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
			return false;
		} else if (worldIn.getBlockState(pos).getBlock() == soilBlockID && worldIn.isAirBlock(pos.offsetUp())) {
			worldIn.setBlockState(pos.offsetUp(), field_150925_a.getDefaultState());
			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}
}
