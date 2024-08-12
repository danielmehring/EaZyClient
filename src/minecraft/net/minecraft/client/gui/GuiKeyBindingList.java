package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {

public static final int EaZy = 481;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiControls field_148191_k;
	private final Minecraft mc;
	private final GuiListExtended.IGuiListEntry[] listEntries;
	private int maxListLabelWidth = 0;
	// private static final String __OBFID = "http://https://fuckuskid00000732";

	public GuiKeyBindingList(final GuiControls p_i45031_1_, final Minecraft mcIn) {
		super(mcIn, p_i45031_1_.width, p_i45031_1_.height, 63, p_i45031_1_.height - 32, 20);
		field_148191_k = p_i45031_1_;
		mc = mcIn;
		final KeyBinding[] var3 = ArrayUtils.clone(Minecraft.gameSettings.keyBindings);
		listEntries = new GuiListExtended.IGuiListEntry[var3.length + KeyBinding.getKeybinds().size()];
		Arrays.sort(var3);
		int var4 = 0;
		String var5 = null;
		final KeyBinding[] var6 = var3;
		final int var7 = var3.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			final KeyBinding var9 = var6[var8];
			final String var10 = var9.getKeyCategory();

			if (!var10.equals(var5)) {
				var5 = var10;
				listEntries[var4++] = new GuiKeyBindingList.CategoryEntry(var10);
			}

			final int var11 = mcIn.fontRendererObj.getStringWidth(I18n.format(var9.getKeyDescription(), new Object[0]));

			if (var11 > maxListLabelWidth) {
				maxListLabelWidth = var11;
			}

			listEntries[var4++] = new GuiKeyBindingList.KeyEntry(var9, null);
		}
	}

	@Override
	protected int getSize() {
		return listEntries.length;
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public GuiListExtended.IGuiListEntry getListEntry(final int p_148180_1_) {
		return listEntries[p_148180_1_];
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 15;
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	public class CategoryEntry implements GuiListExtended.IGuiListEntry {
		private final String labelText;
		private final int labelWidth;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000734";

		public CategoryEntry(final String p_i45028_2_) {
			labelText = I18n.format(p_i45028_2_, new Object[0]);
			labelWidth = mc.fontRendererObj.getStringWidth(labelText);
		}

		@Override
		public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
				final int mouseX, final int mouseY, final boolean isSelected) {
			mc.fontRendererObj.drawString(labelText, Minecraft.currentScreen.width / 2 - labelWidth / 2,
					y + slotHeight - mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
		}

		@Override
		public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
				final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
			return false;
		}

		@Override
		public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent,
				final int relativeX, final int relativeY) {}

		@Override
		public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {}
	}

	public class KeyEntry implements GuiListExtended.IGuiListEntry {
		private final KeyBinding field_148282_b;
		private final String field_148283_c;
		private final GuiButton btnChangeKeyBinding;
		private final GuiButton btnReset;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000735";

		private KeyEntry(final KeyBinding p_i45029_2_) {
			field_148282_b = p_i45029_2_;
			field_148283_c = I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]);
			btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18,
					I18n.format(p_i45029_2_.getKeyDescription(), new Object[0]));
			btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
		}

		@Override
		public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
				final int mouseX, final int mouseY, final boolean isSelected) {
			final boolean var9 = field_148191_k.buttonId == field_148282_b;
			mc.fontRendererObj.drawString(field_148283_c, x + 90 - maxListLabelWidth,
					y + slotHeight / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
			btnReset.xPosition = x + 190;
			btnReset.yPosition = y;
			btnReset.enabled = field_148282_b.getKeyCode() != field_148282_b.getKeyCodeDefault();
			btnReset.drawButton(mc, mouseX, mouseY);
			btnChangeKeyBinding.xPosition = x + 105;
			btnChangeKeyBinding.yPosition = y;
			btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(field_148282_b.getKeyCode());
			boolean var10 = false;

			if (field_148282_b.getKeyCode() != 0) {
				final KeyBinding[] var11 = Minecraft.gameSettings.keyBindings;
				final int var12 = var11.length;

				for (int var13 = 0; var13 < var12; ++var13) {
					final KeyBinding var14 = var11[var13];

					if (var14 != field_148282_b && var14.getKeyCode() == field_148282_b.getKeyCode()) {
						var10 = true;
						break;
					}
				}
			}

			if (var9) {
				btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW
						+ btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
			} else if (var10) {
				btnChangeKeyBinding.displayString = EnumChatFormatting.RED + btnChangeKeyBinding.displayString;
			}

			btnChangeKeyBinding.drawButton(mc, mouseX, mouseY);
		}

		@Override
		public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
				final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
			if (btnChangeKeyBinding.mousePressed(mc, p_148278_2_, p_148278_3_)) {
				field_148191_k.buttonId = field_148282_b;
				return true;
			} else if (btnReset.mousePressed(mc, p_148278_2_, p_148278_3_)) {
				Minecraft.gameSettings.setOptionKeyBinding(field_148282_b, field_148282_b.getKeyCodeDefault());
				KeyBinding.resetKeyBindingArrayAndHash();
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent,
				final int relativeX, final int relativeY) {
			btnChangeKeyBinding.mouseReleased(x, y);
			btnReset.mouseReleased(x, y);
		}

		@Override
		public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {}

		KeyEntry(final KeyBinding p_i45030_2_, final Object p_i45030_3_) {
			this(p_i45030_2_);
		}
	}
}
