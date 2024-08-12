package me.EaZy.client.modules;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class NameTags extends Module {

public static NameTags mod;


	public static final int EaZy = 139;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public NameTags() {
		super("NameTags", 0, "nt", Category.RENDER, "Shows nametags bigger\nand with health.");
		mod = this;

	}

	@Override
	public void onUpdate() {
		if (!isToggled()) {
			if (togglecmd) {
				setToggled(true);
				onEnable();
			}
			return;
		}
		if (isToggled() && !togglecmd) {
			setToggled(false);
			onDisable();
			return;
		}
		super.onUpdate();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "NamenTage";
		} else {
			return super.getRenderName();
		}
	}

	public static void renderNametag(final EntityLivingBase player, final double x, final double y, final double z) {
		GL11.glPushMatrix();
		final String name = NameTags.getNametagName(player);
		GL11.glTranslated((float) x, (float) y + 2.5, (float) z);
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		GL11.glScalef(-0.016666668f * NameTags.getNametagSize(player), -0.016666668f * NameTags.getNametagSize(player),
				0.016666668f * NameTags.getNametagSize(player));
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		final int width = mc.fontRendererObj.getStringWidth(name) / 2;
		RenderUtils.drawBorderedRect(-width - 2, -2, width + 1, 9, -1073741824, Integer.MIN_VALUE);
		mc.fontRendererObj.drawStringWithShadow(name, -width, 0, NameTags.getNametagColor(player));
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}

	private static String getHealthColor(final int health) {
		if (health > 15) {
			return "a";
		}
		if (health > 10) {
			return "e";
		}
		if (health > 5) {
			return "6";
		}
		return "4";
	}

	private static int getNametagColor(final EntityLivingBase player) {
		if (player instanceof EntityPlayer) {
			if (Friends.contains((EntityPlayer) player)) {
				return -43691;
			}
		}
		if (player.isInvisible()) {
			return -13312;
		}
		if (player.isSneaking()) {
			return -1048576;
		}
		return -1;
	}

	private static String getNametagName(final EntityLivingBase player) {
		if (NameProtect.mod.isToggled()) {
			final String p_175065_1_ = player.getDisplayName().getFormattedText();
			if (Minecraft.getNetHandler() != null) {
				final Iterator itr = Minecraft.getNetHandler().getPlayerInfo().iterator();
				while (itr.hasNext()) {
					final NetworkPlayerInfo info = (NetworkPlayerInfo) itr.next();
					EnumChatFormatting.getTextWithoutFormattingCodes(info.getPlayerNameForReal());

					if (EnumChatFormatting.getTextWithoutFormattingCodes(p_175065_1_)
							.contains(Minecraft.session.getUsername())) {}
				}
			}

			return String.valueOf(p_175065_1_) + " §" + NameTags.getHealthColor((int) player.getHealth())
					+ roundFloat(player.getHealth() / 2, 1);

		} else {
			final String name = player.getDisplayName().getFormattedText();
			return String.valueOf(name) + " §" + NameTags.getHealthColor((int) player.getHealth())
					+ roundFloat(player.getHealth() / 2, 1);
		}

	}

	private static float getNametagSize(final EntityLivingBase player) {
		return Minecraft.thePlayer.getDistanceToEntity(player) / 4.0f <= 2.0f ? 2.0f
				: Minecraft.thePlayer.getDistanceToEntity(player) / 4.0f;
	}

	private static float roundFloat(final float number, final int decimalPlaces) {
		float precision = 1.0F;
		for (int i = 0; i < decimalPlaces; i++, precision *= 10) {}
		return (int) (number * precision + 0.5) / precision;
	}
}
