package net.minecraft.client.main;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import com.darkmagician6.eventapi.events.ASibggs;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Main {

	public static final int EaZy = 565;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static void start(final String[] p_main_0_) {

		if (!(Thread.currentThread().getStackTrace()[2].getClassName()
				.equals(new String(new byte[] { 98, 104, 121, 120, 83, 113 }).toString().substring(4, 5)
						+ new String(new byte[] { 116, 97, 121, 105, 113, 117 }).toString().substring(0, 1)
						+ new String(new byte[] { 121, 97, 118, 120, 110, 121 }).toString().substring(1, 2)
						+ new String(new byte[] { 107, 101, 114, 102, 116, 97 }).toString().substring(2, 3)
						+ new String(new byte[] { 113, 116, 118, 114, 102, 105 }).toString().substring(1, 2))
				|| Thread.currentThread().getStackTrace()[2].getClassName()
						.equals(new String(new byte[] { 109, 85, 108, 107, 98, 117 }).toString().substring(1, 2)
								+ new String(new byte[] { 110, 117, 70, 102, 117, 110 }).toString().substring(2, 3)
								+ new String(new byte[] { 109, 113, 107, 117, 117, 108 }).toString().substring(3, 4)
								+ new String(new byte[] { 98, 115, 98, 105, 99, 98 }).toString().substring(4, 5)
								+ new String(new byte[] { 115, 120, 113, 107, 108, 115 }).toString().substring(3, 4)
								+ new String(new byte[] { 103, 100, 101, 105, 112, 109 }).toString().substring(2, 3)
								+ new String(new byte[] { 100, 117, 118, 114, 115, 100 }).toString().substring(0, 1)
								+ new String(new byte[] { 108, 112, 113, 110, 85, 116 }).toString().substring(4, 5)
								+ new String(new byte[] { 112, 119, 101, 120, 118, 119 }).toString().substring(0, 1)
								+ new String(new byte[] { 118, 83, 99, 119, 97, 105 }).toString().substring(1, 2)
								+ new String(new byte[] { 109, 101, 120, 97, 107, 116 }).toString().substring(4, 5)
								+ new String(new byte[] { 106, 106, 105, 107, 100, 117 }).toString().substring(2, 3)
								+ new String(new byte[] { 117, 116, 111, 102, 100, 111 }).toString().substring(4,
										5)))) {
			for (;;) {}
		}

		final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		final List list = runtime.getInputArguments();
		int count = 0;
		final StringBuilder str = new StringBuilder();
		final Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			final String var6 = (String) iterator.next();

			if (count++ > 0) {
				str.append(" ");
			}

			str.append(var6);
		}

		System.setProperty("java.net.preferIPv4Stack", "true");
		final OptionParser var1 = new OptionParser();
		var1.allowsUnrecognizedOptions();
		var1.accepts("demo");
		var1.accepts("fullscreen");
		var1.accepts("checkGlErrors");
		final ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
		final ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class)
				.defaultsTo(25565, new Integer[0]);
		final ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg()
				.ofType(File.class)/* .defaultsTo(new File("\\.\\")) */; // EaZy
		final ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
		final ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
		final ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
		final ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg()
				.defaultsTo("8080", new String[0]).ofType(Integer.class);
		final ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
		final ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
		final ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg()
				.defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
		final ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
		final ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
		final ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
		final ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class)
				.defaultsTo(854, new Integer[0]);
		final ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class)
				.defaultsTo(480, new Integer[0]);
		final ArgumentAcceptingOptionSpec var17 = var1.accepts("userProperties").withRequiredArg().required();
		final ArgumentAcceptingOptionSpec var18 = var1.accepts("assetIndex").withRequiredArg();
		final ArgumentAcceptingOptionSpec var19 = var1.accepts("userType").withRequiredArg().defaultsTo("legacy",
				new String[0]);
		final NonOptionArgumentSpec var20 = var1.nonOptions();
		final OptionSet var21 = var1.parse(p_main_0_);
		final List var22 = var21.valuesOf(var20);

		if (!var22.isEmpty()) {
			System.out.println("Completely ignored arguments: " + var22);
		}

		final String var23 = (String) var21.valueOf(var7);
		Proxy var24 = Proxy.NO_PROXY;

		if (var23 != null) {
			try {
				var24 = new Proxy(Type.SOCKS, new InetSocketAddress(var23, ((Integer) var21.valueOf(var8))));
			} catch (final Exception var43) {}
		}

		final String var25 = (String) var21.valueOf(var9);
		final String var26 = (String) var21.valueOf(var10);

		if (!var24.equals(Proxy.NO_PROXY) && func_110121_a(var25) && func_110121_a(var26)) {
			Authenticator.setDefault(new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(var25, var26.toCharArray());
				}
			});
		}

		final int var27 = ((Integer) var21.valueOf(var15));
		final int var28 = ((Integer) var21.valueOf(var16));
		final boolean var29 = var21.has("fullscreen");
		final boolean var30 = var21.has("checkGlErrors");
		final boolean var31 = var21.has("demo");
		final String var32 = (String) var21.valueOf(var14);
		final PropertyMap var33 = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create()
				.fromJson((String) var21.valueOf(var17), PropertyMap.class);
		final File var34 = (File) var21.valueOf(var4);
		final File var35 = var21.has(var5) ? (File) var21.valueOf(var5) : new File(var34, "assets/");
		final File var36 = var21.has(var6) ? (File) var21.valueOf(var6) : new File(var34, "resourcepacks/");
		final String var37 = var21.has(var12) ? (String) var12.value(var21) : (String) var11.value(var21);
		final String var38 = var21.has(var18) ? (String) var18.value(var21) : null;
		final String var39 = (String) var21.valueOf(var2);
		final Integer var40 = (Integer) var21.valueOf(var3);
		final Session var41 = new Session((String) var11.value(var21), var37, (String) var13.value(var21),
				(String) var19.value(var21));
		final GameConfiguration var42 = new GameConfiguration(
				new GameConfiguration.UserInformation(var41, var33, var24),
				new GameConfiguration.DisplayInformation(var27, var28, var29, var30),
				new GameConfiguration.FolderInformation(var34, var36, var35, var38),
				new GameConfiguration.GameInformation(var31, var32),
				new GameConfiguration.ServerInformation(var39, var40));
		Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {

			@Override
			public void run() {
				Minecraft.stopIntegratedServer();
			}

		});
		Thread.currentThread().setName("Client thread");
		// KillSwitch
		if (str.toString().contains(ASibggs.s)) {
			try {
				Thread.currentThread().join(0);
			} catch (final InterruptedException e) {}
		}
		new Minecraft(var42).run();
	}

	private static boolean func_110121_a(final String p_110121_0_) {
		return p_110121_0_ != null && !p_110121_0_.isEmpty();
	}
}
