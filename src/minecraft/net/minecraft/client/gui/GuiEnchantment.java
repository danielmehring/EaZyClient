package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.glu.Project;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public class GuiEnchantment extends GuiContainer {

public static final int EaZy = 474;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_147078_C = new ResourceLocation(
			"textures/gui/container/enchanting_table.png");
	private static final ResourceLocation field_147070_D = new ResourceLocation(
			"textures/entity/enchanting_table_book.png");
	private static final ModelBook field_147072_E = new ModelBook();
	private final InventoryPlayer field_175379_F;
	private final Random field_147074_F = new Random();
	private final ContainerEnchantment field_147075_G;
	public int field_147073_u;
	public float field_147071_v;
	public float field_147069_w;
	public float field_147082_x;
	public float field_147081_y;
	public float field_147080_z;
	public float field_147076_A;
	ItemStack field_147077_B;
	private final IWorldNameable field_175380_I;
	// private static final String __OBFID = "http://https://fuckuskid00000757";

	public GuiEnchantment(final InventoryPlayer p_i45502_1_, final World worldIn, final IWorldNameable p_i45502_3_) {
		super(new ContainerEnchantment(p_i45502_1_, worldIn));
		field_175379_F = p_i45502_1_;
		field_147075_G = (ContainerEnchantment) inventorySlots;
		field_175380_I = p_i45502_3_;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items). Args : mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		fontRendererObj.drawString(field_175380_I.getDisplayName().getUnformattedText(), 12, 5, 4210752);
		fontRendererObj.drawString(field_175379_F.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		func_147068_g();
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;

		for (int var6 = 0; var6 < 3; ++var6) {
			final int var7 = mouseX - (var4 + 60);
			final int var8 = mouseY - (var5 + 14 + 19 * var6);

			if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19
					&& field_147075_G.enchantItem(Minecraft.thePlayer, var6)) {
				Minecraft.playerController.sendEnchantPacket(field_147075_G.windowId, var6);
			}
		}
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(field_147078_C);
		final int var4 = (width - xSize) / 2;
		final int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		GlStateManager.pushMatrix();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		final ScaledResolution var6 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
		GlStateManager.viewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(),
				(var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(),
				240 * var6.getScaleFactor());
		GlStateManager.translate(-0.34F, 0.23F, 0.0F);
		Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
		final float var7 = 1.0F;
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.translate(0.0F, 3.3F, -16.0F);
		GlStateManager.scale(var7, var7, var7);
		final float var8 = 5.0F;
		GlStateManager.scale(var8, var8, var8);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(field_147070_D);
		GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
		final float var9 = field_147076_A + (field_147080_z - field_147076_A) * partialTicks;
		GlStateManager.translate((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
		GlStateManager.rotate(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		float var10 = field_147069_w + (field_147071_v - field_147069_w) * partialTicks + 0.25F;
		float var11 = field_147069_w + (field_147071_v - field_147069_w) * partialTicks + 0.75F;
		var10 = (var10 - MathHelper.truncateDoubleToInt(var10)) * 1.6F - 0.3F;
		var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6F - 0.3F;

		if (var10 < 0.0F) {
			var10 = 0.0F;
		}

		if (var11 < 0.0F) {
			var11 = 0.0F;
		}

		if (var10 > 1.0F) {
			var10 = 1.0F;
		}

		if (var11 > 1.0F) {
			var11 = 1.0F;
		}

		GlStateManager.enableRescaleNormal();
		field_147072_E.render((Entity) null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.matrixMode(5889);
		GlStateManager.viewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		EnchantmentNameParts.func_178176_a().reseedRandomGenerator(field_147075_G.field_178149_f);
		final int var12 = field_147075_G.func_178147_e();

		for (int var13 = 0; var13 < 3; ++var13) {
			final int var14 = var4 + 60;
			final int var15 = var14 + 20;
			final byte var16 = 86;
			final String var17 = EnchantmentNameParts.func_178176_a().generateNewRandomName();
			zLevel = 0.0F;
			Minecraft.getTextureManager().bindTexture(field_147078_C);
			final int var18 = field_147075_G.enchantLevels[var13];
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			if (var18 == 0) {
				drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
			} else {
				final String var19 = "" + var18;
				FontRenderer var20 = mc.standardGalacticFontRenderer;
				int var21 = 6839882;

				if ((var12 < var13 + 1 || Minecraft.thePlayer.experienceLevel < var18)
						&& !Minecraft.thePlayer.capabilities.isCreativeMode) {
					drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
					drawTexturedModalRect(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 239, 16, 16);
					var20.drawSplitString(var17, var15, var5 + 16 + 19 * var13, var16, (var21 & 16711422) >> 1);
					var21 = 4226832;
				} else {
					final int var22 = mouseX - (var4 + 60);
					final int var23 = mouseY - (var5 + 14 + 19 * var13);

					if (var22 >= 0 && var23 >= 0 && var22 < 108 && var23 < 19) {
						drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 204, 108, 19);
						var21 = 16777088;
					} else {
						drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 166, 108, 19);
					}

					drawTexturedModalRect(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 223, 16, 16);
					var20.drawSplitString(var17, var15, var5 + 16 + 19 * var13, var16, var21);
					var21 = 8453920;
				}

				var20 = mc.fontRendererObj;
				var20.drawStringWithShadow(var19, var15 + 86 - var20.getStringWidth(var19), var5 + 16 + 19 * var13 + 7,
						var21);
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
		final boolean var4 = Minecraft.thePlayer.capabilities.isCreativeMode;
		final int var5 = field_147075_G.func_178147_e();

		for (int var6 = 0; var6 < 3; ++var6) {
			final int var7 = field_147075_G.enchantLevels[var6];
			final int var8 = field_147075_G.field_178151_h[var6];
			final int var9 = var6 + 1;

			if (isPointInRegion(60, 14 + 19 * var6, 108, 17, mouseX, mouseY) && var7 > 0 && var8 >= 0) {
				final ArrayList var10 = Lists.newArrayList();
				String var11;

				if (var8 >= 0 && Enchantment.byID(var8 & 255) != null) {
					var11 = Enchantment.byID(var8 & 255).getTranslatedName((var8 & 65280) >> 8);
					var10.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString()
							+ I18n.format("container.enchant.clue", new Object[] { var11 }));
				}

				if (!var4) {
					if (var8 >= 0) {
						var10.add("");
					}

					if (Minecraft.thePlayer.experienceLevel < var7) {
						var10.add(EnumChatFormatting.RED.toString() + "Level Requirement: "
								+ field_147075_G.enchantLevels[var6]);
					} else {
						var11 = "";

						if (var9 == 1) {
							var11 = I18n.format("container.enchant.lapis.one", new Object[0]);
						} else {
							var11 = I18n.format("container.enchant.lapis.many", new Object[] { var9});
						}

						if (var5 >= var9) {
							var10.add(EnumChatFormatting.GRAY.toString() + "" + var11);
						} else {
							var10.add(EnumChatFormatting.RED.toString() + "" + var11);
						}

						if (var9 == 1) {
							var11 = I18n.format("container.enchant.level.one", new Object[0]);
						} else {
							var11 = I18n.format("container.enchant.level.many", new Object[] { var9});
						}

						var10.add(EnumChatFormatting.GRAY.toString() + "" + var11);
					}
				}

				drawHoveringText(var10, mouseX, mouseY);
				break;
			}
		}
	}

	public void func_147068_g() {
		final ItemStack var1 = inventorySlots.getSlot(0).getStack();

		if (!ItemStack.areItemStacksEqual(var1, field_147077_B)) {
			field_147077_B = var1;

			do {
				field_147082_x += field_147074_F.nextInt(4) - field_147074_F.nextInt(4);
			} while (field_147071_v <= field_147082_x + 1.0F && field_147071_v >= field_147082_x - 1.0F);
		}

		++field_147073_u;
		field_147069_w = field_147071_v;
		field_147076_A = field_147080_z;
		boolean var2 = false;

		for (int var3 = 0; var3 < 3; ++var3) {
			if (field_147075_G.enchantLevels[var3] != 0) {
				var2 = true;
			}
		}

		if (var2) {
			field_147080_z += 0.2F;
		} else {
			field_147080_z -= 0.2F;
		}

		field_147080_z = MathHelper.clamp_float(field_147080_z, 0.0F, 1.0F);
		float var5 = (field_147082_x - field_147071_v) * 0.4F;
		final float var4 = 0.2F;
		var5 = MathHelper.clamp_float(var5, -var4, var4);
		field_147081_y += (var5 - field_147081_y) * 0.9F;
		field_147071_v += field_147081_y;
	}
}
