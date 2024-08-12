package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCocoa extends BlockDirectional implements IGrowable {

public static final int EaZy = 275;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176501_a = PropertyInteger.create("age", 0, 2);
	// private static final String __OBFID = "http://https://fuckuskid00000216";

	public BlockCocoa() {
		super(Material.plants);
		setDefaultState(blockState.getBaseState().withProperty(AGE, EnumFacing.NORTH).withProperty(field_176501_a, 0));
		setTickRandomly(true);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!canBlockStay(worldIn, pos, state)) {
			dropBlock(worldIn, pos, state);
		} else if (worldIn.rand.nextInt(5) == 0) {
			final int var5 = ((Integer) state.getValue(field_176501_a));

			if (var5 < 2) {
				worldIn.setBlockState(pos, state.withProperty(field_176501_a, var5 + 1), 2);
			}
		}
	}

	public boolean canBlockStay(final World worldIn, BlockPos p_176499_2_, final IBlockState p_176499_3_) {
		p_176499_2_ = p_176499_2_.offset((EnumFacing) p_176499_3_.getValue(AGE));
		final IBlockState var4 = worldIn.getBlockState(p_176499_2_);
		return var4.getBlock() == Blocks.log && var4.getValue(BlockPlanks.VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE;
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
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final IBlockState var3 = access.getBlockState(pos);
		final EnumFacing var4 = (EnumFacing) var3.getValue(AGE);
		final int var5 = ((Integer) var3.getValue(field_176501_a));
		final int var6 = 4 + var5 * 2;
		final int var7 = 5 + var5 * 2;
		final float var8 = var6 / 2.0F;

		switch (BlockCocoa.SwitchEnumFacing.FACINGARRAY[var4.ordinal()]) {
			case 1:
				setBlockBounds((8.0F - var8) / 16.0F, (12.0F - var7) / 16.0F, (15.0F - var6) / 16.0F,
						(8.0F + var8) / 16.0F, 0.75F, 0.9375F);
				break;

			case 2:
				setBlockBounds((8.0F - var8) / 16.0F, (12.0F - var7) / 16.0F, 0.0625F, (8.0F + var8) / 16.0F, 0.75F,
						(1.0F + var6) / 16.0F);
				break;

			case 3:
				setBlockBounds(0.0625F, (12.0F - var7) / 16.0F, (8.0F - var8) / 16.0F, (1.0F + var6) / 16.0F, 0.75F,
						(8.0F + var8) / 16.0F);
				break;

			case 4:
				setBlockBounds((15.0F - var6) / 16.0F, (12.0F - var7) / 16.0F, (8.0F - var8) / 16.0F, 0.9375F, 0.75F,
						(8.0F + var8) / 16.0F);
		}
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		final EnumFacing var6 = EnumFacing.fromAngle(placer.rotationYaw);
		worldIn.setBlockState(pos, state.withProperty(AGE, var6), 2);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		if (!facing.getAxis().isHorizontal()) {
			facing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(AGE, facing.getOpposite()).withProperty(field_176501_a, 0);
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!canBlockStay(worldIn, pos, state)) {
			dropBlock(worldIn, pos, state);
		}
	}

	private void dropBlock(final World worldIn, final BlockPos p_176500_2_, final IBlockState p_176500_3_) {
		worldIn.setBlockState(p_176500_2_, Blocks.air.getDefaultState(), 3);
		dropBlockAsItem(worldIn, p_176500_2_, p_176500_3_, 0);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {
		final int var6 = ((Integer) state.getValue(field_176501_a));
		byte var7 = 1;

		if (var6 >= 2) {
			var7 = 3;
		}

		for (int var8 = 0; var8 < var7; ++var8) {
			spawnAsEntity(worldIn, pos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeColorDamage()));
		}
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.dye;
	}

	@Override
	public int getDamageValue(final World worldIn, final BlockPos pos) {
		return EnumDyeColor.BROWN.getDyeColorDamage();
	}

	@Override
	public boolean isStillGrowing(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_,
			final boolean p_176473_4_) {
		return ((Integer) p_176473_3_.getValue(field_176501_a)) < 2;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_,
			final IBlockState p_180670_4_) {
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_,
			final IBlockState p_176474_4_) {
		worldIn.setBlockState(p_176474_3_, p_176474_4_.withProperty(field_176501_a, ((Integer) p_176474_4_.getValue(field_176501_a)) + 1), 2);
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
		return getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(meta)).withProperty(field_176501_a, (meta & 15) >> 2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(AGE)).getHorizontalIndex();
		var3 |= ((Integer) state.getValue(field_176501_a)) << 2;
		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE, field_176501_a });
	}

	static final class SwitchEnumFacing {
		static final int[] FACINGARRAY = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002130";

		static {
			try {
				FACINGARRAY[EnumFacing.SOUTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				FACINGARRAY[EnumFacing.NORTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				FACINGARRAY[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				FACINGARRAY[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
