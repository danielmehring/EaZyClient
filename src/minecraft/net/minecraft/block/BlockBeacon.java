package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {

public static final int EaZy = 259;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000197";

	public BlockBeacon() {
		super(Material.glass);
		setHardness(3.0F);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityBeacon();
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			final TileEntity var9 = worldIn.getTileEntity(pos);

			if (var9 instanceof TileEntityBeacon) {
				playerIn.displayGUIChest((TileEntityBeacon) var9);
			}

			return true;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (stack.hasDisplayName()) {
			final TileEntity var6 = worldIn.getTileEntity(pos);

			if (var6 instanceof TileEntityBeacon) {
				((TileEntityBeacon) var6).func_145999_a(stack.getDisplayName());
			}
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final TileEntity var5 = worldIn.getTileEntity(pos);

		if (var5 instanceof TileEntityBeacon) {
			((TileEntityBeacon) var5).func_174908_m();
			worldIn.addBlockEvent(pos, this, 1, 0);
		}
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	public static void func_176450_d(final World worldIn, final BlockPos p_176450_1_) {
		HttpUtil.field_180193_a.submit(new Runnable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002136";
			@Override
			public void run() {
				final Chunk var1 = worldIn.getChunkFromBlockCoords(p_176450_1_);

				for (int var2 = p_176450_1_.getY() - 1; var2 >= 0; --var2) {
					final BlockPos var3 = new BlockPos(p_176450_1_.getX(), var2, p_176450_1_.getZ());

					if (!var1.canSeeSky(var3)) {
						break;
					}

					final IBlockState var4 = worldIn.getBlockState(var3);

					if (var4.getBlock() == Blocks.beacon) {
						((WorldServer) worldIn).addScheduledTask(new Runnable() {
							// private static final String __OBFID =
							// "http://https://fuckuskid00002135";
							@Override
							public void run() {
								final TileEntity var1 = worldIn.getTileEntity(var3);

								if (var1 instanceof TileEntityBeacon) {
									((TileEntityBeacon) var1).func_174908_m();
									worldIn.addBlockEvent(var3, Blocks.beacon, 1, 0);
								}
							}
						});
					}
				}
			}
		});
	}
}
