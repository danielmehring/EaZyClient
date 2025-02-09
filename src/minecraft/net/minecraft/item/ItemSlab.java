package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {

public static final int EaZy = 1325;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockSlab field_150949_c;
	private final BlockSlab field_179226_c;
	// private static final String __OBFID = "http://https://fuckuskid00000071";

	public ItemSlab(final Block p_i45782_1_, final BlockSlab p_i45782_2_, final BlockSlab p_i45782_3_) {
		super(p_i45782_1_);
		field_150949_c = p_i45782_2_;
		field_179226_c = p_i45782_3_;
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/**
	 * Converts the given ItemStack damage value into a metadata value to be
	 * placed in the world when this Item is placed as a Block (mostly used with
	 * ItemBlocks).
	 */
	@Override
	public int getMetadata(final int damage) {
		return damage;
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return field_150949_c.getFullSlabName(stack.getMetadata());
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn,
			final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (stack.stackSize == 0) {
			return false;
		} else if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
			return false;
		} else {
			final Object var9 = field_150949_c.func_176553_a(stack);
			final IBlockState var10 = worldIn.getBlockState(pos);

			if (var10.getBlock() == field_150949_c) {
				final IProperty var11 = field_150949_c.func_176551_l();
				final Comparable var12 = var10.getValue(var11);
				final BlockSlab.EnumBlockHalf var13 = (BlockSlab.EnumBlockHalf) var10.getValue(BlockSlab.HALF_PROP);

				if ((side == EnumFacing.UP && var13 == BlockSlab.EnumBlockHalf.BOTTOM
						|| side == EnumFacing.DOWN && var13 == BlockSlab.EnumBlockHalf.TOP) && var12 == var9) {
					final IBlockState var14 = field_179226_c.getDefaultState().withProperty(var11, var12);

					if (worldIn.checkNoEntityCollision(field_179226_c.getCollisionBoundingBox(worldIn, pos, var14))
							&& worldIn.setBlockState(pos, var14, 3)) {
						worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
								field_179226_c.stepSound.getPlaceSound(),
								(field_179226_c.stepSound.getVolume() + 1.0F) / 2.0F,
								field_179226_c.stepSound.getFrequency() * 0.8F);
						--stack.stackSize;
					}

					return true;
				}
			}

			return func_180615_a(stack, worldIn, pos.offset(side), var9) ? true
					: super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(final World worldIn, BlockPos p_179222_2_, final EnumFacing p_179222_3_,
			final EntityPlayer p_179222_4_, final ItemStack p_179222_5_) {
		final BlockPos var6 = p_179222_2_;
		final IProperty var7 = field_150949_c.func_176551_l();
		final Object var8 = field_150949_c.func_176553_a(p_179222_5_);
		final IBlockState var9 = worldIn.getBlockState(p_179222_2_);

		if (var9.getBlock() == field_150949_c) {
			final boolean var10 = var9.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;

			if ((p_179222_3_ == EnumFacing.UP && !var10 || p_179222_3_ == EnumFacing.DOWN && var10)
					&& var8 == var9.getValue(var7)) {
				return true;
			}
		}

		p_179222_2_ = p_179222_2_.offset(p_179222_3_);
		final IBlockState var11 = worldIn.getBlockState(p_179222_2_);
		return var11.getBlock() == field_150949_c && var8 == var11.getValue(var7) ? true
				: super.canPlaceBlockOnSide(worldIn, var6, p_179222_3_, p_179222_4_, p_179222_5_);
	}

	private boolean func_180615_a(final ItemStack p_180615_1_, final World worldIn, final BlockPos p_180615_3_,
			final Object p_180615_4_) {
		final IBlockState var5 = worldIn.getBlockState(p_180615_3_);

		if (var5.getBlock() == field_150949_c) {
			final Comparable var6 = var5.getValue(field_150949_c.func_176551_l());

			if (var6 == p_180615_4_) {
				final IBlockState var7 = field_179226_c.getDefaultState().withProperty(field_150949_c.func_176551_l(),
						var6);

				if (worldIn.checkNoEntityCollision(field_179226_c.getCollisionBoundingBox(worldIn, p_180615_3_, var7))
						&& worldIn.setBlockState(p_180615_3_, var7, 3)) {
					worldIn.playSoundEffect(p_180615_3_.getX() + 0.5F, p_180615_3_.getY() + 0.5F,
							p_180615_3_.getZ() + 0.5F, field_179226_c.stepSound.getPlaceSound(),
							(field_179226_c.stepSound.getVolume() + 1.0F) / 2.0F,
							field_179226_c.stepSound.getFrequency() * 0.8F);
					--p_180615_1_.stackSize;
				}

				return true;
			}
		}

		return false;
	}
}
