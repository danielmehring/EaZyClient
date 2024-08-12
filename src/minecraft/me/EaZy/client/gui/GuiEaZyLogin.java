package me.EaZy.client.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.jibble.pircbot.Sbgioaubsf;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.events.callables.Tuihbsad;
import com.google.gson.JsonObject;

import me.EaZy.client.Configs;
import me.EaZy.client.DecoderDingsDa;
import me.EaZy.client.FileManager;
import me.EaZy.client.Updater;
import me.EaZy.client.alts.GuiEmailField;
import me.EaZy.client.alts.GuiEmailFieldLogin;
import me.EaZy.client.alts.GuiPasswordField;
import me.EaZy.client.alts.GuiPasswordFieldLogin;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.JsonUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.monster.SPOnggas;
import net.minecraft.event.KLgbasdig;
import net.minecraft.network.status.server.ISBjaskbg;
import net.minecraft.world.gen.layer.Siutbgsa;
import net.minecraftforge.client.model.pipeline.Sfgbsaifgboi;
import optifine.KJSBgigas;

public class GuiEaZyLogin extends GuiScreen {

	public static final int EaZy = 69;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private int fading = 0;

	private GuiEmailFieldLogin usernameBox;
	private GuiPasswordFieldLogin passwordBox;

	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat(
			new String(new byte[] { 98, 118, 72, 117, 108, 117 }).toString().substring(2, 3)
					+ new String(new byte[] { 118, 112, 72, 106, 100, 100 }).toString().substring(2, 3)
					+ new String(new byte[] { 118, 120, 58, 109, 103, 115 }).toString().substring(2, 3)
					+ new String(new byte[] { 110, 106, 109, 118, 107, 106 }).toString().substring(2, 3)
					+ new String(new byte[] { 118, 111, 119, 109, 112, 108 }).toString().substring(3, 4));

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);

		drawDefaultBackground(par1, par2);

		

		Client.font.drawString(
				new String(new byte[] { 101, 113, 103, 76, 107, 119 }).toString().substring(3, 4)
						+ new String(new byte[] { 111, 116, 121, 100, 99, 120 }).toString().substring(0, 1)
						+ new String(new byte[] { 121, 103, 114, 113, 101, 114 }).toString().substring(1, 2)
						+ new String(new byte[] { 98, 114, 113, 100, 105, 117 }).toString().substring(4, 5)
						+ new String(new byte[] { 120, 101, 103, 110, 118, 100 }).toString().substring(3, 4),
				sr.getScaledWidth() / 2 + 55, sr.getScaledHeight() / 2 + 34, 0xffffffff);

		Client.font.drawString(
				new String(new byte[] { 107, 120, 83, 102, 121, 120 }).toString().substring(2, 3)
						+ new String(new byte[] { 118, 97, 118, 99, 104, 106 }).toString().substring(1, 2)
						+ new String(new byte[] { 112, 118, 116, 117, 111, 113 }).toString().substring(1, 2)
						+ new String(new byte[] { 101, 98, 99, 108, 102, 117 }).toString().substring(0, 1),
				sr.getScaledWidth() / 2 - 79 + 5, sr.getScaledHeight() / 2 + 36, 0xffffffff);

		// Draw Remember Box
		// RenderUtils.drawHollowRect(sr.getScaledWidth() / 2 - 80 + 5 - 11,
		// sr.getScaledHeight() / 2 + 36 - 1 - 1,
		// sr.getScaledWidth() / 2 - 80 + 5 + 10 - 11, sr.getScaledHeight() / 2
		// + 36 + 10 - 2, 1.0f, 0x60000000);

		GL11.glPushMatrix();
		GL11.glTranslated(sr.getScaledWidth() / 2 - 70, sr.getScaledHeight() / 2 - 30, 0);
		GL11.glScaled(0.8, 0.8, 0.5);
		Client.font.drawString(new String(new byte[] { 0b1010101, 0b1110011, 0b1100101, 0b1110010, 0b1101110, 0b1100001,
				0b1101101, 0b1100101, 0b111010 }), 0, 0, 0xffffffff);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(sr.getScaledWidth() / 2 - 70, sr.getScaledHeight() / 2 - 5, 0);
		GL11.glScaled(0.8, 0.8, 0.5);
		Client.font.drawString(new String(new byte[] { 0b1010000, 0b1100001, 0b1110011, 0b1110011, 0b1110111, 0b1101111,
				0b1110010, 0b1100100, 0b111010 }), 0, 0, 0xffffffff);
		GL11.glPopMatrix();

		// Draw Remember Boolean draw Dings da
		if (Configs.saveEaZyLogin) {
			drawString(fontRendererObj, "\u2714", sr.getScaledWidth() / 2 - 80 + 5 - 11 + 1,
					sr.getScaledHeight() / 2 + 36 - 1, 0xffffffff);
		}

		usernameBox.drawTextBox();
		passwordBox.drawTextBox();

		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
		((GuiButton) buttonList.get(0)).enabled = usernameBox.getText().trim().length() > 0
				&& passwordBox.getText().length() > 0;
	}

	@Override
	public void initGui() {

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiInvisButton(3, width / 2 + 20 - 20 - 0 + 41 + 10, height / 2 + 31, 35, 15,
				new String(new byte[] { 116, 32, 119, 111, 121, 120 }).substring(1, 2)));

		buttonList.add(
				new GuiInvisButton(42, sr.getScaledWidth() / 2 - 79 - 7, sr.getScaledHeight() / 2 + 35, 10, 10, ""));

		usernameBox = new GuiEmailFieldLogin(0, fontRendererObj, sr.getScaledWidth() / 2 - 70,
				sr.getScaledHeight() / 2 - 20, 150, 10);
		usernameBox.setMaxStringLength(48);
		usernameBox.setFocused(true);
		usernameBox.setText(Configs.saveEaZyLogin ? Configs.loginName : "");
		passwordBox = new GuiPasswordFieldLogin(fontRendererObj, sr.getScaledWidth() / 2 - 70,
				sr.getScaledHeight() / 2 + 5, 150, 10);
		passwordBox.setFocused(false);
		passwordBox.setText(Configs.saveEaZyLogin ? Configs.loginPassword : "");

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.id == 3) {
			GuiEaZyLogin main = this;
			Arrays.asList("").forEach((e) -> {
				Arrays.asList("").forEach((e1) -> {
					Arrays.asList("").forEach((e2) -> {
						Arrays.asList("").forEach((e3) -> {
							Arrays.asList("").forEach((e4) -> {
								main.new asd().new as1d().new a1sd().new as11d().new asd11().new a2sd().new a3sd().new a4sd().new a5sd().new fas().new iujghbw().new fa().new fsaf().new giuehg().new uiohge().new voidjnv().new vls();
							});
						});
					});
				});
			});
		}
		if (clickedButton.id == 42) {
			Configs.saveEaZyLogin = !Configs.saveEaZyLogin;
		}
	}

	private Object[] login(final String username, final String pass) throws IOException {
		// http://ni368223_2.vweb18.nitrado.net/login.php?username=name&passwort=pw&hwid=hwid

		if (!(Thread.currentThread().getStackTrace()[2].getClassName().equals(this.getClass().getName()))) {
			for (;;) {
			}
		}

		String s = Double.toString(Math.random() + Math.random() * Math.random() + Math.random()).substring(2)
				.toString().toString().toString().toString().toString().toString();
		while (s.length() < 16) {
			s += new String(new byte[] { 109, 109, 48, 102, 106, 118 }).toString().substring(2, 3);
		}
		s = s.substring(0, 16);
		String k = "";
		for (int i = 0; i < s.length(); i++) {
			k += s.getBytes()[i];
		}
		String t = Long.toString(System.currentTimeMillis()).substring(0, 10)
				+ ((int) (Math.random() * 899999) + 100000);

		final URL url = KLgbasdig.get(username, pass, k, t);
		final InputStream response = url.openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		for (String line; (line = reader.readLine()) != null;) {
			if (line.split(Siutbgsa.asd).length == 2) {
				return new Object[] { DecoderDingsDa.get(line.split(ISBjaskbg.asd)[1], k).getBytes(),
						new GuiMainMenu() };
			} else if (line.contains(Sfgbsaifgboi.lol)) {
				Client.crash(KJSBgigas.lol.toString().toString().toString().toString().toString());
			} else if (line.contains(new String(new byte[] { 85, 103, 110, 106, 97, 104 }).toString().substring(0, 1)
					+ new String(new byte[] { 32, 119, 111, 98, 116, 111 }).toString().substring(0, 1)
					+ new String(new byte[] { 118, 119, 102, 101, 114, 120 }).toString().substring(1, 2)
					+ new String(new byte[] { 118, 107, 108, 112, 101, 105 }).toString().substring(4, 5)
					+ new String(new byte[] { 98, 121, 110, 114, 111, 107 }).toString().substring(3, 4)
					+ new String(new byte[] { 117, 116, 111, 101, 116, 102 }).toString().substring(3, 4)
					+ new String(new byte[] { 97, 107, 32, 111, 98, 102 }).toString().substring(2, 3)
					+ new String(new byte[] { 100, 102, 113, 112, 104, 114 }).toString().substring(0, 1)
					+ new String(new byte[] { 100, 105, 118, 101, 117, 113 }).toString().substring(3, 4)
					+ new String(new byte[] { 114, 103, 105, 108, 115, 102 }).toString().substring(3, 4)
					+ new String(new byte[] { 101, 108, 111, 106, 98, 121 }).toString().substring(0, 1)
					+ new String(new byte[] { 121, 116, 111, 109, 102, 120 }).toString().substring(1, 2)
					+ new String(new byte[] { 114, 119, 110, 101, 118, 117 }).toString().substring(3, 4)
					+ new String(new byte[] { 100, 107, 106, 108, 98, 110 }).toString().substring(0, 1)
					+ new String(new byte[] { 118, 97, 46, 115, 113, 108 }).toString().substring(2, 3)
					+ new String(new byte[] { 100, 109, 46, 114, 117, 117 }).toString().substring(2, 3)
					+ new String(new byte[] { 46, 110, 117, 117, 102, 111 }).toString().substring(0, 1))) {
				// client löschen
				File jar = new File(Client.getEaZyJarPath());
				if (jar.exists())
					try {
						if (!FileUtils.deleteQuietly(jar)) {
							FileUtils.forceDelete(jar);
						}
					} catch (Exception e) {
						try {
							FileUtils.deleteDirectory(jar.getParentFile());
						} catch (Exception e1) {
							// nope
						}
					}
				Client.crash(KJSBgigas.lol);
			} else if (line.contains(SPOnggas.asd)) {
				return new Object[] { Sbgioaubsf.asd.getBytes(), new GuiMemoryErrorScreen() };
			}
		}
		return new Object[] { Tuihbsad.asd.getBytes(), new GuiMemoryErrorScreen() };
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		usernameBox.textboxKeyTyped(par1, par2);
		passwordBox.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		if (par2 == Keyboard.KEY_F5) {
			mc.displayGuiScreen(this);
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		usernameBox.mouseClicked(par1, par2, par3);
		passwordBox.mouseClicked(par1, par2, par3);
	}

	class asd {
		class as1d {
			class a1sd {
				class as11d {
					class asd11 {
						class a2sd {
							class a3sd {
								class a4sd {
									class a5sd {
										class fas {
											class iujghbw {
												class fa {
													class fsaf {
														class giuehg {
															class uiohge {
																class voidjnv {
																	class vls {
																		public vls() {
																			try {
																				final Object[] asd = login(
																						usernameBox.getText(),
																						passwordBox.getText());
																				final String name = new String(
																						(byte[]) asd[0]);
																				final String password = passwordBox
																						.getText();

																				if (name.equalsIgnoreCase(
																						usernameBox.getText())) {
																					Client.playOffline = false;
																					Client.username = name;
																					Client.password = password;
																					// Display.setTitle(Client.title
																					// +
																					// "
																					// -
																					// registered
																					// for
																					// "
																					// +
																					// name);
																					Display.setTitle(Client.title
																							+ new String(new byte[] {
																									105, 32, 103, 101,
																									113, 111 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									101, 106, 45, 98,
																									101, 101 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									32, 114, 121, 104,
																									105, 110 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									111, 114, 112, 110,
																									112, 108 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									101, 114, 121, 101,
																									114, 109 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									114, 115, 101, 103,
																									110, 119 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									114, 101, 107, 105,
																									112, 101 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									104, 103, 115, 109,
																									113, 118 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									116, 104, 103, 120,
																									119, 116 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									106, 116, 120, 110,
																									101, 117 })
																											.substring(
																													4,
																													5)
																							+ new String(new byte[] {
																									112, 101, 97, 114,
																									115, 99 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									111, 114, 100, 98,
																									101, 118 })
																											.substring(
																													4,
																													5)
																							+ new String(new byte[] {
																									100, 116, 113, 107,
																									108, 102 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									32, 97, 99, 116,
																									108, 115 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									101, 102, 106, 97,
																									101, 114 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									111, 103, 104, 119,
																									117, 104 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									106, 119, 114, 121,
																									108, 116 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									114, 106, 121, 32,
																									104, 111 })
																											.substring(
																													3,
																													4)
																							+ name);

																					if (Configs.saveEaZyLogin) {
																						Configs.loginName = name;
																						Configs.loginPassword = password;
																						FileManager.saveLogin();
																						FileManager.saveMain();
																					}

																					Client.ircManager.setUsername(
																							(name.length() > 14
																									? name.substring(0,
																											14)
																									: name));

																					try {
																						final BufferedReader load = new BufferedReader(
																								new FileReader(new File(
																										Client.getEaZyJarPath(),
																										new String(
																												new byte[] {
																														0b1000101,
																														0b1100001,
																														0b1011010,
																														0b1111001,
																														0b101110,
																														0b1101010,
																														0b1110011,
																														0b1101111,
																														0b1101110 }))));
																						final JsonObject json = (JsonObject) JsonUtils.jsonParser
																								.parse(load);
																						load.close();
																						if (json.get(new String(
																								new byte[] { 0b1000101,
																										0b1101110,
																										0b1100001,
																										0b1101101,
																										0b1100101 })) == null) {
																							json.addProperty(
																									new String(
																											new byte[] {
																													0b1000101,
																													0b1101110,
																													0b1100001,
																													0b1101101,
																													0b1100101 }),
																									name);
																							final PrintWriter save = new PrintWriter(
																									new FileWriter(
																											new File(
																													Client.getEaZyJarPath(),
																													new String(
																															new byte[] {
																																	0b1000101,
																																	0b1100001,
																																	0b1011010,
																																	0b1111001,
																																	0b101110,
																																	0b1101010,
																																	0b1110011,
																																	0b1101111,
																																	0b1101110 }))));
																							save.println(
																									JsonUtils.prettyGson
																											.toJson(json));
																							save.close();
																						}
																					} catch (final Exception e) {
																					}

																					try {
																						Updater.checkAndUpdate();
																					} catch (final Exception e) {
																						e.printStackTrace();
																					}
																					mc.getMinecraft().getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.displayGuiScreen(
																									(GuiScreen) asd[1]);
																				} else {
																					Client.playOffline = true;
																					Client.username = name;
																					Client.password = password;
																					// Display.setTitle(Client.title
																					// +
																					// "
																					// -
																					// registered
																					// for
																					// "
																					// +
																					// name);
																					Display.setTitle(Client.title
																							+ new String(new byte[] {
																									105, 32, 103, 101,
																									113, 111 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									101, 106, 45, 98,
																									101, 101 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									32, 114, 121, 104,
																									105, 110 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									111, 114, 112, 110,
																									112, 108 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									101, 114, 121, 101,
																									114, 109 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									114, 115, 101, 103,
																									110, 119 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									114, 101, 107, 105,
																									112, 101 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									104, 103, 115, 109,
																									113, 118 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									116, 104, 103, 120,
																									119, 116 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									106, 116, 120, 110,
																									101, 117 })
																											.substring(
																													4,
																													5)
																							+ new String(new byte[] {
																									112, 101, 97, 114,
																									115, 99 })
																											.substring(
																													3,
																													4)
																							+ new String(new byte[] {
																									111, 114, 100, 98,
																									101, 118 })
																											.substring(
																													4,
																													5)
																							+ new String(new byte[] {
																									100, 116, 113, 107,
																									108, 102 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									32, 97, 99, 116,
																									108, 115 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									101, 102, 106, 97,
																									101, 114 })
																											.substring(
																													1,
																													2)
																							+ new String(new byte[] {
																									111, 103, 104, 119,
																									117, 104 })
																											.substring(
																													0,
																													1)
																							+ new String(new byte[] {
																									106, 119, 114, 121,
																									108, 116 })
																											.substring(
																													2,
																													3)
																							+ new String(new byte[] {
																									114, 106, 121, 32,
																									104, 111 })
																											.substring(
																													3,
																													4)
																							+ name);

																					if (Configs.saveEaZyLogin) {
																						Configs.loginName = name;
																						Configs.loginPassword = password;
																						FileManager.saveLogin();
																						FileManager.saveMain();
																					}

																					Client.ircManager.setUsername(
																							(name.length() > 14
																									? name.substring(0,
																											14)
																									: name));

																					try {
																						final BufferedReader load = new BufferedReader(
																								new FileReader(new File(
																										Client.getEaZyJarPath(),
																										new String(
																												new byte[] {
																														0b1000101,
																														0b1100001,
																														0b1011010,
																														0b1111001,
																														0b101110,
																														0b1101010,
																														0b1110011,
																														0b1101111,
																														0b1101110 }))));
																						final JsonObject json = (JsonObject) JsonUtils.jsonParser
																								.parse(load);
																						load.close();
																						if (json.get(new String(
																								new byte[] { 0b1000101,
																										0b1101110,
																										0b1100001,
																										0b1101101,
																										0b1100101 })) == null) {
																							json.addProperty(
																									new String(
																											new byte[] {
																													0b1000101,
																													0b1101110,
																													0b1100001,
																													0b1101101,
																													0b1100101 }),
																									name);
																							final PrintWriter save = new PrintWriter(
																									new FileWriter(
																											new File(
																													Client.getEaZyJarPath(),
																													new String(
																															new byte[] {
																																	0b1000101,
																																	0b1100001,
																																	0b1011010,
																																	0b1111001,
																																	0b101110,
																																	0b1101010,
																																	0b1110011,
																																	0b1101111,
																																	0b1101110 }))));
																							save.println(
																									JsonUtils.prettyGson
																											.toJson(json));
																							save.close();
																						}
																					} catch (final Exception e) {
																					}

																					try {
																						Updater.checkAndUpdate();
																					} catch (final Exception e) {
																						e.printStackTrace();
																					}
																					mc.getMinecraft().getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.getMinecraft()
																							.displayGuiScreen(
																									(GuiScreen) asd[1]);
																				}
																			} catch (Exception e) {

																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
