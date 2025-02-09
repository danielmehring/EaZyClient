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
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockWoodSlab extends BlockSlab {

public static final int EaZy = 404;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176557_b = PropertyEnum.create("variant", BlockPlanks.EnumType.class);
	// private static final String __OBFID = "http://https://fuckuskid00000337";

	public BlockWoodSlab() {
		super(Material.wood);
		IBlockState var1 = blockState.getBaseState();

		if (!isDouble()) {
			var1 = var1.withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.BOTTOM);
		}

		setDefaultState(var1.withProperty(field_176557_b, BlockPlanks.EnumType.OAK));
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
		return Item.getItemFromBlock(Blocks.wooden_slab);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.wooden_slab);
	}

	/**
	 * Returns the slab block name with the type associated with it
	 */
	@Override
	public String getFullSlabName(final int p_150002_1_) {
		return super.getUnlocalizedName() + "." + BlockPlanks.EnumType.func_176837_a(p_150002_1_).func_176840_c();
	}

	@Override
	public IProperty func_176551_l() {
		return field_176557_b;
	}

	@Override
	public Object func_176553_a(final ItemStack p_176553_1_) {
		return BlockPlanks.EnumType.func_176837_a(p_176553_1_.getMetadata() & 7);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		if (itemIn != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
			final BlockPlanks.EnumType[] var4 = BlockPlanks.EnumType.values();
			final int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				final BlockPlanks.EnumType var7 = var4[var6];
				list.add(new ItemStack(itemIn, 1, var7.func_176839_a()));
			}
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		IBlockState var2 = getDefaultState().withProperty(field_176557_b, BlockPlanks.EnumType.func_176837_a(meta & 7));

		if (!isDouble()) {
			var2 = var2.withProperty(HALF_PROP,
					(meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return var2;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((BlockPlanks.EnumType) state.getValue(field_176557_b)).func_176839_a();

		if (!isDouble() && state.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return isDouble() ? new BlockState(this, new IProperty[] { field_176557_b })
				: new BlockState(this, new IProperty[] { HALF_PROP, field_176557_b });
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((BlockPlanks.EnumType) state.getValue(field_176557_b)).func_176839_a();
	}
}
