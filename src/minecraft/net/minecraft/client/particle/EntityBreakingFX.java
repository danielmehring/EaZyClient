package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFX extends EntityFX {

public static final int EaZy = 637;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000897";

	protected EntityBreakingFX(final World worldIn, final double p_i1195_2_, final double p_i1195_4_,
			final double p_i1195_6_, final Item p_i1195_8_) {
		this(worldIn, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_, 0);
	}

	protected EntityBreakingFX(final World worldIn, final double p_i1197_2_, final double p_i1197_4_,
			final double p_i1197_6_, final double p_i1197_8_, final double p_i1197_10_, final double p_i1197_12_,
			final Item p_i1197_14_, final int p_i1197_15_) {
		this(worldIn, p_i1197_2_, p_i1197_4_, p_i1197_6_, p_i1197_14_, p_i1197_15_);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += p_i1197_8_;
		motionY += p_i1197_10_;
		motionZ += p_i1197_12_;
	}

	protected EntityBreakingFX(final World worldIn, final double p_i1196_2_, final double p_i1196_4_,
			final double p_i1196_6_, final Item p_i1196_8_, final int p_i1196_9_) {
		super(worldIn, p_i1196_2_, p_i1196_4_, p_i1196_6_, 0.0D, 0.0D, 0.0D);
		func_180435_a(
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(p_i1196_8_, p_i1196_9_));
		particleRed = particleGreen = particleBlue = 1.0F;
		particleGravity = Blocks.snow.blockParticleGravity;
		particleScale /= 2.0F;
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
		// "http://https://fuckuskid00002613";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			final int var16 = p_178902_15_.length > 1 ? p_178902_15_[1] : 0;
			return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_, Item.getItemById(p_178902_15_[0]), var16);
		}
	}

	public static class SlimeFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002612";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Items.slime_ball);
		}
	}

	public static class SnowballFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002611";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Items.snowball);
		}
	}
}
