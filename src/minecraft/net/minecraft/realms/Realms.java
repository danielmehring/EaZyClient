package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;

import java.net.Proxy;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

public class Realms {

public static final int EaZy = 1501;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001892";

	public static boolean isTouchScreen() {

		return Minecraft.gameSettings.touchscreen;
	}

	public static Proxy getProxy() {
		return Minecraft.getMinecraft().getProxy();
	}

	public static String sessionId() {
		final Session var0 = Minecraft.getMinecraft().getSession();
		return var0 == null ? null : var0.getSessionID();
	}

	public static String userName() {
		final Session var0 = Minecraft.getMinecraft().getSession();
		return var0 == null ? null : var0.getUsername();
	}

	public static long currentTimeMillis() {
		return Minecraft.getSystemTime();
	}

	public static String getSessionId() {
		return Minecraft.getMinecraft().getSession().getSessionID();
	}

	public static String getName() {
		return Minecraft.getMinecraft().getSession().getUsername();
	}

	public static String uuidToName(final String p_uuidToName_0_) {
		return Minecraft.getMinecraft().getSessionService().fillProfileProperties(
				new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), (String) null), false).getName();
	}

	public static void setScreen(final RealmsScreen p_setScreen_0_) {
		Minecraft.getMinecraft().displayGuiScreen(p_setScreen_0_.getProxy());
	}

	public static String getGameDirectoryPath() {
		return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
	}

	public static int survivalId() {
		return WorldSettings.GameType.SURVIVAL.getID();
	}

	public static int creativeId() {
		return WorldSettings.GameType.CREATIVE.getID();
	}

	public static int adventureId() {
		return WorldSettings.GameType.ADVENTURE.getID();
	}
}
