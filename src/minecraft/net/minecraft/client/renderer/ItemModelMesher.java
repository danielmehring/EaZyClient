package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class ItemModelMesher {

public static final int EaZy = 802;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map simpleShapes = Maps.newHashMap();
	private final Map simpleShapesCache = Maps.newHashMap();
	private final Map shapers = Maps.newHashMap();
	private final ModelManager modelManager;

	public ItemModelMesher(final ModelManager p_i46250_1_) {
		modelManager = p_i46250_1_;
	}

	public TextureAtlasSprite getParticleIcon(final Item p_178082_1_) {
		return this.getParticleIcon(p_178082_1_, 0);
	}

	public TextureAtlasSprite getParticleIcon(final Item p_178087_1_, final int p_178087_2_) {
		return this.getItemModel(new ItemStack(p_178087_1_, 1, p_178087_2_)).getTexture();
	}

	public IBakedModel getItemModel(final ItemStack p_178089_1_) {
		final Item var2 = p_178089_1_.getItem();
		IBakedModel var3 = this.getItemModel(var2, getMetadata(p_178089_1_));

		if (var3 == null) {
			final ItemMeshDefinition var4 = (ItemMeshDefinition) shapers.get(var2);

			if (var4 != null) {
				var3 = modelManager.getModel(var4.getModelLocation(p_178089_1_));
			}
		}

		if (var3 == null) {
			var3 = modelManager.getMissingModel();
		}

		return var3;
	}

	protected int getMetadata(final ItemStack p_178084_1_) {
		return p_178084_1_.isItemStackDamageable() ? 0 : p_178084_1_.getMetadata();
	}

	protected IBakedModel getItemModel(final Item p_178088_1_, final int p_178088_2_) {
		return (IBakedModel) simpleShapesCache.get(getIndex(p_178088_1_, p_178088_2_));
	}

	private int getIndex(final Item p_178081_1_, final int p_178081_2_) {
		return Item.getIdFromItem(p_178081_1_) << 16 | p_178081_2_;
	}

	public void register(final Item p_178086_1_, final int p_178086_2_, final ModelResourceLocation p_178086_3_) {
		simpleShapes.put(getIndex(p_178086_1_, p_178086_2_), p_178086_3_);
		simpleShapesCache.put(getIndex(p_178086_1_, p_178086_2_), modelManager.getModel(p_178086_3_));
	}

	public void register(final Item p_178080_1_, final ItemMeshDefinition p_178080_2_) {
		shapers.put(p_178080_1_, p_178080_2_);
	}

	public ModelManager getModelManager() {
		return modelManager;
	}

	public void rebuildCache() {
		simpleShapesCache.clear();
		final Iterator var1 = simpleShapes.entrySet().iterator();

		while (var1.hasNext()) {
			final Entry var2 = (Entry) var1.next();
			simpleShapesCache.put(var2.getKey(), modelManager.getModel((ModelResourceLocation) var2.getValue()));
		}
	}
}
