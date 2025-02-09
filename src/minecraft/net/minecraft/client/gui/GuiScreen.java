package net.minecraft.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import me.EaZy.client.Configs;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tv.twitch.chat.ChatUserInfo;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {

	public static final int EaZy = 504;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger field_175287_a = LogManager.getLogger();
	private static final Set field_175284_f = Sets.newHashSet(new String[] { "http", "https" });
	private static final Splitter field_175285_g = Splitter.on('\n');

	/** Reference to the Minecraft object. */
	protected Minecraft mc;

	int fadingint;

	public static int targetX;
	public static int targetX2;
	public static int targetY;
	public static int targetY2;

	public static int fadeX = 0;
	public static int fadeX2 = 0;
	public static int fadeY = 0;
	public static int fadeY2 = 0;

	/**
	 * Holds a instance of RenderItem, used to draw the achievement icons on
	 * screen (is based on ItemStack)
	 */
	protected RenderItem itemRender;

	/** The width of the screen object. */
	public int width;

	/** The height of the screen object. */
	public int height;

	/** A list of all the buttons in this container. */
	protected List buttonList = Lists.newArrayList();

	/** A list of all the labels in this container. */
	protected List labelList = Lists.newArrayList();
	public boolean allowUserInput;

	/** The FontRenderer used by GuiScreen */
	protected FontRenderer fontRendererObj;

	/** The button that was just pressed. */
	private GuiButton selectedButton;
	private int eventButton;
	private long lastMouseEvent;

	/**
	 * Incremented when the game is in touchscreen mode and the screen is
	 * tapped, decremented if the screen isn't tapped. Does not appear to be
	 * used.
	 */
	private int touchValue;
	private URI urlClick;

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (Minecraft.isFading) {
			// 14 am ende
			if (fadingint == 0) {
				fadingint = 1;
			} else if (fadingint == 7) {
				try {
					mc.displayGuiScreen(Minecraft.guiScreen);
				} catch (final Exception haltdeinmaulkurzH�roBitteDanke) {
					haltdeinmaulkurzH�roBitteDanke.printStackTrace();
				}
			} else if (fadingint == 13) {
				Minecraft.isFading = false;
			}

		} else {
			fadingint = 0;
		}
		for (int i = 0; i < 3; i++) {
			if (fadeX < targetX) {

				fadeX += 1;

			}
			if (fadeX > targetX) {

				fadeX -= 1;

			}

			if (fadeX2 < targetX2) {

				fadeX2 += 1;

			}
			if (fadeX2 > targetX2) {

				fadeX2 -= 1;

			}

			if (fadeY < targetY) {
				fadeY += 1;

			}
			if (fadeY > targetY) {
				fadeY -= 1;

			}

			if (fadeY2 < targetY2) {
				fadeY2 += 1;

			}
			if (fadeY2 > targetY2) {
				fadeY2 -= 1;

			}
		}

		int var4;

		for (var4 = 0; var4 < buttonList.size(); ++var4) {
			((GuiButton) buttonList.get(var4)).drawButton(mc, mouseX, mouseY);
		}

		for (var4 = 0; var4 < labelList.size(); ++var4) {
			((GuiLabel) labelList.get(var4)).drawLabel(mc, mouseX, mouseY);
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (keyCode == 1) {
			mc.displayGuiScreen((GuiScreen) null);

			if (Minecraft.currentScreen == null) {
				mc.setIngameFocus();
			}
		}
	}

	/**
	 * Returns a string stored in the system clipboard.
	 */
	public static String getClipboardString() {
		try {
			final Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object) null);

			if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String) var0.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (final Exception var1) {}

		return "";
	}

	/**
	 * Stores the given string in the system clipboard
	 */
	public static void setClipboardString(final String copyText) {
		if (!StringUtils.isEmpty(copyText)) {
			try {
				final StringSelection var1 = new StringSelection(copyText);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, (ClipboardOwner) null);
			} catch (final Exception var2) {}
		}
	}

	protected void renderToolTip(final ItemStack itemIn, final int x, final int y) {
		final List var4 = itemIn.getTooltip(Minecraft.thePlayer, Minecraft.gameSettings.advancedItemTooltips);

		for (int var5 = 0; var5 < var4.size(); ++var5) {
			if (var5 == 0) {
				var4.set(var5, itemIn.getRarity().rarityColor + (String) var4.get(var5));
			} else {
				var4.set(var5, EnumChatFormatting.GRAY + (String) var4.get(var5));
			}
		}
		try {
			if (itemIn.hasTagCompound()) {
				if (itemIn.getTagCompound().hasKey("SkullOwner", 10)) {
					if (itemIn.getTagCompound().getCompoundTag("SkullOwner").hasKey("Name", 8)) {
						var4.add(
								"�aOwner: �7" + itemIn.getTagCompound().getCompoundTag("SkullOwner").getString("Name"));
					}
				}
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && itemIn.hasTagCompound()) {
				for (Map.Entry tag : (Set<Map.Entry>) itemIn.getTagCompound().tagMap.entrySet()) {
					String val = (tag.getValue() + "");
					if (val.length() > 75) {
						val = val.substring(0, 75) + "...";
					}
					var4.add("�8" + tag.getKey() + "�c: �7" + val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		drawHoveringText(var4, x, y);
	}

	/**
	 * Draws the text when mouse is over creative inventory tab. Params: current
	 * creative tab to be checked, current mouse x position, current mouse y
	 * position.
	 */
	protected void drawCreativeTabHoveringText(final String tabName, final int mouseX, final int mouseY) {
		drawHoveringText(Arrays.asList(new String[] { tabName }), mouseX, mouseY);
	}

	protected void drawHoveringText(final List textLines, final int x, final int y) {
		if (!textLines.isEmpty()) {
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int var4 = 0;
			final Iterator var5 = textLines.iterator();

			while (var5.hasNext()) {
				final String var6 = (String) var5.next();
				final int var7 = fontRendererObj.getStringWidth(var6);

				if (var7 > var4) {
					var4 = var7;
				}
			}

			int var14 = x + 12;
			int var15 = y - 12;
			int var8 = 8;

			if (textLines.size() > 1) {
				var8 += 2 + (textLines.size() - 1) * 10;
			}

			if (var14 + var4 > width) {
				var14 -= 28 + var4;
			}

			if (var15 + var8 + 6 > height) {
				var15 = height - var8 - 6;
			}

			zLevel = 300.0F;
			itemRender.zLevel = 300.0F;
			final int var9 = -267386864;
			drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
			drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
			drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
			drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
			drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
			final int var10 = 1347420415;
			final int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
			drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
			drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
			drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
			drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);

			for (int var12 = 0; var12 < textLines.size(); ++var12) {
				final String var13 = (String) textLines.get(var12);
				fontRendererObj.func_175063_a(var13, var14, var15, -1);

				if (var12 == 0) {
					var15 += 2;
				}

				var15 += 10;
			}

			zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

	protected void func_175272_a(final IChatComponent p_175272_1_, final int p_175272_2_, final int p_175272_3_) {
		if (p_175272_1_ != null && p_175272_1_.getChatStyle().getChatHoverEvent() != null) {
			final HoverEvent var4 = p_175272_1_.getChatStyle().getChatHoverEvent();

			if (var4.getAction() == HoverEvent.Action.SHOW_ITEM) {
				ItemStack var5 = null;

				try {
					final NBTTagCompound var6 = JsonToNBT.parse(var4.getValue().getUnformattedText());

					if (var6 instanceof NBTTagCompound) {
						var5 = ItemStack.loadItemStackFromNBT(var6);
					}
				} catch (final NBTException var11) {}

				if (var5 != null) {
					renderToolTip(var5, p_175272_2_, p_175272_3_);
				} else {
					drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
				}
			} else {
				String var8;

				if (var4.getAction() == HoverEvent.Action.SHOW_ENTITY) {
					if (Minecraft.gameSettings.advancedItemTooltips) {
						try {
							final NBTTagCompound var12 = JsonToNBT.parse(var4.getValue().getUnformattedText());

							if (var12 instanceof NBTTagCompound) {
								final ArrayList var14 = Lists.newArrayList();
								final NBTTagCompound var7 = var12;
								var14.add(var7.getString("name"));

								if (var7.hasKey("type", 8)) {
									var8 = var7.getString("type");
									var14.add("Type: " + var8 + " (" + EntityList.func_180122_a(var8) + ")");
								}

								var14.add(var7.getString("id"));
								drawHoveringText(var14, p_175272_2_, p_175272_3_);
							} else {
								drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_,
										p_175272_3_);
							}
						} catch (final NBTException var10) {
							drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_,
									p_175272_3_);
						}
					}
				} else if (var4.getAction() == HoverEvent.Action.SHOW_TEXT) {
					drawHoveringText(field_175285_g.splitToList(var4.getValue().getFormattedText()), p_175272_2_,
							p_175272_3_);
				} else if (var4.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
					final StatBase var13 = StatList.getOneShotStat(var4.getValue().getUnformattedText());

					if (var13 != null) {
						final IChatComponent var15 = var13.getStatName();
						final ChatComponentTranslation var16 = new ChatComponentTranslation(
								"stats.tooltip.type." + (var13.isAchievement() ? "achievement" : "statistic"),
								new Object[0]);
						var16.getChatStyle().setItalic(true);
						var8 = var13 instanceof Achievement ? ((Achievement) var13).getDescription() : null;
						final ArrayList var9 = Lists
								.newArrayList(new String[] { var15.getFormattedText(), var16.getFormattedText() });

						if (var8 != null) {
							var9.addAll(fontRendererObj.listFormattedStringToWidth(var8, 150));
						}

						drawHoveringText(var9, p_175272_2_, p_175272_3_);
					} else {
						drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!",
								p_175272_2_, p_175272_3_);
					}
				}
			}

			GlStateManager.disableLighting();
		}
	}

	protected void func_175274_a(final String p_175274_1_, final boolean p_175274_2_) {}

	protected boolean func_175276_a(final IChatComponent p_175276_1_) {
		if (p_175276_1_ == null) {
			return false;
		} else {
			final ClickEvent var2 = p_175276_1_.getChatStyle().getChatClickEvent();

			if (isShiftKeyDown()) {
				if (p_175276_1_.getChatStyle().getInsertion() != null) {
					func_175274_a(p_175276_1_.getChatStyle().getInsertion(), false);
				}
			} else if (var2 != null) {
				URI var3;

				if (var2.getAction() == ClickEvent.Action.OPEN_URL) {
					if (!Minecraft.gameSettings.chatLinks) {
						return false;
					}

					try {
						var3 = new URI(var2.getValue());

						if (!field_175284_f.contains(var3.getScheme().toLowerCase())) {
							throw new URISyntaxException(var2.getValue(),
									"Unsupported protocol: " + var3.getScheme().toLowerCase());
						}

						if (Minecraft.gameSettings.chatLinksPrompt) {
							urlClick = var3;
							mc.displayGuiScreen(new GuiConfirmOpenLink(this, var2.getValue(), 31102009, false));
						} else {
							openLink(var3);
						}
					} catch (final URISyntaxException var4) {
						field_175287_a.error("Can\'t open url for " + var2, var4);
					}
				} else if (var2.getAction() == ClickEvent.Action.OPEN_FILE) {
					var3 = new File(var2.getValue()).toURI();
					openLink(var3);
				} else if (var2.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
					func_175274_a(var2.getValue(), true);
				} else if (var2.getAction() == ClickEvent.Action.RUN_COMMAND) {
					func_175281_b(var2.getValue(), false);
				} else if (var2.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
					final ChatUserInfo var5 = mc.getTwitchStream().func_152926_a(var2.getValue());

					if (var5 != null) {
						mc.displayGuiScreen(new GuiTwitchUserMode(mc.getTwitchStream(), var5));
					} else {
						field_175287_a.error("Tried to handle twitch user but couldn\'t find them!");
					}
				} else {
					field_175287_a.error("Don\'t know how to handle " + var2);
				}

				return true;
			}

			return false;
		}
	}

	public void func_175275_f(final String p_175275_1_) {
		func_175281_b(p_175275_1_, true);
	}

	public void func_175281_b(final String p_175281_1_, final boolean p_175281_2_) {
		if (p_175281_2_) {
			mc.ingameGUI.getChatGUI().addToSentMessages(p_175281_1_);
		}

		Minecraft.thePlayer.sendChatMessage(p_175281_1_);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		if (mouseButton == 0) {
			for (int var4 = 0; var4 < buttonList.size(); ++var4) {
				final GuiButton var5 = (GuiButton) buttonList.get(var4);

				if (var5.mousePressed(mc, mouseX, mouseY)) {
					selectedButton = var5;
					var5.playPressSound(mc.getSoundHandler());
					actionPerformed(var5);
				}
			}
		}
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		if (selectedButton != null && state == 0) {
			selectedButton.mouseReleased(mouseX, mouseY);
			selectedButton = null;
		}
	}

	/**
	 * Called when a mouse button is pressed and the mouse is moved around.
	 * Parameters are : mouseX, mouseY, lastButtonClicked & timeSinceMouseClick.
	 */
	protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton,
			final long timeSinceLastClick) {}

	protected void actionPerformed(final GuiButton button) throws IOException {}

	/**
	 * Causes the screen to lay out its subcomponents again. This is the
	 * equivalent of the Java call Container.validate()
	 */
	public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
		this.mc = mc;
		itemRender = mc.getRenderItem();
		fontRendererObj = mc.fontRendererObj;
		this.width = width;
		this.height = height;
		buttonList.clear();
		initGui();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {}

	/**
	 * Delegates mouse and keyboard input.
	 */
	public void handleInput() throws IOException {
		if (Mouse.isCreated()) {
			while (Mouse.next()) {
				handleMouseInput();
			}
		}

		if (Keyboard.isCreated()) {
			while (Keyboard.next()) {
				handleKeyboardInput();
			}
		}
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput() throws IOException {
		final int var1 = Mouse.getEventX() * width / Minecraft.displayWidth;
		final int var2 = height - Mouse.getEventY() * height / Minecraft.displayHeight - 1;
		final int var3 = Mouse.getEventButton();

		if (Mouse.getEventButtonState()) {
			if (Minecraft.gameSettings.touchscreen && touchValue++ > 0) {
				return;
			}

			eventButton = var3;
			lastMouseEvent = Minecraft.getSystemTime();
			mouseClicked(var1, var2, eventButton);
		} else if (var3 != -1) {
			if (Minecraft.gameSettings.touchscreen && --touchValue > 0) {
				return;
			}

			eventButton = -1;
			mouseReleased(var1, var2, var3);
		} else if (eventButton != -1 && lastMouseEvent > 0L) {
			final long var4 = Minecraft.getSystemTime() - lastMouseEvent;
			mouseClickMove(var1, var2, eventButton, var4);
		}
	}

	/**
	 * Handles keyboard input.
	 */
	public void handleKeyboardInput() throws IOException {
		if (Keyboard.getEventKeyState()) {
			keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}

		mc.dispatchKeypresses();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	public void onGuiClosed() {}

	/**
	 * Draws either a gradient over the background screen (when it exists) or a
	 * flat gradient over background.png
	 */
	public void drawDefaultBackground(final int mouseX, final int mouseY) {
		drawWorldBackground(0, mouseX, mouseY);
	}

	public void drawWorldBackground(final int tint, final int mouseX, final int mouseY) {
		if (Minecraft.theWorld != null) {
			drawGradientRect(0, 0, width, height, -1072689136, -804253680);
		} else {
			GlStateManager.color(0.575f, 0.575f, 0.575f, 1.0f);
			Minecraft.getTextureManager().bindTexture(GuiMainMenu.bg);
			final ScaledResolution scaledRes = RenderUtils.newScaledResolution();
			if (Configs.bgMoving) {
				drawScaledCustomSizeModalRectMoving(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
						scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(),
						scaledRes.getScaledHeight(), mouseX, mouseY);
			} else
				drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
						scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(),
						scaledRes.getScaledHeight());
		}
	}

	/**
	 * Draws the background (i is always 0 as of 1.2.2)
	 */
	public void drawBackground(final int tint) {
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		final Tessellator var2 = Tessellator.getInstance();
		final WorldRenderer var3 = var2.getWorldRenderer();
		Minecraft.getTextureManager().bindTexture(optionsBackground);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		final float var4 = 32.0F;
		var3.startDrawingQuads();
		var3.func_178991_c(4210752);
		var3.addVertexWithUV(0.0D, height, 0.0D, 0.0D, height / var4 + tint);
		var3.addVertexWithUV(width, height, 0.0D, width / var4, height / var4 + tint);
		var3.addVertexWithUV(width, 0.0D, 0.0D, width / var4, tint);
		var3.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, tint);
		var2.draw();
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		if (id == 31102009) {
			if (result) {
				openLink(urlClick);
			}

			urlClick = null;
			mc.displayGuiScreen(this);
		}
	}

	private void openLink(final URI p_175282_1_) {
		try {
			final Class var2 = Class.forName("java.awt.Desktop");
			final Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { p_175282_1_ });
		} catch (final Throwable var4) {
			field_175287_a.error("Couldn\'t open link", var4);
		}
	}

	/**
	 * Returns true if either windows ctrl key is down or if either mac meta key
	 * is down
	 */
	public static boolean isCtrlKeyDown() {
		return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)
				: Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
	}

	/**
	 * Returns true if either shift key is down
	 */
	public static boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}

	public static boolean func_175283_s() {
		return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
	}

	public static boolean func_175277_d(final int p_175277_0_) {
		return p_175277_0_ == 45 && isCtrlKeyDown();
	}

	public static boolean func_175279_e(final int p_175279_0_) {
		return p_175279_0_ == 47 && isCtrlKeyDown();
	}

	public static boolean func_175280_f(final int p_175280_0_) {
		return p_175280_0_ == 46 && isCtrlKeyDown();
	}

	public static boolean func_175278_g(final int p_175278_0_) {
		return p_175278_0_ == 30 && isCtrlKeyDown();
	}

	public void func_175273_b(final Minecraft mcIn, final int p_175273_2_, final int p_175273_3_) {
		setWorldAndResolution(mcIn, p_175273_2_, p_175273_3_);
	}
}
