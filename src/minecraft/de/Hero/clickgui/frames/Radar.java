package de.Hero.clickgui.frames;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Radar extends Panel {

	public Radar(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent,
			boolean ipinnable) {
		super(ititle, ix, iy, iwidth, iheight, iextended, parent, ipinnable);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!visible) {
			return;
		}

		if (partialTicks != 42) {
			if (dragging) {
				x = x2 + mouseX;
				y = y2 + mouseY;
			}

			final Color temp = Client.getColor(0l).darker().darker();

			Gui.drawRect(x - 1, y, x + width + 1, y + height, Configs.useClientColor ? temp.getRGB() : 0xff0198E3);

			if (yl1 < ylt1) {
				yl1 += 1;
			}

			if (yl1 > ylt1) {
				yl1 -= 1;
			}

			if (yl2 < ylt2) {
				yl2 += 1;
			}

			if (yl2 > ylt2) {
				yl2 -= 1;
			}

			if (extended) {
				ylt1 = 3;
				ylt2 = 12;
			} else {
				ylt1 = 12;
				ylt2 = 3;
			}

			RenderUtils.drawLine(x + width - 10 + 3, y + yl1, x + width - 5 + 3, y + yl2, 1, 0xffffffff);
			RenderUtils.drawLine(x + width - 10 + 3, y + yl1, x + width - 15 + 3, y + yl2, 1, 0xffffffff);

			GL11.glPushMatrix();
			GL11.glTranslated((int) x + (int) width / 2 - 37, (int) y + (int) height / 2 - 5, 0);
			GL11.glScaled(1.5, 1.5, 0);
			Client.font.drawString(title, 0, 0, pinned ? 0xff40ff40 : 0xffefefef);
			GL11.glPopMatrix();
		}

		if (extended) {
			double yaw = -Math.toRadians(Client.mc.thePlayer.rotationYawHead);
			double midX = x + width / 2;
			double midY = y + height + width / 2;
			double playerX = Client.mc.thePlayer.posX;
			double playerZ = Client.mc.thePlayer.posZ;
			Gui.drawRect(x, y + height, x + width, y + height + 80, 0x40808080);
			RenderUtils.drawHollowCircle(midX, midY, (float) (width / 2), 1, 0x80404040);
			RenderUtils.drawHollowCircle(midX, midY, (float) (width / 2 / 3 * 2), 1, 0x80404040);
			RenderUtils.drawHollowCircle(midX, midY, (float) (width / 2 / 3), 1, 0x80404040);

			for (Entity e : (List<Entity>) Client.mc.theWorld.loadedEntityList) {
				if (!(e instanceof EntityPlayer) || e.isEntityEqual(Client.mc.thePlayer) || Math
						.sqrt((e.posX - playerX) * (e.posX - playerX) + (e.posZ - playerZ) * (e.posZ - playerZ)) > 40) {
					continue;
				}

				double x1 = e.posX - playerX;
				double y1 = e.posZ - playerZ;

				RenderUtils.drawCircle(midX - (x1 * Math.cos(yaw) - y1 * Math.sin(yaw)),
						midY - (x1 * Math.sin(yaw) + y1 * Math.cos(yaw)), 1f,
						e.isInvisible() ? (Friends.contains((EntityPlayer) e) ? 0x600000ff : 0x60ff0000)
								: (Friends.contains((EntityPlayer) e) ? 0xa00000ff : 0xa0ff0000));

			}

			RenderUtils.drawCircle(midX, midY, 1f, 0xa0404040);
		}
	}

}