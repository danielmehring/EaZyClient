package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {

public static final int EaZy = 1305;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000074";

	public ItemLilyPad(final Block p_i45357_1_) {
		super(p_i45357_1_, false);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		final MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

		if (var4 == null) {
			return itemStackIn;
		} else {
			if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				final BlockPos var5 = var4.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, var5)) {
					return itemStackIn;
				}

				if (!playerIn.func_175151_a(var5.offset(var4.facing), var4.facing, itemStackIn)) {
					return itemStackIn;
				}

				final BlockPos var6 = var5.offsetUp();
				final IBlockState var7 = worldIn.getBlockState(var5);

				if (var7.getBlock().getMaterial() == Material.water
						&& ((Integer) var7.getValue(BlockLiquid.LEVEL)) == 0 && worldIn.isAirBlock(var6)) {
					worldIn.setBlockState(var6, Blocks.waterlily.getDefaultState());

					if (!playerIn.capabilities.isCreativeMode) {
						--itemStackIn.stackSize;
					}

					playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
				}
			}

			return itemStackIn;
		}
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(stack.getMetadata()));
	}
}
