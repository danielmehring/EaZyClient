package de.Hero.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.frames.Radar;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.SettingsManager;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.hooks.InGameGUIHook;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ClickGUI extends GuiScreen {

	public static final int EaZy = 13;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
			Minecraft.displayHeight);
	public static ArrayList<Panel> panels;
	public static ArrayList<Panel> rpanels;
	private ModuleButton mb = null;
	public SettingsManager setmgr;

	public static boolean b1 = true;

	boolean isuntenamboden;

	public static int i1;
	public static int i2 = Minecraft.displayHeight;

	public static Panel getPanelByTitle(final String title) {
		for (final Panel p : ClickGUI.panels) {
			if (p.title.equals(title)) {
				return p;
			}
		}
		return null;
	}

	/*
	 * Konstrukor sollte nur einmal aufgerufen werden => in der MainMethode des
	 * eigenen Codes hier Client.startClient() das GUI wird dann so geöffnet:
	 * mc.displayGuiScreen(Client.clickgui); this.setToggled(false); das Module
	 * wird sofort wieder beendet damit nächstes mal nicht 2mal der z.B.
	 * 'RSHIFT' Knopf gedrückt werden muss
	 */
	public ClickGUI() {
		setmgr = Client.setmgr;

		panels = new ArrayList<>();
		final double pwidth = 80;
		final double pheight = 15;
		final double px = 10;
		double py = 10;
		final double pyplus = pheight + 10;

		/*
		 * Zum Sortieren der Panels einfach die Reihenfolge im Enum ändern ;)
		 */
		for (final Category c : Category.values()) {
			if (c == Category.HIDDEN) {
				continue;
			}
			final String title = Character.toUpperCase(c.name().toLowerCase().charAt(0))
					+ c.name().toLowerCase().substring(1);
			ClickGUI.panels.add(new Panel(title, px, py, pwidth, pheight, false, this) {
				@Override
				public void setup() {
					Client.getModules().stream().filter((m) -> !(!m.getCategory().equals(c))).forEachOrdered((m) -> {
						Elements.add(new ModuleButton(m, this));
					});
				}
			});
			py += pyplus;
		}
		ClickGUI.panels.add(new Radar("Radar", px, py, pwidth, pheight, false, this, true));

		/*
		 * Wieso nicht einfach rpanels = panels; Collections.reverse(rpanels);
		 * Ganz eifach: durch diese Zuweisung wird rpanels einfach nur eine
		 * Weiterleitung zu panels, was mit 'Collections.reverse(rpanels);'
		 * nicht ganz funktionieren würde. Und da die Elemente nur
		 * 'rüberkopiert' werden gibt es keine Probleme ;)
		 */
		rpanels = new ArrayList<>();
		panels.forEach((p) -> {
			rpanels.add(p);
		});
		Collections.reverse(rpanels);

	}

	@Override
	public void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.id == 1) {
			b1 = !b1;
		} else if (clickedButton.id == 2) {
			Configs.useClientColor = !Configs.useClientColor;
			FileManager.saveMain();
		}
	}

	@Override
	public void updateScreen() {

		if (i2 == height - 20) {
			((GuiButton) buttonList.get(0)).visible = true;
			((GuiButton) buttonList.get(0)).enabled = true;
			((GuiButton) buttonList.get(1)).visible = true;
			((GuiButton) buttonList.get(1)).enabled = true;
		} else {
			((GuiButton) buttonList.get(0)).visible = false;
			((GuiButton) buttonList.get(0)).enabled = false;
			((GuiButton) buttonList.get(1)).visible = false;
			((GuiButton) buttonList.get(1)).enabled = false;
		}

		super.updateScreen();
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

		/*
		 * Panels und damit auch Buttons rendern. panels wird NUR hier im Code
		 * verwendet, da das zuletzt gerenderte Panel ganz oben ist Auch wenn es
		 * manchmal egal wäre ob panels/rpanels benutzt wird habe ich mich
		 * einfach mal dazu entschieden, einfach weil es einfacher ist nur
		 * einmal panels zu benutzen
		 */
		panels.forEach((p) -> {
			p.drawScreen(mouseX, mouseY, partialTicks);
		});

		final ScaledResolution s = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
		GL11.glPushMatrix();
		/* copyright HeroCode 2017 */ GL11.glTranslated(s.getScaledWidth(), s.getScaledHeight(), 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		/* https://www.youtube.com/channel/UCJum3PIbnYvIfIEu05GL_yQ */ FontUtil.drawStringWithShadow("API by HeroCode",
				-Minecraft.getMinecraft().fontRendererObj.getStringWidth("API by HeroCode"),
				-Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xff11F86B);
		/*															*/ GL11.glPopMatrix();

		mb = null;
		/*
		 * Überprüfen ob ein Button listening == true hat, wenn ja, dann soll
		 * nicht mehr gesucht werden, nicht dass 1+ auf listening steht...
		 */
		listen:
		for (final Panel p : panels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (final ModuleButton e : p.Elements) {
					if (e.listening) {
						mb = e;
						break listen;
					}
				}
			}
		}

		/*
		 * Settings rendern. Da Settings über alles gerendert werden soll,
		 * abgesehen vom ListeningOverlay werden die Elements von hier aus fast
		 * am Schluss gerendert
		 */
		panels.stream().filter((panel) -> (panel.extended && panel.visible && panel.Elements != null))
				.forEachOrdered((panel) -> {
					panel.Elements.stream()
							.filter((b) -> (b.extended && b.menuelements != null && !b.menuelements.isEmpty()))
							.forEachOrdered((b) -> {
								double off = 0;
								for (final Element e : b.menuelements) {
									e.offset = off;
									e.update();
									
									e.drawScreen(mouseX, mouseY, partialTicks);
									off += e.height;
								}
							});
				});

		/*
		 * Wenn mb != null ist => ein Button listening == true dann wird das
		 * Overlay gerendert mit ein paar Informationen.
		 */
		if (mb != null) {
			drawRect(0, 0, width, height, 0x88101010);
			GL11.glPushMatrix();
			GL11.glTranslatef(s.getScaledWidth() / 2, s.getScaledHeight() / 2, 0.0F);
			GL11.glScalef(4.0F, 4.0F, 0F);
			FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0, -10, 0xffffffff);
			GL11.glScalef(0.5F, 0.5F, 0F);
			FontUtil.drawTotalCenteredStringWithShadow(
					"Press 'ESCAPE' to unbind " + mb.mod.getName()
							+ (mb.mod.getKeyCode() > -1 ? " (" + Keyboard.getKeyName(mb.mod.getKeyCode()) + ")" : ""),
					0, 0, 0xffffffff);
			GL11.glScalef(0.25F, 0.25F, 0F);
			FontUtil.drawTotalCenteredStringWithShadow("Api by HeroCode", 0, 20, 0xffffffff);
			GL11.glPopMatrix();
		}

		if (mouseY < height && mouseY > height - 20) {
			i1 = height - 20;
		} else {
			i1 = height;
		}

		if (i2 > i1) {
			i2 -= 1;
		}

		if (i2 < i1) {
			i2 += 1;
		}

		drawRect(0, i2, width, height, 0x8f151515);

		if (i2 == height - 20) {
			drawRect(2, height - 10 - 3, 2 + 5, height - 10 - 3 + 5, !b1 ? 0x4f00ff00 : 0x00000000);
			drawRect(width - 10, height - 10 - 3, width - 5, height - 10 - 3 + 5,
					Configs.useClientColor ? 0x4f00ff00 : 0x00000000);
			Client.font.drawString("Use ClientColor", width - 75, height - 10 - 4, 0xffffffff);
			Client.font.drawString("Infinite opened settings", 2 + 5 + 2, height - 10 - 4, 0xffffffff);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {

		if (mb != null) {
			return;
		}

		for (final Panel panel : rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (final ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (final Element e : b.menuelements) {
							if (e.mouseClicked(mouseX, mouseY, mouseButton)) {
								return;
							}
						}
					}
				}
			}
		}

		for (final Panel p : rpanels) {
			if (p.mouseClicked(mouseX, mouseY, mouseButton)) {
				return;
			}
		}

		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
		/*
		 * Damit man nicht nochmal den Listeningmode aktivieren kann, wenn er
		 * schon aktiviert ist
		 */
		if (mb != null) {
			return;
		}

		/*
		 * Eigentlich nur für die Slider benötigt, aber durch diesen Call
		 * erfährt jedes Element, wenn z.B. Rechtsklick losgelassen wurde
		 */
		rpanels.stream().filter((panel) -> (panel.extended && panel.visible && panel.Elements != null))
				.forEachOrdered((panel) -> {
					panel.Elements.stream().filter((b) -> (b.extended)).forEachOrdered((b) -> {
						b.menuelements.forEach((e) -> {
							e.mouseReleased(mouseX, mouseY, state);
						});
					});
				});

		/*
		 * Benötigt damit Slider auch losgelassen werden können und nicht immer
		 * an der Maus 'festkleben' :>
		 */
		rpanels.forEach((p) -> {
			p.mouseReleased(mouseX, mouseY, state);
		});

		/*
		 * Nicht benötigt, aber es ist so einfach sauberer ;)
		 */
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) {
		/*
		 * Benötigt für die Keybindfunktion
		 */
		for (final Panel p : rpanels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (final ModuleButton e : p.Elements) {
					try {
						if (e.keyTyped(typedChar, keyCode)) {
							return;
						}
					} catch (final IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		/*
		 * keyTyped in GuiScreen MUSS aufgerufen werden, damit man mit z.B.
		 * ESCAPE aus dem GUI gehen kann
		 */
		try {
			super.keyTyped(typedChar, keyCode);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}
	}

	@Override
	public void initGui() {
		buttonList.clear();
		// Start blur
		if (OpenGlHelper.shadersSupported && mc.func_175606_aa() instanceof EntityPlayer && Configs.blurredBackground) {
			if (Minecraft.entityRenderer.theShaderGroup != null) {
				Minecraft.entityRenderer.theShaderGroup.deleteShaderGroup();
			}
			Minecraft.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}

		// try {
		buttonList.add(new GuiButton(1, 2, height - 10 - 3, 5, 5, ""));
		buttonList.add(new GuiButton(2, width - 10, height - 10 - 3, 5, 5, ""));
		// } catch (final Exception e) {
		// e.printStackTrace();
		// }

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		InGameGUIHook.sort();
		FileManager.saveClickGui();
		FileManager.saveConfigs();
		/*
		 * End blur
		 */
		if (Minecraft.entityRenderer.theShaderGroup != null) {
			Minecraft.entityRenderer.theShaderGroup.deleteShaderGroup();
			Minecraft.entityRenderer.theShaderGroup = null;
		}
		/*
		 * Sliderfix
		 */
		ClickGUI.rpanels.stream().filter((panel) -> (panel.extended && panel.visible && panel.Elements != null))
				.forEachOrdered((panel) -> {
					panel.Elements.stream().filter((b) -> (b.extended)).forEachOrdered((b) -> {
						b.menuelements.stream().filter((e) -> (e instanceof ElementSlider)).forEachOrdered((e) -> {
							((ElementSlider) e).dragging = false;
						});
					});
				});
	}

	public void closeAllSettings() {
		rpanels.stream().filter(
				(p) -> (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0 && b1))
				.forEachOrdered((p) -> {
					p.Elements.forEach((e) -> {
						e.extended = false;
					});
				});
	}
}
