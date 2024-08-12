package shadersmod.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumWorldBlockLayer;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import optifine.Reflector;

public class ShadersRender {

public static final int EaZy = 2018;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static void setFrustrumPosition(final Frustrum frustrum, final double x, final double y, final double z) {
		frustrum.setPosition(x, y, z);
	}

	public static void setupTerrain(final RenderGlobal renderGlobal, final Entity viewEntity, final double partialTicks,
			final ICamera camera, final int frameCount, final boolean playerSpectator) {
		renderGlobal.func_174970_a(viewEntity, partialTicks, camera, frameCount, playerSpectator);
	}

	public static void updateChunks(final RenderGlobal renderGlobal, final long finishTimeNano) {
		renderGlobal.func_174967_a(finishTimeNano);
	}

	public static void beginTerrainSolid() {
		if (Shaders.isRenderingWorld) {
			Shaders.fogEnabled = true;
			Shaders.useProgram(7);
		}
	}

	public static void beginTerrainCutoutMipped() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(7);
		}
	}

	public static void beginTerrainCutout() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(7);
		}
	}

	public static void endTerrain() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(3);
		}
	}

	public static void beginTranslucent() {
		if (Shaders.isRenderingWorld) {
			if (Shaders.usedDepthBuffers >= 2) {
				GlStateManager.setActiveTexture(33995);
				Shaders.checkGLError("pre copy depth");
				GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
				Shaders.checkGLError("copy depth");
				GlStateManager.setActiveTexture(33984);
			}

			Shaders.useProgram(12);
		}
	}

	public static void endTranslucent() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(3);
		}
	}

	public static void renderHand0(final EntityRenderer er, final float par1, final int par2) {
		if (!Shaders.isShadowPass) {
			final Item item = Shaders.itemToRender != null ? Shaders.itemToRender.getItem() : null;
			final Block block = item instanceof ItemBlock ? ((ItemBlock) item).getBlock() : null;

			if (!(item instanceof ItemBlock) || !(block instanceof Block)
					|| block.getBlockLayer() == EnumWorldBlockLayer.SOLID) {
				Shaders.readCenterDepth();
				Shaders.beginHand();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				er.renderHand(par1, par2);
				Shaders.endHand();
				Shaders.isHandRendered = true;
			}
		}
	}

	public static void renderHand1(final EntityRenderer er, final float par1, final int par2) {
		if (!Shaders.isShadowPass && !Shaders.isHandRendered) {
			Shaders.readCenterDepth();
			GlStateManager.enableBlend();
			Shaders.beginHand();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			er.renderHand(par1, par2);
			Shaders.endHand();
			Shaders.isHandRendered = true;
		}
	}

	public static void renderItemFP(final ItemRenderer itemRenderer, final float par1) {
		GlStateManager.depthMask(true);
		GlStateManager.depthFunc(515);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		itemRenderer.renderItemInFirstPerson(par1);
	}

	public static void renderFPOverlay(final EntityRenderer er, final float par1, final int par2) {
		if (!Shaders.isShadowPass) {
			Shaders.beginFPOverlay();
			er.renderHand(par1, par2);
			Shaders.endFPOverlay();
		}
	}

	public static void beginBlockDamage() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(11);

			if (Shaders.programsID[11] == Shaders.programsID[7]) {
				Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
				GlStateManager.depthMask(false);
			}
		}
	}

	public static void endBlockDamage() {
		if (Shaders.isRenderingWorld) {
			GlStateManager.depthMask(true);
			Shaders.useProgram(3);
		}
	}

	public static void renderShadowMap(final EntityRenderer entityRenderer, final int pass, final float partialTicks,
			final long finishTimeNano) {
		if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
			final Minecraft mc = Minecraft.getMinecraft();
			Minecraft.mcProfiler.endStartSection("shadow pass");
			final RenderGlobal renderGlobal = mc.renderGlobal;
			Shaders.isShadowPass = true;
			Shaders.shadowPassCounter = Shaders.shadowPassInterval;
			Shaders.preShadowPassThirdPersonView = Minecraft.gameSettings.thirdPersonView;
			Minecraft.gameSettings.thirdPersonView = 1;
			Shaders.checkGLError("pre shadow");
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPushMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();
			Minecraft.mcProfiler.endStartSection("shadow clear");
			EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
			Shaders.checkGLError("shadow bind sfb");
			Shaders.useProgram(30);
			Minecraft.mcProfiler.endStartSection("shadow camera");
			entityRenderer.setupCameraTransform(partialTicks, 2);
			Shaders.setCameraShadow(partialTicks);
			ActiveRenderInfo.updateRenderInfo(Minecraft.thePlayer, Minecraft.gameSettings.thirdPersonView == 2);
			Shaders.checkGLError("shadow camera");
			GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
			Shaders.checkGLError("shadow drawbuffers");
			GL11.glReadBuffer(0);
			Shaders.checkGLError("shadow readbuffer");
			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);

			if (Shaders.usedShadowColorBuffers != 0) {
				EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
			}

			Shaders.checkFramebufferStatus("shadow fb");
			GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glClear(Shaders.usedShadowColorBuffers != 0 ? GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT
					: GL11.GL_DEPTH_BUFFER_BIT);
			Shaders.checkGLError("shadow clear");
			Minecraft.mcProfiler.endStartSection("shadow frustum");
			final ClippingHelper clippingHelper = ClippingHelperShadow.getInstance();
			Minecraft.mcProfiler.endStartSection("shadow culling");
			final Frustrum frustum = new Frustrum(clippingHelper);
			final Entity viewEntity = mc.func_175606_aa();
			final double viewPosX = viewEntity.lastTickPosX
					+ (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
			final double viewPosY = viewEntity.lastTickPosY
					+ (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
			final double viewPosZ = viewEntity.lastTickPosZ
					+ (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
			frustum.setPosition(viewPosX, viewPosY, viewPosZ);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.enableDepth();
			GlStateManager.depthFunc(515);
			GlStateManager.depthMask(true);
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.disableCull();
			Minecraft.mcProfiler.endStartSection("shadow prepareterrain");
			Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			Minecraft.mcProfiler.endStartSection("shadow setupterrain");
			final int var17 = entityRenderer.field_175084_ae;
			entityRenderer.field_175084_ae = var17 + 1;
			renderGlobal.func_174970_a(viewEntity, partialTicks, frustum, var17, Minecraft.thePlayer.isSpectatorMode());
			Minecraft.mcProfiler.endStartSection("shadow updatechunks");
			Minecraft.mcProfiler.endStartSection("shadow terrain");
			GlStateManager.matrixMode(5888);
			GlStateManager.pushMatrix();
			GlStateManager.disableAlpha();
			renderGlobal.func_174977_a(EnumWorldBlockLayer.SOLID, partialTicks, 2, viewEntity);
			Shaders.checkGLError("shadow terrain solid");
			GlStateManager.enableAlpha();
			renderGlobal.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, 2, viewEntity);
			Shaders.checkGLError("shadow terrain cutoutmipped");
			Minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
			renderGlobal.func_174977_a(EnumWorldBlockLayer.CUTOUT, partialTicks, 2, viewEntity);
			Shaders.checkGLError("shadow terrain cutout");
			Minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
			GlStateManager.shadeModel(7424);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			GlStateManager.matrixMode(5888);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			Minecraft.mcProfiler.endStartSection("shadow entities");

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { 0});
			}

			renderGlobal.renderEntities(viewEntity, frustum, partialTicks);
			Shaders.checkGLError("shadow entities");
			GlStateManager.matrixMode(5888);
			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
			GlStateManager.disableBlend();
			GlStateManager.enableCull();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

			if (Shaders.usedShadowDepthBuffers >= 2) {
				GlStateManager.setActiveTexture(33989);
				Shaders.checkGLError("pre copy shadow depth");
				GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.shadowMapWidth,
						Shaders.shadowMapHeight);
				Shaders.checkGLError("copy shadow depth");
				GlStateManager.setActiveTexture(33984);
			}

			GlStateManager.disableBlend();
			GlStateManager.depthMask(true);
			Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			Shaders.checkGLError("shadow pre-translucent");
			GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
			Shaders.checkGLError("shadow drawbuffers pre-translucent");
			Shaders.checkFramebufferStatus("shadow pre-translucent");
			Minecraft.mcProfiler.endStartSection("shadow translucent");
			renderGlobal.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, 2, viewEntity);
			Shaders.checkGLError("shadow translucent");

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				RenderHelper.enableStandardItemLighting();
				Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { 1});
				renderGlobal.renderEntities(viewEntity, frustum, partialTicks);
				Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { -1});
				RenderHelper.disableStandardItemLighting();
				Shaders.checkGLError("shadow entities 1");
			}

			GlStateManager.shadeModel(7424);
			GlStateManager.depthMask(true);
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GL11.glFlush();
			Shaders.checkGLError("shadow flush");
			Shaders.isShadowPass = false;
			Minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
			Minecraft.mcProfiler.endStartSection("shadow postprocess");

			if (Shaders.hasGlGenMipmap) {
				if (Shaders.usedShadowDepthBuffers >= 1) {
					if (Shaders.shadowMipmapEnabled[0]) {
						GlStateManager.setActiveTexture(33988);
						GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(0));
						GL30.glGenerateMipmap(3553);
						GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
								Shaders.shadowFilterNearest[0] ? GL11.GL_NEAREST_MIPMAP_NEAREST
										: GL11.GL_LINEAR_MIPMAP_LINEAR);
					}

					if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
						GlStateManager.setActiveTexture(33989);
						GlStateManager.func_179144_i(Shaders.sfbDepthTextures.get(1));
						GL30.glGenerateMipmap(3553);
						GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
								Shaders.shadowFilterNearest[1] ? GL11.GL_NEAREST_MIPMAP_NEAREST
										: GL11.GL_LINEAR_MIPMAP_LINEAR);
					}

					GlStateManager.setActiveTexture(33984);
				}

				if (Shaders.usedShadowColorBuffers >= 1) {
					if (Shaders.shadowColorMipmapEnabled[0]) {
						GlStateManager.setActiveTexture(33997);
						GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(0));
						GL30.glGenerateMipmap(3553);
						GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
								Shaders.shadowColorFilterNearest[0] ? GL11.GL_NEAREST_MIPMAP_NEAREST
										: GL11.GL_LINEAR_MIPMAP_LINEAR);
					}

					if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
						GlStateManager.setActiveTexture(33998);
						GlStateManager.func_179144_i(Shaders.sfbColorTextures.get(1));
						GL30.glGenerateMipmap(3553);
						GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
								Shaders.shadowColorFilterNearest[1] ? GL11.GL_NEAREST_MIPMAP_NEAREST
										: GL11.GL_LINEAR_MIPMAP_LINEAR);
					}

					GlStateManager.setActiveTexture(33984);
				}
			}

			Shaders.checkGLError("shadow postprocess");
			EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
			GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
			Shaders.activeDrawBuffers = null;
			Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			Shaders.useProgram(7);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Shaders.checkGLError("shadow end");
		}
	}

	public static void preRenderChunkLayer() {
		if (OpenGlHelper.func_176075_f()) {
			GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
			GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
			GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
			GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
		}
	}

	public static void postRenderChunkLayer() {
		if (OpenGlHelper.func_176075_f()) {
			GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
			GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
			GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
			GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
		}
	}

	public static void setupArrayPointersVbo() {
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 56, 0L);
		GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 56, 12L);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 56, 16L);
		OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glTexCoordPointer(2, GL11.GL_SHORT, 56, 24L);
		OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glNormalPointer(GL11.GL_BYTE, 56, 28L);
		GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, GL11.GL_FLOAT, false, 56, 32L);
		GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, GL11.GL_SHORT, false, 56, 40L);
		GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, GL11.GL_SHORT, false, 56, 48L);
	}

	public static void beaconBeamBegin() {
		Shaders.useProgram(14);
	}

	public static void beaconBeamStartQuad1() {}

	public static void beaconBeamStartQuad2() {}

	public static void beaconBeamDraw1() {}

	public static void beaconBeamDraw2() {
		GlStateManager.disableBlend();
	}

	public static void layerArmorBaseDrawEnchantedGlintBegin() {
		Shaders.useProgram(17);
	}

	public static void layerArmorBaseDrawEnchantedGlintEnd() {
		if (Shaders.isRenderingWorld) {
			Shaders.useProgram(16);
		} else {
			Shaders.useProgram(0);
		}
	}
}
