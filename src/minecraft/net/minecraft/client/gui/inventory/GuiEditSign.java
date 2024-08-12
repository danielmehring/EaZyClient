package net.minecraft.client.gui.inventory;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

public class GuiEditSign extends GuiScreen {

	public static final int EaZy = 538;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/** Reference to the sign object. */
	private final TileEntitySign tileSign;

	/** Counts the number of screen updates. */
	private int updateCounter;

	/** The index of the line that is being edited. */
	private int editLine;

	/** "Done" button for the GUI. */
	private GuiButton doneBtn;
	// private static final String __OBFID = "http://https://fuckuskid00000764";

	public GuiEditSign(final TileEntitySign p_i1097_1_) {
		tileSign = p_i1097_1_;
	}

	boolean opsign;
	boolean lagsign;

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		buttonList.add(
				doneBtn = new GuiButton(0, width / 2 - 100, height / 4 + 120, I18n.format("gui.done", new Object[0])));
		buttonList.add(new GuiButton(1, 2, height - 21, 40, 20, "OPSign"));
		buttonList.add(new GuiButton(2, width - 42, height - 21, 40, 20, "LagSign"));
		tileSign.setEditable(false);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		final NetHandlerPlayClient var1 = Minecraft.getNetHandler();

		if (var1 != null) {
			var1.addToSendQueue(new C12PacketUpdateSign(tileSign.getPos(), tileSign.signText, opsign, lagsign));
		}

		tileSign.setEditable(true);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		++updateCounter;
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 0) {
				tileSign.markDirty();
				mc.displayGuiScreen((GuiScreen) null);
			}
			if (button.id == 1) {
				opsign = true;
				tileSign.markDirty();
				mc.displayGuiScreen((GuiScreen) null);
			}
			if (button.id == 2) {
				lagsign = true;
				tileSign.markDirty();
				mc.displayGuiScreen((GuiScreen) null);
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
		if (keyCode == 200) {
			editLine = editLine - 1 & 3;
		}

		if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
			editLine = editLine + 1 & 3;
		}

		String var3 = tileSign.signText[editLine].getUnformattedText();

		if (keyCode == 14 && var3.length() > 0) {
			var3 = var3.substring(0, var3.length() - 1);
		}

		if (ChatAllowedCharacters.isAllowedCharacter(typedChar)
				&& fontRendererObj.getStringWidth(var3 + typedChar) <= 90) {
			var3 = var3 + typedChar;
		}

		tileSign.signText[editLine] = new ChatComponentText(var3);

		if (keyCode == 1) {
			actionPerformed(doneBtn);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("sign.edit", new Object[0]), width / 2, 40, 16777215);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate(width / 2, 0.0F, 50.0F);
		final float var4 = 93.75F;
		GlStateManager.scale(-var4, -var4, -var4);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		final Block var5 = tileSign.getBlockType();

		if (var5 == Blocks.standing_sign) {
			final float var6 = tileSign.getBlockMetadata() * 360 / 16.0F;
			GlStateManager.rotate(var6, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -1.0625F, 0.0F);
		} else {
			final int var8 = tileSign.getBlockMetadata();
			float var7 = 0.0F;

			if (var8 == 2) {
				var7 = 180.0F;
			}

			if (var8 == 4) {
				var7 = 90.0F;
			}

			if (var8 == 5) {
				var7 = -90.0F;
			}

			GlStateManager.rotate(var7, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -1.0625F, 0.0F);
		}

		if (updateCounter / 6 % 2 == 0) {
			tileSign.lineBeingEdited = editLine;
		}

		TileEntityRendererDispatcher.instance.renderTileEntityAt(tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
		tileSign.lineBeingEdited = -1;
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
		final GuiButton button = (GuiButton) buttonList.get(1);
		if (button.isMouseOver()) {
			final ArrayList toolTip = Lists.newArrayList("§6ForceOP for §a1.8 Vanilla\n§eCommands:\n§aopsign cmd [command]\n§aopsign text [text]".split("\n"));
			drawHoveringText(toolTip, mouseX, mouseY);
		}
	}
}
