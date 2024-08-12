package optifine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;

public class TooltipManager {

public static final int EaZy = 1970;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private GuiScreen guiScreen = null;
	private int lastMouseX = 0;
	private int lastMouseY = 0;
	private long mouseStillTime = 0L;

	public TooltipManager(final GuiScreen guiScreen) {
		this.guiScreen = guiScreen;
	}

	public void drawTooltips(final int x, final int y, final List buttonList) {
		if (Math.abs(x - lastMouseX) <= 5 && Math.abs(y - lastMouseY) <= 5) {
			final short activateDelay = 700;

			if (System.currentTimeMillis() >= mouseStillTime + activateDelay) {
				final int x1 = guiScreen.width / 2 - 150;
				int y1 = guiScreen.height / 6 - 7;

				if (y <= y1 + 98) {
					y1 += 105;
				}

				final int x2 = x1 + 150 + 150;
				final int y2 = y1 + 84 + 10;
				final GuiButton btn = getSelectedButton(x, y, buttonList);

				if (btn instanceof IOptionControl) {
					final IOptionControl ctl = (IOptionControl) btn;
					final GameSettings.Options option = ctl.getOption();
					final String[] lines = getTooltipLines(option);

					if (lines == null) {
						return;
					}

					GuiVideoSettings.drawGradientRect(guiScreen, x1, y1, x2, y2, -536870912, -536870912);

					for (int i = 0; i < lines.length; ++i) {
						final String line = lines[i];
						int col = 14540253;

						if (line.endsWith("!")) {
							col = 16719904;
						}

						Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11,
								col);
					}
				}
			}
		} else {
			lastMouseX = x;
			lastMouseY = y;
			mouseStillTime = System.currentTimeMillis();
		}
	}

	private GuiButton getSelectedButton(final int x, final int y, final List buttonList) {
		for (int k = 0; k < buttonList.size(); ++k) {
			final GuiButton btn = (GuiButton) buttonList.get(k);
			final int btnWidth = GuiVideoSettings.getButtonWidth(btn);
			final int btnHeight = GuiVideoSettings.getButtonHeight(btn);
			final boolean flag = x >= btn.xPosition && y >= btn.yPosition && x < btn.xPosition + btnWidth
					&& y < btn.yPosition + btnHeight;

			if (flag) {
				return btn;
			}
		}

		return null;
	}

	private static String[] getTooltipLines(final GameSettings.Options option) {
		return getTooltipLines(option.getEnumString());
	}

	private static String[] getTooltipLines(final String key) {
		final ArrayList list = new ArrayList();

		for (int lines = 0; lines < 10; ++lines) {
			final String lineKey = key + ".tooltip." + (lines + 1);
			final String line = Lang.get(lineKey, (String) null);

			if (line == null) {
				break;
			}

			list.add(line);
		}

		if (list.size() <= 0) {
			return null;
		} else {
			final String[] var5 = (String[]) list.toArray(new String[list.size()]);
			return var5;
		}
	}
}
