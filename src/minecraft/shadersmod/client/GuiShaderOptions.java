package shadersmod.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import optifine.Config;
import optifine.Lang;
import optifine.StrUtils;

public class GuiShaderOptions extends GuiScreen {

public static final int EaZy = 1992;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen prevScreen;
	protected String title;
	private final GameSettings settings;
	private int lastMouseX;
	private int lastMouseY;
	private long mouseStillTime;
	private String screenName;
	private String screenText;
	private boolean changed;
	public static final String OPTION_PROFILE = "<profile>";
	public static final String OPTION_EMPTY = "<empty>";
	public static final String OPTION_REST = "*";

	public GuiShaderOptions(final GuiScreen guiscreen, final GameSettings gamesettings) {
		lastMouseX = 0;
		lastMouseY = 0;
		mouseStillTime = 0L;
		screenName = null;
		screenText = null;
		changed = false;
		title = "Shader Options";
		prevScreen = guiscreen;
		settings = gamesettings;
	}

	public GuiShaderOptions(final GuiScreen guiscreen, final GameSettings gamesettings, final String screenName) {
		this(guiscreen, gamesettings);
		this.screenName = screenName;

		if (screenName != null) {
			screenText = Shaders.translate("screen." + screenName, screenName);
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
		final byte baseId = 100;
		final byte baseY = 30;
		final byte stepY = 20;
		final byte btnWidth = 120;
		final byte btnHeight = 20;
		int columns = 2;
		final ShaderOption[] ops = Shaders.getShaderPackOptions(screenName);

		if (ops != null) {
			if (ops.length > 18) {
				columns = ops.length / 9 + 1;
			}

			for (int i = 0; i < ops.length; ++i) {
				final ShaderOption so = ops[i];

				if (so != null && so.isVisible()) {
					final int col = i % columns;
					final int row = i / columns;
					final int colWidth = Math.min(width / columns, 200);
					final int var21 = (width - colWidth * columns) / 2;
					final int x = col * colWidth + 5 + var21;
					final int y = baseY + row * stepY;
					final int w = colWidth - 10;
					final String text = getButtonText(so, w);
					final GuiButtonShaderOption btn = new GuiButtonShaderOption(baseId + i, x, y, w, btnHeight, so,
							text);
					btn.enabled = so.isEnabled();
					buttonList.add(btn);
				}
			}
		}

		buttonList.add(new GuiButton(201, width / 2 - btnWidth - 20, height / 6 + 168 + 11, btnWidth, btnHeight,
				I18n.format("controls.reset", new Object[0])));
		buttonList.add(new GuiButton(200, width / 2 + 20, height / 6 + 168 + 11, btnWidth, btnHeight,
				I18n.format("gui.done", new Object[0])));
	}

	private String getButtonText(final ShaderOption so, final int btnWidth) {
		String labelName = so.getNameText();

		if (so instanceof ShaderOptionScreen) {
			return labelName + "...";
		} else {
			final FontRenderer fr = Config.getMinecraft().fontRendererObj;

			for (final int lenSuffix = fr.getStringWidth(": " + Lang.getOff()) + 5; fr.getStringWidth(labelName)
					+ lenSuffix >= btnWidth
					&& labelName.length() > 0; labelName = labelName.substring(0, labelName.length() - 1)) {
			}

			final String col = so.isChanged() ? so.getValueColor(so.getValue()) : "";
			final String labelValue = so.getValueText(so.getValue());
			return labelName + ": " + col + labelValue;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton guibutton) {
		if (guibutton.enabled) {
			if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
				final GuiButtonShaderOption opts = (GuiButtonShaderOption) guibutton;
				final ShaderOption i = opts.getShaderOption();

				if (i instanceof ShaderOptionScreen) {
					final String var8 = i.getName();
					final GuiShaderOptions scr = new GuiShaderOptions(this, settings, var8);
					mc.displayGuiScreen(scr);
					return;
				}

				i.nextValue();
				updateAllButtons();
				changed = true;
			}

			if (guibutton.id == 201) {
				final ShaderOption[] var6 = Shaders.getChangedOptions(Shaders.getShaderPackOptions());

				for (final ShaderOption opt : var6) {
					opt.resetValue();
					changed = true;
				}

				updateAllButtons();
			}

			if (guibutton.id == 200) {
				if (changed) {
					Shaders.saveShaderPackOptions();
					Shaders.uninit();
				}

				mc.displayGuiScreen(prevScreen);
			}
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 1) {
			final GuiButton btn = getSelectedButton(mouseX, mouseY);

			if (btn instanceof GuiButtonShaderOption) {
				final GuiButtonShaderOption btnSo = (GuiButtonShaderOption) btn;
				final ShaderOption so = btnSo.getShaderOption();

				if (so.isChanged()) {
					btnSo.playPressSound(mc.getSoundHandler());
					so.resetValue();
					changed = true;
					updateAllButtons();
				}
			}
		}
	}

	private void updateAllButtons() {
		final Iterator it = buttonList.iterator();

		while (it.hasNext()) {
			final GuiButton btn = (GuiButton) it.next();

			if (btn instanceof GuiButtonShaderOption) {
				final GuiButtonShaderOption gbso = (GuiButtonShaderOption) btn;
				final ShaderOption opt = gbso.getShaderOption();

				if (opt instanceof ShaderOptionProfile) {
					final ShaderOptionProfile optProf = (ShaderOptionProfile) opt;
					optProf.updateProfile();
				}

				gbso.displayString = getButtonText(opt, gbso.getButtonWidth());
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int x, final int y, final float f) {
		drawDefaultBackground(x, y);

		if (screenText != null) {
			drawCenteredString(fontRendererObj, screenText, width / 2, 15, 16777215);
		} else {
			drawCenteredString(fontRendererObj, title, width / 2, 15, 16777215);
		}

		super.drawScreen(x, y, f);

		if (Math.abs(x - lastMouseX) <= 5 && Math.abs(y - lastMouseY) <= 5) {
			drawTooltips(x, y, buttonList);
		} else {
			lastMouseX = x;
			lastMouseY = y;
			mouseStillTime = System.currentTimeMillis();
		}
	}

	private void drawTooltips(final int x, final int y, final List buttonList) {
		final short activateDelay = 700;

		if (System.currentTimeMillis() >= mouseStillTime + activateDelay) {
			final int x1 = width / 2 - 150;
			int y1 = height / 6 - 7;

			if (y <= y1 + 98) {
				y1 += 105;
			}

			final int x2 = x1 + 150 + 150;
			final int y2 = y1 + 84 + 10;
			final GuiButton btn = getSelectedButton(x, y);

			if (btn instanceof GuiButtonShaderOption) {
				final GuiButtonShaderOption btnSo = (GuiButtonShaderOption) btn;
				final ShaderOption so = btnSo.getShaderOption();
				final String[] lines = this.makeTooltipLines(so, x2 - x1);

				if (lines == null) {
					return;
				}

				drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);

				for (int i = 0; i < lines.length; ++i) {
					final String line = lines[i];
					int col = 14540253;

					if (line.endsWith("!")) {
						col = 16719904;
					}

					fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, col);
				}
			}
		}
	}

	private String[] makeTooltipLines(final ShaderOption so, final int width) {
		if (so instanceof ShaderOptionProfile) {
			return null;
		} else {
			final String name = so.getNameText();
			final String desc = Config.normalize(so.getDescriptionText()).trim();
			final String[] descs = splitDescription(desc);
			String id = null;

			if (!name.equals(so.getName())) {
				id = Lang.get("of.general.id") + ": " + so.getName();
			}

			String source = null;

			if (so.getPaths() != null) {
				source = Lang.get("of.general.from") + ": " + Config.arrayToString(so.getPaths());
			}

			String def = null;

			if (so.getValueDefault() != null) {
				final String list = so.isEnabled() ? so.getValueText(so.getValueDefault())
						: Lang.get("of.general.ambiguous");
				def = Lang.getDefault() + ": " + list;
			}

			final ArrayList list1 = new ArrayList();
			list1.add(name);
			list1.addAll(Arrays.asList(descs));

			if (id != null) {
				list1.add(id);
			}

			if (source != null) {
				list1.add(source);
			}

			if (def != null) {
				list1.add(def);
			}

			final String[] lines = this.makeTooltipLines(width, list1);
			return lines;
		}
	}

	private String[] splitDescription(String desc) {
		if (desc.length() <= 0) {
			return new String[0];
		} else {
			desc = StrUtils.removePrefix(desc, "//");
			final String[] descs = desc.split("\\. ");

			for (int i = 0; i < descs.length; ++i) {
				descs[i] = "- " + descs[i].trim();
				descs[i] = StrUtils.removeSuffix(descs[i], ".");
			}

			return descs;
		}
	}

	private String[] makeTooltipLines(final int width, final List<String> args) {
		final FontRenderer fr = Config.getMinecraft().fontRendererObj;
		final ArrayList list = new ArrayList();

		for (int lines = 0; lines < args.size(); ++lines) {
			final String arg = args.get(lines);

			if (arg != null && arg.length() > 0) {
				final List parts = fr.listFormattedStringToWidth(arg, width);
				final Iterator it = parts.iterator();

				while (it.hasNext()) {
					final String part = (String) it.next();
					list.add(part);
				}
			}
		}

		final String[] var10 = (String[]) list.toArray(new String[list.size()]);
		return var10;
	}

	private GuiButton getSelectedButton(final int x, final int y) {
		for (int i = 0; i < buttonList.size(); ++i) {
			final GuiButton btn = (GuiButton) buttonList.get(i);
			final int btnWidth = GuiVideoSettings.getButtonWidth(btn);
			final int btnHeight = GuiVideoSettings.getButtonHeight(btn);

			if (x >= btn.xPosition && y >= btn.yPosition && x < btn.xPosition + btnWidth
					&& y < btn.yPosition + btnHeight) {
				return btn;
			}
		}

		return null;
	}
}
