package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderPig extends RenderLiving {

public static final int EaZy = 773;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
	// private static final String __OBFID = "http://https://fuckuskid00001019";

	public RenderPig(final RenderManager p_i46149_1_, final ModelBase p_i46149_2_, final float p_i46149_3_) {
		super(p_i46149_1_, p_i46149_2_, p_i46149_3_);
		addLayer(new LayerSaddle(this));
	}

	protected ResourceLocation func_180583_a(final EntityPig p_180583_1_) {
		return pigTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180583_a((EntityPig) p_110775_1_);
	}
}
