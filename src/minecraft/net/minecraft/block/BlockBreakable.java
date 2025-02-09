package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block {

public static final int EaZy = 262;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean ignoreSimilarity;
	// private static final String __OBFID = "http://https://fuckuskid00000254";

	protected BlockBreakable(final Material p_i45712_1_, final boolean p_i45712_2_) {
		super(p_i45712_1_);
		ignoreSimilarity = p_i45712_2_;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		final IBlockState var4 = worldIn.getBlockState(pos);
		final Block var5 = var4.getBlock();

		if (this == Blocks.glass || this == Blocks.stained_glass) {
			if (worldIn.getBlockState(pos.offset(side.getOpposite())) != var4) {
				return true;
			}

			if (var5 == this) {
				return false;
			}
		}

		return !ignoreSimilarity && var5 == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
	}
}
