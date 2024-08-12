package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiWinGame extends GuiScreen {

public static final int EaZy = 527;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
	private static final ResourceLocation field_146577_g = new ResourceLocation("textures/misc/vignette.png");
	private int field_146581_h;
	private List field_146582_i;
	private int field_146579_r;
	private final float field_146578_s = 0.5F;
	// private static final String __OBFID = "http://https://fuckuskid00000719";

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		++field_146581_h;
		final float var1 = (field_146579_r + height + height + 24) / field_146578_s;

		if (field_146581_h > var1) {
			sendRespawnPacket();
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (keyCode == 1) {
			sendRespawnPacket();
		}
	}

	private void sendRespawnPacket() {
		Minecraft.thePlayer.sendQueue
				.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
		mc.displayGuiScreen((GuiScreen) null);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		if (field_146582_i == null) {
			field_146582_i = Lists.newArrayList();

			try {
				String var1 = "";
				final String var2 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED
						+ EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
				final short var3 = 274;
				BufferedReader var4 = new BufferedReader(new InputStreamReader(Minecraft.getResourceManager()
						.getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
				final Random var5 = new Random(8124371L);
				int var6;

				while ((var1 = var4.readLine()) != null) {
					String var7;
					String var8;

					for (var1 = var1.replaceAll("PLAYERNAME", mc.getSession().getUsername()); var1
							.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED
									+ "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8) {
						var6 = var1.indexOf(var2);
						var7 = var1.substring(0, var6);
						var8 = var1.substring(var6 + var2.length());
					}

					field_146582_i.addAll(mc.fontRendererObj.listFormattedStringToWidth(var1, var3));
					field_146582_i.add("");
				}

				for (var6 = 0; var6 < 8; ++var6) {
					field_146582_i.add("");
				}

				var4 = new BufferedReader(new InputStreamReader(Minecraft.getResourceManager()
						.getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8));

				while ((var1 = var4.readLine()) != null) {
					var1 = var1.replaceAll("PLAYERNAME", mc.getSession().getUsername());
					var1 = var1.replaceAll("\t", "    ");
					field_146582_i.addAll(mc.fontRendererObj.listFormattedStringToWidth(var1, var3));
					field_146582_i.add("");
				}

				field_146579_r = field_146582_i.size() * 12;
			} catch (final Exception var9) {
				logger.error("Couldn\'t load credits", var9);
			}
		}
	}

	private void drawWinGameScreen(final int p_146575_1_, final int p_146575_2_, final float p_146575_3_) {
		final Tessellator var4 = Tessellator.getInstance();
		final WorldRenderer var5 = var4.getWorldRenderer();
		Minecraft.getTextureManager().bindTexture(Gui.optionsBackground);
		var5.startDrawingQuads();
		var5.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F);
		final int var6 = width;
		final float var7 = 0.0F - (field_146581_h + p_146575_3_) * 0.5F * field_146578_s;
		final float var8 = height - (field_146581_h + p_146575_3_) * 0.5F * field_146578_s;
		final float var9 = 0.015625F;
		float var10 = (field_146581_h + p_146575_3_ - 0.0F) * 0.02F;
		final float var11 = (field_146579_r + height + height + 24) / field_146578_s;
		final float var12 = (var11 - 20.0F - (field_146581_h + p_146575_3_)) * 0.005F;

		if (var12 < var10) {
			var10 = var12;
		}

		if (var10 > 1.0F) {
			var10 = 1.0F;
		}

		var10 *= var10;
		var10 = var10 * 96.0F / 255.0F;
		var5.func_178986_b(var10, var10, var10);
		var5.addVertexWithUV(0.0D, height, zLevel, 0.0D, var7 * var9);
		var5.addVertexWithUV(var6, height, zLevel, var6 * var9, var7 * var9);
		var5.addVertexWithUV(var6, 0.0D, zLevel, var6 * var9, var8 * var9);
		var5.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, var8 * var9);
		var4.draw();
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawWinGameScreen(mouseX, mouseY, partialTicks);
		final Tessellator var4 = Tessellator.getInstance();
		final WorldRenderer var5 = var4.getWorldRenderer();
		final short var6 = 274;
		final int var7 = width / 2 - var6 / 2;
		final int var8 = height + 50;
		final float var9 = -(field_146581_h + partialTicks) * field_146578_s;
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, var9, 0.0F);
		Minecraft.getTextureManager().bindTexture(field_146576_f);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(var7, var8, 0, 0, 155, 44);
		drawTexturedModalRect(var7 + 155, var8, 0, 45, 155, 44);
		var5.func_178991_c(16777215);
		int var10 = var8 + 200;
		int var11;

		for (var11 = 0; var11 < field_146582_i.size(); ++var11) {
			if (var11 == field_146582_i.size() - 1) {
				final float var12 = var10 + var9 - (height / 2 - 6);

				if (var12 < 0.0F) {
					GlStateManager.translate(0.0F, -var12, 0.0F);
				}
			}

			if (var10 + var9 + 12.0F + 8.0F > 0.0F && var10 + var9 < height) {
				final String var13 = (String) field_146582_i.get(var11);

				if (var13.startsWith("[C]")) {
					fontRendererObj.drawStringWithShadow(var13.substring(3),
							var7 + (var6 - fontRendererObj.getStringWidth(var13.substring(3))) / 2, var10, 16777215);
				} else {
					fontRendererObj.drawStringWithShadow(var13, var7, var10, 16777215);
				}
			}

			var10 += 12;
		}

		GlStateManager.popMatrix();
		Minecraft.getTextureManager().bindTexture(field_146577_g);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(0, 769);
		var5.startDrawingQuads();
		var5.func_178960_a(1.0F, 1.0F, 1.0F, 1.0F);
		var11 = width;
		final int var14 = height;
		var5.addVertexWithUV(0.0D, var14, zLevel, 0.0D, 1.0D);
		var5.addVertexWithUV(var11, var14, zLevel, 1.0D, 1.0D);
		var5.addVertexWithUV(var11, 0.0D, zLevel, 1.0D, 0.0D);
		var5.addVertexWithUV(0.0D, 0.0D, zLevel, 0.0D, 0.0D);
		var4.draw();
		GlStateManager.disableBlend();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
