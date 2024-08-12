package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {

public static final int EaZy = 539;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(
			"textures/gui/container/furnace.png");
	private final InventoryPlayer field_175383_v;
	private final IInventory tileFurnace;
	// private static final String __OBFID = "http://https://fuckuskid00000758";

	public GuiFurnace(final InventoryPlayer p_i45501_1_, final IInventory p_i45501_2_) {
		super(new ContainerFurnace(p_i45501_1_, p_i45501_2_));
		field_175383_v = p_i45501_1_;
		tileFurnace = p_i45501_2_;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		final String var3 = tileFurnace.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
		fontRendererObj.drawString(field_175383_v.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(furnaceGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		int var6;

		if (TileEntityFurnace.func_174903_a(tileFurnace)) {
			var6 = func_175382_i(13);
			drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 1);
		}

		var6 = func_175381_h(24);
		drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
	}

	private int func_175381_h(final int p_175381_1_) {
		final int var2 = tileFurnace.getField(2);
		final int var3 = tileFurnace.getField(3);
		return var3 != 0 && var2 != 0 ? var2 * p_175381_1_ / var3 : 0;
	}

	private int func_175382_i(final int p_175382_1_) {
		int var2 = tileFurnace.getField(1);

		if (var2 == 0) {
			var2 = 200;
		}

		return tileFurnace.getField(0) * p_175382_1_ / var2;
	}
}
