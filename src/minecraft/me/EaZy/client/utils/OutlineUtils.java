package me.EaZy.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class OutlineUtils {

    public static final int EaZy = 233;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static void renderOne() {
        checkSetupFBO();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.0f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    }

    

    public static void renderFour(final Boolean echest, final boolean redstone) {
        if (!echest) {
            if (!redstone) {
                GL11.glColor3f(0.0f, 1.0f, 1.0f);
            } else {
                GL11.glColor3f(1.0f, 0.0f, 0.0f);
            }
        } else {
            GL11.glColor3f(1.0f, 0.0f, 1.0f);
        }
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    private static void getColors(final EntityLivingBase entity) {

        if (entity instanceof EntityPlayer && !Friends.contains((EntityPlayer) entity)
                && entity != Minecraft.thePlayer) {
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
            return;
        }

        if (entity instanceof EntityPlayer && Friends.contains((EntityPlayer) entity)
                && entity != Minecraft.thePlayer) {
            GL11.glColor3f(0.1f, 0.1f, 1.0f);
            return;
        }
        if (entity instanceof EntityMob) {
            GL11.glColor3f(1.0f, 1.0f, 0.5f);
            return;
        }
        if (entity instanceof EntityAnimal) {
            GL11.glColor3f(0.0f, 1.0f, 0.0f);
        }
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopAttrib();
    }

    

    private static void checkSetupFBO() {
        final Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    private static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);

        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.displayWidth, Minecraft.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
}
