package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import com.google.common.base.Predicate;

public class BlockOldLeaf extends BlockLeaves {

public static final int EaZy = 339;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockPlanks.EnumType.class,
			new Predicate() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002085";
				public boolean func_180202_a(final BlockPlanks.EnumType p_180202_1_) {
					return p_180202_1_.func_176839_a() < 4;
				}

				@Override
				public boolean apply(final Object p_apply_1_) {
					return func_180202_a((BlockPlanks.EnumType) p_apply_1_);
				}
			});
	// private static final String __OBFID = "http://https://fuckuskid00000280";

	public BlockOldLeaf() {
		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROP, BlockPlanks.EnumType.OAK)
				.withProperty(field_176236_b, true)
				.withProperty(field_176237_a, true));
	}

	@Override
	public int getRenderColor(final IBlockState state) {
		if (state.getBlock() != this) {
			return super.getRenderColor(state);
		} else {
			final BlockPlanks.EnumType var2 = (BlockPlanks.EnumType) state.getValue(VARIANT_PROP);
			return var2 == BlockPlanks.EnumType.SPRUCE ? ColorizerFoliage.getFoliageColorPine()
					: var2 == BlockPlanks.EnumType.BIRCH ? ColorizerFoliage.getFoliageColorBirch()
							: super.getRenderColor(state);
		}
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		final IBlockState var4 = worldIn.getBlockState(pos);

		if (var4.getBlock() == this) {
			final BlockPlanks.EnumType var5 = (BlockPlanks.EnumType) var4.getValue(VARIANT_PROP);

			if (var5 == BlockPlanks.EnumType.SPRUCE) {
				return ColorizerFoliage.getFoliageColorPine();
			}

			if (var5 == BlockPlanks.EnumType.BIRCH) {
				return ColorizerFoliage.getFoliageColorBirch();
			}
		}

		return super.colorMultiplier(worldIn, pos, renderPass);
	}

	@Override
	protected void func_176234_a(final World worldIn, final BlockPos p_176234_2_, final IBlockState p_176234_3_,
			final int p_176234_4_) {
		if (p_176234_3_.getValue(VARIANT_PROP) == BlockPlanks.EnumType.OAK && worldIn.rand.nextInt(p_176234_4_) == 0) {
			spawnAsEntity(worldIn, p_176234_2_, new ItemStack(Items.apple, 1, 0));
		}
	}

	@Override
	protected int func_176232_d(final IBlockState p_176232_1_) {
		return p_176232_1_.getValue(VARIANT_PROP) == BlockPlanks.EnumType.JUNGLE ? 40
				: super.func_176232_d(p_176232_1_);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.OAK.func_176839_a()));
		list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()));
		list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()));
		list.add(new ItemStack(itemIn, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()));
	}

	@Override
	protected ItemStack createStackedBlock(final IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1,
				((BlockPlanks.EnumType) state.getValue(VARIANT_PROP)).func_176839_a());
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(VARIANT_PROP, func_176233_b(meta))
				.withProperty(field_176237_a, (meta & 4) == 0)
				.withProperty(field_176236_b, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((BlockPlanks.EnumType) state.getValue(VARIANT_PROP)).func_176839_a();

		if (!((Boolean) state.getValue(field_176237_a))) {
			var3 |= 4;
		}

		if (((Boolean) state.getValue(field_176236_b))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	public BlockPlanks.EnumType func_176233_b(final int p_176233_1_) {
		return BlockPlanks.EnumType.func_176837_a((p_176233_1_ & 3) % 4);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT_PROP, field_176236_b, field_176237_a });
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((BlockPlanks.EnumType) state.getValue(VARIANT_PROP)).func_176839_a();
	}

	@Override
	public void harvestBlock(final World worldIn, final EntityPlayer playerIn, final BlockPos pos,
			final IBlockState state, final TileEntity te) {
		if (!worldIn.isRemote && playerIn.getCurrentEquippedItem() != null
				&& playerIn.getCurrentEquippedItem().getItem() == Items.shears) {
			playerIn.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
			spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1,
					((BlockPlanks.EnumType) state.getValue(VARIANT_PROP)).func_176839_a()));
		} else {
			super.harvestBlock(worldIn, playerIn, pos, state, te);
		}
	}
}
