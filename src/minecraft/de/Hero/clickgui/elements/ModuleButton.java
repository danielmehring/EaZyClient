package de.Hero.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.menu.ElementCheckBox;
import de.Hero.clickgui.elements.menu.ElementComboBox;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ModuleButton {

	public static final int EaZy = 18;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;

	public double yt;
	public double yf;

	public int visibillllllllllllllllllllllllllllllllfy;
	public int vt;

	public double width;
	public double height;
	public boolean extended = false;
	/* mach einfach nen counter rein */
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(final Module imod, final Panel pl) {
		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();
		/*
		 * ich chesk net Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach verwendetem
		 * Konstruktor ändert sich die Value bei .isCheck() usw. so kann man
		 * ganz einfach ohne irgendeinen Aufwand bestimmen welches Element für
		 * ein Setting benötigt wird :>
		 */
		if (Client.setmgr.getSettingsByMod(imod) != null) {
			Client.setmgr.getSettingsByMod(imod).forEach((s) -> {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					if (!s.getOptions().isEmpty()) {
						menuelements.add(new ElementComboBox(this, s));
					}
				}
			});
		}

	}

	/*
	 * Rendern des Elements
	 */
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		final Color temp = Client.getColor(0l);

		/*
		 * Ist das Module an, wenn ja dann soll #ein neues Rechteck in Größe des
		 * Buttons den Knopf als Toggled kennzeichnen #sich der Text anders
		 * färben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isToggled()) {
			vt = (int) height;
			textcolor = 0xffefefef;
		} else {
			vt = -2;
		}

		if (visibillllllllllllllllllllllllllllllllfy < vt) {
			visibillllllllllllllllllllllllllllllllfy += 1;
		}

		if (visibillllllllllllllllllllllllllllllllfy > vt) {
			visibillllllllllllllllllllllllllllllllfy -= 1;
		}

		if (visibillllllllllllllllllllllllllllllllfy != -2) {
			Gui.drawRect(x - 1, y, x + width + 1, y + height + visibillllllllllllllllllllllllllllllllfy - height,
					Configs.useClientColor ? ColorUtils.transparency(temp.getRGB(), 0.35)
							: new Color(223, 223, 223, 63).getRGB());
		}

		/*
		 * Ist die Maus über dem Element, wenn ja dann soll der Button sich
		 * anders färben
		 */
		if (isHovered(mouseX, mouseY)) {
			Gui.drawRect((int) x - 1, (int) y, (int) x + (int) width + 1, (int) y + (int) height,
					new Color(17, 17, 17, 32).getRGB());
		}

		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
		FontUtil.drawTotalCenteredString(mod.getName(), (int) x + (int) width / 2, (int) y + 1 + (int) height / 2,
				textcolor);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen
	 * alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		if (!isHovered(mouseX, mouseY)) {
			return false;
		}

		/*
		 * Rechtsklick, wenn ja dann Module togglen,
		 */
		if (mouseButton == 0) {
			Client.toggle(mod.getName());
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus
			 * geschlossen werden und dieses geöffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				final boolean b = !extended;
				Client.clickgui.closeAllSettings();
				extended = b;
			}
		} else if (mouseButton == 2) {
			/*
			 * MidClick => Set keybind (wait for next key)
			 */
			if (mod.isToggleable)
				listening = true;
		}
		return true;
	}

	public boolean keyTyped(final char typedChar, final int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der nächster Key (abgesehen 'ESCAPE') als
		 * Keybind für mod danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				Client.msg("§6Bound '" + mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.keyCode = keyCode;

				String mods = "";
				for (final Module mod : Client.getModules()) {
					if (mod.getKeyCode() == keyCode && !mod.getName().equals(this.mod.getName())) {
						if (mods.isEmpty()) {
							mods = "§cWARNING: §a" + mod.getName();
						} else {
							mods = mods + ", " + mod.getName();
						}
					}
				}
				if (!mods.isEmpty()) {
					mods = mods + (mods.split(", ").length != 1 ? " §chave " : " §chas ") + "the same keybind!";
					Client.msg(mods);
				}

				FileManager.saveKeybinds();
			} else {
				Client.msg("§6Unbound '" + mod.getName() + "'");
				mod.keyCode = 0;
				FileManager.saveKeybinds();
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(final int mouseX, final int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
