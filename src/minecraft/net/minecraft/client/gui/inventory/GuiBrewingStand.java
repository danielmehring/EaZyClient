package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBrewingStand extends GuiContainer {

public static final int EaZy = 532;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation(
			"textures/gui/container/brewing_stand.png");
	private final InventoryPlayer field_175384_v;
	private final IInventory tileBrewingStand;
	// private static final String __OBFID = "http://https://fuckuskid00000746";

	public GuiBrewingStand(final InventoryPlayer p_i45506_1_, final IInventory p_i45506_2_) {
		super(new ContainerBrewingStand(p_i45506_1_, p_i45506_2_));
		field_175384_v = p_i45506_1_;
		tileBrewingStand = p_i45506_2_;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		final String var3 = tileBrewingStand.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
		fontRendererObj.drawString(field_175384_v.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(brewingStandGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		final int var6 = tileBrewingStand.getField(0);

		if (var6 > 0) {
			int var7 = (int) (28.0F * (1.0F - var6 / 400.0F));

			if (var7 > 0) {
				drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
			}

			final int var8 = var6 / 2 % 7;

			switch (var8) {
				case 0:
					var7 = 29;
					break;

				case 1:
					var7 = 24;
					break;

				case 2:
					var7 = 20;
					break;

				case 3:
					var7 = 16;
					break;

				case 4:
					var7 = 11;
					break;

				case 5:
					var7 = 6;
					break;

				case 6:
					var7 = 0;
			}

			if (var7 > 0) {
				drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
			}
		}
	}
}
