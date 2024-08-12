package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {

public static final int EaZy = 513;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String field_146591_a = "";
	private String field_146589_f = "";
	private int field_146590_g;
	private boolean field_146592_h;
	// private static final String __OBFID = "http://https://fuckuskid00000707";

	/**
	 * Shows the 'Saving level' string.
	 */
	@Override
	public void displaySavingString(final String message) {
		resetProgressAndMessage(message);
	}

	/**
	 * this string, followed by "working..." and then the "% complete" are the 3
	 * lines shown. This resets progress to 0, and the WorkingString to
	 * "working...".
	 */
	@Override
	public void resetProgressAndMessage(final String p_73721_1_) {
		field_146591_a = p_73721_1_;
		displayLoadingString("Working...");
	}

	/**
	 * Displays a string on the loading screen supposed to indicate what is
	 * being done currently.
	 */
	@Override
	public void displayLoadingString(final String message) {
		field_146589_f = message;
		setLoadingProgress(0);
	}

	/**
	 * Updates the progress bar on the loading screen to the specified amount.
	 * Args: loadProgress
	 */
	@Override
	public void setLoadingProgress(final int progress) {
		field_146590_g = progress;
	}

	@Override
	public void setDoneWorking() {
		field_146592_h = true;
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (field_146592_h) {
			mc.displayGuiScreen((GuiScreen) null);
		} else {
			drawDefaultBackground(mouseX, mouseY);
			drawCenteredString(fontRendererObj, field_146591_a, width / 2, 70, 16777215);
			drawCenteredString(fontRendererObj, field_146589_f + " " + field_146590_g + "%", width / 2, 90, 16777215);
			super.drawScreen(mouseX, mouseY, partialTicks);
		}
	}
}
