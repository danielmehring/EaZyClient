/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.opengl.GL11
 */
package me.EaZy.client.gui.alts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.alts.Alt;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.GuiSlot;
import me.EaZy.client.utils.MojangUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;

public class GuiAltList extends GuiSlot {

public static final int EaZy = 59;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int selectedSlot;
	private final Minecraft mc;
	public static ArrayList<Alt> alts = new ArrayList();
	private final GuiAlts prevMenu;

	public GuiAltList(final Minecraft mc, final GuiAlts prevMenu) {
		super(mc, prevMenu.width, prevMenu.height, 36, prevMenu.height - 56, 30);
		this.mc = mc;
		this.prevMenu = prevMenu;
	}

	public static void deleteUnchecked() {
		final ArrayList<Alt> newAlts = new ArrayList<>();
		int i = 0;
		while (i < alts.size()) {
			if (!alts.get(i).isUnchecked()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		alts = newAlts;
	}

	public static void sortAlts() {
		Collections.sort(alts, (final Alt o1, final Alt o2) -> {
                    if (o1 == null || o2 == null) {
                        return 0;
                    }
                    return o1.getNameOrEmail().compareToIgnoreCase(o2.getNameOrEmail());
                });
		final ArrayList<Alt> newAlts = new ArrayList<>();
		int i = 0;
		while (i < alts.size()) {
			if (alts.get(i).isStarred()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		i = 0;
		while (i < alts.size()) {
			if (!alts.get(i).isUnchecked()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		i = 0;
		while (i < alts.size()) {
			if (alts.get(i).isStarred() && alts.get(i).isUnchecked()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		i = 0;
		while (i < alts.size()) {
			if (!alts.get(i).isCracked() && !alts.get(i).isStarred() && alts.get(i).isUnchecked()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		i = 0;
		while (i < alts.size()) {
			if (alts.get(i).isCracked() && !alts.get(i).isStarred() && alts.get(i).isUnchecked()) {
				newAlts.add(alts.get(i));
			}
			++i;
		}
		i = 0;
		while (i < newAlts.size()) {
			int i2 = 0;
			while (i2 < newAlts.size()) {
				if (i != i2 && newAlts.get(i).getEmail().equals(newAlts.get(i2).getEmail())
						&& newAlts.get(i).isCracked() == newAlts.get(i2).isCracked()) {
					newAlts.remove(i2);
				}
				++i2;
			}
			++i;
		}
		i = 0;
		while (i < newAlts.size()) {
			if (newAlts.get(i).isCracked()) {
			} else {
			}
			++i;
		}
		alts = newAlts;
	}

	@Override
	protected boolean isSelected(final int id) {
		return selectedSlot == id;
	}

	protected int getSelectedSlot() {
		if (selectedSlot > alts.size()) {
			selectedSlot = alts.size();
		}
		return selectedSlot;
	}

	@Override
	protected int getSize() {
		return alts.size();
	}

	@Override
	protected void elementClicked(final int var1, final boolean doubleClick, final int var3, final int var4) {
		selectedSlot = var1;
		if (var1 != -1) {
			final Alt alt = GuiAltList.alts.get(var1);
			if (alt != null && (alt.hasOFCape == 0 || alt.hasOFCape == 3) && !alt.isUnchecked()) {
				if (!alt.getName().isEmpty()) {
					new Thread(() -> {
                                            try {
                                                final URL url = new URL("http://s.optifine.net/capes/" + alt.getName() + ".png");
                                                final InputStream response = url.openStream();
                                                final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                                                for (String line; (line = reader.readLine()) != null;) {
                                                    if (line.equals("Not found")) {
                                                        alt.hasOFCape = 1;
                                                    } else {
                                                        alt.hasOFCape = 2;
                                                    }
                                                }
                                            } catch (final FileNotFoundException e) {
                                                alt.hasOFCape = 1;
                                            } catch (final Exception e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                final URL url = new URL(
                                                        "http://capes.labymod.net/capes/" + MojangUtils.getUUID(alt.getName()));
                                                final InputStream response = url.openStream();
                                                final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                                                for (String line; (line = reader.readLine()) != null;) {
                                                    if (line.toLowerCase().contains("404 not found")) {
                                                        alt.hasLabyModCape = 1;
                                                        return;
                                                    }
                                                }
                                                alt.hasLabyModCape = 2;
                                            } catch (final FileNotFoundException e) {
                                                alt.hasLabyModCape = 1;
                                            } catch (final Exception e) {
                                                e.printStackTrace();
                                            }
                                        }, "OF-Check for " + alt.getName()).start();
				} else {
					alt.hasOFCape = 3;
					alt.hasLabyModCape = 3;
				}
			}
		}
		if (doubleClick) {
			prevMenu.actionPerformed(new GuiButton(0, 0, 0, null));
		}
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	protected void drawSlot(final int id, final int x, final int y, final int var4, final int var5, final int var6) {
		final Alt alt = alts.get(id);
		final ScaledResolution sr = RenderUtils.newScaledResolution();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		if (Minecraft.getMinecraft().getSession().getUsername().equals(alt.getName())) {
			final int sideLeft = left + width / 2 - func_148139_c() / 2;
			final int sideRight = left + width / 2 + func_148139_c() / 2;
			RenderUtils.drawBorderedRect(sideLeft + 50, y - 2 + 3, sideRight - 50, y + var4 + 2 - 3,
					ColorUtils.transparency(Client.getColor(0l).getRGB(), 0.3f),
					ColorUtils.transparency(Client.getColor(0l).getRGB(), 0.1f));
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		GL11.glTranslated(sr.getScaledWidth() / 2, y + 3, 42);
		GL11.glScaled(1.35, 1.35, 42);
		mc.fontRendererObj.drawCenteredString(alt.getNameOrEmail(), 0, 0, 0x2e2e2e);

		GL11.glPopMatrix();

		String tags = alt.isUnchecked() ? "" : alt.isCracked() ? "§8cracked" : "§8premium";
		if (alt.isUnchecked()) {
			tags = String.valueOf(String.valueOf(tags)) + "\u2718";
		}
		if (alt.isStarred()) {
			tags = String.valueOf(String.valueOf(tags)) + " §e\u2606";
		}
		mc.fontRendererObj.drawCenteredString(tags, sr.getScaledWidth() / 2, y + 15, 0x2e2e2e);
	}

}
