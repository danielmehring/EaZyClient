package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block {

public static final int EaZy = 375;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockSilverfish.EnumType.class);
	// private static final String __OBFID = "http://https://fuckuskid00000271";

	public BlockSilverfish() {
		super(Material.clay);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROP, BlockSilverfish.EnumType.STONE));
		setHardness(0.0F);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	public static boolean func_176377_d(final IBlockState p_176377_0_) {
		final Block var1 = p_176377_0_.getBlock();
		return p_176377_0_ == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP,
				BlockStone.EnumType.STONE) || var1 == Blocks.cobblestone || var1 == Blocks.stonebrick;
	}

	@Override
	protected ItemStack createStackedBlock(final IBlockState state) {
		switch (BlockSilverfish.SwitchEnumType.field_180178_a[((BlockSilverfish.EnumType) state.getValue(VARIANT_PROP))
				.ordinal()]) {
			case 1:
				return new ItemStack(Blocks.cobblestone);

			case 2:
				return new ItemStack(Blocks.stonebrick);

			case 3:
				return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetaFromState());

			case 4:
				return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetaFromState());

			case 5:
				return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetaFromState());

			default:
				return new ItemStack(Blocks.stone);
		}
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {
		if (!worldIn.isRemote && worldIn.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			final EntitySilverfish var6 = new EntitySilverfish(worldIn);
			var6.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
			worldIn.spawnEntityInWorld(var6);
			var6.spawnExplosionParticle();
		}
	}

	@Override
	public int getDamageValue(final World worldIn, final BlockPos pos) {
		final IBlockState var3 = worldIn.getBlockState(pos);
		return var3.getBlock().getMetaFromState(var3);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		final BlockSilverfish.EnumType[] var4 = BlockSilverfish.EnumType.values();
		final int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final BlockSilverfish.EnumType var7 = var4[var6];
			list.add(new ItemStack(itemIn, 1, var7.func_176881_a()));
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(VARIANT_PROP, BlockSilverfish.EnumType.func_176879_a(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockSilverfish.EnumType) state.getValue(VARIANT_PROP)).func_176881_a();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT_PROP });
	}

	public static enum EnumType implements IStringSerializable {
		STONE("STONE", 0, 0, "stone", (BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002097";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE);
			}
		},
		COBBLESTONE("COBBLESTONE", 1, 1, "cobblestone", "cobble", (BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002096";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.cobblestone.getDefaultState();
			}
		},
		STONEBRICK("STONEBRICK", 2, 2, "stone_brick", "brick", (BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002095";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP,
						BlockStoneBrick.EnumType.DEFAULT);
			}
		},
		MOSSY_STONEBRICK("MOSSY_STONEBRICK", 3, 3, "mossy_brick", "mossybrick", (BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002094";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP,
						BlockStoneBrick.EnumType.MOSSY);
			}
		},
		CRACKED_STONEBRICK("CRACKED_STONEBRICK", 4, 4, "cracked_brick", "crackedbrick",
				(BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002093";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP,
						BlockStoneBrick.EnumType.CRACKED);
			}
		},
		CHISELED_STONEBRICK("CHISELED_STONEBRICK", 5, 5, "chiseled_brick", "chiseledbrick",
				(BlockSilverfish.SwitchEnumType) null) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002092";
			@Override
			public IBlockState func_176883_d() {
				return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP,
						BlockStoneBrick.EnumType.CHISELED);
			}
		};
		private static final BlockSilverfish.EnumType[] field_176885_g = new BlockSilverfish.EnumType[values().length];
		private final int field_176893_h;
		private final String field_176894_i;
		private final String field_176891_j;

		private EnumType(final String p_i45704_1_, final int p_i45704_2_, final int p_i45704_3_,
				final String p_i45704_4_) {
			this(p_i45704_1_, p_i45704_2_, p_i45704_3_, p_i45704_4_, p_i45704_4_);
		}

		private EnumType(final String p_i45705_1_, final int p_i45705_2_, final int p_i45705_3_,
				final String p_i45705_4_, final String p_i45705_5_) {
			field_176893_h = p_i45705_3_;
			field_176894_i = p_i45705_4_;
			field_176891_j = p_i45705_5_;
		}

		public int func_176881_a() {
			return field_176893_h;
		}

		@Override
		public String toString() {
			return field_176894_i;
		}

		public static BlockSilverfish.EnumType func_176879_a(int p_176879_0_) {
			if (p_176879_0_ < 0 || p_176879_0_ >= field_176885_g.length) {
				p_176879_0_ = 0;
			}

			return field_176885_g[p_176879_0_];
		}

		@Override
		public String getName() {
			return field_176894_i;
		}

		public String func_176882_c() {
			return field_176891_j;
		}

		public abstract IBlockState func_176883_d();

		public static BlockSilverfish.EnumType func_176878_a(final IBlockState p_176878_0_) {
			final BlockSilverfish.EnumType[] var1 = values();
			final int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				final BlockSilverfish.EnumType var4 = var1[var3];

				if (p_176878_0_ == var4.func_176883_d()) {
					return var4;
				}
			}

			return STONE;
		}

		EnumType(final String p_i45706_1_, final int p_i45706_2_, final int p_i45706_3_, final String p_i45706_4_,
				final BlockSilverfish.SwitchEnumType p_i45706_5_) {
			this(p_i45706_1_, p_i45706_2_, p_i45706_3_, p_i45706_4_);
		}

		EnumType(final String p_i45707_1_, final int p_i45707_2_, final int p_i45707_3_, final String p_i45707_4_,
				final String p_i45707_5_, final BlockSilverfish.SwitchEnumType p_i45707_6_) {
			this(p_i45707_1_, p_i45707_2_, p_i45707_3_, p_i45707_4_, p_i45707_5_);
		}

		static {
			final BlockSilverfish.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockSilverfish.EnumType var3 = var0[var2];
				field_176885_g[var3.func_176881_a()] = var3;
			}
		}
	}

	static final class SwitchEnumType {
		static final int[] field_180178_a = new int[BlockSilverfish.EnumType.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002099";

		static {
			try {
				field_180178_a[BlockSilverfish.EnumType.COBBLESTONE.ordinal()] = 1;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_180178_a[BlockSilverfish.EnumType.STONEBRICK.ordinal()] = 2;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180178_a[BlockSilverfish.EnumType.MOSSY_STONEBRICK.ordinal()] = 3;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180178_a[BlockSilverfish.EnumType.CRACKED_STONEBRICK.ordinal()] = 4;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180178_a[BlockSilverfish.EnumType.CHISELED_STONEBRICK.ordinal()] = 5;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
