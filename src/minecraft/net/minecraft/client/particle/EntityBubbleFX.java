package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBubbleFX extends EntityFX {

public static final int EaZy = 638;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000898";

	protected EntityBubbleFX(final World worldIn, final double p_i1198_2_, final double p_i1198_4_,
			final double p_i1198_6_, final double p_i1198_8_, final double p_i1198_10_, final double p_i1198_12_) {
		super(worldIn, p_i1198_2_, p_i1198_4_, p_i1198_6_, p_i1198_8_, p_i1198_10_, p_i1198_12_);
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(32);
		setSize(0.02F, 0.02F);
		particleScale *= rand.nextFloat() * 0.6F + 0.2F;
		motionX = p_i1198_8_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
		motionY = p_i1198_10_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
		motionZ = p_i1198_12_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY += 0.002D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.8500000238418579D;
		motionY *= 0.8500000238418579D;
		motionZ *= 0.8500000238418579D;

		if (worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
			setDead();
		}

		if (particleMaxAge-- <= 0) {
			setDead();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002610";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityBubbleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
