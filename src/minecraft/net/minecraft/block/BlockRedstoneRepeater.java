package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {

public static final int EaZy = 365;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool field_176411_a = PropertyBool.create("locked");
	public static final PropertyInteger field_176410_b = PropertyInteger.create("delay", 1, 4);
	// private static final String __OBFID = "http://https://fuckuskid00000301";

	protected BlockRedstoneRepeater(final boolean p_i45424_1_) {
		super(p_i45424_1_);
		setDefaultState(blockState.getBaseState().withProperty(AGE, EnumFacing.NORTH)
				.withProperty(field_176410_b, 1).withProperty(field_176411_a, false));
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		return state.withProperty(field_176411_a, func_176405_b(worldIn, pos, state));
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (!playerIn.capabilities.allowEdit) {
			return false;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(field_176410_b), 3);
			return true;
		}
	}

	@Override
	protected int func_176403_d(final IBlockState p_176403_1_) {
		return ((Integer) p_176403_1_.getValue(field_176410_b)) * 2;
	}

	@Override
	protected IBlockState func_180674_e(final IBlockState p_180674_1_) {
		final Integer var2 = (Integer) p_180674_1_.getValue(field_176410_b);
		final Boolean var3 = (Boolean) p_180674_1_.getValue(field_176411_a);
		final EnumFacing var4 = (EnumFacing) p_180674_1_.getValue(AGE);
		return Blocks.powered_repeater.getDefaultState().withProperty(AGE, var4).withProperty(field_176410_b, var2)
				.withProperty(field_176411_a, var3);
	}

	@Override
	protected IBlockState func_180675_k(final IBlockState p_180675_1_) {
		final Integer var2 = (Integer) p_180675_1_.getValue(field_176410_b);
		final Boolean var3 = (Boolean) p_180675_1_.getValue(field_176411_a);
		final EnumFacing var4 = (EnumFacing) p_180675_1_.getValue(AGE);
		return Blocks.unpowered_repeater.getDefaultState().withProperty(AGE, var4).withProperty(field_176410_b, var2)
				.withProperty(field_176411_a, var3);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.repeater;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.repeater;
	}

	@Override
	public boolean func_176405_b(final IBlockAccess p_176405_1_, final BlockPos p_176405_2_,
			final IBlockState p_176405_3_) {
		return func_176407_c(p_176405_1_, p_176405_2_, p_176405_3_) > 0;
	}

	@Override
	protected boolean func_149908_a(final Block p_149908_1_) {
		return isRedstoneRepeaterBlockID(p_149908_1_);
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (isRepeaterPowered) {
			final EnumFacing var5 = (EnumFacing) state.getValue(AGE);
			final double var6 = pos.getX() + 0.5F + (rand.nextFloat() - 0.5F) * 0.2D;
			final double var8 = pos.getY() + 0.4F + (rand.nextFloat() - 0.5F) * 0.2D;
			final double var10 = pos.getZ() + 0.5F + (rand.nextFloat() - 0.5F) * 0.2D;
			float var12 = -5.0F;

			if (rand.nextBoolean()) {
				var12 = ((Integer) state.getValue(field_176410_b)) * 2 - 1;
			}

			var12 /= 16.0F;
			final double var13 = var12 * var5.getFrontOffsetX();
			final double var15 = var12 * var5.getFrontOffsetZ();
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, var6 + var13, var8, var10 + var15, 0.0D, 0.0D, 0.0D,
					new int[0]);
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		func_176400_h(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(meta))
				.withProperty(field_176411_a, false)
				.withProperty(field_176410_b, 1 + (meta >> 2));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(AGE)).getHorizontalIndex();
		var3 |= ((Integer) state.getValue(field_176410_b)) - 1 << 2;
		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE, field_176410_b, field_176411_a });
	}
}
