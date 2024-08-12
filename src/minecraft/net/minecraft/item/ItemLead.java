package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemLead extends Item {

public static final int EaZy = 1303;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000045";

	public ItemLead() {
		setCreativeTab(CreativeTabs.tabTools);
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
		final Block var9 = worldIn.getBlockState(pos).getBlock();

		if (var9 instanceof BlockFence) {
			if (worldIn.isRemote) {
				return true;
			} else {
				func_180618_a(playerIn, worldIn, pos);
				return true;
			}
		} else {
			return false;
		}
	}

	public static boolean func_180618_a(final EntityPlayer p_180618_0_, final World worldIn,
			final BlockPos p_180618_2_) {
		EntityLeashKnot var3 = EntityLeashKnot.func_174863_b(worldIn, p_180618_2_);
		boolean var4 = false;
		final double var5 = 7.0D;
		final int var7 = p_180618_2_.getX();
		final int var8 = p_180618_2_.getY();
		final int var9 = p_180618_2_.getZ();
		final List var10 = worldIn.getEntitiesWithinAABB(EntityLiving.class,
				new AxisAlignedBB(var7 - var5, var8 - var5, var9 - var5, var7 + var5, var8 + var5, var9 + var5));
		final Iterator var11 = var10.iterator();

		while (var11.hasNext()) {
			final EntityLiving var12 = (EntityLiving) var11.next();

			if (var12.getLeashed() && var12.getLeashedToEntity() == p_180618_0_) {
				if (var3 == null) {
					var3 = EntityLeashKnot.func_174862_a(worldIn, p_180618_2_);
				}

				var12.setLeashedToEntity(var3, true);
				var4 = true;
			}
		}

		return var4;
	}
}
