package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer {

	public static final int EaZy = 839;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final BlockRendererDispatcher field_178462_c = Minecraft.getMinecraft().getBlockRendererDispatcher();
	// private static final String __OBFID = "http://https://fuckuskid00002469";

	public void func_178461_a(final TileEntityPiston p_178461_1_, final double p_178461_2_, final double p_178461_4_,
			final double p_178461_6_, final float p_178461_8_, final int p_178461_9_) {
		final BlockPos var10 = p_178461_1_.getPos();
		IBlockState var11 = p_178461_1_.func_174927_b();
		final Block var12 = var11.getBlock();

		if (var12.getMaterial() != Material.air && p_178461_1_.func_145860_a(p_178461_8_) < 1.0F) {
			final Tessellator var13 = Tessellator.getInstance();
			final WorldRenderer var14 = var13.getWorldRenderer();
			bindTexture(TextureMap.locationBlocksTexture);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.blendFunc(770, 771);
			GlStateManager.enableBlend();
			GlStateManager.disableCull();

			if (Minecraft.isAmbientOcclusionEnabled()) {
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			} else {
				GlStateManager.shadeModel(7424);
			}

			var14.startDrawingQuads();
			var14.setVertexFormat(DefaultVertexFormats.field_176600_a);
			var14.setTranslation((float) p_178461_2_ - var10.getX() + p_178461_1_.func_174929_b(p_178461_8_),
					(float) p_178461_4_ - var10.getY() + p_178461_1_.func_174928_c(p_178461_8_),
					(float) p_178461_6_ - var10.getZ() + p_178461_1_.func_174926_d(p_178461_8_));
			var14.func_178986_b(1.0F, 1.0F, 1.0F);
			final World var15 = getWorld();

			if (var12 == Blocks.piston_head && p_178461_1_.func_145860_a(p_178461_8_) < 0.5F) {
				var11 = var11.withProperty(BlockPistonExtension.field_176327_M, true);
				field_178462_c.func_175019_b().renderBlockModel(var15,
						field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, true);
			} else if (p_178461_1_.shouldPistonHeadBeRendered() && !p_178461_1_.isExtending()) {
				final BlockPistonExtension.EnumPistonType var16 = var12 == Blocks.sticky_piston
						? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
				IBlockState var17 = Blocks.piston_head.getDefaultState()
						.withProperty(BlockPistonExtension.field_176325_b, var16)
						.withProperty(BlockPistonExtension.field_176326_a, var11.getValue(BlockPistonBase.FACING));
				var17 = var17.withProperty(BlockPistonExtension.field_176327_M,
						p_178461_1_.func_145860_a(p_178461_8_) >= 0.5F);
				field_178462_c.func_175019_b().renderBlockModel(var15,
						field_178462_c.getModelFromBlockState(var17, var15, var10), var17, var10, var14, true);
				var14.setTranslation((float) p_178461_2_ - var10.getX(), (float) p_178461_4_ - var10.getY(),
						(float) p_178461_6_ - var10.getZ());
				var11.withProperty(BlockPistonBase.EXTENDED, true);
				field_178462_c.func_175019_b().renderBlockModel(var15,
						field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, true);
			} else {
				field_178462_c.func_175019_b().renderBlockModel(var15,
						field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, false);
			}

			var14.setTranslation(0.0D, 0.0D, 0.0D);
			var13.draw();
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_178461_a((TileEntityPiston) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}
}
