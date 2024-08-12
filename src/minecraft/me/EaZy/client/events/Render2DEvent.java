package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class Render2DEvent
        implements Event {

    public static final int EaZy = 50;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private ScaledResolution scaled;
    private float partialTicks;

    public Render2DEvent(ScaledResolution scaled, float partialTicks) {
        this.setScaledResolution(scaled);
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaled;
    }

    public void setScaledResolution(ScaledResolution scaled) {
        this.scaled = scaled;
    }
}
