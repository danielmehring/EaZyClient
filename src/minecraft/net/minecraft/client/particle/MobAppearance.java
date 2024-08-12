package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobAppearance extends EntityFX {

public static final int EaZy = 670;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private EntityLivingBase field_174844_a;
	// private static final String __OBFID = "http://https://fuckuskid00002594";

	protected MobAppearance(final World worldIn, final double p_i46283_2_, final double p_i46283_4_,
			final double p_i46283_6_) {
		super(worldIn, p_i46283_2_, p_i46283_4_, p_i46283_6_, 0.0D, 0.0D, 0.0D);
		particleRed = particleGreen = particleBlue = 1.0F;
		motionX = motionY = motionZ = 0.0D;
		particleGravity = 0.0F;
		particleMaxAge = 30;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (field_174844_a == null) {
			final EntityGuardian var1 = new EntityGuardian(worldObj);
			var1.func_175465_cm();
			field_174844_a = var1;
		}
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		if (field_174844_a != null) {

			final RenderManager var9 = Minecraft.getRenderManager();
			var9.func_178628_a(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
			final float var10 = 0.42553192F;
			final float var11 = (particleAge + p_180434_3_) / particleMaxAge;
			GlStateManager.depthMask(true);
			GlStateManager.enableBlend();
			GlStateManager.enableDepth();
			GlStateManager.blendFunc(770, 771);
			final float var12 = 240.0F;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var12, var12);
			GlStateManager.pushMatrix();
			final float var13 = 0.05F + 0.5F * MathHelper.sin(var11 * (float) Math.PI);
			GlStateManager.color(1.0F, 1.0F, 1.0F, var13);
			GlStateManager.translate(0.0F, 1.8F, 0.0F);
			GlStateManager.rotate(180.0F - p_180434_2_.rotationYaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(60.0F - 150.0F * var11 - p_180434_2_.rotationPitch, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.4F, -1.5F);
			GlStateManager.scale(var10, var10, var10);
			field_174844_a.rotationYaw = field_174844_a.prevRotationYaw = 0.0F;
			field_174844_a.rotationYawHead = field_174844_a.prevRotationYawHead = 0.0F;
			var9.renderEntityWithPosYaw(field_174844_a, 0.0D, 0.0D, 0.0D, 0.0F, p_180434_3_);
			GlStateManager.popMatrix();
			GlStateManager.enableDepth();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002593";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new MobAppearance(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
		}
	}
}
