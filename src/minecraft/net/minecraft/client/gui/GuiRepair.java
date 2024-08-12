package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import io.netty.buffer.Unpooled;

public class GuiRepair extends GuiContainer implements ICrafting {

public static final int EaZy = 500;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
	private final ContainerRepair anvil;
	private GuiTextField nameField;
	private final InventoryPlayer playerInventory;
	// private static final String __OBFID = "http://https://fuckuskid00000738";

	public GuiRepair(final InventoryPlayer p_i45508_1_, final World worldIn) {

		super(new ContainerRepair(p_i45508_1_, worldIn, Minecraft.thePlayer));
		playerInventory = p_i45508_1_;
		anvil = (ContainerRepair) inventorySlots;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		final int var1 = (width - xSize) / 2;
		final int var2 = (height - ySize) / 2;
		nameField = new GuiTextField(0, fontRendererObj, var1 + 62, var2 + 24, 103, 12);
		nameField.setTextColor(-1);
		nameField.setDisabledTextColour(-1);
		nameField.setEnableBackgroundDrawing(false);
		nameField.setMaxStringLength(40);
		inventorySlots.removeCraftingFromCrafters(this);
		inventorySlots.onCraftGuiOpened(this);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		inventorySlots.removeCraftingFromCrafters(this);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);

		if (anvil.maximumCost > 0) {
			int var3 = 8453920;
			boolean var4 = true;
			String var5 = I18n.format("container.repair.cost", new Object[] { anvil.maximumCost});

			if (anvil.maximumCost >= 40 && !Minecraft.thePlayer.capabilities.isCreativeMode) {
				var5 = I18n.format("container.repair.expensive", new Object[0]);
				var3 = 16736352;
			} else if (!anvil.getSlot(2).getHasStack()) {
				var4 = false;
			} else if (!anvil.getSlot(2).canTakeStack(playerInventory.player)) {
				var3 = 16736352;
			}

			if (var4) {
				final int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
				final int var7 = xSize - 8 - fontRendererObj.getStringWidth(var5);
				final byte var8 = 67;

				if (fontRendererObj.getUnicodeFlag()) {
					drawRect(var7 - 3, var8 - 2, xSize - 7, var8 + 10, -16777216);
					drawRect(var7 - 2, var8 - 1, xSize - 8, var8 + 9, -12895429);
				} else {
					fontRendererObj.drawString(var5, var7, var8 + 1, var6);
					fontRendererObj.drawString(var5, var7 + 1, var8, var6);
					fontRendererObj.drawString(var5, var7 + 1, var8 + 1, var6);
				}

				fontRendererObj.drawString(var5, var7, var8, var3);
			}
		}

		GlStateManager.enableLighting();
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (nameField.textboxKeyTyped(typedChar, keyCode)) {
			renameItem();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	private void renameItem() {
		String var1 = nameField.getText();
		final Slot var2 = anvil.getSlot(0);

		if (var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName()
				&& var1.equals(var2.getStack().getDisplayName())) {
			var1 = "";
		}

		anvil.updateItemName(var1);
		Minecraft.thePlayer.sendQueue.addToSendQueue(
				new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(var1)));
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		nameField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		nameField.drawTextBox();
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(anvilResource);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		drawTexturedModalRect(var4 + 59, var5 + 20, 0, ySize + (anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);

		if ((anvil.getSlot(0).getHasStack() || anvil.getSlot(1).getHasStack()) && !anvil.getSlot(2).getHasStack()) {
			drawTexturedModalRect(var4 + 99, var5 + 45, xSize, 0, 28, 21);
		}
	}

	/**
	 * update the crafting window inventory with the items in the list
	 */
	@Override
	public void updateCraftingInventory(final Container p_71110_1_, final List p_71110_2_) {
		sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
	}

	/**
	 * Sends the contents of an inventory slot to the client-side Container.
	 * This doesn't have to match the actual contents of that slot. Args:
	 * Container, slot number, slot contents
	 */
	@Override
	public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
		if (p_71111_2_ == 0) {
			nameField.setText(p_71111_3_ == null ? "" : p_71111_3_.getDisplayName());
			nameField.setEnabled(p_71111_3_ != null);

			if (p_71111_3_ != null) {
				renameItem();
			}
		}
	}

	/**
	 * Sends two ints to the client-side Container. Used for furnace burning
	 * time, smelting progress, brewing progress, and enchanting level. Normally
	 * the first int identifies which variable to update, and the second
	 * contains the new value. Both are truncated to shorts in non-local SMP.
	 */
	@Override
	public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {}

	@Override
	public void func_175173_a(final Container p_175173_1_, final IInventory p_175173_2_) {}
}
