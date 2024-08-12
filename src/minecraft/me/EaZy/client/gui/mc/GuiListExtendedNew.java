package me.EaZy.client.gui.mc;

import net.minecraft.client.Minecraft;

public abstract class GuiListExtendedNew extends GuiSlot {

    public static final int EaZy = 78;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    // private static final String __OBFID = "http://https://fuckuskid00000674";
    public GuiListExtendedNew(final Minecraft mcIn, final int p_i45010_2_, final int p_i45010_3_, final int p_i45010_4_,
            final int p_i45010_5_, final int p_i45010_6_) {
        super(mcIn, p_i45010_2_, p_i45010_3_, p_i45010_4_, p_i45010_5_, p_i45010_6_);
    }

    /**
     * The element in the slot that was clicked, boolean for whether it was
     * double clicked or not
     */
    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
            final int mouseY) {
    }

    /**
     * Returns true if the element passed in is currently selected
     */
    @Override
    protected boolean isSelected(final int slotIndex) {
        return false;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_,
            final int p_180791_5_, final int p_180791_6_) {
        getListEntry(p_180791_1_).drawEntry(p_180791_1_, p_180791_2_, p_180791_3_, getListWidth(), p_180791_4_,
                p_180791_5_, p_180791_6_, getSlotIndexFromScreenCoords(p_180791_5_, p_180791_6_) == p_180791_1_);
    }

    @Override
    protected void func_178040_a(final int p_178040_1_, final int p_178040_2_, final int p_178040_3_) {
        getListEntry(p_178040_1_).setSelected(p_178040_1_, p_178040_2_, p_178040_3_);
    }

    public boolean func_148179_a(final int p_148179_1_, final int p_148179_2_, final int p_148179_3_) {
        if (isMouseYWithinSlotBounds(p_148179_2_)) {
            final int var4 = getSlotIndexFromScreenCoords(p_148179_1_, p_148179_2_);

            if (var4 >= 0) {
                final int var5 = left + width / 2 - getListWidth() / 2 + 2;
                final int var6 = top + 4 - getAmountScrolled() + var4 * slotHeight + headerPadding;
                final int var7 = p_148179_1_ - var5;
                final int var8 = p_148179_2_ - var6;

                if (getListEntry(var4).mousePressed(var4, p_148179_1_, p_148179_2_, p_148179_3_, var7, var8)) {
                    setEnabled(false);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean func_148181_b(final int p_148181_1_, final int p_148181_2_, final int p_148181_3_) {
        for (int var4 = 0; var4 < getSize(); ++var4) {
            final int var5 = left + width / 2 - getListWidth() / 2 + 2;
            final int var6 = top + 4 - getAmountScrolled() + var4 * slotHeight + headerPadding;
            final int var7 = p_148181_1_ - var5;
            final int var8 = p_148181_2_ - var6;
            getListEntry(var4).mouseReleased(var4, p_148181_1_, p_148181_2_, p_148181_3_, var7, var8);
        }

        setEnabled(true);
        return false;
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public abstract GuiListExtendedNew.IGuiListEntry getListEntry(int var1);

    public interface IGuiListEntry {

        void setSelected(int var1, int var2, int var3);

        void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8);

        boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6);

        void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6);
    }
}
