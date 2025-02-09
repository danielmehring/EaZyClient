package me.EaZy.client.modules;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class LongJump extends Module {

public static LongJump mod;


    public static final int EaZy = 136;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public LongJump() {
        super("LongJump", 0, "", Category.MOVEMENT, "LongJump for latest NCP.\nCan be used as fly.");
        final ArrayList<String> options = new ArrayList<>();
        options.add("NCP");
        options.add("Jump");
        Client.setmgr.rSetting(new Setting("Mode", this, "NCP", options));
        mod = this;
    }

    private String suffix;

    private static String renderName = "NCP";
    private int delay3;

    private final TimeHelper time = new TimeHelper();
    private int airTicks;
    private int groundTicks;
    private int timeonground;
    private final double[] speedVals = new double[]{0.420606, 0.417924, 0.415258, 0.412609, 0.409977, 0.407361,
        0.404761, 0.402178, 0.399611, 0.39706, 0.394525, 0.392, 0.3894, 0.38644, 0.383655, 0.381105, 0.37867,
        0.37625, 0.37384, 0.37145, 0.369, 0.3666, 0.3642, 0.3618, 0.35945, 0.357, 0.354, 0.351, 0.348, 0.345, 0.342,
        0.339, 0.336, 0.333, 0.33, 0.327, 0.324, 0.321, 0.318, 0.315, 0.312, 0.309, 0.307, 0.305, 0.303, 0.3, 0.297,
        0.295, 0.293, 0.291, 0.289, 0.287, 0.285, 0.283, 0.281, 0.279, 0.277, 0.275, 0.273, 0.271, 0.269, 0.267,
        0.265, 0.263, 0.261, 0.259, 0.257, 0.255, 0.253, 0.251, 0.249, 0.247, 0.245, 0.243, 0.241, 0.239, 0.237};

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "LangSprung+Gleitgel [" + renderName + "]";
            } else {
                suffix = "LangSprung+Gleitgel";
            }
        } else {

            if (Configs.suffix) {
                suffix = "Longjump [" + renderName + "]";
            } else {
                suffix = "Longjump";
            }

        }

        return suffix;
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

        float xDir2;
        float zDir2;
        float direction;
        float zDir;
        float xDir;

        timeonground = Minecraft.thePlayer.onGround ? ++timeonground : 0;

        if (Client.setmgr.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("Jump")) {
            renderName = "Jump";
            if (Minecraft.thePlayer.moveForward == 0.0f && Minecraft.thePlayer.moveStrafing == 0.0f) {
                net.minecraft.util.Timer.timerSpeed = 1.0f;
                return;
            }

            if (Minecraft.thePlayer.onGround) {
                delay3++;
                if (delay3 > 3) {
                    Minecraft.thePlayer.jump();
                } else {

                }

            } else {
                delay3 = 0;
            }
            direction = Minecraft.thePlayer.rotationYaw + (Minecraft.thePlayer.moveForward < 0.0f ? 180 : 0)
                    + (Minecraft.thePlayer.moveStrafing > 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f
                                    ? -0.5f : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f) : 0.0f)
                    - (Minecraft.thePlayer.moveStrafing < 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f
                                    ? -0.5f : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f) : 0.0f);
            xDir2 = (float) Math.cos((direction + 90.0f) * 3.141592653589793 / 180.0);
            zDir2 = (float) Math.sin((direction + 90.0f) * 3.141592653589793 / 180.0);
            if (Minecraft.thePlayer.isCollidedVertically && (Minecraft.gameSettings.keyBindForward.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindLeft.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindRight.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindBack.getIsKeyPressed()
                    && Minecraft.gameSettings.keyBindJump.getIsKeyPressed())) {
                Minecraft.thePlayer.motionX = xDir2 * 0.29f;
                Minecraft.thePlayer.motionZ = zDir2 * 0.29f;
            }
            if (Minecraft.thePlayer.motionY == 0.33319999363422365
                    && (Minecraft.gameSettings.keyBindForward.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindLeft.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindRight.getIsKeyPressed()
                    || Minecraft.gameSettings.keyBindBack.getIsKeyPressed())) {
                if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    Minecraft.thePlayer.motionX = xDir2 * 1.31;
                    Minecraft.thePlayer.motionZ = zDir2 * 1.31;
                } else {
                    Minecraft.thePlayer.motionX = xDir2 * 1.25;
                    Minecraft.thePlayer.motionZ = zDir2 * 1.25;
                }
            }
        }

        if (Client.setmgr.getSettingByName(this, "Mode").getValString().equalsIgnoreCase("NCP")) {
            renderName = "NCP";
            if (Minecraft.gameSettings.keyBindSneak.pressed) {
                return;
            }

            if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0
                    && Minecraft.thePlayer.motionY < 0) {
                if (Minecraft.thePlayer.ticksExisted % 2 == 0) {

                } else {

                }

            }

            Minecraft.thePlayer.setSprinting(false);
            Minecraft.gameSettings.keyBindLeft.pressed = false;
            Minecraft.gameSettings.keyBindRight.pressed = false;
            Minecraft.gameSettings.keyBindBack.pressed = false;
            if (isMoving()) {
                if (Keyboard.isKeyDown(56)) {
                    updatePosition(0.0, 2.147483647E9, 0.0);
                }
                if (Minecraft.theWorld != null && Minecraft.thePlayer != null && Minecraft.thePlayer.onGround
                        && !Minecraft.thePlayer.isDead) {
                }
                final float direction3 = Minecraft.thePlayer.rotationYaw
                        + (Minecraft.thePlayer.moveForward < 0.0f ? 180 : 0)
                        + (Minecraft.thePlayer.moveStrafing > 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f
                                        ? -0.5f : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f) : 0.0f)
                        - (Minecraft.thePlayer.moveStrafing < 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f
                                        ? -0.5f : Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f) : 0.0f);
                xDir = (float) Math.cos((direction3 + 90.0f) * 3.141592653589793 / 180.0);
                zDir = (float) Math.sin((direction3 + 90.0f) * 3.141592653589793 / 180.0);
                if (!Minecraft.thePlayer.isCollidedVertically) {
                    ++airTicks;
                    if (Minecraft.gameSettings.keyBindSneak.isPressed()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(
                                new C03PacketPlayer.C04PacketPlayerPosition(0.0, 2.147483647E9, 0.0, false));
                    }
                    groundTicks = 0;
                    if (!Minecraft.thePlayer.isCollidedVertically) {
                        if (Minecraft.thePlayer.motionY == -0.07190068807140403) {
                            Minecraft.thePlayer.motionY *= 0.3499999940395355;
                        }
                        if (Minecraft.thePlayer.motionY == -0.10306193759436909) {
                            Minecraft.thePlayer.motionY *= 0.550000011920929;
                        }
                        if (Minecraft.thePlayer.motionY == -0.13395038817442878) {
                            Minecraft.thePlayer.motionY *= 0.6700000166893005;
                        }
                        if (Minecraft.thePlayer.motionY == -0.16635183030382) {
                            Minecraft.thePlayer.motionY *= 0.6899999976158142;
                        }
                        if (Minecraft.thePlayer.motionY == -0.19088711097794803) {
                            Minecraft.thePlayer.motionY *= 0.7099999785423279;
                        }
                        if (Minecraft.thePlayer.motionY == -0.21121925191528862) {
                            Minecraft.thePlayer.motionY *= 0.20000000298023224;
                        }
                        if (Minecraft.thePlayer.motionY == -0.11979897632390576) {
                            Minecraft.thePlayer.motionY *= 0.9300000071525574;
                        }
                        if (Minecraft.thePlayer.motionY == -0.18758479151225355) {
                            Minecraft.thePlayer.motionY *= 0.7200000286102295;
                        }
                        if (Minecraft.thePlayer.motionY == -0.21075983825251726) {
                            Minecraft.thePlayer.motionY *= 0.7599999904632568;
                        }
                        if (getDistance(Minecraft.thePlayer, 69.0) < 0.5
                                && !BlockUtils
                                        .getBlock(new BlockPos(Minecraft.thePlayer.posX,
                                                Minecraft.thePlayer.posY - 0.32, Minecraft.thePlayer.posZ))
                                        .isFullCube()) {
                            if (Minecraft.thePlayer.motionY == -0.23537393014173347) {
                                Minecraft.thePlayer.motionY *= 0.029999999329447746;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08531999505205401) {
                                Minecraft.thePlayer.motionY *= -0.5;
                            }
                            if (Minecraft.thePlayer.motionY == -0.03659320313669756) {
                                Minecraft.thePlayer.motionY *= -0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.07481386749524899) {
                                Minecraft.thePlayer.motionY *= -0.07000000029802322;
                            }
                            if (Minecraft.thePlayer.motionY == -0.0732677700939672) {
                                Minecraft.thePlayer.motionY *= -0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.07480988066790395) {
                                Minecraft.thePlayer.motionY *= -0.03999999910593033;
                            }
                            if (Minecraft.thePlayer.motionY == -0.0784000015258789) {
                                Minecraft.thePlayer.motionY *= 0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08608320193943977) {
                                Minecraft.thePlayer.motionY *= 0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08683615560584318) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08265497329678266) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08245009535659828) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08244005633718426) {
                                Minecraft.thePlayer.motionY = -0.08243956442521608;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08243956442521608) {
                                Minecraft.thePlayer.motionY = -0.08244005590677261;
                            }
                            if (Minecraft.thePlayer.motionY > -0.1 && Minecraft.thePlayer.motionY < -0.08
                                    && !Minecraft.thePlayer.onGround && Minecraft.gameSettings.keyBindForward.pressed) {
                                Minecraft.thePlayer.motionY = -9.999999747378752E-5;
                            }
                        } else {
                            if (Minecraft.thePlayer.motionY < -0.2 && Minecraft.thePlayer.motionY > -0.24) {
                                Minecraft.thePlayer.motionY *= 0.7;
                            }
                            if (Minecraft.thePlayer.motionY < -0.25 && Minecraft.thePlayer.motionY > -0.32) {
                                Minecraft.thePlayer.motionY *= 0.8;
                            }
                            if (Minecraft.thePlayer.motionY < -0.35 && Minecraft.thePlayer.motionY > -0.8) {
                                Minecraft.thePlayer.motionY *= 0.98;
                            }
                            if (Minecraft.thePlayer.motionY < -0.8 && Minecraft.thePlayer.motionY > -1.6) {
                                Minecraft.thePlayer.motionY *= 0.99;
                            }
                        }
                    }
                    net.minecraft.util.Timer.timerSpeed = 0.95f;
                    if (Minecraft.gameSettings.keyBindForward.pressed) {
                        try {
                            Minecraft.thePlayer.motionX = xDir * speedVals[airTicks - 1] * 3.0
                                    * addSpeedForSpeedEffect();
                            Minecraft.thePlayer.motionZ = zDir * speedVals[airTicks - 1] * 3.0
                                    * addSpeedForSpeedEffect();
                        } catch (final ArrayIndexOutOfBoundsException var6_12) {
                        }
                    } else {
                        Minecraft.thePlayer.motionX = 0.0;
                        Minecraft.thePlayer.motionZ = 0.0;
                    }
                } else {
                    net.minecraft.util.Timer.timerSpeed = 1.0f;
                    airTicks = 0;
                    ++groundTicks;
                    Minecraft.thePlayer.motionX /= 13.0;
                    Minecraft.thePlayer.motionZ /= 13.0;
                    if (groundTicks == 1) {
                        updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
                        updatePosition(Minecraft.thePlayer.posX + 0.0624, Minecraft.thePlayer.posY,
                                Minecraft.thePlayer.posZ);
                        updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.419,
                                Minecraft.thePlayer.posZ);
                        updatePosition(Minecraft.thePlayer.posX + 0.0624, Minecraft.thePlayer.posY,
                                Minecraft.thePlayer.posZ);
                        updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.419,
                                Minecraft.thePlayer.posZ);
                    }
                    if (groundTicks > 2) {
                        groundTicks = 0;
                        Minecraft.thePlayer.motionX = xDir * 0.3;
                        Minecraft.thePlayer.motionZ = zDir * 0.3;
                        Minecraft.thePlayer.motionY = 0.42399999499320984;
                    }
                }
            }
        }
        super.onUpdate();
    }

    private double addSpeedForSpeedEffect() {
        double baseSpeed = 1.0;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed = 0.4;
        }
        return baseSpeed;
    }

    private void updatePosition(final double x, final double y, final double z) {
        Minecraft.thePlayer.sendQueue
                .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, Minecraft.thePlayer.onGround));
    }

    private double getDistance(final EntityPlayer player, final double distance) {
        final List boundingBoxes = player.worldObj.getCollidingBoundingBoxes(player,
                player.getEntityBoundingBox().addCoord(0.0, -distance, 0.0));
        if (boundingBoxes.isEmpty()) {
            return 0.0;
        }
        final double y = 0.0;

        // for (AxisAlignedBB boundingBox : boundingBoxes) {
        // if (boundingBox.maxY <= y) continue;
        // y = boundingBox.maxY;
        // }
        return player.posY - y;
    }

    public boolean isMoving() {
        return !(Minecraft.thePlayer.moveForward == 0.0f && Minecraft.thePlayer.moveStrafing == 0.0f);
    }

    @Override
    public void onEnable() {
        delay3 = 0;
        Client.disable("Speed");
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        Minecraft.thePlayer.motionX = 0;
        Minecraft.thePlayer.motionZ = 0;
        super.onDisable();
    }
}
