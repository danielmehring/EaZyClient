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

public class BlockRedSandstone extends Block {

public static final int EaZy = 360;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockRedSandstone.EnumType.class);
	// private static final String __OBFID = "http://https://fuckuskid00002072";

	public BlockRedSandstone() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, BlockRedSandstone.EnumType.DEFAULT));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((BlockRedSandstone.EnumType) state.getValue(TYPE)).getMetaFromState();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		final BlockRedSandstone.EnumType[] var4 = BlockRedSandstone.EnumType.values();
		final int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final BlockRedSandstone.EnumType var7 = var4[var6];
			list.add(new ItemStack(itemIn, 1, var7.getMetaFromState()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(TYPE, BlockRedSandstone.EnumType.func_176825_a(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockRedSandstone.EnumType) state.getValue(TYPE)).getMetaFromState();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT("DEFAULT", 0, 0, "red_sandstone", "default"), CHISELED("CHISELED", 1, 1, "chiseled_red_sandstone",
				"chiseled"), SMOOTH("SMOOTH", 2, 2, "smooth_red_sandstone", "smooth");
		private static final BlockRedSandstone.EnumType[] field_176831_d = new BlockRedSandstone.EnumType[values().length];
		private final int field_176832_e;
		private final String field_176829_f;
		private final String field_176830_g;

		private EnumType(final String p_i45690_1_, final int p_i45690_2_, final int p_i45690_3_,
				final String p_i45690_4_, final String p_i45690_5_) {
			field_176832_e = p_i45690_3_;
			field_176829_f = p_i45690_4_;
			field_176830_g = p_i45690_5_;
		}

		public int getMetaFromState() {
			return field_176832_e;
		}

		@Override
		public String toString() {
			return field_176829_f;
		}

		public static BlockRedSandstone.EnumType func_176825_a(int p_176825_0_) {
			if (p_176825_0_ < 0 || p_176825_0_ >= field_176831_d.length) {
				p_176825_0_ = 0;
			}

			return field_176831_d[p_176825_0_];
		}

		@Override
		public String getName() {
			return field_176829_f;
		}

		public String func_176828_c() {
			return field_176830_g;
		}

		static {
			final BlockRedSandstone.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockRedSandstone.EnumType var3 = var0[var2];
				field_176831_d[var3.getMetaFromState()] = var3;
			}
		}
	}
}
