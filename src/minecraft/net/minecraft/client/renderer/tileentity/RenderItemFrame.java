package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

import org.lwjgl.opengl.GL11;

import optifine.Config;
import optifine.Reflector;
import shadersmod.client.ShadersTex;

public class RenderItemFrame extends Render {

public static final int EaZy = 830;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation mapBackgroundTextures = new ResourceLocation(
			"textures/map/map_background.png");
	private final Minecraft field_147917_g = Minecraft.getMinecraft();
	private final ModelResourceLocation field_177072_f = new ModelResourceLocation("item_frame", "normal");
	private final ModelResourceLocation field_177073_g = new ModelResourceLocation("item_frame", "map");
	private final RenderItem field_177074_h;
	// private static final String __OBFID = "http://https://fuckuskid00001002";

	public RenderItemFrame(final RenderManager p_i46166_1_, final RenderItem p_i46166_2_) {
		super(p_i46166_1_);
		field_177074_h = p_i46166_2_;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityItemFrame p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		GlStateManager.pushMatrix();
		final BlockPos var10 = p_76986_1_.func_174857_n();
		final double var11 = var10.getX() - p_76986_1_.posX + p_76986_2_;
		final double var13 = var10.getY() - p_76986_1_.posY + p_76986_4_;
		final double var15 = var10.getZ() - p_76986_1_.posZ + p_76986_6_;
		GlStateManager.translate(var11 + 0.5D, var13 + 0.5D, var15 + 0.5D);
		GlStateManager.rotate(180.0F - p_76986_1_.rotationYaw, 0.0F, 1.0F, 0.0F);
		renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		final BlockRendererDispatcher var17 = field_147917_g.getBlockRendererDispatcher();
		final ModelManager var18 = var17.func_175023_a().func_178126_b();
		IBakedModel var19;

		if (p_76986_1_.getDisplayedItem() != null && p_76986_1_.getDisplayedItem().getItem() == Items.filled_map) {
			var19 = var18.getModel(field_177073_g);
		} else {
			var19 = var18.getModel(field_177072_f);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		var17.func_175019_b().func_178262_a(var19, 1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
		GlStateManager.translate(0.0F, 0.0F, 0.4375F);
		func_82402_b(p_76986_1_);
		GlStateManager.popMatrix();
		func_147914_a(p_76986_1_, p_76986_2_ + p_76986_1_.field_174860_b.getFrontOffsetX() * 0.3F, p_76986_4_ - 0.25D,
				p_76986_6_ + p_76986_1_.field_174860_b.getFrontOffsetZ() * 0.3F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityItemFrame p_110775_1_) {
		return null;
	}

	private void func_82402_b(final EntityItemFrame p_82402_1_) {
		final ItemStack var2 = p_82402_1_.getDisplayedItem();

		if (var2 != null) {
			final EntityItem var3 = new EntityItem(p_82402_1_.worldObj, 0.0D, 0.0D, 0.0D, var2);
			final Item var4 = var3.getEntityItem().getItem();
			var3.getEntityItem().stackSize = 1;
			var3.hoverStart = 0.0F;
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			int var5 = p_82402_1_.getRotation();

			if (var4 instanceof ItemMap) {
				var5 = var5 % 4 * 2;
			}

			GlStateManager.rotate(var5 * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);

			if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor,
					new Object[] { p_82402_1_, this })) {
				if (var4 instanceof ItemMap) {
					renderManager.renderEngine.bindTexture(mapBackgroundTextures);
					GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
					final float var12 = 0.0078125F;
					GlStateManager.scale(var12, var12, var12);
					GlStateManager.translate(-64.0F, -64.0F, 0.0F);
					final MapData var13 = Items.filled_map.getMapData(var3.getEntityItem(), p_82402_1_.worldObj);
					GlStateManager.translate(0.0F, 0.0F, -1.0F);

					if (var13 != null) {
						Minecraft.entityRenderer.getMapItemRenderer().func_148250_a(var13, true);
					}
				} else {
					TextureAtlasSprite var121 = null;

					if (var4 == Items.compass) {
						var121 = field_147917_g.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);

						if (Config.isShaders()) {
							ShadersTex.bindTextureMapForUpdateAndRender(Minecraft.getTextureManager(),
									TextureMap.locationBlocksTexture);
						} else {
							Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
						}

						if (var121 instanceof TextureCompass) {
							final TextureCompass var131 = (TextureCompass) var121;
							final double var8 = var131.currentAngle;
							final double var10 = var131.angleDelta;
							var131.currentAngle = 0.0D;
							var131.angleDelta = 0.0D;
							var131.updateCompass(p_82402_1_.worldObj, p_82402_1_.posX, p_82402_1_.posZ, MathHelper
									.wrapAngleTo180_float(180 + p_82402_1_.field_174860_b.getHorizontalIndex() * 90),
									false, true);
							var131.currentAngle = var8;
							var131.angleDelta = var10;
						} else {
							var121 = null;
						}
					}

					GlStateManager.scale(0.5F, 0.5F, 0.5F);

					if (!field_177074_h.func_175050_a(var3.getEntityItem()) || var4 instanceof ItemSkull) {
						GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
					}

					GlStateManager.pushAttrib();
					RenderHelper.enableStandardItemLighting();
					field_177074_h.func_175043_b(var3.getEntityItem());
					RenderHelper.disableStandardItemLighting();
					GlStateManager.popAttrib();

					if (var121 != null && var121.getFrameCount() > 0) {
						var121.updateAnimation();
					}
				}
			}
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}

		if (Config.isShaders()) {
			ShadersTex.updatingTex = null;
		}
	}

	protected void func_147914_a(final EntityItemFrame p_147914_1_, final double p_147914_2_, final double p_147914_4_,
			final double p_147914_6_) {
		if (Minecraft.isGuiEnabled() && p_147914_1_.getDisplayedItem() != null
				&& p_147914_1_.getDisplayedItem().hasDisplayName() && renderManager.field_147941_i == p_147914_1_) {
			final float var8 = 1.6F;
			final float var9 = 0.016666668F * var8;
			final double var10 = p_147914_1_.getDistanceSqToEntity(renderManager.livingPlayer);
			final float var12 = p_147914_1_.isSneaking() ? 32.0F : 64.0F;

			if (var10 < var12 * var12) {
				final String var13 = p_147914_1_.getDisplayedItem().getDisplayName();

				if (p_147914_1_.isSneaking()) {
					final FontRenderer var14 = getFontRendererFromRenderManager();
					GlStateManager.pushMatrix();
					GlStateManager.translate((float) p_147914_2_ + 0.0F,
							(float) p_147914_4_ + p_147914_1_.height + 0.5F, (float) p_147914_6_);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					GlStateManager.scale(-var9, -var9, var9);
					GlStateManager.disableLighting();
					GlStateManager.translate(0.0F, 0.25F / var9, 0.0F);
					GlStateManager.depthMask(false);
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(770, 771);
					final Tessellator var15 = Tessellator.getInstance();
					final WorldRenderer var16 = var15.getWorldRenderer();
					GlStateManager.disableTexture2D();
					var16.startDrawingQuads();
					final int var17 = var14.getStringWidth(var13) / 2;
					var16.func_178960_a(0.0F, 0.0F, 0.0F, 0.25F);
					var16.addVertex(-var17 - 1, -1.0D, 0.0D);
					var16.addVertex(-var17 - 1, 8.0D, 0.0D);
					var16.addVertex(var17 + 1, 8.0D, 0.0D);
					var16.addVertex(var17 + 1, -1.0D, 0.0D);
					var15.draw();
					GlStateManager.enableTexture2D();
					GlStateManager.depthMask(true);
					var14.drawString(var13, -var14.getStringWidth(var13) / 2, 0, 553648127);
					GlStateManager.enableLighting();
					GlStateManager.disableBlend();
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.popMatrix();
				} else {
					renderLivingLabel(p_147914_1_, var13, p_147914_2_, p_147914_4_, p_147914_6_, 64);
				}
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityItemFrame) p_110775_1_);
	}

	@Override
	protected void func_177067_a(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_,
			final double p_177067_6_) {
		func_147914_a((EntityItemFrame) p_177067_1_, p_177067_2_, p_177067_4_, p_177067_6_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		this.doRender((EntityItemFrame) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
