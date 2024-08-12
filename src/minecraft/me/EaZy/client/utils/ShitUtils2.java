package me.EaZy.client.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class ShitUtils2 {

	public static final int EaZy = 2062;
	
	public static double oholoposX;
	public static double oholoposY;
	public static double oholoposZ;

	public static double motionX;
	public static double motionY;
	public static double motionZ;

	public static String holoSpamText = "";

	public static double min;
	public static double max;

	public static boolean lockPos;

	public static void nextHoloSpammer() {
		motionX = MathUtils.round(random(min, max), 4);
		motionY = MathUtils.round(Math.abs(random(min, max)), 4);
		motionZ = MathUtils.round(random(min, max), 4);
		if (!lockPos) {
			oholoposX = Minecraft.thePlayer.posX;
			oholoposY = Minecraft.thePlayer.posY;
			oholoposZ = Minecraft.thePlayer.posZ;
		}
		addHoloSpammergramToInv(holoSpamText);
	}

	public static void addHoloSpammergramToInv(String text) {
		ItemStack stack = new ItemStack(Items.armor_stand);
		try {
			if (text.toLowerCase().startsWith("block:")) {
				stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Invisible:1b,Pos:[0:" + oholoposX + "d,1:" + oholoposY
						+ "d,2:" + oholoposZ + "d],Motion:[0:" + motionX + "d,1:" + motionY + "d,2:" + motionZ
						+ "d],Equipment:[{id:},{id:},{id:},{id:},{id:\"" + text.substring(6) + "\"}]}}"));
			} else {
				stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Pos:[" + oholoposX + "," + oholoposY + "," + oholoposZ
						+ "],Motion:[0:" + motionX + "d,1:" + motionY + "d,2:" + motionZ
						+ "d],Invisible:1,CustomName:\"" + text + "\",CustomNameVisible:true}}"));
			}
		} catch (NBTException e) {
			e.printStackTrace();
		}
		stack.setStackDisplayName("§6MotionHoloSpammer");
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
				Minecraft.thePlayer.inventory.currentItem + 36, stack));
	}

	public static double random(double min, double max) {
		double a = ((Math.random() - 0.5) * 2 * (max - min));
		return a >= 0 ? a + min : a - min;
	}

}
