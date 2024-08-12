package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

import com.mojang.util.UUIDTypeAdapter;

public class RealmsScreen {

public static final int EaZy = 1510;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final int SKIN_HEAD_U = 8;
	public static final int SKIN_HEAD_V = 8;
	public static final int SKIN_HEAD_WIDTH = 8;
	public static final int SKIN_HEAD_HEIGHT = 8;
	public static final int SKIN_HAT_U = 40;
	public static final int SKIN_HAT_V = 8;
	public static final int SKIN_HAT_WIDTH = 8;
	public static final int SKIN_HAT_HEIGHT = 8;
	public static final int SKIN_TEX_WIDTH = 64;
	public static final int SKIN_TEX_HEIGHT = 64;
	protected Minecraft minecraft;
	public int width;
	public int height;
	private final GuiScreenRealmsProxy proxy = new GuiScreenRealmsProxy(this);
	// private static final String __OBFID = "http://https://fuckuskid00001898";

	public GuiScreenRealmsProxy getProxy() {
		return proxy;
	}

	public void init() {}

	public void init(final Minecraft p_init_1_, final int p_init_2_, final int p_init_3_) {}

	public void drawCenteredString(final String p_drawCenteredString_1_, final int p_drawCenteredString_2_,
			final int p_drawCenteredString_3_, final int p_drawCenteredString_4_) {
		proxy.func_154325_a(p_drawCenteredString_1_, p_drawCenteredString_2_, p_drawCenteredString_3_,
				p_drawCenteredString_4_);
	}

	public void drawString(final String p_drawString_1_, final int p_drawString_2_, final int p_drawString_3_,
			final int p_drawString_4_) {
		proxy.func_154322_b(p_drawString_1_, p_drawString_2_, p_drawString_3_, p_drawString_4_);
	}

	public void blit(final int p_blit_1_, final int p_blit_2_, final int p_blit_3_, final int p_blit_4_,
			final int p_blit_5_, final int p_blit_6_) {
		proxy.drawTexturedModalRect(p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_);
	}

	public static void blit(final int p_blit_0_, final int p_blit_1_, final float p_blit_2_, final float p_blit_3_,
			final int p_blit_4_, final int p_blit_5_, final int p_blit_6_, final int p_blit_7_, final float p_blit_8_,
			final float p_blit_9_) {
		Gui.drawScaledCustomSizeModalRect(p_blit_0_, p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_, p_blit_6_,
				p_blit_7_, p_blit_8_, p_blit_9_);
	}

	public static void blit(final int p_blit_0_, final int p_blit_1_, final float p_blit_2_, final float p_blit_3_,
			final int p_blit_4_, final int p_blit_5_, final float p_blit_6_, final float p_blit_7_) {
		Gui.drawModalRectWithCustomSizedTexture(p_blit_0_, p_blit_1_, p_blit_2_, p_blit_3_, p_blit_4_, p_blit_5_,
				p_blit_6_, p_blit_7_);
	}

	public void fillGradient(final int p_fillGradient_1_, final int p_fillGradient_2_, final int p_fillGradient_3_,
			final int p_fillGradient_4_, final int p_fillGradient_5_, final int p_fillGradient_6_) {
		proxy.drawGradientRect(p_fillGradient_1_, p_fillGradient_2_, p_fillGradient_3_, p_fillGradient_4_,
				p_fillGradient_5_, p_fillGradient_6_);
	}

	public void renderBackground(final int mouseX, final int mouseY) {
		proxy.drawDefaultBackground(mouseX, mouseY);
	}

	public boolean isPauseScreen() {
		return proxy.doesGuiPauseGame();
	}

	public void renderBackground(final int p_renderBackground_1_, final int mouseX, final int mouseY) {
		proxy.drawWorldBackground(p_renderBackground_1_, mouseX, mouseY);
	}

	public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
		for (int var4 = 0; var4 < proxy.func_154320_j().size(); ++var4) {
			((RealmsButton) proxy.func_154320_j().get(var4)).render(p_render_1_, p_render_2_);
		}
	}

	public void renderTooltip(final ItemStack p_renderTooltip_1_, final int p_renderTooltip_2_,
			final int p_renderTooltip_3_) {
		proxy.renderToolTip(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
	}

	public void renderTooltip(final String p_renderTooltip_1_, final int p_renderTooltip_2_,
			final int p_renderTooltip_3_) {
		proxy.drawCreativeTabHoveringText(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
	}

	public void renderTooltip(final List p_renderTooltip_1_, final int p_renderTooltip_2_,
			final int p_renderTooltip_3_) {
		proxy.drawHoveringText(p_renderTooltip_1_, p_renderTooltip_2_, p_renderTooltip_3_);
	}

	public static void bindFace(final String p_bindFace_0_, final String p_bindFace_1_) {
		ResourceLocation var2 = AbstractClientPlayer.getLocationSkin(p_bindFace_1_);

		if (var2 == null) {
			var2 = DefaultPlayerSkin.func_177334_a(UUIDTypeAdapter.fromString(p_bindFace_0_));
		}

		AbstractClientPlayer.getDownloadImageSkin(var2, p_bindFace_1_);

		Minecraft.getTextureManager().bindTexture(var2);
	}

	public static void bind(final String p_bind_0_) {
		final ResourceLocation var1 = new ResourceLocation(p_bind_0_);

		Minecraft.getTextureManager().bindTexture(var1);
	}

	public void tick() {}

	public int width() {
		return proxy.width;
	}

	public int height() {
		return proxy.height;
	}

	public int fontLineHeight() {
		return proxy.func_154329_h();
	}

	public int fontWidth(final String p_fontWidth_1_) {
		return proxy.func_154326_c(p_fontWidth_1_);
	}

	public void fontDrawShadow(final String p_fontDrawShadow_1_, final int p_fontDrawShadow_2_,
			final int p_fontDrawShadow_3_, final int p_fontDrawShadow_4_) {
		proxy.func_154319_c(p_fontDrawShadow_1_, p_fontDrawShadow_2_, p_fontDrawShadow_3_, p_fontDrawShadow_4_);
	}

	public List fontSplit(final String p_fontSplit_1_, final int p_fontSplit_2_) {
		return proxy.func_154323_a(p_fontSplit_1_, p_fontSplit_2_);
	}

	public void buttonClicked(final RealmsButton p_buttonClicked_1_) {}

	public static RealmsButton newButton(final int p_newButton_0_, final int p_newButton_1_, final int p_newButton_2_,
			final String p_newButton_3_) {
		return new RealmsButton(p_newButton_0_, p_newButton_1_, p_newButton_2_, p_newButton_3_);
	}

	public static RealmsButton newButton(final int p_newButton_0_, final int p_newButton_1_, final int p_newButton_2_,
			final int p_newButton_3_, final int p_newButton_4_, final String p_newButton_5_) {
		return new RealmsButton(p_newButton_0_, p_newButton_1_, p_newButton_2_, p_newButton_3_, p_newButton_4_,
				p_newButton_5_);
	}

	public void buttonsClear() {
		proxy.func_154324_i();
	}

	public void buttonsAdd(final RealmsButton p_buttonsAdd_1_) {
		proxy.func_154327_a(p_buttonsAdd_1_);
	}

	public List buttons() {
		return proxy.func_154320_j();
	}

	public void buttonsRemove(final RealmsButton p_buttonsRemove_1_) {
		proxy.func_154328_b(p_buttonsRemove_1_);
	}

	public RealmsEditBox newEditBox(final int p_newEditBox_1_, final int p_newEditBox_2_, final int p_newEditBox_3_,
			final int p_newEditBox_4_, final int p_newEditBox_5_) {
		return new RealmsEditBox(p_newEditBox_1_, p_newEditBox_2_, p_newEditBox_3_, p_newEditBox_4_, p_newEditBox_5_);
	}

	public void mouseClicked(final int p_mouseClicked_1_, final int p_mouseClicked_2_, final int p_mouseClicked_3_) {}

	public void mouseEvent() {}

	public void keyboardEvent() {}

	public void mouseReleased(final int p_mouseReleased_1_, final int p_mouseReleased_2_,
			final int p_mouseReleased_3_) {}

	public void mouseDragged(final int p_mouseDragged_1_, final int p_mouseDragged_2_, final int p_mouseDragged_3_,
			final long p_mouseDragged_4_) {}

	public void keyPressed(final char p_keyPressed_1_, final int p_keyPressed_2_) {}

	public void confirmResult(final boolean p_confirmResult_1_, final int p_confirmResult_2_) {}

	public static String getLocalizedString(final String p_getLocalizedString_0_) {
		return I18n.format(p_getLocalizedString_0_, new Object[0]);
	}

	public static String getLocalizedString(final String p_getLocalizedString_0_,
			final Object... p_getLocalizedString_1_) {
		return I18n.format(p_getLocalizedString_0_, p_getLocalizedString_1_);
	}

	public RealmsAnvilLevelStorageSource getLevelStorageSource() {
		return new RealmsAnvilLevelStorageSource(Minecraft.getMinecraft().getSaveLoader());
	}

	public void removed() {}
}
