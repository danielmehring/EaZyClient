package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.utils.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Jesus extends Module {

public static Jesus mod;


    public static final int EaZy = 131;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Jesus() {
        super("Jesus", 71, "", Category.MOVEMENT, "Walk on water.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Yeezuz";
        } else {
            return super.getRenderName();
        }
    }

    public static boolean isInLiquid(AxisAlignedBB par1AxisAlignedBB) {
        par1AxisAlignedBB = par1AxisAlignedBB.contract(0.001, 0.001, 0.001);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (Minecraft.theWorld.getChunkFromBlockCoords(
                new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ)) == null) {
            return false;
        }
        int var12 = var4;
        while (var12 < var5) {
            int var13 = var6;
            while (var13 < var7) {
                int var14 = var8;
                while (var14 < var9) {
                    final Block var15 = BlockUtils.getBlock(var12, var13, var14);
                    if (var15 instanceof BlockLiquid) {
                        return true;
                    }
                    ++var14;
                }
                ++var13;
            }
            ++var12;
        }
        return false;
    }

    public static boolean isOnLiquid(AxisAlignedBB boundingBox) {
        boundingBox = boundingBox.contract(0.01, 0.0, 0.01).offset(0.0, -0.01, 0.0);
        boolean onLiquid = false;
        final int y2 = (int) boundingBox.minY;
        int x2 = MathHelper.floor_double(boundingBox.minX);
        while (x2 < MathHelper.floor_double(boundingBox.maxX + 1.0)) {
            int z2 = MathHelper.floor_double(boundingBox.minZ);
            while (z2 < MathHelper.floor_double(boundingBox.maxZ + 1.0)) {
                final Block block = BlockUtils.getBlock(x2, y2, z2);
                if (block != Blocks.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
                ++z2;
            }
            ++x2;
        }
        return onLiquid;
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

    @Override
    public void onDisable() {
    }
}
