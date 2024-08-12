package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import com.google.common.base.Predicate;

public class BlockHelper implements Predicate {

public static final int EaZy = 426;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block block;
	// private static final String __OBFID = "http://https://fuckuskid00002020";

	private BlockHelper(final Block p_i45654_1_) {
		block = p_i45654_1_;
	}

	public static BlockHelper forBlock(final Block p_177642_0_) {
		return new BlockHelper(p_177642_0_);
	}

	public boolean isBlockEqualTo(final IBlockState p_177643_1_) {
		return p_177643_1_ != null && p_177643_1_.getBlock() == block;
	}

	@Override
	public boolean apply(final Object p_apply_1_) {
		return isBlockEqualTo((IBlockState) p_apply_1_);
	}
}
