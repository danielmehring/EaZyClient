package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import org.lwjgl.opengl.GL11;

import optifine.Config;
import optifine.CustomColors;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer {

public static final int EaZy = 842;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");

	/** The ModelSign instance for use in this renderer */
	private final ModelSign model = new ModelSign();

	public void func_180541_a(final TileEntitySign p_180541_1_, final double p_180541_2_, final double p_180541_4_,
			final double p_180541_6_, final float p_180541_8_, final int p_180541_9_) {
		final Block var10 = p_180541_1_.getBlockType();
		GlStateManager.pushMatrix();
		final float var11 = 0.6666667F;
		float var13;

		if (var10 == Blocks.standing_sign) {
			GlStateManager.translate((float) p_180541_2_ + 0.5F, (float) p_180541_4_ + 0.75F * var11,
					(float) p_180541_6_ + 0.5F);
			final float var20 = p_180541_1_.getBlockMetadata() * 360 / 16.0F;
			GlStateManager.rotate(-var20, 0.0F, 1.0F, 0.0F);
			model.signStick.showModel = true;
		} else {
			final int var19 = p_180541_1_.getBlockMetadata();
			var13 = 0.0F;

			if (var19 == 2) {
				var13 = 180.0F;
			}

			if (var19 == 4) {
				var13 = 90.0F;
			}

			if (var19 == 5) {
				var13 = -90.0F;
			}

			GlStateManager.translate((float) p_180541_2_ + 0.5F, (float) p_180541_4_ + 0.75F * var11,
					(float) p_180541_6_ + 0.5F);
			GlStateManager.rotate(-var13, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
			model.signStick.showModel = false;
		}

		if (p_180541_9_ >= 0) {
			bindTexture(DESTROY_STAGES[p_180541_9_]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 2.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			bindTexture(field_147513_b);
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		GlStateManager.scale(var11, -var11, -var11);
		model.renderSign();
		GlStateManager.popMatrix();
		final FontRenderer var201 = getFontRenderer();
		var13 = 0.015625F * var11;
		GlStateManager.translate(0.0F, 0.5F * var11, 0.07F * var11);
		GlStateManager.scale(var13, -var13, var13);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * var13);
		GlStateManager.depthMask(false);
		int var14 = 0;

		if (Config.isCustomColors()) {
			var14 = CustomColors.getSignTextColor(var14);
		}

		if (p_180541_9_ < 0) {
			for (int var15 = 0; var15 < p_180541_1_.signText.length; ++var15) {
				if (p_180541_1_.signText[var15] != null) {
					final IChatComponent var16 = p_180541_1_.signText[var15];
					final List var17 = GuiUtilRenderComponents.func_178908_a(var16, 90, var201, false, true);
					String var18 = var17 != null && var17.size() > 0
							? ((IChatComponent) var17.get(0)).getFormattedText() : "";

					if (var15 == p_180541_1_.lineBeingEdited) {
						var18 = "> " + var18 + " <";
						var201.drawString(var18, -var201.getStringWidth(var18) / 2,
								var15 * 10 - p_180541_1_.signText.length * 5, var14);
					} else {
						var201.drawString(var18, -var201.getStringWidth(var18) / 2,
								var15 * 10 - p_180541_1_.signText.length * 5, var14);
					}
				}
			}
		}

		GlStateManager.depthMask(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();

		if (p_180541_9_ >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_180541_a((TileEntitySign) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}
}
