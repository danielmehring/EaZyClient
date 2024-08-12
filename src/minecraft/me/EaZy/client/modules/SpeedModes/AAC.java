package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.glides.AACGlide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class AAC extends Module {

public static AAC mod;


    public static final int EaZy = 163;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public AAC() {
        super("AAC", 0, "", Category.SPEED);
        mod = this;
    }

    private double GraundROFL;

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
            return "AbgewandeltesKontraHack";
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

        if (Minecraft.thePlayer.moveForward > 0.0 && Minecraft.thePlayer.onGround && !Minecraft.thePlayer.isInWater()
                && !Minecraft.thePlayer.isInWeb && !Minecraft.thePlayer.movementInput.jump) {

            Minecraft.thePlayer.motionY = 0.25f;

        }
        if (!Minecraft.thePlayer.onGround && !Minecraft.thePlayer.movementInput.jump
                && !(Minecraft.thePlayer.fallDistance > 0) && !Minecraft.thePlayer.isInWater()
                && !Minecraft.thePlayer.isInWeb) {
            Minecraft.thePlayer.motionZ = 0;
            Minecraft.thePlayer.motionX = 0;
            Minecraft.thePlayer.jumpMovementFactor = 0.45f;
            Minecraft.thePlayer.motionY = -0.09F;
        }

    }

}
