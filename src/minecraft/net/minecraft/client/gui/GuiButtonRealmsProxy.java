package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsButton;

public class GuiButtonRealmsProxy extends GuiButton {

public static final int EaZy = 462;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RealmsButton field_154318_o;
	// private static final String __OBFID = "http://https://fuckuskid00001848";

	public GuiButtonRealmsProxy(final RealmsButton p_i46321_1_, final int p_i46321_2_, final int p_i46321_3_,
			final int p_i46321_4_, final String p_i46321_5_) {
		super(p_i46321_2_, p_i46321_3_, p_i46321_4_, p_i46321_5_);
		field_154318_o = p_i46321_1_;
	}

	public GuiButtonRealmsProxy(final RealmsButton p_i1090_1_, final int p_i1090_2_, final int p_i1090_3_,
			final int p_i1090_4_, final String p_i1090_5_, final int p_i1090_6_, final int p_i1090_7_) {
		super(p_i1090_2_, p_i1090_3_, p_i1090_4_, p_i1090_6_, p_i1090_7_, p_i1090_5_);
		field_154318_o = p_i1090_1_;
	}

	public int getId() {
		return super.id;
	}

	public boolean getEnabled() {
		return super.enabled;
	}

	public void setEnabled(final boolean p_154313_1_) {
		super.enabled = p_154313_1_;
	}

	public void setText(final String p_154311_1_) {
		super.displayString = p_154311_1_;
	}

	@Override
	public int getButtonWidth() {
		return super.getButtonWidth();
	}

	public int getPositionY() {
		return super.yPosition;
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			field_154318_o.clicked(mouseX, mouseY);
		}

		return super.mousePressed(mc, mouseX, mouseY);
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(final int mouseX, final int mouseY) {
		field_154318_o.released(mouseX, mouseY);
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	public void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
		field_154318_o.renderBg(mouseX, mouseY);
	}

	public RealmsButton getRealmsButton() {
		return field_154318_o;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	@Override
	public int getHoverState(final boolean mouseOver) {
		return field_154318_o.getYImage(mouseOver);
	}

	public int func_154312_c(final boolean p_154312_1_) {
		return super.getHoverState(p_154312_1_);
	}

	public int func_175232_g() {
		return height;
	}
}
