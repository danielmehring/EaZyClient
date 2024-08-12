package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockDaylightDetector extends BlockContainer {

public static final int EaZy = 282;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176436_a = PropertyInteger.create("power", 0, 15);
	private final boolean field_176435_b;
	// private static final String __OBFID = "http://https://fuckuskid00000223";

	public BlockDaylightDetector(final boolean p_i45729_1_) {
		super(Material.wood);
		field_176435_b = p_i45729_1_;
		setDefaultState(blockState.getBaseState().withProperty(field_176436_a, 0));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
		setCreativeTab(CreativeTabs.tabRedstone);
		setHardness(0.2F);
		setStepSound(soundTypeWood);
		setUnlocalizedName("daylightDetector");
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return ((Integer) state.getValue(field_176436_a));
	}

	public void func_180677_d(final World worldIn, final BlockPos p_180677_2_) {
		if (!worldIn.provider.getHasNoSky()) {
			final IBlockState var3 = worldIn.getBlockState(p_180677_2_);
			int var4 = worldIn.getLightFor(EnumSkyBlock.SKY, p_180677_2_) - worldIn.getSkylightSubtracted();
			float var5 = worldIn.getCelestialAngleRadians(1.0F);
			final float var6 = var5 < (float) Math.PI ? 0.0F : (float) Math.PI * 2F;
			var5 += (var6 - var5) * 0.2F;
			var4 = Math.round(var4 * MathHelper.cos(var5));
			var4 = MathHelper.clamp_int(var4, 0, 15);

			if (field_176435_b) {
				var4 = 15 - var4;
			}

			if (((Integer) var3.getValue(field_176436_a)) != var4) {
				worldIn.setBlockState(p_180677_2_, var3.withProperty(field_176436_a, var4), 3);
			}
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (playerIn.func_175142_cm()) {
			if (worldIn.isRemote) {
				return true;
			} else {
				if (field_176435_b) {
					worldIn.setBlockState(pos, Blocks.daylight_detector.getDefaultState().withProperty(field_176436_a,
							state.getValue(field_176436_a)), 4);
					Blocks.daylight_detector.func_180677_d(worldIn, pos);
				} else {
					worldIn.setBlockState(pos, Blocks.daylight_detector_inverted.getDefaultState()
							.withProperty(field_176436_a, state.getValue(field_176436_a)), 4);
					Blocks.daylight_detector_inverted.func_180677_d(worldIn, pos);
				}

				return true;
			}
		} else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
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
		return Item.getItemFromBlock(Blocks.daylight_detector);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.daylight_detector);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityDaylightDetector();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176436_a, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(field_176436_a));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176436_a });
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		if (!field_176435_b) {
			super.getSubBlocks(itemIn, tab, list);
		}
	}
}
