package me.EaZy.client.modules;

import java.awt.Color;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;

public class ItemESP extends Module {

public static ItemESP mod;


    public static final int EaZy = 130;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public ItemESP() {
        super("ItemESP", 0, "iesp", Category.RENDER, "Draws a box around items.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "ArtikelBessersichtbar";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onRender() {
        if (!isToggled()) {
            if (togglecmd) {
                setToggled(true);
                onEnable();
            }
            return;
        }
        if (isToggled() && !togglecmd) {
            setToggled(false);
            onDisable();
            return;
        }
        Minecraft.theWorld.loadedEntityList.stream().filter((theObject) -> !(!(theObject instanceof EntityItem))).forEachOrdered((theObject) -> {
            item((EntityItem) theObject);
        });
        super.onRender();
    }

    private void item(final EntityItem entity) {
        final double xPos = entity.lastTickPosX
                + (entity.posX - entity.lastTickPosX) * Minecraft.timer.renderPartialTicks - RenderManager.renderPosX;

        final double yPos = entity.lastTickPosY
                + (entity.posY - entity.lastTickPosY) * Minecraft.timer.renderPartialTicks - RenderManager.renderPosY;

        final double zPos = entity.lastTickPosZ
                + (entity.posZ - entity.lastTickPosZ) * Minecraft.timer.renderPartialTicks - RenderManager.renderPosZ;
        render(ColorUtils.rainbow(0l, 1.0f), 0.2f, new Color(1, 1, 1), 0.25f, xPos, yPos, zPos, entity.width,
                entity.height * 2.0f);
    }

    private void render(final Color color, final float colorAlpha, final Color line, final float lineAlpha,
            final double x, final double y, final double z, final float width, final float height) {
        RenderUtils.drawEntityESP(x, y, z, width, height, color, colorAlpha, line.getRed(), line.getGreen(),
                line.getBlue(), lineAlpha, 1f);
    }
}
