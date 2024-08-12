package net.minecraft.realms;

import net.minecraft.client.gui.GuiSlotRealmsProxy;

public class RealmsScrolledSelectionList {

public static final int EaZy = 1511;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiSlotRealmsProxy proxy;
	// private static final String __OBFID = "http://https://fuckuskid00001863";

	public RealmsScrolledSelectionList(final int p_i1119_1_, final int p_i1119_2_, final int p_i1119_3_,
			final int p_i1119_4_, final int p_i1119_5_) {
		proxy = new GuiSlotRealmsProxy(this, p_i1119_1_, p_i1119_2_, p_i1119_3_, p_i1119_4_, p_i1119_5_);
	}

	public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
		proxy.drawScreen(p_render_1_, p_render_2_, p_render_3_);
	}

	public int width() {
		return proxy.func_154338_k();
	}

	public int ym() {
		return proxy.func_154339_l();
	}

	public int xm() {
		return proxy.func_154337_m();
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
		return proxy.func_154338_k() / 2 + 124;
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
