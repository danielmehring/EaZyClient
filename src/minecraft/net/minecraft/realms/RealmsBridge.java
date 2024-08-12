package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBridge extends RealmsScreen {

public static final int EaZy = 1503;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger LOGGER = LogManager.getLogger();
	private GuiScreen previousScreen;
	// private static final String __OBFID = "http://https://fuckuskid00001869";

	public void switchToRealms(final GuiScreen p_switchToRealms_1_) {
		previousScreen = p_switchToRealms_1_;

		try {
			final Class var2 = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
			final Constructor var3 = var2.getDeclaredConstructor(new Class[] { RealmsScreen.class });
			var3.setAccessible(true);
			final Object var4 = var3.newInstance(new Object[] { this });
			Minecraft.getMinecraft().displayGuiScreen(((RealmsScreen) var4).getProxy());
		} catch (final Exception var5) {
			LOGGER.error("Realms module missing", var5);
		}
	}

	@Override
	public void init() {
		Minecraft.getMinecraft().displayGuiScreen(previousScreen);
	}
}
