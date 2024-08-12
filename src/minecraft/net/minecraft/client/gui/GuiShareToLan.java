package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

import java.io.IOException;

public class GuiShareToLan extends GuiScreen {

public static final int EaZy = 515;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_146598_a;
	private GuiButton field_146596_f;
	private GuiButton field_146597_g;
	private String field_146599_h = "survival";
	private boolean field_146600_i;
	// private static final String __OBFID = "http://https://fuckuskid00000713";

	public GuiShareToLan(final GuiScreen p_i1055_1_) {
		field_146598_a = p_i1055_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(101, width / 2 - 155, height - 28, 150, 20,
				I18n.format("lanServer.start", new Object[0])));
		buttonList
				.add(new GuiButton(102, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
		buttonList.add(field_146597_g = new GuiButton(104, width / 2 - 155, 100, 150, 20,
				I18n.format("selectWorld.gameMode", new Object[0])));
		buttonList.add(field_146596_f = new GuiButton(103, width / 2 + 5, 100, 150, 20,
				I18n.format("selectWorld.allowCommands", new Object[0])));
		func_146595_g();
	}

	private void func_146595_g() {
		field_146597_g.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + " "
				+ I18n.format("selectWorld.gameMode." + field_146599_h, new Object[0]);
		field_146596_f.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";

		if (field_146600_i) {
			field_146596_f.displayString = field_146596_f.displayString + I18n.format("options.on", new Object[0]);
		} else {
			field_146596_f.displayString = field_146596_f.displayString + I18n.format("options.off", new Object[0]);
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 102) {
			mc.displayGuiScreen(field_146598_a);
		} else if (button.id == 104) {
			if (field_146599_h.equals("spectator")) {
				field_146599_h = "creative";
			} else if (field_146599_h.equals("creative")) {
				field_146599_h = "adventure";
			} else if (field_146599_h.equals("adventure")) {
				field_146599_h = "survival";
			} else {
				field_146599_h = "spectator";
			}

			func_146595_g();
		} else if (button.id == 103) {
			field_146600_i = !field_146600_i;
			func_146595_g();
		} else if (button.id == 101) {
			mc.displayGuiScreen((GuiScreen) null);
			final String var2 = mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(field_146599_h),
					field_146600_i);
			Object var3;

			if (var2 != null) {
				var3 = new ChatComponentTranslation("commands.publish.started", new Object[] { var2 });
			} else {
				var3 = new ChatComponentText("commands.publish.failed");
			}

			mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent) var3);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("lanServer.title", new Object[0]), width / 2, 50, 16777215);
		drawCenteredString(fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), width / 2, 82,
				16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
