package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {

public static final int EaZy = 1301;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Class hangingEntityClass;
	// private static final String __OBFID = "http://https://fuckuskid00000038";

	public ItemHangingEntity(final Class p_i45342_1_) {
		hangingEntityClass = p_i45342_1_;
		setCreativeTab(CreativeTabs.tabDecorations);
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
		if (side == EnumFacing.DOWN) {
			return false;
		} else if (side == EnumFacing.UP) {
			return false;
		} else {
			final BlockPos var9 = pos.offset(side);

			if (!playerIn.func_175151_a(var9, side, stack)) {
				return false;
			} else {
				final EntityHanging var10 = func_179233_a(worldIn, var9, side);

				if (var10 != null && var10.onValidSurface()) {
					if (!worldIn.isRemote) {
						worldIn.spawnEntityInWorld(var10);
					}

					--stack.stackSize;
				}

				return true;
			}
		}
	}

	private EntityHanging func_179233_a(final World worldIn, final BlockPos p_179233_2_, final EnumFacing p_179233_3_) {
		return hangingEntityClass == EntityPainting.class ? new EntityPainting(worldIn, p_179233_2_, p_179233_3_)
				: hangingEntityClass == EntityItemFrame.class ? new EntityItemFrame(worldIn, p_179233_2_, p_179233_3_)
						: null;
	}
}
