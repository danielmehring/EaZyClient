package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;

public class RenderSnowMan extends RenderLiving {

public static final int EaZy = 783;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");
	// private static final String __OBFID = "http://https://fuckuskid00001025";

	public RenderSnowMan(final RenderManager p_i46140_1_) {
		super(p_i46140_1_, new ModelSnowMan(), 0.5F);
		addLayer(new LayerSnowmanHead(this));
	}

	protected ResourceLocation func_180587_a(final EntitySnowman p_180587_1_) {
		return snowManTextures;
	}

	public ModelSnowMan func_177123_g() {
		return (ModelSnowMan) super.getMainModel();
	}

	@Override
	public ModelBase getMainModel() {
		return func_177123_g();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180587_a((EntitySnowman) p_110775_1_);
	}
}
