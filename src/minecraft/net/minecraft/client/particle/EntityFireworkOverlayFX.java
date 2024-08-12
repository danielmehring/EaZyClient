package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkOverlayFX extends EntityFX {

public static final int EaZy = 646;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000904";

	protected EntityFireworkOverlayFX(final World worldIn, final double p_i46357_2_, final double p_i46357_4_,
			final double p_i46357_6_) {
		super(worldIn, p_i46357_2_, p_i46357_4_, p_i46357_6_);
		particleMaxAge = 4;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		final float var9 = 0.25F;
		final float var10 = var9 + 0.25F;
		final float var11 = 0.125F;
		final float var12 = var11 + 0.25F;
		final float var13 = 7.1F * MathHelper.sin((particleAge + p_180434_3_ - 1.0F) * 0.25F * (float) Math.PI);
		particleAlpha = 0.6F - (particleAge + p_180434_3_ - 1.0F) * 0.25F * 0.5F;
		final float var14 = (float) (prevPosX + (posX - prevPosX) * p_180434_3_ - interpPosX);
		final float var15 = (float) (prevPosY + (posY - prevPosY) * p_180434_3_ - interpPosY);
		final float var16 = (float) (prevPosZ + (posZ - prevPosZ) * p_180434_3_ - interpPosZ);
		p_180434_1_.func_178960_a(particleRed, particleGreen, particleBlue, particleAlpha);
		p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13,
				var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
		p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13,
				var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
		p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13,
				var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
		p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13,
				var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
	}
}
