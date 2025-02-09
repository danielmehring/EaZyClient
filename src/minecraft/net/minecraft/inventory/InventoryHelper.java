package net.minecraft.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class InventoryHelper {

public static final int EaZy = 1234;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Random field_180177_a = new Random();
	// private static final String __OBFID = "http://https://fuckuskid00002262";

	public static void dropInventoryItems(final World worldIn, final BlockPos p_180175_1_,
			final IInventory p_180175_2_) {
		func_180174_a(worldIn, p_180175_1_.getX(), p_180175_1_.getY(), p_180175_1_.getZ(), p_180175_2_);
	}

	public static void func_180176_a(final World worldIn, final Entity p_180176_1_, final IInventory p_180176_2_) {
		func_180174_a(worldIn, p_180176_1_.posX, p_180176_1_.posY, p_180176_1_.posZ, p_180176_2_);
	}

	private static void func_180174_a(final World worldIn, final double p_180174_1_, final double p_180174_3_,
			final double p_180174_5_, final IInventory p_180174_7_) {
		for (int var8 = 0; var8 < p_180174_7_.getSizeInventory(); ++var8) {
			final ItemStack var9 = p_180174_7_.getStackInSlot(var8);

			if (var9 != null) {
				func_180173_a(worldIn, p_180174_1_, p_180174_3_, p_180174_5_, var9);
			}
		}
	}

	private static void func_180173_a(final World worldIn, final double p_180173_1_, final double p_180173_3_,
			final double p_180173_5_, final ItemStack p_180173_7_) {
		final float var8 = field_180177_a.nextFloat() * 0.8F + 0.1F;
		final float var9 = field_180177_a.nextFloat() * 0.8F + 0.1F;
		final float var10 = field_180177_a.nextFloat() * 0.8F + 0.1F;

		while (p_180173_7_.stackSize > 0) {
			int var11 = field_180177_a.nextInt(21) + 10;

			if (var11 > p_180173_7_.stackSize) {
				var11 = p_180173_7_.stackSize;
			}

			p_180173_7_.stackSize -= var11;
			final EntityItem var12 = new EntityItem(worldIn, p_180173_1_ + var8, p_180173_3_ + var9,
					p_180173_5_ + var10, new ItemStack(p_180173_7_.getItem(), var11, p_180173_7_.getMetadata()));

			if (p_180173_7_.hasTagCompound()) {
				var12.getEntityItem().setTagCompound((NBTTagCompound) p_180173_7_.getTagCompound().copy());
			}

			final float var13 = 0.05F;
			var12.motionX = field_180177_a.nextGaussian() * var13;
			var12.motionY = field_180177_a.nextGaussian() * var13 + 0.20000000298023224D;
			var12.motionZ = field_180177_a.nextGaussian() * var13;
			worldIn.spawnEntityInWorld(var12);
		}
	}
}
