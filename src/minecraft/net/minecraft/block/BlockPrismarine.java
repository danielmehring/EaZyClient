package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.List;

public class BlockPrismarine extends Block {

public static final int EaZy = 352;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum VARIANTS = PropertyEnum.create("variant", BlockPrismarine.EnumType.class);
	public static final int ROUGHMETA = BlockPrismarine.EnumType.ROUGH.getMetadata();
	public static final int BRICKSMETA = BlockPrismarine.EnumType.BRICKS.getMetadata();
	public static final int DARKMETA = BlockPrismarine.EnumType.DARK.getMetadata();
	// private static final String __OBFID = "http://https://fuckuskid00002077";

	public BlockPrismarine() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(VARIANTS, BlockPrismarine.EnumType.ROUGH));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((BlockPrismarine.EnumType) state.getValue(VARIANTS)).getMetadata();
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockPrismarine.EnumType) state.getValue(VARIANTS)).getMetadata();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANTS });
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(VARIANTS, BlockPrismarine.EnumType.func_176810_a(meta));
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		list.add(new ItemStack(itemIn, 1, ROUGHMETA));
		list.add(new ItemStack(itemIn, 1, BRICKSMETA));
		list.add(new ItemStack(itemIn, 1, DARKMETA));
	}

	public static enum EnumType implements IStringSerializable {
		ROUGH("ROUGH", 0, 0, "prismarine", "rough"), BRICKS("BRICKS", 1, 1, "prismarine_bricks", "bricks"), DARK("DARK",
				2, 2, "dark_prismarine", "dark");
		private static final BlockPrismarine.EnumType[] field_176813_d = new BlockPrismarine.EnumType[values().length];
		private final int meta;
		private final String field_176811_f;
		private final String field_176812_g;

		private EnumType(final String p_i45692_1_, final int p_i45692_2_, final int p_i45692_3_,
				final String p_i45692_4_, final String p_i45692_5_) {
			meta = p_i45692_3_;
			field_176811_f = p_i45692_4_;
			field_176812_g = p_i45692_5_;
		}

		public int getMetadata() {
			return meta;
		}

		@Override
		public String toString() {
			return field_176811_f;
		}

		public static BlockPrismarine.EnumType func_176810_a(int p_176810_0_) {
			if (p_176810_0_ < 0 || p_176810_0_ >= field_176813_d.length) {
				p_176810_0_ = 0;
			}

			return field_176813_d[p_176810_0_];
		}

		@Override
		public String getName() {
			return field_176811_f;
		}

		public String func_176809_c() {
			return field_176812_g;
		}

		static {
			final BlockPrismarine.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockPrismarine.EnumType var3 = var0[var2];
				field_176813_d[var3.getMetadata()] = var3;
			}
		}
	}
}
