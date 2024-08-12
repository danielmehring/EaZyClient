package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer {

public static final int EaZy = 537;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation dispenserGuiTextures = new ResourceLocation(
			"textures/gui/container/dispenser.png");
	private final InventoryPlayer field_175376_w;
	public IInventory field_175377_u;
	// private static final String __OBFID = "http://https://fuckuskid00000765";

	public GuiDispenser(final InventoryPlayer p_i45503_1_, final IInventory p_i45503_2_) {
		super(new ContainerDispenser(p_i45503_1_, p_i45503_2_));
		field_175376_w = p_i45503_1_;
		field_175377_u = p_i45503_2_;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		final String var3 = field_175377_u.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
		fontRendererObj.drawString(field_175376_w.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(dispenserGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
}
