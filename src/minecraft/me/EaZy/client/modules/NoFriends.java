package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class NoFriends extends Module {

	public static NoFriends mod;

	public static final int EaZy = 144;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public NoFriends() {
		super("NoFriends", 0, "", Category.OTHER, "Allows KillAura,...\nto hit friends.");
		mod = this;
	}

	@Override
	public void onUpdate() {
		if (!isToggled()) {
			if (togglecmd) {
				setToggled(true);
				onEnable();
			}
			return;
		}
		if (isToggled() && !togglecmd) {
			setToggled(false);
			onDisable();
			return;
		}

		super.onUpdate();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "KeineFreunde";
		} else {
			return super.getRenderName();
		}
	}
}
