package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends Module {

public static Tracers mod;


    public static final int EaZy = 190;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Tracers() {
        super("Tracers", 0, "Tracer", Category.RENDER, "Draw lines to players.");
        mod = this;
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Isotopenindikator";
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
        super.onUpdate();
    }

    @Override
    public void onRender() {
        if (!isToggled()) {
            return;
        }
        Minecraft.theWorld.loadedEntityList.stream().filter((entity) -> !(!(entity instanceof EntityPlayer) || ((Entity) entity).getName().equals(mc.getSession().getUsername())
                || ((Entity) entity).isInvisible())).forEachOrdered((entity) -> {
            RenderUtils.tracerLine((Entity) entity, Friends.contains((EntityPlayer) entity) ? 1 : 0);
        });
    }
}
