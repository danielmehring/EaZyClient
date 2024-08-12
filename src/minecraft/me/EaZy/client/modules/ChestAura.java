package me.EaZy.client.modules;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ChestAura extends Module {

public static ChestAura mod;


    public static final int EaZy = 101;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public ChestAura() {
        super("ChestAura", 23, "caura", Category.WORLD, "Opens the chests\nnear your.");
        Client.setmgr.rSetting(new Setting("WallCheck", this, true));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KistenAusstrahlung";
        } else {
            return super.getRenderName();
        }
    }

    private int xOffset;
    private int yOffset;
    private int zOffset;
    private BlockPos ChestPos;

    public static ArrayList<BlockPos> openedChests = new ArrayList<>();

    private int delay = 0;

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
        if (Minecraft.theWorld == null) {
            return;
        }
        delay++;

        if (Minecraft.currentScreen == null && delay > 1 && !Blink.mod.isToggled()) {
            final BlockPos v1 = getChestPos();
            try {
                if (v1 != null) {
                    openChest(v1);
                    ChestPos = v1;
                } else {
                    ChestPos = null;
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
            delay = 0;
        }

        super.onUpdate();
    }

    private void openChest(final BlockPos chestPos) {
        Minecraft.playerController.syncCurrentPlayItem();
        EntityUtils.setLookChanged(true);
        BlockUtils.faceBlockPacket(chestPos);
        Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(chestPos, EnumFacing.UP.getIndex(),
                Minecraft.thePlayer.getCurrentEquippedItem(), chestPos.getX(), chestPos.getY(), chestPos.getZ()));
        Minecraft.thePlayer.swingItem();
        EntityUtils.setLookChanged(false);
        openedChests.add(chestPos);
    }

    public BlockPos getChestPos() {
        xOffset = -3;
        while (xOffset < 4) {
            zOffset = -3;
            while (zOffset < 4) {
                yOffset = 3;
                while (yOffset > -3) {
                    final double v1 = Minecraft.thePlayer.posX + xOffset;
                    final double v2 = Minecraft.thePlayer.posY + yOffset;
                    final double v3 = Minecraft.thePlayer.posZ + zOffset;
                    final int v4 = Block
                            .getIdFromBlock(Minecraft.theWorld.getBlockState(new BlockPos(v1, v2, v3)).getBlock());
                    if ((v4 == 54 || v4 == 130 || v4 == 146) && ChestPos == null
                            && !openedChests.contains(new BlockPos(v1, v2, v3))
                            && (Client.setmgr.getSettingByName(this, "WallCheck").getValBoolean() ? true
                            : Minecraft.thePlayer.canBlockBeSeen(new BlockPos(v1, v2, v3)))) {
                        return new BlockPos(v1, v2, v3);
                    }
                    --yOffset;
                }
                ++zOffset;
            }
            ++xOffset;
        }
        return null;
    }

    @Override
    public void onEnable() {
        ChestPos = null;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        openedChests.clear();
        super.onDisable();
    }
}
