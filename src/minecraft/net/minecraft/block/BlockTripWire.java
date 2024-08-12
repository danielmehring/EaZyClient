package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block {

public static final int EaZy = 398;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool field_176293_a = PropertyBool.create("powered");
	public static final PropertyBool field_176290_b = PropertyBool.create("suspended");
	public static final PropertyBool field_176294_M = PropertyBool.create("attached");
	public static final PropertyBool field_176295_N = PropertyBool.create("disarmed");
	public static final PropertyBool field_176296_O = PropertyBool.create("north");
	public static final PropertyBool field_176291_P = PropertyBool.create("east");
	public static final PropertyBool field_176289_Q = PropertyBool.create("south");
	public static final PropertyBool field_176292_R = PropertyBool.create("west");
	// private static final String __OBFID = "http://https://fuckuskid00000328";

	public BlockTripWire() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(field_176293_a, false)
				.withProperty(field_176290_b, false)
				.withProperty(field_176294_M, false)
				.withProperty(field_176295_N, false)
				.withProperty(field_176296_O, false)
				.withProperty(field_176291_P, false)
				.withProperty(field_176289_Q, false)
				.withProperty(field_176292_R, false));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
		setTickRandomly(true);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		return state.withProperty(field_176296_O, func_176287_c(worldIn, pos, state, EnumFacing.NORTH))
				.withProperty(field_176291_P, func_176287_c(worldIn, pos, state, EnumFacing.EAST))
				.withProperty(field_176289_Q, func_176287_c(worldIn, pos, state, EnumFacing.SOUTH))
				.withProperty(field_176292_R, func_176287_c(worldIn, pos, state, EnumFacing.WEST));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
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
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.string;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.string;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final boolean var5 = ((Boolean) state.getValue(field_176290_b));
		final boolean var6 = !World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown());

		if (var5 != var6) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final IBlockState var3 = access.getBlockState(pos);
		final boolean var4 = ((Boolean) var3.getValue(field_176294_M));
		final boolean var5 = ((Boolean) var3.getValue(field_176290_b));

		if (!var5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
		} else if (!var4) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, IBlockState state) {
		state = state.withProperty(field_176290_b, !World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()));
		worldIn.setBlockState(pos, state, 3);
		func_176286_e(worldIn, pos, state);
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		func_176286_e(worldIn, pos, state.withProperty(field_176293_a, true));
	}

	@Override
	public void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn) {
		if (!worldIn.isRemote) {
			if (playerIn.getCurrentEquippedItem() != null
					&& playerIn.getCurrentEquippedItem().getItem() == Items.shears) {
				worldIn.setBlockState(pos, state.withProperty(field_176295_N, true), 4);
			}
		}
	}

	private void func_176286_e(final World worldIn, final BlockPos p_176286_2_, final IBlockState p_176286_3_) {
		final EnumFacing[] var4 = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.WEST };
		final int var5 = var4.length;
		int var6 = 0;

		while (var6 < var5) {
			final EnumFacing var7 = var4[var6];
			int var8 = 1;

			while (true) {
				if (var8 < 42) {
					final BlockPos var9 = p_176286_2_.offset(var7, var8);
					final IBlockState var10 = worldIn.getBlockState(var9);

					if (var10.getBlock() == Blocks.tripwire_hook) {
						if (var10.getValue(BlockTripWireHook.field_176264_a) == var7.getOpposite()) {
							Blocks.tripwire_hook.func_176260_a(worldIn, var9, var10, false, true, var8, p_176286_3_);
						}
					} else if (var10.getBlock() == Blocks.tripwire) {
						++var8;
						continue;
					}
				}

				++var6;
				break;
			}
		}
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		if (!worldIn.isRemote) {
			if (!((Boolean) state.getValue(field_176293_a))) {
				func_176288_d(worldIn, pos);
			}
		}
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops
	 * to grow, etc.)
	 */
	@Override
	public void randomTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!worldIn.isRemote) {
			if (((Boolean) worldIn.getBlockState(pos).getValue(field_176293_a))) {
				func_176288_d(worldIn, pos);
			}
		}
	}

	private void func_176288_d(final World worldIn, final BlockPos p_176288_2_) {
		IBlockState var3 = worldIn.getBlockState(p_176288_2_);
		final boolean var4 = ((Boolean) var3.getValue(field_176293_a));
		boolean var5 = false;
		final List var6 = worldIn.getEntitiesWithinAABBExcludingEntity((Entity) null,
				new AxisAlignedBB(p_176288_2_.getX() + minX, p_176288_2_.getY() + minY, p_176288_2_.getZ() + minZ,
						p_176288_2_.getX() + maxX, p_176288_2_.getY() + maxY, p_176288_2_.getZ() + maxZ));

		if (!var6.isEmpty()) {
			final Iterator var7 = var6.iterator();

			while (var7.hasNext()) {
				final Entity var8 = (Entity) var7.next();

				if (!var8.doesEntityNotTriggerPressurePlate()) {
					var5 = true;
					break;
				}
			}
		}

		if (var5 != var4) {
			var3 = var3.withProperty(field_176293_a, var5);
			worldIn.setBlockState(p_176288_2_, var3, 3);
			func_176286_e(worldIn, p_176288_2_, var3);
		}

		if (var5) {
			worldIn.scheduleUpdate(p_176288_2_, this, tickRate(worldIn));
		}
	}

	public static boolean func_176287_c(final IBlockAccess p_176287_0_, final BlockPos p_176287_1_,
			final IBlockState p_176287_2_, final EnumFacing p_176287_3_) {
		final BlockPos var4 = p_176287_1_.offset(p_176287_3_);
		final IBlockState var5 = p_176287_0_.getBlockState(var4);
		final Block var6 = var5.getBlock();

		if (var6 == Blocks.tripwire_hook) {
			final EnumFacing var9 = p_176287_3_.getOpposite();
			return var5.getValue(BlockTripWireHook.field_176264_a) == var9;
		} else if (var6 == Blocks.tripwire) {
			final boolean var7 = ((Boolean) p_176287_2_.getValue(field_176290_b));
			final boolean var8 = ((Boolean) var5.getValue(field_176290_b));
			return var7 == var8;
		} else {
			return false;
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176293_a, (meta & 1) > 0)
				.withProperty(field_176290_b, (meta & 2) > 0)
				.withProperty(field_176294_M, (meta & 4) > 0)
				.withProperty(field_176295_N, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		int var2 = 0;

		if (((Boolean) state.getValue(field_176293_a))) {
			var2 |= 1;
		}

		if (((Boolean) state.getValue(field_176290_b))) {
			var2 |= 2;
		}

		if (((Boolean) state.getValue(field_176294_M))) {
			var2 |= 4;
		}

		if (((Boolean) state.getValue(field_176295_N))) {
			var2 |= 8;
		}

		return var2;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176293_a, field_176290_b, field_176294_M, field_176295_N,
				field_176296_O, field_176291_P, field_176292_R, field_176289_Q });
	}
}
