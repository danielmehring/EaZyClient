package net.minecraft.client.gui;

import java.util.List;

import me.EaZy.client.gui.mc.GuiListExtendedNew;
import me.EaZy.client.gui.mc.ResourcePackListEntryNew;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtendedNew {

public static final int EaZy = 502;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Minecraft mc;
	protected final List field_148204_l;
	// private static final String __OBFID = "http://https://fuckuskid00000825";

	public GuiResourcePackList(final Minecraft mcIn, final int p_i45055_2_, final int p_i45055_3_,
			final List p_i45055_4_) {
		super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
		mc = mcIn;
		field_148204_l = p_i45055_4_;
		field_148163_i = false;
		setHasListHeader(true, (int) (mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
	}

	/**
	 * Handles drawing a list's header row.
	 */
	@Override
	protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
		final String var4 = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + getListHeader();
		mc.fontRendererObj.drawString(var4, p_148129_1_ + width / 2 - mc.fontRendererObj.getStringWidth(var4) / 2,
				Math.min(top + 3, p_148129_2_), 16777215);
	}

	protected abstract String getListHeader();

	public List getList() {
		return field_148204_l;
	}

	@Override
	protected int getSize() {
		return getList().size();
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public ResourcePackListEntryNew getListEntry(final int p_148180_1_) {
		return (ResourcePackListEntryNew) getList().get(p_148180_1_);
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return width;
	}

	@Override
	protected int getScrollBarX() {
		return right - 6;
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	public GuiListExtendedNew.IGuiListEntry getListEntry1(final int p_148180_1_) {
		return getListEntry(p_148180_1_);
	}
}
