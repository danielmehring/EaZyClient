package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import com.google.common.collect.Lists;

public class RenderZombie extends RenderBiped {

public static final int EaZy = 793;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation zombieVillagerTextures = new ResourceLocation(
			"textures/entity/zombie/zombie_villager.png");
	private final ModelBiped field_82434_o;
	private final ModelZombieVillager zombieVillagerModel;
	private final List field_177121_n;
	private final List field_177122_o;
	// private static final String __OBFID = "http://https://fuckuskid00001037";

	public RenderZombie(final RenderManager p_i46127_1_) {
		super(p_i46127_1_, new ModelZombie(), 0.5F, 1.0F);
		final LayerRenderer var2 = (LayerRenderer) field_177097_h.get(0);
		field_82434_o = modelBipedMain;
		zombieVillagerModel = new ModelZombieVillager();
		addLayer(new LayerHeldItem(this));
		final LayerBipedArmor var3 = new LayerBipedArmor(this) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002429";
			@Override
			protected void func_177177_a() {
				field_177189_c = new ModelZombie(0.5F, true);
				field_177186_d = new ModelZombie(1.0F, true);
			}
		};
		addLayer(var3);
		field_177122_o = Lists.newArrayList(field_177097_h);

		if (var2 instanceof LayerCustomHead) {
			func_177089_b(var2);
			addLayer(new LayerCustomHead(zombieVillagerModel.bipedHead));
		}

		func_177089_b(var3);
		addLayer(new LayerVillagerArmor(this));
		field_177121_n = Lists.newArrayList(field_177097_h);
	}

	public void func_180579_a(final EntityZombie p_180579_1_, final double p_180579_2_, final double p_180579_4_,
			final double p_180579_6_, final float p_180579_8_, final float p_180579_9_) {
		func_82427_a(p_180579_1_);
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	protected ResourceLocation func_180578_a(final EntityZombie p_180578_1_) {
		return p_180578_1_.isVillager() ? zombieVillagerTextures : zombieTextures;
	}

	private void func_82427_a(final EntityZombie p_82427_1_) {
		if (p_82427_1_.isVillager()) {
			mainModel = zombieVillagerModel;
			field_177097_h = field_177121_n;
		} else {
			mainModel = field_82434_o;
			field_177097_h = field_177122_o;
		}

		modelBipedMain = (ModelBiped) mainModel;
	}

	protected void rotateCorpse(final EntityZombie p_77043_1_, final float p_77043_2_, float p_77043_3_,
			final float p_77043_4_) {
		if (p_77043_1_.isConverting()) {
			p_77043_3_ += (float) (Math.cos(p_77043_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
		}

		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
		return func_180578_a((EntityZombie) p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180579_a((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_,
			final float p_77043_4_) {
		this.rotateCorpse((EntityZombie) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180579_a((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180578_a((EntityZombie) p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180579_a((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
