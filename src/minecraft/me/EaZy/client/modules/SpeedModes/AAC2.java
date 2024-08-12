package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.glides.AACGlide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class AAC2 extends Module {

public static AAC2 mod;


    public static final int EaZy = 164;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public AAC2() {
        super(new String(new byte[]{0b1000001, 0b1000001, 0b1000011, 0b110010}), 0, "", Category.SPEED);
        mod = this;
    }

    private double GraundROFL;

    private int delay = 0;
    private int groundTicks;

    @Override
    public void onEnable() {
        if (AACGlide.mod.isToggled()) {
            Client.disable(new String(new byte[]{0b1000001, 0b1000001, 0b1000011, 0b1000111, 0b1101100, 0b1101001,
                0b1100100, 0b1100101}));
            msg(new String(new byte[]{0b11111111111111111111111110100111, 0b1100011, 0b1011001, 0b1101111, 0b1110101,
                0b100000, 0b1100011, 0b1100001, 0b1101110, 0b100000, 0b1101110, 0b1101111, 0b1110100, 0b100000,
                0b1110101, 0b1110011, 0b1100101, 0b100000, 0b1000001, 0b1000001, 0b1000011, 0b1000111, 0b1101100,
                0b1101001, 0b1100100, 0b1100101, 0b100000, 0b1100001, 0b1101110, 0b1100100, 0b100000, 0b1000001,
                0b1000001, 0b1000011, 0b100000, 0b1010011, 0b1110000, 0b1100101, 0b1100101, 0b1100100, 0b100000,
                0b1100001, 0b1110100, 0b100000, 0b1110100, 0b1101000, 0b1100101, 0b100000, 0b1110011, 0b1100001,
                0b1101101, 0b1100101, 0b100000, 0b1110100, 0b1101001, 0b1101101, 0b1100101, 0b100001}));
        }
        EventManager.register(this);
        super.onEnable();
    }

    public EventTarget onPostMotionUpdates(final EventPostMotionUpdates event) {
        if (Minecraft.thePlayer.onGround & !Minecraft.thePlayer.movementInput.jump) {
            GraundROFL = Minecraft.thePlayer.posY;
        } else if (!Minecraft.thePlayer.movementInput.jump) {
            Minecraft.thePlayer.posY = GraundROFL;
        }
        return null;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "AbgewandeltesKontraHack2";
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

        if (Minecraft.thePlayer.capabilities.isFlying) {
            return;
        }
        delay++;
        final int n = !Minecraft.thePlayer.onGround ? (groundTicks = groundTicks + 1) : 0;
        groundTicks = n;
        if (Minecraft.thePlayer.onGround && Minecraft.thePlayer.moveForward > 0.05
                && !Minecraft.thePlayer.movementInput.jump && !Minecraft.thePlayer.isOnLadder()
                && !Minecraft.thePlayer.isInWater()) {

            Minecraft.thePlayer.motionY = 0.25f;
            Minecraft.thePlayer.setSprinting(true);
        }
        if (!Minecraft.thePlayer.onGround && delay < 9 && !Minecraft.thePlayer.movementInput.jump
                && !Minecraft.thePlayer.isOnLadder() && !Minecraft.thePlayer.isInWater()) {

            Minecraft.thePlayer.motionY = -0.450f;
            Minecraft.thePlayer.motionX = 0f;
            Minecraft.thePlayer.motionZ = 0f;
            Minecraft.thePlayer.jumpMovementFactor = 0.57f;

        }
        if (!Minecraft.thePlayer.onGround && delay > 9 && !Minecraft.thePlayer.movementInput.jump
                && !Minecraft.thePlayer.isOnLadder() && !Minecraft.thePlayer.isInWater()) {

            Minecraft.thePlayer.motionY = -0.450f;
            Minecraft.thePlayer.motionX = 0f;
            Minecraft.thePlayer.motionZ = 0f;
            Minecraft.thePlayer.jumpMovementFactor = 0.66f;
        }

        if (delay > 13) {
            delay = 0;
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }

}
