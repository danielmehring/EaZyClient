package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot {

public static final int EaZy = 520;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RealmsScrolledSelectionList selectionList;
	// private static final String __OBFID = "http://https://fuckuskid00001846";

	public GuiSlotRealmsProxy(final RealmsScrolledSelectionList selectionListIn, final int p_i1085_2_,
			final int p_i1085_3_, final int p_i1085_4_, final int p_i1085_5_, final int p_i1085_6_) {
		super(Minecraft.getMinecraft(), p_i1085_2_, p_i1085_3_, p_i1085_4_, p_i1085_5_, p_i1085_6_);
		selectionList = selectionListIn;
	}

	@Override
	protected int getSize() {
		return selectionList.getItemCount();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was
	 * double clicked or not
	 */
	@Override
	protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
			final int mouseY) {
		selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(final int slotIndex) {
		return selectionList.isSelectedItem(slotIndex);
	}

	@Override
	protected void drawBackground() {
		selectionList.renderBackground();
	}

	@Override
	protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_,
			final int p_180791_5_, final int p_180791_6_) {
		selectionList.renderItem(p_180791_1_, p_180791_2_, p_180791_3_, p_180791_4_, p_180791_5_, p_180791_6_);
	}

	public int func_154338_k() {
		return super.width;
	}

	public int func_154339_l() {
		return super.mouseY;
	}

	public int func_154337_m() {
		return super.mouseX;
	}

	/**
	 * Return the height of the content being scrolled
	 */
	@Override
	protected int getContentHeight() {
		return selectionList.getMaxPosition();
	}

	@Override
	protected int getScrollBarX() {
		return selectionList.getScrollbarPosition();
	}

	@Override
	public void func_178039_p() {
		super.func_178039_p();
	}
}
