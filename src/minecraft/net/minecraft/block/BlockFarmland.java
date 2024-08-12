package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockFarmland extends Block {

public static final int EaZy = 301;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger field_176531_a = PropertyInteger.create("moisture", 0, 7);
	// private static final String __OBFID = "http://https://fuckuskid00000241";

	protected BlockFarmland() {
		super(Material.ground);
		setDefaultState(blockState.getBaseState().withProperty(field_176531_a, 0));
		setTickRandomly(true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setLightOpacity(255);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
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
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		final int var5 = ((Integer) state.getValue(field_176531_a));

		if (!func_176530_e(worldIn, pos) && !worldIn.func_175727_C(pos.offsetUp())) {
			if (var5 > 0) {
				worldIn.setBlockState(pos, state.withProperty(field_176531_a, var5 - 1), 2);
			} else if (!func_176529_d(worldIn, pos)) {
				worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
			}
		} else if (var5 < 7) {
			worldIn.setBlockState(pos, state.withProperty(field_176531_a, 7), 2);
		}
	}

	/**
	 * Block's chance to react to a living entity falling on it.
	 * 
	 * @param fallDistance
	 *            The distance the entity has fallen before landing
	 */
	@Override
	public void onFallenUpon(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance) {
		if (entityIn instanceof EntityLivingBase) {
			if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F) {
				if (!(entityIn instanceof EntityPlayer)
						&& !worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
					return;
				}

				worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
			}

			super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
		}
	}

	private boolean func_176529_d(final World worldIn, final BlockPos p_176529_2_) {
		final Block var3 = worldIn.getBlockState(p_176529_2_.offsetUp()).getBlock();
		return var3 instanceof BlockCrops || var3 instanceof BlockStem;
	}

	private boolean func_176530_e(final World worldIn, final BlockPos p_176530_2_) {
		final Iterator var3 = BlockPos.getAllInBoxMutable(p_176530_2_.add(-4, 0, -4), p_176530_2_.add(4, 1, 4))
				.iterator();
		BlockPos.MutableBlockPos var4;

		do {
			if (!var3.hasNext()) {
				return false;
			}

			var4 = (BlockPos.MutableBlockPos) var3.next();
		}
		while (worldIn.getBlockState(var4).getBlock().getMaterial() != Material.water);

		return true;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);

		if (worldIn.getBlockState(pos.offsetUp()).getBlock().getMaterial().isSolid()) {
			worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
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
		return Blocks.dirt.getItemDropped(
				Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.dirt);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176531_a, meta & 7);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(field_176531_a));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176531_a });
	}
}
