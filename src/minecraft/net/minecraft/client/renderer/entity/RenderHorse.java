package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import com.google.common.collect.Maps;

public class RenderHorse extends RenderLiving {

public static final int EaZy = 760;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Map field_110852_a = Maps.newHashMap();
	private static final ResourceLocation whiteHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_white.png");
	private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
	private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
	private static final ResourceLocation zombieHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_zombie.png");
	private static final ResourceLocation skeletonHorseTextures = new ResourceLocation(
			"textures/entity/horse/horse_skeleton.png");
	// private static final String __OBFID = "http://https://fuckuskid00001000";

	public RenderHorse(final RenderManager p_i46170_1_, final ModelHorse p_i46170_2_, final float p_i46170_3_) {
		super(p_i46170_1_, p_i46170_2_, p_i46170_3_);
	}

	protected void func_180580_a(final EntityHorse p_180580_1_, final float p_180580_2_) {
		float var3 = 1.0F;
		final int var4 = p_180580_1_.getHorseType();

		if (var4 == 1) {
			var3 *= 0.87F;
		} else if (var4 == 2) {
			var3 *= 0.92F;
		}

		GlStateManager.scale(var3, var3, var3);
		super.preRenderCallback(p_180580_1_, p_180580_2_);
	}

	protected ResourceLocation func_180581_a(final EntityHorse p_180581_1_) {
		if (!p_180581_1_.func_110239_cn()) {
			switch (p_180581_1_.getHorseType()) {
				case 0:
				default:
					return whiteHorseTextures;

				case 1:
					return donkeyTextures;

				case 2:
					return muleTextures;

				case 3:
					return zombieHorseTextures;

				case 4:
					return skeletonHorseTextures;
			}
		} else {
			return func_110848_b(p_180581_1_);
		}
	}

	private ResourceLocation func_110848_b(final EntityHorse p_110848_1_) {
		final String var2 = p_110848_1_.getHorseTexture();

		if (!p_110848_1_.func_175507_cI()) {
			return null;
		} else {
			ResourceLocation var3 = (ResourceLocation) field_110852_a.get(var2);

			if (var3 == null) {
				var3 = new ResourceLocation(var2);

				Minecraft.getTextureManager().loadTexture(var3,
						new LayeredTexture(p_110848_1_.getVariantTexturePaths()));
				field_110852_a.put(var2, var3);
			}

			return var3;
		}
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		func_180580_a((EntityHorse) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180581_a((EntityHorse) p_110775_1_);
	}
}
