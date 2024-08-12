package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiMainMenu;

public class ChestESP extends Module {

public static ChestESP mod;


    public static final int EaZy = 102;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public ChestESP() {
        super("ChestESP", 77, "cesp", Category.RENDER, "Shows you all\nchests.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KistenBesserSichtbar";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onUpdate() {
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
        super.onUpdate();
    }

    // @Override
    // public void onRender() {
    // if (!this.isToggled()) {
    // return;
    // }
    // for (Object o : ChestESP.mc.theWorld.loadedTileEntityList) {
    // int x;
    // int y;
    // TileEntity chest;
    // int z;
    // if (o instanceof TileEntityChest) {
    // chest = (TileEntityChest) o;
    // x = chest.getPos().getX();
    // y = chest.getPos().getY();
    // z = chest.getPos().getZ();
    // if (mc.theWorld.getBlockState(chest.getPos()).getBlock() == Blocks.chest)
    // RenderUtils.drawBlockESP((double) x - RenderManager.renderPosX,
    // (double) y - RenderManager.renderPosY, (double) z -
    // RenderManager.renderPosZ, 1.0f, 1.0f,
    // 0.0f, 0.15f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f);
    // else
    // RenderUtils.drawBlockESP((double) x - RenderManager.renderPosX,
    // (double) y - RenderManager.renderPosY, (double) z -
    // RenderManager.renderPosZ, 1.0f, 0.0f,
    // 0.0f, 0.15f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f);
    // }
    // if (!(o instanceof TileEntityEnderChest))
    // continue;
    // chest = (TileEntityEnderChest) o;
    // x = chest.getPos().getX();
    // y = chest.getPos().getY();
    // z = chest.getPos().getZ();
    // RenderUtils.drawBlockESP((double) x - RenderManager.renderPosX, (double)
    // y - RenderManager.renderPosY,
    // (double) z - RenderManager.renderPosZ, 1.0f, 0.0f, 1.0f, 0.15f, 1.0f,
    // 0.0f, 1.0f, 1.0f, 1.0f);
    // }
    // }
}
