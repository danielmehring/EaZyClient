package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX extends EntityFX {

public static final int EaZy = 656;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_110127_a = new ResourceLocation("textures/entity/explosion.png");
	private int field_70581_a;
	private final int field_70584_aq;

	/** The Rendering Engine. */
	private final TextureManager theRenderEngine;
	private final float field_70582_as;
	// private static final String __OBFID = "http://https://fuckuskid00000910";

	protected EntityLargeExplodeFX(final TextureManager p_i1213_1_, final World worldIn, final double p_i1213_3_,
			final double p_i1213_5_, final double p_i1213_7_, final double p_i1213_9_, final double p_i1213_11_,
			final double p_i1213_13_) {
		super(worldIn, p_i1213_3_, p_i1213_5_, p_i1213_7_, 0.0D, 0.0D, 0.0D);
		theRenderEngine = p_i1213_1_;
		field_70584_aq = 6 + rand.nextInt(4);
		particleRed = particleGreen = particleBlue = rand.nextFloat() * 0.6F + 0.4F;
		field_70582_as = 1.0F - (float) p_i1213_9_ * 0.5F;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		final int var9 = (int) ((field_70581_a + p_180434_3_) * 15.0F / field_70584_aq);

		if (var9 <= 15) {
			theRenderEngine.bindTexture(field_110127_a);
			final float var10 = var9 % 4 / 4.0F;
			final float var11 = var10 + 0.24975F;
			final float var12 = var9 / 4 / 4.0F;
			final float var13 = var12 + 0.24975F;
			final float var14 = 2.0F * field_70582_as;
			final float var15 = (float) (prevPosX + (posX - prevPosX) * p_180434_3_ - interpPosX);
			final float var16 = (float) (prevPosY + (posY - prevPosY) * p_180434_3_ - interpPosY);
			final float var17 = (float) (prevPosZ + (posZ - prevPosZ) * p_180434_3_ - interpPosZ);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableLighting();
			RenderHelper.disableStandardItemLighting();
			p_180434_1_.startDrawingQuads();
			p_180434_1_.func_178960_a(particleRed, particleGreen, particleBlue, 1.0F);
			p_180434_1_.func_178980_d(0.0F, 1.0F, 0.0F);
			p_180434_1_.func_178963_b(240);
			p_180434_1_.addVertexWithUV(var15 - p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14,
					var17 - p_180434_6_ * var14 - p_180434_8_ * var14, var11, var13);
			p_180434_1_.addVertexWithUV(var15 - p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14,
					var17 - p_180434_6_ * var14 + p_180434_8_ * var14, var11, var12);
			p_180434_1_.addVertexWithUV(var15 + p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14,
					var17 + p_180434_6_ * var14 + p_180434_8_ * var14, var10, var12);
			p_180434_1_.addVertexWithUV(var15 + p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14,
					var17 + p_180434_6_ * var14 - p_180434_8_ * var14, var10, var13);
			Tessellator.getInstance().draw();
			GlStateManager.doPolygonOffset(0.0F, 0.0F);
			GlStateManager.enableLighting();
		}
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return 61680;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++field_70581_a;

		if (field_70581_a == field_70584_aq) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002598";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {

			return new EntityLargeExplodeFX(Minecraft.getTextureManager(), worldIn, p_178902_3_, p_178902_5_,
					p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
		}
	}
}
