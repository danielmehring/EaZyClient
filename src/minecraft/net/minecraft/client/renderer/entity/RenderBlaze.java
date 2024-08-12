package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;

public class RenderBlaze extends RenderLiving {

public static final int EaZy = 742;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation blazeTextures = new ResourceLocation("textures/entity/blaze.png");
	// private static final String __OBFID = "http://https://fuckuskid00000980";

	public RenderBlaze(final RenderManager p_i46191_1_) {
		super(p_i46191_1_, new ModelBlaze(), 0.5F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityBlaze p_110775_1_) {
		return blazeTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityBlaze) p_110775_1_);
	}
}
