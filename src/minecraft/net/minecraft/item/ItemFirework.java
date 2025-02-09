package net.minecraft.item;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class ItemFirework extends Item {

public static final int EaZy = 1294;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000031";

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
		if (!worldIn.isRemote) {
			final EntityFireworkRocket var9 = new EntityFireworkRocket(worldIn, pos.getX() + hitX, pos.getY() + hitY,
					pos.getZ() + hitZ, stack);
			worldIn.spawnEntityInWorld(var9);

			if (!playerIn.capabilities.isCreativeMode) {
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 * 
	 * @param tooltip
	 *            All lines to display in the Item's tooltip. This is a List of
	 *            Strings.
	 * @param advanced
	 *            Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip,
			final boolean advanced) {
		if (stack.hasTagCompound()) {
			final NBTTagCompound var5 = stack.getTagCompound().getCompoundTag("Fireworks");

			if (var5 != null) {
				if (var5.hasKey("Flight", 99)) {
					tooltip.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + var5.getByte("Flight"));
				}

				final NBTTagList var6 = var5.getTagList("Explosions", 10);

				if (var6 != null && var6.tagCount() > 0) {
					for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
						final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
						final ArrayList var9 = Lists.newArrayList();
						ItemFireworkCharge.func_150902_a(var8, var9);

						if (var9.size() > 0) {
							for (int var10 = 1; var10 < var9.size(); ++var10) {
								var9.set(var10, "  " + (String) var9.get(var10));
							}

							tooltip.addAll(var9);
						}
					}
				}
			}
		}
	}
}
