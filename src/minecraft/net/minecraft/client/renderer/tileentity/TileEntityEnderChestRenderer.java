package net.minecraft.client.renderer.tileentity;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.ChestESP;
import me.EaZy.client.utils.OutlineUtils;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer {

public static final int EaZy = 836;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_147520_b = new ResourceLocation("textures/entity/chest/ender.png");
	private final ModelChest field_147521_c = new ModelChest();

	public void func_180540_a(final TileEntityEnderChest p_180540_1_, final double p_180540_2_,
			final double p_180540_4_, final double p_180540_6_, final float p_180540_8_, final int p_180540_9_) {
		int var10 = 0;

		if (p_180540_1_.hasWorldObj()) {
			var10 = p_180540_1_.getBlockMetadata();
		}

		if (p_180540_9_ >= 0) {
			bindTexture(DESTROY_STAGES[p_180540_9_]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			bindTexture(field_147520_b);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate((float) p_180540_2_, (float) p_180540_4_ + 1.0F, (float) p_180540_6_ + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		short var11 = 0;

		if (var10 == 2) {
			var11 = 180;
		}

		if (var10 == 3) {
			var11 = 0;
		}

		if (var10 == 4) {
			var11 = 90;
		}

		if (var10 == 5) {
			var11 = -90;
		}

		GlStateManager.rotate(var11, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float var12 = p_180540_1_.prevLidAngle + (p_180540_1_.field_145972_a - p_180540_1_.prevLidAngle) * p_180540_8_;
		var12 = 1.0F - var12;
		var12 = 1.0F - var12 * var12 * var12;
		field_147521_c.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
		field_147521_c.renderAll();
		if (ChestESP.mod.isToggled()) {
			field_147521_c.renderAll();
			OutlineUtils.renderOne();
			field_147521_c.renderAll();
			OutlineUtils.renderTwo();
			field_147521_c.renderAll();
			OutlineUtils.renderThree();
			OutlineUtils.renderFour(true, false);
			field_147521_c.renderAll();
			OutlineUtils.renderFive();
		}
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (p_180540_9_ >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_180540_a((TileEntityEnderChest) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_,
				p_180535_9_);
	}
}
