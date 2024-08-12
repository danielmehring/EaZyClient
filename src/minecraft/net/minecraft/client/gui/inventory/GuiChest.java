package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer {

public static final int EaZy = 533;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_147017_u = new ResourceLocation(
			"textures/gui/container/generic_54.png");
	private final IInventory upperChestInventory;
	private final IInventory lowerChestInventory;

	/**
	 * window height is calculated with these values; the more rows, the heigher
	 */
	private final int inventoryRows;
	// private static final String __OBFID = "http://https://fuckuskid00000749";

	public GuiChest(final IInventory p_i46315_1_, final IInventory p_i46315_2_) {

		super(new ContainerChest(p_i46315_1_, p_i46315_2_, Minecraft.thePlayer));
		upperChestInventory = p_i46315_1_;
		lowerChestInventory = p_i46315_2_;
		allowUserInput = false;
		final short var3 = 222;
		final int var4 = var3 - 108;
		inventoryRows = p_i46315_2_.getSizeInventory() / 9;
		ySize = var4 + inventoryRows * 18;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(upperChestInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2,
				4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(field_147017_u);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, inventoryRows * 18 + 17);
		drawTexturedModalRect(var4, var5 + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}
}
