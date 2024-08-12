package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBanner extends BlockContainer {

public static final int EaZy = 256;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
	// private static final String __OBFID = "http://https://fuckuskid00002143";

	protected BlockBanner() {
		super(Material.wood);
		final float var1 = 0.25F;
		final float var2 = 1.0F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(final IBlockAccess blockAccess, final BlockPos pos) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityBanner();
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.banner;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.banner;
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
		final TileEntity var6 = worldIn.getTileEntity(pos);

		if (var6 instanceof TileEntityBanner) {
			final ItemStack var7 = new ItemStack(Items.banner, 1, ((TileEntityBanner) var6).getBaseColor());
			final NBTTagCompound var8 = new NBTTagCompound();
			var6.writeToNBT(var8);
			var8.removeTag("x");
			var8.removeTag("y");
			var8.removeTag("z");
			var8.removeTag("id");
			var7.setTagInfo("BlockEntityTag", var8);
			spawnAsEntity(worldIn, pos, var7);
		} else {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}

	@Override
	public void harvestBlock(final World worldIn, final EntityPlayer playerIn, final BlockPos pos,
			final IBlockState state, final TileEntity te) {
		if (te instanceof TileEntityBanner) {
			final ItemStack var6 = new ItemStack(Items.banner, 1, ((TileEntityBanner) te).getBaseColor());
			final NBTTagCompound var7 = new NBTTagCompound();
			te.writeToNBT(var7);
			var7.removeTag("x");
			var7.removeTag("y");
			var7.removeTag("z");
			var7.removeTag("id");
			var6.setTagInfo("BlockEntityTag", var7);
			spawnAsEntity(worldIn, pos, var6);
		} else {
			super.harvestBlock(worldIn, playerIn, pos, state, (TileEntity) null);
		}
	}

	public static class BlockBannerHanging extends BlockBanner {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002140";

		public BlockBannerHanging() {
			setDefaultState(blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH));
		}

		@Override
		public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
			final EnumFacing var3 = (EnumFacing) access.getBlockState(pos).getValue(FACING_PROP);
			final float var4 = 0.0F;
			final float var5 = 0.78125F;
			final float var6 = 0.0F;
			final float var7 = 1.0F;
			final float var8 = 0.125F;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

			switch (BlockBanner.SwitchEnumFacing.SWITCH_MAP[var3.ordinal()]) {
				case 1:
				default:
					setBlockBounds(var6, var4, 1.0F - var8, var7, var5, 1.0F);
					break;

				case 2:
					setBlockBounds(var6, var4, 0.0F, var7, var5, var8);
					break;

				case 3:
					setBlockBounds(1.0F - var8, var4, var6, 1.0F, var5, var7);
					break;

				case 4:
					setBlockBounds(0.0F, var4, var6, var8, var5, var7);
			}
		}

		@Override
		public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
				final Block neighborBlock) {
			final EnumFacing var5 = (EnumFacing) state.getValue(FACING_PROP);

			if (!worldIn.getBlockState(pos.offset(var5.getOpposite())).getBlock().getMaterial().isSolid()) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}

			super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		}

		@Override
		public IBlockState getStateFromMeta(final int meta) {
			EnumFacing var2 = EnumFacing.getFront(meta);

			if (var2.getAxis() == EnumFacing.Axis.Y) {
				var2 = EnumFacing.NORTH;
			}

			return getDefaultState().withProperty(FACING_PROP, var2);
		}

		@Override
		public int getMetaFromState(final IBlockState state) {
			return ((EnumFacing) state.getValue(FACING_PROP)).getIndex();
		}

		@Override
		protected BlockState createBlockState() {
			return new BlockState(this, new IProperty[] { FACING_PROP });
		}
	}

	public static class BlockBannerStanding extends BlockBanner {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002141";

		public BlockBannerStanding() {
			setDefaultState(blockState.getBaseState().withProperty(ROTATION_PROP, 0));
		}

		@Override
		public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
				final Block neighborBlock) {
			if (!worldIn.getBlockState(pos.offsetDown()).getBlock().getMaterial().isSolid()) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}

			super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		}

		@Override
		public IBlockState getStateFromMeta(final int meta) {
			return getDefaultState().withProperty(ROTATION_PROP, meta);
		}

		@Override
		public int getMetaFromState(final IBlockState state) {
			return ((Integer) state.getValue(ROTATION_PROP));
		}

		@Override
		protected BlockState createBlockState() {
			return new BlockState(this, new IProperty[] { ROTATION_PROP });
		}
	}

	static final class SwitchEnumFacing {
		static final int[] SWITCH_MAP = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002142";

		static {
			try {
				SWITCH_MAP[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				SWITCH_MAP[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				SWITCH_MAP[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				SWITCH_MAP[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
