package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public abstract class StateMapperBase implements IStateMapper {

public static final int EaZy = 690;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected Map field_178133_b = Maps.newLinkedHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002479";

	public String func_178131_a(final Map p_178131_1_) {
		final StringBuilder var2 = new StringBuilder();
		final Iterator var3 = p_178131_1_.entrySet().iterator();

		while (var3.hasNext()) {
			final Entry var4 = (Entry) var3.next();

			if (var2.length() != 0) {
				var2.append(",");
			}

			final IProperty var5 = (IProperty) var4.getKey();
			final Comparable var6 = (Comparable) var4.getValue();
			var2.append(var5.getName());
			var2.append("=");
			var2.append(var5.getName(var6));
		}

		if (var2.length() == 0) {
			var2.append("normal");
		}

		return var2.toString();
	}

	@Override
	public Map func_178130_a(final Block p_178130_1_) {
		final Iterator var2 = p_178130_1_.getBlockState().getValidStates().iterator();

		while (var2.hasNext()) {
			final IBlockState var3 = (IBlockState) var2.next();
			field_178133_b.put(var3, func_178132_a(var3));
		}

		return field_178133_b;
	}

	protected abstract ModelResourceLocation func_178132_a(IBlockState var1);
}
