package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

import java.io.IOException;

public class GrassColorReloadListener implements IResourceManagerReloadListener {

public static final int EaZy = 876;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");
	// private static final String __OBFID = "http://https://fuckuskid00001078";

	@Override
	public void onResourceManagerReload(final IResourceManager p_110549_1_) {
		try {
			ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(p_110549_1_, field_130078_a));
		} catch (final IOException var3) {
		}
	}
}
