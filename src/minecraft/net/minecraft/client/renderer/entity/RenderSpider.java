package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSpider extends RenderLiving {

public static final int EaZy = 784;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
	// private static final String __OBFID = "http://https://fuckuskid00001027";

	public RenderSpider(final RenderManager p_i46139_1_) {
		super(p_i46139_1_, new ModelSpider(), 1.0F);
		addLayer(new LayerSpiderEyes(this));
	}

	protected float getDeathMaxRotation(final EntitySpider p_77037_1_) {
		return 180.0F;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntitySpider p_110775_1_) {
		return spiderTextures;
	}

	@Override
	protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
		return this.getDeathMaxRotation((EntitySpider) p_77037_1_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntitySpider) p_110775_1_);
	}
}
