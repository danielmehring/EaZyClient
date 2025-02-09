package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockCactus extends Block {

public static final int EaZy = 268;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger AGE_PROP = PropertyInteger.create("age", 0, 15);
	// private static final String __OBFID = "http://https://fuckuskid00000210";

	protected BlockCactus() {
		super(Material.cactus);
		setDefaultState(blockState.getBaseState().withProperty(AGE_PROP, 0));
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		final BlockPos var5 = pos.offsetUp();

		if (worldIn.isAirBlock(var5)) {
			int var6;

			for (var6 = 1; worldIn.getBlockState(pos.offsetDown(var6)).getBlock() == this; ++var6) {
			}

			if (var6 < 3) {
				final int var7 = ((Integer) state.getValue(AGE_PROP));

				if (var7 == 15) {
					worldIn.setBlockState(var5, getDefaultState());
					final IBlockState var8 = state.withProperty(AGE_PROP, 0);
					worldIn.setBlockState(pos, var8, 4);
					onNeighborBlockChange(worldIn, var5, var8, this);
				} else {
					worldIn.setBlockState(pos, state.withProperty(AGE_PROP, var7 + 1), 4);
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		final float var4 = 0.0625F;
		return new AxisAlignedBB(pos.getX() + var4, pos.getY(), pos.getZ() + var4, pos.getX() + 1 - var4,
				pos.getY() + 1 - var4, pos.getZ() + 1 - var4);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
		final float var3 = 0.0625F;
		return new AxisAlignedBB(pos.getX() + var3, pos.getY(), pos.getZ() + var3, pos.getX() + 1 - var3,
				pos.getY() + 1, pos.getZ() + 1 - var3);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!canBlockStay(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	public boolean canBlockStay(final World worldIn, final BlockPos p_176586_2_) {
		final Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();

		while (var3.hasNext()) {
			final EnumFacing var4 = (EnumFacing) var3.next();

			if (worldIn.getBlockState(p_176586_2_.offset(var4)).getBlock().getMaterial().isSolid()) {
				return false;
			}
		}

		final Block var5 = worldIn.getBlockState(p_176586_2_.offsetDown()).getBlock();
		return var5 == Blocks.cactus || var5 == Blocks.sand;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
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
		return getDefaultState().withProperty(AGE_PROP, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(AGE_PROP));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE_PROP });
	}
}
