package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer {

public static final int EaZy = 833;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
	// private static final String __OBFID = "http://https://fuckuskid00000962";

	public void func_180536_a(final TileEntityBeacon p_180536_1_, final double p_180536_2_, final double p_180536_4_,
			final double p_180536_6_, final float p_180536_8_, final int p_180536_9_) {
		final float var10 = p_180536_1_.shouldBeamRender();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

		if (var10 > 0.0F) {
			final Tessellator var11 = Tessellator.getInstance();
			final WorldRenderer var12 = var11.getWorldRenderer();
			final List var13 = p_180536_1_.func_174907_n();
			int var14 = 0;

			for (int var15 = 0; var15 < var13.size(); ++var15) {
				final TileEntityBeacon.BeamSegment var16 = (TileEntityBeacon.BeamSegment) var13.get(var15);
				final int var17 = var14 + var16.func_177264_c();
				bindTexture(beaconBeam);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
				GlStateManager.disableLighting();
				GlStateManager.disableCull();
				GlStateManager.disableBlend();
				GlStateManager.depthMask(true);
				GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
				final float var18 = p_180536_1_.getWorld().getTotalWorldTime() + p_180536_8_;
				final float var19 = -var18 * 0.2F - MathHelper.floor_float(-var18 * 0.1F);
				double var20 = var18 * 0.025D * -1.5D;
				var12.startDrawingQuads();
				double var22 = 0.2D;
				double var24 = 0.5D + Math.cos(var20 + 2.356194490192345D) * var22;
				double var26 = 0.5D + Math.sin(var20 + 2.356194490192345D) * var22;
				double var28 = 0.5D + Math.cos(var20 + Math.PI / 4D) * var22;
				double var30 = 0.5D + Math.sin(var20 + Math.PI / 4D) * var22;
				double var32 = 0.5D + Math.cos(var20 + 3.9269908169872414D) * var22;
				double var34 = 0.5D + Math.sin(var20 + 3.9269908169872414D) * var22;
				double var36 = 0.5D + Math.cos(var20 + 5.497787143782138D) * var22;
				double var38 = 0.5D + Math.sin(var20 + 5.497787143782138D) * var22;
				double var40 = 0.0D;
				double var42 = 1.0D;
				final double var44 = -1.0F + var19;
				final double var46 = var16.func_177264_c() * var10 * (0.5D / var22) + var44;
				var12.func_178960_a(var16.func_177263_b()[0], var16.func_177263_b()[1], var16.func_177263_b()[2],
						0.125F);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var26, var42, var46);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var26, var42, var44);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var30, var40, var44);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var30, var40, var46);
				var12.addVertexWithUV(p_180536_2_ + var36, p_180536_4_ + var17, p_180536_6_ + var38, var42, var46);
				var12.addVertexWithUV(p_180536_2_ + var36, p_180536_4_ + var14, p_180536_6_ + var38, var42, var44);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var14, p_180536_6_ + var34, var40, var44);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var17, p_180536_6_ + var34, var40, var46);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var30, var42, var46);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var30, var42, var44);
				var12.addVertexWithUV(p_180536_2_ + var36, p_180536_4_ + var14, p_180536_6_ + var38, var40, var44);
				var12.addVertexWithUV(p_180536_2_ + var36, p_180536_4_ + var17, p_180536_6_ + var38, var40, var46);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var17, p_180536_6_ + var34, var42, var46);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var14, p_180536_6_ + var34, var42, var44);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var26, var40, var44);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var26, var40, var46);
				var11.draw();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.depthMask(false);
				var12.startDrawingQuads();
				var12.func_178960_a(var16.func_177263_b()[0], var16.func_177263_b()[1], var16.func_177263_b()[2],
						0.125F);
				var20 = 0.2D;
				var22 = 0.2D;
				var24 = 0.8D;
				var26 = 0.2D;
				var28 = 0.2D;
				var30 = 0.8D;
				var32 = 0.8D;
				var34 = 0.8D;
				var36 = 0.0D;
				var38 = 1.0D;
				var40 = -1.0F + var19;
				var42 = var16.func_177264_c() * var10 + var40;
				var12.addVertexWithUV(p_180536_2_ + var20, p_180536_4_ + var17, p_180536_6_ + var22, var38, var42);
				var12.addVertexWithUV(p_180536_2_ + var20, p_180536_4_ + var14, p_180536_6_ + var22, var38, var40);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var26, var36, var40);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var26, var36, var42);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var17, p_180536_6_ + var34, var38, var42);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var14, p_180536_6_ + var34, var38, var40);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var30, var36, var40);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var30, var36, var42);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var17, p_180536_6_ + var26, var38, var42);
				var12.addVertexWithUV(p_180536_2_ + var24, p_180536_4_ + var14, p_180536_6_ + var26, var38, var40);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var14, p_180536_6_ + var34, var36, var40);
				var12.addVertexWithUV(p_180536_2_ + var32, p_180536_4_ + var17, p_180536_6_ + var34, var36, var42);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var17, p_180536_6_ + var30, var38, var42);
				var12.addVertexWithUV(p_180536_2_ + var28, p_180536_4_ + var14, p_180536_6_ + var30, var38, var40);
				var12.addVertexWithUV(p_180536_2_ + var20, p_180536_4_ + var14, p_180536_6_ + var22, var36, var40);
				var12.addVertexWithUV(p_180536_2_ + var20, p_180536_4_ + var17, p_180536_6_ + var22, var36, var42);
				var11.draw();
				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
				GlStateManager.depthMask(true);
				var14 = var17;
			}
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_180536_a((TileEntityBeacon) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}
}
