package me.EaZy.client.particle;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.RenderUtils;
import me.EaZy.client.utils.TimerUtils2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

public class Particle {

	public static final int EaZy = 198;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int x;
	private int y;
	private int k;
	private final int id;
	private float size;
	private final Color color;
	private boolean reset;
	private final Random random;
	private final TimerUtils2 timer;
	private final int dir;
	private final boolean inv;

	private float realX;
	private float realY;

	public Particle(final int x, final int y, final int id, final boolean inv) {
		random = new Random();
		timer = new TimerUtils2();
		this.x = x;
		this.y = y;
		realX = x;
		realY = y;
		this.id = id;
		this.inv = inv;
		color = inv ? new Color(0.35F, 0.35F, 0.35f, 1F) : new Color(0.9F, 0.9F, 0.9f, 1F);
		dir = (int) genRandom(0, 360);
		size = 0.7F;
	}

	public void render(final ArrayList<Particle> lastparticles, final int mouseX, final int mouseY) {
		final ScaledResolution scr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		if (realX < -1 || realY < -1 || realX > scr.getScaledWidth_double() + 1
				|| realY > scr.getScaledHeight_double() + 1) {
			reset = true;
		}

		float xx = (float) (Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0) * k * 0.7);
		float yy = (float) (Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0) * k * 0.7);

		timer.updateMS();
		if (timer.hasTimePassedM(20)) {

			++k;
			timer.updateLastMS();
		}
		if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
			final double d = Mouse.isButtonDown(0) ? 1 : Mouse.isButtonDown(1) ? -1 : 0;
			if (realX > mouseX) {
				x -= d;
			} else if (realX < mouseX) {
				x += d;
			}

			if (realY > mouseY) {
				y -= d;
			} else if (realY < mouseY) {
				y += d;
			}
		}
		final float drawX = this.x + xx;
		final float drawY = this.y + yy;
		realX = drawX;
		realY = drawY;

		if (lastparticles != null) {
			for (final Particle p : lastparticles) {
				if (p.equals(this)) {
					continue;
				}
				if (getDistanceTo(p) < 60) {
					if (p != null) {
						GL11.glPushMatrix();
						GL11.glEnable(GL_BLEND);
						GL11.glEnable(GL_LINE_SMOOTH);
						GL11.glDisable(GL_TEXTURE_2D);
						GL11.glLineWidth(0.6f);
						if (inv)
							GL11.glColor4f(0.3F, 0.3F, 0.3F, 0.6F);
						else
							GL11.glColor4f(0.9F, 0.9F, 0.9F, 0.5F);
						GL11.glBegin(3);
						GL11.glVertex2d(drawX - size / 2, drawY - size / 2);
						GL11.glVertex2d(p.getX() - size / 2, p.getY() - size / 2);
						GL11.glEnd();
						GL11.glEnable(GL_TEXTURE_2D);
						GL11.glDisable(GL_LINE_SMOOTH);
						GL11.glDisable(GL_BLEND);
						GL11.glPopMatrix();
					}
				}
			}
		}
		RenderUtils.drawCircle(realX, realY, size, color.getRGB());
	}

	public boolean isReset() {
		return reset;
	}

	public float getX() {
		return realX;
	}

	public float getY() {
		return realY;
	}

	public int getID() {
		return id;
	}

	private double getDistanceTo(final Particle p) {
		return Math.abs(getX() - p.getX()) + Math.abs(getY() - p.getY());
	}

	private float genRandom(final float min, final float max) {
		return (float) (min + Math.random() * (max - min + 1.0f));
	}
}
