package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;

public abstract class BlockStateBase implements IBlockState {

public static final int EaZy = 423;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Joiner COMMA_JOINER = Joiner.on(',');
	private static final Function field_177233_b = new Function() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002031";
		public String func_177225_a(final Entry p_177225_1_) {
			if (p_177225_1_ == null) {
				return "<NULL>";
			} else {
				final IProperty var2 = (IProperty) p_177225_1_.getKey();
				return var2.getName() + "=" + var2.getName((Comparable) p_177225_1_.getValue());
			}
		}

		@Override
		public Object apply(final Object p_apply_1_) {
			return func_177225_a((Entry) p_apply_1_);
		}
	};
	// private static final String __OBFID = "http://https://fuckuskid00002032";
	private int blockId = -1;
	private int blockStateId = -1;
	private int metadata = -1;
	private ResourceLocation blockLocation = null;

	public int getBlockId() {
		if (blockId < 0) {
			blockId = Block.getIdFromBlock(getBlock());
		}

		return blockId;
	}

	public int getBlockStateId() {
		if (blockStateId < 0) {
			blockStateId = Block.getStateId(this);
		}

		return blockStateId;
	}

	public int getMetadata() {
		if (metadata < 0) {
			metadata = getBlock().getMetaFromState(this);
		}

		return metadata;
	}

	public ResourceLocation getBlockLocation() {
		if (blockLocation == null) {
			blockLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(getBlock());
		}

		return blockLocation;
	}

	/**
	 * Create a version of this BlockState with the given property cycled to the
	 * next value in order. If the property was at the highest possible value,
	 * it is set to the lowest one instead.
	 */
	@Override
	public IBlockState cycleProperty(final IProperty property) {
		return withProperty(property, (Comparable) cyclePropertyValue(property.getAllowedValues(), getValue(property)));
	}

	/**
	 * Helper method for cycleProperty.
	 * 
	 * @param values
	 *            The collection of values
	 * @param currentValue
	 *            The current value
	 */
	protected static Object cyclePropertyValue(final Collection values, final Object currentValue) {
		final Iterator var2 = values.iterator();

		while (var2.hasNext()) {
			if (var2.next().equals(currentValue)) {
				if (var2.hasNext()) {
					return var2.next();
				}

				return values.iterator().next();
			}
		}

		return var2.next();
	}

	@Override
	public String toString() {
		final StringBuilder var1 = new StringBuilder();
		var1.append(Block.blockRegistry.getNameForObject(getBlock()));

		if (!getProperties().isEmpty()) {
			var1.append("[");
			COMMA_JOINER.appendTo(var1, Iterables.transform(getProperties().entrySet(), field_177233_b));
			var1.append("]");
		}

		return var1.toString();
	}

	public ImmutableTable<IProperty, Comparable, IBlockState> getPropertyValueTable() {
		return null;
	}
}
