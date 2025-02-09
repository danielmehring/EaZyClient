package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockReed extends Block {

public static final int EaZy = 368;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176355_a = PropertyInteger.create("age", 0, 15);
	// private static final String __OBFID = "http://https://fuckuskid00000300";

	protected BlockReed() {
		super(Material.plants);
		setDefaultState(blockState.getBaseState().withProperty(field_176355_a, 0));
		final float var1 = 0.375F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 1.0F, 0.5F + var1);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (worldIn.getBlockState(pos.offsetDown()).getBlock() == Blocks.reeds || func_176353_e(worldIn, pos, state)) {
			if (worldIn.isAirBlock(pos.offsetUp())) {
				int var5;

				for (var5 = 1; worldIn.getBlockState(pos.offsetDown(var5)).getBlock() == this; ++var5) {
				}

				if (var5 < 3) {
					final int var6 = ((Integer) state.getValue(field_176355_a));

					if (var6 == 15) {
						worldIn.setBlockState(pos.offsetUp(), getDefaultState());
						worldIn.setBlockState(pos, state.withProperty(field_176355_a, 0), 4);
					} else {
						worldIn.setBlockState(pos, state.withProperty(field_176355_a, var6 + 1), 4);
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		final Block var3 = worldIn.getBlockState(pos.offsetDown()).getBlock();

		if (var3 == this) {
			return true;
		} else if (var3 != Blocks.grass && var3 != Blocks.dirt && var3 != Blocks.sand) {
			return false;
		} else {
			final Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing var5;

			do {
				if (!var4.hasNext()) {
					return false;
				}

				var5 = (EnumFacing) var4.next();
			}
			while (worldIn.getBlockState(pos.offset(var5).offsetDown()).getBlock().getMaterial() != Material.water);

			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		func_176353_e(worldIn, pos, state);
	}

	protected final boolean func_176353_e(final World worldIn, final BlockPos p_176353_2_,
			final IBlockState p_176353_3_) {
		if (func_176354_d(worldIn, p_176353_2_)) {
			return true;
		} else {
			dropBlockAsItem(worldIn, p_176353_2_, p_176353_3_, 0);
			worldIn.setBlockToAir(p_176353_2_);
			return false;
		}
	}

	public boolean func_176354_d(final World worldIn, final BlockPos p_176354_2_) {
		return canPlaceBlockAt(worldIn, p_176354_2_);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.reeds;
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
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.reeds;
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		return worldIn.getBiomeGenForCoords(pos).func_180627_b(pos);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176355_a, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(field_176355_a));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176355_a });
	}
}
