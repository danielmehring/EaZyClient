package shadersmod.client;

import net.minecraft.client.gui.GuiSlot;

import java.util.ArrayList;

import optifine.Lang;

class GuiSlotShaders extends GuiSlot {

public static final int EaZy = 1994;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ArrayList shaderslist;
	private int selectedIndex;
	private long lastClickedCached = 0L;
	final GuiShaders shadersGui;

	public GuiSlotShaders(final GuiShaders par1GuiShaders, final int width, final int height, final int top,
			final int bottom, final int slotHeight) {
		super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
		shadersGui = par1GuiShaders;
		updateList();
		amountScrolled = 0.0F;
		final int posYSelected = selectedIndex * slotHeight;
		final int wMid = (bottom - top) / 2;

		if (posYSelected > wMid) {
			scrollBy(posYSelected - wMid);
		}
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return width - 20;
	}

	public void updateList() {
		shaderslist = Shaders.listOfShaders();
		selectedIndex = 0;
		int i = 0;

		for (final int n = shaderslist.size(); i < n; ++i) {
			if (((String) shaderslist.get(i)).equals(Shaders.currentshadername)) {
				selectedIndex = i;
				break;
			}
		}
	}

	@Override
	protected int getSize() {
		return shaderslist.size();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was
	 * double clicked or not
	 */
	@Override
	protected void elementClicked(final int index, final boolean doubleClicked, final int mouseX, final int mouseY) {
		if (index != selectedIndex || lastClicked != lastClickedCached) {
			selectedIndex = index;
			lastClickedCached = lastClicked;
			Shaders.setShaderPack((String) shaderslist.get(index));
			Shaders.uninit();
			shadersGui.updateButtons();
		}
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(final int index) {
		return index == selectedIndex;
	}

	@Override
	protected int getScrollBarX() {
		return width - 6;
	}

	/**
	 * Return the height of the content being scrolled
	 */
	@Override
	protected int getContentHeight() {
		return getSize() * 18;
	}

	@Override
	protected void drawBackground() {}

	@Override
	protected void drawSlot(final int index, final int posX, final int posY, final int contentY, final int mouseX,
			final int mouseY) {
		String label = (String) shaderslist.get(index);

		if (label.equals(Shaders.packNameNone)) {
			label = Lang.get("of.options.shaders.packNone");
		} else if (label.equals(Shaders.packNameDefault)) {
			label = Lang.get("of.options.shaders.packDefault");
		}

		shadersGui.drawCenteredString(label, width / 2, posY + 1, 16777215);
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
}
