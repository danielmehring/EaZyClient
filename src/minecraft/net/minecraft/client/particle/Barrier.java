package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {

public static final int EaZy = 633;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002615";

	protected Barrier(final World worldIn, final double p_i46286_2_, final double p_i46286_4_, final double p_i46286_6_,
			final Item p_i46286_8_) {
		super(worldIn, p_i46286_2_, p_i46286_4_, p_i46286_6_, 0.0D, 0.0D, 0.0D);
		func_180435_a(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(p_i46286_8_));
		particleRed = particleGreen = particleBlue = 1.0F;
		motionX = motionY = motionZ = 0.0D;
		particleGravity = 0.0F;
		particleMaxAge = 80;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		final float var9 = particleIcon.getMinU();
		final float var10 = particleIcon.getMaxU();
		final float var11 = particleIcon.getMinV();
		final float var12 = particleIcon.getMaxV();
		final float var13 = (float) (prevPosX + (posX - prevPosX) * p_180434_3_ - interpPosX);
		final float var14 = (float) (prevPosY + (posY - prevPosY) * p_180434_3_ - interpPosY);
		final float var15 = (float) (prevPosZ + (posZ - prevPosZ) * p_180434_3_ - interpPosZ);
		p_180434_1_.func_178986_b(particleRed, particleGreen, particleBlue);
		final float var16 = 0.5F;
		p_180434_1_.addVertexWithUV(var13 - p_180434_4_ * var16 - p_180434_7_ * var16, var14 - p_180434_5_ * var16,
				var15 - p_180434_6_ * var16 - p_180434_8_ * var16, var10, var12);
		p_180434_1_.addVertexWithUV(var13 - p_180434_4_ * var16 + p_180434_7_ * var16, var14 + p_180434_5_ * var16,
				var15 - p_180434_6_ * var16 + p_180434_8_ * var16, var10, var11);
		p_180434_1_.addVertexWithUV(var13 + p_180434_4_ * var16 + p_180434_7_ * var16, var14 + p_180434_5_ * var16,
				var15 + p_180434_6_ * var16 + p_180434_8_ * var16, var9, var11);
		p_180434_1_.addVertexWithUV(var13 + p_180434_4_ * var16 - p_180434_7_ * var16, var14 - p_180434_5_ * var16,
				var15 + p_180434_6_ * var16 - p_180434_8_ * var16, var9, var12);
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002614";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new Barrier(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Item.getItemFromBlock(Blocks.barrier));
		}
	}
}
