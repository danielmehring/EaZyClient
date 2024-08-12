package net.minecraft.client.gui.inventory;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends InventoryEffectRenderer {

public static final int EaZy = 540;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The old x position of the mouse pointer */
	private float oldMouseX;

	/** The old y position of the mouse pointer */
	private float oldMouseY;
	// private static final String __OBFID = "http://https://fuckuskid00000761";

	public EntityPlayer player;
	
	public GuiInventory(final EntityPlayer p_i1094_1_) {
		super(p_i1094_1_.inventoryContainer);
		allowUserInput = true;
		player = p_i1094_1_;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		if (Minecraft.playerController.isInCreativeMode()) {
			mc.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
		}

		func_175378_g();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();

		if (Minecraft.playerController.isInCreativeMode()) {
			mc.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
		} else {
			super.initGui();
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		oldMouseX = mouseX;
		oldMouseY = mouseY;
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(inventoryBackground);
		final int var4 = guiLeft;
		final int var5 = guiTop;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		drawEntityOnScreen(var4 + 51, var5 + 75, 30, var4 + 51 - oldMouseX, var5 + 75 - 50 - oldMouseY,
				player);
	}

	/**
	 * Draws the entity to the screen. Args: xPos, yPos, scale, mouseX, mouseY,
	 * entityLiving
	 */
	public static void drawEntityOnScreen(final int xPos, final int yPos, final int scale, final float mouseX,
			final float mouseY, final EntityLivingBase entity) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate(xPos, yPos, 50.0F);
		GlStateManager.scale(-scale, scale, scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		final float var6 = entity.renderYawOffset;
		final float var7 = entity.rotationYaw;
		final float var8 = entity.rotationPitch;
		final float var9 = entity.prevRotationYawHead;
		final float var10 = entity.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
		entity.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);

		final RenderManager var11 = Minecraft.getRenderManager();
		var11.func_178631_a(180.0F);
		var11.func_178633_a(false);
		var11.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		var11.func_178633_a(true);
		entity.renderYawOffset = var6;
		entity.rotationYaw = var7;
		entity.rotationPitch = var8;
		entity.prevRotationYawHead = var9;
		entity.rotationYawHead = var10;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
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
}
