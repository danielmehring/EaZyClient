package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.main.Client;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class FastWeb extends Module {

public static FastWeb mod;


    public static final int EaZy = 113;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private String suffix;

    private int level = 1;
    private boolean disabling;

    private final boolean reverse = true;
    private int groundTicks;

    public FastWeb() {
        super("FastWeb", 36, "web", Category.MOVEMENT, "Go faster through\ncobwebs.\n§4Only NCP!");
        Client.setmgr.rSetting(new Setting("Speed", this, 16.0f, 1.0f, 50.0f, true));
        mod = this;
    }

    @Override
    public String getRenderName() {

        if (GuiMainMenu.ersterapril) {
            if (Configs.suffix) {
                suffix = "SchnellesWeben [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "SchnellesWeben";
            }
        } else {

            if (Configs.suffix) {
                suffix = "FastWeb [" + Client.setmgr.getSettingByName(this, "Speed").getValFloat() + "]";
            } else {
                suffix = "FastWeb";
            }

        }
        return suffix;
    }

    public EventTarget onPreMotionUpdates(final EventPreMotionUpdates event) {
        if (!YesCheat.enabled) {
            return null;
        }
        if (!Minecraft.thePlayer.isInWeb) {
            return null;
        }
        if (!(!reverse || Minecraft.gameSettings.keyBindJump.pressed || Minecraft.thePlayer.isOnLadder()
                || Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.isInsideOfMaterial(Material.lava)
                || Minecraft.thePlayer.isInWater()
                || (this.getBlock(-1.1) instanceof BlockAir || this.getBlock(-1.1) instanceof BlockAir)
                && (this.getBlock(-0.1) instanceof BlockAir || Minecraft.thePlayer.motionX == 0.0
                || Minecraft.thePlayer.motionZ == 0.0 || !reverse || Minecraft.thePlayer.onGround
                || Minecraft.thePlayer.fallDistance >= 3.0f || Minecraft.thePlayer.fallDistance <= 0.05)
                || level != 3)) {
            Minecraft.thePlayer.motionY = -0.3994;
        }
        final int n = Minecraft.thePlayer.onGround ? (groundTicks = groundTicks + 1) : 0;
        groundTicks = n;
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
        if (YesCheat.enabled) {
            Timer.timerSpeed = 1.0f;
            level = 1;
            if (!disabling) {
                super.onEnable();
            }
        } else {
            super.onEnable();
        }
    }

    public EventTarget onPreMotion(final EventMovePlayer event) {
        if (!YesCheat.enabled) {
            return null;
        }
        if (!Minecraft.thePlayer.isInWeb) {
            Timer.timerSpeed = 1.0f;
            return null;
        }

        if (Minecraft.thePlayer.movementInput.sneak) {
            event.y = -0.1;
        } else {
            event.y = 0;
        }
        event.x *= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
        event.z *= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
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
        if (YesCheat.enabled) {
            Timer.timerSpeed = 1.0f;
            level = 0;
            disabling = false;
        }
        super.onDisable();
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

        if (!YesCheat.enabled) {
            Minecraft.thePlayer.isInWeb = false;
        }

        super.onUpdate();
    }
}
