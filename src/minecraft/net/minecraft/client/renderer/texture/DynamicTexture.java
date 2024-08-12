package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

import optifine.Config;
import shadersmod.client.ShadersTex;

public class DynamicTexture extends AbstractTexture {

public static final int EaZy = 813;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final int[] dynamicTextureData;

	/** width of this icon in pixels */
	private final int width;

	/** height of this icon in pixels */
	private final int height;
	// private static final String __OBFID = "http://https://fuckuskid00001048";
	private boolean shadersInitialized;

	public DynamicTexture(final BufferedImage p_i1270_1_) {
		this(p_i1270_1_.getWidth(), p_i1270_1_.getHeight());
		p_i1270_1_.getRGB(0, 0, p_i1270_1_.getWidth(), p_i1270_1_.getHeight(), dynamicTextureData, 0,
				p_i1270_1_.getWidth());
		updateDynamicTexture();
	}

	public DynamicTexture(final int p_i1271_1_, final int p_i1271_2_) {
		shadersInitialized = false;
		width = p_i1271_1_;
		height = p_i1271_2_;
		dynamicTextureData = new int[p_i1271_1_ * p_i1271_2_ * 3];

		if (Config.isShaders()) {
			ShadersTex.initDynamicTexture(getGlTextureId(), p_i1271_1_, p_i1271_2_, this);
			shadersInitialized = true;
		} else {
			TextureUtil.allocateTexture(getGlTextureId(), p_i1271_1_, p_i1271_2_);
		}
	}

	@Override
	public void loadTexture(final IResourceManager p_110551_1_) throws IOException {}

	public void updateDynamicTexture() {
		if (Config.isShaders()) {
			if (!shadersInitialized) {
				ShadersTex.initDynamicTexture(getGlTextureId(), width, height, this);
				shadersInitialized = true;
			}

			ShadersTex.updateDynamicTexture(getGlTextureId(), dynamicTextureData, width, height, this);
		} else {
			TextureUtil.uploadTexture(getGlTextureId(), dynamicTextureData, width, height);
		}
	}

	public int[] getTextureData() {
		return dynamicTextureData;
	}
}
