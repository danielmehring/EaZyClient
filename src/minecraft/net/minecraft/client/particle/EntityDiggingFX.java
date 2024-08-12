package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFX extends EntityFX {

public static final int EaZy = 642;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IBlockState field_174847_a;
	// private static final String __OBFID = "http://https://fuckuskid00000932";

	protected EntityDiggingFX(final World worldIn, final double p_i46280_2_, final double p_i46280_4_,
			final double p_i46280_6_, final double p_i46280_8_, final double p_i46280_10_, final double p_i46280_12_,
			final IBlockState p_i46280_14_) {
		super(worldIn, p_i46280_2_, p_i46280_4_, p_i46280_6_, p_i46280_8_, p_i46280_10_, p_i46280_12_);
		field_174847_a = p_i46280_14_;
		func_180435_a(
				Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178122_a(p_i46280_14_));
		particleGravity = p_i46280_14_.getBlock().blockParticleGravity;
		particleRed = particleGreen = particleBlue = 0.6F;
		particleScale /= 2.0F;
	}

	public EntityDiggingFX func_174846_a(final BlockPos p_174846_1_) {
		if (field_174847_a.getBlock() == Blocks.grass) {
			return this;
		} else {
			final int var2 = field_174847_a.getBlock().colorMultiplier(worldObj, p_174846_1_);
			particleRed *= (var2 >> 16 & 255) / 255.0F;
			particleGreen *= (var2 >> 8 & 255) / 255.0F;
			particleBlue *= (var2 & 255) / 255.0F;
			return this;
		}
	}

	public EntityDiggingFX func_174845_l() {
		final Block var1 = field_174847_a.getBlock();

		if (var1 == Blocks.grass) {
			return this;
		} else {
			final int var2 = var1.getRenderColor(field_174847_a);
			particleRed *= (var2 >> 16 & 255) / 255.0F;
			particleGreen *= (var2 >> 8 & 255) / 255.0F;
			particleBlue *= (var2 & 255) / 255.0F;
			return this;
		}
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (particleTextureIndexX + particleTextureJitterX / 4.0F) / 16.0F;
		float var10 = var9 + 0.015609375F;
		float var11 = (particleTextureIndexY + particleTextureJitterY / 4.0F) / 16.0F;
		float var12 = var11 + 0.015609375F;
		final float var13 = 0.1F * particleScale;

		if (particleIcon != null) {
			var9 = particleIcon.getInterpolatedU(particleTextureJitterX / 4.0F * 16.0F);
			var10 = particleIcon.getInterpolatedU((particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
			var11 = particleIcon.getInterpolatedV(particleTextureJitterY / 4.0F * 16.0F);
			var12 = particleIcon.getInterpolatedV((particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
		}

		final float var14 = (float) (prevPosX + (posX - prevPosX) * p_180434_3_ - interpPosX);
		final float var15 = (float) (prevPosY + (posY - prevPosY) * p_180434_3_ - interpPosY);
		final float var16 = (float) (prevPosZ + (posZ - prevPosZ) * p_180434_3_ - interpPosZ);
		p_180434_1_.func_178986_b(particleRed, particleGreen, particleBlue);
		p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13,
				var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
		p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13,
				var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
		p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13,
				var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
		p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13,
				var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002575";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityDiggingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_, Block.getStateById(p_178902_15_[0])).func_174845_l();
		}
	}
}
