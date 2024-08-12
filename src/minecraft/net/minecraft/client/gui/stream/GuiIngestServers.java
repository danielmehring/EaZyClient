package net.minecraft.client.gui.stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

import tv.twitch.broadcast.IngestServer;

public class GuiIngestServers extends GuiScreen {

public static final int EaZy = 559;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_152309_a;
	private String field_152310_f;
	private GuiIngestServers.ServerList field_152311_g;
	// private static final String __OBFID = "http://https://fuckuskid00001843";

	public GuiIngestServers(final GuiScreen p_i46312_1_) {
		field_152309_a = p_i46312_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		field_152310_f = I18n.format("options.stream.ingest.title", new Object[0]);
		field_152311_g = new GuiIngestServers.ServerList(mc);

		if (!mc.getTwitchStream().func_152908_z()) {
			mc.getTwitchStream().func_152909_x();
		}

		buttonList.add(
				new GuiButton(1, width / 2 - 155, height - 24 - 6, 150, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(new GuiButton(2, width / 2 + 5, height - 24 - 6, 150, 20,
				I18n.format("options.stream.ingest.reset", new Object[0])));
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_152311_g.func_178039_p();
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		if (mc.getTwitchStream().func_152908_z()) {
			mc.getTwitchStream().func_152932_y().func_153039_l();
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(field_152309_a);
			} else {
				Minecraft.gameSettings.streamPreferredServer = "";
				Minecraft.gameSettings.saveOptions();
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		field_152311_g.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, field_152310_f, width / 2, 20, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	class ServerList extends GuiSlot {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001842";

		public ServerList(final Minecraft mcIn) {
			super(mcIn, GuiIngestServers.this.width, GuiIngestServers.this.height, 32,
					GuiIngestServers.this.height - 35, (int) (mcIn.fontRendererObj.FONT_HEIGHT * 3.5D));
			setShowSelectionBox(false);
		}

		@Override
		protected int getSize() {
			return mc.getTwitchStream().func_152925_v().length;
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
			Minecraft.gameSettings.streamPreferredServer = mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl;
			Minecraft.gameSettings.saveOptions();
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return mc.getTwitchStream().func_152925_v()[slotIndex].serverUrl
					.equals(Minecraft.gameSettings.streamPreferredServer);
		}

		@Override
		protected void drawBackground() {}

		@Override
		protected void drawSlot(final int p_180791_1_, int p_180791_2_, final int p_180791_3_, final int p_180791_4_,
				final int p_180791_5_, final int p_180791_6_) {
			final IngestServer var7 = mc.getTwitchStream().func_152925_v()[p_180791_1_];
			String var8 = var7.serverUrl.replaceAll("\\{stream_key\\}", "");
			String var9 = (int) var7.bitrateKbps + " kbps";
			String var10 = null;
			final IngestServerTester var11 = mc.getTwitchStream().func_152932_y();

			if (var11 != null) {
				if (var7 == var11.func_153040_c()) {
					var8 = EnumChatFormatting.GREEN + var8;
					var9 = (int) (var11.func_153030_h() * 100.0F) + "%";
				} else if (p_180791_1_ < var11.func_153028_p()) {
					if (var7.bitrateKbps == 0.0F) {
						var9 = EnumChatFormatting.RED + "Down!";
					}
				} else {
					var9 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
				}
			} else if (var7.bitrateKbps == 0.0F) {
				var9 = EnumChatFormatting.RED + "Down!";
			}

			p_180791_2_ -= 15;

			if (isSelected(p_180791_1_)) {
				var10 = EnumChatFormatting.BLUE + "(Preferred)";
			} else if (var7.defaultServer) {
				var10 = EnumChatFormatting.GREEN + "(Default)";
			}

			drawString(GuiIngestServers.this.fontRendererObj, var7.serverName, p_180791_2_ + 2, p_180791_3_ + 5,
					16777215);
			drawString(GuiIngestServers.this.fontRendererObj, var8, p_180791_2_ + 2,
					p_180791_3_ + GuiIngestServers.this.fontRendererObj.FONT_HEIGHT + 5 + 3, 3158064);
			drawString(GuiIngestServers.this.fontRendererObj, var9,
					getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(var9), p_180791_3_ + 5,
					8421504);

			if (var10 != null) {
				drawString(GuiIngestServers.this.fontRendererObj, var10,
						getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(var10),
						p_180791_3_ + 5 + 3 + GuiIngestServers.this.fontRendererObj.FONT_HEIGHT, 8421504);
			}
		}

		@Override
		protected int getScrollBarX() {
			return super.getScrollBarX() + 15;
		}
	}
}
