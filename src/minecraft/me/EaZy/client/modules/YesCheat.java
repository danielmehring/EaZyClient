package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.gui.GuiMainMenu;

public class YesCheat extends Module {

	public static YesCheat mod;

	public static final int EaZy = 197;

	@Override
	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static boolean enabled = false;
	public static Mode mode = Mode.ALL;

	private static final ArrayList<String> modes = new ArrayList();

	private String suffix;

	public YesCheat() {
		super("YesCheat", 45, "bypass", Category.OTHER, "ByPass AntiCheats.");
		modes.add("ALL");
		modes.add("NCP");
		modes.add("AAC");
		modes.add("Gomme");

		Client.setmgr.rSetting(new Setting("Mode", this, "ALL", modes));
		mod = this;
	}

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "ProBetrügen [" + mode.name() + "]";
			} else {
				suffix = "ProBetrügen";
			}
		} else {

			if (Configs.suffix) {
				suffix = "YesCheat [" + mode.name() + "]";
			} else {
				suffix = "YesCheat";
			}

		}

		return suffix;
	}

	@Override
	public void onDisable() {
		enabled = false;
		super.onDisable();
	}

	@Override
	public void onEnable() {
		updateMode();
		enabled = true;
		super.onEnable();
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

	public static void updateMode() {
		if (Client.setmgr.getSettingByName(YesCheat.mod, "Mode").getValString().equalsIgnoreCase("ALL")) {
			mode = Mode.valueOf("ALL");
		} else if (Client.setmgr.getSettingByName(YesCheat.mod, "Mode").getValString().equalsIgnoreCase("AAC")) {
			mode = Mode.valueOf("AAC");
		} else if (Client.setmgr.getSettingByName(YesCheat.mod, "Mode").getValString().equalsIgnoreCase("NCP")) {
			mode = Mode.valueOf("NCP");
		} else if (Client.setmgr.getSettingByName(YesCheat.mod, "Mode").getValString().equalsIgnoreCase("Gomme")) {
			mode = Mode.valueOf("Gomme");
		} else {
			mode = Mode.valueOf("ALL");
		}
	}

	public static enum Mode {
		ALL, AAC, NCP, Gomme;
	}

}
