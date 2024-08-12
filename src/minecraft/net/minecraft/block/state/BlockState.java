package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BlockState {

public static final int EaZy = 422;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Joiner COMMA_JOINER = Joiner.on(", ");

	/** Function that converts a Property into it's name. */
	private static final Function GET_NAME_FUNC = new Function() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002029";
		public String apply(final IProperty property) {
			return property == null ? "<NULL>" : property.getName();
		}

		@Override
		public Object apply(final Object p_apply_1_) {
			return this.apply((IProperty) p_apply_1_);
		}
	};
	private final Block block;
	private final ImmutableList properties;
	private final ImmutableList validStates;
	// private static final String __OBFID = "http://https://fuckuskid00002030";

	public BlockState(final Block blockIn, final IProperty... properties) {
		block = blockIn;
		Arrays.sort(properties, new Comparator() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002028";
			public int compare(final IProperty left, final IProperty right) {
				return left.getName().compareTo(right.getName());
			}

			@Override
			public int compare(final Object p_compare_1_, final Object p_compare_2_) {
				return this.compare((IProperty) p_compare_1_, (IProperty) p_compare_2_);
			}
		});
		this.properties = ImmutableList.copyOf(properties);
		final LinkedHashMap var3 = Maps.newLinkedHashMap();
		final ArrayList var4 = Lists.newArrayList();
		final Iterable var5 = Cartesian.cartesianProduct(getAllowedValues());
		Iterator var6 = var5.iterator();

		while (var6.hasNext()) {
			final List var7 = (List) var6.next();
			final Map var8 = MapPopulator.createMap(this.properties, var7);
			final BlockState.StateImplemenation var9 = new BlockState.StateImplemenation(blockIn,
					ImmutableMap.copyOf(var8), null);
			var3.put(var8, var9);
			var4.add(var9);
		}

		var6 = var4.iterator();

		while (var6.hasNext()) {
			final BlockState.StateImplemenation var10 = (BlockState.StateImplemenation) var6.next();
			var10.buildPropertyValueTable(var3);
		}

		validStates = ImmutableList.copyOf(var4);
	}

	public ImmutableList getValidStates() {
		return validStates;
	}

	private List getAllowedValues() {
		final ArrayList var1 = Lists.newArrayList();

		for (int var2 = 0; var2 < properties.size(); ++var2) {
			var1.add(((IProperty) properties.get(var2)).getAllowedValues());
		}

		return var1;
	}

	public IBlockState getBaseState() {
		return (IBlockState) validStates.get(0);
	}

	public Block getBlock() {
		return block;
	}

	public Collection getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(block))
				.add("properties", Iterables.transform(properties, GET_NAME_FUNC)).toString();
	}

	static class StateImplemenation extends BlockStateBase {
		private final Block block;
		private final ImmutableMap properties;
		private ImmutableTable propertyValueTable;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002027";

		private StateImplemenation(final Block p_i45660_1_, final ImmutableMap p_i45660_2_) {
			block = p_i45660_1_;
			properties = p_i45660_2_;
		}

		@Override
		public Collection getPropertyNames() {
			return Collections.unmodifiableCollection(properties.keySet());
		}

		@Override
		public Comparable getValue(final IProperty property) {
			if (!properties.containsKey(property)) {
				throw new IllegalArgumentException(
						"Cannot get property " + property + " as it does not exist in " + block.getBlockState());
			} else {
				return (Comparable) property.getValueClass().cast(properties.get(property));
			}
		}

		@Override
		public IBlockState withProperty(final IProperty property, final Comparable value) {
			if (!properties.containsKey(property)) {
				throw new IllegalArgumentException(
						"Cannot set property " + property + " as it does not exist in " + block.getBlockState());
			} else if (!property.getAllowedValues().contains(value)) {
				throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block "
						+ Block.blockRegistry.getNameForObject(block) + ", it is not an allowed value");
			} else {
				return properties.get(property) == value ? this : (IBlockState) propertyValueTable.get(property, value);
			}
		}

		@Override
		public ImmutableMap getProperties() {
			return properties;
		}

		@Override
		public Block getBlock() {
			return block;
		}

		@Override
		public boolean equals(final Object p_equals_1_) {
			return this == p_equals_1_;
		}

		@Override
		public int hashCode() {
			return properties.hashCode();
		}

		public void buildPropertyValueTable(final Map map) {
			if (propertyValueTable != null) {
				throw new IllegalStateException();
			} else {
				final HashBasedTable var2 = HashBasedTable.create();
				final Iterator var3 = properties.keySet().iterator();

				while (var3.hasNext()) {
					final IProperty var4 = (IProperty) var3.next();
					final Iterator var5 = var4.getAllowedValues().iterator();

					while (var5.hasNext()) {
						final Comparable var6 = (Comparable) var5.next();

						if (var6 != properties.get(var4)) {
							var2.put(var4, var6, map.get(setPropertyValue(var4, var6)));
						}
					}
				}

				propertyValueTable = ImmutableTable.copyOf(var2);
			}
		}

		private Map setPropertyValue(final IProperty property, final Comparable value) {
			final HashMap var3 = Maps.newHashMap(properties);
			var3.put(property, value);
			return var3;
		}

		StateImplemenation(final Block p_i45661_1_, final ImmutableMap p_i45661_2_, final Object p_i45661_3_) {
			this(p_i45661_1_, p_i45661_2_);
		}
	}
}
