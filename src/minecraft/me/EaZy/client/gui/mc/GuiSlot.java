package me.EaZy.client.gui.mc;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;

public abstract class GuiSlot {

    public static final int EaZy = 79;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    protected int width;
    private int height;

    /**
     * The top of the slot container. Affects the overlays and scrolling.
     */
    protected int top;

    /**
     * The bottom of the slot container. Affects the overlays and scrolling.
     */
    private int bottom;
    protected int right;
    protected int left;

    /**
     * The height of a slot.
     */
    protected final int slotHeight;

    /**
     * The buttonID of the button used to scroll up
     */
    private int scrollUpButtonID;

    /**
     * The buttonID of the button used to scroll down
     */
    private int scrollDownButtonID;
    private int mouseX;
    private int mouseY;
    protected boolean field_148163_i = true;

    /**
     * Where the mouse was in the window when you first clicked to scroll
     */
    private float initialClickY = -2.0F;

    /**
     * What to multiply the amount you moved your mouse by (used for slowing
     * down scrolling when over the items and not on the scroll bar)
     */
    private float scrollMultiplier;

    /**
     * How far down this slot has been scrolled
     */
    private float amountScrolled;

    /**
     * The element in the list that was selected
     */
    private int selectedElement = -1;

    /**
     * The time when this button was last clicked.
     */
    private long lastClicked;
    private final boolean field_178041_q = true;

    /**
     * Set to true if a selected element in this gui will show an outline box
     */
    private boolean showSelectionBox = true;
    private boolean hasListHeader;
    protected int headerPadding;
    private boolean enabled = true;

    public GuiSlot(final Minecraft mcIn, final int width, final int height, final int p_i1052_4_, final int p_i1052_5_,
            final int p_i1052_6_) {
        this.width = width;
        this.height = height;
        top = p_i1052_4_;
        bottom = p_i1052_5_;
        slotHeight = p_i1052_6_;
        left = 0;
        right = width;
    }

    public void setDimensions(final int p_148122_1_, final int p_148122_2_, final int p_148122_3_,
            final int p_148122_4_) {
        width = p_148122_1_;
        height = p_148122_2_;
        top = p_148122_3_;
        bottom = p_148122_4_;
        left = 0;
        right = p_148122_1_;
    }

    public void setShowSelectionBox(final boolean p_148130_1_) {
        showSelectionBox = p_148130_1_;
    }

    /**
     * Sets hasListHeader and headerHeight. Params: hasListHeader, headerHeight.
     * If hasListHeader is false headerHeight is set to 0.
     */
    protected void setHasListHeader(final boolean p_148133_1_, final int p_148133_2_) {
        hasListHeader = p_148133_1_;
        headerPadding = p_148133_2_;

        if (!p_148133_1_) {
            headerPadding = 0;
        }
    }

    protected abstract int getSize();

    /**
     * The element in the slot that was clicked, boolean for whether it was
     * double clicked or not
     */
    protected abstract void elementClicked(int var1, boolean var2, int var3, int var4);

    /**
     * Returns true if the element passed in is currently selected
     */
    protected abstract boolean isSelected(int var1);

    /**
     * Return the height of the content being scrolled
     */
    private int getContentHeight() {
        return getSize() * slotHeight + headerPadding;
    }

    protected abstract void drawBackground();

    protected void func_178040_a(final int p_178040_1_, final int p_178040_2_, final int p_178040_3_) {
    }

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6);

    /**
     * Handles drawing a list's header row.
     */
    protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
    }

    private void func_148132_a(final int p_148132_1_, final int p_148132_2_) {
    }

    private void func_148142_b(final int p_148142_1_, final int p_148142_2_) {
    }

    public int getSlotIndexFromScreenCoords(final int p_148124_1_, final int p_148124_2_) {
        final int var3 = left + width / 2 - getListWidth() / 2;
        final int var4 = left + width / 2 + getListWidth() / 2;
        final int var5 = p_148124_2_ - top - headerPadding + (int) amountScrolled - 4;
        final int var6 = var5 / slotHeight;
        return p_148124_1_ < getScrollBarX() && p_148124_1_ >= var3 && p_148124_1_ <= var4 && var6 >= 0 && var5 >= 0
                && var6 < getSize() ? var6 : -1;
    }

    /**
     * Registers the IDs that can be used for the scrollbar's up/down buttons.
     */
    public void registerScrollButtons(final int p_148134_1_, final int p_148134_2_) {
        scrollUpButtonID = p_148134_1_;
        scrollDownButtonID = p_148134_2_;
    }

    /**
     * Stop the thing from scrolling out of bounds
     */
    private void bindAmountScrolled() {
        int var1 = func_148135_f();

        if (var1 < 0) {
            var1 /= 2;
        }

        if (!field_148163_i && var1 < 0) {
            var1 = 0;
        }

        amountScrolled = MathHelper.clamp_float(amountScrolled, 0.0F, var1);
    }

    private int func_148135_f() {
        return Math.max(0, getContentHeight() - (bottom - top - 4));
    }

    /**
     * Returns the amountScrolled field as an integer.
     */
    public int getAmountScrolled() {
        return (int) amountScrolled;
    }

    public boolean isMouseYWithinSlotBounds(final int p_148141_1_) {
        return p_148141_1_ >= top && p_148141_1_ <= bottom && mouseX >= left && mouseX <= right;
    }

    /**
     * Scrolls the slot by the given amount. A positive value scrolls down, and
     * a negative value scrolls up.
     */
    public void scrollBy(final int p_148145_1_) {
        amountScrolled += p_148145_1_;
        bindAmountScrolled();
        initialClickY = -2.0F;
    }

    public void actionPerformed(final GuiButton p_148147_1_) {
        if (p_148147_1_.enabled) {
            if (p_148147_1_.id == scrollUpButtonID) {
                amountScrolled -= slotHeight * 2 / 3;
                initialClickY = -2.0F;
                bindAmountScrolled();
            } else if (p_148147_1_.id == scrollDownButtonID) {
                amountScrolled += slotHeight * 2 / 3;
                initialClickY = -2.0F;
                bindAmountScrolled();
            }
        }
    }

    public void drawScreen(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
        if (field_178041_q) {
            mouseX = p_148128_1_;
            mouseY = p_148128_2_;
            drawBackground();
            final int var4 = getScrollBarX();
            final int var5 = var4 + 6;
            bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            final Tessellator var6 = Tessellator.getInstance();
            final WorldRenderer var7 = var6.getWorldRenderer();
            Minecraft.getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            final float var8 = 32.0F;
            var7.startDrawingQuads();
            var7.func_178991_c(2105376);
            var7.addVertexWithUV(left, bottom, 0.0D, left / var8, (bottom + (int) amountScrolled) / var8);
            var7.addVertexWithUV(right, bottom, 0.0D, right / var8, (bottom + (int) amountScrolled) / var8);
            var7.addVertexWithUV(right, top, 0.0D, right / var8, (top + (int) amountScrolled) / var8);
            var7.addVertexWithUV(left, top, 0.0D, left / var8, (top + (int) amountScrolled) / var8);
            var6.draw();
            final int var9 = left + width / 2 - getListWidth() / 2 + 2;
            final int var10 = top + 4 - (int) amountScrolled;

            if (hasListHeader) {
                drawListHeader(var9, var10, var6);
            }

            drawSelectionBox(var9, var10, p_148128_1_, p_148128_2_);
            GlStateManager.disableDepth();
            final byte var11 = 4;
            overlayBackground(0, top, 255, 255);
            overlayBackground(bottom, height, 255, 255);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.disableTexture2D();
            var7.startDrawingQuads();
            var7.func_178974_a(0, 0);
            var7.addVertexWithUV(left, top + var11, 0.0D, 0.0D, 1.0D);
            var7.addVertexWithUV(right, top + var11, 0.0D, 1.0D, 1.0D);
            var7.func_178974_a(0, 255);
            var7.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
            var7.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
            var6.draw();
            var7.startDrawingQuads();
            var7.func_178974_a(0, 255);
            var7.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
            var7.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
            var7.func_178974_a(0, 0);
            var7.addVertexWithUV(right, bottom - var11, 0.0D, 1.0D, 0.0D);
            var7.addVertexWithUV(left, bottom - var11, 0.0D, 0.0D, 0.0D);
            var6.draw();
            final int var12 = func_148135_f();

            if (var12 > 0) {
                int var13 = (bottom - top) * (bottom - top) / getContentHeight();
                var13 = MathHelper.clamp_int(var13, 32, bottom - top - 8);
                int var14 = (int) amountScrolled * (bottom - top - var13) / var12 + top;

                if (var14 < top) {
                    var14 = top;
                }

                var7.startDrawingQuads();
                var7.func_178974_a(0, 255);
                var7.addVertexWithUV(var4, bottom, 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV(var5, bottom, 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV(var5, top, 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV(var4, top, 0.0D, 0.0D, 0.0D);
                var6.draw();
                var7.startDrawingQuads();
                var7.func_178974_a(8421504, 255);
                var7.addVertexWithUV(var4, var14 + var13, 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV(var5, var14 + var13, 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV(var5, var14, 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV(var4, var14, 0.0D, 0.0D, 0.0D);
                var6.draw();
                var7.startDrawingQuads();
                var7.func_178974_a(12632256, 255);
                var7.addVertexWithUV(var4, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV(var5 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV(var5 - 1, var14, 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV(var4, var14, 0.0D, 0.0D, 0.0D);
                var6.draw();
            }

            func_148142_b(p_148128_1_, p_148128_2_);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void func_178039_p() {
        if (isMouseYWithinSlotBounds(mouseY)) {
            if (Mouse.isButtonDown(0) && getEnabled()) {
                if (initialClickY == -1.0F) {
                    boolean var1 = true;

                    if (mouseY >= top && mouseY <= bottom) {
                        final int var2 = width / 2 - getListWidth() / 2;
                        final int var3 = width / 2 + getListWidth() / 2;
                        final int var4 = mouseY - top - headerPadding + (int) amountScrolled - 4;
                        final int var5 = var4 / slotHeight;

                        if (mouseX >= var2 && mouseX <= var3 && var5 >= 0 && var4 >= 0 && var5 < getSize()) {
                            final boolean var6 = var5 == selectedElement
                                    && Minecraft.getSystemTime() - lastClicked < 250L;
                            elementClicked(var5, var6, mouseX, mouseY);
                            selectedElement = var5;
                            lastClicked = Minecraft.getSystemTime();
                        } else if (mouseX >= var2 && mouseX <= var3 && var4 < 0) {
                            func_148132_a(mouseX - var2, mouseY - top + (int) amountScrolled - 4);
                            var1 = false;
                        }

                        final int var11 = getScrollBarX();
                        final int var7 = var11 + 6;

                        if (mouseX >= var11 && mouseX <= var7) {
                            scrollMultiplier = -1.0F;
                            int var8 = func_148135_f();

                            if (var8 < 1) {
                                var8 = 1;
                            }

                            int var9 = (int) ((float) ((bottom - top) * (bottom - top)) / (float) getContentHeight());
                            var9 = MathHelper.clamp_int(var9, 32, bottom - top - 8);
                            scrollMultiplier /= (float) (bottom - top - var9) / (float) var8;
                        } else {
                            scrollMultiplier = 1.0F;
                        }

                        if (var1) {
                            initialClickY = mouseY;
                        } else {
                            initialClickY = -2.0F;
                        }
                    } else {
                        initialClickY = -2.0F;
                    }
                } else if (initialClickY >= 0.0F) {
                    amountScrolled -= (mouseY - initialClickY) * scrollMultiplier;
                    initialClickY = mouseY;
                }
            } else {
                initialClickY = -1.0F;
            }

            int var10 = Mouse.getEventDWheel();

            if (var10 != 0) {
                if (var10 > 0) {
                    var10 = -1;
                } else if (var10 < 0) {
                    var10 = 1;
                }

                amountScrolled += var10 * slotHeight / 2;
            }
        }
    }

    public void setEnabled(final boolean p_148143_1_) {
        enabled = p_148143_1_;
    }

    public boolean getEnabled() {
        return enabled;
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth() {
        return 220;
    }

    /**
     * Draws the selection box around the selected slot element.
     */
    private void drawSelectionBox(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_,
            final int p_148120_4_) {
        final int var5 = getSize();
        final Tessellator var6 = Tessellator.getInstance();
        final WorldRenderer var7 = var6.getWorldRenderer();

        for (int var8 = 0; var8 < var5; ++var8) {
            final int var9 = p_148120_2_ + var8 * slotHeight + headerPadding;
            final int var10 = slotHeight - 4;

            if (var9 > bottom || var9 + var10 < top) {
                func_178040_a(var8, p_148120_1_, var9);
            }

            if (showSelectionBox && isSelected(var8)) {
                final int var11 = left + width / 2 - getListWidth() / 2;
                final int var12 = left + width / 2 + getListWidth() / 2;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableTexture2D();
                var7.startDrawingQuads();
                var7.func_178991_c(8421504);
                var7.addVertexWithUV(var11, var9 + var10 + 2, 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV(var12, var9 + var10 + 2, 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV(var12, var9 - 2, 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV(var11, var9 - 2, 0.0D, 0.0D, 0.0D);
                var7.func_178991_c(0);
                var7.addVertexWithUV(var11 + 1, var9 + var10 + 1, 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV(var12 - 1, var9 + var10 + 1, 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV(var12 - 1, var9 - 1, 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV(var11 + 1, var9 - 1, 0.0D, 0.0D, 0.0D);
                var6.draw();
                GlStateManager.enableTexture2D();
            }

            drawSlot(var8, p_148120_1_, var9, var10, p_148120_3_, p_148120_4_);
        }
    }

    protected int getScrollBarX() {
        return width / 2 + 124;
    }

    /**
     * Overlays the background to hide scrolled items
     */
    private void overlayBackground(final int p_148136_1_, final int p_148136_2_, final int p_148136_3_,
            final int p_148136_4_) {
        final Tessellator var5 = Tessellator.getInstance();
        final WorldRenderer var6 = var5.getWorldRenderer();
        Minecraft.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        final float var7 = 32.0F;
        var6.startDrawingQuads();
        var6.func_178974_a(4210752, p_148136_4_);
        var6.addVertexWithUV(left, p_148136_2_, 0.0D, 0.0D, p_148136_2_ / var7);
        var6.addVertexWithUV(left + width, p_148136_2_, 0.0D, width / var7, p_148136_2_ / var7);
        var6.func_178974_a(4210752, p_148136_3_);
        var6.addVertexWithUV(left + width, p_148136_1_, 0.0D, width / var7, p_148136_1_ / var7);
        var6.addVertexWithUV(left, p_148136_1_, 0.0D, 0.0D, p_148136_1_ / var7);
        var5.draw();
    }

    /**
     * Sets the left and right bounds of the slot. Param is the left bound,
     * right is calculated as left + width.
     */
    public void setSlotXBoundsFromLeft(final int p_148140_1_) {
        left = p_148140_1_;
        right = p_148140_1_ + width;
    }

    public int getSlotHeight() {
        return slotHeight;
    }
}
