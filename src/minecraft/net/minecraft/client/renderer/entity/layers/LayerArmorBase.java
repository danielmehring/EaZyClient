package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import com.google.common.collect.Maps;

import optifine.Config;
import optifine.CustomItems;
import optifine.Reflector;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public abstract class LayerArmorBase implements LayerRenderer {

public static final int EaZy = 714;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected static final ResourceLocation field_177188_b = new ResourceLocation(
			"textures/misc/enchanted_item_glint.png");
	protected ModelBase field_177189_c;
	protected ModelBase field_177186_d;
	private final RendererLivingEntity field_177190_a;
	private final float field_177187_e = 1.0F;
	private final float field_177184_f = 1.0F;
	private final float field_177185_g = 1.0F;
	private final float field_177192_h = 1.0F;
	private boolean field_177193_i;
	private static final Map field_177191_j = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002428";

	public LayerArmorBase(final RendererLivingEntity p_i46125_1_) {
		field_177190_a = p_i46125_1_;
		func_177177_a();
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		func_177182_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
				p_177141_8_, 4);
		func_177182_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
				p_177141_8_, 3);
		func_177182_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
				p_177141_8_, 2);
		func_177182_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
				p_177141_8_, 1);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	private void func_177182_a(final EntityLivingBase p_177182_1_, final float p_177182_2_, final float p_177182_3_,
			final float p_177182_4_, final float p_177182_5_, final float p_177182_6_, final float p_177182_7_,
			final float p_177182_8_, final int p_177182_9_) {
		final ItemStack var10 = func_177176_a(p_177182_1_, p_177182_9_);

		if (var10 != null && var10.getItem() instanceof ItemArmor) {
			final ItemArmor var11 = (ItemArmor) var10.getItem();
			ModelBase var12 = func_177175_a(p_177182_9_);
			var12.setModelAttributes(field_177190_a.getMainModel());
			var12.setLivingAnimations(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_4_);

			if (Reflector.ForgeHooksClient_getArmorModel.exists()) {
				var12 = (ModelBase) Reflector.call(Reflector.ForgeHooksClient_getArmorModel,
						new Object[] { p_177182_1_, var10, p_177182_9_, var12 });
			}

			func_177179_a(var12, p_177182_9_);
			final boolean var13 = func_177180_b(p_177182_9_);

			if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, (String) null)) {
				if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
					field_177190_a.bindTexture(getArmorResource(p_177182_1_, var10, var13 ? 2 : 1, (String) null));
				} else {
					field_177190_a.bindTexture(func_177181_a(var11, var13));
				}
			}

			int var14;
			float var15;
			float var16;
			float var17;

			if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
				var14 = var11.getColor(var10);

				if (var14 != -1) {
					var15 = (var14 >> 16 & 255) / 255.0F;
					var16 = (var14 >> 8 & 255) / 255.0F;
					var17 = (var14 & 255) / 255.0F;
					GlStateManager.color(field_177184_f * var15, field_177185_g * var16, field_177192_h * var17,
							field_177187_e);
					var12.render(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_,
							p_177182_8_);

					if (!Config.isCustomItems()
							|| !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, "overlay")) {
						field_177190_a.bindTexture(getArmorResource(p_177182_1_, var10, var13 ? 2 : 1, "overlay"));
					}
				}

				GlStateManager.color(field_177184_f, field_177185_g, field_177192_h, field_177187_e);
				var12.render(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);

				if (!field_177193_i && var10.isItemEnchanted()
						&& (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(p_177182_1_, var10, var12,
								p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_,
								p_177182_8_))) {
					func_177183_a(p_177182_1_, var12, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_,
							p_177182_7_, p_177182_8_);
				}

				return;
			}

			switch (LayerArmorBase.SwitchArmorMaterial.field_178747_a[var11.getArmorMaterial().ordinal()]) {
				case 1:
					var14 = var11.getColor(var10);
					var15 = (var14 >> 16 & 255) / 255.0F;
					var16 = (var14 >> 8 & 255) / 255.0F;
					var17 = (var14 & 255) / 255.0F;
					GlStateManager.color(field_177184_f * var15, field_177185_g * var16, field_177192_h * var17,
							field_177187_e);
					var12.render(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_,
							p_177182_8_);

					if (!Config.isCustomItems()
							|| !CustomItems.bindCustomArmorTexture(var10, var13 ? 2 : 1, "overlay")) {
						field_177190_a.bindTexture(func_177178_a(var11, var13, "overlay"));
					}

				case 2:
				case 3:
				case 4:
				case 5:
					GlStateManager.color(field_177184_f, field_177185_g, field_177192_h, field_177187_e);
					var12.render(p_177182_1_, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_,
							p_177182_8_);

				default:
					if (!field_177193_i && var10.isItemEnchanted()
							&& (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(p_177182_1_, var10,
									var12, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_,
									p_177182_8_))) {
						func_177183_a(p_177182_1_, var12, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_,
								p_177182_6_, p_177182_7_, p_177182_8_);
					}
			}
		}
	}

	public ItemStack func_177176_a(final EntityLivingBase p_177176_1_, final int p_177176_2_) {
		return p_177176_1_.getCurrentArmor(p_177176_2_ - 1);
	}

	public ModelBase func_177175_a(final int p_177175_1_) {
		return func_177180_b(p_177175_1_) ? field_177189_c : field_177186_d;
	}

	private boolean func_177180_b(final int p_177180_1_) {
		return p_177180_1_ == 2;
	}

	private void func_177183_a(final EntityLivingBase p_177183_1_, final ModelBase p_177183_2_, final float p_177183_3_,
			final float p_177183_4_, final float p_177183_5_, final float p_177183_6_, final float p_177183_7_,
			final float p_177183_8_, final float p_177183_9_) {
		if (!Config.isCustomItems() || CustomItems.isUseGlint()) {
			if (Config.isShaders()) {
				if (Shaders.isShadowPass) {
					return;
				}

				ShadersRender.layerArmorBaseDrawEnchantedGlintBegin();
			}

			final float var10 = p_177183_1_.ticksExisted + p_177183_5_;
			field_177190_a.bindTexture(field_177188_b);
			GlStateManager.enableBlend();
			GlStateManager.depthFunc(514);
			GlStateManager.depthMask(false);
			final float var11 = 0.5F;
			GlStateManager.color(var11, var11, var11, 1.0F);

			for (int var12 = 0; var12 < 2; ++var12) {
				GlStateManager.disableLighting();
				GlStateManager.blendFunc(768, 1);
				final float var13 = 0.76F;
				GlStateManager.color(0.5F * var13, 0.25F * var13, 0.8F * var13, 1.0F);
				GlStateManager.matrixMode(5890);
				GlStateManager.loadIdentity();
				final float var14 = 0.33333334F;
				GlStateManager.scale(var14, var14, var14);
				GlStateManager.rotate(30.0F - var12 * 60.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.translate(0.0F, var10 * (0.001F + var12 * 0.003F) * 20.0F, 0.0F);
				GlStateManager.matrixMode(5888);
				p_177183_2_.render(p_177183_1_, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_,
						p_177183_9_);
			}

			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(5888);
			GlStateManager.enableLighting();
			GlStateManager.depthMask(true);
			GlStateManager.depthFunc(515);
			GlStateManager.disableBlend();

			if (Config.isShaders()) {
				ShadersRender.layerArmorBaseDrawEnchantedGlintEnd();
			}
		}
	}

	private ResourceLocation func_177181_a(final ItemArmor p_177181_1_, final boolean p_177181_2_) {
		return func_177178_a(p_177181_1_, p_177181_2_, (String) null);
	}

	private ResourceLocation func_177178_a(final ItemArmor p_177178_1_, final boolean p_177178_2_,
			final String p_177178_3_) {
		final String var4 = String.format("textures/models/armor/%s_layer_%d%s.png",
				new Object[] { p_177178_1_.getArmorMaterial().func_179242_c(), p_177178_2_ ? 2 : 1,
						p_177178_3_ == null ? "" : String.format("_%s", new Object[] { p_177178_3_ }) });
		ResourceLocation var5 = (ResourceLocation) field_177191_j.get(var4);

		if (var5 == null) {
			var5 = new ResourceLocation(var4);
			field_177191_j.put(var4, var5);
		}

		return var5;
	}

	protected abstract void func_177177_a();

	protected abstract void func_177179_a(ModelBase var1, int var2);

	public ResourceLocation getArmorResource(final Entity entity, final ItemStack stack, final int slot,
			final String type) {
		stack.getItem();
		String texture = ((ItemArmor) stack.getItem()).getArmorMaterial().func_179242_c();
		String domain = "minecraft";
		final int idx = texture.indexOf(58);

		if (idx != -1) {
			domain = texture.substring(0, idx);
			texture = texture.substring(idx + 1);
		}

		String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", new Object[] { domain, texture, slot == 2 ? 2 : 1, type == null ? "" : String.format("_%s", new Object[] { type }) });
		s1 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture,
				new Object[] { entity, stack, s1, slot, type });
		ResourceLocation resourcelocation = (ResourceLocation) field_177191_j.get(s1);

		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			field_177191_j.put(s1, resourcelocation);
		}

		return resourcelocation;
	}

	static final class SwitchArmorMaterial {
		static final int[] field_178747_a = new int[ItemArmor.ArmorMaterial.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002427";

		static {
			try {
				field_178747_a[ItemArmor.ArmorMaterial.LEATHER.ordinal()] = 1;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_178747_a[ItemArmor.ArmorMaterial.CHAIN.ordinal()] = 2;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178747_a[ItemArmor.ArmorMaterial.IRON.ordinal()] = 3;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178747_a[ItemArmor.ArmorMaterial.GOLD.ordinal()] = 4;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178747_a[ItemArmor.ArmorMaterial.DIAMOND.ordinal()] = 5;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
