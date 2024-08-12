package net.minecraft.realms;

import net.minecraft.util.IChatComponent;

import java.util.Iterator;
import java.util.List;

public class DisconnectedRealmsScreen extends RealmsScreen {

public static final int EaZy = 1500;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String title;
	private final IChatComponent reason;
	private List lines;
	private final RealmsScreen parent;
	// private static final String __OBFID = "http://https://fuckuskid00002145";

	public DisconnectedRealmsScreen(final RealmsScreen p_i45742_1_, final String p_i45742_2_,
			final IChatComponent p_i45742_3_) {
		parent = p_i45742_1_;
		title = getLocalizedString(p_i45742_2_);
		reason = p_i45742_3_;
	}

	@Override
	public void init() {
		buttonsClear();
		buttonsAdd(newButton(0, width() / 2 - 100, height() / 4 + 120 + 12, getLocalizedString("gui.back")));
		lines = fontSplit(reason.getFormattedText(), width() - 50);
	}

	@Override
	public void keyPressed(final char p_keyPressed_1_, final int p_keyPressed_2_) {
		if (p_keyPressed_2_ == 1) {
			Realms.setScreen(parent);
		}
	}

	@Override
	public void buttonClicked(final RealmsButton p_buttonClicked_1_) {
		if (p_buttonClicked_1_.id() == 0) {
			Realms.setScreen(parent);
		}
	}

	@Override
	public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
		this.renderBackground(p_render_1_, p_render_2_);
		drawCenteredString(title, width() / 2, height() / 2 - 50, 11184810);
		int var4 = height() / 2 - 30;

		if (lines != null) {
			for (final Iterator var5 = lines.iterator(); var5.hasNext(); var4 += fontLineHeight()) {
				final String var6 = (String) var5.next();
				drawCenteredString(var6, width() / 2, var4, 16777215);
			}
		}

		super.render(p_render_1_, p_render_2_, p_render_3_);
	}
}
