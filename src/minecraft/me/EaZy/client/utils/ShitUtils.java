package me.EaZy.client.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class ShitUtils {

	public static final int EaZy = 2061;
	
	public static double holoposX;
	public static double holoposY;
	public static double holoposZ;

	public static double oholoposX;
	public static double oholoposY;
	public static double oholoposZ;

	public static String holoSpamText = "";

	public static double min;
	public static double max;

	public static double motionX;
	public static double motionY;
	public static double motionZ;

	public static boolean motion = false;
	public static boolean randomColors = false;

	public static String[] colors = new String[] { "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a",
			"§b", "§c", "§d", "§e", "§f" };

	public static String[] spcialCodes = new String[] { "§m", "§n", "§k", "§l", "§o" };

	public static void nextHoloSpammer() {
		motionX = MathUtils.round(random(min, max), 4);
		motionY = MathUtils.round(Math.abs(random(min, max)), 4);
		motionZ = MathUtils.round(random(min, max), 4);
		holoposX = oholoposX + new Random().nextInt(30 - (-30)) + (-30);
		holoposY = oholoposY + new Random().nextInt(10 - (-10)) + (-10);
		holoposZ = oholoposZ + new Random().nextInt(30 - (-30)) + (-30);
		addHoloSpammergramToInv(holoSpamText);
	}

	public static void addHoloSpammergramToInv(String text) {
		ItemStack stack = new ItemStack(Items.armor_stand);
		try {
			if (randomColors) {
				if (!text.toLowerCase().startsWith("block:")) {
					text = getRandomColor() + text;
				}
			}
			if (motion) {
				if (text.toLowerCase().startsWith("block:")) {
					stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Invisible:1b,Pos:[0:" + holoposX + "d,1:"
							+ holoposY + "d,2:" + holoposZ + "d],Motion:[0:" + motionX + "d,1:" + motionY + "d,2:"
							+ motionZ + "d],Equipment:[{id:},{id:},{id:},{id:},{id:\"" + text.substring(6) + "\"}]}}"));
				} else {
					stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Pos:[" + holoposX + "," + holoposY + ","
							+ holoposZ + "],Motion:[0:" + motionX + "d,1:" + motionY + "d,2:" + motionZ
							+ "d],Invisible:1,CustomName:\"" + text + "\",CustomNameVisible:true}}"));
				}
			} else {
				if (text.toLowerCase().startsWith("block:")) {
					stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Invisible:1b,NoGravity:1b,Pos:[0:" + holoposX
							+ "d,1:" + holoposY + "d,2:" + holoposZ + "d],Equipment:[{id:},{id:},{id:},{id:},{id:\""
							+ text.substring(6) + "\"}]}}"));
				} else {
					stack.setTagCompound(JsonToNBT.parse("{EntityTag:{Pos:[" + holoposX + "," + holoposY + ","
							+ holoposZ + "],Invisible:1,NoGravity:1,CustomName:\"" + text
							+ "\",CustomNameVisible:true}}"));
				}
			}
		} catch (NBTException e) {
			e.printStackTrace();
		}
		stack.setStackDisplayName("§6HoloSpammer");
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
				Minecraft.thePlayer.inventory.currentItem + 36, stack));
	}

	public static double random(double min, double max) {
		double a = ((Math.random() - 0.5) * 2 * (max - min));
		return a >= 0 ? a + min : a - min;
	}

	public static String getRandomColor() {
		String end = "";

		end += colors[(int) (Math.random() * 16)];

		int specialCharsAmount = (int) (Math.random() * 2);
		for (int i = 0; i < specialCharsAmount; i++) {
			String specialChar = spcialCodes[(int) (Math.random() * 5)];
			while (end.contains(specialChar)) { // keine duplikate
				specialChar = spcialCodes[(int) (Math.random() * 5)];
			}
			end += specialChar;
		}
		return end;
	}

}
