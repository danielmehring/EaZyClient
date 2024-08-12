package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.Unpooled;

public class GuiBeacon extends GuiContainer {

public static final int EaZy = 531;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");
	private final IInventory tileBeacon;
	private GuiBeacon.ConfirmButton beaconConfirmButton;
	private boolean buttonsNotDrawn;
	// private static final String __OBFID = "http://https://fuckuskid00000739";

	public GuiBeacon(final InventoryPlayer p_i45507_1_, final IInventory p_i45507_2_) {
		super(new ContainerBeacon(p_i45507_1_, p_i45507_2_));
		tileBeacon = p_i45507_2_;
		xSize = 230;
		ySize = 219;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(beaconConfirmButton = new GuiBeacon.ConfirmButton(-1, guiLeft + 164, guiTop + 107));
		buttonList.add(new GuiBeacon.CancelButton(-2, guiLeft + 190, guiTop + 107));
		buttonsNotDrawn = true;
		beaconConfirmButton.enabled = false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		final int var1 = tileBeacon.getField(0);
		final int var2 = tileBeacon.getField(1);
		final int var3 = tileBeacon.getField(2);

		if (buttonsNotDrawn && var1 >= 0) {
			buttonsNotDrawn = false;
			int var5;
			int var6;
			int var7;
			int var8;
			GuiBeacon.PowerButton var9;

			for (int var4 = 0; var4 <= 2; ++var4) {
				var5 = TileEntityBeacon.effectsList[var4].length;
				var6 = var5 * 22 + (var5 - 1) * 2;

				for (var7 = 0; var7 < var5; ++var7) {
					var8 = TileEntityBeacon.effectsList[var4][var7].id;
					var9 = new GuiBeacon.PowerButton(var4 << 8 | var8, guiLeft + 76 + var7 * 24 - var6 / 2,
							guiTop + 22 + var4 * 25, var8, var4);
					buttonList.add(var9);

					if (var4 >= var1) {
						var9.enabled = false;
					} else if (var8 == var2) {
						var9.func_146140_b(true);
					}
				}
			}

			final byte var10 = 3;
			var5 = TileEntityBeacon.effectsList[var10].length + 1;
			var6 = var5 * 22 + (var5 - 1) * 2;

			for (var7 = 0; var7 < var5 - 1; ++var7) {
				var8 = TileEntityBeacon.effectsList[var10][var7].id;
				var9 = new GuiBeacon.PowerButton(var10 << 8 | var8, guiLeft + 167 + var7 * 24 - var6 / 2, guiTop + 47,
						var8, var10);
				buttonList.add(var9);

				if (var10 >= var1) {
					var9.enabled = false;
				} else if (var8 == var3) {
					var9.func_146140_b(true);
				}
			}

			if (var2 > 0) {
				final GuiBeacon.PowerButton var11 = new GuiBeacon.PowerButton(var10 << 8 | var2,
						guiLeft + 167 + (var5 - 1) * 24 - var6 / 2, guiTop + 47, var2, var10);
				buttonList.add(var11);

				if (var10 >= var1) {
					var11.enabled = false;
				} else if (var2 == var3) {
					var11.func_146140_b(true);
				}
			}
		}

		beaconConfirmButton.enabled = tileBeacon.getStackInSlot(0) != null && var2 > 0;
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == -2) {
			mc.displayGuiScreen((GuiScreen) null);
		} else if (button.id == -1) {
			final String var2 = "MC|Beacon";
			final PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
			var3.writeInt(tileBeacon.getField(1));
			var3.writeInt(tileBeacon.getField(2));
			Minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
			mc.displayGuiScreen((GuiScreen) null);
		} else if (button instanceof GuiBeacon.PowerButton) {
			if (((GuiBeacon.PowerButton) button).func_146141_c()) {
				return;
			}

			final int var5 = button.id;
			final int var6 = var5 & 255;
			final int var4 = var5 >> 8;

			if (var4 < 3) {
				tileBeacon.setField(1, var6);
			} else {
				tileBeacon.setField(2, var6);
			}

			buttonList.clear();
			initGui();
			updateScreen();
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		RenderHelper.disableStandardItemLighting();
		drawCenteredString(fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
		drawCenteredString(fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
		final Iterator var3 = buttonList.iterator();

		while (var3.hasNext()) {
			final GuiButton var4 = (GuiButton) var3.next();

			if (var4.isMouseOver()) {
				var4.drawButtonForegroundLayer(mouseX - guiLeft, mouseY - guiTop);
				break;
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(beaconGuiTextures);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		itemRender.zLevel = 100.0F;
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), var4 + 42, var5 + 109);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.diamond), var4 + 42 + 22, var5 + 109);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.gold_ingot), var4 + 42 + 44, var5 + 109);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.iron_ingot), var4 + 42 + 66, var5 + 109);
		itemRender.zLevel = 0.0F;
	}

	static class Button extends GuiButton {
		private final ResourceLocation field_146145_o;
		private final int field_146144_p;
		private final int field_146143_q;
		private boolean field_146142_r;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000743";

		protected Button(final int p_i1077_1_, final int p_i1077_2_, final int p_i1077_3_,
				final ResourceLocation p_i1077_4_, final int p_i1077_5_, final int p_i1077_6_) {
			super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
			field_146145_o = p_i1077_4_;
			field_146144_p = p_i1077_5_;
			field_146143_q = p_i1077_6_;
		}

		@Override
		public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
			if (visible) {
				Minecraft.getTextureManager().bindTexture(GuiBeacon.beaconGuiTextures);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
						&& mouseY < yPosition + height;
				final short var4 = 219;
				int var5 = 0;

				if (!enabled) {
					var5 += width * 2;
				} else if (field_146142_r) {
					var5 += width * 1;
				} else if (hovered) {
					var5 += width * 3;
				}

				drawTexturedModalRect(xPosition, yPosition, var5, var4, width, height);

				if (!GuiBeacon.beaconGuiTextures.equals(field_146145_o)) {
					Minecraft.getTextureManager().bindTexture(field_146145_o);
				}

				drawTexturedModalRect(xPosition + 2, yPosition + 2, field_146144_p, field_146143_q, 18, 18);
			}
		}

		public boolean func_146141_c() {
			return field_146142_r;
		}

		public void func_146140_b(final boolean p_146140_1_) {
			field_146142_r = p_146140_1_;
		}
	}

	class CancelButton extends GuiBeacon.Button {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000740";

		public CancelButton(final int p_i1074_2_, final int p_i1074_3_, final int p_i1074_4_) {
			super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.beaconGuiTextures, 112, 220);
		}

		@Override
		public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
			drawCreativeTabHoveringText(I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
		}
	}

	class ConfirmButton extends GuiBeacon.Button {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000741";

		public ConfirmButton(final int p_i1075_2_, final int p_i1075_3_, final int p_i1075_4_) {
			super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.beaconGuiTextures, 90, 220);
		}

		@Override
		public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
			drawCreativeTabHoveringText(I18n.format("gui.done", new Object[0]), mouseX, mouseY);
		}
	}

	class PowerButton extends GuiBeacon.Button {
		private final int field_146149_p;
		private final int field_146148_q;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000742";

		public PowerButton(final int p_i1076_2_, final int p_i1076_3_, final int p_i1076_4_, final int p_i1076_5_,
				final int p_i1076_6_) {
			super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.inventoryBackground,
					0 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18,
					198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
			field_146149_p = p_i1076_5_;
			field_146148_q = p_i1076_6_;
		}

		@Override
		public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
			String var3 = I18n.format(Potion.potionTypes[field_146149_p].getName(), new Object[0]);

			if (field_146148_q >= 3 && field_146149_p != Potion.regeneration.id) {
				var3 = var3 + " II";
			}

			drawCreativeTabHoveringText(var3, mouseX, mouseY);
		}
	}
}
