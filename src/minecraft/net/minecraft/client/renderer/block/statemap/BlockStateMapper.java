package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class BlockStateMapper {

public static final int EaZy = 686;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map field_178450_a = Maps.newIdentityHashMap();
	private final Set field_178449_b = Sets.newIdentityHashSet();
	// private static final String __OBFID = "http://https://fuckuskid00002478";

	public void func_178447_a(final Block p_178447_1_, final IStateMapper p_178447_2_) {
		field_178450_a.put(p_178447_1_, p_178447_2_);
	}

	public void registerBuiltInBlocks(final Block... p_178448_1_) {
		Collections.addAll(field_178449_b, p_178448_1_);
	}

	public Map func_178446_a() {
		final IdentityHashMap var1 = Maps.newIdentityHashMap();
		final Iterator var2 = Block.blockRegistry.iterator();

		while (var2.hasNext()) {
			final Block var3 = (Block) var2.next();

			if (!field_178449_b.contains(var3)) {
				var1.putAll(((IStateMapper) Objects.firstNonNull(field_178450_a.get(var3), new DefaultStateMapper()))
						.func_178130_a(var3));
			}
		}

		return var1;
	}
}
