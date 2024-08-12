package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

public class BlockStateHelper implements Predicate {

public static final int EaZy = 428;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockState field_177641_a;
	private final Map field_177640_b = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002019";

	private BlockStateHelper(final BlockState p_i45653_1_) {
		field_177641_a = p_i45653_1_;
	}

	public static BlockStateHelper forBlock(final Block p_177638_0_) {
		return new BlockStateHelper(p_177638_0_.getBlockState());
	}

	public boolean func_177639_a(final IBlockState p_177639_1_) {
		if (p_177639_1_ != null && p_177639_1_.getBlock().equals(field_177641_a.getBlock())) {
			final Iterator var2 = field_177640_b.entrySet().iterator();
			Entry var3;
			Comparable var4;

			do {
				if (!var2.hasNext()) {
					return true;
				}

				var3 = (Entry) var2.next();
				var4 = p_177639_1_.getValue((IProperty) var3.getKey());
			}
			while (((Predicate) var3.getValue()).apply(var4));

			return false;
		} else {
			return false;
		}
	}

	public BlockStateHelper func_177637_a(final IProperty p_177637_1_, final Predicate p_177637_2_) {
		if (!field_177641_a.getProperties().contains(p_177637_1_)) {
			throw new IllegalArgumentException(field_177641_a + " cannot support property " + p_177637_1_);
		} else {
			field_177640_b.put(p_177637_1_, p_177637_2_);
			return this;
		}
	}

	@Override
	public boolean apply(final Object p_apply_1_) {
		return func_177639_a((IBlockState) p_apply_1_);
	}
}
