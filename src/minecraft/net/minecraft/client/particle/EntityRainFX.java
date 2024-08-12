package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRainFX extends EntityFX {

public static final int EaZy = 662;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000934";

	protected EntityRainFX(final World worldIn, final double p_i1235_2_, final double p_i1235_4_,
			final double p_i1235_6_) {
		super(worldIn, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.30000001192092896D;
		motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
		motionZ *= 0.30000001192092896D;
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(19 + rand.nextInt(4));
		setSize(0.01F, 0.01F);
		particleGravity = 0.06F;
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
		motionY -= particleGravity;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if (particleMaxAge-- <= 0) {
			setDead();
		}

		if (onGround) {
			if (Math.random() < 0.5D) {
				setDead();
			}

			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}

		final BlockPos var1 = new BlockPos(this);
		final IBlockState var2 = worldObj.getBlockState(var1);
		final Block var3 = var2.getBlock();
		var3.setBlockBoundsBasedOnState(worldObj, var1);
		final Material var4 = var2.getBlock().getMaterial();

		if (var4.isLiquid() || var4.isSolid()) {
			double var5 = 0.0D;

			if (var2.getBlock() instanceof BlockLiquid) {
				var5 = 1.0F
						- BlockLiquid.getLiquidHeightPercent(((Integer) var2.getValue(BlockLiquid.LEVEL)));
			} else {
				var5 = var3.getBlockBoundsMaxY();
			}

			final double var7 = MathHelper.floor_double(posY) + var5;

			if (posY < var7) {
				setDead();
			}
		}
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002572";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityRainFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
		}
	}
}
