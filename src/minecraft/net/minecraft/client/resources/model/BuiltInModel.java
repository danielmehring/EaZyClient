package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class BuiltInModel implements IBakedModel {

public static final int EaZy = 886;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ItemCameraTransforms field_177557_a;
	// private static final String __OBFID = "http://https://fuckuskid00002392";

	public BuiltInModel(final ItemCameraTransforms p_i46086_1_) {
		field_177557_a = p_i46086_1_;
	}

	@Override
	public List func_177551_a(final EnumFacing p_177551_1_) {
		return null;
	}

	@Override
	public List func_177550_a() {
		return null;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isAmbientOcclusionEnabled() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getTexture() {
		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return field_177557_a;
	}
}
