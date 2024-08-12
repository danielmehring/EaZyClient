package me.EaZy.client.utils.holoimage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class Image {

	public static final int EaZy = 2059;
	
	public static double x = -1.0D;
	public static double y = -1.0D;
	public static double z = -1.0D;
	public static int counter = 0;
	public static ItemStack[] stands;

	public static void setLoc(double ix, double iy, double iz) {
		x = ix;
		y = iy;
		z = iz;
	}

	public static void setup(String link, int width, int height) {
		ImageStand is = null;
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(link));
		} catch (MalformedURLException localMalformedURLException) {
			try {
				image = ImageIO.read(new URL("file:///" + link));
			} catch (MalformedURLException e) {} catch (IOException e) {}
		} catch (Exception localException) {}
		if (image != null) {
			try {
				is = new ImageStand(image, width, height, '\u2B1B');
			} catch (Exception localException1) {
				localException1.printStackTrace();
			}
			if (is == null) {
				Client.msg("§cImage not found!");
				return;
			}
			stands = is.getArmorStands(x, y, z);
			counter = 0;
			giveNextArmorstand();
		} else {
			Client.msg("§cImage not found!");
		}
	}

	public static void giveNextArmorstand() {
		if (stands != null) {
			Minecraft.getMinecraft().thePlayer.sendQueue
					.addToSendQueue(new C10PacketCreativeInventoryAction(36, stands[(counter++)]));
			if (counter >= stands.length) {
				counter = 0;
				stands = null;
			}
		} else {
			Minecraft mc = Minecraft.getMinecraft();
			if ((mc.thePlayer.getHeldItem() != null)
					&& (mc.thePlayer.getHeldItem().getDisplayName().startsWith("ImageStand #"))) {
				int slotID = mc.playerController.currentPlayerItem + 36;
				mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(slotID, null));
			}
		}
	}

}
