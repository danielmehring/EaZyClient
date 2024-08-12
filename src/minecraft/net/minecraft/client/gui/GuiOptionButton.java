package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettings;

public class GuiOptionButton extends GuiButton {

public static final int EaZy = 492;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GameSettings.Options enumOptions;
	// private static final String __OBFID = "http://https://fuckuskid00000676";

	public GuiOptionButton(final int p_i45011_1_, final int p_i45011_2_, final int p_i45011_3_,
			final String p_i45011_4_) {
		this(p_i45011_1_, p_i45011_2_, p_i45011_3_, (GameSettings.Options) null, p_i45011_4_);
	}

	public GuiOptionButton(final int p_i45012_1_, final int p_i45012_2_, final int p_i45012_3_, final int p_i45012_4_,
			final int p_i45012_5_, final String p_i45012_6_) {
		super(p_i45012_1_, p_i45012_2_, p_i45012_3_, p_i45012_4_, p_i45012_5_, p_i45012_6_);
		enumOptions = null;
	}

	public GuiOptionButton(final int p_i45013_1_, final int p_i45013_2_, final int p_i45013_3_,
			final GameSettings.Options p_i45013_4_, final String p_i45013_5_) {
		super(p_i45013_1_, p_i45013_2_, p_i45013_3_, 150, 20, p_i45013_5_);
		enumOptions = p_i45013_4_;
	}

	public GameSettings.Options returnEnumOptions() {
		return enumOptions;
	}
}
