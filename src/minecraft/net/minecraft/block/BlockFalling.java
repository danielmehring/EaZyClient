package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFalling extends Block {

public static final int EaZy = 300;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static boolean fallInstantly;
	// private static final String __OBFID = "http://https://fuckuskid00000240";

	public BlockFalling() {
		super(Material.sand);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	public BlockFalling(final Material materialIn) {
		super(materialIn);
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
		if (!worldIn.isRemote) {
			checkFallable(worldIn, pos);
		}
	}

	private void checkFallable(final World worldIn, final BlockPos pos) {
		if (canFallInto(worldIn, pos.offsetDown()) && pos.getY() >= 0) {
			final byte var3 = 32;

			if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-var3, -var3, -var3), pos.add(var3, var3, var3))) {
				if (!worldIn.isRemote) {
					final EntityFallingBlock var5 = new EntityFallingBlock(worldIn, pos.getX() + 0.5D, pos.getY(),
							pos.getZ() + 0.5D, worldIn.getBlockState(pos));
					onStartFalling(var5);
					worldIn.spawnEntityInWorld(var5);
				}
			} else {
				worldIn.setBlockToAir(pos);
				BlockPos var4;

				for (var4 = pos.offsetDown(); canFallInto(worldIn, var4) && var4.getY() > 0; var4 = var4.offsetDown()) {
				}

				if (var4.getY() > 0) {
					worldIn.setBlockState(var4.offsetUp(), getDefaultState());
				}
			}
		}
	}

	protected void onStartFalling(final EntityFallingBlock fallingEntity) {}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 2;
	}

	public static boolean canFallInto(final World worldIn, final BlockPos pos) {
		final Block var2 = worldIn.getBlockState(pos).getBlock();
		final Material var3 = var2.blockMaterial;
		return var2 == Blocks.fire || var3 == Material.air || var3 == Material.water || var3 == Material.lava;
	}

	public void onEndFalling(final World worldIn, final BlockPos pos) {}
}
