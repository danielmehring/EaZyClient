package me.EaZy.client.modules;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;

public class Gui extends Module {

	public static Gui mod;

	public static final int EaZy = 122;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Gui() {
		super("Gui", 54, "", Category.HIDDEN);
		mod = this;
	}

	@Override
	public void onUpdate() {
		if (isToggled() && !(Minecraft.currentScreen instanceof ClickGUI)) {
			setToggled(false);
			onDisable();
		}
	}

	@Override
	public void onEnable() {
		mc.displayGuiScreen(Client.getClickGUI());
		super.onEnable();
	}
}
