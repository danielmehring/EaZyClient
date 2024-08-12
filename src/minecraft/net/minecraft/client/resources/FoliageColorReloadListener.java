package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;

import java.io.IOException;

public class FoliageColorReloadListener implements IResourceManagerReloadListener {

public static final int EaZy = 875;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_130079_a = new ResourceLocation("textures/colormap/foliage.png");
	// private static final String __OBFID = "http://https://fuckuskid00001077";

	@Override
	public void onResourceManagerReload(final IResourceManager p_110549_1_) {
		try {
			ColorizerFoliage.setFoliageBiomeColorizer(TextureUtil.readImageData(p_110549_1_, field_130079_a));
		} catch (final IOException var3) {
		}
	}
}
