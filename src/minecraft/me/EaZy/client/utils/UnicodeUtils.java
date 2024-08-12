package me.EaZy.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtils {
	public static final int EaZy = 2055;

	public static String getUnicodeText(String s) {
		final Pattern pattern = Pattern.compile("(\\\\u[0-9a-fA-F]{4})");
		final Matcher matcher = pattern.matcher(s);
		final StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, getUnicode(matcher.group()));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static String getUnicode(String s) {
		if (s.startsWith("\\u")) {
			s = s.substring(2);
		}
		return new String(new char[] { (char) Integer.parseInt(s, 16) });
	}
}
