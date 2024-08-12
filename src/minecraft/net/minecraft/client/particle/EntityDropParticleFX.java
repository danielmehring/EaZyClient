package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticleFX extends EntityFX {

public static final int EaZy = 643;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** the material type for dropped items/blocks */
	private final Material materialType;

	/** The height of the current bob */
	private int bobTimer;
	// private static final String __OBFID = "http://https://fuckuskid00000901";

	protected EntityDropParticleFX(final World worldIn, final double p_i1203_2_, final double p_i1203_4_,
			final double p_i1203_6_, final Material p_i1203_8_) {
		super(worldIn, p_i1203_2_, p_i1203_4_, p_i1203_6_, 0.0D, 0.0D, 0.0D);
		motionX = motionY = motionZ = 0.0D;

		if (p_i1203_8_ == Material.water) {
			particleRed = 0.0F;
			particleGreen = 0.0F;
			particleBlue = 1.0F;
		} else {
			particleRed = 1.0F;
			particleGreen = 0.0F;
			particleBlue = 0.0F;
		}

		setParticleTextureIndex(113);
		setSize(0.01F, 0.01F);
		particleGravity = 0.06F;
		materialType = p_i1203_8_;
		bobTimer = 40;
		particleMaxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
		motionX = motionY = motionZ = 0.0D;
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return materialType == Material.water ? super.getBrightnessForRender(p_70070_1_) : 257;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return materialType == Material.water ? super.getBrightness(p_70013_1_) : 1.0F;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (materialType == Material.water) {
			particleRed = 0.2F;
			particleGreen = 0.3F;
			particleBlue = 1.0F;
		} else {
			particleRed = 1.0F;
			particleGreen = 16.0F / (40 - bobTimer + 16);
			particleBlue = 4.0F / (40 - bobTimer + 8);
		}

		motionY -= particleGravity;

		if (bobTimer-- > 0) {
			motionX *= 0.02D;
			motionY *= 0.02D;
			motionZ *= 0.02D;
			setParticleTextureIndex(113);
		} else {
			setParticleTextureIndex(112);
		}

		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if (particleMaxAge-- <= 0) {
			setDead();
		}

		if (onGround) {
			if (materialType == Material.water) {
				setDead();
				worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			} else {
				setParticleTextureIndex(114);
			}

			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}

		final BlockPos var1 = new BlockPos(this);
		final IBlockState var2 = worldObj.getBlockState(var1);
		final Material var3 = var2.getBlock().getMaterial();

		if (var3.isLiquid() || var3.isSolid()) {
			double var4 = 0.0D;

			if (var2.getBlock() instanceof BlockLiquid) {
				var4 = BlockLiquid.getLiquidHeightPercent(((Integer) var2.getValue(BlockLiquid.LEVEL)));
			}

			final double var6 = MathHelper.floor_double(posY) + 1 - var4;

			if (posY < var6) {
				setDead();
			}
		}
	}

	public static class LavaFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002607";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityDropParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Material.lava);
		}
	}

	public static class WaterFactory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002606";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityDropParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Material.water);
		}
	}
}
