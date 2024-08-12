package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderCow extends RenderLiving {

public static final int EaZy = 746;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
	// private static final String __OBFID = "http://https://fuckuskid00000984";

	public RenderCow(final RenderManager p_i46187_1_, final ModelBase p_i46187_2_, final float p_i46187_3_) {
		super(p_i46187_1_, p_i46187_2_, p_i46187_3_);
	}

	protected ResourceLocation func_180572_a(final EntityCow p_180572_1_) {
		return cowTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180572_a((EntityCow) p_110775_1_);
	}
}
