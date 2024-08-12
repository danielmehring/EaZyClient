package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import optifine.Config;
import shadersmod.client.ShadersTex;

public class SimpleTexture extends AbstractTexture {

public static final int EaZy = 820;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	protected final ResourceLocation textureLocation;
	// private static final String __OBFID = "http://https://fuckuskid00001052";

	public SimpleTexture(final ResourceLocation p_i1275_1_) {
		textureLocation = p_i1275_1_;
	}

	@Override
	public void loadTexture(final IResourceManager p_110551_1_) throws IOException {
		deleteGlTexture();
		InputStream var2 = null;

		try {
			final IResource var3 = p_110551_1_.getResource(textureLocation);
			var2 = var3.getInputStream();
			final BufferedImage var4 = TextureUtil.func_177053_a(var2);
			boolean var5 = false;
			boolean var6 = false;

			if (var3.hasMetadata()) {
				try {
					final TextureMetadataSection var11 = (TextureMetadataSection) var3.getMetadata("texture");

					if (var11 != null) {
						var5 = var11.getTextureBlur();
						var6 = var11.getTextureClamp();
					}
				} catch (final RuntimeException var111) {
					logger.warn("Failed reading metadata of: " + textureLocation, var111);
				}
			}

			if (Config.isShaders()) {
				ShadersTex.loadSimpleTexture(getGlTextureId(), var4, var5, var6, p_110551_1_, textureLocation,
						getMultiTexID());
			} else {
				TextureUtil.uploadTextureImageAllocate(getGlTextureId(), var4, var5, var6);
			}
		}
		finally {
			if (var2 != null) {
				var2.close();
			}
		}
	}
}
