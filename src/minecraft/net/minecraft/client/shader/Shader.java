package net.minecraft.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;

import java.util.Iterator;
import java.util.List;

import javax.vecmath.Matrix4f;

import com.google.common.collect.Lists;

public class Shader {

    public static final int EaZy = 905;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final ShaderManager manager;
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List listAuxFramebuffers = Lists.newArrayList();
    private final List listAuxNames = Lists.newArrayList();
    private final List listAuxWidths = Lists.newArrayList();
    private final List listAuxHeights = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    // private static final String __OBFID = "http://https://fuckuskid00001042";

    public Shader(final IResourceManager p_i45089_1_, final String p_i45089_2_, final Framebuffer p_i45089_3_,
            final Framebuffer p_i45089_4_) throws JsonException {
        manager = new ShaderManager(p_i45089_1_, p_i45089_2_);
        framebufferIn = p_i45089_3_;
        framebufferOut = p_i45089_4_;
    }

    public void deleteShader() {
        manager.deleteShader();
    }

    public void addAuxFramebuffer(final String p_148041_1_, final Object p_148041_2_, final int p_148041_3_,
            final int p_148041_4_) {
        listAuxNames.add(listAuxNames.size(), p_148041_1_);
        listAuxFramebuffers.add(listAuxFramebuffers.size(), p_148041_2_);
        listAuxWidths.add(listAuxWidths.size(), p_148041_3_);
        listAuxHeights.add(listAuxHeights.size(), p_148041_4_);
    }

    private void preLoadShader() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.func_179144_i(0);
    }

    public void setProjectionMatrix(final Matrix4f p_148045_1_) {
        projectionMatrix = p_148045_1_;
    }

    public void loadShader(final float p_148042_1_) {
        preLoadShader();
        framebufferIn.unbindFramebuffer();
        final float var2 = framebufferOut.framebufferTextureWidth;
        final float var3 = framebufferOut.framebufferTextureHeight;
        GlStateManager.viewport(0, 0, (int) var2, (int) var3);
        manager.addSamplerTexture("DiffuseSampler", framebufferIn);

        for (int var4 = 0; var4 < listAuxFramebuffers.size(); ++var4) {
            manager.addSamplerTexture((String) listAuxNames.get(var4), listAuxFramebuffers.get(var4));
            manager.getShaderUniformOrDefault("AuxSize" + var4).set(((Integer) listAuxWidths.get(var4)), ((Integer) listAuxHeights.get(var4)));
        }

        manager.getShaderUniformOrDefault("ProjMat").set(projectionMatrix);
        manager.getShaderUniformOrDefault("InSize").set(framebufferIn.framebufferTextureWidth,
                framebufferIn.framebufferTextureHeight);
        manager.getShaderUniformOrDefault("OutSize").set(var2, var3);
        manager.getShaderUniformOrDefault("Time").set(p_148042_1_);
        final Minecraft var9 = Minecraft.getMinecraft();
        manager.getShaderUniformOrDefault("ScreenSize").set(Minecraft.displayWidth, Minecraft.displayHeight);
        manager.useShader();
        framebufferOut.framebufferClear();
        framebufferOut.bindFramebuffer(false);
        GlStateManager.depthMask(false);
        GlStateManager.colorMask(true, true, true, true);
        final Tessellator var5 = Tessellator.getInstance();
        final WorldRenderer var6 = var5.getWorldRenderer();
        var6.startDrawingQuads();
        var6.func_178991_c(-1);
        var6.addVertex(0.0D, var3, 500.0D);
        var6.addVertex(var2, var3, 500.0D);
        var6.addVertex(var2, 0.0D, 500.0D);
        var6.addVertex(0.0D, 0.0D, 500.0D);
        var5.draw();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        manager.endShader();
        framebufferOut.unbindFramebuffer();
        framebufferIn.unbindFramebufferTexture();
        final Iterator var7 = listAuxFramebuffers.iterator();

        while (var7.hasNext()) {
            final Object var8 = var7.next();

            if (var8 instanceof Framebuffer) {
                ((Framebuffer) var8).unbindFramebufferTexture();
            }
        }
    }

    public ShaderManager getShaderManager() {
        return manager;
    }
}
