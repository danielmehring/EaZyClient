package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

import java.util.List;

import com.google.common.base.Objects;

public class SpectatorDetails {

public static final int EaZy = 551;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List field_178682_b;
	private final int field_178683_c;
	// private static final String __OBFID = "http://https://fuckuskid00001923";

	public SpectatorDetails(final ISpectatorMenuView p_i45494_1_, final List p_i45494_2_, final int p_i45494_3_) {
		field_178682_b = p_i45494_2_;
		field_178683_c = p_i45494_3_;
	}

	public ISpectatorMenuObject func_178680_a(final int p_178680_1_) {
		return p_178680_1_ >= 0 && p_178680_1_ < field_178682_b.size() ? (ISpectatorMenuObject) Objects
				.firstNonNull(field_178682_b.get(p_178680_1_), SpectatorMenu.field_178657_a)
				: SpectatorMenu.field_178657_a;
	}

	public int func_178681_b() {
		return field_178683_c;
	}
}
