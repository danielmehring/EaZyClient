package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.MiscUtils;
import net.minecraft.client.gui.GuiMainMenu;

public class Speed extends Module {

	public static Speed mod;

	public static final int EaZy = 162;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static ArrayList<String> modes = new ArrayList();
	private static String renderName = "NCPRace";

	private String suffix;

	public Speed() {
		super("Speed", 24, "", Category.MOVEMENT);
		Client.getModules(Category.SPEED).forEach((m) -> {
			modes.add(m.getName());
		});

		Client.setmgr.rSetting(new Setting("Mode", this, "NCPRace", modes));
		mod = this;
	}

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "GeschwindigkeitsErhöherer [" + renderName + "]";
			} else {
				suffix = "GeschwindigkeitsErhöherer";
			}
		} else {

			if (Configs.suffix) {
				suffix = "Speed [" + renderName + "]";
			} else {
				suffix = "Speed";
			}

		}
		return suffix;
	}

	public static void updateMode() {

		if (!Speed.mod.isToggled()) {
			return;
		}

		try {

			final String s = Client.setmgr.getSettingByName(Speed.mod, "Mode").getValString();

			String real = MiscUtils.getRealStringFromArrayList(s, modes);
			if (Client.existsSpeed(real)) {
				Client.disable(Category.SPEED);
				Client.enable(real);
				renderName = real;
			} else {
				Client.disable(Category.SPEED);
				Client.enable("MySpeed");
				renderName = "MySpeed";
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable() {
		Speed.updateMode();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		Client.disable(Category.SPEED);
		super.onDisable();
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
