package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class BlockSponge extends Block {

public static final int EaZy = 383;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool WET_PROP = PropertyBool.create("wet");
	// private static final String __OBFID = "http://https://fuckuskid00000311";

	protected BlockSponge() {
		super(Material.sponge);
		setDefaultState(blockState.getBaseState().withProperty(WET_PROP, false));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((Boolean) state.getValue(WET_PROP)) ? 1 : 0;
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		setWet(worldIn, pos, state);
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		setWet(worldIn, pos, state);
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	protected void setWet(final World worldIn, final BlockPos p_176311_2_, final IBlockState p_176311_3_) {
		if (!((Boolean) p_176311_3_.getValue(WET_PROP)) && absorbWater(worldIn, p_176311_2_)) {
			worldIn.setBlockState(p_176311_2_, p_176311_3_.withProperty(WET_PROP, true), 2);
			worldIn.playAuxSFX(2001, p_176311_2_, Block.getIdFromBlock(Blocks.water));
		}
	}

	private boolean absorbWater(final World worldIn, final BlockPos p_176312_2_) {
		final LinkedList var3 = Lists.newLinkedList();
		final ArrayList var4 = Lists.newArrayList();
		var3.add(new Tuple(p_176312_2_, 0));
		int var5 = 0;
		BlockPos var7;

		while (!var3.isEmpty()) {
			final Tuple var6 = (Tuple) var3.poll();
			var7 = (BlockPos) var6.getFirst();
			final int var8 = ((Integer) var6.getSecond());
			final EnumFacing[] var9 = EnumFacing.values();
			final int var10 = var9.length;

			for (int var11 = 0; var11 < var10; ++var11) {
				final EnumFacing var12 = var9[var11];
				final BlockPos var13 = var7.offset(var12);

				if (worldIn.getBlockState(var13).getBlock().getMaterial() == Material.water) {
					worldIn.setBlockState(var13, Blocks.air.getDefaultState(), 2);
					var4.add(var13);
					++var5;

					if (var8 < 6) {
						var3.add(new Tuple(var13, var8 + 1));
					}
				}
			}

			if (var5 > 64) {
				break;
			}
		}

		final Iterator var14 = var4.iterator();

		while (var14.hasNext()) {
			var7 = (BlockPos) var14.next();
			worldIn.notifyNeighborsOfStateChange(var7, Blocks.air);
		}

		return var5 > 0;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(WET_PROP, (meta & 1) == 1);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Boolean) state.getValue(WET_PROP)) ? 1 : 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { WET_PROP });
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (((Boolean) state.getValue(WET_PROP))) {
			final EnumFacing var5 = EnumFacing.random(rand);

			if (var5 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(worldIn, pos.offset(var5))) {
				double var6 = pos.getX();
				double var8 = pos.getY();
				double var10 = pos.getZ();

				if (var5 == EnumFacing.DOWN) {
					var8 -= 0.05D;
					var6 += rand.nextDouble();
					var10 += rand.nextDouble();
				} else {
					var8 += rand.nextDouble() * 0.8D;

					if (var5.getAxis() == EnumFacing.Axis.X) {
						var10 += rand.nextDouble();

						if (var5 == EnumFacing.EAST) {
							++var6;
						} else {
							var6 += 0.05D;
						}
					} else {
						var6 += rand.nextDouble();

						if (var5 == EnumFacing.SOUTH) {
							++var10;
						} else {
							var10 += 0.05D;
						}
					}
				}

				worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, var6, var8, var10, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}
