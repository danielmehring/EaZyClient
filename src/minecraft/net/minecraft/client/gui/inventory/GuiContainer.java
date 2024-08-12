package net.minecraft.client.gui.inventory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Sets;

import me.EaZy.client.Configs;
import me.EaZy.client.particle.ParticleSystem;

public abstract class GuiContainer extends GuiScreen {

	public static final int EaZy = 534;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/** The location of the inventory background texture */
	protected static final ResourceLocation inventoryBackground = new ResourceLocation(
			"textures/gui/container/inventory.png");

	/** The X size of the inventory window in pixels. */
	protected int xSize = 176;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 166;

	/** A list of the players inventory slots */
	public Container inventorySlots;

	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;

	/** holds the slot currently hovered */
	private Slot theSlot;

	/** Used when touchscreen is enabled. */
	private Slot clickedSlot;

	/** Used when touchscreen is enabled. */
	private boolean isRightMouseClick;

	/** Used when touchscreen is enabled */
	private ItemStack draggedStack;
	private int touchUpX;
	private int touchUpY;
	private Slot returningStackDestSlot;
	private long returningStackTime;

	/** Used when touchscreen is enabled */
	private ItemStack returningStack;
	private Slot currentDragTargetSlot;
	private long dragItemDropDelay;
	protected final Set dragSplittingSlots = Sets.newHashSet();
	protected boolean dragSplitting;
	private int dragSplittingLimit;
	private int dragSplittingButton;
	private boolean ignoreMouseUp;
	private int dragSplittingRemnant;
	private long lastClickTime;
	private Slot lastClickSlot;
	private int lastClickButton;
	private boolean doubleClick;
	private ItemStack shiftClickedSlot;
	// private static final String __OBFID = "http://https://fuckuskid00000737";

	public ParticleSystem ps = new ParticleSystem(100, true);

	public GuiContainer(final Container p_i1072_1_) {
		inventorySlots = p_i1072_1_;
		ignoreMouseUp = true;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		Minecraft.thePlayer.openContainer = inventorySlots;
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		if (Configs.invParticles)
			ps.render(mouseX, mouseY);
		final int var4 = guiLeft;
		final int var5 = guiTop;
		drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		super.drawScreen(mouseX, mouseY, partialTicks);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(var4, var5, 0.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		theSlot = null;
		final short var6 = 240;
		final short var7 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int var11;

		for (int var8 = 0; var8 < inventorySlots.inventorySlots.size(); ++var8) {
			final Slot var9 = (Slot) inventorySlots.inventorySlots.get(var8);
			drawSlot(var9);

			if (isMouseOverSlot(var9, mouseX, mouseY) && var9.canBeHovered()) {
				theSlot = var9;
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				final int var10 = var9.xDisplayPosition;
				var11 = var9.yDisplayPosition;
				GlStateManager.colorMask(true, true, true, false);
				drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
				GlStateManager.colorMask(true, true, true, true);
				GlStateManager.enableLighting();
				GlStateManager.enableDepth();
			}
		}

		RenderHelper.disableStandardItemLighting();
		drawGuiContainerForegroundLayer(mouseX, mouseY);
		RenderHelper.enableGUIStandardItemLighting();
		final InventoryPlayer var15 = Minecraft.thePlayer.inventory;
		ItemStack var16 = draggedStack == null ? var15.getItemStack() : draggedStack;

		if (var16 != null) {
			final byte var17 = 8;
			var11 = draggedStack == null ? 8 : 16;
			String var12 = null;

			if (draggedStack != null && isRightMouseClick) {
				var16 = var16.copy();
				var16.stackSize = MathHelper.ceiling_float_int(var16.stackSize / 2.0F);
			} else if (dragSplitting && dragSplittingSlots.size() > 1) {
				var16 = var16.copy();
				var16.stackSize = dragSplittingRemnant;

				if (var16.stackSize == 0) {
					var12 = "" + EnumChatFormatting.YELLOW + "0";
				}
			}

			drawItemStack(var16, mouseX - var4 - var17, mouseY - var5 - var11, var12);
		}

		if (returningStack != null) {
			float var18 = (Minecraft.getSystemTime() - returningStackTime) / 100.0F;

			if (var18 >= 1.0F) {
				var18 = 1.0F;
				returningStack = null;
			}

			var11 = returningStackDestSlot.xDisplayPosition - touchUpX;
			final int var20 = returningStackDestSlot.yDisplayPosition - touchUpY;
			final int var13 = touchUpX + (int) (var11 * var18);
			final int var14 = touchUpY + (int) (var20 * var18);
			drawItemStack(returningStack, var13, var14, (String) null);
		}

		GlStateManager.popMatrix();

		if (var15.getItemStack() == null && theSlot != null && theSlot.getHasStack()) {
			final ItemStack var19 = theSlot.getStack();
			renderToolTip(var19, mouseX, mouseY);
		}

		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
	}

	/**
	 * Render an ItemStack. Args : stack, x, y, format
	 */
	private void drawItemStack(final ItemStack stack, final int x, final int y, final String altText) {
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, stack, x, y - (draggedStack == null ? 0 : 8), altText);
		zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

	private void drawSlot(final Slot slotIn) {
		final int var2 = slotIn.xDisplayPosition;
		final int var3 = slotIn.yDisplayPosition;
		ItemStack var4 = slotIn.getStack();
		boolean var5 = false;
		boolean var6 = slotIn == clickedSlot && draggedStack != null && !isRightMouseClick;
		final ItemStack var7 = Minecraft.thePlayer.inventory.getItemStack();
		String var8 = null;

		if (slotIn == clickedSlot && draggedStack != null && isRightMouseClick && var4 != null) {
			var4 = var4.copy();
			var4.stackSize /= 2;
		} else if (dragSplitting && dragSplittingSlots.contains(slotIn) && var7 != null) {
			if (dragSplittingSlots.size() == 1) {
				return;
			}

			if (Container.canAddItemToSlot(slotIn, var7, true) && inventorySlots.canDragIntoSlot(slotIn)) {
				var4 = var7.copy();
				var5 = true;
				Container.computeStackSize(dragSplittingSlots, dragSplittingLimit, var4,
						slotIn.getStack() == null ? 0 : slotIn.getStack().stackSize);

				if (var4.stackSize > var4.getMaxStackSize()) {
					var8 = EnumChatFormatting.YELLOW + "" + var4.getMaxStackSize();
					var4.stackSize = var4.getMaxStackSize();
				}

				if (var4.stackSize > slotIn.func_178170_b(var4)) {
					var8 = EnumChatFormatting.YELLOW + "" + slotIn.func_178170_b(var4);
					var4.stackSize = slotIn.func_178170_b(var4);
				}
			} else {
				dragSplittingSlots.remove(slotIn);
				updateDragSplitting();
			}
		}

		zLevel = 100.0F;
		itemRender.zLevel = 100.0F;

		if (var4 == null) {
			final String var9 = slotIn.func_178171_c();

			if (var9 != null) {
				final TextureAtlasSprite var10 = mc.getTextureMapBlocks().getAtlasSprite(var9);
				GlStateManager.disableLighting();
				Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				func_175175_a(var2, var3, var10, 16, 16);
				GlStateManager.enableLighting();
				var6 = true;
			}
		}

		if (!var6) {
			if (var5) {
				drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
			}

			GlStateManager.enableDepth();
			itemRender.renderItemAndEffectIntoGUI(var4, var2, var3);
			itemRender.renderItemOverlayIntoGUI(fontRendererObj, var4, var2, var3, var8);
		}

		itemRender.zLevel = 0.0F;
		zLevel = 0.0F;
	}

	private void updateDragSplitting() {
		final ItemStack var1 = Minecraft.thePlayer.inventory.getItemStack();

		if (var1 != null && dragSplitting) {
			dragSplittingRemnant = var1.stackSize;
			ItemStack var4;
			int var5;

			for (final Iterator var2 = dragSplittingSlots.iterator(); var2
					.hasNext(); dragSplittingRemnant -= var4.stackSize - var5) {
				final Slot var3 = (Slot) var2.next();
				var4 = var1.copy();
				var5 = var3.getStack() == null ? 0 : var3.getStack().stackSize;
				Container.computeStackSize(dragSplittingSlots, dragSplittingLimit, var4, var5);

				if (var4.stackSize > var4.getMaxStackSize()) {
					var4.stackSize = var4.getMaxStackSize();
				}

				if (var4.stackSize > var3.func_178170_b(var4)) {
					var4.stackSize = var3.func_178170_b(var4);
				}
			}
		}
	}

	/**
	 * Returns the slot at the given coordinates or null if there is none.
	 */
	private Slot getSlotAtPosition(final int x, final int y) {
		for (int var3 = 0; var3 < inventorySlots.inventorySlots.size(); ++var3) {
			final Slot var4 = (Slot) inventorySlots.inventorySlots.get(var3);

			if (isMouseOverSlot(var4, x, y)) {
				return var4;
			}
		}

		return null;
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		final boolean var4 = mouseButton == Minecraft.gameSettings.keyBindPickBlock.getKeyCode() + 100;
		final Slot var5 = getSlotAtPosition(mouseX, mouseY);
		final long var6 = Minecraft.getSystemTime();
		doubleClick = lastClickSlot == var5 && var6 - lastClickTime < 250L && lastClickButton == mouseButton;
		ignoreMouseUp = false;

		if (mouseButton == 0 || mouseButton == 1 || var4) {
			final int var8 = guiLeft;
			final int var9 = guiTop;
			final boolean var10 = mouseX < var8 || mouseY < var9 || mouseX >= var8 + xSize || mouseY >= var9 + ySize;
			int var11 = -1;

			if (var5 != null) {
				var11 = var5.slotNumber;
			}

			if (var10) {
				var11 = -999;
			}

			if (Minecraft.gameSettings.touchscreen && var10 && Minecraft.thePlayer.inventory.getItemStack() == null) {
				mc.displayGuiScreen((GuiScreen) null);
				return;
			}

			if (var11 != -1) {
				if (Minecraft.gameSettings.touchscreen) {
					if (var5 != null && var5.getHasStack()) {
						clickedSlot = var5;
						draggedStack = null;
						isRightMouseClick = mouseButton == 1;
					} else {
						clickedSlot = null;
					}
				} else if (!dragSplitting) {
					if (Minecraft.thePlayer.inventory.getItemStack() == null) {
						if (mouseButton == Minecraft.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
							handleMouseClick(var5, var11, mouseButton, 3);
						} else {
							final boolean var12 = var11 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
							byte var13 = 0;

							if (var12) {
								shiftClickedSlot = var5 != null && var5.getHasStack() ? var5.getStack() : null;
								var13 = 1;
							} else if (var11 == -999) {
								var13 = 4;
							}

							handleMouseClick(var5, var11, mouseButton, var13);
						}

						ignoreMouseUp = true;
					} else {
						dragSplitting = true;
						dragSplittingButton = mouseButton;
						dragSplittingSlots.clear();

						if (mouseButton == 0) {
							dragSplittingLimit = 0;
						} else if (mouseButton == 1) {
							dragSplittingLimit = 1;
						} else if (mouseButton == Minecraft.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
							dragSplittingLimit = 2;
						}
					}
				}
			}
		}

		lastClickSlot = var5;
		lastClickTime = var6;
		lastClickButton = mouseButton;
	}

	/**
	 * Called when a mouse button is pressed and the mouse is moved around.
	 * Parameters are : mouseX, mouseY, lastButtonClicked & timeSinceMouseClick.
	 */
	@Override
	protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton,
			final long timeSinceLastClick) {
		final Slot var6 = getSlotAtPosition(mouseX, mouseY);
		final ItemStack var7 = Minecraft.thePlayer.inventory.getItemStack();

		if (clickedSlot != null && Minecraft.gameSettings.touchscreen) {
			if (clickedMouseButton == 0 || clickedMouseButton == 1) {
				if (draggedStack == null) {
					if (var6 != clickedSlot) {
						draggedStack = clickedSlot.getStack().copy();
					}
				} else if (draggedStack.stackSize > 1 && var6 != null
						&& Container.canAddItemToSlot(var6, draggedStack, false)) {
					final long var8 = Minecraft.getSystemTime();

					if (currentDragTargetSlot == var6) {
						if (var8 - dragItemDropDelay > 500L) {
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
							handleMouseClick(var6, var6.slotNumber, 1, 0);
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, 0, 0);
							dragItemDropDelay = var8 + 750L;
							--draggedStack.stackSize;
						}
					} else {
						currentDragTargetSlot = var6;
						dragItemDropDelay = var8;
					}
				}
			}
		} else if (dragSplitting && var6 != null && var7 != null && var7.stackSize > dragSplittingSlots.size()
				&& Container.canAddItemToSlot(var6, var7, true) && var6.isItemValid(var7)
				&& inventorySlots.canDragIntoSlot(var6)) {
			dragSplittingSlots.add(var6);
			updateDragSplitting();
		}
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		final Slot var4 = getSlotAtPosition(mouseX, mouseY);
		final int var5 = guiLeft;
		final int var6 = guiTop;
		final boolean var7 = mouseX < var5 || mouseY < var6 || mouseX >= var5 + xSize || mouseY >= var6 + ySize;
		int var8 = -1;

		if (var4 != null) {
			var8 = var4.slotNumber;
		}

		if (var7) {
			var8 = -999;
		}

		Slot var10;
		Iterator var11;

		if (doubleClick && var4 != null && state == 0 && inventorySlots.func_94530_a((ItemStack) null, var4)) {
			if (isShiftKeyDown()) {
				if (var4 != null && var4.inventory != null && shiftClickedSlot != null) {
					var11 = inventorySlots.inventorySlots.iterator();

					while (var11.hasNext()) {
						var10 = (Slot) var11.next();

						if (var10 != null && var10.canTakeStack(Minecraft.thePlayer) && var10.getHasStack()
								&& var10.inventory == var4.inventory
								&& Container.canAddItemToSlot(var10, shiftClickedSlot, true)) {
							handleMouseClick(var10, var10.slotNumber, state, 1);
						}
					}
				}
			} else {
				handleMouseClick(var4, var8, state, 6);
			}

			doubleClick = false;
			lastClickTime = 0L;
		} else {
			if (dragSplitting && dragSplittingButton != state) {
				dragSplitting = false;
				dragSplittingSlots.clear();
				ignoreMouseUp = true;
				return;
			}

			if (ignoreMouseUp) {
				ignoreMouseUp = false;
				return;
			}

			boolean var9;

			if (clickedSlot != null && Minecraft.gameSettings.touchscreen) {
				if (state == 0 || state == 1) {
					if (draggedStack == null && var4 != clickedSlot) {
						draggedStack = clickedSlot.getStack();
					}

					var9 = Container.canAddItemToSlot(var4, draggedStack, false);

					if (var8 != -1 && draggedStack != null && var9) {
						handleMouseClick(clickedSlot, clickedSlot.slotNumber, state, 0);
						handleMouseClick(var4, var8, 0, 0);

						if (Minecraft.thePlayer.inventory.getItemStack() != null) {
							handleMouseClick(clickedSlot, clickedSlot.slotNumber, state, 0);
							touchUpX = mouseX - var5;
							touchUpY = mouseY - var6;
							returningStackDestSlot = clickedSlot;
							returningStack = draggedStack;
							returningStackTime = Minecraft.getSystemTime();
						} else {
							returningStack = null;
						}
					} else if (draggedStack != null) {
						touchUpX = mouseX - var5;
						touchUpY = mouseY - var6;
						returningStackDestSlot = clickedSlot;
						returningStack = draggedStack;
						returningStackTime = Minecraft.getSystemTime();
					}

					draggedStack = null;
					clickedSlot = null;
				}
			} else if (dragSplitting && !dragSplittingSlots.isEmpty()) {
				handleMouseClick((Slot) null, -999, Container.func_94534_d(0, dragSplittingLimit), 5);
				var11 = dragSplittingSlots.iterator();

				while (var11.hasNext()) {
					var10 = (Slot) var11.next();
					handleMouseClick(var10, var10.slotNumber, Container.func_94534_d(1, dragSplittingLimit), 5);
				}

				handleMouseClick((Slot) null, -999, Container.func_94534_d(2, dragSplittingLimit), 5);
			} else if (Minecraft.thePlayer.inventory.getItemStack() != null) {
				if (state == Minecraft.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
					handleMouseClick(var4, var8, state, 3);
				} else {
					var9 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));

					if (var9) {
						shiftClickedSlot = var4 != null && var4.getHasStack() ? var4.getStack() : null;
					}

					handleMouseClick(var4, var8, state, var9 ? 1 : 0);
				}
			}
		}

		if (Minecraft.thePlayer.inventory.getItemStack() == null) {
			lastClickTime = 0L;
		}

		dragSplitting = false;
	}

	/**
	 * Returns if the passed mouse position is over the specified slot. Args :
	 * slot, mouseX, mouseY
	 */
	private boolean isMouseOverSlot(final Slot slotIn, final int mouseX, final int mouseY) {
		return isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
	}

	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args :
	 * rectX, rectY, rectWidth, rectHeight, pointX, pointY
	 */
	protected boolean isPointInRegion(final int left, final int top, final int right, final int bottom, int pointX,
			int pointY) {
		final int var7 = guiLeft;
		final int var8 = guiTop;
		pointX -= var7;
		pointY -= var8;
		return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	protected void handleMouseClick(final Slot slotIn, int slotId, final int clickedButton, final int clickType) {
		if (slotIn != null) {
			slotId = slotIn.slotNumber;
		}

		Minecraft.playerController.windowClick(inventorySlots.windowId, slotId, clickedButton, clickType,
				Minecraft.thePlayer);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (keyCode == 1 || keyCode == Minecraft.gameSettings.keyBindInventory.getKeyCode()) {
			Minecraft.thePlayer.closeScreen();
		}

		checkHotbarKeys(keyCode);

		if (theSlot != null && theSlot.getHasStack()) {
			if (keyCode == Minecraft.gameSettings.keyBindPickBlock.getKeyCode()) {
				handleMouseClick(theSlot, theSlot.slotNumber, 0, 3);
			} else if (keyCode == Minecraft.gameSettings.keyBindDrop.getKeyCode()) {
				handleMouseClick(theSlot, theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
			}
		}
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack. Args : keyCode, Returns true if a
	 * Hotbar key is pressed, else false
	 */
	protected boolean checkHotbarKeys(final int keyCode) {
		if (Minecraft.thePlayer.inventory.getItemStack() == null && theSlot != null) {
			for (int var2 = 0; var2 < 9; ++var2) {
				if (keyCode == Minecraft.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
					handleMouseClick(theSlot, theSlot.slotNumber, var2, 2);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		if (Minecraft.thePlayer != null) {
			inventorySlots.onContainerClosed(Minecraft.thePlayer);
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();

		if (!Minecraft.thePlayer.isEntityAlive() || Minecraft.thePlayer.isDead) {
			Minecraft.thePlayer.closeScreen();
		}
	}
}
