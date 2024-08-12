package net.minecraft.client.renderer.entity.layers;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer {

	public static final int EaZy = 721;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	// private static final String __OBFID = "http://https://fuckuskid00002420";

	public void func_177213_a(final EntityDragon p_177213_1_, final float p_177213_2_, final float p_177213_3_,
			final float p_177213_4_, final float p_177213_5_, final float p_177213_6_, final float p_177213_7_,
			final float p_177213_8_) {
		if (p_177213_1_.deathTicks > 0) {
			final Tessellator var9 = Tessellator.getInstance();
			final WorldRenderer var10 = var9.getWorldRenderer();
			RenderHelper.disableStandardItemLighting();
			final float var11 = (p_177213_1_.deathTicks + p_177213_4_) / 200.0F;
			float var12 = 0.0F;

			if (var11 > 0.8F) {
				var12 = (var11 - 0.8F) / 0.2F;
			}

			final Random var13 = new Random(432L);
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 1);
			GlStateManager.disableAlpha();
			GlStateManager.enableCull();
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -1.0F, -2.0F);

			for (int var14 = 0; var14 < (var11 + var11 * var11) / 2.0F * 60.0F; ++var14) {
				GlStateManager.rotate(var13.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(var13.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(var13.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(var13.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(var13.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(var13.nextFloat() * 360.0F + var11 * 90.0F, 0.0F, 0.0F, 1.0F);
				var10.startDrawing(6);
				final float var15 = var13.nextFloat() * 20.0F + 5.0F + var12 * 10.0F;
				final float var16 = var13.nextFloat() * 2.0F + 1.0F + var12 * 2.0F;
				var10.func_178974_a(16777215, (int) (255.0F * (1.0F - var12)));
				var10.addVertex(0.0D, 0.0D, 0.0D);
				var10.func_178974_a(16711935, 0);
				var10.addVertex(-0.866D * var16, var15, -0.5F * var16);
				var10.addVertex(0.866D * var16, var15, -0.5F * var16);
				var10.addVertex(0.0D, var15, 1.0F * var16);
				var10.addVertex(-0.866D * var16, var15, -0.5F * var16);
				var9.draw();
			}

			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
			GlStateManager.disableCull();
			GlStateManager.disableBlend();
			GlStateManager.shadeModel(7424);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		func_177213_a((EntityDragon) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
