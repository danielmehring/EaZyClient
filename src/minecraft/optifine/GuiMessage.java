package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class GuiMessage extends GuiScreen {

public static final int EaZy = 1905;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen parentScreen;
	private final String messageLine1;
	private final String messageLine2;
	private final List listLines2 = Lists.newArrayList();
	protected String confirmButtonText;
	private int ticksUntilEnable;

	public GuiMessage(final GuiScreen parentScreen, final String line1, final String line2) {
		this.parentScreen = parentScreen;
		messageLine1 = line1;
		messageLine2 = line2;
		confirmButtonText = I18n.format("gui.done", new Object[0]);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.add(new GuiOptionButton(0, width / 2 - 74, height / 6 + 96, confirmButtonText));
		listLines2.clear();
		listLines2.addAll(fontRendererObj.listFormattedStringToWidth(messageLine2, width - 50));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		Config.getMinecraft().displayGuiScreen(parentScreen);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, messageLine1, width / 2, 70, 16777215);
		int var4 = 90;

		for (final Iterator var5 = listLines2.iterator(); var5.hasNext(); var4 += fontRendererObj.FONT_HEIGHT) {
			final String var6 = (String) var5.next();
			drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void setButtonDelay(final int ticksUntilEnable) {
		this.ticksUntilEnable = ticksUntilEnable;
		GuiButton var3;

		for (final Iterator var2 = buttonList.iterator(); var2.hasNext(); var3.enabled = false) {
			var3 = (GuiButton) var2.next();
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		GuiButton var2;

		if (--ticksUntilEnable == 0) {
			for (final Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
				var2 = (GuiButton) var1.next();
			}
		}
	}
}
