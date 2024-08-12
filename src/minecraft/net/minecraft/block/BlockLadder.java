package net.minecraft.block;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.FastLadder;
import me.EaZy.client.modules.YesCheat;
import me.EaZy.client.modules.YesCheat.Mode;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;

public class BlockLadder extends Block {

public static final int EaZy = 321;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176382_a = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);

	protected BlockLadder() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(field_176382_a, EnumFacing.NORTH));
		setCreativeTab(CreativeTabs.tabDecorations);
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

		if (var3.getBlock() == this) {
			float var4 = 0.175F;
			if (FastLadder.mod.isToggled() && YesCheat.mode == Mode.AAC) {
				if (!Minecraft.thePlayer.isOnLadder() && !Minecraft.thePlayer.isSneaking()) {
					var4 = 0.875F;
				} else {
					var4 = 0.175F;
					FastLadder.canFastLadder = false;
				}
                                FastLadder.canFastLadder = Minecraft.thePlayer.isCollidedHorizontally && !Minecraft.thePlayer.isOnLadder();

			} else {
				var4 = 0.175F;
			}
			switch (BlockLadder.SwitchEnumFacing.field_180190_a[((EnumFacing) var3.getValue(field_176382_a))
					.ordinal()]) {
				case 1:
					setBlockBounds(0.0F, 0.0F, 1.0F - var4, 1.0F, 1.0F, 1.0F);
					break;

				case 2:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var4);
					break;

				case 3:
					setBlockBounds(1.0F - var4, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;

				case 4:
				default:
					setBlockBounds(0.0F, 0.0F, 0.0F, var4, 1.0F, 1.0F);
			}
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
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos.offsetWest()).getBlock().isNormalCube() ? true
				: worldIn.getBlockState(pos.offsetEast()).getBlock().isNormalCube() ? true
						: worldIn.getBlockState(pos.offsetNorth()).getBlock().isNormalCube() ? true
								: worldIn.getBlockState(pos.offsetSouth()).getBlock().isNormalCube();
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		if (facing.getAxis().isHorizontal() && func_176381_b(worldIn, pos, facing)) {
			return getDefaultState().withProperty(field_176382_a, facing);
		} else {
			final Iterator var9 = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing var10;

			do {
				if (!var9.hasNext()) {
					return getDefaultState();
				}

				var10 = (EnumFacing) var9.next();
			}
			while (!func_176381_b(worldIn, pos, var10));

			return getDefaultState().withProperty(field_176382_a, var10);
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final EnumFacing var5 = (EnumFacing) state.getValue(field_176382_a);

		if (!func_176381_b(worldIn, pos, var5)) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	protected boolean func_176381_b(final World worldIn, final BlockPos p_176381_2_, final EnumFacing p_176381_3_) {
		return worldIn.getBlockState(p_176381_2_.offset(p_176381_3_.getOpposite())).getBlock().isNormalCube();
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
		EnumFacing var2 = EnumFacing.getFront(meta);

		if (var2.getAxis() == EnumFacing.Axis.Y) {
			var2 = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(field_176382_a, var2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumFacing) state.getValue(field_176382_a)).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176382_a });
	}

	static final class SwitchEnumFacing {
		static final int[] field_180190_a = new int[EnumFacing.values().length];
		static {
			try {
				field_180190_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180190_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180190_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180190_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
