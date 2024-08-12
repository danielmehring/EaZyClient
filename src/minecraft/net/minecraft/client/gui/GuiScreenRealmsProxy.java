package net.minecraft.client.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class GuiScreenRealmsProxy extends GuiScreen {

	public static final int EaZy = 510;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final RealmsScreen field_154330_a;
	// private static final String __OBFID = "http://https://fuckuskid00001847";

	public GuiScreenRealmsProxy(final RealmsScreen p_i1087_1_) {
		field_154330_a = p_i1087_1_;
		super.buttonList = Collections.synchronizedList(Lists.newArrayList());
	}

	public RealmsScreen func_154321_a() {
		return field_154330_a;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		field_154330_a.init();
		super.initGui();
	}

	public void func_154325_a(final String p_154325_1_, final int p_154325_2_, final int p_154325_3_,
			final int p_154325_4_) {
		super.drawCenteredString(fontRendererObj, p_154325_1_, p_154325_2_, p_154325_3_, p_154325_4_);
	}

	public void func_154322_b(final String p_154322_1_, final int p_154322_2_, final int p_154322_3_,
			final int p_154322_4_) {
		GuiScreen.drawString(fontRendererObj, p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v,
	 * width, height
	 */
	@Override
	public void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY, final int width,
			final int height) {
		field_154330_a.blit(x, y, textureX, textureY, width, height);
		super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors
	 * (ARGB format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	@Override
	public void drawGradientRect(final int left, final int top, final int right, final int bottom, final int startColor,
			final int endColor) {
		super.drawGradientRect(left, top, right, bottom, startColor, endColor);
	}

	/**
	 * Draws either a gradient over the background screen (when it exists) or a
	 * flat gradient over background.png
	 */
	@Override
	public void drawDefaultBackground(final int mouseX, final int mouseY) {
		super.drawDefaultBackground(mouseX, mouseY);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return super.doesGuiPauseGame();
	}

	@Override
	public void drawWorldBackground(final int tint, final int mouseX, final int mouseY) {
		super.drawWorldBackground(tint, mouseX, mouseY);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		field_154330_a.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public void renderToolTip(final ItemStack itemIn, final int x, final int y) {
		super.renderToolTip(itemIn, x, y);
	}

	/**
	 * Draws the text when mouse is over creative inventory tab. Params: current
	 * creative tab to be checked, current mouse x position, current mouse y
	 * position.
	 */
	@Override
	public void drawCreativeTabHoveringText(final String tabName, final int mouseX, final int mouseY) {
		super.drawCreativeTabHoveringText(tabName, mouseX, mouseY);
	}

	@Override
	public void drawHoveringText(final List textLines, final int x, final int y) {
		super.drawHoveringText(textLines, x, y);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		field_154330_a.tick();
		super.updateScreen();
	}

	public int func_154329_h() {
		return fontRendererObj.FONT_HEIGHT;
	}

	public int func_154326_c(final String p_154326_1_) {
		return fontRendererObj.getStringWidth(p_154326_1_);
	}

	public void func_154319_c(final String p_154319_1_, final int p_154319_2_, final int p_154319_3_,
			final int p_154319_4_) {
		fontRendererObj.drawStringWithShadow(p_154319_1_, p_154319_2_, p_154319_3_, p_154319_4_);
	}

	public List func_154323_a(final String p_154323_1_, final int p_154323_2_) {
		return fontRendererObj.listFormattedStringToWidth(p_154323_1_, p_154323_2_);
	}

	@Override
	public final void actionPerformed(final GuiButton button) throws IOException {
		field_154330_a.buttonClicked(((GuiButtonRealmsProxy) button).getRealmsButton());
	}

	public void func_154324_i() {
		super.buttonList.clear();
	}

	public void func_154327_a(final RealmsButton p_154327_1_) {
		super.buttonList.add(p_154327_1_.getProxy());
	}

	public List func_154320_j() {
		final ArrayList var1 = Lists.newArrayListWithExpectedSize(super.buttonList.size());
		final Iterator var2 = super.buttonList.iterator();

		while (var2.hasNext()) {
			final GuiButton var3 = (GuiButton) var2.next();
			var1.add(((GuiButtonRealmsProxy) var3).getRealmsButton());
		}

		return var1;
	}

	public void func_154328_b(final RealmsButton p_154328_1_) {
		super.buttonList.remove(p_154328_1_);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		field_154330_a.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		field_154330_a.mouseEvent();
		super.handleMouseInput();
	}

	/**
	 * Handles keyboard input.
	 */
	@Override
	public void handleKeyboardInput() throws IOException {
		field_154330_a.keyboardEvent();
		super.handleKeyboardInput();
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
		field_154330_a.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * Called when a mouse button is pressed and the mouse is moved around.
	 * Parameters are : mouseX, mouseY, lastButtonClicked & timeSinceMouseClick.
	 */
	@Override
	public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton,
			final long timeSinceLastClick) {
		field_154330_a.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	public void keyTyped(final char typedChar, final int keyCode) throws IOException {
		field_154330_a.keyPressed(typedChar, keyCode);
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		field_154330_a.confirmResult(result, id);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		field_154330_a.removed();
		super.onGuiClosed();
	}
}
