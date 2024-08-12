package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntitySuspendFX extends EntityFX {

public static final int EaZy = 668;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000928";

	protected EntitySuspendFX(final World worldIn, final double p_i1231_2_, final double p_i1231_4_,
			final double p_i1231_6_, final double p_i1231_8_, final double p_i1231_10_, final double p_i1231_12_) {
		super(worldIn, p_i1231_2_, p_i1231_4_ - 0.125D, p_i1231_6_, p_i1231_8_, p_i1231_10_, p_i1231_12_);
		particleRed = 0.4F;
		particleGreen = 0.4F;
		particleBlue = 0.7F;
		setParticleTextureIndex(0);
		setSize(0.01F, 0.01F);
		particleScale *= rand.nextFloat() * 0.6F + 0.2F;
		motionX = p_i1231_8_ * 0.0D;
		motionY = p_i1231_10_ * 0.0D;
		motionZ = p_i1231_12_ * 0.0D;
		particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		moveEntity(motionX, motionY, motionZ);

		if (worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
			setDead();
		}

		if (particleMaxAge-- <= 0) {
			setDead();
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002579";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntitySuspendFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
