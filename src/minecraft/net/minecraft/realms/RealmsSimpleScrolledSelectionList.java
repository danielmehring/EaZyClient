package net.minecraft.realms;

import net.minecraft.client.gui.GuiSimpleScrolledSelectionListProxy;

public class RealmsSimpleScrolledSelectionList {

public static final int EaZy = 1516;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiSimpleScrolledSelectionListProxy proxy;
	// private static final String __OBFID = "http://https://fuckuskid00002186";

	public RealmsSimpleScrolledSelectionList(final int p_i45803_1_, final int p_i45803_2_, final int p_i45803_3_,
			final int p_i45803_4_, final int p_i45803_5_) {
		proxy = new GuiSimpleScrolledSelectionListProxy(this, p_i45803_1_, p_i45803_2_, p_i45803_3_, p_i45803_4_,
				p_i45803_5_);
	}

	public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
		proxy.drawScreen(p_render_1_, p_render_2_, p_render_3_);
	}

	public int width() {
		return proxy.func_178048_e();
	}

	public int ym() {
		return proxy.func_178047_f();
	}

	public int xm() {
		return proxy.func_178049_g();
	}

	protected void renderItem(final int p_renderItem_1_, final int p_renderItem_2_, final int p_renderItem_3_,
			final int p_renderItem_4_, final Tezzelator p_renderItem_5_, final int p_renderItem_6_,
			final int p_renderItem_7_) {}

	public void renderItem(final int p_renderItem_1_, final int p_renderItem_2_, final int p_renderItem_3_,
			final int p_renderItem_4_, final int p_renderItem_5_, final int p_renderItem_6_) {
		this.renderItem(p_renderItem_1_, p_renderItem_2_, p_renderItem_3_, p_renderItem_4_, Tezzelator.instance,
				p_renderItem_5_, p_renderItem_6_);
	}

	public int getItemCount() {
		return 0;
	}

	public void selectItem(final int p_selectItem_1_, final boolean p_selectItem_2_, final int p_selectItem_3_,
			final int p_selectItem_4_) {}

	public boolean isSelectedItem(final int p_isSelectedItem_1_) {
		return false;
	}

	public void renderBackground() {}

	public int getMaxPosition() {
		return 0;
	}

	public int getScrollbarPosition() {
		return proxy.func_178048_e() / 2 + 124;
	}

	public void mouseEvent() {
		proxy.func_178039_p();
	}

	public void scroll(final int p_scroll_1_) {
		proxy.scrollBy(p_scroll_1_);
	}

	public int getScroll() {
		return proxy.getAmountScrolled();
	}

	protected void renderList(final int p_renderList_1_, final int p_renderList_2_, final int p_renderList_3_,
			final int p_renderList_4_) {}
}
