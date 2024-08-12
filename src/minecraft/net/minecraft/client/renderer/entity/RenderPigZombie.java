package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped {

public static final int EaZy = 774;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177120_j = new ResourceLocation("textures/entity/zombie_pigman.png");
	// private static final String __OBFID = "http://https://fuckuskid00002434";

	public RenderPigZombie(final RenderManager p_i46148_1_) {
		super(p_i46148_1_, new ModelZombie(), 0.5F, 1.0F);
		addLayer(new LayerHeldItem(this));
		addLayer(new LayerBipedArmor(this) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002433";
			@Override
			protected void func_177177_a() {
				field_177189_c = new ModelZombie(0.5F, true);
				field_177186_d = new ModelZombie(1.0F, true);
			}
		});
	}

	protected ResourceLocation func_177119_a(final EntityPigZombie p_177119_1_) {
		return field_177120_j;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
		return func_177119_a((EntityPigZombie) p_110775_1_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_177119_a((EntityPigZombie) p_110775_1_);
	}
}
