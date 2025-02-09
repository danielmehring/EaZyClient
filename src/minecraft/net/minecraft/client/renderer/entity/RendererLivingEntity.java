package net.minecraft.client.renderer.entity;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.Chams;
import me.EaZy.client.modules.ESP;
import me.EaZy.client.modules.NameTags;
import me.EaZy.client.modules.TrueSight;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.OutlineUtilsOld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.Reflector;
import shadersmod.client.Shaders;

public abstract class RendererLivingEntity extends Render {

	public static final int EaZy = 753;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
	protected ModelBase mainModel;
	protected FloatBuffer field_177095_g = GLAllocation.createDirectFloatBuffer(4);
	protected List field_177097_h = Lists.newArrayList();
	protected boolean field_177098_i = false;
	public static float NAME_TAG_RANGE = 64.0F;
	public static float NAME_TAG_RANGE_SNEAK = 32.0F;

	public RendererLivingEntity(final RenderManager p_i46156_1_, final ModelBase p_i46156_2_, final float p_i46156_3_) {
		super(p_i46156_1_);
		mainModel = p_i46156_2_;
		shadowSize = p_i46156_3_;
	}

	public boolean addLayer(final LayerRenderer p_177094_1_) {
		return field_177097_h.add(p_177094_1_);
	}

	protected boolean func_177089_b(final LayerRenderer p_177089_1_) {
		return field_177097_h.remove(p_177089_1_);
	}

	public ModelBase getMainModel() {
		return mainModel;
	}

	/**
	 * Returns a rotation angle that is inbetween two other rotation angles.
	 * par1 and par2 are the angles between which to interpolate, par3 is
	 * probably a float between 0.0 and 1.0 that tells us where "between" the
	 * two angles we are. Example: par1 = 30, par2 = 50, par3 = 0.5, then return
	 * = 40
	 */
	protected float interpolateRotation(final float p_77034_1_, final float p_77034_2_, final float p_77034_3_) {
		float var4;

		for (var4 = p_77034_2_ - p_77034_1_; var4 < -180.0F; var4 += 360.0F) {}

		while (var4 >= 180.0F) {
			var4 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * var4;
	}

	public void func_82422_c() {}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		if (!Reflector.RenderLivingEvent_Pre_Constructor.exists()
				|| !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor,
						new Object[] { p_76986_1_, this, p_76986_2_, p_76986_4_, p_76986_6_ })) {
			if (Chams.mod.isToggled() && p_76986_1_ instanceof EntityPlayer) {
				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				GL11.glPolygonOffset(1.0f, -2000000.0f);
			}
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			mainModel.swingProgress = getSwingProgress(p_76986_1_, p_76986_9_);
			mainModel.isRiding = p_76986_1_.isRiding();

			if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
				mainModel.isRiding = p_76986_1_.isRiding() && p_76986_1_.ridingEntity != null && Reflector
						.callBoolean(p_76986_1_.ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]);
			}

			mainModel.isChild = p_76986_1_.isChild();

			try {
				float var19 = interpolateRotation(p_76986_1_.prevRenderYawOffset, p_76986_1_.renderYawOffset,
						p_76986_9_);
				float var11 = interpolateRotation(p_76986_1_.prevRotationYawHead, p_76986_1_.rotationYawHead,
						p_76986_9_);

				float var201 = p_76986_1_.prevRotationPitch
						+ (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;

				if (Configs.f5PositionXD) {
					if (p_76986_1_.isEntityEqual(Minecraft.thePlayer)) { // ==
						if (p_76986_9_ != 1) {
							final float xd1 = interpolateRotation(Minecraft.thePlayer.sendQueue.netManager.prevYaw,
									Minecraft.thePlayer.sendQueue.netManager.yaw, p_76986_9_);
							final float xd2 = Minecraft.thePlayer.sendQueue.netManager.prevPitch
									+ (Minecraft.thePlayer.sendQueue.netManager.pitch
											- Minecraft.thePlayer.sendQueue.netManager.prevPitch) * p_76986_9_;
							var11 = xd1;
							var201 = xd2;
						}
					}
				}

				float var12 = var11 - var19;
				float var14;

				if (mainModel.isRiding && p_76986_1_.ridingEntity instanceof EntityLivingBase) {
					final EntityLivingBase var20 = (EntityLivingBase) p_76986_1_.ridingEntity;
					var19 = interpolateRotation(var20.prevRenderYawOffset, var20.renderYawOffset, p_76986_9_);
					var12 = var11 - var19;
					var14 = MathHelper.wrapAngleTo180_float(var12);

					if (var14 < -85.0F) {
						var14 = -85.0F;
					}

					if (var14 >= 85.0F) {
						var14 = 85.0F;
					}

					var19 = var11 - var14;

					if (var14 * var14 > 2500.0F) {
						var19 += var14 * 0.2F;
					}
				}

				renderLivingAt(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
				var14 = handleRotationFloat(p_76986_1_, p_76986_9_);
				rotateCorpse(p_76986_1_, var14, var19, p_76986_9_);
				GlStateManager.enableRescaleNormal();
				GlStateManager.scale(-1.0F, -1.0F, 1.0F);
				preRenderCallback(p_76986_1_, p_76986_9_);
				GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
				float var16 = p_76986_1_.prevLimbSwingAmount
						+ (p_76986_1_.limbSwingAmount - p_76986_1_.prevLimbSwingAmount) * p_76986_9_;
				float var17 = p_76986_1_.limbSwing - p_76986_1_.limbSwingAmount * (1.0F - p_76986_9_);

				if (p_76986_1_.isChild()) {
					var17 *= 3.0F;
				}

				if (var16 > 1.0F) {
					var16 = 1.0F;
				}

				GlStateManager.enableAlpha();
				mainModel.setLivingAnimations(p_76986_1_, var17, var16, p_76986_9_);
				mainModel.setRotationAngles(var17, var16, var14, var12, var201, 0.0625F, p_76986_1_);
				boolean var18;

				if (field_177098_i) {
					var18 = func_177088_c(p_76986_1_);
					renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);

					if (var18) {
						func_180565_e();
					}
				} else {
					var18 = func_177090_c(p_76986_1_, p_76986_9_);
					renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);

					// TODO: OutlineESP
					if (ESP.mod.isToggled()
							&& Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString().equals("Outline")) {
						GlStateManager.depthMask(true);
						if (!(p_76986_1_ instanceof EntityPlayer) || !((EntityPlayer) p_76986_1_).isSpectatorMode()) {
							func_177093_a(p_76986_1_, var17, var16, p_76986_9_, var14, var12, var201, 0.0625F);
						}

						if (p_76986_1_ instanceof EntityPlayer && !Friends.contains((EntityPlayer) p_76986_1_)
								&& p_76986_1_ != Minecraft.thePlayer) {
							renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
							OutlineUtilsOld.renderOne();
							renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
							OutlineUtilsOld.renderTwo();
							renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
							OutlineUtilsOld.renderThree();
							OutlineUtilsOld.renderFour(new Color(1.0f, 0.0f, 0.0f));
							renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
							OutlineUtilsOld.renderFive();
						} else {
							if (p_76986_1_ instanceof EntityPlayer && Friends.contains((EntityPlayer) p_76986_1_)
									&& p_76986_1_ != Minecraft.thePlayer) {
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderOne();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderTwo();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderThree();
								OutlineUtilsOld.renderFour(new Color(0.1f, 0.1f, 1.0f));
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderFive();
							} else if (p_76986_1_ instanceof EntityMob) {

								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderOne();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderTwo();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderThree();
								OutlineUtilsOld.renderFour(new Color(1.0f, 1.0f, 0.5f));
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderFive();

							} else if (p_76986_1_ instanceof EntityAnimal) {

								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderOne();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderTwo();
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderThree();
								OutlineUtilsOld.renderFour(new Color(0.0f, 1.0f, 0.0f));
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
								OutlineUtilsOld.renderFive();

							} else {
								renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
							}
						}
					} else {
						renderModel(p_76986_1_, var17, var16, var14, var12, var201, 0.0625F);
					}

					if (var18) {
						func_177091_f();
					}

					GlStateManager.depthMask(true);

					if (!(p_76986_1_ instanceof EntityPlayer) || !((EntityPlayer) p_76986_1_).isSpectatorMode()) {
						func_177093_a(p_76986_1_, var17, var16, p_76986_9_, var14, var12, var201, 0.0625F);
					}
				}

				GlStateManager.disableRescaleNormal();
			} catch (final Exception var191) {
				logger.error("Couldn\'t render entity", var191);
			}

			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.enableTexture2D();
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.enableCull();
			GlStateManager.popMatrix();

			if (!field_177098_i) {
				super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
			}

			if (!Reflector.RenderLivingEvent_Post_Constructor.exists()
					|| !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor,
							new Object[] { p_76986_1_, this, p_76986_2_, p_76986_4_, p_76986_6_ })) {}
			if (Chams.mod.isToggled() && p_76986_1_ instanceof EntityPlayer) {
				GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
			}
		}
	}

	protected boolean func_177088_c(final EntityLivingBase p_177088_1_) {
		int var2 = 16777215;

		if (p_177088_1_ instanceof EntityPlayer) {
			final ScorePlayerTeam var6 = (ScorePlayerTeam) p_177088_1_.getTeam();

			if (var6 != null) {
				final String var7 = FontRenderer.getFormatFromString(var6.getColorPrefix());

				if (var7.length() >= 2) {
					var2 = getFontRendererFromRenderManager().func_175064_b(var7.charAt(1));
				}
			}
		}

		final float var61 = (var2 >> 16 & 255) / 255.0F;
		final float var71 = (var2 >> 8 & 255) / 255.0F;
		final float var5 = (var2 & 255) / 255.0F;
		GlStateManager.disableLighting();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.color(var61, var71, var5, 1.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		return true;
	}

	protected void func_180565_e() {
		GlStateManager.enableLighting();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	protected void renderModel(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_,
			final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
		final boolean var8 = !p_77036_1_.isInvisible();

		final boolean var9 = !var8
				&& (!p_77036_1_.isInvisibleToPlayer(Minecraft.thePlayer) || TrueSight.mod.isToggled());

		if (var8 || var9) {
			if (!bindEntityTexture(p_77036_1_)) {
				return;
			}

			if (var9) {
				GlStateManager.pushMatrix();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
				GlStateManager.depthMask(false);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(770, 771);
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
			}

			// TODO: ESP
			if (ESP.mod.isToggled() && Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString().equals("Wire")) {
				GL11.glPushMatrix();
				GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				if (p_77036_1_ instanceof EntityPlayer && !Friends.contains((EntityPlayer) p_77036_1_)
						&& p_77036_1_ != Minecraft.thePlayer) {
					GL11.glColor3f(1.0f, 0.1f, 0.1f);
				} else {

					if (p_77036_1_ instanceof EntityPlayer && Friends.contains((EntityPlayer) p_77036_1_)
							&& p_77036_1_ != Minecraft.thePlayer) {
						GL11.glColor3f(0.1f, 0.1f, 1.0f);
					} else if (p_77036_1_ instanceof EntityMob) {
						GL11.glColor3f(0.5f, 0.5f, 0.5f);
					} else if (p_77036_1_ instanceof EntityAnimal) {
						GL11.glColor3f(0.0f, 1.0f, 0.0f);
					} else if (!(p_77036_1_ instanceof EntityPlayer && p_77036_1_ instanceof EntityMob
							&& p_77036_1_ instanceof EntityAnimal)) {
						GL11.glColor3f(1.0f, 1.0f, 1.0f);
					}
				}

				GL11.glLineWidth(p_77036_1_.getDistanceToEntity(Minecraft.thePlayer) < 10.0f ? 2.0f : 0.5f);
				mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
			mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

			if (var9) {
				GlStateManager.disableBlend();
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
				GlStateManager.popMatrix();
				GlStateManager.depthMask(true);
			}
		}
	}

	protected boolean func_177090_c(final EntityLivingBase p_177090_1_, final float p_177090_2_) {
		return func_177092_a(p_177090_1_, p_177090_2_, true);
	}

	protected boolean func_177092_a(final EntityLivingBase p_177092_1_, final float p_177092_2_,
			final boolean p_177092_3_) {
		final float var4 = p_177092_1_.getBrightness(p_177092_2_);
		final int var5 = getColorMultiplier(p_177092_1_, var4, p_177092_2_);
		final boolean var6 = (var5 >> 24 & 255) > 0;
		final boolean var7 = p_177092_1_.hurtTime > 0 || p_177092_1_.deathTime > 0;

		if (!var6 && !var7) {
			return false;
		} else if (!var6 && !p_177092_3_) {
			return false;
		} else {
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.enableTexture2D();
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, GL11.GL_MODULATE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_REPLACE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.enableTexture2D();
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176076_D, GL11.GL_SRC_ALPHA);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_REPLACE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
			field_177095_g.position(0);

			if (var7) {
				field_177095_g.put(1.0F);
				field_177095_g.put(0.0F);
				field_177095_g.put(0.0F);
				field_177095_g.put(0.3F);

				if (Config.isShaders()) {
					Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
				}
			} else {
				final float var8 = (var5 >> 24 & 255) / 255.0F;
				final float var9 = (var5 >> 16 & 255) / 255.0F;
				final float var10 = (var5 >> 8 & 255) / 255.0F;
				final float var11 = (var5 & 255) / 255.0F;
				field_177095_g.put(var9);
				field_177095_g.put(var10);
				field_177095_g.put(var11);
				field_177095_g.put(1.0F - var8);

				if (Config.isShaders()) {
					Shaders.setEntityColor(var9, var10, var11, 1.0F - var8);
				}
			}

			field_177095_g.flip();
			GL11.glTexEnv(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_COLOR, field_177095_g);
			GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
			GlStateManager.enableTexture2D();
			GlStateManager.func_179144_i(field_177096_e.getGlTextureId());
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, GL11.GL_MODULATE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.lightmapTexUnit);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_REPLACE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			return true;
		}
	}

	protected void func_177091_f() {
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableTexture2D();
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176086_J, GL11.GL_SRC_ALPHA);
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, GL11.GL_TEXTURE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, GL11.GL_TEXTURE);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
		GlStateManager.disableTexture2D();
		GlStateManager.func_179144_i(0);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.field_176095_s);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176099_x, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176081_B, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176082_C, GL11.GL_SRC_COLOR);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176098_y, GL11.GL_TEXTURE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176077_E, GL11.GL_MODULATE);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176085_I, GL11.GL_SRC_ALPHA);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.field_176078_F, GL11.GL_TEXTURE);
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(final EntityLivingBase entity, final double x, final double y, final double z) {
		GlStateManager.translate((float) x, (float) y, (float) z);
	}

	protected void rotateCorpse(final EntityLivingBase entity, final float p_77043_2_, final float p_77043_3_,
			final float p_77043_4_) {
		GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);

		if (entity.deathTime > 0) {
			float var6 = (entity.deathTime + p_77043_4_ - 1.0F) / 20.0F * 1.6F;
			var6 = MathHelper.sqrt_float(var6);

			if (var6 > 1.0F) {
				var6 = 1.0F;
			}

			GlStateManager.rotate(var6 * getDeathMaxRotation(entity), 0.0F, 0.0F, 1.0F);
		} else {
			final String var61 = EnumChatFormatting.getTextWithoutFormattingCodes(entity.getName());

			if (var61 != null && (var61.equals("Dinnerbone") || var61.equals("Grumm"))
					&& (!(entity instanceof EntityPlayer)
							|| ((EntityPlayer) entity).func_175148_a(EnumPlayerModelParts.CAPE))) {
				GlStateManager.translate(0.0F, entity.height + 0.1F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}
		}
	}

	/**
	 * Returns where in the swing animation the living entity is (from 0 to 1).
	 * Args : entity, partialTickTime
	 */
	protected float getSwingProgress(final EntityLivingBase entity, final float progress) {
		return entity.getSwingProgress(progress);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
		return p_77044_1_.ticksExisted + p_77044_2_;
	}

	protected void func_177093_a(final EntityLivingBase p_177093_1_, final float p_177093_2_, final float p_177093_3_,
			final float p_177093_4_, final float p_177093_5_, final float p_177093_6_, final float p_177093_7_,
			final float p_177093_8_) {
		final Iterator var9 = field_177097_h.iterator();

		while (var9.hasNext()) {
			final LayerRenderer var10 = (LayerRenderer) var9.next();
			final boolean var11 = func_177092_a(p_177093_1_, p_177093_4_, var10.shouldCombineTextures());
			var10.doRenderLayer(p_177093_1_, p_177093_2_, p_177093_3_, p_177093_4_, p_177093_5_, p_177093_6_,
					p_177093_7_, p_177093_8_);

			if (var11) {
				func_177091_f();
			}
		}
	}

	protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
		return 90.0F;
	}

	/**
	 * Returns an ARGB int color back. Args: entityLiving, lightBrightness,
	 * partialTickTime
	 */
	protected int getColorMultiplier(final EntityLivingBase p_77030_1_, final float p_77030_2_,
			final float p_77030_3_) {
		return 0;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {}

	/**
	 * Passes the specialRender and renders it
	 */
	public void passSpecialRender(final EntityLivingBase entity, final double p_77033_2_, final double p_77033_4_,
			final double p_77033_6_) {
		if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists()
				|| !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor,
						new Object[] { entity, this, p_77033_2_, p_77033_4_, p_77033_6_ })) {
			if (canRenderName(entity)) {
				// TODO: MitteESP, NameTags
				if (ESP.mod.isToggled() && (Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString()
						.equalsIgnoreCase("Mitte")
						|| Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString().equalsIgnoreCase("2D")
						|| Client.setmgr.getSettingByName(ESP.mod, "Mode").getValString().equalsIgnoreCase("Box"))) {
					ESP.renderMittesTritte(entity, p_77033_2_, p_77033_4_, p_77033_6_);
				}
				if (NameTags.mod.isToggled()) {
					NameTags.renderNametag(entity, p_77033_2_, p_77033_4_, p_77033_6_);
				} else {
					final double var8 = entity.getDistanceSqToEntity(renderManager.livingPlayer);
					final float var10 = entity.isSneaking() ? 32.0F : 64.0F;

					if (var8 < var10 * var10) {
						final String var11 = entity.getDisplayName().getFormattedText();
						GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

						if (entity.isSneaking()) {
							final FontRenderer var13 = getFontRendererFromRenderManager();
							GlStateManager.pushMatrix();
							GlStateManager.translate((float) p_77033_2_, (float) p_77033_4_ + entity.height + 0.5F
									- (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) p_77033_6_);
							GL11.glNormal3f(0.0F, 1.0F, 0.0F);
							GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
							GlStateManager.rotate(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
							GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
							GlStateManager.translate(0.0F, 9.374999F, 0.0F);
							GlStateManager.disableLighting();
							GlStateManager.depthMask(false);
							GlStateManager.enableBlend();
							GlStateManager.disableTexture2D();
							GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
							final Tessellator var14 = Tessellator.getInstance();
							final WorldRenderer var15 = var14.getWorldRenderer();
							var15.startDrawingQuads();
							final int var16 = var13.getStringWidth(var11) / 2;
							var15.func_178960_a(0.0F, 0.0F, 0.0F, 0.25F);
							var15.addVertex(-var16 - 1, -1.0D, 0.0D);
							var15.addVertex(-var16 - 1, 8.0D, 0.0D);
							var15.addVertex(var16 + 1, 8.0D, 0.0D);
							var15.addVertex(var16 + 1, -1.0D, 0.0D);
							var14.draw();
							GlStateManager.enableTexture2D();
							GlStateManager.depthMask(true);
							var13.drawString(var11, -var13.getStringWidth(var11) / 2, 0, 553648127);
							GlStateManager.enableLighting();
							GlStateManager.disableBlend();
							GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
							GlStateManager.popMatrix();
						} else {
							func_177069_a(entity, p_77033_2_,
									p_77033_4_ - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0D),
									p_77033_6_, var11, 0.02666667F, var8);
						}
					}
				}
			}

			if (!Reflector.RenderLivingEvent_Specials_Post_Constructor.exists()
					|| !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor,
							new Object[] { entity, this, p_77033_2_, p_77033_4_, p_77033_6_ })) {}
		}
	}

	/**
	 * Test if the entity name must be rendered
	 */
	protected boolean canRenderName(final EntityLivingBase targetEntity) {
		if (targetEntity instanceof EntityPlayer && targetEntity != Minecraft.thePlayer) {
			final Team var3 = targetEntity.getTeam();
			final Team var4 = Minecraft.thePlayer.getTeam();

			if (var3 != null) {
				final Team.EnumVisible var5 = var3.func_178770_i();

				switch (RendererLivingEntity.SwitchEnumVisible.field_178679_a[var5.ordinal()]) {
				case 1:
					return true;

				case 2:
					return false;

				case 3:
					return var4 == null || var3.isSameTeam(var4);

				case 4:
					return var4 == null || !var3.isSameTeam(var4);

				default:
					return true;
				}
			}
		}

		return Minecraft.isGuiEnabled() && targetEntity != renderManager.livingPlayer
				&& !targetEntity.isInvisibleToPlayer(Minecraft.thePlayer) && targetEntity.riddenByEntity == null;
	}

	public void func_177086_a(final boolean p_177086_1_) {
		field_177098_i = p_177086_1_;
	}

	@Override
	protected boolean func_177070_b(final Entity p_177070_1_) {
		return canRenderName((EntityLivingBase) p_177070_1_);
	}

	@Override
	public void func_177067_a(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_,
			final double p_177067_6_) {
		passSpecialRender((EntityLivingBase) p_177067_1_, p_177067_2_, p_177067_4_, p_177067_6_);
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
		this.doRender((EntityLivingBase) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	static {
		field_177096_e.updateDynamicTexture();
	}

	static final class SwitchEnumVisible {

		static final int[] field_178679_a = new int[Team.EnumVisible.values().length];

		static {
			try {
				field_178679_a[Team.EnumVisible.ALWAYS.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {}

			try {
				field_178679_a[Team.EnumVisible.NEVER.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_178679_a[Team.EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_178679_a[Team.EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
