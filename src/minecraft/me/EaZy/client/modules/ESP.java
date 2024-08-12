package me.EaZy.client.modules;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class ESP extends Module {

	public static ESP mod;

	public static final int EaZy = 107;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static String renderName = "Outline";

	private static final ResourceLocation mittebekommttrittebild = new ResourceLocation("EaZy/MittebekommtTritte.png");
	private static final ResourceLocation zwei_D_esp = new ResourceLocation("EaZy/2desp.png");

	private String suffix;

	public ESP() {
		super("ESP", 0, "ESP", Category.RENDER);
		final ArrayList<String> Mode = new ArrayList<>();
		Mode.add("Wire");
		Mode.add("Outline");
		Mode.add("2D");
		Mode.add("Mitte");
		Mode.add("Box");
		Client.setmgr.rSetting(new Setting("Mode", this, "Outline", Mode));
		mod = this;
	}

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "BesserSichtbar [" + renderName + "]";
			} else {
				suffix = "BesserSichtbar";
			}
		} else {

			if (Configs.suffix) {
				suffix = "ESP [" + renderName + "]";
			} else {
				suffix = "ESP";
			}

		}

		return suffix;
	}

	public static void updateMode() {
		try {
			renderName = Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString();
		} catch (final Exception allahuküçük) {
			allahuküçük.printStackTrace();
		}
	}

	@Override
	public void onEnable() {
		ESP.updateMode();
		super.onEnable();
	}

	public static void renderMittesTritte(final EntityLivingBase player, final double x, final double y,
			final double z) {
		if (player.isInvisible() || player.isInvisibleToPlayer(Minecraft.thePlayer)) {
			return;
		}
		if (Client.setmgr.getSettingByName(mod, "Mode").getValString().equalsIgnoreCase("Box")) {
			Color c = Client.getColor(0l);
			RenderUtils.drawEntityESP(x, y, z, player.width / 1.5, player.height + 0.25, c, 0.15f, c, 0.75f, 1.0f);
		} else {
			GL11.glPushMatrix();
			final FontRenderer var13 = mc.fontRendererObj;
			final String name = player.getName();
			GL11.glTranslated((float) x, (float) y + 2.5, (float) z);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(-0.016666668f * getNametagSize(player), -0.016666668f * getNametagSize(player),
					0.016666668f * getNametagSize(player));
			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			final int width = var13.getStringWidth(name) / 2;
			final boolean zweid = Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString().equalsIgnoreCase("2D");
			if (zweid) {
				if (player.hurtTime > 0) {
					GL11.glColor4f(player.hurtTime / 20.0f, 0.98f - player.hurtTime / 20.0f,
							0.807f - player.hurtTime / 20.0f, 1.0f);
				} else {
					GL11.glColor4f(0.0f, 0.98f, 0.807f, 1.0f);
				}
			} else {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			}

			final int var7 = width / 2 - 137;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColorMask(true, true, true, true);

			GL11.glPushMatrix();
			{
				if (zweid) {
					GL11.glScaled(0.25, 0.35, 0.35);
					GL11.glRotated(mc.thePlayer.rotationPitch, 10, -1, 0);
					Minecraft.getTextureManager().bindTexture(zwei_D_esp);
					drawTexturedModalRect(var7, 0, 0, 0, 256, 256);
				} else {
					GL11.glScaled(0.35, 0.35, 0.35);
					GL11.glRotated(mc.thePlayer.rotationPitch, 10, -1, 0);
					Minecraft.getTextureManager().bindTexture(mittebekommttrittebild);
					drawTexturedModalRect(var7, 0, 0, 0, 256, 260);
				}
			}
			GL11.glPopMatrix();

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GlStateManager.depthMask(true);
			GlStateManager.enableLighting();
			GL11.glPopMatrix();
		}
	}

	private static void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY,
			final int width, final int height) {
		final float var7 = 0.00390625F;
		final float var8 = 0.00390625F;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		var10.startDrawingQuads();
		var10.addVertexWithUV(x + 0, y + height, 0, (textureX + 0) * var7, (textureY + height) * var8);
		var10.addVertexWithUV(x + width, y + height, 0, (textureX + width) * var7, (textureY + height) * var8);
		var10.addVertexWithUV(x + width, y + 0, 0, (textureX + width) * var7, (textureY + 0) * var8);
		var10.addVertexWithUV(x + 0, y + 0, 0, (textureX + 0) * var7, (textureY + 0) * var8);
		var9.draw();
	}

	private static float getNametagSize(final EntityLivingBase player) {
		return 2.0f;
	}

}
