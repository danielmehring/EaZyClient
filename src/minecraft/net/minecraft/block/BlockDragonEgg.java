package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDragonEgg extends Block {

public static final int EaZy = 292;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000232";

	public BlockDragonEgg() {
		super(Material.dragonEgg);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		func_180683_d(worldIn, pos);
	}

	private void func_180683_d(final World worldIn, final BlockPos p_180683_2_) {
		if (BlockFalling.canFallInto(worldIn, p_180683_2_.offsetDown()) && p_180683_2_.getY() >= 0) {
			final byte var3 = 32;

			if (!BlockFalling.fallInstantly
					&& worldIn.isAreaLoaded(p_180683_2_.add(-var3, -var3, -var3), p_180683_2_.add(var3, var3, var3))) {
				worldIn.spawnEntityInWorld(new EntityFallingBlock(worldIn, p_180683_2_.getX() + 0.5F,
						p_180683_2_.getY(), p_180683_2_.getZ() + 0.5F, getDefaultState()));
			} else {
				worldIn.setBlockToAir(p_180683_2_);
				BlockPos var4;

				for (var4 = p_180683_2_; BlockFalling.canFallInto(worldIn, var4)
						&& var4.getY() > 0; var4 = var4.offsetDown()) {
				}

				if (var4.getY() > 0) {
					worldIn.setBlockState(var4, getDefaultState(), 2);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		func_180684_e(worldIn, pos);
		return true;
	}

	@Override
	public void onBlockClicked(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
		func_180684_e(worldIn, pos);
	}

	private void func_180684_e(final World worldIn, final BlockPos p_180684_2_) {
		final IBlockState var3 = worldIn.getBlockState(p_180684_2_);

		if (var3.getBlock() == this) {
			for (int var4 = 0; var4 < 1000; ++var4) {
				final BlockPos var5 = p_180684_2_.add(worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16),
						worldIn.rand.nextInt(8) - worldIn.rand.nextInt(8),
						worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16));

				if (worldIn.getBlockState(var5).getBlock().blockMaterial == Material.air) {
					if (worldIn.isRemote) {
						for (int var6 = 0; var6 < 128; ++var6) {
							final double var7 = worldIn.rand.nextDouble();
							final float var9 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
							final float var10 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
							final float var11 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
							final double var12 = var5.getX() + (p_180684_2_.getX() - var5.getX()) * var7
									+ (worldIn.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							final double var14 = var5.getY() + (p_180684_2_.getY() - var5.getY()) * var7
									+ worldIn.rand.nextDouble() * 1.0D - 0.5D;
							final double var16 = var5.getZ() + (p_180684_2_.getZ() - var5.getZ()) * var7
									+ (worldIn.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
							worldIn.spawnParticle(EnumParticleTypes.PORTAL, var12, var14, var16, var9, var10, var11,
									new int[0]);
						}
					} else {
						worldIn.setBlockState(var5, var3, 2);
						worldIn.setBlockToAir(p_180684_2_);
					}

					return;
				}
			}
		}
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 5;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return null;
	}
}
