package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTallGrass extends BlockBush implements IGrowable {

public static final int EaZy = 394;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176497_a = PropertyEnum.create("type", BlockTallGrass.EnumType.class);
	// private static final String __OBFID = "http://https://fuckuskid00000321";

	protected BlockTallGrass() {
		super(Material.vine);
		setDefaultState(blockState.getBaseState().withProperty(field_176497_a, BlockTallGrass.EnumType.DEAD_BUSH));
		final float var1 = 0.4F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
	}

	@Override
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(0.5D, 1.0D);
	}

	@Override
	public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
		return canPlaceBlockOn(worldIn.getBlockState(p_180671_2_.offsetDown()).getBlock());
	}

	/**
	 * Whether this Block can be replaced directly by other blocks (true for
	 * e.g. tall grass)
	 */
	@Override
	public boolean isReplaceable(final World worldIn, final BlockPos pos) {
		return true;
	}

	@Override
	public int getRenderColor(final IBlockState state) {
		if (state.getBlock() != this) {
			return super.getRenderColor(state);
		} else {
			final BlockTallGrass.EnumType var2 = (BlockTallGrass.EnumType) state.getValue(field_176497_a);
			return var2 == BlockTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
		}
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		return worldIn.getBiomeGenForCoords(pos).func_180627_b(pos);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return rand.nextInt(8) == 0 ? Items.wheat_seeds : null;
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(final int fortune, final Random random) {
		return 1 + random.nextInt(fortune * 2 + 1);
	}

	@Override
	public void harvestBlock(final World worldIn, final EntityPlayer playerIn, final BlockPos pos,
			final IBlockState state, final TileEntity te) {
		if (!worldIn.isRemote && playerIn.getCurrentEquippedItem() != null
				&& playerIn.getCurrentEquippedItem().getItem() == Items.shears) {
			playerIn.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
			spawnAsEntity(worldIn, pos, new ItemStack(Blocks.tallgrass, 1,
					((BlockTallGrass.EnumType) state.getValue(field_176497_a)).func_177044_a()));
		} else {
			super.harvestBlock(worldIn, playerIn, pos, state, te);
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
		for (int var4 = 1; var4 < 3; ++var4) {
			list.add(new ItemStack(itemIn, 1, var4));
		}
	}

	@Override
	public boolean isStillGrowing(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_,
			final boolean p_176473_4_) {
		return p_176473_3_.getValue(field_176497_a) != BlockTallGrass.EnumType.DEAD_BUSH;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_,
			final IBlockState p_180670_4_) {
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_,
			final IBlockState p_176474_4_) {
		BlockDoublePlant.EnumPlantType var5 = BlockDoublePlant.EnumPlantType.GRASS;

		if (p_176474_4_.getValue(field_176497_a) == BlockTallGrass.EnumType.FERN) {
			var5 = BlockDoublePlant.EnumPlantType.FERN;
		}

		if (Blocks.double_plant.canPlaceBlockAt(worldIn, p_176474_3_)) {
			Blocks.double_plant.func_176491_a(worldIn, p_176474_3_, var5, 2);
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176497_a, BlockTallGrass.EnumType.func_177045_a(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockTallGrass.EnumType) state.getValue(field_176497_a)).func_177044_a();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176497_a });
	}

	/**
	 * Get the OffsetType for this Block. Determines if the model is rendered
	 * slightly offset.
	 */
	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XYZ;
	}

	public static enum EnumType implements IStringSerializable {
		DEAD_BUSH("DEAD_BUSH", 0, 0, "dead_bush"), GRASS("GRASS", 1, 1, "tall_grass"), FERN("FERN", 2, 2, "fern");
		private static final BlockTallGrass.EnumType[] field_177048_d = new BlockTallGrass.EnumType[values().length];
		private final int field_177049_e;
		private final String field_177046_f;

		private EnumType(final String p_i45676_1_, final int p_i45676_2_, final int p_i45676_3_,
				final String p_i45676_4_) {
			field_177049_e = p_i45676_3_;
			field_177046_f = p_i45676_4_;
		}

		public int func_177044_a() {
			return field_177049_e;
		}

		@Override
		public String toString() {
			return field_177046_f;
		}

		public static BlockTallGrass.EnumType func_177045_a(int p_177045_0_) {
			if (p_177045_0_ < 0 || p_177045_0_ >= field_177048_d.length) {
				p_177045_0_ = 0;
			}

			return field_177048_d[p_177045_0_];
		}

		@Override
		public String getName() {
			return field_177046_f;
		}

		static {
			final BlockTallGrass.EnumType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockTallGrass.EnumType var3 = var0[var2];
				field_177048_d[var3.func_177044_a()] = var3;
			}
		}
	}
}
