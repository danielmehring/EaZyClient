package me.EaZy.client.modules;

import java.util.ArrayList;
import java.util.List;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fucker extends Module {

public static Fucker mod;


    public static final int EaZy = 115;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int xOffset;
    private int yOffset;
    private int zOffset;
    private BlockPos BedPos;

    public static List<Integer> ids = new ArrayList<>();

    public Fucker() {
        super("Fucker", 0, "breaker", Category.WORLD,
                "Breaks blocks.\nUse the command\n\"fucker\" to edit\nthe block-list.");
        ids.add(26);
        Client.setmgr.rSetting(new Setting("Silent", this, true));
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "JuliaFicker";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onEnable() {
        BedPos = null;
        super.onEnable();
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

        if (Minecraft.theWorld == null) {
            return;
        }
        final BlockPos v1 = getBedPos();
        try {
            if (v1 != null) {
                breakBlock(v1);
                if (Client.setmgr.getSettingByName(this, "Silent").getValBoolean()) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                } else {
                    Minecraft.thePlayer.swingItem();
                }
                BedPos = v1;
            } else {
                BedPos = null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        super.onUpdate();
    }

    public BlockPos getBedPos() {
        xOffset = -4;
        while (xOffset < 5) {
            zOffset = -4;
            while (zOffset < 5) {
                yOffset = 4;
                while (yOffset > -4) {
                    final double v1 = Minecraft.thePlayer.posX + xOffset;
                    final double v2 = Minecraft.thePlayer.posY + yOffset;
                    final double v3 = Minecraft.thePlayer.posZ + zOffset;
                    final int v4 = Block
                            .getIdFromBlock(Minecraft.theWorld.getBlockState(new BlockPos(v1, v2, v3)).getBlock());
                    if (ids.contains(v4)
                            && (BedPos == null || BedPos.getX() != v1 && BedPos.getY() != v2 && BedPos.getZ() != v3)) {
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

    private void breakBlock(final BlockPos bedPos) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(
                new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bedPos, EnumFacing.UP));
        Minecraft.thePlayer.sendQueue.addToSendQueue(
                new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bedPos, EnumFacing.UP));
    }

}
