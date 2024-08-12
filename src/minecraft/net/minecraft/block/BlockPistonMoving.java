package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer {

public static final int EaZy = 346;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176426_a = BlockPistonExtension.field_176326_a;
	public static final PropertyEnum field_176425_b = BlockPistonExtension.field_176325_b;
	// private static final String __OBFID = "http://https://fuckuskid00000368";

	public BlockPistonMoving() {
		super(Material.piston);
		setDefaultState(blockState.getBaseState().withProperty(field_176426_a, EnumFacing.NORTH)
				.withProperty(field_176425_b, BlockPistonExtension.EnumPistonType.DEFAULT));
		setHardness(-1.0F);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return null;
	}

	public static TileEntity func_176423_a(final IBlockState p_176423_0_, final EnumFacing p_176423_1_,
			final boolean p_176423_2_, final boolean p_176423_3_) {
		return new TileEntityPiston(p_176423_0_, p_176423_1_, p_176423_2_, p_176423_3_);
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final TileEntity var4 = worldIn.getTileEntity(pos);

		if (var4 instanceof TileEntityPiston) {
			((TileEntityPiston) var4).clearPistonTileEntity();
		} else {
			super.breakBlock(worldIn, pos, state);
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return false;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(final World worldIn, final BlockPos pos, final EnumFacing side) {
		return false;
	}

	/**
	 * Called when a player destroys this Block
	 */
	@Override
	public void onBlockDestroyedByPlayer(final World worldIn, final BlockPos pos, final IBlockState state) {
		final BlockPos var4 = pos.offset(((EnumFacing) state.getValue(field_176426_a)).getOpposite());
		final IBlockState var5 = worldIn.getBlockState(var4);

		if (var5.getBlock() instanceof BlockPistonBase
				&& ((Boolean) var5.getValue(BlockPistonBase.EXTENDED))) {
			worldIn.setBlockToAir(var4);
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

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
			worldIn.setBlockToAir(pos);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return null;
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
		if (!worldIn.isRemote) {
			final TileEntityPiston var6 = func_176422_e(worldIn, pos);

			if (var6 != null) {
				final IBlockState var7 = var6.func_174927_b();
				var7.getBlock().dropBlockAsItem(worldIn, pos, var7, 0);
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit.
	 * 
	 * @param start
	 *            The start vector
	 * @param end
	 *            The end vector
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(final World worldIn, final BlockPos pos, final Vec3 start,
			final Vec3 end) {
		return null;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			worldIn.getTileEntity(pos);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		final TileEntityPiston var4 = func_176422_e(worldIn, pos);

		if (var4 == null) {
			return null;
		} else {
			float var5 = var4.func_145860_a(0.0F);

			if (var4.isExtending()) {
				var5 = 1.0F - var5;
			}

			return func_176424_a(worldIn, pos, var4.func_174927_b(), var5, var4.func_174930_e());
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final TileEntityPiston var3 = func_176422_e(access, pos);

		if (var3 != null) {
			final IBlockState var4 = var3.func_174927_b();
			final Block var5 = var4.getBlock();

			if (var5 == this || var5.getMaterial() == Material.air) {
				return;
			}

			float var6 = var3.func_145860_a(0.0F);

			if (var3.isExtending()) {
				var6 = 1.0F - var6;
			}

			var5.setBlockBoundsBasedOnState(access, pos);

			if (var5 == Blocks.piston || var5 == Blocks.sticky_piston) {
				var6 = 0.0F;
			}

			final EnumFacing var7 = var3.func_174930_e();
			minX = var5.getBlockBoundsMinX() - var7.getFrontOffsetX() * var6;
			minY = var5.getBlockBoundsMinY() - var7.getFrontOffsetY() * var6;
			minZ = var5.getBlockBoundsMinZ() - var7.getFrontOffsetZ() * var6;
			maxX = var5.getBlockBoundsMaxX() - var7.getFrontOffsetX() * var6;
			maxY = var5.getBlockBoundsMaxY() - var7.getFrontOffsetY() * var6;
			maxZ = var5.getBlockBoundsMaxZ() - var7.getFrontOffsetZ() * var6;
		}
	}

	public AxisAlignedBB func_176424_a(final World worldIn, final BlockPos p_176424_2_, final IBlockState p_176424_3_,
			final float p_176424_4_, final EnumFacing p_176424_5_) {
		if (p_176424_3_.getBlock() != this && p_176424_3_.getBlock().getMaterial() != Material.air) {
			final AxisAlignedBB var6 = p_176424_3_.getBlock().getCollisionBoundingBox(worldIn, p_176424_2_,
					p_176424_3_);

			if (var6 == null) {
				return null;
			} else {
				double var7 = var6.minX;
				double var9 = var6.minY;
				double var11 = var6.minZ;
				double var13 = var6.maxX;
				double var15 = var6.maxY;
				double var17 = var6.maxZ;

				if (p_176424_5_.getFrontOffsetX() < 0) {
					var7 -= p_176424_5_.getFrontOffsetX() * p_176424_4_;
				} else {
					var13 -= p_176424_5_.getFrontOffsetX() * p_176424_4_;
				}

				if (p_176424_5_.getFrontOffsetY() < 0) {
					var9 -= p_176424_5_.getFrontOffsetY() * p_176424_4_;
				} else {
					var15 -= p_176424_5_.getFrontOffsetY() * p_176424_4_;
				}

				if (p_176424_5_.getFrontOffsetZ() < 0) {
					var11 -= p_176424_5_.getFrontOffsetZ() * p_176424_4_;
				} else {
					var17 -= p_176424_5_.getFrontOffsetZ() * p_176424_4_;
				}

				return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
			}
		} else {
			return null;
		}
	}

	private TileEntityPiston func_176422_e(final IBlockAccess p_176422_1_, final BlockPos p_176422_2_) {
		final TileEntity var3 = p_176422_1_.getTileEntity(p_176422_2_);
		return var3 instanceof TileEntityPiston ? (TileEntityPiston) var3 : null;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return null;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176426_a, BlockPistonExtension.func_176322_b(meta))
				.withProperty(field_176425_b, (meta & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY
						: BlockPistonExtension.EnumPistonType.DEFAULT);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(field_176426_a)).getIndex();

		if (state.getValue(field_176425_b) == BlockPistonExtension.EnumPistonType.STICKY) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176426_a, field_176425_b });
	}
}
