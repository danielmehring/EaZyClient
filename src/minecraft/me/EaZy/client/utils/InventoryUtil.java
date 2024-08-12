package me.EaZy.client.utils;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.AutoArmor;
import me.EaZy.client.modules.YesCheat;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class InventoryUtil {

	public static final int EaZy = 219;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static void switchItemsInSlots(final int slot1, final int slot2) {
		final int windowId = Minecraft.thePlayer.inventoryContainer.windowId;
		Minecraft.playerController.windowClick(windowId, slot1, 0, 0, Minecraft.thePlayer);
		Minecraft.playerController.windowClick(windowId, slot2, 0, 0, Minecraft.thePlayer);
		Minecraft.playerController.windowClick(windowId, slot1, 0, 0, Minecraft.thePlayer);
	}

	public static void dropSlot(final int slot) {
		final int windowId = new GuiInventory(Minecraft.thePlayer).inventorySlots.windowId;
		Minecraft.playerController.windowClick(windowId, slot, 1, 4, Minecraft.thePlayer);
	}

	private static void updatePlayerInventory() {
		int i = 0;
		while (i < Minecraft.thePlayer.inventoryContainer.getInventory().size()) {
			Minecraft.playerController
					.sendSlotPacket((ItemStack) Minecraft.thePlayer.inventoryContainer.getInventory().get(i), i);
			++i;
		}
	}

	public static int findPickaxeInHotbar() {
		int slotID = 0;
		while (slotID < 9) {

			final ItemStack currentSlotItemStack = Minecraft.thePlayer.inventory.getStackInSlot(slotID);
			if (currentSlotItemStack != null && currentSlotItemStack.getItem() instanceof ItemPickaxe) {
				return slotID;
			}
			++slotID;
		}
		return -1;
	}

	public static int findBlockInHotbar() {
		int slotID = 0;
		while (slotID < 9) {

			final ItemStack currentSlotItemStack = Minecraft.thePlayer.inventory.getStackInSlot(slotID);
			if (currentSlotItemStack != null && currentSlotItemStack.getItem() instanceof ItemBlock) {
				return slotID;
			}
			++slotID;
		}
		return -1;
	}

	public static int findBestArmorSlot(final AutoArmor.ArmorType type) {
		int currentBestSlot = type.slotOffset + 36;
		int currentBestValue = 0;
		ItemStack currentSlotItemStack = Minecraft.thePlayer.inventory.getStackInSlot(currentBestSlot);
		if (currentSlotItemStack != null && currentSlotItemStack.getItem() instanceof ItemArmor) {
			currentBestValue = ((ItemArmor) currentSlotItemStack.getItem()).getArmorMaterial().getAutoArmor();
		}
		int slotID = 0;
		while (slotID < 36) {
			try {
				currentSlotItemStack = Minecraft.thePlayer.inventory.getStackInSlot(slotID);
				if (currentSlotItemStack != null && currentSlotItemStack.getItem() instanceof ItemArmor) {
					final boolean ingame = Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory")
							.getValBoolean();
					if (!(YesCheat.enabled && YesCheat.mode == Mode.Gomme && currentSlotItemStack.getMetadata() != 0)
							|| (ingame && Minecraft.currentScreen instanceof GuiInventory)
							|| (!ingame && (Minecraft.currentScreen == null
									|| Minecraft.currentScreen instanceof GuiInventory
									|| Minecraft.currentScreen instanceof ClickGUI))) {

						final ItemArmor armorItem = (ItemArmor) currentSlotItemStack.getItem();
						if (armorItem.armorType == type.armorType
								&& armorItem.getArmorMaterial().getAutoArmor() > currentBestValue) {
							currentBestSlot = slotID;
							currentBestValue = armorItem.getArmorMaterial().getAutoArmor();
						}
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
			++slotID;
		}
		return currentBestSlot;
	}
}
