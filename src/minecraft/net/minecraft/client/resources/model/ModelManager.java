package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {

public static final int EaZy = 889;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IRegistry modelRegistry;
	private final TextureMap field_174956_b;
	private final BlockModelShapes field_174957_c;
	private IBakedModel defaultModel;
	// private static final String __OBFID = "http://https://fuckuskid00002388";

	public ModelManager(final TextureMap p_i46082_1_) {
		field_174956_b = p_i46082_1_;
		field_174957_c = new BlockModelShapes(this);
	}

	@Override
	public void onResourceManagerReload(final IResourceManager p_110549_1_) {
		final ModelBakery var2 = new ModelBakery(p_110549_1_, field_174956_b, field_174957_c);
		modelRegistry = var2.setupModelRegistry();
		defaultModel = (IBakedModel) modelRegistry.getObject(ModelBakery.MODEL_MISSING);
		field_174957_c.func_178124_c();
	}

	public IBakedModel getModel(final ModelResourceLocation p_174953_1_) {
		if (p_174953_1_ == null) {
			return defaultModel;
		} else {
			final IBakedModel var2 = (IBakedModel) modelRegistry.getObject(p_174953_1_);
			return var2 == null ? defaultModel : var2;
		}
	}

	public IBakedModel getMissingModel() {
		return defaultModel;
	}

	public TextureMap func_174952_b() {
		return field_174956_b;
	}

	public BlockModelShapes getBlockModelShapes() {
		return field_174957_c;
	}
}
