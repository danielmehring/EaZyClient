package me.EaZy.client.modules.glides;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AACGlide extends Module {

public static AACGlide mod;


    public static final int EaZy = 118;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public AACGlide() {
        super(new String(
                new byte[]{0b1000001, 0b1000001, 0b1000011, 0b1000111, 0b1101100, 0b1101001, 0b1100100, 0b1100101}),
                0, "", Category.GLIDE,
                new String(new byte[]{0b1001010, 0b1110101, 0b1101101, 0b1110000, 0b100000, 0b1100100, 0b1101111,
                    0b1110111, 0b1101110, 0b101100})
                + "\n"
                + new String(new byte[]{0b1100111, 0b1100101, 0b1110100, 0b100000, 0b1100100, 0b1100001,
                    0b1101101, 0b1100001, 0b1100111, 0b1100101, 0b1100100})
                + "\n"
                + new String(
                        new byte[]{0b1100110, 0b1101100, 0b1111001, 0b100001, 0b100000, 0b111010, 0b1000100}));
        Client.setmgr.rSetting(new Setting("Speed", this, 0.6f, 0.0f, 5.0f, false));
        mod = this;
    }

    private int isDamaged = 0;

    @Override
    public void onEnable() {
        Client.disable(Category.GLIDE, this.getClass());
        EventManager.register(this);
        isDamaged = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        isDamaged = 0;
        if (!Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionX = 0;
            Minecraft.thePlayer.motionZ = 0;
        }
        super.onDisable();
    }

    public EventTarget onReceivePacket(final EventReceivePacket event) {
        if (isDamaged > 0 && event.getPacket() instanceof S12PacketEntityVelocity && Minecraft.theWorld
                .getEntityByID(((S12PacketEntityVelocity) event.getPacket()).entityID()).equals(Minecraft.thePlayer)
                || event.getPacket() instanceof S27PacketExplosion) {
            event.setCancelled(true);
        }
        return null;
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

        if (Minecraft.thePlayer.fallDistance > 3.2 && !Minecraft.thePlayer.movementInput.sneak) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            Minecraft.thePlayer.fallDistance = 0.0f;

        }

        if (Minecraft.thePlayer.hurtTime > 0.0F && !Minecraft.thePlayer.onGround) {
            isDamaged += 2;
            Minecraft.thePlayer.motionY = 0.3311f;

            Minecraft.thePlayer.motionX = 0.0f;
            Minecraft.thePlayer.motionZ = 0.0f;
            Minecraft.thePlayer.jumpMovementFactor = 88.795f; // xD

        }

        if (Minecraft.thePlayer.hurtTime > 0.0F && Minecraft.thePlayer.movementInput.sneak
                && !Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionY = 0.0f;

            // mc.thePlayer.motionX = 0.0f;
            // mc.thePlayer.motionZ = 0.0f;
            // mc.thePlayer.jumpMovementFactor = 0.495f;
            isDamaged += 3;
        }

        if (isDamaged > 1) {
            Minecraft.thePlayer.motionX = 0.0f;
            Minecraft.thePlayer.motionZ = 0.0f;
            Minecraft.thePlayer.jumpMovementFactor = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
        }

        if (Minecraft.thePlayer.onGround || Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.isInWeb
                || Minecraft.thePlayer.isOnLadder()) {
            Minecraft.thePlayer.fallDistance = 0;
            isDamaged = 0;
        }

        super.onUpdate();
    }
}
