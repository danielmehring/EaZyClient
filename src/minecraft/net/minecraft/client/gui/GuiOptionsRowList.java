package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import java.util.List;

import com.google.common.collect.Lists;

public class GuiOptionsRowList extends GuiListExtended {

public static final int EaZy = 495;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List field_148184_k = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00000677";

	public GuiOptionsRowList(final Minecraft mcIn, final int p_i45015_2_, final int p_i45015_3_, final int p_i45015_4_,
			final int p_i45015_5_, final int p_i45015_6_, final GameSettings.Options... p_i45015_7_) {
		super(mcIn, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
		field_148163_i = false;

		for (int var8 = 0; var8 < p_i45015_7_.length; var8 += 2) {
			final GameSettings.Options var9 = p_i45015_7_[var8];
			final GameSettings.Options var10 = var8 < p_i45015_7_.length - 1 ? p_i45015_7_[var8 + 1] : null;
			final GuiButton var11 = func_148182_a(mcIn, p_i45015_2_ / 2 - 155, 0, var9);
			final GuiButton var12 = func_148182_a(mcIn, p_i45015_2_ / 2 - 155 + 160, 0, var10);
			field_148184_k.add(new GuiOptionsRowList.Row(var11, var12));
		}
	}

	private GuiButton func_148182_a(final Minecraft mcIn, final int p_148182_2_, final int p_148182_3_,
			final GameSettings.Options p_148182_4_) {
		if (p_148182_4_ == null) {
			return null;
		} else {
			final int var5 = p_148182_4_.returnEnumOrdinal();
			return p_148182_4_.getEnumFloat() ? new GuiOptionSlider(var5, p_148182_2_, p_148182_3_, p_148182_4_)
					: new GuiOptionButton(var5, p_148182_2_, p_148182_3_, p_148182_4_,
							Minecraft.gameSettings.getKeyBinding(p_148182_4_));
		}
	}

	public GuiOptionsRowList.Row func_180792_c(final int p_180792_1_) {
		return (GuiOptionsRowList.Row) field_148184_k.get(p_180792_1_);
	}

	@Override
	protected int getSize() {
		return field_148184_k.size();
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return 400;
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 32;
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public GuiListExtended.IGuiListEntry getListEntry(final int p_148180_1_) {
		return func_180792_c(p_148180_1_);
	}

	public static class Row implements GuiListExtended.IGuiListEntry {
		private final Minecraft field_148325_a = Minecraft.getMinecraft();
		private final GuiButton field_148323_b;
		private final GuiButton field_148324_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000678";

		public Row(final GuiButton p_i45014_1_, final GuiButton p_i45014_2_) {
			field_148323_b = p_i45014_1_;
			field_148324_c = p_i45014_2_;
		}

		@Override
		public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
				final int mouseX, final int mouseY, final boolean isSelected) {
			if (field_148323_b != null) {
				field_148323_b.yPosition = y;
				field_148323_b.drawButton(field_148325_a, mouseX, mouseY);
			}

			if (field_148324_c != null) {
				field_148324_c.yPosition = y;
				field_148324_c.drawButton(field_148325_a, mouseX, mouseY);
			}
		}

		@Override
		public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
				final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
			if (field_148323_b.mousePressed(field_148325_a, p_148278_2_, p_148278_3_)) {
				if (field_148323_b instanceof GuiOptionButton) {
					Minecraft.gameSettings.setOptionValue(((GuiOptionButton) field_148323_b).returnEnumOptions(), 1);
					field_148323_b.displayString = Minecraft.gameSettings
							.getKeyBinding(GameSettings.Options.getEnumOptions(field_148323_b.id));
				}

				return true;
			} else if (field_148324_c != null
					&& field_148324_c.mousePressed(field_148325_a, p_148278_2_, p_148278_3_)) {
				if (field_148324_c instanceof GuiOptionButton) {
					Minecraft.gameSettings.setOptionValue(((GuiOptionButton) field_148324_c).returnEnumOptions(), 1);
					field_148324_c.displayString = Minecraft.gameSettings
							.getKeyBinding(GameSettings.Options.getEnumOptions(field_148324_c.id));
				}

				return true;
			} else {
				return false;
			}
		}

		@Override
		public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent,
				final int relativeX, final int relativeY) {
			if (field_148323_b != null) {
				field_148323_b.mouseReleased(x, y);
			}

			if (field_148324_c != null) {
				field_148324_c.mouseReleased(x, y);
			}
		}

		@Override
		public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {}
	}
}
