package me.EaZy.client.modules.hud;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;

public class Appearance extends Module {

	public static Appearance mod;

	public static final int EaZy = 124;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	public Appearance() {
		super("Appearance", 0, "", Category.HUD, false);
		Client.setmgr.rSetting(new Setting("Red", this, 0f, 0.0f, 255.0f, true));
		Client.setmgr.rSetting(new Setting("Green", this, 0f, 0.0f, 255.0f, true));
		Client.setmgr.rSetting(new Setting("Blue", this, 0f, 0.0f, 255.0f, true));
		Client.setmgr.rSetting(new Setting("Rainbow", this, true));

		// final ArrayList<String> hudthemes = new ArrayList<>();
		// Client.setmgr.rSetting(new Setting("HudThemes", this, "New",
		// hudthemes));
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
}
