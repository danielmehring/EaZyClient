package me.EaZy.client.gui.mc;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public abstract class ResourcePackListEntryNew implements GuiListExtendedNew.IGuiListEntry {

    public static final int EaZy = 80;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static final ResourceLocation field_148316_c = new ResourceLocation("textures/gui/resource_packs.png");
    protected final Minecraft field_148317_a;
    private final GuiScreenResourcePacks field_148315_b;
    // private static final String __OBFID = "http://https://fuckuskid00000821";

    public ResourcePackListEntryNew(final GuiScreenResourcePacks p_i45051_1_) {
        field_148315_b = p_i45051_1_;
        field_148317_a = Minecraft.getMinecraft();
    }

    @Override
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
            final int mouseX, final int mouseY, final boolean isSelected) {
        func_148313_c();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        int var10;

        if ((Minecraft.gameSettings.touchscreen || isSelected) && func_148310_d()) {
            Minecraft.getTextureManager().bindTexture(field_148316_c);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            final int var9 = mouseX - x;
            var10 = mouseY - y;

            if (func_148309_e()) {
                if (var9 < 32) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                } else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            } else {
                if (func_148308_f()) {
                    if (var9 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (func_148314_g()) {
                    if (var9 < 32 && var9 > 16 && var10 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (func_148307_h()) {
                    if (var9 < 32 && var9 > 16 && var10 > 16) {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }
            }
        }

        String var13 = func_148312_b();
        var10 = field_148317_a.fontRendererObj.getStringWidth(var13);

        if (var10 > 157) {
            var13 = field_148317_a.fontRendererObj.trimStringToWidth(var13,
                    157 - field_148317_a.fontRendererObj.getStringWidth("...")) + "...";
        }

        field_148317_a.fontRendererObj.drawStringWithShadow(var13, x + 32 + 2, y + 1, 16777215);
        final List var11 = field_148317_a.fontRendererObj.listFormattedStringToWidth(func_148311_a(), 157);

        for (int var12 = 0; var12 < 2 && var12 < var11.size(); ++var12) {
            field_148317_a.fontRendererObj.drawStringWithShadow((String) var11.get(var12), (float) (x + 32 + 2),
                    (float) (y + 12 + 10 * var12), 8421504);
        }
    }

    protected abstract String func_148311_a();

    protected abstract String func_148312_b();

    protected abstract void func_148313_c();

    protected boolean func_148310_d() {
        return true;
    }

    protected boolean func_148309_e() {
        return !field_148315_b.hasResourcePackEntry(this);
    }

    protected boolean func_148308_f() {
        return field_148315_b.hasResourcePackEntry(this);
    }

    protected boolean func_148314_g() {
        final List var1 = field_148315_b.func_146962_b(this);
        final int var2 = var1.indexOf(this);
        return var2 > 0 && ((ResourcePackListEntryNew) var1.get(var2 - 1)).func_148310_d();
    }

    protected boolean func_148307_h() {
        final List var1 = field_148315_b.func_146962_b(this);
        final int var2 = var1.indexOf(this);
        return var2 >= 0 && var2 < var1.size() - 1 && ((ResourcePackListEntryNew) var1.get(var2 + 1)).func_148310_d();
    }

    /**
     * Returns true if the mouse has been pressed on this control.
     */
    @Override
    public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
            final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (func_148310_d() && p_148278_5_ <= 32) {
            if (func_148309_e()) {
                field_148315_b.func_146962_b(this).remove(this);
                field_148315_b.func_146963_h().add(0, this);
                field_148315_b.func_175288_g();
                return true;
            }

            if (p_148278_5_ < 16 && func_148308_f()) {
                field_148315_b.func_146962_b(this).remove(this);
                field_148315_b.func_146964_g().add(0, this);
                field_148315_b.func_175288_g();
                return true;
            }

            List var7;
            int var8;

            if (p_148278_5_ > 16 && p_148278_6_ < 16 && func_148314_g()) {
                var7 = field_148315_b.func_146962_b(this);
                var8 = var7.indexOf(this);
                var7.remove(this);
                var7.add(var8 - 1, this);
                field_148315_b.func_175288_g();
                return true;
            }

            if (p_148278_5_ > 16 && p_148278_6_ > 16 && func_148307_h()) {
                var7 = field_148315_b.func_146962_b(this);
                var8 = var7.indexOf(this);
                var7.remove(this);
                var7.add(var8 + 1, this);
                field_148315_b.func_175288_g();
                return true;
            }
        }

        return false;
    }

    @Override
    public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y,
     * mouseEvent, relativeX, relativeY
     */
    @Override
    public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX,
            final int relativeY) {
    }
}
