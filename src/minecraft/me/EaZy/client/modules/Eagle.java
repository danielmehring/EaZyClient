package me.EaZy.client.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;

public class Eagle extends Module {

public static Eagle mod;


    public static final int EaZy = 106;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Eagle() {
        super("Eagle", 0, "Elegal", Category.WORLD);
        Client.setmgr.rSetting(new Setting("Black Eagle", this, false));
        Client.setmgr.rSetting(new Setting("Test4ItemBlock", this, true));
        mod = this;
    }

    private int delay;
    int delay4onestack;

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "SchwarzerAdler";
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
        if (Client.setmgr.getSettingByName(this, "Test4ItemBlock").getValBoolean()) {
            if (Minecraft.thePlayer.getHeldItem() != null
                    && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {

                if (!Minecraft.thePlayer.movementInput.jump) {
                    delay4onestack = 0;
                    if (Minecraft.thePlayer.onGround
                            && Minecraft.theWorld
                                    .getBlockState(new BlockPos(Minecraft.thePlayer.posX,
                                            Minecraft.thePlayer.posY - 0.5, Minecraft.thePlayer.posZ))
                                    .getBlock() instanceof BlockAir
                            && Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                        if (!Minecraft.thePlayer.capabilities.isFlying) {
                            Minecraft.gameSettings.keyBindSneak.pressed = true;
                        }
                        if (Client.setmgr.getSettingByName(this, "Black Eagle").getValBoolean()) {
                            if (Minecraft.thePlayer.moveForward > 0) {
                            } else {
                                if (Minecraft.gameSettings.keyBindRight.pressed
                                        || Minecraft.gameSettings.keyBindLeft.pressed
                                        || Minecraft.gameSettings.keyBindForward.pressed
                                        || Minecraft.gameSettings.keyBindBack.pressed && delay > 5) {
                                    Minecraft.thePlayer.motionX *= 1.6f;
                                    Minecraft.thePlayer.motionZ *= 1.6f;
                                }
                            }
                            Minecraft.rightClickDelayTimer = 0;
                        }
                    }
                    if (Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX,
                            Minecraft.thePlayer.posY - 1, Minecraft.thePlayer.posZ)).getBlock() instanceof BlockAir) {
                        delay++;
                    } else {
                        Minecraft.gameSettings.keyBindSneak.pressed = false;
                    }
                } else {
                    if (!(Minecraft.gameSettings.keyBindRight.pressed && Minecraft.gameSettings.keyBindLeft.pressed
                            && Minecraft.gameSettings.keyBindForward.pressed
                            && Minecraft.gameSettings.keyBindBack.pressed)) {
                        Minecraft.rightClickDelayTimer = 0;
                    }
                    if (!Minecraft.thePlayer.onGround && Minecraft.thePlayer.motionY > 0) {
                        delay4onestack++;
                        if (delay4onestack > 0) {
                            if (!Minecraft.thePlayer.capabilities.isFlying && !(Minecraft.thePlayer.moveForward > 0)) {
                                Minecraft.thePlayer.motionX /= 1.08f;
                                Minecraft.thePlayer.motionZ /= 1.08f;
                                Minecraft.gameSettings.keyBindSneak.pressed = true;
                            }
                        }
                    }
                    if (Minecraft.thePlayer.onGround) {
                        delay4onestack = 0;
                        Minecraft.gameSettings.keyBindSneak.pressed = false;
                    }
                }
            } else {
                try {
                    if (!Keyboard.isKeyDown(Minecraft.gameSettings.keyBindSneak.getKeyCode())
                            && Minecraft.gameSettings.keyBindSneak.pressed) {
                        Minecraft.gameSettings.keyBindSneak.pressed = false;
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        } else {

            if (!Minecraft.thePlayer.movementInput.jump) {
                delay4onestack = 0;
                if (Minecraft.thePlayer.onGround
                        && Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX,
                                Minecraft.thePlayer.posY - 0.5, Minecraft.thePlayer.posZ))
                                .getBlock() instanceof BlockAir
                        && Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                    if (!Minecraft.thePlayer.capabilities.isFlying) {
                        Minecraft.gameSettings.keyBindSneak.pressed = true;
                    }
                    if (Client.setmgr.getSettingByName(this, "Black Eagle").getValBoolean()) {
                        if (Minecraft.thePlayer.moveForward > 0) {
                        } else {
                            if (Minecraft.gameSettings.keyBindRight.pressed
                                    || Minecraft.gameSettings.keyBindLeft.pressed
                                    || Minecraft.gameSettings.keyBindForward.pressed
                                    || Minecraft.gameSettings.keyBindBack.pressed && delay < 5) {
                                Minecraft.thePlayer.motionX *= 1.6f;
                                Minecraft.thePlayer.motionZ *= 1.6f;
                            }
                        }
                        Minecraft.rightClickDelayTimer = 0;
                    }
                }
                if (Minecraft.theWorld.getBlockState(
                        new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1, Minecraft.thePlayer.posZ))
                        .getBlock() instanceof BlockAir) {
                    delay++;
                } else {
                    Minecraft.gameSettings.keyBindSneak.pressed = false;
                }
            } else {
                Minecraft.rightClickDelayTimer = 0;
                if (!Minecraft.thePlayer.onGround && Minecraft.thePlayer.motionY > 0) {
                    delay4onestack++;
                    if (delay4onestack > 0) {
                        if (!Minecraft.thePlayer.capabilities.isFlying && !(Minecraft.thePlayer.moveForward > 0)) {
                            Minecraft.thePlayer.motionX /= 1.08f;
                            Minecraft.thePlayer.motionZ /= 1.08f;
                            Minecraft.gameSettings.keyBindSneak.pressed = true;
                        }
                    }
                }
                if (Minecraft.thePlayer.onGround) {
                    delay4onestack = 0;
                    Minecraft.gameSettings.keyBindSneak.pressed = false;
                }
            }

        }
        super.onUpdate();
    }

    @Override
    public void onEnable() {
        delay4onestack = 0;
        Minecraft.rightClickDelayTimer = 6;
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        delay4onestack = 0;
        EventManager.unregister(this);
        super.onDisable();
        Minecraft.gameSettings.keyBindSneak.pressed = false;
    }
}
