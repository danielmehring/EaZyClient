package me.EaZy.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;

public class ParticleSystem {

    public static final int EaZy = 199;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final int amount;
    private final boolean inv;
    private final ArrayList<Particle> particles;

    public ParticleSystem(final int amount, boolean inv) {
        this.amount = amount;
        this.inv = inv;
        particles = new ArrayList<>();
    }

    public void render(final int mouseX, final int mouseY) {
        if (particles.size() < amount && !Mouse.isButtonDown(1)) {
            newParticle();
        } else if (particles.size() > amount) {
            particles.remove(0);
        }
        final ArrayList<Particle> remove = new ArrayList<>();
        particles.stream().map((p) -> {
            if (p.isReset()) {
                remove.add(p);
            }
            return p;
        }).forEachOrdered((p) -> {
            final int range = 50;
            if (Math.abs(mouseX - p.getX()) < range && Math.abs(mouseY - p.getY()) < range) {
                p.render(particles, mouseX, mouseY);
            } else {
                p.render(null, mouseX, mouseY);
            }
        });

        remove.forEach((p) -> {
            particles.remove(p);
        });
    }

    private void newParticle() {
        final ScaledResolution scr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
                Minecraft.displayHeight);
        final Random r = new Random();
        final int id = r.nextInt(1000);
        for (final Particle p : particles) {
            if (id == p.getID()) {
                return;
            }
        }

        particles.add(new Particle(r.nextInt(scr.getScaledWidth()), r.nextInt(scr.getScaledHeight()), id, inv));
    }
}
