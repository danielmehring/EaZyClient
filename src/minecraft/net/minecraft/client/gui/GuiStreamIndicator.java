package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

public class GuiStreamIndicator {

public static final int EaZy = 523;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation locationStreamIndicator = new ResourceLocation(
			"textures/gui/stream_indicator.png");
	private final Minecraft mc;
	private float field_152443_c = 1.0F;
	private int field_152444_d = 1;
	// private static final String __OBFID = "http://https://fuckuskid00001849";

	public GuiStreamIndicator(final Minecraft mcIn) {
		mc = mcIn;
	}

	public void render(final int p_152437_1_, final int p_152437_2_) {
		if (mc.getTwitchStream().func_152934_n()) {
			GlStateManager.enableBlend();
			final int var3 = mc.getTwitchStream().func_152920_A();

			if (var3 > 0) {
				final String var4 = "" + var3;
				final int var5 = mc.fontRendererObj.getStringWidth(var4);
				final int var7 = p_152437_1_ - var5 - 1;
				final int var8 = p_152437_2_ + 20 - 1;
				final int var10 = p_152437_2_ + 20 + mc.fontRendererObj.FONT_HEIGHT - 1;
				GlStateManager.disableTexture2D();
				final Tessellator var11 = Tessellator.getInstance();
				final WorldRenderer var12 = var11.getWorldRenderer();
				GlStateManager.color(0.0F, 0.0F, 0.0F, (0.65F + 0.35000002F * field_152443_c) / 2.0F);
				var12.startDrawingQuads();
				var12.addVertex(var7, var10, 0.0D);
				var12.addVertex(p_152437_1_, var10, 0.0D);
				var12.addVertex(p_152437_1_, var8, 0.0D);
				var12.addVertex(var7, var8, 0.0D);
				var11.draw();
				GlStateManager.enableTexture2D();
				mc.fontRendererObj.drawString(var4, p_152437_1_ - var5, p_152437_2_ + 20, 16777215);
			}

			this.render(p_152437_1_, p_152437_2_, func_152440_b(), 0);
			this.render(p_152437_1_, p_152437_2_, func_152438_c(), 17);
		}
	}

	private void render(final int p_152436_1_, final int p_152436_2_, final int p_152436_3_, final int p_152436_4_) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * field_152443_c);
		Minecraft.getTextureManager().bindTexture(locationStreamIndicator);
		final float var5 = 150.0F;
		final float var6 = 0.0F;
		final float var7 = p_152436_3_ * 0.015625F;
		final float var8 = 1.0F;
		final float var9 = (p_152436_3_ + 16) * 0.015625F;
		final Tessellator var10 = Tessellator.getInstance();
		final WorldRenderer var11 = var10.getWorldRenderer();
		var11.startDrawingQuads();
		var11.addVertexWithUV(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 16, var5, var6, var9);
		var11.addVertexWithUV(p_152436_1_ - p_152436_4_, p_152436_2_ + 16, var5, var8, var9);
		var11.addVertexWithUV(p_152436_1_ - p_152436_4_, p_152436_2_ + 0, var5, var8, var7);
		var11.addVertexWithUV(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 0, var5, var6, var7);
		var10.draw();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private int func_152440_b() {
		return mc.getTwitchStream().isPaused() ? 16 : 0;
	}

	private int func_152438_c() {
		return mc.getTwitchStream().func_152929_G() ? 48 : 32;
	}

	public void func_152439_a() {
		if (mc.getTwitchStream().func_152934_n()) {
			field_152443_c += 0.025F * field_152444_d;

			if (field_152443_c < 0.0F) {
				field_152444_d *= -1;
				field_152443_c = 0.0F;
			} else if (field_152443_c > 1.0F) {
				field_152444_d *= -1;
				field_152443_c = 1.0F;
			}
		} else {
			field_152443_c = 1.0F;
			field_152444_d = 1;
		}
	}
}
