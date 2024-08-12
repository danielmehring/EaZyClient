package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.Unpooled;

public class GuiMerchant extends GuiContainer {

public static final int EaZy = 489;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
	private final IMerchant field_147037_w;
	private GuiMerchant.MerchantButton field_147043_x;
	private GuiMerchant.MerchantButton field_147042_y;
	private int field_147041_z;
	private final IChatComponent field_147040_A;
	// private static final String __OBFID = "http://https://fuckuskid00000762";

	public GuiMerchant(final InventoryPlayer p_i45500_1_, final IMerchant p_i45500_2_, final World worldIn) {
		super(new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
		field_147037_w = p_i45500_2_;
		field_147040_A = p_i45500_2_.getDisplayName();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		final int var1 = (width - xSize) / 2;
		final int var2 = (height - ySize) / 2;
		buttonList.add(field_147043_x = new GuiMerchant.MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
		buttonList.add(field_147042_y = new GuiMerchant.MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
		field_147043_x.enabled = false;
		field_147042_y.enabled = false;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		final String var3 = field_147040_A.getUnformattedText();
		fontRendererObj.drawString(var3, xSize / 2 - fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		final MerchantRecipeList var1 = field_147037_w.getRecipes(Minecraft.thePlayer);

		if (var1 != null) {
			field_147043_x.enabled = field_147041_z < var1.size() - 1;
			field_147042_y.enabled = field_147041_z > 0;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		boolean var2 = false;

		if (button == field_147043_x) {
			++field_147041_z;
			final MerchantRecipeList var3 = field_147037_w.getRecipes(Minecraft.thePlayer);

			if (var3 != null && field_147041_z >= var3.size()) {
				field_147041_z = var3.size() - 1;
			}

			var2 = true;
		} else if (button == field_147042_y) {
			--field_147041_z;

			if (field_147041_z < 0) {
				field_147041_z = 0;
			}

			var2 = true;
		}

		if (var2) {
			((ContainerMerchant) inventorySlots).setCurrentRecipeIndex(field_147041_z);
			final PacketBuffer var4 = new PacketBuffer(Unpooled.buffer());
			var4.writeInt(field_147041_z);
			Minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var4));
		}
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(field_147038_v);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		final MerchantRecipeList var6 = field_147037_w.getRecipes(Minecraft.thePlayer);

		if (var6 != null && !var6.isEmpty()) {
			final int var7 = field_147041_z;

			if (var7 < 0 || var7 >= var6.size()) {
				return;
			}

			final MerchantRecipe var8 = (MerchantRecipe) var6.get(var7);

			if (var8.isRecipeDisabled()) {
				Minecraft.getTextureManager().bindTexture(field_147038_v);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableLighting();
				drawTexturedModalRect(guiLeft + 83, guiTop + 21, 212, 0, 28, 21);
				drawTexturedModalRect(guiLeft + 83, guiTop + 51, 212, 0, 28, 21);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		final MerchantRecipeList var4 = field_147037_w.getRecipes(Minecraft.thePlayer);

		if (var4 != null && !var4.isEmpty()) {
			final int var5 = (width - xSize) / 2;
			final int var6 = (height - ySize) / 2;
			final int var7 = field_147041_z;
			final MerchantRecipe var8 = (MerchantRecipe) var4.get(var7);
			final ItemStack var9 = var8.getItemToBuy();
			final ItemStack var10 = var8.getSecondItemToBuy();
			final ItemStack var11 = var8.getItemToSell();
			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableColorMaterial();
			GlStateManager.enableLighting();
			itemRender.zLevel = 100.0F;
			itemRender.renderItemAndEffectIntoGUI(var9, var5 + 36, var6 + 24);
			itemRender.renderItemOverlayIntoGUI(fontRendererObj, var9, var5 + 36, var6 + 24);

			if (var10 != null) {
				itemRender.renderItemAndEffectIntoGUI(var10, var5 + 62, var6 + 24);
				itemRender.renderItemOverlayIntoGUI(fontRendererObj, var10, var5 + 62, var6 + 24);
			}

			itemRender.renderItemAndEffectIntoGUI(var11, var5 + 120, var6 + 24);
			itemRender.renderItemOverlayIntoGUI(fontRendererObj, var11, var5 + 120, var6 + 24);
			itemRender.zLevel = 0.0F;
			GlStateManager.disableLighting();

			if (isPointInRegion(36, 24, 16, 16, mouseX, mouseY) && var9 != null) {
				renderToolTip(var9, mouseX, mouseY);
			} else if (var10 != null && isPointInRegion(62, 24, 16, 16, mouseX, mouseY) && var10 != null) {
				renderToolTip(var10, mouseX, mouseY);
			} else if (var11 != null && isPointInRegion(120, 24, 16, 16, mouseX, mouseY) && var11 != null) {
				renderToolTip(var11, mouseX, mouseY);
			} else if (var8.isRecipeDisabled() && (isPointInRegion(83, 21, 28, 21, mouseX, mouseY)
					|| isPointInRegion(83, 51, 28, 21, mouseX, mouseY))) {
				drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), mouseX, mouseY);
			}

			GlStateManager.popMatrix();
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
		}
	}

	public IMerchant getMerchant() {
		return field_147037_w;
	}

	static class MerchantButton extends GuiButton {
		private final boolean field_146157_o;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000763";

		public MerchantButton(final int p_i1095_1_, final int p_i1095_2_, final int p_i1095_3_,
				final boolean p_i1095_4_) {
			super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 19, "");
			field_146157_o = p_i1095_4_;
		}

		@Override
		public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
			if (visible) {
				Minecraft.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				final boolean var4 = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
						&& mouseY < yPosition + height;
				int var5 = 0;
				int var6 = 176;

				if (!enabled) {
					var6 += width * 2;
				} else if (var4) {
					var6 += width;
				}

				if (!field_146157_o) {
					var5 += height;
				}

				drawTexturedModalRect(xPosition, yPosition, var6, var5, width, height);
			}
		}
	}
}
