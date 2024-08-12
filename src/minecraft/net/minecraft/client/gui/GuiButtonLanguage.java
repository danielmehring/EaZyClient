package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {

public static final int EaZy = 461;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000672";

	public GuiButtonLanguage(final int p_i1041_1_, final int p_i1041_2_, final int p_i1041_3_) {
		super(p_i1041_1_, p_i1041_2_, p_i1041_3_, 20, 20, "");
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
		if (visible) {
			Minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			final boolean var4 = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
					&& mouseY < yPosition + height;
			int var5 = 106;

			if (var4) {
				var5 += height;
			}

			drawTexturedModalRect(xPosition, yPosition, 0, var5, width, height);
		}
	}
}
