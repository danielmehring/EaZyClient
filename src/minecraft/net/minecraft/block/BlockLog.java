package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class BlockLog extends BlockRotatedPillar {

public static final int EaZy = 327;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum AXIS_PROP = PropertyEnum.create("axis", BlockLog.EnumAxis.class);
	// private static final String __OBFID = "http://https://fuckuskid00000266";

	public BlockLog() {
		super(Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final byte var4 = 4;
		final int var5 = var4 + 1;

		if (worldIn.isAreaLoaded(pos.add(-var5, -var5, -var5), pos.add(var5, var5, var5))) {
			final Iterator var6 = BlockPos.getAllInBox(pos.add(-var4, -var4, -var4), pos.add(var4, var4, var4))
					.iterator();

			while (var6.hasNext()) {
				final BlockPos var7 = (BlockPos) var6.next();
				final IBlockState var8 = worldIn.getBlockState(var7);

				if (var8.getBlock().getMaterial() == Material.leaves
						&& !((Boolean) var8.getValue(BlockLeaves.field_176236_b))) {
					worldIn.setBlockState(var7, var8.withProperty(BlockLeaves.field_176236_b, true),
							4);
				}
			}
		}
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(AXIS_PROP,
				BlockLog.EnumAxis.func_176870_a(facing.getAxis()));
	}

	public static enum EnumAxis implements IStringSerializable {
		X("X", 0, "x"), Y("Y", 1, "y"), Z("Z", 2, "z"), NONE("NONE", 3, "none");
		private final String field_176874_e;

		private EnumAxis(final String p_i45708_1_, final int p_i45708_2_, final String p_i45708_3_) {
			field_176874_e = p_i45708_3_;
		}

		@Override
		public String toString() {
			return field_176874_e;
		}

		public static BlockLog.EnumAxis func_176870_a(final EnumFacing.Axis p_176870_0_) {
			switch (BlockLog.SwitchAxis.field_180167_a[p_176870_0_.ordinal()]) {
				case 1:
					return X;

				case 2:
					return Y;

				case 3:
					return Z;

				default:
					return NONE;
			}
		}

		@Override
		public String getName() {
			return field_176874_e;
		}
	}

	static final class SwitchAxis {
		static final int[] field_180167_a = new int[EnumFacing.Axis.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002101";

		static {
			try {
				field_180167_a[EnumFacing.Axis.X.ordinal()] = 1;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180167_a[EnumFacing.Axis.Y.ordinal()] = 2;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180167_a[EnumFacing.Axis.Z.ordinal()] = 3;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
