package net.minecraft.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiResourcePackSelected extends GuiResourcePackList {

public static final int EaZy = 503;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000827";

	public GuiResourcePackSelected(final Minecraft mcIn, final int p_i45056_2_, final int p_i45056_3_,
			final List p_i45056_4_) {
		super(mcIn, p_i45056_2_, p_i45056_3_, p_i45056_4_);
	}

	@Override
	protected String getListHeader() {
		return I18n.format("resourcePack.selected.title", new Object[0]);
	}
}
