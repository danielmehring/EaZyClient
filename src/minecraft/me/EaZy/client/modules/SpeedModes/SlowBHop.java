package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class SlowBHop extends Module {

public static SlowBHop mod;


    public static final int EaZy = 180;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int stage;
    private boolean disabling;

    private double moveSpeed;

    private double lastDist;

    public SlowBHop() {
        super(new String(new byte[]{83, 102, 109, 98, 105, 113}).substring(0, 1)
                + new String(new byte[]{121, 112, 118, 108, 107, 116}).substring(3, 4)
                + new String(new byte[]{120, 112, 114, 111, 117, 103}).substring(3, 4)
                + new String(new byte[]{104, 119, 117, 102, 106, 102}).substring(1, 2)
                + new String(new byte[]{107, 66, 101, 107, 117, 104}).substring(1, 2)
                + new String(new byte[]{72, 103, 118, 113, 110, 99}).substring(0, 1)
                + new String(new byte[]{110, 111, 99, 107, 99, 118}).substring(1, 2)
                + new String(new byte[]{110, 103, 109, 97, 112, 117}).substring(4, 5), 0, "",
                Category.SPEED);
        mod = this;
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

    public EventTarget onPreMotionUpdate(final EventPreMotionUpdates event) {
        final double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
        final double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        return null;
    }

    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public EventTarget onPreMotion(final EventMovePlayer event) {
        if (Minecraft.thePlayer.isInWater()) {
            return null;
        }
        Timer.timerSpeed = 1.0f;
        if (MathUtils.round(Minecraft.thePlayer.posY - (int) Minecraft.thePlayer.posY, 3) == MathUtils.round(0.138,
                3)) {
            Minecraft.thePlayer.motionY -= 0.08;
            event.y -= 0.09316090325960147;
            Minecraft.thePlayer.posY -= 0.09316090325960147;
        }
        if (stage == 1 && (Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f)) {
            stage = 2;
            Timer.timerSpeed = 1.0f;
            moveSpeed = 1 * getBaseMoveSpeed() - 0.01;
        } else if (stage == 2) {
            Timer.timerSpeed = 1.1f;
            stage = 3;
            Minecraft.thePlayer.motionY = 0.399399995803833;
            event.y = 0.399399995803833;
            moveSpeed *= 1.959;
        } else if (stage == 3) {
            stage = 4;
            Timer.timerSpeed = 1.0f;
            final double difference = 0.66 * (lastDist - getBaseMoveSpeed());
            moveSpeed = lastDist - difference;
        } else {
            if (Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer,
                    Minecraft.thePlayer.boundingBox.offset(0.0, Minecraft.thePlayer.motionY, 0.0)).size() > 0
                    || Minecraft.thePlayer.isCollidedVertically) {
                stage = 1;
            }
            moveSpeed = lastDist - lastDist / 159.0;
        }
        moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
        final MovementInput movementInput = Minecraft.thePlayer.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = Minecraft.thePlayer.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += forward > 0.0f ? -45 : 45;
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += forward > 0.0f ? 45 : -45;
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = forward * moveSpeed * mx2 + strafe * moveSpeed * mz2;
        event.z = forward * moveSpeed * mz2 - strafe * moveSpeed * mx2;
        return null;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = 1.0f;
        stage = 1;
        moveSpeed = Minecraft.thePlayer == null ? 5.5873 : getBaseMoveSpeed();
        if (!disabling) {
            super.onEnable();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
        moveSpeed = getBaseMoveSpeed();
        stage = 0;
        disabling = false;
        super.onDisable();
    }
}
