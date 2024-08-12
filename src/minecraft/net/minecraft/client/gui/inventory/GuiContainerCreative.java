package net.minecraft.client.gui.inventory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiContainerCreative extends InventoryEffectRenderer {

	public static final int EaZy = 535;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/** The location of the creative inventory tabs texture */
	private static final ResourceLocation creativeInventoryTabs = new ResourceLocation(
			"textures/gui/container/creative_inventory/tabs.png");
	private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);

	/** Currently selected creative inventory tab index. */
	private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();

	/** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
	private float currentScroll;

	/** True if the scrollbar is being dragged */
	private boolean isScrolling;

	/**
	 * True if the left mouse button was held down last time drawScreen was
	 * called.
	 */
	private boolean wasClicking;
	private GuiTextField searchField;
	private List field_147063_B;
	private Slot field_147064_C;
	private boolean field_147057_D;
	private CreativeCrafting field_147059_E;
	// private static final String __OBFID = "http://https://fuckuskid00000752";

	public GuiContainerCreative(final EntityPlayer p_i1088_1_) {
		super(new GuiContainerCreative.ContainerCreative(p_i1088_1_));
		p_i1088_1_.openContainer = inventorySlots;
		allowUserInput = true;
		ySize = 136;
		xSize = 195;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		if (!Minecraft.playerController.isInCreativeMode()) {
			mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
		}

		func_175378_g();
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	@Override
	protected void handleMouseClick(final Slot slotIn, final int slotId, final int clickedButton, int clickType) {
		field_147057_D = true;
		final boolean var5 = clickType == 1;
		clickType = slotId == -999 && clickType == 0 ? 4 : clickType;
		ItemStack var7;
		InventoryPlayer var11;

		if (slotIn == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && clickType != 5) {
			var11 = Minecraft.thePlayer.inventory;

			if (var11.getItemStack() != null) {
				if (clickedButton == 0) {
					Minecraft.thePlayer.dropPlayerItemWithRandomChoice(var11.getItemStack(), true);
					Minecraft.playerController.sendPacketDropItem(var11.getItemStack());
					var11.setItemStack((ItemStack) null);
				}

				if (clickedButton == 1) {
					var7 = var11.getItemStack().splitStack(1);
					Minecraft.thePlayer.dropPlayerItemWithRandomChoice(var7, true);
					Minecraft.playerController.sendPacketDropItem(var7);

					if (var11.getItemStack().stackSize == 0) {
						var11.setItemStack((ItemStack) null);
					}
				}
			}
		} else {
			int var10;

			if (slotIn == field_147064_C && var5) {
				for (var10 = 0; var10 < Minecraft.thePlayer.inventoryContainer.getInventory().size(); ++var10) {
					Minecraft.playerController.sendSlotPacket((ItemStack) null, var10);
				}
			} else {
				ItemStack var6;

				if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
					if (slotIn == field_147064_C) {
						Minecraft.thePlayer.inventory.setItemStack((ItemStack) null);
					} else if (clickType == 4 && slotIn != null && slotIn.getHasStack()) {
						var6 = slotIn.decrStackSize(clickedButton == 0 ? 1 : slotIn.getStack().getMaxStackSize());
						Minecraft.thePlayer.dropPlayerItemWithRandomChoice(var6, true);
						Minecraft.playerController.sendPacketDropItem(var6);
					} else if (clickType == 4 && Minecraft.thePlayer.inventory.getItemStack() != null) {
						Minecraft.thePlayer.dropPlayerItemWithRandomChoice(Minecraft.thePlayer.inventory.getItemStack(),
								true);
						Minecraft.playerController.sendPacketDropItem(Minecraft.thePlayer.inventory.getItemStack());
						Minecraft.thePlayer.inventory.setItemStack((ItemStack) null);
					} else {
						Minecraft.thePlayer.inventoryContainer.slotClick(
								slotIn == null ? slotId
										: ((GuiContainerCreative.CreativeSlot) slotIn).field_148332_b.slotNumber,
								clickedButton, clickType, Minecraft.thePlayer);
						Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
					}
				} else if (clickType != 5 && slotIn.inventory == field_147060_v) {
					var11 = Minecraft.thePlayer.inventory;
					var7 = var11.getItemStack();
					final ItemStack var8 = slotIn.getStack();
					ItemStack var9;

					if (clickType == 2) {
						if (var8 != null && clickedButton >= 0 && clickedButton < 9) {
							var9 = var8.copy();
							var9.stackSize = var9.getMaxStackSize();
							Minecraft.thePlayer.inventory.setInventorySlotContents(clickedButton, var9);
							Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
						}

						return;
					}

					if (clickType == 3) {
						if (var11.getItemStack() == null && slotIn.getHasStack()) {
							var9 = slotIn.getStack().copy();
							var9.stackSize = var9.getMaxStackSize();
							var11.setItemStack(var9);
						}

						return;
					}

					if (clickType == 4) {
						if (var8 != null) {
							var9 = var8.copy();
							var9.stackSize = clickedButton == 0 ? 1 : var9.getMaxStackSize();
							Minecraft.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
							Minecraft.playerController.sendPacketDropItem(var9);
						}

						return;
					}

					if (var7 != null && var8 != null && var7.isItemEqual(var8)) {
						if (clickedButton == 0) {
							if (var5) {
								var7.stackSize = var7.getMaxStackSize();
							} else if (var7.stackSize < var7.getMaxStackSize()) {
								++var7.stackSize;
							}
						} else if (var7.stackSize <= 1) {
							var11.setItemStack((ItemStack) null);
						} else {
							--var7.stackSize;
						}
					} else if (var8 != null && var7 == null) {
						var11.setItemStack(ItemStack.copyItemStack(var8));
						var7 = var11.getItemStack();

						if (var5) {
							var7.stackSize = var7.getMaxStackSize();
						}
					} else {
						var11.setItemStack((ItemStack) null);
					}
				} else {
					inventorySlots.slotClick(slotIn == null ? slotId : slotIn.slotNumber, clickedButton, clickType,
							Minecraft.thePlayer);

					if (Container.getDragEvent(clickedButton) == 2) {
						for (var10 = 0; var10 < 9; ++var10) {
							Minecraft.playerController.sendSlotPacket(inventorySlots.getSlot(45 + var10).getStack(),
									36 + var10);
						}
					} else if (slotIn != null) {
						var6 = inventorySlots.getSlot(slotIn.slotNumber).getStack();
						Minecraft.playerController.sendSlotPacket(var6,
								slotIn.slotNumber - inventorySlots.inventorySlots.size() + 9 + 36);
					}
				}
			}
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		if (Minecraft.playerController.isInCreativeMode()) {
			super.initGui();
			buttonList.clear();
			Keyboard.enableRepeatEvents(true);
			searchField = new GuiTextField(0, fontRendererObj, guiLeft + 82, guiTop + 6, 89,
					fontRendererObj.FONT_HEIGHT);
			searchField.setMaxStringLength(15);
			searchField.setEnableBackgroundDrawing(false);
			searchField.setVisible(false);
			searchField.setTextColor(16777215);
			final int var1 = selectedTabIndex;
			selectedTabIndex = -1;
			setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
			field_147059_E = new CreativeCrafting(mc);
			Minecraft.thePlayer.inventoryContainer.onCraftGuiOpened(field_147059_E);
		} else {
			mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
		}
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		if (Minecraft.thePlayer != null && Minecraft.thePlayer.inventory != null) {
			Minecraft.thePlayer.inventoryContainer.removeCraftingFromCrafters(field_147059_E);
		}

		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
			if (GameSettings.isKeyDown(Minecraft.gameSettings.keyBindChat)) {
				setCurrentCreativeTab(CreativeTabs.tabAllSearch);
			} else {
				super.keyTyped(typedChar, keyCode);
			}
		} else {
			if (field_147057_D) {
				field_147057_D = false;
				searchField.setText("");
			}

			if (!checkHotbarKeys(keyCode)) {
				if (searchField.textboxKeyTyped(typedChar, keyCode)) {
					updateCreativeSearch();
				} else {
					super.keyTyped(typedChar, keyCode);
				}
			}
		}
	}

	private void updateCreativeSearch() {
		final GuiContainerCreative.ContainerCreative var1 = (GuiContainerCreative.ContainerCreative) inventorySlots;
		var1.itemList.clear();
		Iterator var2 = Item.itemRegistry.iterator();

		while (var2.hasNext()) {
			final Item var3 = (Item) var2.next();

			if (var3 != null && var3.getCreativeTab() != null) {
				var3.getSubItems(var3, (CreativeTabs) null, var1.itemList);
			}
		}

		final Enchantment[] var8 = Enchantment.enchantmentsList;
		final int var9 = var8.length;

		for (int var4 = 0; var4 < var9; ++var4) {
			final Enchantment var5 = var8[var4];

			if (var5 != null && var5.type != null) {
				Items.enchanted_book.func_92113_a(var5, var1.itemList);
			}
		}

		var2 = var1.itemList.iterator();
		final String var10 = searchField.getText().toLowerCase();

		while (var2.hasNext()) {
			final ItemStack var11 = (ItemStack) var2.next();
			boolean var12 = false;
			final Iterator var6 = var11.getTooltip(Minecraft.thePlayer, Minecraft.gameSettings.advancedItemTooltips)
					.iterator();

			while (true) {
				if (var6.hasNext()) {
					final String var7 = (String) var6.next();

					if (!EnumChatFormatting.getTextWithoutFormattingCodes(var7).toLowerCase().contains(var10)) {
						continue;
					}

					var12 = true;
				}

				if (!var12) {
					var2.remove();
				}

				break;
			}
		}

		currentScroll = 0.0F;
		var1.scrollTo(0.0F);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		final CreativeTabs var3 = CreativeTabs.creativeTabArray[selectedTabIndex];

		if (var3.drawInForegroundOfTab()) {
			GlStateManager.disableBlend();
			fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		if (mouseButton == 0) {
			final int var4 = mouseX - guiLeft;
			final int var5 = mouseY - guiTop;
			final CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
			final int var7 = var6.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final CreativeTabs var9 = var6[var8];

				if (func_147049_a(var9, var4, var5)) {
					return;
				}
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		if (state == 0) {
			final int var4 = mouseX - guiLeft;
			final int var5 = mouseY - guiTop;
			final CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
			final int var7 = var6.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final CreativeTabs var9 = var6[var8];

				if (func_147049_a(var9, var4, var5)) {
					setCurrentCreativeTab(var9);
					return;
				}
			}
		}

		super.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * returns (if you are not on the inventoryTab) and (the flag isn't set) and
	 * (you have more than 1 page of items)
	 */
	private boolean needsScrollBars() {
		return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex()
				&& CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory()
				&& ((GuiContainerCreative.ContainerCreative) inventorySlots).func_148328_e();
	}

	private void setCurrentCreativeTab(final CreativeTabs p_147050_1_) {
		final int var2 = selectedTabIndex;
		selectedTabIndex = p_147050_1_.getTabIndex();
		final GuiContainerCreative.ContainerCreative var3 = (GuiContainerCreative.ContainerCreative) inventorySlots;
		dragSplittingSlots.clear();
		var3.itemList.clear();
		p_147050_1_.displayAllReleventItems(var3.itemList);

		if (p_147050_1_ == CreativeTabs.tabInventory) {
			final Container var4 = Minecraft.thePlayer.inventoryContainer;

			if (field_147063_B == null) {
				field_147063_B = var3.inventorySlots;
			}

			var3.inventorySlots = Lists.newArrayList();

			for (int var5 = 0; var5 < var4.inventorySlots.size(); ++var5) {
				final GuiContainerCreative.CreativeSlot var6 = new GuiContainerCreative.CreativeSlot(
						(Slot) var4.inventorySlots.get(var5), var5);
				var3.inventorySlots.add(var6);
				int var7;
				int var8;
				int var9;

				if (var5 >= 5 && var5 < 9) {
					var7 = var5 - 5;
					var8 = var7 / 2;
					var9 = var7 % 2;
					var6.xDisplayPosition = 9 + var8 * 54;
					var6.yDisplayPosition = 6 + var9 * 27;
				} else if (var5 >= 0 && var5 < 5) {
					var6.yDisplayPosition = -2000;
					var6.xDisplayPosition = -2000;
				} else if (var5 < var4.inventorySlots.size()) {
					var7 = var5 - 9;
					var8 = var7 % 9;
					var9 = var7 / 9;
					var6.xDisplayPosition = 9 + var8 * 18;

					if (var5 >= 36) {
						var6.yDisplayPosition = 112;
					} else {
						var6.yDisplayPosition = 54 + var9 * 18;
					}
				}
			}

			field_147064_C = new Slot(field_147060_v, 0, 173, 112);
			var3.inventorySlots.add(field_147064_C);
		} else if (var2 == CreativeTabs.tabInventory.getTabIndex()) {
			var3.inventorySlots = field_147063_B;
			field_147063_B = null;
		}

		if (searchField != null) {
			if (p_147050_1_ == CreativeTabs.tabAllSearch) {
				searchField.setVisible(true);
				searchField.setCanLoseFocus(false);
				searchField.setFocused(true);
				searchField.setText("");
				updateCreativeSearch();
			} else {
				searchField.setVisible(false);
				searchField.setCanLoseFocus(true);
				searchField.setFocused(false);
			}
		}

		currentScroll = 0.0F;
		var3.scrollTo(0.0F);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int var1 = Mouse.getEventDWheel();

		if (var1 != 0 && needsScrollBars()) {
			final int var2 = ((GuiContainerCreative.ContainerCreative) inventorySlots).itemList.size() / 9 - 5 + 1;

			if (var1 > 0) {
				var1 = 1;
			}

			if (var1 < 0) {
				var1 = -1;
			}

			currentScroll = (float) (currentScroll - (double) var1 / (double) var2);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0F, 1.0F);
			((GuiContainerCreative.ContainerCreative) inventorySlots).scrollTo(currentScroll);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		final boolean var4 = Mouse.isButtonDown(0);
		final int var5 = guiLeft;
		final int var6 = guiTop;
		final int var7 = var5 + 175;
		final int var8 = var6 + 18;
		final int var9 = var7 + 14;
		final int var10 = var8 + 112;

		if (!wasClicking && var4 && mouseX >= var7 && mouseY >= var8 && mouseX < var9 && mouseY < var10) {
			isScrolling = needsScrollBars();
		}

		if (!var4) {
			isScrolling = false;
		}

		wasClicking = var4;

		if (isScrolling) {
			currentScroll = (mouseY - var8 - 7.5F) / (var10 - var8 - 15.0F);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0F, 1.0F);
			((GuiContainerCreative.ContainerCreative) inventorySlots).scrollTo(currentScroll);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
		final CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
		final int var12 = var11.length;

		for (int var13 = 0; var13 < var12; ++var13) {
			final CreativeTabs var14 = var11[var13];

			if (renderCreativeInventoryHoveringText(var14, mouseX, mouseY)) {
				break;
			}
		}

		if (field_147064_C != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && isPointInRegion(
				field_147064_C.xDisplayPosition, field_147064_C.yDisplayPosition, 16, 16, mouseX, mouseY)) {
			drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), mouseX, mouseY);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
	}

	@Override
	protected void renderToolTip(final ItemStack itemIn, final int x, final int y) {
		if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
			final List var4 = itemIn.getTooltip(Minecraft.thePlayer, Minecraft.gameSettings.advancedItemTooltips);
			CreativeTabs var5 = itemIn.getItem().getCreativeTab();

			if (var5 == null && itemIn.getItem() == Items.enchanted_book) {
				final Map var6 = EnchantmentHelper.getEnchantments(itemIn);

				if (var6.size() == 1) {
					final Enchantment var7 = Enchantment.byID(((Integer) var6.keySet().iterator().next()));
					final CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
					final int var9 = var8.length;

					for (int var10 = 0; var10 < var9; ++var10) {
						final CreativeTabs var11 = var8[var10];

						if (var11.hasRelevantEnchantmentType(var7.type)) {
							var5 = var11;
							break;
						}
					}
				}
			}

			if (var5 != null) {
				var4.add(1, "" + EnumChatFormatting.BOLD + EnumChatFormatting.BLUE
						+ I18n.format(var5.getTranslatedTabLabel(), new Object[0]));
			}

			for (int var12 = 0; var12 < var4.size(); ++var12) {
				if (var12 == 0) {
					var4.set(var12, itemIn.getRarity().rarityColor + (String) var4.get(var12));
				} else {
					var4.set(var12, EnumChatFormatting.GRAY + (String) var4.get(var12));
				}
			}

			drawHoveringText(var4, x, y);
		} else {
			super.renderToolTip(itemIn, x, y);
		}
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		final CreativeTabs var4 = CreativeTabs.creativeTabArray[selectedTabIndex];
		final CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
		int var6 = var5.length;
		int var7;

		for (var7 = 0; var7 < var6; ++var7) {
			final CreativeTabs var8 = var5[var7];
			Minecraft.getTextureManager().bindTexture(creativeInventoryTabs);

			if (var8.getTabIndex() != selectedTabIndex) {
				func_147051_a(var8);
			}
		}

		Minecraft.getTextureManager().bindTexture(
				new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		searchField.drawTextBox();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		final int var9 = guiLeft + 175;
		var6 = guiTop + 18;
		var7 = var6 + 112;
		Minecraft.getTextureManager().bindTexture(creativeInventoryTabs);

		if (var4.shouldHidePlayerInventory()) {
			drawTexturedModalRect(var9, var6 + (int) ((var7 - var6 - 17) * currentScroll),
					232 + (needsScrollBars() ? 0 : 12), 0, 12, 15);
		}

		func_147051_a(var4);

		if (var4 == CreativeTabs.tabInventory) {
			GuiInventory.drawEntityOnScreen(guiLeft + 43, guiTop + 45, 20, guiLeft + 43 - mouseX,
					guiTop + 45 - 30 - mouseY, Minecraft.thePlayer);
		}
	}

	protected boolean func_147049_a(final CreativeTabs p_147049_1_, final int p_147049_2_, final int p_147049_3_) {
		final int var4 = p_147049_1_.getTabColumn();
		int var5 = 28 * var4;
		final byte var6 = 0;

		if (var4 == 5) {
			var5 = xSize - 28 + 2;
		} else if (var4 > 0) {
			var5 += var4;
		}

		int var7;

		if (p_147049_1_.isTabInFirstRow()) {
			var7 = var6 - 32;
		} else {
			var7 = var6 + ySize;
		}

		return p_147049_2_ >= var5 && p_147049_2_ <= var5 + 28 && p_147049_3_ >= var7 && p_147049_3_ <= var7 + 32;
	}

	/**
	 * Renders the creative inventory hovering text if mouse is over it. Returns
	 * true if did render or false otherwise. Params: current creative tab to be
	 * checked, current mouse x position, current mouse y position.
	 */
	protected boolean renderCreativeInventoryHoveringText(final CreativeTabs p_147052_1_, final int p_147052_2_,
			final int p_147052_3_) {
		final int var4 = p_147052_1_.getTabColumn();
		int var5 = 28 * var4;
		final byte var6 = 0;

		if (var4 == 5) {
			var5 = xSize - 28 + 2;
		} else if (var4 > 0) {
			var5 += var4;
		}

		int var7;

		if (p_147052_1_.isTabInFirstRow()) {
			var7 = var6 - 32;
		} else {
			var7 = var6 + ySize;
		}

		if (isPointInRegion(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_)) {
			drawCreativeTabHoveringText(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_,
					p_147052_3_);
			return true;
		} else {
			return false;
		}
	}

	protected void func_147051_a(final CreativeTabs p_147051_1_) {
		final boolean var2 = p_147051_1_.getTabIndex() == selectedTabIndex;
		final boolean var3 = p_147051_1_.isTabInFirstRow();
		final int var4 = p_147051_1_.getTabColumn();
		final int var5 = var4 * 28;
		int var6 = 0;
		int var7 = guiLeft + 28 * var4;
		int var8 = guiTop;
		final byte var9 = 32;

		if (var2) {
			var6 += 32;
		}

		if (var4 == 5) {
			var7 = guiLeft + xSize - 28;
		} else if (var4 > 0) {
			var7 += var4;
		}

		if (var3) {
			var8 -= 28;
		} else {
			var6 += 64;
			var8 += ySize - 4;
		}

		GlStateManager.disableLighting();
		drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
		zLevel = 100.0F;
		itemRender.zLevel = 100.0F;
		var7 += 6;
		var8 += 8 + (var3 ? 1 : -1);
		GlStateManager.enableLighting();
		GlStateManager.enableRescaleNormal();
		final ItemStack var10 = p_147051_1_.getIconItemStack();
		itemRender.renderItemAndEffectIntoGUI(var10, var7, var8);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, var10, var7, var8);
		GlStateManager.disableLighting();
		itemRender.zLevel = 0.0F;
		zLevel = 0.0F;
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 0) {
			mc.displayGuiScreen(new GuiAchievements(this, Minecraft.thePlayer.getStatFileWriter()));
		}

		if (button.id == 1) {
			mc.displayGuiScreen(new GuiStats(this, Minecraft.thePlayer.getStatFileWriter()));
		}
	}

	public int func_147056_g() {
		return selectedTabIndex;
	}

	static class ContainerCreative extends Container {
		public List itemList = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000753";

		public ContainerCreative(final EntityPlayer p_i1086_1_) {
			final InventoryPlayer var2 = p_i1086_1_.inventory;
			int var3;

			for (var3 = 0; var3 < 5; ++var3) {
				for (int var4 = 0; var4 < 9; ++var4) {
					addSlotToContainer(new Slot(GuiContainerCreative.field_147060_v, var3 * 9 + var4, 9 + var4 * 18,
							18 + var3 * 18));
				}
			}

			for (var3 = 0; var3 < 9; ++var3) {
				addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
			}

			scrollTo(0.0F);
		}

		@Override
		public boolean canInteractWith(final EntityPlayer playerIn) {
			return true;
		}

		public void scrollTo(final float p_148329_1_) {
			final int var2 = (itemList.size() + 8) / 9 - 5;
			int var3 = (int) (p_148329_1_ * var2 + 0.5D);

			if (var3 < 0) {
				var3 = 0;
			}

			for (int var4 = 0; var4 < 5; ++var4) {
				for (int var5 = 0; var5 < 9; ++var5) {
					final int var6 = var5 + (var4 + var3) * 9;

					if (var6 >= 0 && var6 < itemList.size()) {
						GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9,
								(ItemStack) itemList.get(var6));
					} else {
						GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, (ItemStack) null);
					}
				}
			}
		}

		public boolean func_148328_e() {
			return itemList.size() > 45;
		}

		@Override
		protected void retrySlotClick(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_,
				final EntityPlayer p_75133_4_) {}

		@Override
		public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
			if (index >= inventorySlots.size() - 9 && index < inventorySlots.size()) {
				final Slot var3 = (Slot) inventorySlots.get(index);

				if (var3 != null && var3.getHasStack()) {
					var3.putStack((ItemStack) null);
				}
			}

			return null;
		}

		@Override
		public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
			return p_94530_2_.yDisplayPosition > 90;
		}

		@Override
		public boolean canDragIntoSlot(final Slot p_94531_1_) {
			return p_94531_1_.inventory instanceof InventoryPlayer
					|| p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162;
		}
	}

	class CreativeSlot extends Slot {
		private final Slot field_148332_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000754";

		public CreativeSlot(final Slot p_i46313_2_, final int p_i46313_3_) {
			super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
			field_148332_b = p_i46313_2_;
		}

		@Override
		public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
			field_148332_b.onPickupFromSlot(playerIn, stack);
		}

		@Override
		public boolean isItemValid(final ItemStack stack) {
			return field_148332_b.isItemValid(stack);
		}

		@Override
		public ItemStack getStack() {
			return field_148332_b.getStack();
		}

		@Override
		public boolean getHasStack() {
			return field_148332_b.getHasStack();
		}

		@Override
		public void putStack(final ItemStack p_75215_1_) {
			field_148332_b.putStack(p_75215_1_);
		}

		@Override
		public void onSlotChanged() {
			field_148332_b.onSlotChanged();
		}

		@Override
		public int getSlotStackLimit() {
			return field_148332_b.getSlotStackLimit();
		}

		@Override
		public int func_178170_b(final ItemStack p_178170_1_) {
			return field_148332_b.func_178170_b(p_178170_1_);
		}

		@Override
		public String func_178171_c() {
			return field_148332_b.func_178171_c();
		}

		@Override
		public ItemStack decrStackSize(final int p_75209_1_) {
			return field_148332_b.decrStackSize(p_75209_1_);
		}

		@Override
		public boolean isHere(final IInventory p_75217_1_, final int p_75217_2_) {
			return field_148332_b.isHere(p_75217_1_, p_75217_2_);
		}
	}
}
