package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCrafting extends GuiContainer {

public static final int EaZy = 536;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation(
			"textures/gui/container/crafting_table.png");
	// private static final String __OBFID = "http://https://fuckuskid00000750";

	public GuiCrafting(final InventoryPlayer p_i45504_1_, final World worldIn) {
		this(p_i45504_1_, worldIn, BlockPos.ORIGIN);
	}

	public GuiCrafting(final InventoryPlayer p_i45505_1_, final World worldIn, final BlockPos p_i45505_3_) {
		super(new ContainerWorkbench(p_i45505_1_, worldIn, p_i45505_3_));
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(craftingTableGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
	}
}
