package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {

public static final int EaZy = 652;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_110126_a = new ResourceLocation("textures/particle/footprint.png");
	private int footstepAge;
	private final int footstepMaxAge;
	private final TextureManager currentFootSteps;
	// private static final String __OBFID = "http://https://fuckuskid00000908";

	protected EntityFootStepFX(final TextureManager p_i1210_1_, final World worldIn, final double p_i1210_3_,
			final double p_i1210_5_, final double p_i1210_7_) {
		super(worldIn, p_i1210_3_, p_i1210_5_, p_i1210_7_, 0.0D, 0.0D, 0.0D);
		currentFootSteps = p_i1210_1_;
		motionX = motionY = motionZ = 0.0D;
		footstepMaxAge = 200;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (footstepAge + p_180434_3_) / footstepMaxAge;
		var9 *= var9;
		float var10 = 2.0F - var9 * 2.0F;

		if (var10 > 1.0F) {
			var10 = 1.0F;
		}

		var10 *= 0.2F;
		GlStateManager.disableLighting();
		final float var11 = 0.125F;
		final float var12 = (float) (posX - interpPosX);
		final float var13 = (float) (posY - interpPosY);
		final float var14 = (float) (posZ - interpPosZ);
		final float var15 = worldObj.getLightBrightness(new BlockPos(this));
		currentFootSteps.bindTexture(field_110126_a);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		p_180434_1_.startDrawingQuads();
		p_180434_1_.func_178960_a(var15, var15, var15, var10);
		p_180434_1_.addVertexWithUV(var12 - var11, var13, var14 + var11, 0.0D, 1.0D);
		p_180434_1_.addVertexWithUV(var12 + var11, var13, var14 + var11, 1.0D, 1.0D);
		p_180434_1_.addVertexWithUV(var12 + var11, var13, var14 - var11, 1.0D, 0.0D);
		p_180434_1_.addVertexWithUV(var12 - var11, var13, var14 - var11, 0.0D, 0.0D);
		Tessellator.getInstance().draw();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		++footstepAge;

		if (footstepAge == footstepMaxAge) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002601";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {

			return new EntityFootStepFX(Minecraft.getTextureManager(), worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
		}
	}
}
