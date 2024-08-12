package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {

public static final int EaZy = 1319;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150908_b;

	/** Block ID of the soil this seed food should be planted on. */
	private final Block soilId;
	// private static final String __OBFID = "http://https://fuckuskid00000060";

	public ItemSeedFood(final int p_i45351_1_, final float p_i45351_2_, final Block p_i45351_3_,
			final Block p_i45351_4_) {
		super(p_i45351_1_, p_i45351_2_, false);
		field_150908_b = p_i45351_3_;
		soilId = p_i45351_4_;
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
		} else if (worldIn.getBlockState(pos).getBlock() == soilId && worldIn.isAirBlock(pos.offsetUp())) {
			worldIn.setBlockState(pos.offsetUp(), field_150908_b.getDefaultState());
			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}
}
