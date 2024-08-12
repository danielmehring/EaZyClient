package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHopper extends GuiContainer {

public static final int EaZy = 478;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_147085_u = new ResourceLocation("textures/gui/container/hopper.png");
	private final IInventory field_147084_v;
	private final IInventory field_147083_w;
	// private static final String __OBFID = "http://https://fuckuskid00000759";

	public GuiHopper(final InventoryPlayer p_i1092_1_, final IInventory p_i1092_2_) {

		super(new ContainerHopper(p_i1092_1_, p_i1092_2_, Minecraft.thePlayer));
		field_147084_v = p_i1092_1_;
		field_147083_w = p_i1092_2_;
		allowUserInput = false;
		ySize = 133;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(field_147083_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(field_147084_v.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(field_147085_u);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
}
