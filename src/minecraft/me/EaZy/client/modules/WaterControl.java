package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.BlockPos;

public class WaterControl extends Module {

public static WaterControl mod;


    public static final int EaZy = 194;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public WaterControl() {
        super("WaterControl", 0, "WC", Category.MOVEMENT, "Let's you control\nyourself better\nin water!");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Wasserkontrolle";
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

        if (!Minecraft.thePlayer.onGround) {
            if (Minecraft.theWorld.getBlockState(
                    new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1, Minecraft.thePlayer.posZ))
                    .getBlock() instanceof BlockLiquid
                    || Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX + 1,
                            Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ)).getBlock() instanceof BlockLiquid
                    && Minecraft.thePlayer.isInWater()) {

                if (!Minecraft.thePlayer.movementInput.sneak && !Minecraft.thePlayer.movementInput.jump) {
                    Minecraft.thePlayer.motionY = 0;
                } else if (Minecraft.thePlayer.movementInput.sneak && !Minecraft.thePlayer.movementInput.jump) {
                    Minecraft.thePlayer.motionY = -0.2f;
                } else if (!Minecraft.thePlayer.movementInput.sneak && Minecraft.thePlayer.movementInput.jump) {
                    Minecraft.thePlayer.motionY = 0.1f;
                }
            }

            if (Minecraft.theWorld.getBlockState(
                    new BlockPos(Minecraft.thePlayer.posX - 1, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ))
                    .getBlock() instanceof BlockLiquid
                    || Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY,
                            Minecraft.thePlayer.posZ + 1)).getBlock() instanceof BlockLiquid
                    || Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY,
                            Minecraft.thePlayer.posZ - 1)).getBlock() instanceof BlockLiquid) {
                if (Minecraft.thePlayer.isInWater()) {
                    if (!Minecraft.thePlayer.movementInput.sneak && !Minecraft.thePlayer.movementInput.jump) {
                        Minecraft.thePlayer.motionY = 0;
                    }
                }

            }
        }
        super.onUpdate();
    }
}
