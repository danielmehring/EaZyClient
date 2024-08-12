package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Damage extends Module {

public static Damage mod;


    public static final int EaZy = 105;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Damage() {
        super("Damage", 11, "", Category.HIDDEN);
        mod = this;
    }

    private static void damage() {
        final double test1 = 0.5;
        int i = 0;
        while (i < 80.0 + 40.0 * (test1 - 0.5)) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.049, Minecraft.thePlayer.posZ, false));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
            ++i;
        }
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
    }
    
    /**
     * Jump before this!
     */
    public static void damageAAC3() {
    	mc.thePlayer.sendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
				mc.thePlayer.posY + 0.4, mc.thePlayer.posZ, false));
		mc.thePlayer.sendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
				mc.thePlayer.posY + 1, mc.thePlayer.posZ, false));
    }

    @Override
    public void onEnable() {
        Damage.damage();
        setToggled(false);
        togglecmd = false;
        super.onUpdate();
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
        super.onUpdate();
    }
}
