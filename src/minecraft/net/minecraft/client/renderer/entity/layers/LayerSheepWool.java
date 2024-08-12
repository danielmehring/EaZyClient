package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

import optifine.Config;
import optifine.CustomColors;

public class LayerSheepWool implements LayerRenderer {

public static final int EaZy = 731;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private final RenderSheep sheepRenderer;
	private final ModelSheep1 sheepModel = new ModelSheep1();
	// private static final String __OBFID = "http://https://fuckuskid00002413";

	public LayerSheepWool(final RenderSheep p_i46112_1_) {
		sheepRenderer = p_i46112_1_;
	}

	public void doRenderLayer(final EntitySheep p_177162_1_, final float p_177162_2_, final float p_177162_3_,
			final float p_177162_4_, final float p_177162_5_, final float p_177162_6_, final float p_177162_7_,
			final float p_177162_8_) {
		if (!p_177162_1_.getSheared() && !p_177162_1_.isInvisible()) {
			sheepRenderer.bindTexture(TEXTURE);

			if (p_177162_1_.hasCustomName() && "jeb_".equals(p_177162_1_.getCustomNameTag())) {
				final int var10 = p_177162_1_.ticksExisted / 25 + p_177162_1_.getEntityId();
				final int var11 = EnumDyeColor.values().length;
				final int var12 = var10 % var11;
				final int var13 = (var10 + 1) % var11;
				final float var14 = (p_177162_1_.ticksExisted % 25 + p_177162_4_) / 25.0F;
				float[] var15 = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(var12));
				float[] var16 = EntitySheep.func_175513_a(EnumDyeColor.func_176764_b(var13));

				if (Config.isCustomColors()) {
					var15 = CustomColors.getSheepColors(EnumDyeColor.func_176764_b(var12), var15);
					var16 = CustomColors.getSheepColors(EnumDyeColor.func_176764_b(var13), var16);
				}

				GlStateManager.color(var15[0] * (1.0F - var14) + var16[0] * var14,
						var15[1] * (1.0F - var14) + var16[1] * var14, var15[2] * (1.0F - var14) + var16[2] * var14);
			} else {
				float[] var9 = EntitySheep.func_175513_a(p_177162_1_.func_175509_cj());

				if (Config.isCustomColors()) {
					var9 = CustomColors.getSheepColors(p_177162_1_.func_175509_cj(), var9);
				}

				GlStateManager.color(var9[0], var9[1], var9[2]);
			}

			sheepModel.setModelAttributes(sheepRenderer.getMainModel());
			sheepModel.setLivingAnimations(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_4_);
			sheepModel.render(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_,
					p_177162_8_);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		this.doRenderLayer((EntitySheep) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
