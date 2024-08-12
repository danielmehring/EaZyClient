package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate {

public static final int EaZy = 350;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool POWERED = PropertyBool.create("powered");
	private final BlockPressurePlate.Sensitivity sensitivity;
	// private static final String __OBFID = "http://https://fuckuskid00000289";

	protected BlockPressurePlate(final Material p_i45693_1_, final BlockPressurePlate.Sensitivity p_i45693_2_) {
		super(p_i45693_1_);
		setDefaultState(blockState.getBaseState().withProperty(POWERED, false));
		sensitivity = p_i45693_2_;
	}

	@Override
	protected int getRedstoneStrength(final IBlockState p_176576_1_) {
		return ((Boolean) p_176576_1_.getValue(POWERED)) ? 15 : 0;
	}

	@Override
	protected IBlockState setRedstoneStrength(final IBlockState p_176575_1_, final int p_176575_2_) {
		return p_176575_1_.withProperty(POWERED, p_176575_2_ > 0);
	}

	@Override
	protected int computeRedstoneStrength(final World worldIn, final BlockPos pos) {
		final AxisAlignedBB var3 = getSensitiveAABB(pos);
		List var4;

		switch (BlockPressurePlate.SwitchSensitivity.SENSITIVITY_ARRAY[sensitivity.ordinal()]) {
			case 1:
				var4 = worldIn.getEntitiesWithinAABBExcludingEntity((Entity) null, var3);
				break;

			case 2:
				var4 = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, var3);
				break;

			default:
				return 0;
		}

		if (!var4.isEmpty()) {
			final Iterator var5 = var4.iterator();

			while (var5.hasNext()) {
				final Entity var6 = (Entity) var5.next();

				if (!var6.doesEntityNotTriggerPressurePlate()) {
					return 15;
				}
			}
		}

		return 0;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(POWERED, meta == 1);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Boolean) state.getValue(POWERED)) ? 1 : 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { POWERED });
	}

	public static enum Sensitivity {
		EVERYTHING("EVERYTHING", 0), MOBS("MOBS", 1);

		private Sensitivity(final String p_i45417_1_, final int p_i45417_2_) {}
	}

	static final class SwitchSensitivity {
		static final int[] SENSITIVITY_ARRAY = new int[BlockPressurePlate.Sensitivity.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002078";

		static {
			try {
				SENSITIVITY_ARRAY[BlockPressurePlate.Sensitivity.EVERYTHING.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				SENSITIVITY_ARRAY[BlockPressurePlate.Sensitivity.MOBS.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
