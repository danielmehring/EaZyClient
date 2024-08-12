package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {

public static final int EaZy = 303;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool field_176466_a = PropertyBool.create("open");
	public static final PropertyBool field_176465_b = PropertyBool.create("powered");
	public static final PropertyBool field_176467_M = PropertyBool.create("in_wall");
	// private static final String __OBFID = "http://https://fuckuskid00000243";

	public BlockFenceGate() {
		super(Material.wood);
		setDefaultState(blockState.getBaseState().withProperty(field_176466_a, false)
				.withProperty(field_176465_b, false)
				.withProperty(field_176467_M, false));
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		final EnumFacing.Axis var4 = ((EnumFacing) state.getValue(AGE)).getAxis();

		if (var4 == EnumFacing.Axis.Z
				&& (worldIn.getBlockState(pos.offsetWest()).getBlock() == Blocks.cobblestone_wall
						|| worldIn.getBlockState(pos.offsetEast()).getBlock() == Blocks.cobblestone_wall)
				|| var4 == EnumFacing.Axis.X
						&& (worldIn.getBlockState(pos.offsetNorth()).getBlock() == Blocks.cobblestone_wall
								|| worldIn.getBlockState(pos.offsetSouth()).getBlock() == Blocks.cobblestone_wall)) {
			state = state.withProperty(field_176467_M, true);
		}

		return state;
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos.offsetDown()).getBlock().getMaterial().isSolid()
				? super.canPlaceBlockAt(worldIn, pos) : false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (((Boolean) state.getValue(field_176466_a))) {
			return null;
		} else {
			final EnumFacing.Axis var4 = ((EnumFacing) state.getValue(AGE)).getAxis();
			return var4 == EnumFacing.Axis.Z
					? new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ() + 0.375F, pos.getX() + 1, pos.getY() + 1.5F,
							pos.getZ() + 0.625F)
					: new AxisAlignedBB(pos.getX() + 0.375F, pos.getY(), pos.getZ(), pos.getX() + 0.625F,
							pos.getY() + 1.5F, pos.getZ() + 1);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final EnumFacing.Axis var3 = ((EnumFacing) access.getBlockState(pos).getValue(AGE)).getAxis();

		if (var3 == EnumFacing.Axis.Z) {
			setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		} else {
			setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
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
	public boolean isPassable(final IBlockAccess blockAccess, final BlockPos pos) {
		return ((Boolean) blockAccess.getBlockState(pos).getValue(field_176466_a));
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(AGE, placer.func_174811_aO())
				.withProperty(field_176466_a, false)
				.withProperty(field_176465_b, false)
				.withProperty(field_176467_M, false);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (((Boolean) state.getValue(field_176466_a))) {
			state = state.withProperty(field_176466_a, false);
			worldIn.setBlockState(pos, state, 2);
		} else {
			final EnumFacing var9 = EnumFacing.fromAngle(playerIn.rotationYaw);

			if (state.getValue(AGE) == var9.getOpposite()) {
				state = state.withProperty(AGE, var9);
			}

			state = state.withProperty(field_176466_a, true);
			worldIn.setBlockState(pos, state, 2);
		}

		worldIn.playAuxSFXAtEntity(playerIn, ((Boolean) state.getValue(field_176466_a)) ? 1003 : 1006,
				pos, 0);
		return true;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			final boolean var5 = worldIn.isBlockPowered(pos);

			if (var5 || neighborBlock.canProvidePower()) {
				if (var5 && !((Boolean) state.getValue(field_176466_a))
						&& !((Boolean) state.getValue(field_176465_b))) {
					worldIn.setBlockState(pos, state.withProperty(field_176466_a, true)
							.withProperty(field_176465_b, true), 2);
					worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1003, pos, 0);
				} else if (!var5 && ((Boolean) state.getValue(field_176466_a))
						&& ((Boolean) state.getValue(field_176465_b))) {
					worldIn.setBlockState(pos, state.withProperty(field_176466_a, false)
							.withProperty(field_176465_b, false), 2);
					worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1006, pos, 0);
				} else if (var5 != ((Boolean) state.getValue(field_176465_b))) {
					worldIn.setBlockState(pos, state.withProperty(field_176465_b, var5), 2);
				}
			}
		}
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(meta))
				.withProperty(field_176466_a, (meta & 4) != 0)
				.withProperty(field_176465_b, (meta & 8) != 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(AGE)).getHorizontalIndex();

		if (((Boolean) state.getValue(field_176465_b))) {
			var3 |= 8;
		}

		if (((Boolean) state.getValue(field_176466_a))) {
			var3 |= 4;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE, field_176466_a, field_176465_b, field_176467_M });
	}
}
