package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped extends RenderLiving {

public static final int EaZy = 741;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177118_j = new ResourceLocation("textures/entity/steve.png");
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;
	// private static final String __OBFID = "http://https://fuckuskid00001001";

	public RenderBiped(final RenderManager p_i46168_1_, final ModelBiped p_i46168_2_, final float p_i46168_3_) {
		this(p_i46168_1_, p_i46168_2_, p_i46168_3_, 1.0F);
		addLayer(new LayerHeldItem(this));
	}

	public RenderBiped(final RenderManager p_i46169_1_, final ModelBiped p_i46169_2_, final float p_i46169_3_,
			final float p_i46169_4_) {
		super(p_i46169_1_, p_i46169_2_, p_i46169_3_);
		modelBipedMain = p_i46169_2_;
		field_77070_b = p_i46169_4_;
		addLayer(new LayerCustomHead(p_i46169_2_.bipedHead));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
		return field_177118_j;
	}

	@Override
	public void func_82422_c() {
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityLiving) p_110775_1_);
	}
}
