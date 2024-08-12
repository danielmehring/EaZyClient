package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import optifine.Config;
import shadersmod.client.Shaders;

public abstract class Render {

    public static final int EaZy = 738;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    protected final RenderManager renderManager;
    protected float shadowSize;

    /**
     * Determines the darkness of the object's shadow. Higher value makes a
     * darker shadow.
     */
    protected float shadowOpaque = 1.0F;

    protected Render(final RenderManager p_i46179_1_) {
        renderManager = p_i46179_1_;
    }

    public boolean func_177071_a(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_,
            final double p_177071_5_, final double p_177071_7_) {
        return p_177071_1_.isInRangeToRender3d(p_177071_3_, p_177071_5_, p_177071_7_) && (p_177071_1_.ignoreFrustumCheck
                || p_177071_2_.isBoundingBoxInFrustum(p_177071_1_.getEntityBoundingBox()));
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker
     * function which does the actual work. In all probabilty, the class Render
     * is generic (Render<T extends Entity) and this method has signature public
     * void doRender(T entity, double d, double d1, double d2, float f, float
     * f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_,
            final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        func_177067_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
    }

    protected void func_177067_a(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_,
            final double p_177067_6_) {
        if (func_177070_b(p_177067_1_)) {
            renderLivingLabel(p_177067_1_, p_177067_1_.getDisplayName().getFormattedText(), p_177067_2_, p_177067_4_,
                    p_177067_6_, 64);
        }
    }

    protected boolean func_177070_b(final Entity p_177070_1_) {
        return p_177070_1_.getAlwaysRenderNameTagForRender() && p_177070_1_.hasCustomName();
    }

    protected void func_177069_a(final Entity p_177069_1_, final double p_177069_2_, final double p_177069_4_,
            final double p_177069_6_, final String p_177069_8_, final float p_177069_9_, final double p_177069_10_) {
        renderLivingLabel(p_177069_1_, p_177069_8_, p_177069_2_, p_177069_4_, p_177069_6_, 64);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called
     * unless you call Render.bindEntityTexture.
     */
    protected abstract ResourceLocation getEntityTexture(Entity var1);

    protected boolean bindEntityTexture(final Entity p_180548_1_) {
        final ResourceLocation var2 = getEntityTexture(p_180548_1_);

        if (var2 == null) {
            return false;
        } else {
            bindTexture(var2);
            return true;
        }
    }

    public void bindTexture(final ResourceLocation p_110776_1_) {
        renderManager.renderEngine.bindTexture(p_110776_1_);
    }

    /**
     * Renders fire on top of the entity. Args: entity, x, y, z, partialTickTime
     */
    private void renderEntityOnFire(final Entity p_76977_1_, final double p_76977_2_, final double p_76977_4_,
            final double p_76977_6_, final float p_76977_8_) {
        GlStateManager.disableLighting();
        final TextureMap var9 = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite var10 = var9.getAtlasSprite("minecraft:blocks/fire_layer_0");
        final TextureAtlasSprite var11 = var9.getAtlasSprite("minecraft:blocks/fire_layer_1");
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) p_76977_2_, (float) p_76977_4_, (float) p_76977_6_);
        final float var12 = p_76977_1_.width * 1.4F;
        GlStateManager.scale(var12, var12, var12);
        final Tessellator var13 = Tessellator.getInstance();
        final WorldRenderer var14 = var13.getWorldRenderer();
        float var15 = 0.5F;
        final float var16 = 0.0F;
        float var17 = p_76977_1_.height / var12;
        float var18 = (float) (p_76977_1_.posY - p_76977_1_.getEntityBoundingBox().minY);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + (int) var17 * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float var19 = 0.0F;
        int var20 = 0;
        var14.startDrawingQuads();

        while (var17 > 0.0F) {
            final TextureAtlasSprite var21 = var20 % 2 == 0 ? var10 : var11;
            bindTexture(TextureMap.locationBlocksTexture);
            float var22 = var21.getMinU();
            final float var23 = var21.getMinV();
            float var24 = var21.getMaxU();
            final float var25 = var21.getMaxV();

            if (var20 / 2 % 2 == 0) {
                final float var26 = var24;
                var24 = var22;
                var22 = var26;
            }

            var14.addVertexWithUV(var15 - var16, 0.0F - var18, var19, var24, var25);
            var14.addVertexWithUV(-var15 - var16, 0.0F - var18, var19, var22, var25);
            var14.addVertexWithUV(-var15 - var16, 1.4F - var18, var19, var22, var23);
            var14.addVertexWithUV(var15 - var16, 1.4F - var18, var19, var24, var23);
            var17 -= 0.45F;
            var18 -= 0.45F;
            var15 *= 0.9F;
            var19 += 0.03F;
            ++var20;
        }

        var13.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }

    /**
     * Renders the entity shadows at the position, shadow alpha and
     * partialTickTime. Args: entity, x, y, z, shadowAlpha, partialTickTime
     */
    private void renderShadow(final Entity p_76975_1_, final double p_76975_2_, final double p_76975_4_,
            final double p_76975_6_, final float p_76975_8_, final float p_76975_9_) {
        if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            renderManager.renderEngine.bindTexture(shadowTextures);
            final World var10 = getWorldFromRenderManager();
            GlStateManager.depthMask(false);
            float var11 = shadowSize;

            if (p_76975_1_ instanceof EntityLiving) {
                final EntityLiving var35 = (EntityLiving) p_76975_1_;
                var11 *= var35.getRenderSizeModifier();

                if (var35.isChild()) {
                    var11 *= 0.5F;
                }
            }

            final double var351 = p_76975_1_.lastTickPosX + (p_76975_1_.posX - p_76975_1_.lastTickPosX) * p_76975_9_;
            final double var14 = p_76975_1_.lastTickPosY + (p_76975_1_.posY - p_76975_1_.lastTickPosY) * p_76975_9_;
            final double var16 = p_76975_1_.lastTickPosZ + (p_76975_1_.posZ - p_76975_1_.lastTickPosZ) * p_76975_9_;
            final int var18 = MathHelper.floor_double(var351 - var11);
            final int var19 = MathHelper.floor_double(var351 + var11);
            final int var20 = MathHelper.floor_double(var14 - var11);
            final int var21 = MathHelper.floor_double(var14);
            final int var22 = MathHelper.floor_double(var16 - var11);
            final int var23 = MathHelper.floor_double(var16 + var11);
            final double var24 = p_76975_2_ - var351;
            final double var26 = p_76975_4_ - var14;
            final double var28 = p_76975_6_ - var16;
            final Tessellator var30 = Tessellator.getInstance();
            final WorldRenderer var31 = var30.getWorldRenderer();
            var31.startDrawingQuads();
            final Iterator var32 = BlockPos
                    .getAllInBox(new BlockPos(var18, var20, var22), new BlockPos(var19, var21, var23)).iterator();

            while (var32.hasNext()) {
                final BlockPos var33 = (BlockPos) var32.next();
                final Block var34 = var10.getBlockState(var33.offsetDown()).getBlock();

                if (var34.getRenderType() != -1 && var10.getLightFromNeighbors(var33) > 3) {
                    func_180549_a(var34, p_76975_2_, p_76975_4_, p_76975_6_, var33, p_76975_8_, var11, var24, var26,
                            var28);
                }
            }

            var30.draw();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }

    /**
     * Returns the render manager's world object
     */
    private World getWorldFromRenderManager() {
        return renderManager.worldObj;
    }

    private void func_180549_a(final Block p_180549_1_, final double p_180549_2_, final double p_180549_4_,
            final double p_180549_6_, final BlockPos p_180549_8_, final float p_180549_9_, final float p_180549_10_,
            final double p_180549_11_, final double p_180549_13_, final double p_180549_15_) {
        if (p_180549_1_.isFullCube()) {
            final Tessellator var17 = Tessellator.getInstance();
            final WorldRenderer var18 = var17.getWorldRenderer();
            double var19 = (p_180549_9_ - (p_180549_4_ - (p_180549_8_.getY() + p_180549_13_)) / 2.0D) * 0.5D
                    * getWorldFromRenderManager().getLightBrightness(p_180549_8_);

            if (var19 >= 0.0D) {
                if (var19 > 1.0D) {
                    var19 = 1.0D;
                }

                var18.func_178960_a(1.0F, 1.0F, 1.0F, (float) var19);
                final double var21 = p_180549_8_.getX() + p_180549_1_.getBlockBoundsMinX() + p_180549_11_;
                final double var23 = p_180549_8_.getX() + p_180549_1_.getBlockBoundsMaxX() + p_180549_11_;
                final double var25 = p_180549_8_.getY() + p_180549_1_.getBlockBoundsMinY() + p_180549_13_ + 0.015625D;
                final double var27 = p_180549_8_.getZ() + p_180549_1_.getBlockBoundsMinZ() + p_180549_15_;
                final double var29 = p_180549_8_.getZ() + p_180549_1_.getBlockBoundsMaxZ() + p_180549_15_;
                final float var31 = (float) ((p_180549_2_ - var21) / 2.0D / p_180549_10_ + 0.5D);
                final float var32 = (float) ((p_180549_2_ - var23) / 2.0D / p_180549_10_ + 0.5D);
                final float var33 = (float) ((p_180549_6_ - var27) / 2.0D / p_180549_10_ + 0.5D);
                final float var34 = (float) ((p_180549_6_ - var29) / 2.0D / p_180549_10_ + 0.5D);
                var18.addVertexWithUV(var21, var25, var27, var31, var33);
                var18.addVertexWithUV(var21, var25, var29, var31, var34);
                var18.addVertexWithUV(var23, var25, var29, var32, var34);
                var18.addVertexWithUV(var23, var25, var27, var32, var33);
            }
        }
    }

    /**
     * Renders a white box with the bounds of the AABB translated by the offset.
     * Args: aabb, x, y, z
     */
    public static void renderOffsetAABB(final AxisAlignedBB p_76978_0_, final double p_76978_1_,
            final double p_76978_3_, final double p_76978_5_) {
        GlStateManager.disableTexture2D();
        final Tessellator var7 = Tessellator.getInstance();
        final WorldRenderer var8 = var7.getWorldRenderer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        var8.startDrawingQuads();
        var8.setTranslation(p_76978_1_, p_76978_3_, p_76978_5_);
        var8.func_178980_d(0.0F, 0.0F, -1.0F);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.func_178980_d(0.0F, 0.0F, 1.0F);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.func_178980_d(0.0F, -1.0F, 0.0F);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.func_178980_d(0.0F, 1.0F, 0.0F);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.func_178980_d(-1.0F, 0.0F, 0.0F);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.func_178980_d(1.0F, 0.0F, 0.0F);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var8.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var8.setTranslation(0.0D, 0.0D, 0.0D);
        var7.draw();
        GlStateManager.enableTexture2D();
    }

    /**
     * Renders the entity's shadow and fire (if its on fire). Args: entity, x,
     * y, z, yaw, partialTickTime
     */
    public void doRenderShadowAndFire(final Entity p_76979_1_, final double p_76979_2_, final double p_76979_4_,
            final double p_76979_6_, final float p_76979_8_, final float p_76979_9_) {
        if (renderManager.options != null) {
            if (renderManager.options.fancyGraphics && shadowSize > 0.0F && !p_76979_1_.isInvisible()
                    && renderManager.func_178627_a()) {
                final double var10 = renderManager.getDistanceToCamera(p_76979_1_.posX, p_76979_1_.posY,
                        p_76979_1_.posZ);
                final float var12 = (float) ((1.0D - var10 / 256.0D) * shadowOpaque);

                if (var12 > 0.0F) {
                    renderShadow(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, var12, p_76979_9_);
                }
            }

            if (p_76979_1_.canRenderOnFire()
                    && (!(p_76979_1_ instanceof EntityPlayer) || !((EntityPlayer) p_76979_1_).isSpectatorMode())) {
                renderEntityOnFire(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_9_);
            }
        }
    }

    /**
     * Returns the font renderer from the set render manager
     */
    public FontRenderer getFontRendererFromRenderManager() {
        return renderManager.getFontRenderer();
    }

    /**
     * Renders an entity's name above its head
     */
    protected void renderLivingLabel(final Entity p_147906_1_, final String p_147906_2_, final double p_147906_3_,
            final double p_147906_5_, final double p_147906_7_, final int p_147906_9_) {
        final double var10 = p_147906_1_.getDistanceSqToEntity(renderManager.livingPlayer);

        if (var10 <= p_147906_9_ * p_147906_9_) {
            final FontRenderer var12 = getFontRendererFromRenderManager();
            final float var13 = 1.6F;
            final float var14 = 0.016666668F * var13;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) p_147906_3_ + 0.0F, (float) p_147906_5_ + p_147906_1_.height + 0.5F,
                    (float) p_147906_7_);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-var14, -var14, var14);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator var15 = Tessellator.getInstance();
            final WorldRenderer var16 = var15.getWorldRenderer();
            byte var17 = 0;

            if (p_147906_2_.equals("deadmau5")) {
                var17 = -10;
            }

            GlStateManager.disableTexture2D();
            var16.startDrawingQuads();
            final int var18 = var12.getStringWidth(p_147906_2_) / 2;
            var16.func_178960_a(0.0F, 0.0F, 0.0F, 0.25F);
            var16.addVertex(-var18 - 1, -1 + var17, 0.0D);
            var16.addVertex(-var18 - 1, 8 + var17, 0.0D);
            var16.addVertex(var18 + 1, 8 + var17, 0.0D);
            var16.addVertex(var18 + 1, -1 + var17, 0.0D);
            var15.draw();
            GlStateManager.enableTexture2D();
            var12.drawString(p_147906_2_, -var12.getStringWidth(p_147906_2_) / 2, var17, 553648127);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            var12.drawString(p_147906_2_, -var12.getStringWidth(p_147906_2_) / 2, var17, -1);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    public RenderManager func_177068_d() {
        return renderManager;
    }
}
