package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;

import io.netty.buffer.Unpooled;

public class GuiScreenBook extends GuiScreen {

public static final int EaZy = 506;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");

	/** The player editing the book */
	private final EntityPlayer editingPlayer;
	private final ItemStack bookObj;

	/** Whether the book is signed or can still be edited */
	private final boolean bookIsUnsigned;

	/**
	 * Whether the book's title or contents has been modified since being opened
	 */
	private boolean bookIsModified;

	/** Determines if the signing screen is open */
	private boolean bookGettingSigned;

	/** Update ticks since the gui was opened */
	private int updateCount;
	private final int bookImageWidth = 192;
	private final int bookImageHeight = 192;
	private int bookTotalPages = 1;
	private int currPage;
	private NBTTagList bookPages;
	private String bookTitle = "";
	private List field_175386_A;
	private int field_175387_B = -1;
	private GuiScreenBook.NextPageButton buttonNextPage;
	private GuiScreenBook.NextPageButton buttonPreviousPage;
	private GuiButton buttonDone;

	/** The GuiButton to sign this book. */
	private GuiButton buttonSign;
	private GuiButton buttonFinalize;
	private GuiButton buttonCancel;
	// private static final String __OBFID = "http://https://fuckuskid00000744";

	public GuiScreenBook(final EntityPlayer p_i1080_1_, final ItemStack p_i1080_2_, final boolean p_i1080_3_) {
		editingPlayer = p_i1080_1_;
		bookObj = p_i1080_2_;
		bookIsUnsigned = p_i1080_3_;

		if (p_i1080_2_.hasTagCompound()) {
			final NBTTagCompound var4 = p_i1080_2_.getTagCompound();
			bookPages = var4.getTagList("pages", 8);

			if (bookPages != null) {
				bookPages = (NBTTagList) bookPages.copy();
				bookTotalPages = bookPages.tagCount();

				if (bookTotalPages < 1) {
					bookTotalPages = 1;
				}
			}
		}

		if (bookPages == null && p_i1080_3_) {
			bookPages = new NBTTagList();
			bookPages.appendTag(new NBTTagString(""));
			bookTotalPages = 1;
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		++updateCount;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		if (bookIsUnsigned) {
			buttonList.add(buttonSign = new GuiButton(3, width / 2 - 100, 4 + bookImageHeight, 98, 20,
					I18n.format("book.signButton", new Object[0])));
			buttonList.add(buttonDone = new GuiButton(0, width / 2 + 2, 4 + bookImageHeight, 98, 20,
					I18n.format("gui.done", new Object[0])));
			buttonList.add(buttonFinalize = new GuiButton(5, width / 2 - 100, 4 + bookImageHeight, 98, 20,
					I18n.format("book.finalizeButton", new Object[0])));
			buttonList.add(buttonCancel = new GuiButton(4, width / 2 + 2, 4 + bookImageHeight, 98, 20,
					I18n.format("gui.cancel", new Object[0])));
		} else {
			buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + bookImageHeight, 200, 20,
					I18n.format("gui.done", new Object[0])));
		}

		final int var1 = (width - bookImageWidth) / 2;
		final byte var2 = 2;
		buttonList.add(buttonNextPage = new GuiScreenBook.NextPageButton(1, var1 + 120, var2 + 154, true));
		buttonList.add(buttonPreviousPage = new GuiScreenBook.NextPageButton(2, var1 + 38, var2 + 154, false));
		updateButtons();
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	private void updateButtons() {
		buttonNextPage.visible = !bookGettingSigned && (currPage < bookTotalPages - 1 || bookIsUnsigned);
		buttonPreviousPage.visible = !bookGettingSigned && currPage > 0;
		buttonDone.visible = !bookIsUnsigned || !bookGettingSigned;

		if (bookIsUnsigned) {
			buttonSign.visible = !bookGettingSigned;
			buttonCancel.visible = bookGettingSigned;
			buttonFinalize.visible = bookGettingSigned;
			buttonFinalize.enabled = bookTitle.trim().length() > 0;
		}
	}

	private void sendBookToServer(final boolean p_146462_1_) throws IOException {
		if (bookIsUnsigned && bookIsModified) {
			if (bookPages != null) {
				String var2;

				while (bookPages.tagCount() > 1) {
					var2 = bookPages.getStringTagAt(bookPages.tagCount() - 1);

					if (var2.length() != 0) {
						break;
					}

					bookPages.removeTag(bookPages.tagCount() - 1);
				}

				if (bookObj.hasTagCompound()) {
					final NBTTagCompound var6 = bookObj.getTagCompound();
					var6.setTag("pages", bookPages);
				} else {
					bookObj.setTagInfo("pages", bookPages);
				}

				var2 = "MC|BEdit";

				if (p_146462_1_) {
					var2 = "MC|BSign";
					bookObj.setTagInfo("author", new NBTTagString(editingPlayer.getName()));
					bookObj.setTagInfo("title", new NBTTagString(bookTitle.trim()));

					for (int var3 = 0; var3 < bookPages.tagCount(); ++var3) {
						String var4 = bookPages.getStringTagAt(var3);
						final ChatComponentText var5 = new ChatComponentText(var4);
						var4 = IChatComponent.Serializer.componentToJson(var5);
						bookPages.set(var3, new NBTTagString(var4));
					}

					bookObj.setItem(Items.written_book);
				}
				final PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
				var7.writeItemStackToBuffer(bookObj);
				Minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var7));
			}
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 0) {
				mc.displayGuiScreen((GuiScreen) null);
				sendBookToServer(false);
			} else if (button.id == 3 && bookIsUnsigned) {
				bookGettingSigned = true;
			} else if (button.id == 1) {
				if (currPage < bookTotalPages - 1) {
					++currPage;
				} else if (bookIsUnsigned) {
					addNewPage();

					if (currPage < bookTotalPages - 1) {
						++currPage;
					}
				}
			} else if (button.id == 2) {
				if (currPage > 0) {
					--currPage;
				}
			} else if (button.id == 5 && bookGettingSigned) {
				sendBookToServer(true);
				mc.displayGuiScreen((GuiScreen) null);
			} else if (button.id == 4 && bookGettingSigned) {
				bookGettingSigned = false;
			}

			updateButtons();
		}
	}

	private void addNewPage() {
		if (bookPages != null && bookPages.tagCount() < 50) {
			bookPages.appendTag(new NBTTagString(""));
			++bookTotalPages;
			bookIsModified = true;
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		if (bookIsUnsigned) {
			if (bookGettingSigned) {
				keyTypedInTitle(typedChar, keyCode);
			} else {
				keyTypedInBook(typedChar, keyCode);
			}
		}
	}

	/**
	 * Processes keystrokes when editing the text of a book
	 */
	private void keyTypedInBook(final char p_146463_1_, final int p_146463_2_) {
		if (GuiScreen.func_175279_e(p_146463_2_)) {
			pageInsertIntoCurrent(GuiScreen.getClipboardString());
		} else {
			switch (p_146463_2_) {
				case 14:
					final String var3 = pageGetCurrent();

					if (var3.length() > 0) {
						pageSetCurrent(var3.substring(0, var3.length() - 1));
					}

					return;

				case 28:
				case 156:
					pageInsertIntoCurrent("\n");
					return;

				default:
					if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_)) {
						pageInsertIntoCurrent(Character.toString(p_146463_1_));
					}
			}
		}
	}

	/**
	 * Processes keystrokes when editing the title of a book
	 */
	private void keyTypedInTitle(final char p_146460_1_, final int p_146460_2_) throws IOException {
		switch (p_146460_2_) {
			case 14:
				if (!bookTitle.isEmpty()) {
					bookTitle = bookTitle.substring(0, bookTitle.length() - 1);
					updateButtons();
				}

				return;

			case 28:
			case 156:
				if (!bookTitle.isEmpty()) {
					sendBookToServer(true);
					mc.displayGuiScreen((GuiScreen) null);
				}

				return;

			default:
				if (bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
					bookTitle = bookTitle + Character.toString(p_146460_1_);
					updateButtons();
					bookIsModified = true;
				}
		}
	}

	/**
	 * Returns the entire text of the current page as determined by currPage
	 */
	private String pageGetCurrent() {
		return bookPages != null && currPage >= 0 && currPage < bookPages.tagCount()
				? bookPages.getStringTagAt(currPage) : "";
	}

	/**
	 * Sets the text of the current page as determined by currPage
	 */
	private void pageSetCurrent(final String p_146457_1_) {
		if (bookPages != null && currPage >= 0 && currPage < bookPages.tagCount()) {
			bookPages.set(currPage, new NBTTagString(p_146457_1_));
			bookIsModified = true;
		}
	}

	/**
	 * Processes any text getting inserted into the current page, enforcing the
	 * page size limit
	 */
	private void pageInsertIntoCurrent(final String p_146459_1_) {
		final String var2 = pageGetCurrent();
		final String var3 = var2 + p_146459_1_;
		final int var4 = fontRendererObj.splitStringWidth(var3 + "" + EnumChatFormatting.BLACK + "_", 118);

		if (var4 <= 128 && var3.length() < 256) {
			pageSetCurrent(var3);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getTextureManager().bindTexture(bookGuiTextures);
		final int var4 = (width - bookImageWidth) / 2;
		final byte var5 = 2;
		drawTexturedModalRect(var4, var5, 0, 0, bookImageWidth, bookImageHeight);
		String var6;
		String var7;
		int var8;
		int var9;

		if (bookGettingSigned) {
			var6 = bookTitle;

			if (bookIsUnsigned) {
				if (updateCount / 6 % 2 == 0) {
					var6 = var6 + "" + EnumChatFormatting.BLACK + "_";
				} else {
					var6 = var6 + "" + EnumChatFormatting.GRAY + "_";
				}
			}

			var7 = I18n.format("book.editTitle", new Object[0]);
			var8 = fontRendererObj.getStringWidth(var7);
			fontRendererObj.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
			var9 = fontRendererObj.getStringWidth(var6);
			fontRendererObj.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
			final String var10 = I18n.format("book.byAuthor", new Object[] { editingPlayer.getName() });
			final int var11 = fontRendererObj.getStringWidth(var10);
			fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2,
					var5 + 48 + 10, 0);
			final String var12 = I18n.format("book.finalizeWarning", new Object[0]);
			fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
		} else {
			var6 = I18n.format("book.pageIndicator",
					new Object[] { currPage + 1, bookTotalPages});
			var7 = "";

			if (bookPages != null && currPage >= 0 && currPage < bookPages.tagCount()) {
				var7 = bookPages.getStringTagAt(currPage);
			}

			if (bookIsUnsigned) {
				if (fontRendererObj.getBidiFlag()) {
					var7 = var7 + "_";
				} else if (updateCount / 6 % 2 == 0) {
					var7 = var7 + "" + EnumChatFormatting.BLACK + "_";
				} else {
					var7 = var7 + "" + EnumChatFormatting.GRAY + "_";
				}
			} else if (field_175387_B != currPage) {
				if (ItemEditableBook.validBookTagContents(bookObj.getTagCompound())) {
					try {
						final IChatComponent var14 = IChatComponent.Serializer.jsonToComponent(var7);
						field_175386_A = var14 != null
								? GuiUtilRenderComponents.func_178908_a(var14, 116, fontRendererObj, true, true) : null;
					} catch (final JsonParseException var13) {
						field_175386_A = null;
					}
				} else {
					final ChatComponentText var15 = new ChatComponentText(
							EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *");
					field_175386_A = Lists.newArrayList(var15);
				}

				field_175387_B = currPage;
			}

			var8 = fontRendererObj.getStringWidth(var6);
			fontRendererObj.drawString(var6, var4 - var8 + bookImageWidth - 44, var5 + 16, 0);

			if (field_175386_A == null) {
				fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
			} else {
				var9 = Math.min(128 / fontRendererObj.FONT_HEIGHT, field_175386_A.size());

				for (int var16 = 0; var16 < var9; ++var16) {
					final IChatComponent var18 = (IChatComponent) field_175386_A.get(var16);
					fontRendererObj.drawString(var18.getUnformattedText(), var4 + 36,
							var5 + 16 + 16 + var16 * fontRendererObj.FONT_HEIGHT, 0);
				}

				final IChatComponent var17 = func_175385_b(mouseX, mouseY);

				if (var17 != null) {
					func_175272_a(var17, mouseX, mouseY);
				}
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		if (mouseButton == 0) {
			final IChatComponent var4 = func_175385_b(mouseX, mouseY);

			if (func_175276_a(var4)) {
				return;
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected boolean func_175276_a(final IChatComponent p_175276_1_) {
		final ClickEvent var2 = p_175276_1_ == null ? null : p_175276_1_.getChatStyle().getChatClickEvent();

		if (var2 == null) {
			return false;
		} else if (var2.getAction() == ClickEvent.Action.CHANGE_PAGE) {
			final String var6 = var2.getValue();

			try {
				final int var4 = Integer.parseInt(var6) - 1;

				if (var4 >= 0 && var4 < bookTotalPages && var4 != currPage) {
					currPage = var4;
					updateButtons();
					return true;
				}
			} catch (final Throwable var5) {
			}

			return false;
		} else {
			final boolean var3 = super.func_175276_a(p_175276_1_);

			if (var3 && var2.getAction() == ClickEvent.Action.RUN_COMMAND) {
				mc.displayGuiScreen((GuiScreen) null);
			}

			return var3;
		}
	}

	public IChatComponent func_175385_b(final int p_175385_1_, final int p_175385_2_) {
		if (field_175386_A == null) {
			return null;
		} else {
			final int var3 = p_175385_1_ - (width - bookImageWidth) / 2 - 36;
			final int var4 = p_175385_2_ - 2 - 16 - 16;

			if (var3 >= 0 && var4 >= 0) {
				final int var5 = Math.min(128 / fontRendererObj.FONT_HEIGHT, field_175386_A.size());

				if (var3 <= 116 && var4 < mc.fontRendererObj.FONT_HEIGHT * var5 + var5) {
					final int var6 = var4 / mc.fontRendererObj.FONT_HEIGHT;

					if (var6 >= 0 && var6 < field_175386_A.size()) {
						final IChatComponent var7 = (IChatComponent) field_175386_A.get(var6);
						int var8 = 0;
						final Iterator var9 = var7.iterator();

						while (var9.hasNext()) {
							final IChatComponent var10 = (IChatComponent) var9.next();

							if (var10 instanceof ChatComponentText) {
								var8 += mc.fontRendererObj
										.getStringWidth(((ChatComponentText) var10).getChatComponentText_TextValue());

								if (var8 > var3) {
									return var10;
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

	static class NextPageButton extends GuiButton {
		private final boolean field_146151_o;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000745";

		public NextPageButton(final int p_i46316_1_, final int p_i46316_2_, final int p_i46316_3_,
				final boolean p_i46316_4_) {
			super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
			field_146151_o = p_i46316_4_;
		}

		@Override
		public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
			if (visible) {
				final boolean var4 = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
						&& mouseY < yPosition + height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				Minecraft.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
				int var5 = 0;
				int var6 = 192;

				if (var4) {
					var5 += 23;
				}

				if (!field_146151_o) {
					var6 += 13;
				}

				drawTexturedModalRect(xPosition, yPosition, var5, var6, 23, 13);
			}
		}
	}
}
