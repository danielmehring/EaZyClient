package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPreUpdate;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class NCPRace extends Module {

public static NCPRace mod;


    public static final int EaZy = 176;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int level = 1;
    private boolean disabling;

    private double moveSpeed = 0.2873;

    private double lastDist;
    private boolean speedTick;
    private final float speedTimer = 1.3f;
    private int timerDelay;

    private final boolean reverse = true;
    private int groundTicks;

    public NCPRace() {
        super(new String(new byte[]{0b1001110, 0b1000011, 0b1010000, 0b1010010, 0b1100001, 0b1100011, 0b1100101}), 0, "", Category.SPEED);
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "NeinBetrügenPlusRennen";
        } else {
            return super.getRenderName();
        }
    }

    public EventTarget onPreUpdate(final EventPreUpdate event) {
        if (Minecraft.thePlayer.isInWater()) {
            return null;
        }
        if (Minecraft.thePlayer.fallDistance > 0.4 && !Minecraft.thePlayer.movementInput.jump) {
            return null;
        }
        if (!Minecraft.thePlayer.movementInput.jump && groundTicks < 1) {
            Minecraft.thePlayer.posY = Minecraft.thePlayer.groundY;
        }
        if (!(!reverse || Minecraft.gameSettings.keyBindJump.pressed || Minecraft.thePlayer.isOnLadder()
                || Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.isInsideOfMaterial(Material.lava)
                || Minecraft.thePlayer.isInWater()
                || this.getBlock(-1.1) instanceof BlockAir
                && (this.getBlock(-0.1) instanceof BlockAir || Minecraft.thePlayer.motionX == 0.0
                || Minecraft.thePlayer.motionZ == 0.0 || !reverse || Minecraft.thePlayer.onGround
                || Minecraft.thePlayer.fallDistance >= 3.0f || Minecraft.thePlayer.fallDistance <= 0.05)
                || level != 3)) {
            Minecraft.thePlayer.motionY = -0.3994;
        }
        final int n = !Minecraft.thePlayer.onGround ? (groundTicks = groundTicks + 1) : 0;
        groundTicks = n;
        final double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
        final double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
        lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        return null;
    }

    private Block getBlock(final AxisAlignedBB bb2) {
        final int y2 = (int) bb2.minY;
        int x2 = MathHelper.floor_double(bb2.minX);
        while (x2 < MathHelper.floor_double(bb2.maxX) + 1) {
            int z2 = MathHelper.floor_double(bb2.minZ);
            while (z2 < MathHelper.floor_double(bb2.maxZ) + 1) {
                final Block block = Minecraft.theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
                if (block != null) {
                    return block;
                }
                ++z2;
            }
            ++x2;
        }
        return null;
    }

    private Block getBlock(final double offset) {
        return this.getBlock(Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, offset, 0.0));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = 1.0f;
        level = 1;
        moveSpeed = Minecraft.thePlayer == null ? 0.2873 : getBaseMoveSpeed();
        if (!disabling) {
            super.onEnable();
        }
    }

    public EventTarget onPreMotion(final EventMovePlayer event) {
        if (Minecraft.thePlayer.isInWater()) {
            return null;
        }
        if (Minecraft.thePlayer.fallDistance > 0.4 && !Minecraft.thePlayer.movementInput.jump) {
            return null;
        }
        if (!Minecraft.thePlayer.movementInput.jump && groundTicks < 1) {
            Minecraft.thePlayer.posY = Minecraft.thePlayer.groundY;
        }
        speedTick = !speedTick;
        ++timerDelay;
        timerDelay %= 5;
        if (timerDelay != 0) {
            Timer.timerSpeed = 1.0f;
        } else {
            if (PlayerUtil.isPlayerMoving()) {
                Timer.timerSpeed = 32767.0f;
            }
            if (PlayerUtil.isPlayerMoving()) {
                Timer.timerSpeed = speedTimer;
                Minecraft.thePlayer.motionX *= 1.0199999809265137;
                Minecraft.thePlayer.motionZ *= 1.0199999809265137;
            }
        }
        if (Minecraft.thePlayer.onGround && PlayerUtil.isPlayerMoving()) {
            level = 2;
        }
        if (level == 1 && (Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f)) {
            if (!Minecraft.thePlayer.onGround) {
                return null;
            }
            level = 2;
            moveSpeed = 1.38 * getBaseMoveSpeed() - 0.01;
        } else if (level == 2) {
            level = 3;
            Minecraft.thePlayer.motionY = 0.399399995803833;
            event.y = 0.399399995803833;
            moveSpeed *= 2.149;
        } else if (level == 3) {
            level = 4;
            final double difference = 0.66 * (lastDist - getBaseMoveSpeed());
            moveSpeed = lastDist - difference;
        } else {
            if (Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer,
                    Minecraft.thePlayer.boundingBox.offset(0.0, Minecraft.thePlayer.motionY, 0.0)).size() > 0
                    || Minecraft.thePlayer.isCollidedVertically) {
                level = 1;
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
        Minecraft.thePlayer.stepHeight = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        } else {

            if (Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer,
                    Minecraft.thePlayer.boundingBox.expand(0.5, 0.0, 0.5)).size() > 0) {
            }
            if (forward != 0.0f) {
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
        }
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

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
        moveSpeed = getBaseMoveSpeed();
        level = 0;
        disabling = false;
        super.onDisable();
    }
}
