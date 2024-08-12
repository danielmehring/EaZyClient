package net.minecraft.client.renderer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonSyntaxException;

import me.EaZy.client.Configs;
import me.EaZy.client.events.Event3D;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.AntiBlind;
import me.EaZy.client.modules.NoFov;
import me.EaZy.client.modules.NoHurtcam;
import me.EaZy.client.modules.Reach;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.CustomColors;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class EntityRenderer implements IResourceManagerReloadListener {

	public static final int EaZy = 794;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
	private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
	public static boolean anaglyphEnable;

	public float zoomWithAni;

	public float target;

	/** Anaglyph field (0=R, 1=GB) */
	public static int anaglyphField;

	/** A reference to the Minecraft object. */
	private final Minecraft mc;
	private final IResourceManager resourceManager;
	private final Random random = new Random();
	private float farPlaneDistance;
	public ItemRenderer itemRenderer;
	private final MapItemRenderer theMapItemRenderer;

	/** Entity renderer update count */
	private int rendererUpdateCount;

	/** Pointed entity */
	private Entity pointedEntity;
	private MouseFilter mouseFilterXAxis = new MouseFilter();
	private MouseFilter mouseFilterYAxis = new MouseFilter();
	private final float thirdPersonDistance = 4.0F;

	/** Third person distance temp */
	private float thirdPersonDistanceTemp = 4.0F;

	/** Smooth cam yaw */
	private float smoothCamYaw;

	/** Smooth cam pitch */
	private float smoothCamPitch;

	/** Smooth cam filter X */
	private float smoothCamFilterX;

	/** Smooth cam filter Y */
	private float smoothCamFilterY;

	/** Smooth cam partial ticks */
	private float smoothCamPartialTicks;

	/** FOV modifier hand */
	private float fovModifierHand;

	/** FOV modifier hand prev */
	private float fovModifierHandPrev;
	private float bossColorModifier;
	private float bossColorModifierPrev;

	/** Cloud fog mode */
	private boolean cloudFog;
	private final boolean field_175074_C = true;
	private final boolean field_175073_D = true;

	/** Previous frame time in milliseconds */
	private long prevFrameTime = Minecraft.getSystemTime();

	/** End time of last render (ns) */
	public long renderEndNanoTime;

	/**
	 * The texture id of the blocklight/skylight texture used for lighting effects
	 */
	private final DynamicTexture lightmapTexture;

	/**
	 * Colors computed in updateLightmap() and loaded into the lightmap emptyTexture
	 */
	private final int[] lightmapColors;
	private final ResourceLocation locationLightMap;

	/**
	 * Is set, updateCameraAndRender() calls updateLightmap(); set by
	 * updateTorchFlicker()
	 */
	private boolean lightmapUpdateNeeded;

	/** Torch flicker X */
	private float torchFlickerX;
	private float field_175075_L;

	/** Rain sound counter */
	private int rainSoundCounter;
	private final float[] field_175076_N = new float[1024];
	private final float[] field_175077_O = new float[1024];

	/** Fog color buffer */
	private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
	public float field_175080_Q;
	public float field_175082_R;
	public float field_175081_S;

	/** Fog color 2 */
	private float fogColor2;

	/** Fog color 1 */
	private float fogColor1;
	private final int field_175079_V = 0;
	private final boolean field_175078_W = false;
	private final double cameraZoom = 1.0D;
	private double cameraYaw;
	private double cameraPitch;
	public ShaderGroup theShaderGroup;
	private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] {
			new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"),
			new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"),
			new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"),
			new ResourceLocation("shaders/post/color_convolve.json"),
			new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"),
			new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"),
			new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"),
			new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"),
			new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"),
			new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"),
			new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"),
			new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"),
			new ResourceLocation("shaders/post/spider.json") };
	public static final int shaderCount = shaderResourceLocations.length;
	private int shaderIndex;
	private boolean field_175083_ad;
	public int field_175084_ae;
	private boolean initialized = false;
	private World updatedWorld = null;
	public boolean fogStandard = false;
	private float clipDistance = 128.0F;
	private long lastServerTime = 0L;
	private int lastServerTicks = 0;
	private int serverWaitTime = 0;
	private long lastErrorCheckTimeMs = 0L;
	private final ShaderGroup[] fxaaShaders = new ShaderGroup[10];

	public EntityRenderer(final Minecraft mcIn, final IResourceManager p_i45076_2_) {
		shaderIndex = shaderCount;
		field_175083_ad = false;
		field_175084_ae = 0;
		mc = mcIn;
		resourceManager = p_i45076_2_;
		itemRenderer = mcIn.getItemRenderer();
		theMapItemRenderer = new MapItemRenderer(Minecraft.getTextureManager());
		lightmapTexture = new DynamicTexture(16, 16);
		locationLightMap = Minecraft.getTextureManager().getDynamicTextureLocation("lightMap", lightmapTexture);
		lightmapColors = lightmapTexture.getTextureData();
		theShaderGroup = null;

		for (int var3 = 0; var3 < 32; ++var3) {
			for (int var4 = 0; var4 < 32; ++var4) {
				final float var5 = var4 - 16;
				final float var6 = var3 - 16;
				final float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
				field_175076_N[var3 << 5 | var4] = -var6 / var7;
				field_175077_O[var3 << 5 | var4] = var5 / var7;
			}
		}
	}

	public boolean isShaderActive() {
		return OpenGlHelper.shadersSupported && theShaderGroup != null;
	}

	public void stopUseShader() {
		if (theShaderGroup != null) {
			theShaderGroup.deleteShaderGroup();
		}

		theShaderGroup = null;
		shaderIndex = shaderCount;
	}

	public void func_175071_c() {
		field_175083_ad = !field_175083_ad;
	}

	public void func_175066_a(final Entity p_175066_1_) {
		if (OpenGlHelper.shadersSupported) {
			if (theShaderGroup != null) {
				theShaderGroup.deleteShaderGroup();
			}

			theShaderGroup = null;

			if (p_175066_1_ instanceof EntityCreeper) {
				loadShader(new ResourceLocation("shaders/post/creeper.json"));
			} else if (p_175066_1_ instanceof EntitySpider) {
				loadShader(new ResourceLocation("shaders/post/spider.json"));
			} else if (p_175066_1_ instanceof EntityEnderman) {
				loadShader(new ResourceLocation("shaders/post/invert.json"));
			} else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
				Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { p_175066_1_, this });
			}
		}
	}

	public void activateNextShader() {
		if (OpenGlHelper.shadersSupported && mc.func_175606_aa() instanceof EntityPlayer) {
			if (theShaderGroup != null) {
				theShaderGroup.deleteShaderGroup();
			}

			shaderIndex = (shaderIndex + 1) % (shaderResourceLocations.length + 1);

			if (shaderIndex != shaderCount) {
				loadShader(shaderResourceLocations[shaderIndex]);
			} else {
				theShaderGroup = null;
			}
		}
	}

	public void loadShader(final ResourceLocation p_175069_1_) {
		if (OpenGlHelper.isFramebufferEnabled()) {
			try {
				theShaderGroup = new ShaderGroup(Minecraft.getTextureManager(), resourceManager, mc.getFramebuffer(),
						p_175069_1_);
				theShaderGroup.createBindFramebuffers(Minecraft.displayWidth, Minecraft.displayHeight);
				field_175083_ad = true;
			} catch (final IOException var3) {
				logger.warn("Failed to load shader: " + p_175069_1_, var3);
				shaderIndex = shaderCount;
				field_175083_ad = false;
			} catch (final JsonSyntaxException var4) {
				logger.warn("Failed to load shader: " + p_175069_1_, var4);
				shaderIndex = shaderCount;
				field_175083_ad = false;
			}
		}
	}

	@Override
	public void onResourceManagerReload(final IResourceManager resourceManager) {
		if (theShaderGroup != null) {
			theShaderGroup.deleteShaderGroup();
		}

		theShaderGroup = null;

		if (shaderIndex != shaderCount) {
			loadShader(shaderResourceLocations[shaderIndex]);
		} else {
			func_175066_a(mc.func_175606_aa());
		}
	}

	/**
	 * Updates the entity renderer
	 */
	public void updateRenderer() {
		if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
			ShaderLinkHelper.setNewStaticShaderLinkHelper();
		}

		updateFovModifierHand();
		updateTorchFlicker();
		fogColor2 = fogColor1;
		thirdPersonDistanceTemp = thirdPersonDistance;
		float var1;
		float var2;

		if (Minecraft.gameSettings.smoothCamera) {
			var1 = Minecraft.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			var2 = var1 * var1 * var1 * 8.0F;
			smoothCamFilterX = mouseFilterXAxis.smooth(smoothCamYaw, 0.05F * var2);
			smoothCamFilterY = mouseFilterYAxis.smooth(smoothCamPitch, 0.05F * var2);
			smoothCamPartialTicks = 0.0F;
			smoothCamYaw = 0.0F;
			smoothCamPitch = 0.0F;
		} else {
			smoothCamFilterX = 0.0F;
			smoothCamFilterY = 0.0F;
			mouseFilterXAxis.func_180179_a();
			mouseFilterYAxis.func_180179_a();
		}

		if (mc.func_175606_aa() == null) {
			mc.func_175607_a(Minecraft.thePlayer);
		}

		final Entity viewEntity = mc.func_175606_aa();
		final double vx = viewEntity.posX;
		final double vy = viewEntity.posY + viewEntity.getEyeHeight();
		final double vz = viewEntity.posZ;
		var1 = Minecraft.theWorld.getLightBrightness(new BlockPos(vx, vy, vz));
		var2 = Minecraft.gameSettings.renderDistanceChunks / 16.0F;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		final float var3 = var1 * (1.0F - var2) + var2;
		fogColor1 += (var3 - fogColor1) * 0.1F;
		++rendererUpdateCount;
		itemRenderer.updateEquippedItem();
		addRainParticles();
		bossColorModifierPrev = bossColorModifier;

		if (BossStatus.hasColorModifier) {
			bossColorModifier += 0.05F;

			if (bossColorModifier > 1.0F) {
				bossColorModifier = 1.0F;
			}

			BossStatus.hasColorModifier = false;
		} else if (bossColorModifier > 0.0F) {
			bossColorModifier -= 0.0125F;
		}
	}

	public ShaderGroup getShaderGroup() {
		return theShaderGroup;
	}

	public void updateShaderGroupSize(final int p_147704_1_, final int p_147704_2_) {
		if (OpenGlHelper.shadersSupported) {
			if (theShaderGroup != null) {
				theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
			}

			mc.renderGlobal.createBindEntityOutlineFbs(p_147704_1_, p_147704_2_);
		}
	}

	/**
	 * Finds what block or object the mouse is over at the specified partial tick
	 * time. Args: partialTickTime
	 */
	public void getMouseOver(final float p_78473_1_) {
		final Entity entity = mc.func_175606_aa();

		if (entity != null && Minecraft.theWorld != null) {
			Minecraft.mcProfiler.startSection("pick");
			mc.pointedEntity = null;
			double reachDistFromPlyController = Minecraft.playerController.getBlockReachDistance();
			mc.objectMouseOver = entity.func_174822_a(reachDistFromPlyController, p_78473_1_);
			double reachDist = reachDistFromPlyController;
			final Vec3 var7 = entity.func_174824_e(p_78473_1_);

			final boolean reach = Reach.mod.isToggled();
			final float reachDistance = Client.setmgr.getSettingByName(Reach.mod, "Range").getValFloat();

			if (Minecraft.playerController.extendedReach()) {
				reachDistFromPlyController = 6.0D;
				reachDist = 6.0D;
			} else {
				if (reachDistFromPlyController > (reach ? reachDistance : 3.0D)) {
					reachDistFromPlyController = (reach ? reachDistance : 3.0D);
				}
				reachDistFromPlyController = reachDist;
			}

			if (mc.objectMouseOver != null) {
				reachDist = reach ? Math.max(reachDistance, mc.objectMouseOver.hitVec.distanceTo(var7))
						: mc.objectMouseOver.hitVec.distanceTo(var7);
			}

			final Vec3 var8 = entity.getLook(p_78473_1_);
			final Vec3 var9 = var7.addVector(var8.xCoord * reachDistFromPlyController,
					var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController);
			pointedEntity = null;
			Vec3 var10 = null;
			final float var11 = 1.0F;
			final List var12 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox()
							.addCoord(var8.xCoord * reachDistFromPlyController,
									var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController)
							.expand(var11, var11, var11));
			double var13 = reachDist;

			for (int var15 = 0; var15 < var12.size(); ++var15) {
				final Entity var16 = (Entity) var12.get(var15);

				if (var16.canBeCollidedWith()) {
					final float var17 = var16.getCollisionBorderSize();
					final AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
					final MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);

					if (var18.isVecInside(var7)) {
						if (0.0D < var13 || var13 == 0.0D) {
							pointedEntity = var16;
							var10 = var19 == null ? var7 : var19.hitVec;
							var13 = 0.0D;
						}
					} else if (var19 != null) {
						final double var20 = var7.distanceTo(var19.hitVec);

						if (var20 < var13 || var13 == 0.0D) {
							boolean canRiderInteract = false;

							if (Reflector.ForgeEntity_canRiderInteract.exists()) {
								canRiderInteract = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract,
										new Object[0]);
							}

							if (var16 == entity.ridingEntity && !canRiderInteract) {
								if (var13 == 0.0D) {
									pointedEntity = var16;
									var10 = var19.hitVec;
								}
							} else {
								pointedEntity = var16;
								var10 = var19.hitVec;
								var13 = var20;
							}
						}
					}
				}
			}

			if (pointedEntity != null && (var13 < reachDist || mc.objectMouseOver == null)) {
				mc.objectMouseOver = new MovingObjectPosition(pointedEntity, var10);

				if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
					mc.pointedEntity = pointedEntity;
				}
			}

			Minecraft.mcProfiler.endSection();
		}
	}

	/**
	 * Update FOV modifier hand
	 */
	private void updateFovModifierHand() {
		float var1 = 1.0F;

		if (mc.func_175606_aa() instanceof AbstractClientPlayer) {
			final AbstractClientPlayer var2 = (AbstractClientPlayer) mc.func_175606_aa();
			var1 = var2.func_175156_o();
		}

		fovModifierHandPrev = fovModifierHand;
		if (NoFov.mod.isToggled() && Minecraft.thePlayer.ticksExisted > 20) {
			return;
		}
		fovModifierHand += (var1 - fovModifierHand) * 0.5F;

		if (fovModifierHand > 1.5F) {
			fovModifierHand = 1.5F;
		}

		if (fovModifierHand < 0.1F) {
			fovModifierHand = 0.1F;
		}
	}

	/**
	 * Changes the field of view of the player depending on if they are underwater
	 * or not
	 */
	private float getFOVModifier(final float partialTicks, final boolean p_78481_2_) {
		if (field_175078_W) {
			return 90.0F;
		} else {
			final Entity var3 = mc.func_175606_aa();
			float var4 = 70.0F;

			if (p_78481_2_) {
				var4 = Minecraft.gameSettings.fovSetting;

				if (Config.isDynamicFov()) {
					var4 *= fovModifierHandPrev + (fovModifierHand - fovModifierHandPrev) * partialTicks;
				}
			}

			boolean zoomActive = false;

			if (Minecraft.currentScreen == null) {
				zoomActive = GameSettings.isKeyDown(Minecraft.gameSettings.ofKeyBindZoom);
			}
			// TODO
			var4 /= zoomWithAni;

			if (zoomActive) {
				if (!Config.zoomMode) {
					Config.zoomMode = true;
					Minecraft.gameSettings.smoothCamera = true;
				}

			} else if (Config.zoomMode) {
				Config.zoomMode = false;
				Minecraft.gameSettings.smoothCamera = false;
				mouseFilterXAxis = new MouseFilter();
				mouseFilterYAxis = new MouseFilter();
				mc.renderGlobal.displayListEntitiesDirty = true;
			}

			if (var3 instanceof EntityLivingBase && ((EntityLivingBase) var3).getHealth() <= 0.0F
					&& !Configs.gta5Death) {
				final float var6 = ((EntityLivingBase) var3).deathTime + partialTicks;
				var4 /= (1.0F - 500.0F / (var6 + 500.0F)) * 2.0F + 1.0F;
			}

			final Block var61 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);

			if (var61.getMaterial() == Material.water) {
				var4 = var4 * 60.0F / 70.0F;
			}

			return var4;
		}
	}

	private void hurtCameraEffect(final float p_78482_1_) {
		if (mc.func_175606_aa() instanceof EntityLivingBase && !NoHurtcam.mod.isToggled()) {
			final EntityLivingBase var2 = (EntityLivingBase) mc.func_175606_aa();
			float var3 = var2.hurtTime - p_78482_1_;
			float var4;

			if (var2.getHealth() <= 0.0F && !Configs.gta5Death) {
				var4 = var2.deathTime + p_78482_1_;
				GlStateManager.rotate(40.0F - 8000.0F / (var4 + 200.0F), 0.0F, 0.0F, 1.0F);
			}

			if (var3 < 0.0F) {
				return;
			}

			var3 /= var2.maxHurtTime;
			var3 = MathHelper.sin(var3 * var3 * var3 * var3 * (float) Math.PI);
			var4 = var2.attackedAtYaw;
			GlStateManager.rotate(-var4, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-var3 * 14.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(var4, 0.0F, 1.0F, 0.0F);
		}
	}

	/**
	 * Setups all the GL settings for view bobbing. Args: partialTickTime
	 */
	private void setupViewBobbing(final float p_78475_1_) {
		if (mc.func_175606_aa() instanceof EntityPlayer) {
			final EntityPlayer var2 = (EntityPlayer) mc.func_175606_aa();
			final float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
			final float var4 = -(var2.distanceWalkedModified + var3 * p_78475_1_);
			final float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * p_78475_1_;
			final float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * p_78475_1_;
			GlStateManager.translate(MathHelper.sin(var4 * (float) Math.PI) * var5 * 0.5F,
					-Math.abs(MathHelper.cos(var4 * (float) Math.PI) * var5), 0.0F);
			GlStateManager.rotate(MathHelper.sin(var4 * (float) Math.PI) * var5 * 3.0F * /* TODO EaZy */ (int) 1.9,
					0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(
					Math.abs(MathHelper.cos(var4 * (float) Math.PI - 0.2F) * var5) * 5.0F * /* TODO EaZy */ (int) 1.0,
					1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(var6, 1.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * sets up player's eye (or camera in third person mode)
	 */
	private void orientCamera(final float p_78467_1_) {
		if (Configs.gta5Death && Minecraft.thePlayer.getHealth() <= 0.0f) {
			final Entity var2 = mc.thePlayer;
			float var3 = var2.getEyeHeight();
			double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
			double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
			double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
			float yaw;
			float pitch;
			double var28 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * p_78467_1_;

			yaw = var2.rotationYaw;
			pitch = var2.rotationPitch;

			final double roll = -MathHelper.sin(yaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * var28;
			final double event = MathHelper.cos(yaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * var28;
			final double var18 = -MathHelper.sin(pitch / 180.0F * (float) Math.PI) * var28;

			for (int var20 = 0; var20 < 8; ++var20) {
				float var21 = (var20 & 1) * 2 - 1;
				float var22 = (var20 >> 1 & 1) * 2 - 1;
				float var23 = (var20 >> 2 & 1) * 2 - 1;
				var21 *= 0.1F;
				var22 *= 0.1F;
				var23 *= 0.1F;
				final MovingObjectPosition var24 = Minecraft.theWorld.rayTraceBlocks(
						new Vec3(var4 + var21, var6 + var22, var8 + var23),
						new Vec3(var4 - roll + var21 + var23, var6 - var18 + var22, var8 - event + var23));

				if (var24 != null) {
					final double var25 = var24.hitVec.distanceTo(new Vec3(var4, var6, var8));

					if (var25 < var28) {
						var28 = var25;
					}
				}
			}

			GlStateManager.rotate(var2.rotationPitch - pitch, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(var2.rotationYaw - yaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, (float) -var28);
			GlStateManager.rotate(yaw - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(pitch - var2.rotationPitch, 1.0F, 0.0F, 0.0F);

			if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
				if (!Minecraft.gameSettings.debugCamEnable) {
					yaw = var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F;
					pitch = var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_;
					float var31 = 0.0F;

					if (var2 instanceof EntityAnimal) {
						final EntityAnimal block = (EntityAnimal) var2;
						yaw = block.prevRotationYawHead
								+ (block.rotationYawHead - block.prevRotationYawHead) * p_78467_1_ + 180.0F;
					}

					final Block var32 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var2, p_78467_1_);
					final Object var33 = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor,
							new Object[] { this, var2, var32, p_78467_1_, yaw, pitch, var31 });
					Reflector.postForgeBusEvent(var33);
					var31 = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_roll,
							var31);
					pitch = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_pitch,
							pitch);
					yaw = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_yaw, yaw);
					GlStateManager.rotate(var31, 0.0F, 0.0F, 1.0F);
					GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
				}
			} else if (!Minecraft.gameSettings.debugCamEnable) {
				GlStateManager.rotate(
						var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, 1.0F, 0.0F,
						0.0F);

				if (var2 instanceof EntityAnimal) {
					final EntityAnimal var281 = (EntityAnimal) var2;
					GlStateManager.rotate(
							var281.prevRotationYawHead
									+ (var281.rotationYawHead - var281.prevRotationYawHead) * p_78467_1_ + 180.0F,
							0.0F, 1.0F, 0.0F);
				} else {
					GlStateManager.rotate(
							var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F,
							0.0F, 1.0F, 0.0F);
				}
			}

			GlStateManager.translate(0.0F, -var3, 0.0F);
			var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
			var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
			var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
			cloudFog = mc.renderGlobal.hasCloudFog(var4, var6, var8, p_78467_1_);
		} else {
			final Entity var2 = mc.func_175606_aa();
			float var3 = var2.getEyeHeight();
			double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
			double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
			double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
			float yaw;
			float pitch;

			if (var2 instanceof EntityLivingBase && ((EntityLivingBase) var2).isPlayerSleeping()) {
				var3 = (float) (var3 + 1.0D);
				GlStateManager.translate(0.0F, 0.3F, 0.0F);

				if (!Minecraft.gameSettings.debugCamEnable) {
					final BlockPos var27 = new BlockPos(var2);
					final IBlockState partialTicks = Minecraft.theWorld.getBlockState(var27);
					final Block var29 = partialTicks.getBlock();

					if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
						Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera,
								new Object[] { Minecraft.theWorld, var27, partialTicks, var2 });
					} else if (var29 == Blocks.bed) {
						final int var30 = ((EnumFacing) partialTicks.getValue(BlockDirectional.AGE))
								.getHorizontalIndex();
						GlStateManager.rotate(var30 * 90, 0.0F, 1.0F, 0.0F);
					}

					GlStateManager.rotate(
							var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F,
							0.0F, -1.0F, 0.0F);
					GlStateManager.rotate(
							var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, -1.0F,
							0.0F, 0.0F);
				}
			} else if (Minecraft.gameSettings.thirdPersonView > 0) {
				double var28 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * p_78467_1_;

				if (Minecraft.gameSettings.debugCamEnable) {
					GlStateManager.translate(0.0F, 0.0F, (float) -var28);
				} else {
					yaw = var2.rotationYaw;
					pitch = var2.rotationPitch;

					if (Minecraft.gameSettings.thirdPersonView == 2) {
						pitch += 180.0F;
					}

					final double roll = -MathHelper.sin(yaw / 180.0F * (float) Math.PI)
							* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * var28;
					final double event = MathHelper.cos(yaw / 180.0F * (float) Math.PI)
							* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * var28;
					final double var18 = -MathHelper.sin(pitch / 180.0F * (float) Math.PI) * var28;

					for (int var20 = 0; var20 < 8; ++var20) {
						float var21 = (var20 & 1) * 2 - 1;
						float var22 = (var20 >> 1 & 1) * 2 - 1;
						float var23 = (var20 >> 2 & 1) * 2 - 1;
						var21 *= 0.1F;
						var22 *= 0.1F;
						var23 *= 0.1F;
						final MovingObjectPosition var24 = Minecraft.theWorld.rayTraceBlocks(
								new Vec3(var4 + var21, var6 + var22, var8 + var23),
								new Vec3(var4 - roll + var21 + var23, var6 - var18 + var22, var8 - event + var23));

						if (var24 != null) {
							final double var25 = var24.hitVec.distanceTo(new Vec3(var4, var6, var8));

							if (var25 < var28) {
								var28 = var25;
							}
						}
					}

					if (Minecraft.gameSettings.thirdPersonView == 2) {
						GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
					}

					GlStateManager.rotate(var2.rotationPitch - pitch, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotate(var2.rotationYaw - yaw, 0.0F, 1.0F, 0.0F);
					GlStateManager.translate(0.0F, 0.0F, (float) -var28);
					GlStateManager.rotate(yaw - var2.rotationYaw, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(pitch - var2.rotationPitch, 1.0F, 0.0F, 0.0F);
				}
			} else {
				GlStateManager.translate(0.0F, 0.0F, -0.1F);
			}

			if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
				if (!Minecraft.gameSettings.debugCamEnable) {
					yaw = var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F;
					pitch = var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_;
					float var31 = 0.0F;

					if (var2 instanceof EntityAnimal) {
						final EntityAnimal block = (EntityAnimal) var2;
						yaw = block.prevRotationYawHead
								+ (block.rotationYawHead - block.prevRotationYawHead) * p_78467_1_ + 180.0F;
					}

					final Block var32 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var2, p_78467_1_);
					final Object var33 = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor,
							new Object[] { this, var2, var32, p_78467_1_, yaw, pitch, var31 });
					Reflector.postForgeBusEvent(var33);
					var31 = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_roll,
							var31);
					pitch = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_pitch,
							pitch);
					yaw = Reflector.getFieldValueFloat(var33, Reflector.EntityViewRenderEvent_CameraSetup_yaw, yaw);
					GlStateManager.rotate(var31, 0.0F, 0.0F, 1.0F);
					GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
					GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
				}
			} else if (!Minecraft.gameSettings.debugCamEnable) {
				GlStateManager.rotate(
						var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, 1.0F, 0.0F,
						0.0F);

				if (var2 instanceof EntityAnimal) {
					final EntityAnimal var281 = (EntityAnimal) var2;
					GlStateManager.rotate(
							var281.prevRotationYawHead
									+ (var281.rotationYawHead - var281.prevRotationYawHead) * p_78467_1_ + 180.0F,
							0.0F, 1.0F, 0.0F);
				} else {
					GlStateManager.rotate(
							var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0F,
							0.0F, 1.0F, 0.0F);
				}
			}

			GlStateManager.translate(0.0F, -var3, 0.0F);
			var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
			var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
			var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
			cloudFog = mc.renderGlobal.hasCloudFog(var4, var6, var8, p_78467_1_);
		}
	}

	/**
	 * sets up projection, view effects, camera position/rotation
	 */
	@SuppressWarnings("unused")
	public void setupCameraTransform(final float partialTicks, final int pass) {
		farPlaneDistance = Minecraft.gameSettings.renderDistanceChunks * 16;

		if (Config.isFogFancy()) {
			farPlaneDistance *= 0.95F;
		}

		if (Config.isFogFast()) {
			farPlaneDistance *= 0.83F;
		}

		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		final float var3 = 0.07F;

		if (Minecraft.gameSettings.anaglyph) {
			GlStateManager.translate(-(pass * 2 - 1) * var3, 0.0F, 0.0F);
		}

		clipDistance = farPlaneDistance * 2.0F;

		if (clipDistance < 173.0F) {
			clipDistance = 173.0F;
		}

		if (Minecraft.theWorld.provider.getDimensionId() == 1) {
			clipDistance = 256.0F;
		}

		if (cameraZoom != 1.0D) {
			GlStateManager.translate((float) cameraYaw, (float) -cameraPitch, 0.0F);
			GlStateManager.scale(cameraZoom, cameraZoom, 1.0D);
		}

		Project.gluPerspective(getFOVModifier(partialTicks, true),
				(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, clipDistance);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();

		if (Minecraft.gameSettings.anaglyph) {
			GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		hurtCameraEffect(partialTicks);

		// TODO EaZy
		if (Minecraft.gameSettings.viewBobbing) {
			setupViewBobbing(partialTicks);
		}

		final float var4 = Minecraft.thePlayer.prevTimeInPortal
				+ (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * partialTicks;

		if (var4 > 0.0F) {
			byte var5 = 20;

			if (Minecraft.thePlayer.isPotionActive(Potion.confusion)) {
				var5 = 7;
			}

			float var6 = 5.0F / (var4 * var4 + 5.0F) - var4 * 0.04F;
			var6 *= var6;
			GlStateManager.rotate((rendererUpdateCount + partialTicks) * var5, 0.0F, 1.0F, 1.0F);
			GlStateManager.scale(1.0F / var6, 1.0F, 1.0F);
			GlStateManager.rotate(-(rendererUpdateCount + partialTicks) * var5, 0.0F, 1.0F, 1.0F);
		}

		orientCamera(partialTicks);

		if (field_175078_W) {
			switch (field_175079_V) {
			case 0:
				GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				break;

			case 1:
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				break;

			case 2:
				GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				break;

			case 3:
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				break;

			case 4:
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			}
		}
	}

	/**
	 * Render player hand
	 */
	public void renderHand(final float p_78476_1_, final int p_78476_2_) {
		if (Configs.gta5Death && mc.thePlayer.getHealth() <= 0.0f) {
			return;
		}
		if (!field_175078_W) {
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			final float var3 = 0.07F;

			if (Minecraft.gameSettings.anaglyph) {
				GlStateManager.translate(-(p_78476_2_ * 2 - 1) * var3, 0.0F, 0.0F);
			}

			if (Config.isShaders()) {
				Shaders.applyHandDepth();
			}

			Project.gluPerspective(getFOVModifier(p_78476_1_, false),
					(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, farPlaneDistance * 2.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();

			if (Minecraft.gameSettings.anaglyph) {
				GlStateManager.translate((p_78476_2_ * 2 - 1) * 0.1F, 0.0F, 0.0F);
			}

			boolean var4 = false;

			if (!Config.isShaders() || !Shaders.isHandRendered) {
				GlStateManager.pushMatrix();
				hurtCameraEffect(p_78476_1_);

				if (Minecraft.gameSettings.viewBobbing) {
					setupViewBobbing(p_78476_1_);
				}

				var4 = mc.func_175606_aa() instanceof EntityLivingBase
						&& ((EntityLivingBase) mc.func_175606_aa()).isPlayerSleeping();

				if (Minecraft.gameSettings.thirdPersonView == 0 && !var4 && !Minecraft.gameSettings.hideGUI
						&& !Minecraft.playerController.enableEverythingIsScrewedUpMode()) {
					func_180436_i();

					if (Config.isShaders()) {
						ShadersRender.renderItemFP(itemRenderer, p_78476_1_);
					} else {
						itemRenderer.renderItemInFirstPerson(p_78476_1_);
					}

					func_175072_h();
				}

				GlStateManager.popMatrix();
			}

			if (Config.isShaders() && !Shaders.isCompositeRendered) {
				return;
			}

			func_175072_h();

			if (Minecraft.gameSettings.thirdPersonView == 0 && !var4) {
				itemRenderer.renderOverlays(p_78476_1_);
				hurtCameraEffect(p_78476_1_);
			}

			if (Minecraft.gameSettings.viewBobbing) {
				setupViewBobbing(p_78476_1_);
			}
		}
	}

	public void func_175072_h() {
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.disableLightmap();
		}
	}

	public void func_180436_i() {
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		final float var1 = 0.00390625F;
		GlStateManager.scale(var1, var1, var1);
		GlStateManager.translate(8.0F, 8.0F, 8.0F);
		GlStateManager.matrixMode(5888);
		Minecraft.getTextureManager().bindTexture(locationLightMap);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.enableLightmap();
		}
	}

	/**
	 * Recompute a random value that is applied to block color in updateLightmap()
	 */
	// TODO
	private void updateTorchFlicker() {
		field_175075_L = (float) (field_175075_L + (Math.random() - Math.random()) * Math.random() * Math.random());
		field_175075_L = (float) (field_175075_L * 0.9D);
		torchFlickerX += (field_175075_L - torchFlickerX) * 1.0F;
		lightmapUpdateNeeded = true;

	}

	private void updateLightmap(final float partialTicks) {
		if (Configs.smoothZoom) {
			if (zoomWithAni < target) {
				zoomWithAni += 0.1;
			}

			if (zoomWithAni > target) {
				zoomWithAni -= 0.1;
			}
		} else {
			if (Config.zoomMode) {
				zoomWithAni = 4;
			} else {
				zoomWithAni = 1;
			}
		}

		if (Config.zoomMode) {
			target = 4;
		} else {
			target = 1;
		}

		if (lightmapUpdateNeeded) {
			Minecraft.mcProfiler.startSection("lightTex");
			final WorldClient var2 = Minecraft.theWorld;

			if (var2 != null) {
				if (Config.isCustomColors() && CustomColors.updateLightmap(var2, torchFlickerX, lightmapColors,
						Minecraft.thePlayer.isPotionActive(Potion.nightVision))) {
					lightmapTexture.updateDynamicTexture();
					lightmapUpdateNeeded = false;
					Minecraft.mcProfiler.endSection();
					return;
				}

				for (int var3 = 0; var3 < 256; ++var3) {
					final float var4 = var2.getSunBrightness(1.0F) * 0.95F + 0.05F;
					float var5 = var2.provider.getLightBrightnessTable()[var3 / 16] * var4;
					final float var6 = var2.provider.getLightBrightnessTable()[var3 % 16]
							* (torchFlickerX * 0.1F + 1.5F);

					if (var2.func_175658_ac() > 0) {
						var5 = var2.provider.getLightBrightnessTable()[var3 / 16];
					}

					final float var7 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
					final float var8 = var5 * (var2.getSunBrightness(1.0F) * 0.65F + 0.35F);
					final float var11 = var6 * ((var6 * 0.6F + 0.4F) * 0.6F + 0.4F);
					final float var12 = var6 * (var6 * var6 * 0.6F + 0.4F);
					float var13 = var7 + var6;
					float var14 = var8 + var11;
					float var15 = var5 + var12;
					var13 = var13 * 0.96F + 0.03F;
					var14 = var14 * 0.96F + 0.03F;
					var15 = var15 * 0.96F + 0.03F;
					float var16;

					if (bossColorModifier > 0.0F) {
						var16 = bossColorModifierPrev + (bossColorModifier - bossColorModifierPrev) * partialTicks;
						var13 = var13 * (1.0F - var16) + var13 * 0.7F * var16;
						var14 = var14 * (1.0F - var16) + var14 * 0.6F * var16;
						var15 = var15 * (1.0F - var16) + var15 * 0.6F * var16;
					}

					if (var2.provider.getDimensionId() == 1) {
						var13 = 0.22F + var6 * 0.75F;
						var14 = 0.28F + var11 * 0.75F;
						var15 = 0.25F + var12 * 0.75F;
					}

					float var17;

					if (Minecraft.thePlayer.isPotionActive(Potion.nightVision)) {
						var16 = func_180438_a(Minecraft.thePlayer, partialTicks);
						var17 = 1.0F / var13;

						if (var17 > 1.0F / var14) {
							var17 = 1.0F / var14;
						}

						if (var17 > 1.0F / var15) {
							var17 = 1.0F / var15;
						}

						var13 = var13 * (1.0F - var16) + var13 * var17 * var16;
						var14 = var14 * (1.0F - var16) + var14 * var17 * var16;
						var15 = var15 * (1.0F - var16) + var15 * var17 * var16;
					}

					if (var13 > 1.0F) {
						var13 = 1.0F;
					}

					if (var14 > 1.0F) {
						var14 = 1.0F;
					}

					if (var15 > 1.0F) {
						var15 = 1.0F;
					}

					var16 = Minecraft.gameSettings.gammaSetting;
					var17 = 1.0F - var13;
					float var18 = 1.0F - var14;
					float var19 = 1.0F - var15;
					var17 = 1.0F - var17 * var17 * var17 * var17;
					var18 = 1.0F - var18 * var18 * var18 * var18;
					var19 = 1.0F - var19 * var19 * var19 * var19;
					var13 = var13 * (1.0F - var16) + var17 * var16;
					var14 = var14 * (1.0F - var16) + var18 * var16;
					var15 = var15 * (1.0F - var16) + var19 * var16;
					var13 = var13 * 0.96F + 0.03F;
					var14 = var14 * 0.96F + 0.03F;
					var15 = var15 * 0.96F + 0.03F;

					if (var13 > 1.0F) {
						var13 = 1.0F;
					}

					if (var14 > 1.0F) {
						var14 = 1.0F;
					}

					if (var15 > 1.0F) {
						var15 = 1.0F;
					}

					if (var13 < 0.0F) {
						var13 = 0.0F;
					}

					if (var14 < 0.0F) {
						var14 = 0.0F;
					}

					if (var15 < 0.0F) {
						var15 = 0.0F;
					}

					final short var20 = 255;
					final int var21 = (int) (var13 * 255.0F);
					final int var22 = (int) (var14 * 255.0F);
					final int var23 = (int) (var15 * 255.0F);
					lightmapColors[var3] = var20 << 24 | var21 << 16 | var22 << 8 | var23;
				}

				lightmapTexture.updateDynamicTexture();
				lightmapUpdateNeeded = false;
				Minecraft.mcProfiler.endSection();
			}
		}
	}

	private float func_180438_a(final EntityLivingBase p_180438_1_, final float partialTicks) {
		final int var3 = p_180438_1_.getActivePotionEffect(Potion.nightVision).getDuration();
		return var3 > 200 ? 1.0F : 0.7F + MathHelper.sin((var3 - partialTicks) * (float) Math.PI * 0.2F) * 0.3F;
	}

	/**
	 * Will update any inputs that effect the camera angle (mouse) and then render
	 * the world and GUI
	 */
	public void updateCameraAndRender(final float partialTicks) {
		frameInit();
		final boolean var2 = Display.isActive();

		if (!var2 && Minecraft.gameSettings.pauseOnLostFocus
				&& (!Minecraft.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
			if (Minecraft.getSystemTime() - prevFrameTime > 500L) {
				mc.displayInGameMenu();
			}
		} else {
			prevFrameTime = Minecraft.getSystemTime();
		}

		Minecraft.mcProfiler.startSection("mouse");

		if (var2 && Minecraft.isRunningOnMac && mc.inGameHasFocus && !Mouse.isInsideWindow()) {
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
			Mouse.setGrabbed(true);
		}

		if (mc.inGameHasFocus && var2) {
			mc.mouseHelper.mouseXYChange();
			final float var13 = Minecraft.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			final float var14 = var13 * var13 * var13 * 8.0F;
			float var15 = mc.mouseHelper.deltaX * var14;
			float var16 = mc.mouseHelper.deltaY * var14;
			byte var17 = 1;

			if (Minecraft.gameSettings.invertMouse) {
				var17 = -1;
			}

			if (Minecraft.gameSettings.smoothCamera) {
				smoothCamYaw += var15;
				smoothCamPitch += var16;
				final float var18 = partialTicks - smoothCamPartialTicks;
				smoothCamPartialTicks = partialTicks;
				var15 = smoothCamFilterX * var18;
				var16 = smoothCamFilterY * var18;
				Minecraft.thePlayer.setAngles(var15, var16 * var17);
			} else {
				smoothCamYaw = 0.0F;
				smoothCamPitch = 0.0F;
				Minecraft.thePlayer.setAngles(var15, var16 * var17);
			}
		}

		Minecraft.mcProfiler.endSection();

		if (!mc.skipRenderWorld) {
			anaglyphEnable = Minecraft.gameSettings.anaglyph;
			final ScaledResolution var131 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
			final int var141 = var131.getScaledWidth();
			final int var151 = var131.getScaledHeight();
			final int var161 = Mouse.getX() * var141 / Minecraft.displayWidth;
			final int var171 = var151 - Mouse.getY() * var151 / Minecraft.displayHeight - 1;
			if (Minecraft.theWorld != null) {
				Minecraft.mcProfiler.startSection("level");
				final int var12 = Math.max(Minecraft.func_175610_ah(), 30);
				renderWorld(partialTicks, renderEndNanoTime + 1000000000 / var12);

				if (OpenGlHelper.shadersSupported) {
					mc.renderGlobal.renderEntityOutlineFramebuffer();

					if (theShaderGroup != null && field_175083_ad) {
						GlStateManager.matrixMode(5890);
						GlStateManager.pushMatrix();
						GlStateManager.loadIdentity();
						theShaderGroup.loadShaderGroup(partialTicks);
						GlStateManager.popMatrix();
					}

					mc.getFramebuffer().bindFramebuffer(true);
				}

				renderEndNanoTime = System.nanoTime();
				Minecraft.mcProfiler.endStartSection("gui");

				if (!Minecraft.gameSettings.hideGUI || Minecraft.currentScreen != null) {
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
					mc.ingameGUI.func_175180_a(partialTicks);

					if (Minecraft.gameSettings.ofShowFps && !Minecraft.gameSettings.showDebugInfo) {
						Config.drawFps();
					}

					if (Minecraft.gameSettings.showDebugInfo) {
						Lagometer.showLagometer(var131);
					}
				}

				Minecraft.mcProfiler.endSection();
			} else {
				GlStateManager.viewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
				GlStateManager.matrixMode(5889);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(5888);
				GlStateManager.loadIdentity();
				setupOverlayRendering();
				renderEndNanoTime = System.nanoTime();
			}
			GuiScreen screen;
			if ((screen = Minecraft.currentScreen) != null) {
				GlStateManager.clear(256);

				try {
					if (Reflector.ForgeHooksClient_drawScreen.exists()) {
						Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen,
								new Object[] { Minecraft.currentScreen, var161, var171, partialTicks });
					} else {
						screen.drawScreen(var161, var171, partialTicks);
					}
				} catch (final Throwable var121) {
					final CrashReport var10 = CrashReport.makeCrashReport(var121, "Rendering screen");
					final CrashReportCategory var11 = var10.makeCategory("Screen render details");
					var11.addCrashSectionCallable("Screen name", new Callable() {
						@Override
						public String call() {
							return Minecraft.currentScreen.getClass().getCanonicalName();
						}
					});
					var11.addCrashSectionCallable("Mouse location", new Callable() {
						@Override
						public String call() {
							return String.format("Scaled: (%d, %d). Absolute: (%d, %d)",
									new Object[] { var161, var171, Mouse.getX(), Mouse.getY() });
						}
					});
					var11.addCrashSectionCallable("Screen size", new Callable() {
						@Override
						public String call() {
							return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d",
									new Object[] { var131.getScaledWidth(), var131.getScaledHeight(),
											Minecraft.displayWidth, Minecraft.displayHeight, var131.getScaleFactor() });
						}
					});
					throw new ReportedException(var10);
				}
			}
		}

		frameFinish();
		waitForServerThread();
		Lagometer.updateLagometer();

		if (Minecraft.gameSettings.ofProfiler) {
			Minecraft.gameSettings.showDebugProfilerChart = true;
		}
	}

	public void func_152430_c(final float p_152430_1_) {
		setupOverlayRendering();
		mc.ingameGUI.func_180478_c(new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight));
	}

	private boolean func_175070_n() {
		if (!field_175073_D) {
			return false;
		} else {
			final Entity var1 = mc.func_175606_aa();
			boolean var2 = var1 instanceof EntityPlayer && !Minecraft.gameSettings.hideGUI;

			if (var2 && !((EntityPlayer) var1).capabilities.allowEdit) {
				final ItemStack var3 = ((EntityPlayer) var1).getCurrentEquippedItem();

				if (mc.objectMouseOver != null
						&& mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					final BlockPos var4 = mc.objectMouseOver.getBlockPos();
					final IBlockState state = Minecraft.theWorld.getBlockState(var4);
					final Block var5 = state.getBlock();

					if (Minecraft.playerController.func_178889_l() == WorldSettings.GameType.SPECTATOR) {
						var2 = ReflectorForge.blockHasTileEntity(state)
								&& Minecraft.theWorld.getTileEntity(var4) instanceof IInventory;
					} else {
						var2 = var3 != null && (var3.canDestroy(var5) || var3.canPlaceOn(var5));
					}
				}
			}

			return var2;
		}
	}

	private void func_175067_i(final float p_175067_1_) {
		if (Minecraft.gameSettings.showDebugInfo && !Minecraft.gameSettings.hideGUI
				&& !Minecraft.thePlayer.func_175140_cp() && !Minecraft.gameSettings.field_178879_v) {
			final Entity var2 = mc.func_175606_aa();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GL11.glLineWidth(1.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			orientCamera(p_175067_1_);
			GlStateManager.translate(0.0F, var2.getEyeHeight(), 0.0F);
			RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), -65536);
			RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D),
					-16776961);
			RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D),
					-16711936);
			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}
	}

	public void renderWorld(final float partialTicks, final long finishTimeNano) {
		updateLightmap(partialTicks);

		if (mc.func_175606_aa() == null) {
			mc.func_175607_a(Minecraft.thePlayer);
		}

		getMouseOver(partialTicks);

		if (Config.isShaders()) {
			Shaders.beginRender(mc, partialTicks, finishTimeNano);
		}

		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		Minecraft.mcProfiler.startSection("center");

		if (Minecraft.gameSettings.anaglyph) {
			anaglyphField = 0;
			GlStateManager.colorMask(false, true, true, false);
			func_175068_a(0, partialTicks, finishTimeNano);
			anaglyphField = 1;
			GlStateManager.colorMask(true, false, false, false);
			func_175068_a(1, partialTicks, finishTimeNano);
			GlStateManager.colorMask(true, true, true, false);
		} else {
			func_175068_a(2, partialTicks, finishTimeNano);
		}

		Minecraft.mcProfiler.endSection();
	}

	private void func_175068_a(final int pass, final float partialTicks, final long finishTimeNano) {
		final boolean isShaders = Config.isShaders();

		if (isShaders) {
			Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
		}

		final RenderGlobal var5 = mc.renderGlobal;
		final EffectRenderer var6 = Minecraft.effectRenderer;
		final boolean var7 = func_175070_n();
		GlStateManager.enableCull();
		Minecraft.mcProfiler.endStartSection("clear");

		if (isShaders) {
			Shaders.setViewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
		} else {
			GlStateManager.viewport(0, 0, Minecraft.displayWidth, Minecraft.displayHeight);
		}

		updateFogColor(partialTicks);
		GlStateManager.clear(16640);

		if (isShaders) {
			Shaders.clearRenderBuffer();
		}

		Minecraft.mcProfiler.endStartSection("camera");
		setupCameraTransform(partialTicks, pass);

		if (isShaders) {
			Shaders.setCamera(partialTicks);
		}

		ActiveRenderInfo.updateRenderInfo(Minecraft.thePlayer, Minecraft.gameSettings.thirdPersonView == 2);
		Minecraft.mcProfiler.endStartSection("frustum");
		ClippingHelperImpl.getInstance();
		Minecraft.mcProfiler.endStartSection("culling");
		final Frustrum var8 = new Frustrum();
		final Entity var9 = mc.func_175606_aa();
		final double var10 = var9.lastTickPosX + (var9.posX - var9.lastTickPosX) * partialTicks;
		final double var12 = var9.lastTickPosY + (var9.posY - var9.lastTickPosY) * partialTicks;
		final double var14 = var9.lastTickPosZ + (var9.posZ - var9.lastTickPosZ) * partialTicks;

		if (isShaders) {
			ShadersRender.setFrustrumPosition(var8, var10, var12, var14);
		} else {
			var8.setPosition(var10, var12, var14);
		}

		if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
			setupFog(-1, partialTicks);
			Minecraft.mcProfiler.endStartSection("sky");
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(getFOVModifier(partialTicks, true),
					(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, clipDistance);
			GlStateManager.matrixMode(5888);

			if (isShaders) {
				Shaders.beginSky();
			}

			var5.func_174976_a(partialTicks, pass);

			if (isShaders) {
				Shaders.endSky();
			}

			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(getFOVModifier(partialTicks, true),
					(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, clipDistance);
			GlStateManager.matrixMode(5888);
		} else {
			GlStateManager.disableBlend();
		}

		setupFog(0, partialTicks);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		if (var9.posY + var9.getEyeHeight() < 128.0D + Minecraft.gameSettings.ofCloudsHeight * 128.0F) {
			func_180437_a(var5, partialTicks, pass);
		}

		Minecraft.mcProfiler.endStartSection("prepareterrain");
		setupFog(0, partialTicks);
		Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		RenderHelper.disableStandardItemLighting();
		Minecraft.mcProfiler.endStartSection("terrain_setup");

		if (isShaders) {
			ShadersRender.setupTerrain(var5, var9, partialTicks, var8, field_175084_ae++,
					Minecraft.thePlayer.isSpectatorMode());
		} else {
			var5.func_174970_a(var9, partialTicks, var8, field_175084_ae++, Minecraft.thePlayer.isSpectatorMode());
		}

		if (pass == 0 || pass == 2) {
			Minecraft.mcProfiler.endStartSection("updatechunks");
			Lagometer.timerChunkUpload.start();

			if (isShaders) {
				ShadersRender.updateChunks(var5, finishTimeNano);
			} else {
				mc.renderGlobal.func_174967_a(finishTimeNano);
			}

			Lagometer.timerChunkUpload.end();
		}

		Minecraft.mcProfiler.endStartSection("terrain");
		Lagometer.timerTerrain.start();

		if (Minecraft.gameSettings.ofSmoothFps && pass > 0) {
			Minecraft.mcProfiler.endStartSection("finish");
			GL11.glFinish();
			Minecraft.mcProfiler.endStartSection("terrain");
		}

		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();

		if (isShaders) {
			ShadersRender.beginTerrainSolid();
		}

		var5.func_174977_a(EnumWorldBlockLayer.SOLID, partialTicks, pass, var9);
		GlStateManager.enableAlpha();

		if (isShaders) {
			ShadersRender.beginTerrainCutoutMipped();
		}

		var5.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, var9);
		Minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);

		if (isShaders) {
			ShadersRender.beginTerrainCutout();
		}

		var5.func_174977_a(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, var9);
		Minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();

		if (isShaders) {
			ShadersRender.endTerrain();
		}

		Lagometer.timerTerrain.end();
		GlStateManager.shadeModel(7424);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		EntityPlayer var16;

		if (!field_175078_W) {
			GlStateManager.matrixMode(5888);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			RenderHelper.enableStandardItemLighting();
			Minecraft.mcProfiler.endStartSection("entities");

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { 0 });
			}

			var5.renderEntities(var9, var8, partialTicks);

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { -1 });
			}

			RenderHelper.disableStandardItemLighting();
			func_175072_h();
			GlStateManager.matrixMode(5888);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();

			if (mc.objectMouseOver != null && var9.isInsideOfMaterial(Material.water) && var7) {
				var16 = (EntityPlayer) var9;
				GlStateManager.disableAlpha();
				Minecraft.mcProfiler.endStartSection("outline");

				if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists()
						|| !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight,
								new Object[] { var5, var16, mc.objectMouseOver, 0, var16.getHeldItem(), partialTicks }))
						&& !Minecraft.gameSettings.hideGUI) {
					var5.drawSelectionBox(var16, mc.objectMouseOver, 0, partialTicks);
				}
				GlStateManager.enableAlpha();
			}
		}

		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();

		if (var7 && mc.objectMouseOver != null && !var9.isInsideOfMaterial(Material.water)) {
			var16 = (EntityPlayer) var9;
			GlStateManager.disableAlpha();
			Minecraft.mcProfiler.endStartSection("outline");

			if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists()
					|| !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight,
							new Object[] { var5, var16, mc.objectMouseOver, 0, var16.getHeldItem(), partialTicks }))
					&& !Minecraft.gameSettings.hideGUI) {
				var5.drawSelectionBox(var16, mc.objectMouseOver, 0, partialTicks);
			}
			GlStateManager.enableAlpha();
		}

		if (!var5.damagedBlocks.isEmpty()) {
			Minecraft.mcProfiler.endStartSection("destroyProgress");
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
			var5.func_174981_a(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var9,
					partialTicks);
			GlStateManager.disableBlend();
		}

		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.disableBlend();

		if (!field_175078_W) {
			func_180436_i();
			Minecraft.mcProfiler.endStartSection("litParticles");

			if (isShaders) {
				Shaders.beginLitParticles();
			}

			var6.renderLitParticles(var9, partialTicks);
			RenderHelper.disableStandardItemLighting();
			setupFog(0, partialTicks);
			Minecraft.mcProfiler.endStartSection("particles");

			if (isShaders) {
				Shaders.beginParticles();
			}

			var6.renderParticles(var9, partialTicks);

			if (isShaders) {
				Shaders.endParticles();
			}

			func_175072_h();
		}

		GlStateManager.depthMask(false);
		GlStateManager.enableCull();
		Minecraft.mcProfiler.endStartSection("weather");

		if (isShaders) {
			Shaders.beginWeather();
		}

		renderRainSnow(partialTicks);

		if (isShaders) {
			Shaders.endWeather();
		}

		GlStateManager.depthMask(true);
		var5.func_180449_a(var9, partialTicks);

		if (isShaders) {
			ShadersRender.renderHand0(this, partialTicks, pass);
			Shaders.preWater();
		}

		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		setupFog(0, partialTicks);
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		if (Config.isTranslucentBlocksFancy()) {
			Minecraft.mcProfiler.endStartSection("translucent");
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

			if (isShaders) {
				Shaders.beginWater();
			}

			var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);

			if (isShaders) {
				Shaders.endWater();
			}

			GlStateManager.disableBlend();
		} else {
			Minecraft.mcProfiler.endStartSection("translucent");

			if (isShaders) {
				Shaders.beginWater();
			}

			var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);

			if (isShaders) {
				Shaders.endWater();
			}
		}

		if (Reflector.ForgeHooksClient_setRenderPass.exists() && !field_175078_W) {
			RenderHelper.enableStandardItemLighting();
			Minecraft.mcProfiler.endStartSection("entities");
			Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { 1 });
			mc.renderGlobal.renderEntities(var9, var8, partialTicks);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { -1 });
			RenderHelper.disableStandardItemLighting();
		}

		GlStateManager.shadeModel(7424);
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableFog();

		if (var9.posY + var9.getEyeHeight() >= 128.0D + Minecraft.gameSettings.ofCloudsHeight * 128.0F) {
			Minecraft.mcProfiler.endStartSection("aboveClouds");
			func_180437_a(var5, partialTicks, pass);
		}

		if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
			Minecraft.mcProfiler.endStartSection("forge_render_last");
			Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { var5, partialTicks });
		}

		final Event3D evnt = new Event3D(partialTicks);
		EventManager.call(evnt);
		Minecraft.mcProfiler.endStartSection("hand");

		// TODO: ANTILAG

		if (Configs.antiLag) {
			if (System.currentTimeMillis() - mc.lastTickMS > 50 && mc.thePlayer != null
					&& mc.thePlayer.sendQueue.doneLoadingTerrain) {
				mc.lagTicks++;
				if (mc.lagTicks > 1) {
					mc.antiLag = true;
					mc.lagTicksTicker = 0;
				}
			} else {
				mc.lagTicksTicker++;
				if (mc.antiLag) {
					if (mc.lagTicksTicker > 200)
						mc.antiLag = false;
				}
				if (mc.lagTicks > 0) {
					mc.lagTicks--;
				}
			}
		}
		mc.lastTickMS = System.currentTimeMillis();

		Client.onRender();
		final boolean handRendered = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand,
				new Object[] { mc.renderGlobal, partialTicks, pass });

		if (!handRendered && field_175074_C && !Shaders.isShadowPass) {
			if (isShaders) {
				ShadersRender.renderHand1(this, partialTicks, pass);
				Shaders.renderCompositeFinal();
			}

			GlStateManager.clear(256);

			if (isShaders) {
				ShadersRender.renderFPOverlay(this, partialTicks, pass);
			} else {
				renderHand(partialTicks, pass);
			}

			func_175067_i(partialTicks);
		}

		if (isShaders) {
			Shaders.endRender();
		}
	}

	private void func_180437_a(final RenderGlobal p_180437_1_, final float partialTicks, final int pass) {
		if (Minecraft.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff()
				&& Shaders.shouldRenderClouds(Minecraft.gameSettings)) {
			Minecraft.mcProfiler.endStartSection("clouds");
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(getFOVModifier(partialTicks, true),
					(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, clipDistance * 4.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.pushMatrix();
			setupFog(0, partialTicks);
			p_180437_1_.func_180447_b(partialTicks, pass);
			GlStateManager.disableFog();
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(getFOVModifier(partialTicks, true),
					(float) Minecraft.displayWidth / (float) Minecraft.displayHeight, 0.05F, clipDistance);
			GlStateManager.matrixMode(5888);
		}
	}

	private void addRainParticles() {
		float var1 = Minecraft.theWorld.getRainStrength(1.0F);

		if (!Config.isRainFancy()) {
			var1 /= 2.0F;
		}

		if (var1 != 0.0F && Config.isRainSplash()) {
			random.setSeed(rendererUpdateCount * 312987231L);
			final Entity var2 = mc.func_175606_aa();
			final WorldClient var3 = Minecraft.theWorld;
			final BlockPos var4 = new BlockPos(var2);
			final byte var5 = 10;
			double var6 = 0.0D;
			double var8 = 0.0D;
			double var10 = 0.0D;
			int var12 = 0;
			int var13 = (int) (100.0F * var1 * var1);

			if (Minecraft.gameSettings.particleSetting == 1) {
				var13 >>= 1;
			} else if (Minecraft.gameSettings.particleSetting == 2) {
				var13 = 0;
			}

			for (int var14 = 0; var14 < var13; ++var14) {
				final BlockPos var15 = var3.func_175725_q(var4.add(random.nextInt(var5) - random.nextInt(var5), 0,
						random.nextInt(var5) - random.nextInt(var5)));
				final BiomeGenBase var16 = var3.getBiomeGenForCoords(var15);
				final BlockPos var17 = var15.offsetDown();
				final Block var18 = var3.getBlockState(var17).getBlock();

				if (var15.getY() <= var4.getY() + var5 && var15.getY() >= var4.getY() - var5
						&& var16.canSpawnLightningBolt() && var16.func_180626_a(var15) >= 0.15F) {
					final float var19 = random.nextFloat();
					final float var20 = random.nextFloat();

					if (var18.getMaterial() == Material.lava) {
						Minecraft.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var15.getX() + var19,
								var15.getY() + 0.1F - var18.getBlockBoundsMinY(), var15.getZ() + var20, 0.0D, 0.0D,
								0.0D, new int[0]);
					} else if (var18.getMaterial() != Material.air) {
						var18.setBlockBoundsBasedOnState(var3, var17);
						++var12;

						if (random.nextInt(var12) == 0) {
							var6 = var17.getX() + var19;
							var8 = var17.getY() + 0.1F + var18.getBlockBoundsMaxY() - 1.0D;
							var10 = var17.getZ() + var20;
						}

						Minecraft.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, var17.getX() + var19,
								var17.getY() + 0.1F + var18.getBlockBoundsMaxY(), var17.getZ() + var20, 0.0D, 0.0D,
								0.0D, new int[0]);
					}
				}
			}

			if (var12 > 0 && random.nextInt(3) < rainSoundCounter++) {
				rainSoundCounter = 0;

				if (var8 > var4.getY() + 1 && var3.func_175725_q(var4).getY() > MathHelper.floor_float(var4.getY())) {
					Minecraft.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.1F, 0.5F, false);
				} else {
					Minecraft.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.2F, 1.0F, false);
				}
			}
		}
	}

	/**
	 * Render rain and snow
	 */
	protected void renderRainSnow(final float partialTicks) {
		if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
			final WorldProvider var2 = Minecraft.theWorld.provider;
			final Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);

			if (var3 != null) {
				Reflector.callVoid(var3, Reflector.IRenderHandler_render,
						new Object[] { partialTicks, Minecraft.theWorld, mc });
				return;
			}
		}

		final float var421 = Minecraft.theWorld.getRainStrength(partialTicks);

		if (var421 > 0.0F) {
			if (Config.isRainOff()) {
				return;
			}

			func_180436_i();
			final Entity var431 = mc.func_175606_aa();
			final WorldClient var4 = Minecraft.theWorld;
			final int var5 = MathHelper.floor_double(var431.posX);
			final int var6 = MathHelper.floor_double(var431.posY);
			final int var7 = MathHelper.floor_double(var431.posZ);
			final Tessellator var8 = Tessellator.getInstance();
			final WorldRenderer var9 = var8.getWorldRenderer();
			GlStateManager.disableCull();
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			final double var10 = var431.lastTickPosX + (var431.posX - var431.lastTickPosX) * partialTicks;
			final double var12 = var431.lastTickPosY + (var431.posY - var431.lastTickPosY) * partialTicks;
			final double var14 = var431.lastTickPosZ + (var431.posZ - var431.lastTickPosZ) * partialTicks;
			final int var16 = MathHelper.floor_double(var12);
			byte var17 = 5;

			if (Config.isRainFancy()) {
				var17 = 10;
			}

			byte var18 = -1;
			final float var19 = rendererUpdateCount + partialTicks;

			if (Config.isRainFancy()) {
				var17 = 10;
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			for (int var20 = var7 - var17; var20 <= var7 + var17; ++var20) {
				for (int var21 = var5 - var17; var21 <= var5 + var17; ++var21) {
					final int var22 = (var20 - var7 + 16) * 32 + var21 - var5 + 16;
					final float var23 = field_175076_N[var22] * 0.5F;
					final float var24 = field_175077_O[var22] * 0.5F;
					final BlockPos var25 = new BlockPos(var21, 0, var20);
					final BiomeGenBase var26 = var4.getBiomeGenForCoords(var25);

					if (var26.canSpawnLightningBolt() || var26.getEnableSnow()) {
						final int var27 = var4.func_175725_q(var25).getY();
						int var28 = var6 - var17;
						int var29 = var6 + var17;

						if (var28 < var27) {
							var28 = var27;
						}

						if (var29 < var27) {
							var29 = var27;
						}

						final float var30 = 1.0F;
						int var31 = var27;

						if (var27 < var16) {
							var31 = var16;
						}

						if (var28 != var29) {
							random.setSeed(
									var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
							final float var32 = var26.func_180626_a(new BlockPos(var21, var28, var20));
							float var33;
							double var36;

							if (var4.getWorldChunkManager().getTemperatureAtHeight(var32, var27) >= 0.15F) {
								if (var18 != 0) {
									if (var18 >= 0) {
										var8.draw();
									}

									var18 = 0;
									Minecraft.getTextureManager().bindTexture(locationRainPng);
									var9.startDrawingQuads();
								}

								var33 = ((rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971
										+ var20 * var20 * 418711 + var20 * 13761 & 31) + partialTicks) / 32.0F
										* (3.0F + random.nextFloat());
								final double var42 = var21 + 0.5F - var431.posX;
								var36 = var20 + 0.5F - var431.posZ;
								final float var43 = MathHelper.sqrt_double(var42 * var42 + var36 * var36) / var17;
								final float var39 = 1.0F;
								var9.func_178963_b(var4.getCombinedLight(new BlockPos(var21, var31, var20), 0));
								var9.func_178960_a(var39, var39, var39,
										((1.0F - var43 * var43) * 0.5F + 0.5F) * var421);
								var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
								var9.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D, 0.0F * var30,
										var28 * var30 / 4.0F + var33 * var30);
								var9.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D, 1.0F * var30,
										var28 * var30 / 4.0F + var33 * var30);
								var9.addVertexWithUV(var21 + var23 + 0.5D, var29, var20 + var24 + 0.5D, 1.0F * var30,
										var29 * var30 / 4.0F + var33 * var30);
								var9.addVertexWithUV(var21 - var23 + 0.5D, var29, var20 - var24 + 0.5D, 0.0F * var30,
										var29 * var30 / 4.0F + var33 * var30);
								var9.setTranslation(0.0D, 0.0D, 0.0D);
							} else {
								if (var18 != 1) {
									if (var18 >= 0) {
										var8.draw();
									}

									var18 = 1;
									Minecraft.getTextureManager().bindTexture(locationSnowPng);
									var9.startDrawingQuads();
								}

								var33 = ((rendererUpdateCount & 511) + partialTicks) / 512.0F;
								final float var44 = random.nextFloat() + var19 * 0.01F * (float) random.nextGaussian();
								final float var35 = random.nextFloat() + var19 * (float) random.nextGaussian() * 0.001F;
								var36 = var21 + 0.5F - var431.posX;
								final double var45 = var20 + 0.5F - var431.posZ;
								final float var40 = MathHelper.sqrt_double(var36 * var36 + var45 * var45) / var17;
								final float var41 = 1.0F;
								var9.func_178963_b(
										(var4.getCombinedLight(new BlockPos(var21, var31, var20), 0) * 3 + 15728880)
												/ 4);
								var9.func_178960_a(var41, var41, var41,
										((1.0F - var40 * var40) * 0.3F + 0.5F) * var421);
								var9.setTranslation(-var10 * 1.0D, -var12 * 1.0D, -var14 * 1.0D);
								var9.addVertexWithUV(var21 - var23 + 0.5D, var28, var20 - var24 + 0.5D,
										0.0F * var30 + var44, var28 * var30 / 4.0F + var33 * var30 + var35);
								var9.addVertexWithUV(var21 + var23 + 0.5D, var28, var20 + var24 + 0.5D,
										1.0F * var30 + var44, var28 * var30 / 4.0F + var33 * var30 + var35);
								var9.addVertexWithUV(var21 + var23 + 0.5D, var29, var20 + var24 + 0.5D,
										1.0F * var30 + var44, var29 * var30 / 4.0F + var33 * var30 + var35);
								var9.addVertexWithUV(var21 - var23 + 0.5D, var29, var20 - var24 + 0.5D,
										0.0F * var30 + var44, var29 * var30 / 4.0F + var33 * var30 + var35);
								var9.setTranslation(0.0D, 0.0D, 0.0D);
							}
						}
					}
				}
			}

			if (var18 >= 0) {
				var8.draw();
			}

			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			func_175072_h();
		}
	}

	/**
	 * Setup orthogonal projection for rendering GUI screen overlays
	 */
	public void setupOverlayRendering() {
		final ScaledResolution var1 = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
		GlStateManager.clear(256);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
	}

	/**
	 * calculates fog and calls glClearColor
	 */
	public void updateFogColor(final float partialTicks) {
		final WorldClient var2 = Minecraft.theWorld;
		final Entity var3 = mc.func_175606_aa();
		float var4 = 0.25F + 0.75F * Minecraft.gameSettings.renderDistanceChunks / 32.0F;
		var4 = 1.0F - (float) Math.pow(var4, 0.25D);
		Vec3 var5 = var2.getSkyColor(mc.func_175606_aa(), partialTicks);
		var5 = CustomColors.getWorldSkyColor(var5, var2, mc.func_175606_aa(), partialTicks);
		final float var6 = (float) var5.xCoord;
		final float var7 = (float) var5.yCoord;
		final float var8 = (float) var5.zCoord;
		Vec3 var9 = var2.getFogColor(partialTicks);
		var9 = CustomColors.getWorldFogColor(var9, var2, mc.func_175606_aa(), partialTicks);
		field_175080_Q = (float) var9.xCoord;
		field_175082_R = (float) var9.yCoord;
		field_175081_S = (float) var9.zCoord;
		float var13;

		if (Minecraft.gameSettings.renderDistanceChunks >= 4) {
			final double var19 = -1.0D;
			final Vec3 var20 = MathHelper.sin(var2.getCelestialAngleRadians(partialTicks)) > 0.0F
					? new Vec3(var19, 0.0D, 0.0D)
					: new Vec3(1.0D, 0.0D, 0.0D);
			var13 = (float) var3.getLook(partialTicks).dotProduct(var20);

			if (var13 < 0.0F) {
				var13 = 0.0F;
			}

			if (var13 > 0.0F) {
				final float[] var21 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(partialTicks),
						partialTicks);

				if (var21 != null) {
					var13 *= var21[3];
					field_175080_Q = field_175080_Q * (1.0F - var13) + var21[0] * var13;
					field_175082_R = field_175082_R * (1.0F - var13) + var21[1] * var13;
					field_175081_S = field_175081_S * (1.0F - var13) + var21[2] * var13;
				}
			}
		}

		field_175080_Q += (var6 - field_175080_Q) * var4;
		field_175082_R += (var7 - field_175082_R) * var4;
		field_175081_S += (var8 - field_175081_S) * var4;
		final float var191 = var2.getRainStrength(partialTicks);
		float var11;
		float var201;

		if (var191 > 0.0F) {
			var11 = 1.0F - var191 * 0.5F;
			var201 = 1.0F - var191 * 0.4F;
			field_175080_Q *= var11;
			field_175082_R *= var11;
			field_175081_S *= var201;
		}

		var11 = var2.getWeightedThunderStrength(partialTicks);

		if (var11 > 0.0F) {
			var201 = 1.0F - var11 * 0.5F;
			field_175080_Q *= var201;
			field_175082_R *= var201;
			field_175081_S *= var201;
		}

		final Block var211 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);
		Vec3 fogYFactor;

		if (cloudFog) {
			fogYFactor = var2.getCloudColour(partialTicks);
			field_175080_Q = (float) fogYFactor.xCoord;
			field_175082_R = (float) fogYFactor.yCoord;
			field_175081_S = (float) fogYFactor.zCoord;
		} else if (var211.getMaterial() == Material.water) {
			var13 = EnchantmentHelper.func_180319_a(var3) * 0.2F;

			if (var3 instanceof EntityLivingBase && ((EntityLivingBase) var3).isPotionActive(Potion.waterBreathing)) {
				var13 = var13 * 0.3F + 0.6F;
			}

			field_175080_Q = 0.02F + var13;
			field_175082_R = 0.02F + var13;
			field_175081_S = 0.2F + var13;
			fogYFactor = CustomColors.getUnderwaterColor(Minecraft.theWorld, mc.func_175606_aa().posX,
					mc.func_175606_aa().posY + 1.0D, mc.func_175606_aa().posZ);

			if (fogYFactor != null) {
				field_175080_Q = (float) fogYFactor.xCoord;
				field_175082_R = (float) fogYFactor.yCoord;
				field_175081_S = (float) fogYFactor.zCoord;
			}
		} else if (var211.getMaterial() == Material.lava) {
			field_175080_Q = 0.6F;
			field_175082_R = 0.1F;
			field_175081_S = 0.0F;
		}

		var13 = fogColor2 + (fogColor1 - fogColor2) * partialTicks;
		field_175080_Q *= var13;
		field_175082_R *= var13;
		field_175081_S *= var13;
		final double fogYFactor1 = var2.provider.getVoidFogYFactor();
		double var23 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * partialTicks) * fogYFactor1;

		if (var3 instanceof EntityLivingBase && ((EntityLivingBase) var3).isPotionActive(Potion.blindness)) {
			if (AntiBlind.mod.isToggled()) {
				return;
			}
			final int var24 = ((EntityLivingBase) var3).getActivePotionEffect(Potion.blindness).getDuration();

			if (var24 < 20) {
				var23 *= 1.0F - var24 / 20.0F;
			} else {
				var23 = 0.0D;
			}
		}

		if (var23 < 1.0D) {
			if (var23 < 0.0D) {
				var23 = 0.0D;
			}

			var23 *= var23;
			field_175080_Q = (float) (field_175080_Q * var23);
			field_175082_R = (float) (field_175082_R * var23);
			field_175081_S = (float) (field_175081_S * var23);
		}

		float var241;

		if (bossColorModifier > 0.0F) {
			var241 = bossColorModifierPrev + (bossColorModifier - bossColorModifierPrev) * partialTicks;
			field_175080_Q = field_175080_Q * (1.0F - var241) + field_175080_Q * 0.7F * var241;
			field_175082_R = field_175082_R * (1.0F - var241) + field_175082_R * 0.6F * var241;
			field_175081_S = field_175081_S * (1.0F - var241) + field_175081_S * 0.6F * var241;
		}

		float var17;

		if (var3 instanceof EntityLivingBase && ((EntityLivingBase) var3).isPotionActive(Potion.nightVision)) {
			var241 = func_180438_a((EntityLivingBase) var3, partialTicks);
			var17 = 1.0F / field_175080_Q;

			if (var17 > 1.0F / field_175082_R) {
				var17 = 1.0F / field_175082_R;
			}

			if (var17 > 1.0F / field_175081_S) {
				var17 = 1.0F / field_175081_S;
			}

			field_175080_Q = field_175080_Q * (1.0F - var241) + field_175080_Q * var17 * var241;
			field_175082_R = field_175082_R * (1.0F - var241) + field_175082_R * var17 * var241;
			field_175081_S = field_175081_S * (1.0F - var241) + field_175081_S * var17 * var241;
		}

		if (Minecraft.gameSettings.anaglyph) {
			var241 = (field_175080_Q * 30.0F + field_175082_R * 59.0F + field_175081_S * 11.0F) / 100.0F;
			var17 = (field_175080_Q * 30.0F + field_175082_R * 70.0F) / 100.0F;
			final float event = (field_175080_Q * 30.0F + field_175081_S * 70.0F) / 100.0F;
			field_175080_Q = var241;
			field_175082_R = var17;
			field_175081_S = event;
		}

		if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
			final Object event1 = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor,
					new Object[] { this, var3, var211, partialTicks, field_175080_Q, field_175082_R, field_175081_S });
			Reflector.postForgeBusEvent(event1);
			field_175080_Q = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_red,
					field_175080_Q);
			field_175082_R = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_green,
					field_175082_R);
			field_175081_S = Reflector.getFieldValueFloat(event1, Reflector.EntityViewRenderEvent_FogColors_blue,
					field_175081_S);
		}

		Shaders.setClearColor(field_175080_Q, field_175082_R, field_175081_S, 0.0F);
	}

	/**
	 * Sets up the fog to be rendered. If the arg passed in is -1 the fog starts at
	 * 0 and goes to 80% of far plane distance and is used for sky rendering.
	 */
	private void setupFog(final int p_78468_1_, final float partialTicks) {
		final Entity var3 = mc.func_175606_aa();
		fogStandard = false;

		if (var3 instanceof EntityPlayer) {}

		GL11.glFog(GL11.GL_FOG_COLOR, setFogColorBuffer(field_175080_Q, field_175082_R, field_175081_S, 1.0F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		final Block var5 = ActiveRenderInfo.func_180786_a(Minecraft.theWorld, var3, partialTicks);
		float forgeFogDensity = -1.0F;

		if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
			forgeFogDensity = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity,
					new Object[] { this, var3, var5, partialTicks, 0.1F });
		}

		if (forgeFogDensity >= 0.0F) {
			GlStateManager.setFogDensity(forgeFogDensity);
		} else {
			float var6;

			if (var3 instanceof EntityLivingBase && ((EntityLivingBase) var3).isPotionActive(Potion.blindness)) {
				if (AntiBlind.mod.isToggled()) {
					return;
				}
				var6 = 5.0F;
				final int var7 = ((EntityLivingBase) var3).getActivePotionEffect(Potion.blindness).getDuration();

				if (var7 < 20) {
					var6 = 5.0F + (farPlaneDistance - 5.0F) * (1.0F - var7 / 20.0F);
				}

				if (Config.isShaders()) {
					Shaders.setFog(9729);
				} else {
					GlStateManager.setFog(9729);
				}

				if (p_78468_1_ == -1) {
					GlStateManager.setFogStart(0.0F);
					GlStateManager.setFogEnd(var6 * 0.8F);
				} else {
					GlStateManager.setFogStart(var6 * 0.25F);
					GlStateManager.setFogEnd(var6);
				}

				if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
					GL11.glFogi(34138, 34139);
				}
			} else if (cloudFog) {
				if (Config.isShaders()) {
					Shaders.setFog(2048);
				} else {
					GlStateManager.setFog(2048);
				}

				GlStateManager.setFogDensity(0.1F);
			} else if (var5.getMaterial() == Material.water) {
				if (Config.isShaders()) {
					Shaders.setFog(2048);
				} else {
					GlStateManager.setFog(2048);
				}

				if (var3 instanceof EntityLivingBase
						&& ((EntityLivingBase) var3).isPotionActive(Potion.waterBreathing)) {
					GlStateManager.setFogDensity(0.01F);
				} else {
					GlStateManager.setFogDensity(0.1F - EnchantmentHelper.func_180319_a(var3) * 0.03F);
				}

				if (Config.isClearWater()) {
					GlStateManager.setFogDensity(0.02F);
				}
			} else if (var5.getMaterial() == Material.lava) {
				if (Config.isShaders()) {
					Shaders.setFog(2048);
				} else {
					GlStateManager.setFog(2048);
				}

				GlStateManager.setFogDensity(2.0F);
			} else {
				var6 = farPlaneDistance;
				fogStandard = true;

				if (Config.isShaders()) {
					Shaders.setFog(9729);
				} else {
					GlStateManager.setFog(9729);
				}

				if (p_78468_1_ == -1) {
					GlStateManager.setFogStart(0.0F);
					GlStateManager.setFogEnd(var6);
				} else {
					GlStateManager.setFogStart(var6 * Config.getFogStart());
					GlStateManager.setFogEnd(var6);
				}

				if (GLContext.getCapabilities().GL_NV_fog_distance) {
					if (Config.isFogFancy()) {
						GL11.glFogi(34138, 34139);
					}

					if (Config.isFogFast()) {
						GL11.glFogi(34138, 34140);
					}
				}

				if (Minecraft.theWorld.provider.doesXZShowFog((int) var3.posX, (int) var3.posZ)) {
					GlStateManager.setFogStart(var6 * 0.05F);
					GlStateManager.setFogEnd(var6);
				}

				if (Reflector.ForgeHooksClient_onFogRender.exists()) {
					Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender,
							new Object[] { this, var3, var5, partialTicks, p_78468_1_, var6 });
				}
			}
		}

		GlStateManager.enableColorMaterial();
		GlStateManager.enableFog();
		GlStateManager.colorMaterial(1028, 4608);
	}

	/**
	 * Update and return fogColorBuffer with the RGBA values passed as arguments
	 */
	private FloatBuffer setFogColorBuffer(final float p_78469_1_, final float p_78469_2_, final float p_78469_3_,
			final float p_78469_4_) {
		if (Config.isShaders()) {
			Shaders.setFogColor(p_78469_1_, p_78469_2_, p_78469_3_);
		}

		fogColorBuffer.clear();
		fogColorBuffer.put(p_78469_1_).put(p_78469_2_).put(p_78469_3_).put(p_78469_4_);
		fogColorBuffer.flip();
		return fogColorBuffer;
	}

	public MapItemRenderer getMapItemRenderer() {
		return theMapItemRenderer;
	}

	private void waitForServerThread() {
		if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
			if (mc.isIntegratedServerRunning()) {
				final IntegratedServer srv = mc.getIntegratedServer();

				if (srv != null) {
					final boolean paused = mc.isGamePaused();

					if (!paused && !(Minecraft.currentScreen instanceof GuiDownloadTerrain)) {
						if (serverWaitTime > 0) {
							Lagometer.timerServer.start();
							Config.sleep(serverWaitTime);
							Lagometer.timerServer.end();
						}

						final long timeNow = System.nanoTime() / 1000000L;

						if (lastServerTime != 0L && lastServerTicks != 0) {
							long timeDiff = timeNow - lastServerTime;

							if (timeDiff < 0L) {
								lastServerTime = timeNow;
								timeDiff = 0L;
							}

							if (timeDiff >= 50L) {
								lastServerTime = timeNow;
								final int ticks = srv.getTickCounter();
								int tickDiff = ticks - lastServerTicks;

								if (tickDiff < 0) {
									lastServerTicks = ticks;
									tickDiff = 0;
								}

								if (tickDiff < 1 && serverWaitTime < 100) {
									serverWaitTime += 2;
								}

								if (tickDiff > 1 && serverWaitTime > 0) {
									--serverWaitTime;
								}

								lastServerTicks = ticks;
							}
						} else {
							lastServerTime = timeNow;
							lastServerTicks = srv.getTickCounter();
						}
					} else {
						if (Minecraft.currentScreen instanceof GuiDownloadTerrain) {
							Config.sleep(20L);
						}

						lastServerTime = 0L;
						lastServerTicks = 0;
					}
				}
			}
		} else {
			lastServerTime = 0L;
			lastServerTicks = 0;
		}
	}

	private void frameInit() {
		if (!initialized) {
			TextureUtils.registerResourceListener();

			if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
				Config.setNotify64BitJava(true);
			}

			initialized = true;
		}

		Config.checkDisplayMode();
		final WorldClient world = Minecraft.theWorld;

		if (world != null) {
			if (Config.getNewRelease() != null) {
				final String msg = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
				final String fullNewVer = msg + " " + Config.getNewRelease();
				final ChatComponentText msg1 = new ChatComponentText(
						I18n.format("of.message.newVersion", new Object[] { fullNewVer }));
				mc.ingameGUI.getChatGUI().printChatMessage(msg1);
				Config.setNewRelease((String) null);
			}

			if (Config.isNotify64BitJava()) {
				Config.setNotify64BitJava(false);
				final ChatComponentText msg2 = new ChatComponentText(
						I18n.format("of.message.java64Bit", new Object[0]));
				mc.ingameGUI.getChatGUI().printChatMessage(msg2);
			}
		}

		if (Minecraft.currentScreen instanceof GuiMainMenu) {
			updateMainMenu((GuiMainMenu) Minecraft.currentScreen);
		}

		if (updatedWorld != world) {
			RandomMobs.worldChanged(updatedWorld, world);
			Config.updateThreadPriorities();
			lastServerTime = 0L;
			lastServerTicks = 0;
			updatedWorld = world;
		}

		if (!setFxaaShader(Shaders.configAntialiasingLevel)) {
			Shaders.configAntialiasingLevel = 0;
		}
	}

	private void frameFinish() {
		if (Minecraft.theWorld != null) {
			final long now = System.currentTimeMillis();

			if (now > lastErrorCheckTimeMs + 10000L) {
				lastErrorCheckTimeMs = now;
				final int err = GL11.glGetError();

				if (err != 0) {
					final String text = GLU.gluErrorString(err);
					final ChatComponentText msg = new ChatComponentText(
							I18n.format("of.message.openglError", new Object[] { err, text }));
					mc.ingameGUI.getChatGUI().printChatMessage(msg);
				}
			}
		}
	}

	private void updateMainMenu(final GuiMainMenu mainGui) {
		try {
			String e = null;
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			final int day = calendar.get(5);
			final int month = calendar.get(2) + 1;

			if (day == 8 && month == 4) {
				e = "Happy birthday, OptiFine!";
			}

			if (day == 14 && month == 8) {
				e = "Happy birthday, sp614x!";
			}

			if (e == null) {
				return;
			}

			final Field[] fs = GuiMainMenu.class.getDeclaredFields();

			for (final Field element : fs) {
				if (element.getType() == String.class) {
					element.setAccessible(true);
					element.set(mainGui, e);
					break;
				}
			}
		} catch (final Throwable var8) {}
	}

	public boolean setFxaaShader(final int fxaaLevel) {
		if (!OpenGlHelper.isFramebufferEnabled()) {
			return false;
		} else if (theShaderGroup != null && theShaderGroup != fxaaShaders[2] && theShaderGroup != fxaaShaders[4]) {
			return true;
		} else if (fxaaLevel != 2 && fxaaLevel != 4) {
			if (theShaderGroup == null) {
				return true;
			} else {
				theShaderGroup.deleteShaderGroup();
				theShaderGroup = null;
				return true;
			}
		} else if (theShaderGroup != null && theShaderGroup == fxaaShaders[fxaaLevel]) {
			return true;
		} else if (Minecraft.theWorld == null) {
			return true;
		} else {
			loadShader(new ResourceLocation("shaders/post/fxaa_of_" + fxaaLevel + "x.json"));
			fxaaShaders[fxaaLevel] = theShaderGroup;
			return field_175083_ad;
		}
	}
}
