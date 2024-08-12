package net.minecraft.client.gui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;

public class GuiCommandBlock extends GuiScreen {

public static final int EaZy = 465;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_146488_a = LogManager.getLogger();

	/** Text field containing the command block's command. */
	private GuiTextField commandTextField;
	private GuiTextField field_146486_g;

	/** Command block being edited. */
	private final CommandBlockLogic localCommandBlock;

	/** "Done" button for the GUI. */
	private GuiButton doneBtn;
	private GuiButton cancelBtn;
	private GuiButton field_175390_s;
	private boolean field_175389_t;
	// private static final String __OBFID = "http://https://fuckuskid00000748";

	public GuiCommandBlock(final CommandBlockLogic p_i45032_1_) {
		localCommandBlock = p_i45032_1_;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		commandTextField.updateCursorCounter();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(doneBtn = new GuiButton(0, width / 2 - 4 - 150, height / 4 + 120 + 12, 150, 20,
				I18n.format("gui.done", new Object[0])));
		buttonList.add(cancelBtn = new GuiButton(1, width / 2 + 4, height / 4 + 120 + 12, 150, 20,
				I18n.format("gui.cancel", new Object[0])));
		buttonList.add(field_175390_s = new GuiButton(4, width / 2 + 150 - 20, 150, 20, 20, "O"));
		commandTextField = new GuiTextField(2, fontRendererObj, width / 2 - 150, 50, 300, 20);
		commandTextField.setMaxStringLength(32767);
		commandTextField.setFocused(true);
		commandTextField.setText(localCommandBlock.getCustomName());
		field_146486_g = new GuiTextField(3, fontRendererObj, width / 2 - 150, 150, 276, 20);
		field_146486_g.setMaxStringLength(32767);
		field_146486_g.setEnabled(false);
		field_146486_g.setText("-");
		field_175389_t = localCommandBlock.func_175571_m();
		func_175388_a();
		doneBtn.enabled = commandTextField.getText().trim().length() > 0;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				localCommandBlock.func_175573_a(field_175389_t);
				mc.displayGuiScreen((GuiScreen) null);
			} else if (button.id == 0) {
				final PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
				var2.writeByte(localCommandBlock.func_145751_f());
				localCommandBlock.func_145757_a(var2);
				var2.writeString(commandTextField.getText());
				var2.writeBoolean(localCommandBlock.func_175571_m());
				Minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));

				if (!localCommandBlock.func_175571_m()) {
					localCommandBlock.func_145750_b((IChatComponent) null);
				}

				mc.displayGuiScreen((GuiScreen) null);
			} else if (button.id == 4) {
				localCommandBlock.func_175573_a(!localCommandBlock.func_175571_m());
				func_175388_a();
			}
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		commandTextField.textboxKeyTyped(typedChar, keyCode);
		field_146486_g.textboxKeyTyped(typedChar, keyCode);
		doneBtn.enabled = commandTextField.getText().trim().length() > 0;

		if (keyCode != 28 && keyCode != 156) {
			if (keyCode == 1) {
				actionPerformed(cancelBtn);
			}
		} else {
			actionPerformed(doneBtn);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
		field_146486_g.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), width / 2, 20, 16777215);
		drawString(fontRendererObj, I18n.format("advMode.command", new Object[0]), width / 2 - 150, 37, 10526880);
		commandTextField.drawTextBox();
		final byte var4 = 75;
		final byte var5 = 0;
		final FontRenderer var10001 = fontRendererObj;
		final String var10002 = I18n.format("advMode.nearestPlayer", new Object[0]);
		final int var10003 = width / 2 - 150;
		int var7 = var5 + 1;
		drawString(var10001, var10002, var10003, var4 + var5 * fontRendererObj.FONT_HEIGHT, 10526880);
		drawString(fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), width / 2 - 150,
				var4 + var7++ * fontRendererObj.FONT_HEIGHT, 10526880);
		drawString(fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), width / 2 - 150,
				var4 + var7++ * fontRendererObj.FONT_HEIGHT, 10526880);
		drawString(fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), width / 2 - 150,
				var4 + var7++ * fontRendererObj.FONT_HEIGHT, 10526880);
		drawString(fontRendererObj, "", width / 2 - 150, var4 + var7++ * fontRendererObj.FONT_HEIGHT, 10526880);

		if (field_146486_g.getText().length() > 0) {
			final int var6 = var4 + var7 * fontRendererObj.FONT_HEIGHT + 16;
			drawString(fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), width / 2 - 150, var6,
					10526880);
			field_146486_g.drawTextBox();
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void func_175388_a() {
		if (localCommandBlock.func_175571_m()) {
			field_175390_s.displayString = "O";

			if (localCommandBlock.getLastOutput() != null) {
				field_146486_g.setText(localCommandBlock.getLastOutput().getUnformattedText());
			}
		} else {
			field_175390_s.displayString = "X";
			field_146486_g.setText("-");
		}
	}
}
