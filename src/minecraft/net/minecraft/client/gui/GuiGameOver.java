package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {

	public static final int EaZy = 477;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int tickCounter;

	Clip clip;

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();

		if (Configs.gta5Death && Configs.gta5DeathSound) {
			try {
				final File soundFile = new File(FileManager.eazyDir.getAbsolutePath(), "GTA5Wasted.wav");
				final AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			if (mc.isIntegratedServerRunning()) {
				buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96,
						I18n.format("deathScreen.deleteWorld", new Object[0])));
			} else {
				buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96,
						I18n.format("deathScreen.leaveServer", new Object[0])));
			}
		} else {
			if (!Configs.gta5Death) {
				buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72,
						I18n.format("deathScreen.respawn", new Object[0])));
				buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96,
						I18n.format("deathScreen.titleScreen", new Object[0])));

				if (mc.getSession() == null) {
					((GuiButton) buttonList.get(1)).enabled = false;
				}
			}
		}

		GuiButton var2;

		for (final Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = false) {
			var2 = (GuiButton) var1.next();
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			Minecraft.thePlayer.respawnPlayer();
			mc.displayGuiScreen((GuiScreen) null);
			break;

		case 1:
			final GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "",
					I18n.format("deathScreen.titleScreen", new Object[0]),
					I18n.format("deathScreen.respawn", new Object[0]), 0);
			mc.displayGuiScreen(var2);
			var2.setButtonDelay(20);
		}
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		if (result) {
			Minecraft.theWorld.sendQuittingDisconnectingPacket();
			mc.loadWorld((WorldClient) null);
			mc.displayGuiScreen(new GuiMainMenu());
		} else {
			Minecraft.thePlayer.respawnPlayer();
			mc.displayGuiScreen((GuiScreen) null);
		}
	}

	@Override
	public void onGuiClosed() {
		if (Configs.gta5Death && clip != null && clip.isActive()) {
			clip.stop();
		}
		super.onGuiClosed();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (Configs.gta5Death && mouseButton == 1) {
			Minecraft.thePlayer.respawnPlayer();
			mc.displayGuiScreen((GuiScreen) null);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (Configs.gta5Death) {
			GL11.glPushMatrix();
			{
				GL11.glTranslated(width - (Client.gta5font.getStringWidth("Right Click to Respawn") / 2.0),
						height - 12, 0);
				GL11.glScaled(0.5, 0.5, 1);
				Client.gta5font.drawString("Right Click to Respawn", 0, 0, 0x66000000);
			}
			GL11.glPopMatrix();
			if (tickCounter < 3) {
				final Tessellator tesselator = Tessellator.getInstance();
				final WorldRenderer worldRenderer = tesselator.getWorldRenderer();
				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.color(1.0f, 1.0f, 1.0f, 0.75f);
				worldRenderer.startDrawingQuads();
				worldRenderer.addVertex(0, height, 0.0);
				worldRenderer.addVertex(width, height, 0.0);
				worldRenderer.addVertex(width, 0, 0.0);
				worldRenderer.addVertex(0, 0, 0.0);
				tesselator.draw();
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
			} else {
				final Tessellator tesselator = Tessellator.getInstance();
				final WorldRenderer worldRenderer = tesselator.getWorldRenderer();
				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.color(0.0f, 0.0f, 0.0f, 0.3f);
				worldRenderer.startDrawingQuads();
				worldRenderer.addVertex(0, height, 0.0);
				worldRenderer.addVertex(width, height, 0.0);
				worldRenderer.addVertex(width, 0, 0.0);
				worldRenderer.addVertex(0, 0, 0.0);
				tesselator.draw();
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
			}
			if (tickCounter > 40) {
				final Tessellator tesselator = Tessellator.getInstance();
				final WorldRenderer worldRenderer = tesselator.getWorldRenderer();
				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.color(0.0f, 0.0f, 0.0f, 0.75f);
				worldRenderer.startDrawingQuads();
				worldRenderer.addVertex(0, height / 2 + 30, 0.0);
				worldRenderer.addVertex(width, height / 2 + 35, 0.0);
				worldRenderer.addVertex(width, height / 2 - 50, 0.0);
				worldRenderer.addVertex(0, height / 2 - 30, 0.0);
				tesselator.draw();
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
				GL11.glPushMatrix();
				{
					float val = 0.0f;
					if (tickCounter > 60) {
						if (tickCounter < 80) {
							float ye = tickCounter - 60;
							val = ye / 20.0f;
						} else {
							val = 1.0f;
						}
					}
					GL11.glTranslated(width / 2, height / 2 - (Client.gta5font.getHeight(Configs.gta5Text) / 2.0), 0);
					GL11.glScaled(3, 3, 1);
					Client.gta5font.drawCenteredStringWithShadow(Configs.gta5Text, 0, 0,
							new Color(1.0f, val, val, 0.9f).getRGB());
				}
				GL11.glPopMatrix();
			}
			super.drawScreen(mouseX, mouseY, partialTicks);
		} else {
			drawGradientRect(0, 0, width, height, 1615855616, -1602211792);
			GlStateManager.pushMatrix();
			GlStateManager.scale(2.0F, 2.0F, 2.0F);
			final boolean var4 = Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled();
			final String var5 = var4 ? I18n.format("deathScreen.title.hardcore", new Object[0])
					: I18n.format("deathScreen.title", new Object[0]);
			drawCenteredString(fontRendererObj, var5, width / 2 / 2, 30, 16777215);
			GlStateManager.popMatrix();

			if (var4) {
				drawCenteredString(fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), width / 2,
						144, 16777215);
			}

			drawCenteredString(fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": "
					+ EnumChatFormatting.YELLOW + Minecraft.thePlayer.getScore(), width / 2, 100, 16777215);
			super.drawScreen(mouseX, mouseY, partialTicks);
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
		GuiButton var2;

		if (tickCounter == 20) {
			for (final Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
				var2 = (GuiButton) var1.next();
			}
		}
	}
}
