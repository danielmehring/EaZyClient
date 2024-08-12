package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {

public static final int EaZy = 351;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	private final int field_150068_a;
	// private static final String __OBFID = "http://https://fuckuskid00000334";

	protected BlockPressurePlateWeighted(final String p_i45436_1_, final Material p_i45436_2_, final int p_i45436_3_) {
		super(p_i45436_2_);
		setDefaultState(blockState.getBaseState().withProperty(POWER, 0));
		field_150068_a = p_i45436_3_;
	}

	@Override
	protected int computeRedstoneStrength(final World worldIn, final BlockPos pos) {
		final int var3 = Math.min(worldIn.getEntitiesWithinAABB(Entity.class, getSensitiveAABB(pos)).size(),
				field_150068_a);

		if (var3 > 0) {
			final float var4 = (float) Math.min(field_150068_a, var3) / (float) field_150068_a;
			return MathHelper.ceiling_float_int(var4 * 15.0F);
		} else {
			return 0;
		}
	}

	@Override
	protected int getRedstoneStrength(final IBlockState p_176576_1_) {
		return ((Integer) p_176576_1_.getValue(POWER));
	}

	@Override
	protected IBlockState setRedstoneStrength(final IBlockState p_176575_1_, final int p_176575_2_) {
		return p_176575_1_.withProperty(POWER, p_176575_2_);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 10;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(POWER, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(POWER));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { POWER });
	}
}
