package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer {

public static final int EaZy = 725;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RendererLivingEntity field_177206_a;
	// private static final String __OBFID = "http://https://fuckuskid00002416";

	public LayerHeldItem(final RendererLivingEntity p_i46115_1_) {
		field_177206_a = p_i46115_1_;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		ItemStack var9 = p_177141_1_.getHeldItem();

		if (var9 != null) {
			GlStateManager.pushMatrix();

			if (field_177206_a.getMainModel().isChild) {
				final float var10 = 0.5F;
				GlStateManager.translate(0.0F, 0.625F, 0.0F);
				GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
				GlStateManager.scale(var10, var10, var10);
			}

			((ModelBiped) field_177206_a.getMainModel()).postRenderHiddenArm(0.0625F);
			GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);

			if (p_177141_1_ instanceof EntityPlayer && ((EntityPlayer) p_177141_1_).fishEntity != null) {
				var9 = new ItemStack(Items.fishing_rod, 0);
			}

			final Item var13 = var9.getItem();
			final Minecraft var11 = Minecraft.getMinecraft();

			if (var13 instanceof ItemBlock && Block.getBlockFromItem(var13).getRenderType() == 2) {
				GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
				GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
				final float var12 = 0.375F;
				GlStateManager.scale(-var12, -var12, var12);
			}

			var11.getItemRenderer().renderItem(p_177141_1_, var9, ItemCameraTransforms.TransformType.THIRD_PERSON);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
