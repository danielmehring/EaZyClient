package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer {

public static final int EaZy = 724;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderEnderman field_177174_a;
	// private static final String __OBFID = "http://https://fuckuskid00002424";

	public LayerHeldBlock(final RenderEnderman p_i46122_1_) {
		field_177174_a = p_i46122_1_;
	}

	public void func_177173_a(final EntityEnderman p_177173_1_, final float p_177173_2_, final float p_177173_3_,
			final float p_177173_4_, final float p_177173_5_, final float p_177173_6_, final float p_177173_7_,
			final float p_177173_8_) {
		final IBlockState var9 = p_177173_1_.func_175489_ck();

		if (var9.getBlock().getMaterial() != Material.air) {
			final BlockRendererDispatcher var10 = Minecraft.getMinecraft().getBlockRendererDispatcher();
			GlStateManager.enableRescaleNormal();
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.6875F, -0.75F);
			GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.25F, 0.1875F, 0.25F);
			final float var11 = 0.5F;
			GlStateManager.scale(-var11, -var11, var11);
			final int var12 = p_177173_1_.getBrightnessForRender(p_177173_4_);
			final int var13 = var12 % 65536;
			final int var14 = var12 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var13 / 1.0F, var14 / 1.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			field_177174_a.bindTexture(TextureMap.locationBlocksTexture);
			var10.func_175016_a(var9, 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
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
		func_177173_a((EntityEnderman) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
