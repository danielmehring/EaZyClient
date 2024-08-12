/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.opengl.GL11
 */
package me.EaZy.client.modules;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Trajectories extends Module {

public static Trajectories mod;


    public static final int EaZy = 191;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public Trajectories() {
        super("Trajectories", 0, "trj", Category.RENDER, "Shows the flight path of\narrows, snowballs,...");
        mod = this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public String getRenderName() {
        if (GuiMainMenu.ersterapril) {
            return "Flugbahnindikator";
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
        final EntityPlayerSP player = Minecraft.thePlayer;
        final ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null) {
            return;
        }
        final Item item = stack.getItem();
        if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg
                || item instanceof ItemEnderPearl
                || item instanceof ItemPotion && ItemPotion.isSplash(stack.getItemDamage()))) {
            return;
        }
        final boolean usingBow = player.getCurrentEquippedItem().getItem() instanceof ItemBow;
        double arrowPosX = player.lastTickPosX
                + (player.posX - player.lastTickPosX) * Minecraft.timer.renderPartialTicks
                - MathHelper.cos((float) Math.toRadians(player.rotationYaw)) * 0.16f;

        double arrowPosY = player.lastTickPosY
                + (player.posY - player.lastTickPosY) * Minecraft.timer.renderPartialTicks + player.getEyeHeight()
                - 0.1;

        double arrowPosZ = player.lastTickPosZ
                + (player.posZ - player.lastTickPosZ) * Minecraft.timer.renderPartialTicks
                - MathHelper.sin((float) Math.toRadians(player.rotationYaw)) * 0.16f;
        final float arrowMotionFactor = usingBow ? 1.0f : 0.4f;
        final float yaw = (float) Math.toRadians(player.rotationYaw);
        final float pitch = (float) Math.toRadians(player.rotationPitch);
        float arrowMotionX = -MathHelper.sin(yaw) * MathHelper.cos(pitch) * arrowMotionFactor;
        float arrowMotionY = -MathHelper.sin(pitch) * arrowMotionFactor;
        float arrowMotionZ = MathHelper.cos(yaw) * MathHelper.cos(pitch) * arrowMotionFactor;
        final double arrowMotion = Math
                .sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX = (float) (arrowMotionX / arrowMotion);
        arrowMotionY = (float) (arrowMotionY / arrowMotion);
        arrowMotionZ = (float) (arrowMotionZ / arrowMotion);
        if (usingBow) {
            float bowPower = (72000 - player.getItemInUseCount()) / 20.0f;
            if ((bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f) > 1.0f) {
                bowPower = 1.0f;
            }
            if (bowPower <= 0.1f) {
                bowPower = 1.0f;
            }
            arrowMotionX *= bowPower *= 3.0f;
            arrowMotionY *= bowPower;
            arrowMotionZ *= bowPower;
        } else {
            arrowMotionX = (float) (arrowMotionX * 1.5);
            arrowMotionY = (float) (arrowMotionY * 1.5);
            arrowMotionZ = (float) (arrowMotionZ * 1.5);
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // gibts gar ned xD
        GL11.glEnable(32925);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.8f);

        final double gravity = item instanceof ItemPotion ? 0.2 : usingBow ? 0.03 : 0.03;
        final Vec3 playerVector = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        GL11.glColor3f((float) Client.getColor(0l).getRed() / 255, (float) Client.getColor(0l).getGreen() / 255,
                (float) Client.getColor(0l).getBlue() / 255);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        int i = 0;
        while (i < 1000) {
            GL11.glVertex3d(arrowPosX - RenderManager.renderPosX, arrowPosY - RenderManager.renderPosY,
                    arrowPosZ - RenderManager.renderPosZ);
            arrowMotionX = (float) (arrowMotionX * 0.99);
            arrowMotionY = (float) (arrowMotionY * 0.99);
            arrowMotionZ = (float) (arrowMotionZ * 0.99);
            arrowMotionY = (float) (arrowMotionY - gravity);
            if (Minecraft.theWorld.rayTraceBlocks(playerVector, new Vec3(arrowPosX += arrowMotionX,
                    arrowPosY += arrowMotionY, arrowPosZ += arrowMotionZ)) != null) {
                break;
            }
            ++i;
        }
        GL11.glEnd();
        final double renderX = arrowPosX - RenderManager.renderPosX;
        final double renderY = arrowPosY - RenderManager.renderPosY;
        final double renderZ = arrowPosZ - RenderManager.renderPosZ;
        final AxisAlignedBB bb = new AxisAlignedBB(renderX - 0.5, renderY - 0.5, renderZ - 0.5, renderX + 0.5,
                renderY + 0.5, renderZ + 0.5);
        GL11.glColor4f((float) Client.getColor(0l).getRed() / 255, (float) Client.getColor(0l).getGreen() / 255,
                (float) Client.getColor(0l).getBlue() / 255, (float) 0.15000000596046448);
        RenderUtils.drawColorBox(bb);
        GL11.glColor4f((float) Client.getColor(0l).getRed() / 255, (float) Client.getColor(0l).getGreen() / 255,
                (float) Client.getColor(0l).getBlue() / 255, (float) 0.5);
        RenderGlobal.drawOutlinedBoundingBox(bb, -1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // gibts gar ned xD
        GL11.glDisable(32925);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }
}
