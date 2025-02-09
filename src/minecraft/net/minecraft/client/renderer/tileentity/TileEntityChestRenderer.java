package net.minecraft.client.renderer.tileentity;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.ChestESP;
import me.EaZy.client.utils.OutlineUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

import java.util.Calendar;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer {

public static final int EaZy = 834;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation textureTrappedDouble = new ResourceLocation(
			"textures/entity/chest/trapped_double.png");
	private static final ResourceLocation textureChristmasDouble = new ResourceLocation(
			"textures/entity/chest/christmas_double.png");
	private static final ResourceLocation textureNormalDouble = new ResourceLocation(
			"textures/entity/chest/normal_double.png");
	private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
	private static final ResourceLocation textureChristmas = new ResourceLocation(
			"textures/entity/chest/christmas.png");
	private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
	private final ModelChest simpleChest = new ModelChest();
	private final ModelChest largeChest = new ModelLargeChest();
	private boolean isChristams;

	public TileEntityChestRenderer() {
		final Calendar var1 = Calendar.getInstance();

		if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
			isChristams = true;
		}
	}

	public void func_180538_a(final TileEntityChest p_180538_1_, final double p_180538_2_, final double p_180538_4_,
			final double p_180538_6_, final float p_180538_8_, final int p_180538_9_) {
		int var10;

		if (!p_180538_1_.hasWorldObj()) {
			var10 = 0;
		} else {
			final Block var11 = p_180538_1_.getBlockType();
			var10 = p_180538_1_.getBlockMetadata();

			if (var11 instanceof BlockChest && var10 == 0) {
				((BlockChest) var11).checkForSurroundingChests(p_180538_1_.getWorld(), p_180538_1_.getPos(),
						p_180538_1_.getWorld().getBlockState(p_180538_1_.getPos()));
				var10 = p_180538_1_.getBlockMetadata();
			}

			p_180538_1_.checkForAdjacentChests();
		}

		if (p_180538_1_.adjacentChestZNeg == null && p_180538_1_.adjacentChestXNeg == null) {
			ModelChest var15;

			if (p_180538_1_.adjacentChestXPos == null && p_180538_1_.adjacentChestZPos == null) {
				var15 = simpleChest;

				if (p_180538_9_ >= 0) {
					bindTexture(DESTROY_STAGES[p_180538_9_]);
					GlStateManager.matrixMode(5890);
					GlStateManager.pushMatrix();
					GlStateManager.scale(4.0F, 4.0F, 1.0F);
					GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
					GlStateManager.matrixMode(5888);
				} else if (p_180538_1_.getChestType() == 1) {
					bindTexture(textureTrapped);
				} else if (isChristams) {
					bindTexture(textureChristmas);
				} else {
					bindTexture(textureNormal);
				}
			} else {
				var15 = largeChest;

				if (p_180538_9_ >= 0) {
					bindTexture(DESTROY_STAGES[p_180538_9_]);
					GlStateManager.matrixMode(5890);
					GlStateManager.pushMatrix();
					GlStateManager.scale(8.0F, 4.0F, 1.0F);
					GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
					GlStateManager.matrixMode(5888);
				} else if (p_180538_1_.getChestType() == 1) {
					bindTexture(textureTrappedDouble);
				} else if (isChristams) {
					bindTexture(textureChristmasDouble);
				} else {
					bindTexture(textureNormalDouble);
				}
			}

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();

			if (p_180538_9_ < 0) {
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			}

			GlStateManager.translate((float) p_180538_2_, (float) p_180538_4_ + 1.0F, (float) p_180538_6_ + 1.0F);
			GlStateManager.scale(1.0F, -1.0F, -1.0F);
			GlStateManager.translate(0.5F, 0.5F, 0.5F);
			short var12 = 0;

			if (var10 == 2) {
				var12 = 180;
			}

			if (var10 == 3) {
				var12 = 0;
			}

			if (var10 == 4) {
				var12 = 90;
			}

			if (var10 == 5) {
				var12 = -90;
			}

			if (var10 == 2 && p_180538_1_.adjacentChestXPos != null) {
				GlStateManager.translate(1.0F, 0.0F, 0.0F);
			}

			if (var10 == 5 && p_180538_1_.adjacentChestZPos != null) {
				GlStateManager.translate(0.0F, 0.0F, -1.0F);
			}

			GlStateManager.rotate(var12, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
			float var13 = p_180538_1_.prevLidAngle + (p_180538_1_.lidAngle - p_180538_1_.prevLidAngle) * p_180538_8_;
			float var14;

			if (p_180538_1_.adjacentChestZNeg != null) {
				var14 = p_180538_1_.adjacentChestZNeg.prevLidAngle
						+ (p_180538_1_.adjacentChestZNeg.lidAngle - p_180538_1_.adjacentChestZNeg.prevLidAngle)
								* p_180538_8_;

				if (var14 > var13) {
					var13 = var14;
				}
			}

			if (p_180538_1_.adjacentChestXNeg != null) {
				var14 = p_180538_1_.adjacentChestXNeg.prevLidAngle
						+ (p_180538_1_.adjacentChestXNeg.lidAngle - p_180538_1_.adjacentChestXNeg.prevLidAngle)
								* p_180538_8_;

				if (var14 > var13) {
					var13 = var14;
				}
			}

			var13 = 1.0F - var13;
			var13 = 1.0F - var13 * var13 * var13;
			var15.chestLid.rotateAngleX = -(var13 * (float) Math.PI / 2.0F);
			var15.renderAll();
			if (ChestESP.mod.isToggled()) {
				var15.renderAll();
				OutlineUtils.renderOne();
				var15.renderAll();
				OutlineUtils.renderTwo();
				var15.renderAll();
				OutlineUtils.renderThree();
				OutlineUtils.renderFour(false, p_180538_1_.getChestType() == 1);
				var15.renderAll();
				OutlineUtils.renderFive();
			}
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			if (p_180538_9_ >= 0) {
				GlStateManager.matrixMode(5890);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
			}
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_180538_a((TileEntityChest) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}
}
