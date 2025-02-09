package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class LayerCustomHead implements LayerRenderer {

public static final int EaZy = 719;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ModelRenderer field_177209_a;
	// private static final String __OBFID = "http://https://fuckuskid00002422";

	public LayerCustomHead(final ModelRenderer p_i46120_1_) {
		field_177209_a = p_i46120_1_;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		final ItemStack var9 = p_177141_1_.getCurrentArmor(3);

		if (var9 != null && var9.getItem() != null) {
			final Item var10 = var9.getItem();
			final Minecraft var11 = Minecraft.getMinecraft();
			GlStateManager.pushMatrix();

			if (p_177141_1_.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}

			final boolean var12 = p_177141_1_ instanceof EntityVillager
					|| p_177141_1_ instanceof EntityZombie && ((EntityZombie) p_177141_1_).isVillager();
			float var13;

			if (!var12 && p_177141_1_.isChild()) {
				var13 = 2.0F;
				final float var14 = 1.4F;
				GlStateManager.scale(var14 / var13, var14 / var13, var14 / var13);
				GlStateManager.translate(0.0F, 16.0F * p_177141_8_, 0.0F);
			}

			field_177209_a.postRender(0.0625F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			if (var10 instanceof ItemBlock) {
				var13 = 0.625F;
				GlStateManager.translate(0.0F, -0.25F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.scale(var13, -var13, -var13);

				if (var12) {
					GlStateManager.translate(0.0F, 0.1875F, 0.0F);
				}

				var11.getItemRenderer().renderItem(p_177141_1_, var9, ItemCameraTransforms.TransformType.HEAD);
			} else if (var10 == Items.skull) {
				var13 = 1.1875F;
				GlStateManager.scale(var13, -var13, -var13);

				if (var12) {
					GlStateManager.translate(0.0F, 0.0625F, 0.0F);
				}

				GameProfile var16 = null;

				if (var9.hasTagCompound()) {
					final NBTTagCompound var15 = var9.getTagCompound();

					if (var15.hasKey("SkullOwner", 10)) {
						var16 = NBTUtil.readGameProfileFromNBT(var15.getCompoundTag("SkullOwner"));
					} else if (var15.hasKey("SkullOwner", 8)) {
						var16 = TileEntitySkull
								.updateGameprofile(new GameProfile((UUID) null, var15.getString("SkullOwner")));
						var15.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var16));
					}
				}

				TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F,
						var9.getMetadata(), var16, -1);
			}

			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
