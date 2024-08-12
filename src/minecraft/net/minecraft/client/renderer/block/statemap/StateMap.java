package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class StateMap extends StateMapperBase {

public static final int EaZy = 689;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IProperty field_178142_a;
	private final String field_178141_c;
	private final List field_178140_d;
	// private static final String __OBFID = "http://https://fuckuskid00002476";

	private StateMap(final IProperty p_i46210_1_, final String p_i46210_2_, final List p_i46210_3_) {
		field_178142_a = p_i46210_1_;
		field_178141_c = p_i46210_2_;
		field_178140_d = p_i46210_3_;
	}

	@Override
	protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
		final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());
		String var3;

		if (field_178142_a == null) {
			var3 = ((ResourceLocation) Block.blockRegistry.getNameForObject(p_178132_1_.getBlock())).toString();
		} else {
			var3 = field_178142_a.getName((Comparable) var2.remove(field_178142_a));
		}

		if (field_178141_c != null) {
			var3 = var3 + field_178141_c;
		}

		final Iterator var4 = field_178140_d.iterator();

		while (var4.hasNext()) {
			final IProperty var5 = (IProperty) var4.next();
			var2.remove(var5);
		}

		return new ModelResourceLocation(var3, func_178131_a(var2));
	}

	StateMap(final IProperty p_i46211_1_, final String p_i46211_2_, final List p_i46211_3_, final Object p_i46211_4_) {
		this(p_i46211_1_, p_i46211_2_, p_i46211_3_);
	}

	public static class Builder {
		private IProperty field_178445_a;
		private String field_178443_b;
		private final List field_178444_c = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00002474";

		public StateMap.Builder func_178440_a(final IProperty p_178440_1_) {
			field_178445_a = p_178440_1_;
			return this;
		}

		public StateMap.Builder func_178439_a(final String p_178439_1_) {
			field_178443_b = p_178439_1_;
			return this;
		}

		public StateMap.Builder func_178442_a(final IProperty... p_178442_1_) {
			Collections.addAll(field_178444_c, p_178442_1_);
			return this;
		}

		public StateMap build() {
			return new StateMap(field_178445_a, field_178443_b, field_178444_c, null);
		}
	}
}
