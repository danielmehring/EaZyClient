package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer {

public static final int EaZy = 726;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderWitch field_177144_a;
	// private static final String __OBFID = "http://https://fuckuskid00002407";

	public LayerHeldItemWitch(final RenderWitch p_i46106_1_) {
		field_177144_a = p_i46106_1_;
	}

	public void func_177143_a(final EntityWitch p_177143_1_, final float p_177143_2_, final float p_177143_3_,
			final float p_177143_4_, final float p_177143_5_, final float p_177143_6_, final float p_177143_7_,
			final float p_177143_8_) {
		final ItemStack var9 = p_177143_1_.getHeldItem();

		if (var9 != null) {
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.pushMatrix();

			if (field_177144_a.getMainModel().isChild) {
				GlStateManager.translate(0.0F, 0.625F, 0.0F);
				GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
				final float var10 = 0.5F;
				GlStateManager.scale(var10, var10, var10);
			}

			((ModelWitch) field_177144_a.getMainModel()).villagerNose.postRender(0.0625F);
			GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
			final Item var13 = var9.getItem();
			final Minecraft var11 = Minecraft.getMinecraft();
			float var12;

			if (var13 instanceof ItemBlock && var11.getBlockRendererDispatcher()
					.func_175021_a(Block.getBlockFromItem(var13), var9.getMetadata())) {
				GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
				GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
				var12 = 0.375F;
				GlStateManager.scale(var12, -var12, var12);
			} else if (var13 == Items.bow) {
				GlStateManager.translate(0.0F, 0.125F, 0.3125F);
				GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
				var12 = 0.625F;
				GlStateManager.scale(var12, -var12, var12);
				GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (var13.isFull3D()) {
				if (var13.shouldRotateAroundWhenRendering()) {
					GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
					GlStateManager.translate(0.0F, -0.125F, 0.0F);
				}

				field_177144_a.func_82422_c();
				var12 = 0.625F;
				GlStateManager.scale(var12, -var12, var12);
				GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
				var12 = 0.375F;
				GlStateManager.scale(var12, var12, var12);
				GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
			}

			GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
			var11.getItemRenderer().renderItem(p_177143_1_, var9, ItemCameraTransforms.TransformType.THIRD_PERSON);
			GlStateManager.popMatrix();
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
		func_177143_a((EntityWitch) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
