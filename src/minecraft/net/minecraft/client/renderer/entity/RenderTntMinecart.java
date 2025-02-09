package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class RenderTntMinecart extends RenderMinecart {

public static final int EaZy = 786;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001029";

	public RenderTntMinecart(final RenderManager p_i46135_1_) {
		super(p_i46135_1_);
	}

	protected void func_180561_a(final EntityMinecartTNT p_180561_1_, final float p_180561_2_,
			final IBlockState p_180561_3_) {
		final int var4 = p_180561_1_.func_94104_d();

		if (var4 > -1 && var4 - p_180561_2_ + 1.0F < 10.0F) {
			float var5 = 1.0F - (var4 - p_180561_2_ + 1.0F) / 10.0F;
			var5 = MathHelper.clamp_float(var5, 0.0F, 1.0F);
			var5 *= var5;
			var5 *= var5;
			final float var6 = 1.0F + var5 * 0.3F;
			GlStateManager.scale(var6, var6, var6);
		}

		super.func_180560_a(p_180561_1_, p_180561_2_, p_180561_3_);

		if (var4 > -1 && var4 / 5 % 2 == 0) {
			final BlockRendererDispatcher var7 = Minecraft.getMinecraft().getBlockRendererDispatcher();
			GlStateManager.disableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 772);
			GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - (var4 - p_180561_2_ + 1.0F) / 100.0F) * 0.8F);
			GlStateManager.pushMatrix();
			var7.func_175016_a(Blocks.tnt.getDefaultState(), 1.0F);
			GlStateManager.popMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
		}
	}

	@Override
	protected void func_180560_a(final EntityMinecart p_180560_1_, final float p_180560_2_,
			final IBlockState p_180560_3_) {
		func_180561_a((EntityMinecartTNT) p_180560_1_, p_180560_2_, p_180560_3_);
	}
}
