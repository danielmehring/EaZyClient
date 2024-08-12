package me.EaZy.client.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class ShitUtils3 {

	public static final int EaZy = 2063;

	public static double oholoposX;
	public static double oholoposY;
	public static double oholoposZ;

	public static double motionX;
	public static double motionY;
	public static double motionZ;

	public static double min;
	public static double max;

	public static void nextHoloSpammer() {
		motionX = MathUtils.round(random(min, max), 4);
		motionY = MathUtils.round(Math.abs(random(min, max)), 4);
		motionZ = MathUtils.round(random(min, max), 4);
		addHoloSpammergramToInv();
	}

	public static void addHoloSpammergramToInv() {
		ItemStack stack = new ItemStack(Items.armor_stand);
		try {
			int i = (int) (Math.random() * 16777215);
			stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Motion:[0:" + motionX + "d,1:" + motionY + "d,2:"
					+ motionZ + "d,],Pos:[0:" + oholoposX + "d,1:" + oholoposY + "d,2:"
					+ oholoposZ + "d,],Equipment:[0:{},1:" + "{id:\"leather_boots\",Count:1b,tag:{display:{color:" + i
					+ ",},},},2:" + "{id:\"leather_leggings\",Count:1b,tag:{display:{color:" + i + ",},},},3:"
					+ "{id:\"leather_chestplate\",Count:1b,tag:{display:{color:" + i + ",},},},4:"
					+ "{id:\"leather_helmet\",Count:1b,tag:{display:{color:" + i + ",},},},],Invisible:1b,},}"));
		} catch (NBTException e) {
			e.printStackTrace();
		}
		stack.setStackDisplayName("§6ColoredHoloSpammer");
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
				new C10PacketCreativeInventoryAction(Minecraft.thePlayer.inventory.currentItem + 36, stack));
	}

	public static double random(double min, double max) {
		double a = ((Math.random() - 0.5) * 2 * (max - min));
		return a >= 0 ? a + min : a - min;
	}

}
