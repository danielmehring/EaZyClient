package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving {

public static final int EaZy = 778;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation shearedSheepTextures = new ResourceLocation(
			"textures/entity/sheep/sheep.png");
	// private static final String __OBFID = "http://https://fuckuskid00001021";

	public RenderSheep(final RenderManager p_i46145_1_, final ModelBase p_i46145_2_, final float p_i46145_3_) {
		super(p_i46145_1_, p_i46145_2_, p_i46145_3_);
		addLayer(new LayerSheepWool(this));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntitySheep p_110775_1_) {
		return shearedSheepTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntitySheep) p_110775_1_);
	}
}
