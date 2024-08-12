package me.EaZy.client.modules.SpeedModes;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.utils.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.Timer;

public class GommeRape extends Module {

public static GommeRape mod;


    public static final int EaZy = 172;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public GommeRape() {
        super("GommeRape", 0, "", Category.SPEED);
        mod = this;
    }

    private double GraundROFL;

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = 1.6f;
        GraundROFL = Minecraft.thePlayer.posY;
        super.onEnable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "GammelVergewaltigen";
        } else {
            return super.getRenderName();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
        Minecraft.thePlayer.motionX /= 2;
        Minecraft.thePlayer.motionZ /= 2;
        super.onDisable();
    }

    public EventTarget onPostMotionUpdates(final EventPostMotionUpdates event) {
        if (!Minecraft.thePlayer.movementInput.jump) {
            if (Minecraft.thePlayer.onGround) {
                GraundROFL = Minecraft.thePlayer.posY;
            } else if (Minecraft.thePlayer.motionX != 0 && Minecraft.thePlayer.motionZ != 0) {
                Minecraft.thePlayer.posY = GraundROFL;
            }
        }
        return null;
    }

    public EventTarget onPlayerMove(final EventMovePlayer e) {
        if (!Minecraft.thePlayer.isInWater() && !Minecraft.thePlayer.capabilities.isFlying) {
            e.x *= 1;
            e.z *= 1;
            if (!Minecraft.thePlayer.movementInput.jump && e.y >= 0) {
                e.y *= 0.85;
            }
            Timer.timerSpeed = Minecraft.thePlayer.ticksExisted % 20 == 0 ? 10.0f
                    : Minecraft.thePlayer.ticksExisted % 10 == 0 ? 5.0f
                            : Minecraft.thePlayer.ticksExisted % 4 == 0 ? 2.0f
                                    : Minecraft.thePlayer.ticksExisted % 2 == 0 ? 0.95f : 1.0f;
        } else {
            Timer.timerSpeed = 1.0f;
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
        if (Minecraft.thePlayer.onGround && MovementUtil.areWalkingKeysDown()) {
            Minecraft.thePlayer.jump();
        }
    }

}
