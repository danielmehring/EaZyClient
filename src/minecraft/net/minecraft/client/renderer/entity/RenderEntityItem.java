package net.minecraft.client.renderer.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.Configs;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render {

public static final int EaZy = 752;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderItem field_177080_a;
	private final Random field_177079_e = new Random();

	public RenderEntityItem(final RenderManager p_i46167_1_, final RenderItem p_i46167_2_) {
		super(p_i46167_1_);
		field_177080_a = p_i46167_2_;
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}

	private int func_177077_a(final EntityItem p_177077_1_, final double p_177077_2_, final double p_177077_4_,
			final double p_177077_6_, final float p_177077_8_, final IBakedModel p_177077_9_) {
		final ItemStack var10 = p_177077_1_.getEntityItem();
		final Item var11 = var10.getItem();

		if (var11 == null) {
			return 0;
		} else {
			final boolean var12 = p_177077_9_.isAmbientOcclusionEnabled();
			final int var13 = func_177078_a(var10);
			final float var15 = MathHelper
					.sin((p_177077_1_.func_174872_o() + p_177077_8_) / 10.0F + p_177077_1_.hoverStart) * 0.1F + 0.1F;
			GlStateManager.translate((float) p_177077_2_,
					(float) p_177077_4_ + (Configs.itemPhysics ? 0 : var15) + 0.25F, (float) p_177077_6_);
			float var16;

			if (var12 || renderManager.options != null && renderManager.options.fancyGraphics) {
				var16 = ((p_177077_1_.func_174872_o() + p_177077_8_) / 20.0F + p_177077_1_.hoverStart)
						* (180F / (float) Math.PI);
				if (Configs.itemPhysics) {
					GlStateManager.rotate(90.0F, 90.0F, 1.0F, 0.0F);
				} else {
					GlStateManager.rotate(var16, 0.0F, 1.0F, 0.0F);
				}
			}

			if (!var12) {
				var16 = -0.0F * (var13 - 1) * 0.5F;
				final float var17 = -0.0F * (var13 - 1) * 0.5F;
				final float var18 = -0.046875F * (var13 - 1) * 0.5F;
				GlStateManager.translate(var16, var17, var18);
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			return var13;
		}
	}

	private int func_177078_a(final ItemStack p_177078_1_) {
		byte var2 = 1;

		if (p_177078_1_.stackSize > 48) {
			var2 = 5;
		} else if (p_177078_1_.stackSize > 32) {
			var2 = 4;
		} else if (p_177078_1_.stackSize > 16) {
			var2 = 3;
		} else if (p_177078_1_.stackSize > 1) {
			var2 = 2;
		}

		return var2;
	}

	public void func_177075_a(final EntityItem p_177075_1_, final double p_177075_2_, final double p_177075_4_,
			final double p_177075_6_, final float p_177075_8_, final float p_177075_9_) {
		final ItemStack var10 = p_177075_1_.getEntityItem();
		field_177079_e.setSeed(187L);
		boolean var11 = false;

		if (bindEntityTexture(p_177075_1_)) {
			renderManager.renderEngine.getTexture(func_177076_a(p_177075_1_)).func_174936_b(false, false);
			var11 = true;
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.pushMatrix();
		final IBakedModel var12 = field_177080_a.getItemModelMesher().getItemModel(var10);
		final int var13 = func_177077_a(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_9_, var12);

		for (int var14 = 0; var14 < var13; ++var14) {
			if (var12.isAmbientOcclusionEnabled()) {
				GlStateManager.pushMatrix();

				if (var14 > 0) {
					final float var15 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
					final float var16 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
					final float var17 = (field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
					GlStateManager.translate(var15, var16, var17);
				}
				// TODO: EaZy Item Size Item Render Size
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				field_177080_a.func_180454_a(var10, var12);
				GlStateManager.popMatrix();
			} else {
				field_177080_a.func_180454_a(var10, var12);
				GlStateManager.translate(0.0F, 0.0F, 0.046875F);
			}
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
		bindEntityTexture(p_177075_1_);

		if (var11) {
			renderManager.renderEngine.getTexture(func_177076_a(p_177075_1_)).func_174935_a();
		}

		super.doRender(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_8_, p_177075_9_);
	}

	protected ResourceLocation func_177076_a(final EntityItem p_177076_1_) {
		return TextureMap.locationBlocksTexture;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_177076_a((EntityItem) p_110775_1_);
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
		func_177075_a((EntityItem) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
