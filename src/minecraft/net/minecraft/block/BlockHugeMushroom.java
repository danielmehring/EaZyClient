package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.Random;

public class BlockHugeMushroom extends Block {

public static final int EaZy = 318;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176380_a = PropertyEnum.create("variant", BlockHugeMushroom.EnumType.class);
	private final Block field_176379_b;
	// private static final String __OBFID = "http://https://fuckuskid00000258";

	public BlockHugeMushroom(final Material p_i45711_1_, final Block p_i45711_2_) {
		super(p_i45711_1_);
		setDefaultState(blockState.getBaseState().withProperty(field_176380_a, BlockHugeMushroom.EnumType.ALL_OUTSIDE));
		field_176379_b = p_i45711_2_;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return Math.max(0, random.nextInt(10) - 7);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(field_176379_b);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(field_176379_b);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176380_a, BlockHugeMushroom.EnumType.func_176895_a(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockHugeMushroom.EnumType) state.getValue(field_176380_a)).func_176896_a();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176380_a });
	}

	public static enum EnumType implements IStringSerializable {
		NORTH_WEST("NORTH_WEST", 0, 1, "north_west"), NORTH("NORTH", 1, 2, "north"), NORTH_EAST("NORTH_EAST", 2, 3,
				"north_east"), WEST("WEST", 3, 4, "west"), CENTER("CENTER", 4, 5, "center"), EAST("EAST", 5, 6,
						"east"), SOUTH_WEST("SOUTH_WEST", 6, 7, "south_west"), SOUTH("SOUTH", 7, 8,
								"south"), SOUTH_EAST("SOUTH_EAST", 8, 9, "south_east"), STEM("STEM", 9, 10,
										"stem"), ALL_INSIDE("ALL_INSIDE", 10, 0, "all_inside"), ALL_OUTSIDE(
												"ALL_OUTSIDE", 11, 14,
												"all_outside"), ALL_STEM("ALL_STEM", 12, 15, "all_stem");
		private static final BlockHugeMushroom.EnumType[] field_176905_n = new BlockHugeMushroom.EnumType[16];
		private final int field_176906_o;
		private final String field_176914_p;

		private EnumType(final String p_i45710_1_, final int p_i45710_2_, final int p_i45710_3_,
				final String p_i45710_4_) {
			field_176906_o = p_i45710_3_;
			field_176914_p = p_i45710_4_;
		}

		public int func_176896_a() {
			return field_176906_o;
		}

		@Override
		public String toString() {
			return field_176914_p;
		}

		public static BlockHugeMushroom.EnumType func_176895_a(int p_176895_0_) {
			if (p_176895_0_ < 0 || p_176895_0_ >= field_176905_n.length) {
				p_176895_0_ = 0;
			}

			final BlockHugeMushroom.EnumType var1 = field_176905_n[p_176895_0_];
			return var1 == null ? field_176905_n[0] : var1;
		}

		@Override
		public String getName() {
			return field_176914_p;
		}

		static {
			final BlockHugeMushroom.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockHugeMushroom.EnumType var3 = var0[var2];
				field_176905_n[var3.func_176896_a()] = var3;
			}
		}
	}
}
