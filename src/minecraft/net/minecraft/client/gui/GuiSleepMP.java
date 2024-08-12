package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

import java.io.IOException;

public class GuiSleepMP extends GuiChat {

public static final int EaZy = 517;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000697";

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(
				new GuiButton(1, width / 2 - 100, height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (keyCode == 1) {
			wakeFromSleep();
		} else if (keyCode != 28 && keyCode != 156) {
			super.keyTyped(typedChar, keyCode);
		} else {
			final String var3 = inputField.getText().trim();

			if (!var3.isEmpty()) {
				Minecraft.thePlayer.sendChatMessage(var3);
			}

			inputField.setText("");
			mc.ingameGUI.getChatGUI().resetScroll();
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 1) {
			wakeFromSleep();
		} else {
			super.actionPerformed(button);
		}
	}

	private void wakeFromSleep() {
		final NetHandlerPlayClient var1 = Minecraft.thePlayer.sendQueue;
		var1.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
	}
}
