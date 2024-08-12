package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.util.ResourceLocation;

public class RenderMooshroom extends RenderLiving {

public static final int EaZy = 770;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
	// private static final String __OBFID = "http://https://fuckuskid00001016";

	public RenderMooshroom(final RenderManager p_i46152_1_, final ModelBase p_i46152_2_, final float p_i46152_3_) {
		super(p_i46152_1_, p_i46152_2_, p_i46152_3_);
		addLayer(new LayerMooshroomMushroom(this));
	}

	protected ResourceLocation func_180582_a(final EntityMooshroom p_180582_1_) {
		return mooshroomTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180582_a((EntityMooshroom) p_110775_1_);
	}
}
