package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiConfirmOpenLink extends GuiYesNo {

public static final int EaZy = 466;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Text to warn players from opening unsafe links. */
	private final String openLinkWarning;

	/** Label for the Copy to Clipboard button. */
	private final String copyLinkButtonText;
	private final String linkText;
	private boolean showSecurityWarning = true;
	// private static final String __OBFID = "http://https://fuckuskid00000683";

	public GuiConfirmOpenLink(final GuiYesNoCallback p_i1084_1_, final String p_i1084_2_, final int p_i1084_3_,
			final boolean p_i1084_4_) {
		super(p_i1084_1_, I18n.format(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]),
				p_i1084_2_, p_i1084_3_);
		confirmButtonText = I18n.format(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
		cancelButtonText = I18n.format(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
		copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
		openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
		linkText = p_i1084_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 50 - 105, height / 6 + 96, 100, 20, confirmButtonText));
		buttonList.add(new GuiButton(2, width / 2 - 50, height / 6 + 96, 100, 20, copyLinkButtonText));
		buttonList.add(new GuiButton(1, width / 2 - 50 + 105, height / 6 + 96, 100, 20, cancelButtonText));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 2) {
			copyLinkToClipboard();
		}

		parentScreen.confirmClicked(button.id == 0, parentButtonClickedId);
	}

	/**
	 * Copies the link to the system clipboard.
	 */
	public void copyLinkToClipboard() {
		setClipboardString(linkText);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (showSecurityWarning) {
			drawCenteredString(fontRendererObj, openLinkWarning, width / 2, 110, 16764108);
		}
	}

	public void disableSecurityWarning() {
		showSecurityWarning = false;
	}
}
