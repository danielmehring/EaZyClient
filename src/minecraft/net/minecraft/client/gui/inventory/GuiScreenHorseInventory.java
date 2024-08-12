package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {

public static final int EaZy = 541;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
	private final IInventory field_147030_v;
	private final IInventory field_147029_w;
	private final EntityHorse field_147034_x;
	private float field_147033_y;
	private float field_147032_z;
	// private static final String __OBFID = "http://https://fuckuskid00000760";

	public GuiScreenHorseInventory(final IInventory p_i1093_1_, final IInventory p_i1093_2_,
			final EntityHorse p_i1093_3_) {

		super(new ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_, Minecraft.thePlayer));
		field_147030_v = p_i1093_1_;
		field_147029_w = p_i1093_2_;
		field_147034_x = p_i1093_3_;
		allowUserInput = false;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(field_147029_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(field_147030_v.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(horseGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);

		if (field_147034_x.isChested()) {
			drawTexturedModalRect(var4 + 79, var5 + 17, 0, ySize, 90, 54);
		}

		if (field_147034_x.canWearArmor()) {
			drawTexturedModalRect(var4 + 7, var5 + 35, 0, ySize + 54, 18, 18);
		}

		GuiInventory.drawEntityOnScreen(var4 + 51, var5 + 60, 17, var4 + 51 - field_147033_y,
				var5 + 75 - 50 - field_147032_z, field_147034_x);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		field_147033_y = mouseX;
		field_147032_z = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
