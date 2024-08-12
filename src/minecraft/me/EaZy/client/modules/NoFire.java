package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class NoFire extends Module {

public static NoFire mod;


    public static final int EaZy = 142;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public NoFire() {
        super("NoFire", 47, "zoot", Category.PLAYER, "Avoid Fire-Damage.");
        mod = this;
    }

    @Override
    public void onDisable() {
        Minecraft.thePlayer.capabilities.isFlying = false;
        super.onDisable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "KeinFeuer";
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
        if (!Minecraft.thePlayer.isCollidedVertically) {
            return;
        }
        if (Minecraft.thePlayer.getHeldItem() != null
                && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemFood) {
            return;
        }
        final Potion[] arrpotion = Potion.potionTypes;
        final int n = arrpotion.length;
        int n2 = 0;
        while (n2 < n) {
            PotionEffect effect;
            final Potion potion = arrpotion[n2];
            if (potion != null && potion.isBadEffect()
                    && (effect = Minecraft.thePlayer.getActivePotionEffect(potion)) != null
                    && !effect.getIsPotionDurationMax()) {
                int index = 0;
                while (index < effect.getDuration()) {
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer(Minecraft.thePlayer.onGround));
                    Minecraft.thePlayer.getActivePotionEffects().forEach((effect2) -> {
                        ((PotionEffect) effect2).deincrementDuration();
                    });
                    ++index;
                }
            }
            ++n2;
        }
        if (Minecraft.thePlayer.isBurning() && Minecraft.thePlayer.getActivePotionEffect(Potion.fireResistance) == null
                && Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX,
                        Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ)).getBlock()
                        .getMaterial() != Material.lava) {
            int i = 0;
            while (i < 32) {
                Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer(Minecraft.thePlayer.onGround));
                Minecraft.thePlayer.getActivePotionEffects().forEach((effect2) -> {
                    ((PotionEffect) effect2).deincrementDuration();
                });
                ++i;
            }
        }
        super.onUpdate();
    }
}
