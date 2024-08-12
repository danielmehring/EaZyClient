package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import me.EaZy.client.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class GuiChat extends GuiScreen {

public static final int EaZy = 463;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private String historyBuffer = "";

	/**
	 * keeps position of which chat message you will select when you press up,
	 * (does not increase for duplicated messages sent immediately after each
	 * other)
	 */
	private int sentHistoryCursor = -1;
	private boolean playerNamesFound;
	private boolean waitingOnAutocomplete;
	private int autocompleteIndex;
	private final List foundPlayerNames = Lists.newArrayList();

	/** Chat entry field */
	protected GuiTextField inputField;

	/**
	 * is the text that appears when you press the chat key and the input box
	 * appears pre-filled
	 */
	private String defaultInputFieldText = "";

	public GuiChat() {
	}

	public GuiChat(final String p_i1024_1_) {
		defaultInputFieldText = p_i1024_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		sentHistoryCursor = mc.ingameGUI.getChatGUI().getSentMessages().size();
		inputField = new GuiTextField(0, fontRendererObj, 4, height - 12, width - 4, 12);
		inputField.setMaxStringLength(65535);
		inputField.setEnableBackgroundDrawing(false);
		inputField.setFocused(true);
		inputField.setText(defaultInputFieldText);
		inputField.setCanLoseFocus(false);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		mc.ingameGUI.getChatGUI().resetScroll();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		inputField.updateCursorCounter();
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		waitingOnAutocomplete = false;

		if (keyCode == 15) {
			autocompletePlayerNames();
		} else {
			playerNamesFound = false;
		}

		if (keyCode == 1) {
			mc.displayGuiScreen((GuiScreen) null);
		} else if (keyCode != 28 && keyCode != 156) {
			if (keyCode == 200) {
				getSentHistory(-1);
			} else if (keyCode == 208) {
				getSentHistory(1);
			} else if (keyCode == 201) {
				mc.ingameGUI.getChatGUI().scroll(mc.ingameGUI.getChatGUI().getLineCount() - 1);
			} else if (keyCode == 209) {
				mc.ingameGUI.getChatGUI().scroll(-mc.ingameGUI.getChatGUI().getLineCount() + 1);
			} else {
				inputField.textboxKeyTyped(typedChar, keyCode);
			}
		} else {
			final String var3 = inputField.getText().trim();

			if (var3.length() > 0) {
				func_175275_f(var3);
			}

			mc.displayGuiScreen((GuiScreen) null);
		}
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int var1 = Mouse.getEventDWheel();

		if (var1 != 0) {
			if (var1 > 1) {
				var1 = 1;
			}

			if (var1 < -1) {
				var1 = -1;
			}

			if (!isShiftKeyDown()) {
				var1 *= 7;
			}

			mc.ingameGUI.getChatGUI().scroll(var1);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		if (mouseButton == 0) {
			final IChatComponent var4 = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

			if (func_175276_a(var4)) {
				return;
			}
		}

		inputField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void func_175274_a(final String p_175274_1_, final boolean p_175274_2_) {
		if (p_175274_2_) {
			inputField.setText(p_175274_1_);
		} else {
			inputField.writeText(p_175274_1_);
		}
	}

	public void autocompletePlayerNames() {
		String var3;

		if (playerNamesFound) {
			inputField.deleteFromCursor(inputField.func_146197_a(-1, inputField.getCursorPosition(), false)
					- inputField.getCursorPosition());

			if (autocompleteIndex >= foundPlayerNames.size()) {
				autocompleteIndex = 0;
			}
		} else {
			final int var1 = inputField.func_146197_a(-1, inputField.getCursorPosition(), false);
			foundPlayerNames.clear();
			autocompleteIndex = 0;
			final String var2 = inputField.getText().substring(var1).toLowerCase();
			var3 = inputField.getText().substring(0, inputField.getCursorPosition());
			sendAutocompleteRequest(var3, var2);

			if (foundPlayerNames.isEmpty()) {
				return;
			}

			playerNamesFound = true;
			inputField.deleteFromCursor(var1 - inputField.getCursorPosition());
		}

		if (foundPlayerNames.size() > 1) {
			final StringBuilder var4 = new StringBuilder();

			for (final Iterator var5 = foundPlayerNames.iterator(); var5.hasNext(); var4.append(var3)) {
				var3 = (String) var5.next();

				if (var4.length() > 0) {
					var4.append(", ");
				}
			}

			mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);
		}

		inputField.writeText((String) foundPlayerNames.get(autocompleteIndex++));
	}

	private void sendAutocompleteRequest(final String p_146405_1_, final String p_146405_2_) {
		if (p_146405_1_.length() >= 1) {
			BlockPos var3 = null;

			if (mc.objectMouseOver != null
					&& mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				var3 = mc.objectMouseOver.getBlockPos();
			}

			Minecraft.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, var3));
			waitingOnAutocomplete = true;
		}
	}

	/**
	 * input is relative and is applied directly to the sentHistoryCursor so -1
	 * is the previous message, 1 is the next message from the current cursor
	 * position
	 */
	public void getSentHistory(final int p_146402_1_) {
		int var2 = sentHistoryCursor + p_146402_1_;
		final int var3 = mc.ingameGUI.getChatGUI().getSentMessages().size();
		var2 = MathHelper.clamp_int(var2, 0, var3);

		if (var2 != sentHistoryCursor) {
			if (var2 == var3) {
				sentHistoryCursor = var3;
				inputField.setText(historyBuffer);
			} else {
				if (sentHistoryCursor == var3) {
					historyBuffer = inputField.getText();
				}

				inputField.setText((String) mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
				sentHistoryCursor = var2;
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (!Configs.betterChat) {
			drawRect(2, height - 14, width - 2, height - 2, Integer.MIN_VALUE);
			inputField.drawTextBox();
			final IChatComponent var4 = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

			if (var4 != null && var4.getChatStyle().getChatHoverEvent() != null) {
				func_175272_a(var4, mouseX, mouseY);
			}
		} else {

			final double swidth = 16 + Math.max(fontRendererObj.getStringWidth(inputField.getText() + "__"),
					GuiNewChat.calculateChatboxWidth(Minecraft.gameSettings.chatWidth) - 11);
			drawRect(0, height - 14, (int) swidth, height, 0xaf151515);
			drawRect(0, height - 15, (int) swidth, height - 14, 0xff2BFF77);

			final int size = 14;
			Minecraft.getTextureManager().bindTexture(Minecraft.thePlayer.getLocationSkin());
			GL11.glColor4f(1F, 1F, 1F, 1F);
			Gui.drawScaledCustomSizeModalRect(0, height - 14, 8, 8, 8, 8, size, size, 64, 64);

			inputField.xPosition = 16;
			inputField.yPosition = height - 11;
			inputField.drawTextBox();

			final IChatComponent var5 = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

			if (var5 != null && var5.getChatStyle().getChatHoverEvent() != null) {
				func_175272_a(var5, mouseX, mouseY);
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void onAutocompleteResponse(final String[] p_146406_1_) {
		if (waitingOnAutocomplete) {
			playerNamesFound = false;
			foundPlayerNames.clear();
			final String[] var2 = p_146406_1_;
			final int var3 = p_146406_1_.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final String var5 = var2[var4];

				if (var5.length() > 0) {
					foundPlayerNames.add(var5);
				}
			}

			final String var6 = inputField.getText()
					.substring(inputField.func_146197_a(-1, inputField.getCursorPosition(), false));
			final String var7 = StringUtils.getCommonPrefix(p_146406_1_);

			if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
				inputField.deleteFromCursor(inputField.func_146197_a(-1, inputField.getCursorPosition(), false)
						- inputField.getCursorPosition());
				inputField.writeText(var7);
			} else if (foundPlayerNames.size() > 0) {
				playerNamesFound = true;
				autocompletePlayerNames();
			}
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
}
