package net.minecraft.client;

import org.lwjgl.input.Mouse;

import me.EaZy.client.Configs;
import me.EaZy.client.utils.RenderUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ResourceLocation;

public class LoadingScreenRenderer implements IProgressUpdate {

	public static final int EaZy = 563;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private String field_73727_a = "";

	/** A reference to the Minecraft object. */
	private final Minecraft mc;

	/**
	 * The text currently displayed (i.e. the argument to the last call to
	 * printText or func_73722_d)
	 */
	private String currentlyDisplayedText = "";
	private long field_73723_d = Minecraft.getSystemTime();
	private boolean field_73724_e;
	private final ScaledResolution field_146587_f;
	private final Framebuffer field_146588_g;
	// private static final String __OBFID = "http://https://fuckuskid00000655";

	public LoadingScreenRenderer(final Minecraft mcIn) {
		mc = mcIn;
		field_146587_f = new ScaledResolution(mcIn, Minecraft.displayWidth, Minecraft.displayHeight);
		field_146588_g = new Framebuffer(Minecraft.displayWidth, Minecraft.displayHeight, false);
		field_146588_g.setFramebufferFilter(9728);
	}

	/**
	 * this string, followed by "working..." and then the "% complete" are the 3
	 * lines shown. This resets progress to 0, and the WorkingString to
	 * "working...".
	 */
	@Override
	public void resetProgressAndMessage(final String p_73721_1_) {
		field_73724_e = false;
		func_73722_d(p_73721_1_);
	}

	/**
	 * Shows the 'Saving level' string.
	 */
	@Override
	public void displaySavingString(final String message) {
		field_73724_e = true;
		func_73722_d(message);
	}

	private void func_73722_d(final String p_73722_1_) {
		currentlyDisplayedText = p_73722_1_;

		if (!mc.running) {
			if (!field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			GlStateManager.clear(256);
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();

			if (OpenGlHelper.isFramebufferEnabled()) {
				final int var2 = field_146587_f.getScaleFactor();
				GlStateManager.ortho(0.0D, field_146587_f.getScaledWidth() * var2,
						field_146587_f.getScaledHeight() * var2, 0.0D, 100.0D, 300.0D);
			} else {
				final ScaledResolution var3 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
				GlStateManager.ortho(0.0D, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0D, 100.0D,
						300.0D);
			}

			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.0F, 0.0F, -200.0F);
		}
	}

	/**
	 * Displays a string on the loading screen supposed to indicate what is
	 * being done currently.
	 */
	@Override
	public void displayLoadingString(final String message) {
		if (!mc.running) {
			if (!field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			field_73723_d = 0L;
			field_73727_a = message;
			setLoadingProgress(-1);
			field_73723_d = 0L;
		}
	}

	/**
	 * Updates the progress bar on the loading screen to the specified amount.
	 * Args: loadProgress
	 */
	@Override
	public void setLoadingProgress(final int progress) {
		if (!mc.running) {
			if (!field_73724_e) {
				throw new MinecraftError();
			}
		} else {
			final long var2 = Minecraft.getSystemTime();

			if (var2 - field_73723_d >= 100L) {
				field_73723_d = var2;
				final ScaledResolution var4 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
				final int var5 = var4.getScaleFactor();
				final int var6 = var4.getScaledWidth();
				final int var7 = var4.getScaledHeight();

				if (OpenGlHelper.isFramebufferEnabled()) {
					field_146588_g.framebufferClear();
				} else {
					GlStateManager.clear(256);
				}

				field_146588_g.bindFramebuffer(false);
				GlStateManager.matrixMode(5889);
				GlStateManager.loadIdentity();
				GlStateManager.ortho(0.0D, var4.getScaledWidth_double(), var4.getScaledHeight_double(), 0.0D, 100.0D,
						300.0D);
				GlStateManager.matrixMode(5888);
				GlStateManager.loadIdentity();
				GlStateManager.translate(0.0F, 0.0F, -200.0F);

				if (!OpenGlHelper.isFramebufferEnabled()) {
					GlStateManager.clear(16640);
				}

				final Tessellator var8 = Tessellator.getInstance();
				final WorldRenderer var9 = var8.getWorldRenderer();
				GlStateManager.color(0.575f, 0.575f, 0.575f, 1.0f);
				Minecraft.getTextureManager().bindTexture(GuiMainMenu.bg);
				final ScaledResolution scaledRes = RenderUtils.newScaledResolution();
				Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, scaledRes.getScaledWidth(),
						scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
						scaledRes.getScaledWidth(), scaledRes.getScaledHeight());

				if (progress >= 0) {
					final byte var11 = 100;
					final byte var12 = 2;
					final int var13 = var6 / 2 - var11 / 2;
					final int var14 = var7 / 2 + 16;
					GlStateManager.disableTexture2D();
					var9.startDrawingQuads();
					var9.func_178991_c(8421504);
					var9.addVertex(var13, var14, 0.0D);
					var9.addVertex(var13, var14 + var12, 0.0D);
					var9.addVertex(var13 + var11, var14 + var12, 0.0D);
					var9.addVertex(var13 + var11, var14, 0.0D);
					var9.func_178991_c(8454016);
					var9.addVertex(var13, var14, 0.0D);
					var9.addVertex(var13, var14 + var12, 0.0D);
					var9.addVertex(var13 + progress, var14 + var12, 0.0D);
					var9.addVertex(var13 + progress, var14, 0.0D);
					var8.draw();
					GlStateManager.enableTexture2D();
				}

				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				mc.fontRendererObj.func_175063_a(currentlyDisplayedText,
						(var6 - mc.fontRendererObj.getStringWidth(currentlyDisplayedText)) / 2, var7 / 2 - 4 - 16,
						16777215);
				mc.fontRendererObj.func_175063_a(field_73727_a,
						(var6 - mc.fontRendererObj.getStringWidth(field_73727_a)) / 2, var7 / 2 - 4 + 8, 16777215);
				field_146588_g.unbindFramebuffer();

				if (OpenGlHelper.isFramebufferEnabled()) {
					field_146588_g.framebufferRender(var6 * var5, var7 * var5);
				}

				mc.func_175601_h();

				try {
					Thread.yield();
				} catch (final Exception var15) {}
			}
		}
	}

	@Override
	public void setDoneWorking() {}
}
