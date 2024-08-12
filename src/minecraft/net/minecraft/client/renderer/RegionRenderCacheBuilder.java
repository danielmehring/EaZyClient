package net.minecraft.client.renderer;

import net.minecraft.util.EnumWorldBlockLayer;

public class RegionRenderCacheBuilder {

public static final int EaZy = 806;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final WorldRenderer[] field_179040_a = new WorldRenderer[EnumWorldBlockLayer.values().length];

	public RegionRenderCacheBuilder() {
		field_179040_a[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(2097152);
		field_179040_a[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(131072);
		field_179040_a[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(131072);
		field_179040_a[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(262144);
	}

	public WorldRenderer func_179038_a(final EnumWorldBlockLayer p_179038_1_) {
		return field_179040_a[p_179038_1_.ordinal()];
	}

	public WorldRenderer func_179039_a(final int p_179039_1_) {
		return field_179040_a[p_179039_1_];
	}
}
