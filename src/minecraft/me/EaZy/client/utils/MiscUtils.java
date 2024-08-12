/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.input.Keyboard
 */
package me.EaZy.client.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;

import me.EaZy.client.main.Client;

public class MiscUtils {

	public static final int EaZy = 225;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static String getRealStringFromArrayList(String s, ArrayList<String> arr) {
		int i = 0;
		for (String asd : arr) {
			if (asd.equalsIgnoreCase(s)) {
				return arr.get(i);
			}
			i++;
		}
		return null;
	}

	public static boolean openLink(final String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
			return true;
		} catch (final Exception e) {
			Client.getLogger().error("Failed to open link", e);
			return false;
		}
	}

	public static boolean isInteger(final String str) {
		try {
			Integer.parseInt(str);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isLong(final String str) {
		try {
			Long.parseLong(str);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isBoolean(final String str) {
		try {
			Boolean.parseBoolean(str.toLowerCase());
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(final String str) {
		try {
			Double.parseDouble(str);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isFloat(final String str) {
		try {
			Float.parseFloat(str);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}

}
