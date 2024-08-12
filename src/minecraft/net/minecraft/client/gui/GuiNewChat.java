package net.minecraft.client.gui;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.darkmagician6.eventapi.EventManager;
import com.google.common.collect.Lists;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.events.ChatInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

public class GuiNewChat extends Gui {

public static final int EaZy = 491;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final Minecraft mc;

	/** A list of messages previously sent through the chat GUI */
	private final List sentMessages = Lists.newArrayList();

	/** Chat lines to be displayed in the chat box */
	private final List chatLines = Lists.newArrayList();
	private final List chatLines2 = Lists.newArrayList();
	private int scrollPos;
	private boolean isScrolled;

	public GuiNewChat(final Minecraft mcIn) {
		mc = mcIn;
	}

	public void drawChat(final int p_146230_1_) {
		if (Minecraft.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN
				&& !(Minecraft.currentScreen instanceof ClickGUI)) {
			final int var2 = getLineCount();
			boolean var3 = false;
			int var4 = 0;
			final int var5 = chatLines2.size();
			final float var6 = Minecraft.gameSettings.chatOpacity * 0.9F + 0.1F;

			if (var5 > 0) {
				if (getChatOpen()) {
					var3 = true;
				}

				final float var7 = getChatScale();
				final int var8 = MathHelper.ceiling_float_int(getChatWidth() / var7);
				GlStateManager.pushMatrix();
				GlStateManager.translate(2.0F, 20.0F, 0.0F);
				GlStateManager.scale(var7, var7, 1.0F);
				int var9;
				int var11;
				int var14;

				for (var9 = 0; var9 + scrollPos < chatLines2.size() && var9 < var2; ++var9) {
					final ChatLine var10 = (ChatLine) chatLines2.get(var9 + scrollPos);

					if (var10 != null) {
						var11 = p_146230_1_ - var10.getUpdatedCounter();

						if (var11 < 200 || var3) {
							double var12 = var11 / 200.0D;
							var12 = 1.0D - var12;
							var12 *= 10.0D;
							var12 = MathHelper.clamp_double(var12, 0.0D, 1.0D);
							var12 *= var12;
							var14 = (int) (255.0D * var12);

							if (var3) {
								var14 = 255;
							}

							var14 = (int) (var14 * var6);
							++var4;

							if (var14 > 3) {
								final byte var15 = 0;
								final int var16 = -var9 * 9;
								drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
								final String var17 = var10.getChatComponent().getFormattedText();
								GlStateManager.enableBlend();
								mc.fontRendererObj.drawStringWithShadow(var17, var15, var16 - 8,
										16777215 + (var14 << 24));
								GlStateManager.disableAlpha();
								GlStateManager.disableBlend();
							}
						}
					}
				}

				if (var3) {
					var9 = mc.fontRendererObj.FONT_HEIGHT;
					GlStateManager.translate(-3.0F, 0.0F, 0.0F);
					final int var18 = var5 * var9 + var5;
					var11 = var4 * var9 + var4;
					final int var19 = scrollPos * var11 / var5;
					final int var13 = var11 * var11 / var18;

					if (var18 != var11) {
						var14 = var19 > 0 ? 170 : 96;
						final int var20 = isScrolled ? 13382451 : 3355562;
						drawRect(0, -var19, 2, -var19 - var13, var20 + (var14 << 24));
						drawRect(2, -var19, 1, -var19 - var13, 13421772 + (var14 << 24));
					}
				}

				GlStateManager.popMatrix();
			}
		}
	}

	/**
	 * Clears the chat.
	 */
	public void clearChatMessages() {
		chatLines2.clear();
		chatLines.clear();
		sentMessages.clear();
	}

	public void printChatMessage(final IChatComponent p_146227_1_) {
		printChatMessageWithOptionalDeletion(p_146227_1_, 0);
	}

	/**
	 * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing
	 * Chat Line of that ID from the GUI
	 */
	public void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, final int p_146234_2_) {
		final ChatInputEvent event = new ChatInputEvent(p_146234_1_, chatLines2);
		EventManager.call(event);
		if (event.isCancelled()) {
			return;
		}
		p_146234_1_ = event.getComponent();
		setChatLine(p_146234_1_, p_146234_2_, mc.ingameGUI.getUpdateCounter(), false);
		logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
	}

	private void setChatLine(final IChatComponent p_146237_1_, final int p_146237_2_, final int p_146237_3_,
			final boolean p_146237_4_) {
		if (p_146237_2_ != 0) {
			deleteChatLine(p_146237_2_);
		}

		final int var5 = MathHelper.floor_float(getChatWidth() / getChatScale());
		final List var6 = GuiUtilRenderComponents.func_178908_a(p_146237_1_, var5, mc.fontRendererObj, false, false);
		final boolean var7 = getChatOpen();
		IChatComponent var9;

		for (final Iterator var8 = var6.iterator(); var8.hasNext(); chatLines2.add(0,
				new ChatLine(p_146237_3_, var9, p_146237_2_))) {
			var9 = (IChatComponent) var8.next();

			if (var7 && scrollPos > 0) {
				isScrolled = true;
				scroll(1);
			}
		}

		while (chatLines2.size() > 100) {
			chatLines2.remove(chatLines2.size() - 1);
		}

		if (!p_146237_4_) {
			chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));

			while (chatLines.size() > 100) {
				chatLines.remove(chatLines.size() - 1);
			}
		}
	}

	public void refreshChat() {
		chatLines2.clear();
		resetScroll();

		for (int var1 = chatLines.size() - 1; var1 >= 0; --var1) {
			final ChatLine var2 = (ChatLine) chatLines.get(var1);
			setChatLine(var2.getChatComponent(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
		}
	}

	/**
	 * Gets the list of messages previously sent through the chat GUI
	 */
	public List getSentMessages() {
		return sentMessages;
	}

	/**
	 * Adds this string to the list of sent messages, for recall using the
	 * up/down arrow keys
	 */
	public void addToSentMessages(String p_146239_1_) {
		if (p_146239_1_.length() > 100) {
			p_146239_1_ = p_146239_1_.substring(0, 100);
		}
		if (sentMessages.isEmpty() || !((String) sentMessages.get(sentMessages.size() - 1)).equals(p_146239_1_)) {
			sentMessages.add(p_146239_1_);
		}
	}

	/**
	 * Resets the chat scroll (executed when the GUI is closed, among others)
	 */
	public void resetScroll() {
		scrollPos = 0;
		isScrolled = false;
	}

	/**
	 * Scrolls the chat by the given number of lines.
	 */
	public void scroll(final int p_146229_1_) {
		scrollPos += p_146229_1_;
		final int var2 = chatLines2.size();

		if (scrollPos > var2 - getLineCount()) {
			scrollPos = var2 - getLineCount();
		}

		if (scrollPos <= 0) {
			scrollPos = 0;
			isScrolled = false;
		}
	}

	/**
	 * Gets the chat component under the mouse
	 */
	public IChatComponent getChatComponent(final int p_146236_1_, final int p_146236_2_) {
		if (!getChatOpen()) {
			return null;
		} else {
			final ScaledResolution var3 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
			final int var4 = var3.getScaleFactor();
			final float var5 = getChatScale();
			int var6 = p_146236_1_ / var4 - 3;
			int var7 = p_146236_2_ / var4 - 27;
			var6 = MathHelper.floor_float(var6 / var5);
			var7 = MathHelper.floor_float(var7 / var5);

			if (var6 >= 0 && var7 >= 0) {
				final int var8 = Math.min(getLineCount(), chatLines2.size());

				if (var6 <= MathHelper.floor_float(getChatWidth() / getChatScale())
						&& var7 < mc.fontRendererObj.FONT_HEIGHT * var8 + var8) {
					final int var9 = var7 / mc.fontRendererObj.FONT_HEIGHT + scrollPos;

					if (var9 >= 0 && var9 < chatLines2.size()) {
						final ChatLine var10 = (ChatLine) chatLines2.get(var9);
						int var11 = 0;
						final Iterator var12 = var10.getChatComponent().iterator();

						while (var12.hasNext()) {
							final IChatComponent var13 = (IChatComponent) var12.next();

							if (var13 instanceof ChatComponentText) {
								var11 += mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(
										((ChatComponentText) var13).getChatComponentText_TextValue(), false));

								if (var11 > var6) {
									return var13;
								}
							}
						}
					}

					return null;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	/**
	 * Returns true if the chat GUI is open
	 */
	public boolean getChatOpen() {
		return Minecraft.currentScreen instanceof GuiChat;
	}

	/**
	 * finds and deletes a Chat line by ID
	 */
	public void deleteChatLine(final int p_146242_1_) {
		Iterator var2 = chatLines2.iterator();
		ChatLine var3;

		while (var2.hasNext()) {
			var3 = (ChatLine) var2.next();

			if (var3.getChatLineID() == p_146242_1_) {
				var2.remove();
			}
		}

		var2 = chatLines.iterator();

		while (var2.hasNext()) {
			var3 = (ChatLine) var2.next();

			if (var3.getChatLineID() == p_146242_1_) {
				var2.remove();
				break;
			}
		}
	}

	public int getChatWidth() {
		return calculateChatboxWidth(Minecraft.gameSettings.chatWidth);
	}

	public int getChatHeight() {
		return calculateChatboxHeight(
				getChatOpen() ? Minecraft.gameSettings.chatHeightFocused : Minecraft.gameSettings.chatHeightUnfocused);
	}

	/**
	 * Returns the chatscale from mc.gameSettings.chatScale
	 */
	public float getChatScale() {
		return Minecraft.gameSettings.chatScale;
	}

	public static int calculateChatboxWidth(final float p_146233_0_) {
		final short var1 = 320;
		final byte var2 = 40;
		return MathHelper.floor_float(p_146233_0_ * (var1 - var2) + var2);
	}

	public static int calculateChatboxHeight(final float p_146243_0_) {
		final short var1 = 180;
		final byte var2 = 20;
		return MathHelper.floor_float(p_146243_0_ * (var1 - var2) + var2);
	}

	public int getLineCount() {
		return getChatHeight() / 9;
	}
}
