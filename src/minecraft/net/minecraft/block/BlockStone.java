package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.List;
import java.util.Random;

public class BlockStone extends Block {

public static final int EaZy = 390;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockStone.EnumType.class);
	// private static final String __OBFID = "http://https://fuckuskid00000317";

	public BlockStone() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROP, BlockStone.EnumType.STONE));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return state.getValue(VARIANT_PROP) == BlockStone.EnumType.STONE ? Item.getItemFromBlock(Blocks.cobblestone)
				: Item.getItemFromBlock(Blocks.stone);
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((BlockStone.EnumType) state.getValue(VARIANT_PROP)).getMetaFromState();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		final BlockStone.EnumType[] var4 = BlockStone.EnumType.values();
		final int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final BlockStone.EnumType var7 = var4[var6];
			list.add(new ItemStack(itemIn, 1, var7.getMetaFromState()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(VARIANT_PROP, BlockStone.EnumType.getStateFromMeta(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockStone.EnumType) state.getValue(VARIANT_PROP)).getMetaFromState();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT_PROP });
	}

	public static enum EnumType implements IStringSerializable {
		STONE("STONE", 0, 0, "stone"), GRANITE("GRANITE", 1, 1, "granite"), GRANITE_SMOOTH("GRANITE_SMOOTH", 2, 2,
				"smooth_granite", "graniteSmooth"), DIORITE("DIORITE", 3, 3, "diorite"), DIORITE_SMOOTH(
						"DIORITE_SMOOTH", 4, 4, "smooth_diorite",
						"dioriteSmooth"), ANDESITE("ANDESITE", 5, 5, "andesite"), ANDESITE_SMOOTH("ANDESITE_SMOOTH", 6,
								6, "smooth_andesite", "andesiteSmooth");
		private static final BlockStone.EnumType[] BLOCKSTATES = new BlockStone.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String field_176654_k;

		private EnumType(final String p_i45680_1_, final int p_i45680_2_, final int p_i45680_3_,
				final String p_i45680_4_) {
			this(p_i45680_1_, p_i45680_2_, p_i45680_3_, p_i45680_4_, p_i45680_4_);
		}

		private EnumType(final String p_i45681_1_, final int p_i45681_2_, final int p_i45681_3_,
				final String p_i45681_4_, final String p_i45681_5_) {
			meta = p_i45681_3_;
			name = p_i45681_4_;
			field_176654_k = p_i45681_5_;
		}

		public int getMetaFromState() {
			return meta;
		}

		@Override
		public String toString() {
			return name;
		}

		public static BlockStone.EnumType getStateFromMeta(int p_176643_0_) {
			if (p_176643_0_ < 0 || p_176643_0_ >= BLOCKSTATES.length) {
				p_176643_0_ = 0;
			}

			return BLOCKSTATES[p_176643_0_];
		}

		@Override
		public String getName() {
			return name;
		}

		public String func_176644_c() {
			return field_176654_k;
		}

		static {
			final BlockStone.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockStone.EnumType var3 = var0[var2];
				BLOCKSTATES[var3.getMetaFromState()] = var3;
			}
		}
	}
}
