package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsClickableScrolledSelectionList;
import net.minecraft.realms.Tezzelator;

import org.lwjgl.input.Mouse;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot {

public static final int EaZy = 464;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RealmsClickableScrolledSelectionList field_178046_u;
	// private static final String __OBFID = "http://https://fuckuskid00001939";

	public GuiClickableScrolledSelectionListProxy(final RealmsClickableScrolledSelectionList p_i45526_1_,
			final int p_i45526_2_, final int p_i45526_3_, final int p_i45526_4_, final int p_i45526_5_,
			final int p_i45526_6_) {
		super(Minecraft.getMinecraft(), p_i45526_2_, p_i45526_3_, p_i45526_4_, p_i45526_5_, p_i45526_6_);
		field_178046_u = p_i45526_1_;
	}

	@Override
	protected int getSize() {
		return field_178046_u.getItemCount();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was
	 * double clicked or not
	 */
	@Override
	protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
			final int mouseY) {
		field_178046_u.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(final int slotIndex) {
		return field_178046_u.isSelectedItem(slotIndex);
	}

	@Override
	protected void drawBackground() {
		field_178046_u.renderBackground();
	}

	@Override
	protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_,
			final int p_180791_5_, final int p_180791_6_) {
		field_178046_u.renderItem(p_180791_1_, p_180791_2_, p_180791_3_, p_180791_4_, p_180791_5_, p_180791_6_);
	}

	public int func_178044_e() {
		return super.width;
	}

	public int func_178042_f() {
		return super.mouseY;
	}

	public int func_178045_g() {
		return super.mouseX;
	}

	/**
	 * Return the height of the content being scrolled
	 */
	@Override
	protected int getContentHeight() {
		return field_178046_u.getMaxPosition();
	}

	@Override
	protected int getScrollBarX() {
		return field_178046_u.getScrollbarPosition();
	}

	@Override
	public void func_178039_p() {
		super.func_178039_p();

		if (scrollMultiplier > 0.0F && Mouse.getEventButtonState()) {
			field_178046_u.customMouseEvent(top, bottom, headerPadding, amountScrolled, slotHeight);
		}
	}

	public void func_178043_a(final int p_178043_1_, final int p_178043_2_, final int p_178043_3_,
			final Tezzelator p_178043_4_) {
		field_178046_u.renderSelected(p_178043_1_, p_178043_2_, p_178043_3_, p_178043_4_);
	}

	/**
	 * Draws the selection box around the selected slot element.
	 */
	@Override
	protected void drawSelectionBox(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_,
			final int p_148120_4_) {
		final int var5 = getSize();

		for (int var6 = 0; var6 < var5; ++var6) {
			final int var7 = p_148120_2_ + var6 * slotHeight + headerPadding;
			final int var8 = slotHeight - 4;

			if (var7 > bottom || var7 + var8 < top) {
				func_178040_a(var6, p_148120_1_, var7);
			}

			if (showSelectionBox && isSelected(var6)) {
				func_178043_a(width, var7, var8, Tezzelator.instance);
			}

			drawSlot(var6, p_148120_1_, var7, var8, p_148120_3_, p_148120_4_);
		}
	}
}
