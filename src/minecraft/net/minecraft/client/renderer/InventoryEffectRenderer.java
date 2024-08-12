package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;
import java.util.Iterator;

public abstract class InventoryEffectRenderer extends GuiContainer {

public static final int EaZy = 800;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** True if there is some potion effect to display */
	private boolean hasActivePotionEffects;

	public InventoryEffectRenderer(final Container p_i1089_1_) {
		super(p_i1089_1_);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		func_175378_g();
	}

	protected void func_175378_g() {
		if (!Minecraft.thePlayer.getActivePotionEffects().isEmpty()) {
			guiLeft = 160 + (width - xSize - 200) / 2;
			hasActivePotionEffects = true;
		} else {
			guiLeft = (width - xSize) / 2;
			hasActivePotionEffects = false;
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (hasActivePotionEffects) {
			drawActivePotionEffects();
		}
	}

	/**
	 * Display the potion effects list
	 */
	private void drawActivePotionEffects() {
		final int var1 = guiLeft - 124;
		int var2 = guiTop;
		final Collection var4 = Minecraft.thePlayer.getActivePotionEffects();

		if (!var4.isEmpty()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableLighting();
			int var5 = 33;

			if (var4.size() > 5) {
				var5 = 132 / (var4.size() - 1);
			}

			for (final Iterator var6 = Minecraft.thePlayer.getActivePotionEffects().iterator(); var6
					.hasNext(); var2 += var5) {
				final PotionEffect var7 = (PotionEffect) var6.next();
				final Potion var8 = Potion.potionTypes[var7.getPotionID()];
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				Minecraft.getTextureManager().bindTexture(inventoryBackground);
				drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

				if (var8.hasStatusIcon()) {
					final int var9 = var8.getStatusIconIndex();
					drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
				}

				String var11 = I18n.format(var8.getName(), new Object[0]);

				if (var7.getAmplifier() == 1) {
					var11 = var11 + " " + I18n.format("enchantment.level.2", new Object[0]);
				} else if (var7.getAmplifier() == 2) {
					var11 = var11 + " " + I18n.format("enchantment.level.3", new Object[0]);
				} else if (var7.getAmplifier() == 3) {
					var11 = var11 + " " + I18n.format("enchantment.level.4", new Object[0]);
				}

				fontRendererObj.func_175063_a(var11, var1 + 10 + 18, var2 + 6, 16777215);
				final String var10 = Potion.getDurationString(var7);
				fontRendererObj.func_175063_a(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}
}
