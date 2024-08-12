package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStaticLiquid extends BlockLiquid {

public static final int EaZy = 388;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000315";

	protected BlockStaticLiquid(final Material p_i45429_1_) {
		super(p_i45429_1_);
		setTickRandomly(false);

		if (p_i45429_1_ == Material.lava) {
			setTickRandomly(true);
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!func_176365_e(worldIn, pos, state)) {
			updateLiquid(worldIn, pos, state);
		}
	}

	private void updateLiquid(final World worldIn, final BlockPos p_176370_2_, final IBlockState p_176370_3_) {
		final BlockDynamicLiquid var4 = getDynamicLiquidForMaterial(blockMaterial);
		worldIn.setBlockState(p_176370_2_, var4.getDefaultState().withProperty(LEVEL, p_176370_3_.getValue(LEVEL)), 2);
		worldIn.scheduleUpdate(p_176370_2_, var4, tickRate(worldIn));
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (blockMaterial == Material.lava) {
			if (worldIn.getGameRules().getGameRuleBooleanValue("doFireTick")) {
				final int var5 = rand.nextInt(3);

				if (var5 > 0) {
					BlockPos var6 = pos;

					for (int var7 = 0; var7 < var5; ++var7) {
						var6 = var6.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
						final Block var8 = worldIn.getBlockState(var6).getBlock();

						if (var8.blockMaterial == Material.air) {
							if (isSurroundingBlockFlammable(worldIn, var6)) {
								worldIn.setBlockState(var6, Blocks.fire.getDefaultState());
								return;
							}
						} else if (var8.blockMaterial.blocksMovement()) {
							return;
						}
					}
				} else {
					for (int var9 = 0; var9 < 3; ++var9) {
						final BlockPos var10 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);

						if (worldIn.isAirBlock(var10.offsetUp()) && getCanBlockBurn(worldIn, var10)) {
							worldIn.setBlockState(var10.offsetUp(), Blocks.fire.getDefaultState());
						}
					}
				}
			}
		}
	}

	protected boolean isSurroundingBlockFlammable(final World worldIn, final BlockPos p_176369_2_) {
		final EnumFacing[] var3 = EnumFacing.values();
		final int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final EnumFacing var6 = var3[var5];

			if (getCanBlockBurn(worldIn, p_176369_2_.offset(var6))) {
				return true;
			}
		}

		return false;
	}

	private boolean getCanBlockBurn(final World worldIn, final BlockPos p_176368_2_) {
		return worldIn.getBlockState(p_176368_2_).getBlock().getMaterial().getCanBurn();
	}
}
