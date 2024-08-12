package net.minecraft.client.renderer;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.BlockOverlay;
import me.EaZy.client.modules.ESP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import optifine.ChunkUtils;
import optifine.CloudRenderer;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.RenderInfoLazy;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {

	public static final int EaZy = 807;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation(
			"textures/environment/moon_phases.png");
	private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
	private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation field_175006_g = new ResourceLocation("textures/misc/forcefield.png");

	/** A reference to the Minecraft object. */
	public final Minecraft mc;

	/** The RenderEngine instance used by RenderGlobal */
	private final TextureManager renderEngine;
	private final RenderManager renderManager;
	private WorldClient theWorld;
	private Set field_175009_l = Sets.newLinkedHashSet();

	/** List of OpenGL lists for the current render pass */
	private List glRenderLists = Lists.newArrayListWithCapacity(69696);
	private ViewFrustum field_175008_n;

	/** The star GL Call list */
	private int starGLCallList = -1;

	/** OpenGL sky list */
	private int glSkyList = -1;

	/** OpenGL sky list 2 */
	private int glSkyList2 = -1;
	private final VertexFormat field_175014_r;
	private VertexBuffer field_175013_s;
	private VertexBuffer field_175012_t;
	private VertexBuffer field_175011_u;

	/**
	 * counts the cloud render updates. Used with mod to stagger some updates
	 */
	private int cloudTickCounter;

	/**
	 * Stores blocks currently being broken. Key is entity ID of the thing doing
	 * the breaking. Value is a DestroyBlockProgress
	 */
	public final Map damagedBlocks = Maps.newHashMap();

	/** Currently playing sounds. Type: HashMap<ChunkCoordinates, ISound> */
	private final Map mapSoundPositions = Maps.newHashMap();
	private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
	private Framebuffer entityOutlineFramebuffer;
	private ShaderGroup entityOutlineShader;
	private double field_174992_B = Double.MIN_VALUE;
	private double field_174993_C = Double.MIN_VALUE;
	private double field_174987_D = Double.MIN_VALUE;
	private int field_174988_E = Integer.MIN_VALUE;
	private int field_174989_F = Integer.MIN_VALUE;
	private int field_174990_G = Integer.MIN_VALUE;
	private double field_174997_H = Double.MIN_VALUE;
	private double field_174998_I = Double.MIN_VALUE;
	private double field_174999_J = Double.MIN_VALUE;
	private double field_175000_K = Double.MIN_VALUE;
	private double field_174994_L = Double.MIN_VALUE;
	private final ChunkRenderDispatcher field_174995_M = new ChunkRenderDispatcher();
	private ChunkRenderContainer field_174996_N;
	private int renderDistanceChunks = -1;

	/** Render entities startup counter (init value=2) */
	private int renderEntitiesStartupCounter = 2;

	/** Count entities total */
	private int countEntitiesTotal;

	/** Count entities rendered */
	private int countEntitiesRendered;

	/** Count entities hidden */
	private int countEntitiesHidden;
	private boolean field_175002_T = false;
	private ClippingHelper field_175001_U;
	private final Vector4f[] field_175004_V = new Vector4f[8];
	private final Vector3d field_175003_W = new Vector3d();
	private boolean field_175005_X = false;
	IRenderChunkFactory field_175007_a;
	private double prevRenderSortX;
	private double prevRenderSortY;
	private double prevRenderSortZ;
	public boolean displayListEntitiesDirty = true;
	private final CloudRenderer cloudRenderer;
	public Entity renderedEntity;
	public Set chunksToResortTransparency = new LinkedHashSet();
	public Set chunksToUpdateForced = new LinkedHashSet();
	private final Deque visibilityDeque = new ArrayDeque();
	private List renderInfosEntities = new ArrayList(1024);
	private List renderInfosTileEntities = new ArrayList(1024);
	private final List renderInfosNormal = new ArrayList(1024);
	private final List renderInfosEntitiesNormal = new ArrayList(1024);
	private final List renderInfosTileEntitiesNormal = new ArrayList(1024);
	private final List renderInfosShadow = new ArrayList(1024);
	private final List renderInfosEntitiesShadow = new ArrayList(1024);
	private final List renderInfosTileEntitiesShadow = new ArrayList(1024);
	private int renderDistance = 0;
	private int renderDistanceSq = 0;
	private static final Set SET_ALL_FACINGS = Collections
			.unmodifiableSet(new HashSet(Arrays.asList(EnumFacing.VALUES)));
	private int countTileEntitiesRendered;

	public RenderGlobal(final Minecraft mcIn) {
		cloudRenderer = new CloudRenderer(mcIn);
		mc = mcIn;
		renderManager = Minecraft.getRenderManager();
		renderEngine = Minecraft.getTextureManager();
		renderEngine.bindTexture(field_175006_g);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GlStateManager.func_179144_i(0);
		func_174971_n();
		field_175005_X = OpenGlHelper.func_176075_f();

		if (field_175005_X) {
			field_174996_N = new VboRenderList();
			field_175007_a = new VboChunkFactory();
		} else {
			field_174996_N = new RenderList();
			field_175007_a = new ListChunkFactory();
		}

		field_175014_r = new VertexFormat();
		field_175014_r.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT,
				VertexFormatElement.EnumUseage.POSITION, 3));
		func_174963_q();
		func_174980_p();
		func_174964_o();
	}

	@Override
	public void onResourceManagerReload(final IResourceManager resourceManager) {
		func_174971_n();
	}

	private void func_174971_n() {
		final TextureMap var1 = mc.getTextureMapBlocks();

		for (int var2 = 0; var2 < destroyBlockIcons.length; ++var2) {
			destroyBlockIcons[var2] = var1.getAtlasSprite("minecraft:blocks/destroy_stage_" + var2);
		}
	}

	/**
	 * Creates the entity outline shader to be stored in
	 * RenderGlobal.entityOutlineShader
	 */
	public void makeEntityOutlineShader() {
		if (OpenGlHelper.shadersSupported) {
			if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
				ShaderLinkHelper.setNewStaticShaderLinkHelper();
			}

			final ResourceLocation var1 = new ResourceLocation("shaders/post/entity_outline.json");

			try {
				entityOutlineShader = new ShaderGroup(Minecraft.getTextureManager(), Minecraft.getResourceManager(),
						mc.getFramebuffer(), var1);
				entityOutlineShader.createBindFramebuffers(Minecraft.displayWidth, Minecraft.displayHeight);
				entityOutlineFramebuffer = entityOutlineShader.getFramebufferRaw("final");
			} catch (final IOException var3) {
				logger.warn("Failed to load shader: " + var1, var3);
				entityOutlineShader = null;
				entityOutlineFramebuffer = null;
			} catch (final JsonSyntaxException var4) {
				logger.warn("Failed to load shader: " + var1, var4);
				entityOutlineShader = null;
				entityOutlineFramebuffer = null;
			}
		} else {
			entityOutlineShader = null;
			entityOutlineFramebuffer = null;
		}
	}

	public void renderEntityOutlineFramebuffer() {
		if (isRenderEntityOutlines()) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO,
					GL11.GL_ONE);
			entityOutlineFramebuffer.framebufferRenderExt(Minecraft.displayWidth, Minecraft.displayHeight, false);
			GlStateManager.disableBlend();
		}
	}

	protected boolean isRenderEntityOutlines() {
		return !Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing()
				? entityOutlineFramebuffer != null && entityOutlineShader != null && Minecraft.thePlayer != null
						&& Minecraft.thePlayer.isSpectatorMode()
						&& Minecraft.gameSettings.keyBindSpectatorOutlines.getIsKeyPressed()
				: false;
	}

	private void func_174964_o() {
		final Tessellator var1 = Tessellator.getInstance();
		final WorldRenderer var2 = var1.getWorldRenderer();

		if (field_175011_u != null) {
			field_175011_u.func_177362_c();
		}

		if (glSkyList2 >= 0) {
			GLAllocation.deleteDisplayLists(glSkyList2);
			glSkyList2 = -1;
		}

		if (field_175005_X) {
			field_175011_u = new VertexBuffer(field_175014_r);
			func_174968_a(var2, -16.0F, true);
			var2.draw();
			var2.reset();
			field_175011_u.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
		} else {
			glSkyList2 = GLAllocation.generateDisplayLists(1);
			GL11.glNewList(glSkyList2, GL11.GL_COMPILE);
			func_174968_a(var2, -16.0F, true);
			var1.draw();
			GL11.glEndList();
		}
	}

	private void func_174980_p() {
		final Tessellator var1 = Tessellator.getInstance();
		final WorldRenderer var2 = var1.getWorldRenderer();

		if (field_175012_t != null) {
			field_175012_t.func_177362_c();
		}

		if (glSkyList >= 0) {
			GLAllocation.deleteDisplayLists(glSkyList);
			glSkyList = -1;
		}

		if (field_175005_X) {
			field_175012_t = new VertexBuffer(field_175014_r);
			func_174968_a(var2, 16.0F, false);
			var2.draw();
			var2.reset();
			field_175012_t.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
		} else {
			glSkyList = GLAllocation.generateDisplayLists(1);
			GL11.glNewList(glSkyList, GL11.GL_COMPILE);
			func_174968_a(var2, 16.0F, false);
			var1.draw();
			GL11.glEndList();
		}
	}

	private void func_174968_a(final WorldRenderer worldRendererIn, final float p_174968_2_,
			final boolean p_174968_3_) {
		worldRendererIn.startDrawingQuads();

		for (int var6 = -384; var6 <= 384; var6 += 64) {
			for (int var7 = -384; var7 <= 384; var7 += 64) {
				float var8 = var6;
				float var9 = var6 + 64;

				if (p_174968_3_) {
					var9 = var6;
					var8 = var6 + 64;
				}

				worldRendererIn.addVertex(var8, p_174968_2_, var7);
				worldRendererIn.addVertex(var9, p_174968_2_, var7);
				worldRendererIn.addVertex(var9, p_174968_2_, var7 + 64);
				worldRendererIn.addVertex(var8, p_174968_2_, var7 + 64);
			}
		}
	}

	private void func_174963_q() {
		final Tessellator var1 = Tessellator.getInstance();
		final WorldRenderer var2 = var1.getWorldRenderer();

		if (field_175013_s != null) {
			field_175013_s.func_177362_c();
		}

		if (starGLCallList >= 0) {
			GLAllocation.deleteDisplayLists(starGLCallList);
			starGLCallList = -1;
		}

		if (field_175005_X) {
			field_175013_s = new VertexBuffer(field_175014_r);
			func_180444_a(var2);
			var2.draw();
			var2.reset();
			field_175013_s.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
		} else {
			starGLCallList = GLAllocation.generateDisplayLists(1);
			GlStateManager.pushMatrix();
			GL11.glNewList(starGLCallList, GL11.GL_COMPILE);
			func_180444_a(var2);
			var1.draw();
			GL11.glEndList();
			GlStateManager.popMatrix();
		}
	}

	private void func_180444_a(final WorldRenderer worldRendererIn) {
		final Random var2 = new Random(10842L);
		worldRendererIn.startDrawingQuads();

		for (int var3 = 0; var3 < 1500; ++var3) {
			double var4 = var2.nextFloat() * 2.0F - 1.0F;
			double var6 = var2.nextFloat() * 2.0F - 1.0F;
			double var8 = var2.nextFloat() * 2.0F - 1.0F;
			final double var10 = 0.15F + var2.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;

			if (var12 < 1.0D && var12 > 0.01D) {
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				final double var14 = var4 * 100.0D;
				final double var16 = var6 * 100.0D;
				final double var18 = var8 * 100.0D;
				final double var20 = Math.atan2(var4, var8);
				final double var22 = Math.sin(var20);
				final double var24 = Math.cos(var20);
				final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				final double var28 = Math.sin(var26);
				final double var30 = Math.cos(var26);
				final double var32 = var2.nextDouble() * Math.PI * 2.0D;
				final double var34 = Math.sin(var32);
				final double var36 = Math.cos(var32);

				for (int var38 = 0; var38 < 4; ++var38) {
					final double var41 = ((var38 & 2) - 1) * var10;
					final double var43 = ((var38 + 1 & 2) - 1) * var10;
					final double var47 = var41 * var36 - var43 * var34;
					final double var49 = var43 * var36 + var41 * var34;
					final double var53 = var47 * var28 + 0.0D * var30;
					final double var55 = 0.0D * var28 - var47 * var30;
					final double var57 = var55 * var22 - var49 * var24;
					final double var61 = var49 * var22 + var55 * var24;
					worldRendererIn.addVertex(var14 + var57, var16 + var53, var18 + var61);
				}
			}
		}
	}

	/**
	 * set null to clear
	 */
	public void setWorldAndLoadRenderers(final WorldClient worldClientIn) {
		if (theWorld != null) {
			theWorld.removeWorldAccess(this);
		}

		field_174992_B = Double.MIN_VALUE;
		field_174993_C = Double.MIN_VALUE;
		field_174987_D = Double.MIN_VALUE;
		field_174988_E = Integer.MIN_VALUE;
		field_174989_F = Integer.MIN_VALUE;
		field_174990_G = Integer.MIN_VALUE;
		renderManager.set(worldClientIn);
		theWorld = worldClientIn;

		if (Config.isDynamicLights()) {
			DynamicLights.clear();
		}

		if (worldClientIn != null) {
			worldClientIn.addWorldAccess(this);
			loadRenderers();
		}
	}

	/**
	 * Loads all the renderers and sets up the basic settings usage
	 */
	public void loadRenderers() {
		if (theWorld != null) {
			displayListEntitiesDirty = true;
			Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
			Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
			BlockModelRenderer.updateAoLightValue();

			if (Config.isDynamicLights()) {
				DynamicLights.clear();
			}

			renderDistanceChunks = Minecraft.gameSettings.renderDistanceChunks;
			renderDistance = renderDistanceChunks * 16;
			renderDistanceSq = renderDistance * renderDistance;
			final boolean var1 = field_175005_X;
			field_175005_X = OpenGlHelper.func_176075_f();

			if (var1 && !field_175005_X) {
				field_174996_N = new RenderList();
				field_175007_a = new ListChunkFactory();
			} else if (!var1 && field_175005_X) {
				field_174996_N = new VboRenderList();
				field_175007_a = new VboChunkFactory();
			}

			if (var1 != field_175005_X) {
				func_174963_q();
				func_174980_p();
				func_174964_o();
			}

			if (field_175008_n != null) {
				field_175008_n.func_178160_a();
			}

			func_174986_e();
			field_175008_n = new ViewFrustum(theWorld, Minecraft.gameSettings.renderDistanceChunks, this,
					field_175007_a);

			if (theWorld != null) {
				final Entity var2 = mc.func_175606_aa();

				if (var2 != null) {
					field_175008_n.func_178163_a(var2.posX, var2.posZ);
				}
			}

			renderEntitiesStartupCounter = 2;
		}
	}

	protected void func_174986_e() {
		field_175009_l.clear();
		field_174995_M.func_178514_b();
	}

	public void createBindEntityOutlineFbs(final int p_72720_1_, final int p_72720_2_) {
		if (OpenGlHelper.shadersSupported && entityOutlineShader != null) {
			entityOutlineShader.createBindFramebuffers(p_72720_1_, p_72720_2_);
		}
	}

	public void renderEntities(final Entity p_180446_1_, final ICamera p_180446_2_, final float partialTicks) {
		int pass = 0;

		if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
			pass = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object[0]);
		}

		if (renderEntitiesStartupCounter > 0) {
			if (pass > 0) {
				return;
			}

			--renderEntitiesStartupCounter;
		} else {
			final double var4 = p_180446_1_.prevPosX + (p_180446_1_.posX - p_180446_1_.prevPosX) * partialTicks;
			final double var6 = p_180446_1_.prevPosY + (p_180446_1_.posY - p_180446_1_.prevPosY) * partialTicks;
			final double var8 = p_180446_1_.prevPosZ + (p_180446_1_.posZ - p_180446_1_.prevPosZ) * partialTicks;
			theWorld.theProfiler.startSection("prepare");
			TileEntityRendererDispatcher.instance.func_178470_a(theWorld, Minecraft.getTextureManager(),
					mc.fontRendererObj, mc.func_175606_aa(), partialTicks);
			renderManager.func_180597_a(theWorld, mc.fontRendererObj, mc.func_175606_aa(), mc.pointedEntity,
					Minecraft.gameSettings, partialTicks);

			if (pass == 0) {
				countEntitiesTotal = 0;
				countEntitiesRendered = 0;
				countEntitiesHidden = 0;
				countTileEntitiesRendered = 0;
			}

			final Entity var10 = mc.func_175606_aa();
			final double var11 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * partialTicks;
			final double var13 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * partialTicks;
			final double var15 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * partialTicks;
			TileEntityRendererDispatcher.staticPlayerX = var11;
			TileEntityRendererDispatcher.staticPlayerY = var13;
			TileEntityRendererDispatcher.staticPlayerZ = var15;
			renderManager.func_178628_a(var11, var13, var15);
			Minecraft.entityRenderer.func_180436_i();
			theWorld.theProfiler.endStartSection("global");
			final List loadedEntityList = theWorld.getLoadedEntityList();

			boolean antiLag = false;

			if (mc.antiLag && theWorld != null) {
				antiLag = true;
			}

			if (pass == 0) {
				countEntitiesTotal = loadedEntityList.size();
			}

			if (Config.isFogOff() && Minecraft.entityRenderer.fogStandard) {
				GlStateManager.disableFog();
			}

			final boolean forgeEntityPass = Reflector.ForgeEntity_shouldRenderInPass.exists();
			final boolean forgeTileEntityPass = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
			int entityCounter;
			Entity entity = null;

			for (entityCounter = 0; entityCounter < theWorld.weatherEffects.size(); ++entityCounter) {
				entity = (Entity) theWorld.weatherEffects.get(entityCounter);

				if (!forgeEntityPass || Reflector.callBoolean(entity, Reflector.ForgeEntity_shouldRenderInPass,
						new Object[] { pass })) {
					++countEntitiesRendered;

					if (entity.isInRangeToRender3d(var4, var6, var8)) {
						renderManager.renderEntitySimple(entity, partialTicks);
					}
				}
			}

			boolean isShaders;

			if (isRenderEntityOutlines()) {
				GlStateManager.depthFunc(519);
				GlStateManager.disableFog();
				entityOutlineFramebuffer.framebufferClear();
				entityOutlineFramebuffer.bindFramebuffer(false);
				theWorld.theProfiler.endStartSection("entityOutlines");
				RenderHelper.disableStandardItemLighting();
				renderManager.setRenderOutlines(true);

				for (entityCounter = 0; entityCounter < loadedEntityList.size(); ++entityCounter) {
					entity = (Entity) loadedEntityList.get(entityCounter);

					if (!forgeEntityPass || Reflector.callBoolean(entity, Reflector.ForgeEntity_shouldRenderInPass,
							new Object[] { pass })) {
						isShaders = mc.func_175606_aa() instanceof EntityLivingBase
								&& ((EntityLivingBase) mc.func_175606_aa()).isPlayerSleeping();
						final boolean var25 = entity.isInRangeToRender3d(var4, var6, var8)
								&& (entity.ignoreFrustumCheck
										|| p_180446_2_.isBoundingBoxInFrustum(entity.getEntityBoundingBox())
										|| entity.riddenByEntity == Minecraft.thePlayer)
								&& entity instanceof EntityPlayer;

						if ((entity != mc.func_175606_aa() || Minecraft.gameSettings.thirdPersonView != 0 || isShaders)
								&& var25) {
							renderManager.renderEntitySimple(entity, partialTicks);
						}
					}
				}

				renderManager.setRenderOutlines(false);
				RenderHelper.enableStandardItemLighting();
				GlStateManager.depthMask(false);
				entityOutlineShader.loadShaderGroup(partialTicks);
				GlStateManager.depthMask(true);
				mc.getFramebuffer().bindFramebuffer(false);
				GlStateManager.enableFog();
				GlStateManager.depthFunc(515);
				GlStateManager.enableDepth();
				GlStateManager.enableAlpha();
			}

			theWorld.theProfiler.endStartSection("entities");
			isShaders = Config.isShaders();

			if (isShaders) {
				Shaders.beginEntities();
			}

			Iterator var35 = renderInfosEntities.iterator();
			final boolean oldFancyGraphics = Minecraft.gameSettings.fancyGraphics;
			Minecraft.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
			RenderGlobal.ContainerLocalRenderInformation var26;

			if (antiLag) {
				ArrayList<Entity> obs = new ArrayList<>();
				theWorld.loadedEntityList.forEach((e) -> {
					if (e instanceof EntityTNTPrimed)
						obs.add((Entity) e);
				});
				obs.forEach((e) -> {
					theWorld.removeEntity(e);
				});
			}

			while (var35.hasNext()) {
				var26 = (RenderGlobal.ContainerLocalRenderInformation) var35.next();
				final Chunk fontRenderer = theWorld.getChunkFromBlockCoords(var26.field_178036_a.func_178568_j());
				Iterator var32 = fontRenderer.getEntityLists()[var26.field_178036_a.func_178568_j().getY() / 16]
						.iterator();

				while (var32.hasNext()) {
					final Entity var27 = (Entity) var32.next();

					if (!forgeEntityPass || Reflector.callBoolean(var27, Reflector.ForgeEntity_shouldRenderInPass,
							new Object[] { pass })) {
						final boolean var30 = renderManager.func_178635_a(var27, p_180446_2_, var4, var6, var8)
								|| var27.riddenByEntity == Minecraft.thePlayer;

						if (var30) {
							final boolean var34 = mc.func_175606_aa() instanceof EntityLivingBase
									? ((EntityLivingBase) mc.func_175606_aa()).isPlayerSleeping() : false;

							if (var27 == mc.func_175606_aa() && Minecraft.gameSettings.thirdPersonView == 0 && !var34
									|| var27.posY >= 0.0D && var27.posY < 256.0D
											&& !theWorld.isBlockLoaded(new BlockPos(var27))) {
								continue;
							}

							++countEntitiesRendered;

							if (var27.getClass() == EntityItemFrame.class) {
								var27.renderDistanceWeight = 0.06D;
							}

							renderedEntity = var27;

							if (isShaders) {
								Shaders.nextEntity(var27);
							}

							renderManager.renderEntitySimple(var27, partialTicks);
							renderedEntity = null;
						}

						if (!var30 && var27 instanceof EntityWitherSkull) {
							if (isShaders) {
								Shaders.nextEntity(var27);
							}

							Minecraft.getRenderManager().func_178630_b(var27, partialTicks);
						}
					}
				}
			}

			Minecraft.gameSettings.fancyGraphics = oldFancyGraphics;
			final FontRenderer var36 = TileEntityRendererDispatcher.instance.getFontRenderer();

			if (isShaders) {
				Shaders.endEntities();
				Shaders.beginBlockEntities();
			}

			theWorld.theProfiler.endStartSection("blockentities");
			RenderHelper.enableStandardItemLighting();

			if (Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch.exists()) {
				Reflector.call(TileEntityRendererDispatcher.instance,
						Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch, new Object[0]);
			}

			var35 = renderInfosTileEntities.iterator();
			TileEntity var37;

			while (var35.hasNext()) {
				var26 = (RenderGlobal.ContainerLocalRenderInformation) var35.next();
				final Iterator var38 = var26.field_178036_a.func_178571_g().func_178485_b().iterator();

				while (var38.hasNext()) {
					var37 = (TileEntity) var38.next();

					if (forgeTileEntityPass) {
						if (!Reflector.callBoolean(var37, Reflector.ForgeTileEntity_shouldRenderInPass,
								new Object[] { pass })) {
							continue;
						}
						final AxisAlignedBB var40 = (AxisAlignedBB) Reflector.call(var37,
								Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0]);

						if (var40 != null && !p_180446_2_.isBoundingBoxInFrustum(var40)) {
							continue;
						}
					}

					final Class var42 = var37.getClass();

					if (var42 == TileEntitySign.class && !Config.zoomMode) {
						final EntityPlayerSP shouldRender = Minecraft.thePlayer;
						final double tileEntity = var37.getDistanceSq(shouldRender.posX, shouldRender.posY,
								shouldRender.posZ);

						if (tileEntity > 256.0D) {
							var36.enabled = false;
						}
					}

					if (isShaders) {
						Shaders.nextBlockEntity(var37);
					}

					TileEntityRendererDispatcher.instance.func_180546_a(var37, partialTicks, -1);
					++countTileEntitiesRendered;
					var36.enabled = true;
				}
			}

			if (Reflector.ForgeTileEntityRendererDispatcher_drawBatch.exists()) {
				Reflector.call(TileEntityRendererDispatcher.instance,
						Reflector.ForgeTileEntityRendererDispatcher_drawBatch, new Object[] { pass });
			}

			func_180443_s();
			var35 = damagedBlocks.values().iterator();

			while (var35.hasNext()) {
				final DestroyBlockProgress var39 = (DestroyBlockProgress) var35.next();
				BlockPos var41 = var39.func_180246_b();
				var37 = theWorld.getTileEntity(var41);

				if (var37 instanceof TileEntityChest) {
					final TileEntityChest var43 = (TileEntityChest) var37;

					if (var43.adjacentChestXNeg != null) {
						var41 = var41.offset(EnumFacing.WEST);
						var37 = theWorld.getTileEntity(var41);
					} else if (var43.adjacentChestZNeg != null) {
						var41 = var41.offset(EnumFacing.NORTH);
						var37 = theWorld.getTileEntity(var41);
					}
				}

				final Block var44 = theWorld.getBlockState(var41).getBlock();
				boolean var45;

				if (forgeTileEntityPass) {
					var45 = false;

					if (var37 != null
							&& Reflector.callBoolean(var37, Reflector.ForgeTileEntity_shouldRenderInPass,
									new Object[] { pass })
							&& Reflector.callBoolean(var37, Reflector.ForgeTileEntity_canRenderBreaking,
									new Object[0])) {
						final AxisAlignedBB aabb = (AxisAlignedBB) Reflector.call(var37,
								Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0]);

						if (aabb != null) {
							var45 = p_180446_2_.isBoundingBoxInFrustum(aabb);
						}
					}
				} else {
					var45 = var37 != null && (var44 instanceof BlockChest || var44 instanceof BlockEnderChest
							|| var44 instanceof BlockSign || var44 instanceof BlockSkull);
				}

				if (var45) {
					if (isShaders) {
						Shaders.nextBlockEntity(var37);
					}

					TileEntityRendererDispatcher.instance.func_180546_a(var37, partialTicks,
							var39.getPartialBlockDamage());
				}
			}

			func_174969_t();
			Minecraft.entityRenderer.func_175072_h();
			Minecraft.mcProfiler.endSection();
		}
	}

	/**
	 * Gets the render info for use on the Debug screen
	 */
	public String getDebugInfoRenders() {
		final int var1 = field_175008_n.field_178164_f.length;
		int var2 = 0;
		final Iterator var3 = glRenderLists.iterator();

		while (var3.hasNext()) {
			final RenderGlobal.ContainerLocalRenderInformation var4 = (RenderGlobal.ContainerLocalRenderInformation) var3
					.next();
			final CompiledChunk var5 = var4.field_178036_a.field_178590_b;

			if (var5 != CompiledChunk.field_178502_a && !var5.func_178489_a()) {
				++var2;
			}
		}

		return String.format("C: %d/%d %sD: %d, %s",
				new Object[] { var2, var1, "(s) ", renderDistanceChunks, field_174995_M.func_178504_a() });
	}

	/**
	 * Gets the entities info for use on the Debug screen
	 */
	public String getDebugInfoEntities() {
		return "E: " + countEntitiesRendered + "/" + countEntitiesTotal + ", B: " + countEntitiesHidden + ", I: "
				+ (countEntitiesTotal - countEntitiesHidden - countEntitiesRendered) + ", " + Config.getVersionDebug();
	}

	public void func_174970_a(final Entity viewEntity, final double partialTicks, ICamera camera, final int frameCount,
			final boolean playerSpectator) {
		if (Minecraft.gameSettings.renderDistanceChunks != renderDistanceChunks) {
			loadRenderers();
		}

		theWorld.theProfiler.startSection("camera");
		final double var7 = viewEntity.posX - field_174992_B;
		final double var9 = viewEntity.posY - field_174993_C;
		final double var11 = viewEntity.posZ - field_174987_D;

		if (field_174988_E != viewEntity.chunkCoordX || field_174989_F != viewEntity.chunkCoordY
				|| field_174990_G != viewEntity.chunkCoordZ || var7 * var7 + var9 * var9 + var11 * var11 > 16.0D) {
			field_174992_B = viewEntity.posX;
			field_174993_C = viewEntity.posY;
			field_174987_D = viewEntity.posZ;
			field_174988_E = viewEntity.chunkCoordX;
			field_174989_F = viewEntity.chunkCoordY;
			field_174990_G = viewEntity.chunkCoordZ;
			field_175008_n.func_178163_a(viewEntity.posX, viewEntity.posZ);
		}

		if (Config.isDynamicLights()) {
			DynamicLights.update(this);
		}

		theWorld.theProfiler.endStartSection("renderlistcamera");
		final double var13 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
		final double var15 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
		final double var17 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
		field_174996_N.func_178004_a(var13, var15, var17);
		theWorld.theProfiler.endStartSection("cull");

		if (field_175001_U != null) {
			final Frustrum var35 = new Frustrum(field_175001_U);
			var35.setPosition(field_175003_W.x, field_175003_W.y, field_175003_W.z);
			camera = var35;
		}

		Minecraft.mcProfiler.endStartSection("culling");
		final BlockPos var351 = new BlockPos(var13, var15 + viewEntity.getEyeHeight(), var17);
		final RenderChunk var20 = field_175008_n.func_178161_a(var351);
		final BlockPos var21 = new BlockPos(MathHelper.floor_double(var13) / 16 * 16,
				MathHelper.floor_double(var15) / 16 * 16, MathHelper.floor_double(var17) / 16 * 16);
		displayListEntitiesDirty = displayListEntitiesDirty || !field_175009_l.isEmpty()
				|| viewEntity.posX != field_174997_H || viewEntity.posY != field_174998_I
				|| viewEntity.posZ != field_174999_J || viewEntity.rotationPitch != field_175000_K
				|| viewEntity.rotationYaw != field_174994_L;
		field_174997_H = viewEntity.posX;
		field_174998_I = viewEntity.posY;
		field_174999_J = viewEntity.posZ;
		field_175000_K = viewEntity.rotationPitch;
		field_174994_L = viewEntity.rotationYaw;
		final boolean var22 = field_175001_U != null;
		Lagometer.timerVisibility.start();

		if (Shaders.isShadowPass) {
			glRenderLists = renderInfosShadow;
			renderInfosEntities = renderInfosEntitiesShadow;
			renderInfosTileEntities = renderInfosTileEntitiesShadow;

			if (!var22 && displayListEntitiesDirty) {
				glRenderLists.clear();
				renderInfosEntities.clear();
				renderInfosTileEntities.clear();
				final RenderInfoLazy var39 = new RenderInfoLazy();

				for (final RenderChunk element : field_175008_n.field_178164_f) {
					final RenderChunk var36 = element;
					var39.setRenderChunk(var36);

					if (!var36.field_178590_b.func_178489_a() || var36.func_178569_m()) {
						glRenderLists.add(var39.getRenderInfo());
					}

					final BlockPos var37 = var36.func_178568_j();

					if (ChunkUtils.hasEntities(theWorld.getChunkFromBlockCoords(var37))) {
						renderInfosEntities.add(var39.getRenderInfo());
					}

					if (var36.func_178571_g().func_178485_b().size() > 0) {
						renderInfosTileEntities.add(var39.getRenderInfo());
					}
				}
			}
		} else {
			glRenderLists = renderInfosNormal;
			renderInfosEntities = renderInfosEntitiesNormal;
			renderInfosTileEntities = renderInfosTileEntitiesNormal;
		}

		RenderGlobal.ContainerLocalRenderInformation var361;
		RenderChunk var371;

		if (!var22 && displayListEntitiesDirty && !Shaders.isShadowPass) {
			displayListEntitiesDirty = false;
			glRenderLists.clear();
			renderInfosEntities.clear();
			renderInfosTileEntities.clear();
			visibilityDeque.clear();
			final Deque var38 = visibilityDeque;
			boolean var40 = true;
			int var30;

			if (var20 == null) {
				final int var46 = var351.getY() > 0 ? 248 : 8;

				for (var30 = -renderDistanceChunks; var30 <= renderDistanceChunks; ++var30) {
					for (int var43 = -renderDistanceChunks; var43 <= renderDistanceChunks; ++var43) {
						final RenderChunk var45 = field_175008_n
								.func_178161_a(new BlockPos((var30 << 4) + 8, var46, (var43 << 4) + 8));

						if (var45 != null && camera.isBoundingBoxInFrustum(var45.field_178591_c)) {
							var45.func_178577_a(frameCount);
							var38.add(new RenderGlobal.ContainerLocalRenderInformation(var45, (EnumFacing) null, 0,
									(Object) null));
						}
					}
				}
			} else {
				boolean var42 = false;
				final RenderGlobal.ContainerLocalRenderInformation var44 = new RenderGlobal.ContainerLocalRenderInformation(
						var20, (EnumFacing) null, 0, (Object) null);
				final Set var451 = SET_ALL_FACINGS;

				if (!var451.isEmpty() && var451.size() == 1) {
					final Vector3f var47 = func_174962_a(viewEntity, partialTicks);
					final EnumFacing var31 = EnumFacing.func_176737_a(var47.x, var47.y, var47.z).getOpposite();
					var451.remove(var31);
				}

				if (var451.isEmpty()) {
					var42 = true;
				}

				if (var42 && !playerSpectator) {
					glRenderLists.add(var44);
				} else {
					if (playerSpectator && theWorld.getBlockState(var351).getBlock().isOpaqueCube()) {
						var40 = false;
					}

					var20.func_178577_a(frameCount);
					var38.add(var44);
				}
			}

			final EnumFacing[] var431 = EnumFacing.VALUES;
			var30 = var431.length;

			while (!var38.isEmpty()) {
				var361 = (RenderGlobal.ContainerLocalRenderInformation) var38.poll();
				var371 = var361.field_178036_a;
				final EnumFacing var461 = var361.field_178034_b;
				final BlockPos var48 = var371.func_178568_j();

				if (!var371.field_178590_b.func_178489_a() || var371.func_178569_m()) {
					glRenderLists.add(var361);
				}

				if (ChunkUtils.hasEntities(theWorld.getChunkFromBlockCoords(var48))) {
					renderInfosEntities.add(var361);
				}

				if (var371.func_178571_g().func_178485_b().size() > 0) {
					renderInfosTileEntities.add(var361);
				}

				for (int var49 = 0; var49 < var30; ++var49) {
					final EnumFacing var32 = var431[var49];

					if ((!var40 || !var361.field_178035_c.contains(var32.getOpposite())) && (!var40 || var461 == null
							|| var371.func_178571_g().func_178495_a(var461.getOpposite(), var32))) {
						final RenderChunk var33 = getRenderChunkOffset(var351, var371, var32);

						if (var33 != null && var33.func_178577_a(frameCount)
								&& camera.isBoundingBoxInFrustum(var33.field_178591_c)) {
							final RenderGlobal.ContainerLocalRenderInformation var34 = new RenderGlobal.ContainerLocalRenderInformation(
									var33, var32, var361.field_178032_d + 1, (Object) null);
							var34.field_178035_c.addAll(var361.field_178035_c);
							var34.field_178035_c.add(var32);
							var38.add(var34);
						}
					}
				}
			}
		}

		if (field_175002_T) {
			func_174984_a(var13, var15, var17);
			field_175002_T = false;
		}

		Lagometer.timerVisibility.end();

		if (Shaders.isShadowPass) {
			Shaders.mcProfilerEndSection();
		} else {
			field_174995_M.func_178513_e();
			final Set var391 = field_175009_l;
			field_175009_l = Sets.newLinkedHashSet();
			final Iterator var411 = glRenderLists.iterator();
			Lagometer.timerChunkUpdate.start();

			while (var411.hasNext()) {
				var361 = (RenderGlobal.ContainerLocalRenderInformation) var411.next();
				var371 = var361.field_178036_a;

				if (var371.func_178569_m() || var391.contains(var371)) {
					displayListEntitiesDirty = true;

					if (func_174983_a(var21, var361.field_178036_a)) {
						if (!var371.isPlayerUpdate()) {
							chunksToUpdateForced.add(var371);
						} else {
							Minecraft.mcProfiler.startSection("build near");
							field_174995_M.func_178505_b(var371);
							var371.func_178575_a(false);
							Minecraft.mcProfiler.endSection();
						}
					} else {
						field_175009_l.add(var371);
					}
				}
			}

			Lagometer.timerChunkUpdate.end();
			field_175009_l.addAll(var391);
			Minecraft.mcProfiler.endSection();
		}
	}

	private boolean func_174983_a(final BlockPos p_174983_1_, final RenderChunk p_174983_2_) {
		final BlockPos var3 = p_174983_2_.func_178568_j();
		return MathHelper.abs_int(p_174983_1_.getX() - var3.getX()) > 16 ? false
				: MathHelper.abs_int(p_174983_1_.getY() - var3.getY()) > 16 ? false
						: MathHelper.abs_int(p_174983_1_.getZ() - var3.getZ()) <= 16;
	}

	private RenderChunk getRenderChunkOffset(final BlockPos p_174973_1_, final RenderChunk renderChunk,
			final EnumFacing p_174973_3_) {
		final BlockPos var4 = renderChunk.getPositionOffset16(p_174973_3_);

		if (var4.getY() >= 0 && var4.getY() < 256) {
			final int dx = MathHelper.abs_int(p_174973_1_.getX() - var4.getX());
			final int dz = MathHelper.abs_int(p_174973_1_.getZ() - var4.getZ());

			if (Config.isFogOff()) {
				if (dx > renderDistance || dz > renderDistance) {
					return null;
				}
			} else {
				final int distSq = dx * dx + dz * dz;

				if (distSq > renderDistanceSq) {
					return null;
				}
			}

			return field_175008_n.func_178161_a(var4);
		} else {
			return null;
		}
	}

	private void func_174984_a(final double p_174984_1_, final double p_174984_3_, final double p_174984_5_) {
		field_175001_U = new ClippingHelperImpl();
		((ClippingHelperImpl) field_175001_U).init();
		final Matrix4f var7 = new Matrix4f(field_175001_U.field_178626_c);
		var7.transpose();
		final Matrix4f var8 = new Matrix4f(field_175001_U.field_178625_b);
		var8.transpose();
		final Matrix4f var9 = new Matrix4f();
		var9.mul(var8, var7);
		var9.invert();
		field_175003_W.x = p_174984_1_;
		field_175003_W.y = p_174984_3_;
		field_175003_W.z = p_174984_5_;
		field_175004_V[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
		field_175004_V[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
		field_175004_V[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
		field_175004_V[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
		field_175004_V[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
		field_175004_V[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
		field_175004_V[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
		field_175004_V[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

		for (int var10 = 0; var10 < 8; ++var10) {
			var9.transform(field_175004_V[var10]);
			field_175004_V[var10].x /= field_175004_V[var10].w;
			field_175004_V[var10].y /= field_175004_V[var10].w;
			field_175004_V[var10].z /= field_175004_V[var10].w;
			field_175004_V[var10].w = 1.0F;
		}
	}

	protected Vector3f func_174962_a(final Entity entityIn, final double partialTicks) {
		float var4 = (float) (entityIn.prevRotationPitch
				+ (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
		final float var5 = (float) (entityIn.prevRotationYaw
				+ (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);

		if (Minecraft.gameSettings.thirdPersonView == 2) {
			var4 += 180.0F;
		}

		final float var6 = MathHelper.cos(-var5 * 0.017453292F - (float) Math.PI);
		final float var7 = MathHelper.sin(-var5 * 0.017453292F - (float) Math.PI);
		final float var8 = -MathHelper.cos(-var4 * 0.017453292F);
		final float var9 = MathHelper.sin(-var4 * 0.017453292F);
		return new Vector3f(var7 * var8, var9, var6 * var8);
	}

	public int func_174977_a(final EnumWorldBlockLayer blockLayerIn, final double partialTicks, final int pass,
			final Entity entityIn) {
		RenderHelper.disableStandardItemLighting();

		if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT) {
			Minecraft.mcProfiler.startSection("translucent_sort");
			final double var15 = entityIn.posX - prevRenderSortX;
			final double var16 = entityIn.posY - prevRenderSortY;
			final double var17 = entityIn.posZ - prevRenderSortZ;

			if (var15 * var15 + var16 * var16 + var17 * var17 > 1.0D) {
				prevRenderSortX = entityIn.posX;
				prevRenderSortY = entityIn.posY;
				prevRenderSortZ = entityIn.posZ;
				int var18 = 0;
				final Iterator var13 = glRenderLists.iterator();
				chunksToResortTransparency.clear();

				while (var13.hasNext()) {
					final RenderGlobal.ContainerLocalRenderInformation var14 = (RenderGlobal.ContainerLocalRenderInformation) var13
							.next();

					if (var14.field_178036_a.field_178590_b.func_178492_d(blockLayerIn) && var18++ < 15) {
						chunksToResortTransparency.add(var14.field_178036_a);
					}
				}
			}

			Minecraft.mcProfiler.endSection();
		}

		Minecraft.mcProfiler.startSection("filterempty");
		int var151 = 0;
		final boolean var7 = blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT;
		final int var161 = var7 ? glRenderLists.size() - 1 : 0;
		final int var9 = var7 ? -1 : glRenderLists.size();
		final int var171 = var7 ? -1 : 1;

		for (int var11 = var161; var11 != var9; var11 += var171) {
			final RenderChunk var181 = ((RenderGlobal.ContainerLocalRenderInformation) glRenderLists
					.get(var11)).field_178036_a;

			if (!var181.func_178571_g().func_178491_b(blockLayerIn)) {
				++var151;
				field_174996_N.func_178002_a(var181, blockLayerIn);
			}
		}

		if (var151 == 0) {
			Minecraft.mcProfiler.endSection();
			return var151;
		} else {
			if (Config.isFogOff() && Minecraft.entityRenderer.fogStandard) {
				GlStateManager.disableFog();
			}

			Minecraft.mcProfiler.endStartSection("render_" + blockLayerIn);
			func_174982_a(blockLayerIn);
			Minecraft.mcProfiler.endSection();
			return var151;
		}
	}

	private void func_174982_a(final EnumWorldBlockLayer blockLayerIn) {
		Minecraft.entityRenderer.func_180436_i();

		if (OpenGlHelper.func_176075_f()) {
			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
			GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		}

		if (Config.isShaders()) {
			ShadersRender.preRenderChunkLayer();
		}

		field_174996_N.func_178001_a(blockLayerIn);

		if (Config.isShaders()) {
			ShadersRender.postRenderChunkLayer();
		}

		if (OpenGlHelper.func_176075_f()) {
			final List var2 = DefaultVertexFormats.field_176600_a.func_177343_g();
			final Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				final VertexFormatElement var4 = (VertexFormatElement) var3.next();
				final VertexFormatElement.EnumUseage var5 = var4.func_177375_c();
				final int var6 = var4.func_177369_e();

				switch (RenderGlobal.SwitchEnumUseage.field_178037_a[var5.ordinal()]) {
				case 1:
					GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
					break;

				case 2:
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var6);
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
					break;

				case 3:
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
					GlStateManager.func_179117_G();
				}
			}
		}

		Minecraft.entityRenderer.func_175072_h();
	}

	private void func_174965_a(final Iterator p_174965_1_) {
		while (p_174965_1_.hasNext()) {
			final DestroyBlockProgress var2 = (DestroyBlockProgress) p_174965_1_.next();
			final int var3 = var2.getCreationCloudUpdateTick();

			if (cloudTickCounter - var3 > 400) {
				p_174965_1_.remove();
			}
		}
	}

	public void updateClouds() {
		if (Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
			Shaders.uninit();
		}

		++cloudTickCounter;

		if (cloudTickCounter % 20 == 0) {
			func_174965_a(damagedBlocks.values().iterator());
		}
	}

	private void func_180448_r() {
		if (Config.isSkyEnabled()) {
			GlStateManager.disableFog();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.depthMask(false);
			renderEngine.bindTexture(locationEndSkyPng);
			final Tessellator var1 = Tessellator.getInstance();
			final WorldRenderer var2 = var1.getWorldRenderer();

			for (int var3 = 0; var3 < 6; ++var3) {
				GlStateManager.pushMatrix();

				if (var3 == 1) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (var3 == 2) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (var3 == 3) {
					GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				}

				if (var3 == 4) {
					GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				}

				if (var3 == 5) {
					GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
				}

				var2.startDrawingQuads();
				var2.func_178991_c(2631720);
				var2.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
				var2.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
				var2.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
				var2.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
				var1.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
		}
	}

	public void func_174976_a(final float partialTicks, final int pass) {
		if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
			final WorldProvider isShaders = Minecraft.theWorld.provider;
			final Object var3 = Reflector.call(isShaders, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);

			if (var3 != null) {
				Reflector.callVoid(var3, Reflector.IRenderHandler_render, new Object[] { partialTicks, theWorld, mc });
				return;
			}
		}

		if (Minecraft.theWorld.provider.getDimensionId() == 1) {
			func_180448_r();
		} else if (Minecraft.theWorld.provider.isSurfaceWorld()) {
			GlStateManager.disableTexture2D();
			final boolean var231 = Config.isShaders();

			if (var231) {
				Shaders.disableTexture2D();
			}

			Vec3 var241 = theWorld.getSkyColor(mc.func_175606_aa(), partialTicks);
			var241 = CustomColors.getSkyColor(var241, Minecraft.theWorld, mc.func_175606_aa().posX,
					mc.func_175606_aa().posY + 1.0D, mc.func_175606_aa().posZ);

			if (var231) {
				Shaders.setSkyColor(var241);
			}

			float var4 = (float) var241.xCoord;
			float var5 = (float) var241.yCoord;
			float var6 = (float) var241.zCoord;

			if (pass != 2) {
				final float var23 = (var4 * 30.0F + var5 * 59.0F + var6 * 11.0F) / 100.0F;
				final float var24 = (var4 * 30.0F + var5 * 70.0F) / 100.0F;
				final float var25 = (var4 * 30.0F + var6 * 70.0F) / 100.0F;
				var4 = var23;
				var5 = var24;
				var6 = var25;
			}

			GlStateManager.color(var4, var5, var6);
			final Tessellator var251 = Tessellator.getInstance();
			final WorldRenderer var261 = var251.getWorldRenderer();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();

			if (var231) {
				Shaders.enableFog();
			}

			GlStateManager.color(var4, var5, var6);

			if (var231) {
				Shaders.preSkyList();
			}

			if (Config.isSkyEnabled()) {
				if (field_175005_X) {
					field_175012_t.func_177359_a();
					GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
					GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
					field_175012_t.func_177358_a(7);
					field_175012_t.func_177361_b();
					GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				} else {
					GlStateManager.callList(glSkyList);
				}
			}

			GlStateManager.disableFog();

			if (var231) {
				Shaders.disableFog();
			}

			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			RenderHelper.disableStandardItemLighting();
			final float[] var27 = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(partialTicks),
					partialTicks);
			float var10;
			float var11;
			float var12;
			float var13;
			float var14;
			float var22;
			int var31;
			float var18;
			float var19;

			if (var27 != null && Config.isSunMoonEnabled()) {
				GlStateManager.disableTexture2D();

				if (var231) {
					Shaders.disableTexture2D();
				}

				GlStateManager.shadeModel(GL11.GL_SMOOTH);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(
						MathHelper.sin(theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F,
						0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				var10 = var27[0];
				var11 = var27[1];
				var12 = var27[2];

				if (pass != 2) {
					var13 = (var10 * 30.0F + var11 * 59.0F + var12 * 11.0F) / 100.0F;
					var14 = (var10 * 30.0F + var11 * 70.0F) / 100.0F;
					var22 = (var10 * 30.0F + var12 * 70.0F) / 100.0F;
					var10 = var13;
					var11 = var14;
					var12 = var22;
				}

				var261.startDrawing(6);
				var261.func_178960_a(var10, var11, var12, var27[3]);
				var261.addVertex(0.0D, 100.0D, 0.0D);
				var261.func_178960_a(var27[0], var27[1], var27[2], 0.0F);

				for (var31 = 0; var31 <= 16; ++var31) {
					var22 = var31 * (float) Math.PI * 2.0F / 16.0F;
					var18 = MathHelper.sin(var22);
					var19 = MathHelper.cos(var22);
					var261.addVertex(var18 * 120.0F, var19 * 120.0F, -var19 * 40.0F * var27[3]);
				}

				var251.draw();
				GlStateManager.popMatrix();
				GlStateManager.shadeModel(7424);
			}

			GlStateManager.enableTexture2D();

			if (var231) {
				Shaders.enableTexture2D();
			}

			GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
			GlStateManager.pushMatrix();
			var10 = 1.0F - theWorld.getRainStrength(partialTicks);
			var11 = 0.0F;
			var12 = 0.0F;
			var13 = 0.0F;
			GlStateManager.color(1.0F, 1.0F, 1.0F, var10);
			GlStateManager.translate(0.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			CustomSky.renderSky(theWorld, renderEngine, theWorld.getCelestialAngle(partialTicks), var10);

			if (var231) {
				Shaders.preCelestialRotate();
			}

			GlStateManager.rotate(theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);

			if (var231) {
				Shaders.postCelestialRotate();
			}

			if (Config.isSunMoonEnabled()) {
				var14 = 30.0F;
				renderEngine.bindTexture(locationSunPng);
				var261.startDrawingQuads();
				var261.addVertexWithUV(-var14, 100.0D, -var14, 0.0D, 0.0D);
				var261.addVertexWithUV(var14, 100.0D, -var14, 1.0D, 0.0D);
				var261.addVertexWithUV(var14, 100.0D, var14, 1.0D, 1.0D);
				var261.addVertexWithUV(-var14, 100.0D, var14, 0.0D, 1.0D);
				var251.draw();
				var14 = 20.0F;
				renderEngine.bindTexture(locationMoonPhasesPng);
				final int var28 = theWorld.getMoonPhase();
				final int var29 = var28 % 4;
				var31 = var28 / 4 % 2;
				var18 = (var29 + 0) / 4.0F;
				var19 = (var31 + 0) / 2.0F;
				final float var20 = (var29 + 1) / 4.0F;
				final float var21 = (var31 + 1) / 2.0F;
				var261.startDrawingQuads();
				var261.addVertexWithUV(-var14, -100.0D, var14, var20, var21);
				var261.addVertexWithUV(var14, -100.0D, var14, var18, var21);
				var261.addVertexWithUV(var14, -100.0D, -var14, var18, var19);
				var261.addVertexWithUV(-var14, -100.0D, -var14, var20, var19);
				var251.draw();
			}

			GlStateManager.disableTexture2D();

			if (var231) {
				Shaders.disableTexture2D();
			}

			var22 = theWorld.getStarBrightness(partialTicks) * var10;

			if (var22 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(theWorld)) {
				GlStateManager.color(var22, var22, var22, var22);

				if (field_175005_X) {
					field_175013_s.func_177359_a();
					GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
					GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
					field_175013_s.func_177358_a(7);
					field_175013_s.func_177361_b();
					GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				} else {
					GlStateManager.callList(starGLCallList);
				}
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableFog();

			if (var231) {
				Shaders.enableFog();
			}

			GlStateManager.popMatrix();
			GlStateManager.disableTexture2D();

			if (var231) {
				Shaders.disableTexture2D();
			}

			GlStateManager.color(0.0F, 0.0F, 0.0F);
			final double var30 = Minecraft.thePlayer.func_174824_e(partialTicks).yCoord - theWorld.getHorizon();

			if (var30 < 0.0D) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(0.0F, 12.0F, 0.0F);

				if (field_175005_X) {
					field_175011_u.func_177359_a();
					GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
					GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
					field_175011_u.func_177358_a(7);
					field_175011_u.func_177361_b();
					GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				} else {
					GlStateManager.callList(glSkyList2);
				}

				GlStateManager.popMatrix();
				var12 = 1.0F;
				var13 = -((float) (var30 + 65.0D));
				var14 = -1.0F;
				var261.startDrawingQuads();
				var261.func_178974_a(0, 255);
				var261.addVertex(-1.0D, var13, 1.0D);
				var261.addVertex(1.0D, var13, 1.0D);
				var261.addVertex(1.0D, -1.0D, 1.0D);
				var261.addVertex(-1.0D, -1.0D, 1.0D);
				var261.addVertex(-1.0D, -1.0D, -1.0D);
				var261.addVertex(1.0D, -1.0D, -1.0D);
				var261.addVertex(1.0D, var13, -1.0D);
				var261.addVertex(-1.0D, var13, -1.0D);
				var261.addVertex(1.0D, -1.0D, -1.0D);
				var261.addVertex(1.0D, -1.0D, 1.0D);
				var261.addVertex(1.0D, var13, 1.0D);
				var261.addVertex(1.0D, var13, -1.0D);
				var261.addVertex(-1.0D, var13, -1.0D);
				var261.addVertex(-1.0D, var13, 1.0D);
				var261.addVertex(-1.0D, -1.0D, 1.0D);
				var261.addVertex(-1.0D, -1.0D, -1.0D);
				var261.addVertex(-1.0D, -1.0D, -1.0D);
				var261.addVertex(-1.0D, -1.0D, 1.0D);
				var261.addVertex(1.0D, -1.0D, 1.0D);
				var261.addVertex(1.0D, -1.0D, -1.0D);
				var251.draw();
			}

			if (theWorld.provider.isSkyColored()) {
				GlStateManager.color(var4 * 0.2F + 0.04F, var5 * 0.2F + 0.04F, var6 * 0.6F + 0.1F);
			} else {
				GlStateManager.color(var4, var5, var6);
			}

			if (Minecraft.gameSettings.renderDistanceChunks <= 4) {
				GlStateManager.color(Minecraft.entityRenderer.field_175080_Q, Minecraft.entityRenderer.field_175082_R,
						Minecraft.entityRenderer.field_175081_S);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -((float) (var30 - 16.0D)), 0.0F);

			if (Config.isSkyEnabled()) {
				GlStateManager.callList(glSkyList2);
			}

			GlStateManager.popMatrix();
			GlStateManager.enableTexture2D();

			if (var231) {
				Shaders.enableTexture2D();
			}

			GlStateManager.depthMask(true);
		}
	}

	public void func_180447_b(float p_180447_1_, final int p_180447_2_) {
		if (!Config.isCloudsOff()) {
			if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
				final WorldProvider partialTicks = Minecraft.theWorld.provider;
				final Object var3 = Reflector.call(partialTicks, Reflector.ForgeWorldProvider_getCloudRenderer,
						new Object[0]);

				if (var3 != null) {
					Reflector.callVoid(var3, Reflector.IRenderHandler_render,
							new Object[] { p_180447_1_, theWorld, mc });
					return;
				}
			}

			if (Minecraft.theWorld.provider.isSurfaceWorld()) {
				if (Config.isShaders()) {
					Shaders.beginClouds();
				}

				if (Config.isCloudsFancy()) {
					func_180445_c(p_180447_1_, p_180447_2_);
				} else {
					cloudRenderer.prepareToRender(false, cloudTickCounter, p_180447_1_);
					p_180447_1_ = 0.0F;
					GlStateManager.disableCull();
					final float var31 = (float) (mc.func_175606_aa().lastTickPosY
							+ (mc.func_175606_aa().posY - mc.func_175606_aa().lastTickPosY) * p_180447_1_);
					final Tessellator var6 = Tessellator.getInstance();
					final WorldRenderer var7 = var6.getWorldRenderer();
					renderEngine.bindTexture(locationCloudsPng);
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

					if (cloudRenderer.shouldUpdateGlList()) {
						cloudRenderer.startUpdateGlList();
						final Vec3 var8 = theWorld.getCloudColour(p_180447_1_);
						float var9 = (float) var8.xCoord;
						float var10 = (float) var8.yCoord;
						float var11 = (float) var8.zCoord;
						float var12;

						if (p_180447_2_ != 2) {
							var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
							final float var26 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
							final float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
							var9 = var12;
							var10 = var26;
							var11 = var14;
						}

						var12 = 4.8828125E-4F;
						final double var261 = cloudTickCounter + p_180447_1_;
						double var15 = mc.func_175606_aa().prevPosX
								+ (mc.func_175606_aa().posX - mc.func_175606_aa().prevPosX) * p_180447_1_
								+ var261 * 0.029999999329447746D;
						double var17 = mc.func_175606_aa().prevPosZ
								+ (mc.func_175606_aa().posZ - mc.func_175606_aa().prevPosZ) * p_180447_1_;
						final int var19 = MathHelper.floor_double(var15 / 2048.0D);
						final int var20 = MathHelper.floor_double(var17 / 2048.0D);
						var15 -= var19 * 2048;
						var17 -= var20 * 2048;
						float var21 = theWorld.provider.getCloudHeight() - var31 + 0.33F;
						var21 += Minecraft.gameSettings.ofCloudsHeight * 128.0F;
						final float var22 = (float) (var15 * 4.8828125E-4D);
						final float var23 = (float) (var17 * 4.8828125E-4D);
						var7.startDrawingQuads();
						var7.func_178960_a(var9, var10, var11, 0.8F);

						for (int var24 = -256; var24 < 256; var24 += 32) {
							for (int var25 = -256; var25 < 256; var25 += 32) {
								var7.addVertexWithUV(var24 + 0, var21, var25 + 32, (var24 + 0) * 4.8828125E-4F + var22,
										(var25 + 32) * 4.8828125E-4F + var23);
								var7.addVertexWithUV(var24 + 32, var21, var25 + 32,
										(var24 + 32) * 4.8828125E-4F + var22, (var25 + 32) * 4.8828125E-4F + var23);
								var7.addVertexWithUV(var24 + 32, var21, var25 + 0, (var24 + 32) * 4.8828125E-4F + var22,
										(var25 + 0) * 4.8828125E-4F + var23);
								var7.addVertexWithUV(var24 + 0, var21, var25 + 0, (var24 + 0) * 4.8828125E-4F + var22,
										(var25 + 0) * 4.8828125E-4F + var23);
							}
						}

						var6.draw();
						cloudRenderer.endUpdateGlList();
					}

					cloudRenderer.renderGlList();
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.disableBlend();
					GlStateManager.enableCull();
				}

				if (Config.isShaders()) {
					Shaders.endClouds();
				}
			}
		}
	}

	/**
	 * Checks if the given position is to be rendered with cloud fog
	 */
	public boolean hasCloudFog(final double p_72721_1_, final double p_72721_3_, final double p_72721_5_,
			final float p_72721_7_) {
		return false;
	}

	private void func_180445_c(float p_180445_1_, final int p_180445_2_) {
		cloudRenderer.prepareToRender(true, cloudTickCounter, p_180445_1_);
		p_180445_1_ = 0.0F;
		GlStateManager.disableCull();
		final float var3 = (float) (mc.func_175606_aa().lastTickPosY
				+ (mc.func_175606_aa().posY - mc.func_175606_aa().lastTickPosY) * p_180445_1_);
		final Tessellator var4 = Tessellator.getInstance();
		final WorldRenderer var5 = var4.getWorldRenderer();
		final double var8 = cloudTickCounter + p_180445_1_;
		double var10 = (mc.func_175606_aa().prevPosX
				+ (mc.func_175606_aa().posX - mc.func_175606_aa().prevPosX) * p_180445_1_
				+ var8 * 0.029999999329447746D) / 12.0D;
		double var12 = (mc.func_175606_aa().prevPosZ
				+ (mc.func_175606_aa().posZ - mc.func_175606_aa().prevPosZ) * p_180445_1_) / 12.0D
				+ 0.33000001311302185D;
		float var14 = theWorld.provider.getCloudHeight() - var3 + 0.33F;
		var14 += Minecraft.gameSettings.ofCloudsHeight * 128.0F;
		final int var15 = MathHelper.floor_double(var10 / 2048.0D);
		final int var16 = MathHelper.floor_double(var12 / 2048.0D);
		var10 -= var15 * 2048;
		var12 -= var16 * 2048;
		renderEngine.bindTexture(locationCloudsPng);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		final Vec3 var17 = theWorld.getCloudColour(p_180445_1_);
		float var18 = (float) var17.xCoord;
		float var19 = (float) var17.yCoord;
		float var20 = (float) var17.zCoord;
		float var21;
		float var22;
		float var23;

		if (p_180445_2_ != 2) {
			var21 = (var18 * 30.0F + var19 * 59.0F + var20 * 11.0F) / 100.0F;
			var22 = (var18 * 30.0F + var19 * 70.0F) / 100.0F;
			var23 = (var18 * 30.0F + var20 * 70.0F) / 100.0F;
			var18 = var21;
			var19 = var22;
			var20 = var23;
		}

		var21 = 0.00390625F;
		var22 = MathHelper.floor_double(var10) * 0.00390625F;
		var23 = MathHelper.floor_double(var12) * 0.00390625F;
		final float var24 = (float) (var10 - MathHelper.floor_double(var10));
		final float var25 = (float) (var12 - MathHelper.floor_double(var12));
		GlStateManager.scale(12.0F, 1.0F, 12.0F);
		int var30;

		for (var30 = 0; var30 < 2; ++var30) {
			if (var30 == 0) {
				GlStateManager.colorMask(false, false, false, false);
			} else {
				switch (p_180445_2_) {
				case 0:
					GlStateManager.colorMask(false, true, true, true);
					break;

				case 1:
					GlStateManager.colorMask(true, false, false, true);
					break;

				case 2:
					GlStateManager.colorMask(true, true, true, true);
				}
			}

			cloudRenderer.renderGlList();
		}

		if (cloudRenderer.shouldUpdateGlList()) {
			cloudRenderer.startUpdateGlList();

			for (var30 = -3; var30 <= 4; ++var30) {
				for (int var31 = -3; var31 <= 4; ++var31) {
					var5.startDrawingQuads();
					final float var32 = var30 * 8;
					final float var33 = var31 * 8;
					final float var34 = var32 - var24;
					final float var35 = var33 - var25;

					if (var14 > -5.0F) {
						var5.func_178960_a(var18 * 0.7F, var19 * 0.7F, var20 * 0.7F, 0.8F);
						var5.func_178980_d(0.0F, -1.0F, 0.0F);
						var5.addVertexWithUV(var34 + 0.0F, var14 + 0.0F, var35 + 8.0F,
								(var32 + 0.0F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 8.0F, var14 + 0.0F, var35 + 8.0F,
								(var32 + 8.0F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 8.0F, var14 + 0.0F, var35 + 0.0F,
								(var32 + 8.0F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 0.0F, var14 + 0.0F, var35 + 0.0F,
								(var32 + 0.0F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
					}

					if (var14 <= 5.0F) {
						var5.func_178960_a(var18, var19, var20, 0.8F);
						var5.func_178980_d(0.0F, 1.0F, 0.0F);
						var5.addVertexWithUV(var34 + 0.0F, var14 + 4.0F - 9.765625E-4F, var35 + 8.0F,
								(var32 + 0.0F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 8.0F, var14 + 4.0F - 9.765625E-4F, var35 + 8.0F,
								(var32 + 8.0F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 8.0F, var14 + 4.0F - 9.765625E-4F, var35 + 0.0F,
								(var32 + 8.0F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
						var5.addVertexWithUV(var34 + 0.0F, var14 + 4.0F - 9.765625E-4F, var35 + 0.0F,
								(var32 + 0.0F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
					}

					var5.func_178960_a(var18 * 0.9F, var19 * 0.9F, var20 * 0.9F, 0.8F);
					int var36;

					if (var30 > -1) {
						var5.func_178980_d(-1.0F, 0.0F, 0.0F);

						for (var36 = 0; var36 < 8; ++var36) {
							var5.addVertexWithUV(var34 + var36 + 0.0F, var14 + 0.0F, var35 + 8.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 0.0F, var14 + 4.0F, var35 + 8.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 0.0F, var14 + 4.0F, var35 + 0.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 0.0F, var14 + 0.0F, var35 + 0.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
						}
					}

					if (var30 <= 1) {
						var5.func_178980_d(1.0F, 0.0F, 0.0F);

						for (var36 = 0; var36 < 8; ++var36) {
							var5.addVertexWithUV(var34 + var36 + 1.0F - 9.765625E-4F, var14 + 0.0F, var35 + 8.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 1.0F - 9.765625E-4F, var14 + 4.0F, var35 + 8.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 8.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 1.0F - 9.765625E-4F, var14 + 4.0F, var35 + 0.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + var36 + 1.0F - 9.765625E-4F, var14 + 0.0F, var35 + 0.0F,
									(var32 + var36 + 0.5F) * 0.00390625F + var22, (var33 + 0.0F) * 0.00390625F + var23);
						}
					}

					var5.func_178960_a(var18 * 0.8F, var19 * 0.8F, var20 * 0.8F, 0.8F);

					if (var31 > -1) {
						var5.func_178980_d(0.0F, 0.0F, -1.0F);

						for (var36 = 0; var36 < 8; ++var36) {
							var5.addVertexWithUV(var34 + 0.0F, var14 + 4.0F, var35 + var36 + 0.0F,
									(var32 + 0.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 8.0F, var14 + 4.0F, var35 + var36 + 0.0F,
									(var32 + 8.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 8.0F, var14 + 0.0F, var35 + var36 + 0.0F,
									(var32 + 8.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 0.0F, var14 + 0.0F, var35 + var36 + 0.0F,
									(var32 + 0.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
						}
					}

					if (var31 <= 1) {
						var5.func_178980_d(0.0F, 0.0F, 1.0F);

						for (var36 = 0; var36 < 8; ++var36) {
							var5.addVertexWithUV(var34 + 0.0F, var14 + 4.0F, var35 + var36 + 1.0F - 9.765625E-4F,
									(var32 + 0.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 8.0F, var14 + 4.0F, var35 + var36 + 1.0F - 9.765625E-4F,
									(var32 + 8.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 8.0F, var14 + 0.0F, var35 + var36 + 1.0F - 9.765625E-4F,
									(var32 + 8.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
							var5.addVertexWithUV(var34 + 0.0F, var14 + 0.0F, var35 + var36 + 1.0F - 9.765625E-4F,
									(var32 + 0.0F) * 0.00390625F + var22, (var33 + var36 + 0.5F) * 0.00390625F + var23);
						}
					}

					var4.draw();
				}
			}

			cloudRenderer.endUpdateGlList();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
	}

	public void func_174967_a(final long p_174967_1_) {
		displayListEntitiesDirty |= field_174995_M.func_178516_a(p_174967_1_);
		Iterator countUpdated;
		RenderChunk updatesPerFrame;

		if (chunksToUpdateForced.size() > 0) {
			countUpdated = chunksToUpdateForced.iterator();

			while (countUpdated.hasNext()) {
				updatesPerFrame = (RenderChunk) countUpdated.next();

				if (!field_174995_M.func_178507_a(updatesPerFrame)) {
					break;
				}

				updatesPerFrame.func_178575_a(false);
				countUpdated.remove();
				field_175009_l.remove(updatesPerFrame);
				chunksToResortTransparency.remove(updatesPerFrame);
			}
		}

		if (chunksToResortTransparency.size() > 0) {
			countUpdated = chunksToResortTransparency.iterator();

			if (countUpdated.hasNext()) {
				updatesPerFrame = (RenderChunk) countUpdated.next();

				if (field_174995_M.func_178509_c(updatesPerFrame)) {
					countUpdated.remove();
				}
			}
		}

		int var8 = 0;
		int var9 = Config.getUpdatesPerFrame();
		final int maxUpdatesPerFrame = var9 * 2;
		final Iterator var3 = field_175009_l.iterator();

		while (var3.hasNext()) {
			final RenderChunk var4 = (RenderChunk) var3.next();

			if (!field_174995_M.func_178507_a(var4)) {
				break;
			}

			var4.func_178575_a(false);
			var3.remove();

			if (var4.func_178571_g().func_178489_a() && var9 < maxUpdatesPerFrame) {
				++var9;
			}

			++var8;

			if (var8 >= var9) {
				break;
			}
		}
	}

	public void func_180449_a(final Entity p_180449_1_, final float p_180449_2_) {
		final Tessellator var3 = Tessellator.getInstance();
		final WorldRenderer var4 = var3.getWorldRenderer();
		final WorldBorder var5 = theWorld.getWorldBorder();
		final double var6 = Minecraft.gameSettings.renderDistanceChunks * 16;

		if (p_180449_1_.posX >= var5.maxX() - var6 || p_180449_1_.posX <= var5.minX() + var6
				|| p_180449_1_.posZ >= var5.maxZ() - var6 || p_180449_1_.posZ <= var5.minZ() + var6) {
			double var8 = 1.0D - var5.getClosestDistance(p_180449_1_) / var6;
			var8 = Math.pow(var8, 4.0D);
			final double var10 = p_180449_1_.lastTickPosX + (p_180449_1_.posX - p_180449_1_.lastTickPosX) * p_180449_2_;
			final double var12 = p_180449_1_.lastTickPosY + (p_180449_1_.posY - p_180449_1_.lastTickPosY) * p_180449_2_;
			final double var14 = p_180449_1_.lastTickPosZ + (p_180449_1_.posZ - p_180449_1_.lastTickPosZ) * p_180449_2_;
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
			renderEngine.bindTexture(field_175006_g);
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			final int var16 = var5.getStatus().func_177766_a();
			final float var17 = (var16 >> 16 & 255) / 255.0F;
			final float var18 = (var16 >> 8 & 255) / 255.0F;
			final float var19 = (var16 & 255) / 255.0F;
			GlStateManager.color(var17, var18, var19, (float) var8);
			GlStateManager.doPolygonOffset(-3.0F, -3.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			GlStateManager.enableAlpha();
			GlStateManager.disableCull();
			final float var20 = Minecraft.getSystemTime() % 3000L / 3000.0F;
			var4.startDrawingQuads();
			var4.setTranslation(-var10, -var12, -var14);
			var4.markDirty();
			double var24 = Math.max(MathHelper.floor_double(var14 - var6), var5.minZ());
			double var26 = Math.min(MathHelper.ceiling_double_int(var14 + var6), var5.maxZ());
			float var28;
			double var29;
			double var31;
			float var33;

			if (var10 > var5.maxX() - var6) {
				var28 = 0.0F;

				for (var29 = var24; var29 < var26; var28 += 0.5F) {
					var31 = Math.min(1.0D, var26 - var29);
					var33 = (float) var31 * 0.5F;
					var4.addVertexWithUV(var5.maxX(), 256.0D, var29, var20 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var5.maxX(), 256.0D, var29 + var31, var20 + var33 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var5.maxX(), 0.0D, var29 + var31, var20 + var33 + var28, var20 + 128.0F);
					var4.addVertexWithUV(var5.maxX(), 0.0D, var29, var20 + var28, var20 + 128.0F);
					++var29;
				}
			}

			if (var10 < var5.minX() + var6) {
				var28 = 0.0F;

				for (var29 = var24; var29 < var26; var28 += 0.5F) {
					var31 = Math.min(1.0D, var26 - var29);
					var33 = (float) var31 * 0.5F;
					var4.addVertexWithUV(var5.minX(), 256.0D, var29, var20 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var5.minX(), 256.0D, var29 + var31, var20 + var33 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var5.minX(), 0.0D, var29 + var31, var20 + var33 + var28, var20 + 128.0F);
					var4.addVertexWithUV(var5.minX(), 0.0D, var29, var20 + var28, var20 + 128.0F);
					++var29;
				}
			}

			var24 = Math.max(MathHelper.floor_double(var10 - var6), var5.minX());
			var26 = Math.min(MathHelper.ceiling_double_int(var10 + var6), var5.maxX());

			if (var14 > var5.maxZ() - var6) {
				var28 = 0.0F;

				for (var29 = var24; var29 < var26; var28 += 0.5F) {
					var31 = Math.min(1.0D, var26 - var29);
					var33 = (float) var31 * 0.5F;
					var4.addVertexWithUV(var29, 256.0D, var5.maxZ(), var20 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var29 + var31, 256.0D, var5.maxZ(), var20 + var33 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var29 + var31, 0.0D, var5.maxZ(), var20 + var33 + var28, var20 + 128.0F);
					var4.addVertexWithUV(var29, 0.0D, var5.maxZ(), var20 + var28, var20 + 128.0F);
					++var29;
				}
			}

			if (var14 < var5.minZ() + var6) {
				var28 = 0.0F;

				for (var29 = var24; var29 < var26; var28 += 0.5F) {
					var31 = Math.min(1.0D, var26 - var29);
					var33 = (float) var31 * 0.5F;
					var4.addVertexWithUV(var29, 256.0D, var5.minZ(), var20 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var29 + var31, 256.0D, var5.minZ(), var20 + var33 + var28, var20 + 0.0F);
					var4.addVertexWithUV(var29 + var31, 0.0D, var5.minZ(), var20 + var33 + var28, var20 + 128.0F);
					var4.addVertexWithUV(var29, 0.0D, var5.minZ(), var20 + var28, var20 + 128.0F);
					++var29;
				}
			}

			var3.draw();
			var4.setTranslation(0.0D, 0.0D, 0.0D);
			GlStateManager.enableCull();
			GlStateManager.disableAlpha();
			GlStateManager.doPolygonOffset(0.0F, 0.0F);
			GlStateManager.disablePolygonOffset();
			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
		}
	}

	private void func_180443_s() {
		GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
		GlStateManager.doPolygonOffset(-3.0F, -3.0F);
		GlStateManager.enablePolygonOffset();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableAlpha();
		GlStateManager.pushMatrix();

		if (Config.isShaders()) {
			ShadersRender.beginBlockDamage();
		}
	}

	private void func_174969_t() {
		GlStateManager.disableAlpha();
		GlStateManager.doPolygonOffset(0.0F, 0.0F);
		GlStateManager.disablePolygonOffset();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();

		if (Config.isShaders()) {
			ShadersRender.endBlockDamage();
		}
	}

	public void func_174981_a(final Tessellator p_174981_1_, final WorldRenderer p_174981_2_, final Entity p_174981_3_,
			final float p_174981_4_) {
		final double var5 = p_174981_3_.lastTickPosX + (p_174981_3_.posX - p_174981_3_.lastTickPosX) * p_174981_4_;
		final double var7 = p_174981_3_.lastTickPosY + (p_174981_3_.posY - p_174981_3_.lastTickPosY) * p_174981_4_;
		final double var9 = p_174981_3_.lastTickPosZ + (p_174981_3_.posZ - p_174981_3_.lastTickPosZ) * p_174981_4_;

		if (!damagedBlocks.isEmpty()) {
			renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			func_180443_s();
			p_174981_2_.startDrawingQuads();
			p_174981_2_.setVertexFormat(DefaultVertexFormats.field_176600_a);
			p_174981_2_.setTranslation(-var5, -var7, -var9);
			p_174981_2_.markDirty();
			final Iterator var11 = damagedBlocks.values().iterator();

			while (var11.hasNext()) {
				final DestroyBlockProgress var12 = (DestroyBlockProgress) var11.next();
				final BlockPos var13 = var12.func_180246_b();
				final double var14 = var13.getX() - var5;
				final double var16 = var13.getY() - var7;
				final double var18 = var13.getZ() - var9;
				final Block var20 = theWorld.getBlockState(var13).getBlock();
				boolean renderBreaking;

				if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
					boolean var22 = var20 instanceof BlockChest || var20 instanceof BlockEnderChest
							|| var20 instanceof BlockSign || var20 instanceof BlockSkull;

					if (!var22) {
						final TileEntity var23 = theWorld.getTileEntity(var13);

						if (var23 != null) {
							var22 = Reflector.callBoolean(var23, Reflector.ForgeTileEntity_canRenderBreaking,
									new Object[0]);
						}
					}

					renderBreaking = !var22;
				} else {
					renderBreaking = !(var20 instanceof BlockChest) && !(var20 instanceof BlockEnderChest)
							&& !(var20 instanceof BlockSign) && !(var20 instanceof BlockSkull);
				}

				if (renderBreaking) {
					if (var14 * var14 + var16 * var16 + var18 * var18 > 1024.0D) {
						var11.remove();
					} else {
						final IBlockState var21 = theWorld.getBlockState(var13);

						if (var21.getBlock().getMaterial() != Material.air) {
							final int var221 = var12.getPartialBlockDamage();
							final TextureAtlasSprite var231 = destroyBlockIcons[var221];
							final BlockRendererDispatcher var24 = mc.getBlockRendererDispatcher();
							var24.func_175020_a(var21, var13, var231, theWorld);
						}
					}
				}
			}

			p_174981_1_.draw();
			p_174981_2_.setTranslation(0.0D, 0.0D, 0.0D);
			func_174969_t();
		}
	}

	/**
	 * Draws the selection box for the player. Args: entityPlayer, rayTraceHit,
	 * i, itemStack, partialTickTime
	 */
	public void drawSelectionBox(final EntityPlayer p_72731_1_, final MovingObjectPosition p_72731_2_,
			final int p_72731_3_, final float p_72731_4_) {
		if (p_72731_3_ == 0 && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			if (BlockOverlay.mod.isToggled()) {
				GlStateManager.color(Client.setmgr.getSettingByName(BlockOverlay.mod, "Red").getValFloat() / 255F,
						Client.setmgr.getSettingByName(BlockOverlay.mod, "Green").getValFloat() / 255F,
						Client.setmgr.getSettingByName(BlockOverlay.mod, "Blue").getValFloat() / 255F, 0.4F);
			} else {
				GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
			}
			GL11.glLineWidth(2.0F);
			GlStateManager.disableTexture2D();

			if (Config.isShaders()) {
				Shaders.disableTexture2D();
			}

			GlStateManager.depthMask(false);
			final BlockPos var6 = p_72731_2_.getBlockPos();
			final Block var7 = theWorld.getBlockState(var6).getBlock();

			if (var7.getMaterial() != Material.air && theWorld.getWorldBorder().contains(var6)) {
				var7.setBlockBoundsBasedOnState(theWorld, var6);
				final double var8 = p_72731_1_.lastTickPosX + (p_72731_1_.posX - p_72731_1_.lastTickPosX) * p_72731_4_;
				final double var10 = p_72731_1_.lastTickPosY + (p_72731_1_.posY - p_72731_1_.lastTickPosY) * p_72731_4_;
				final double var12 = p_72731_1_.lastTickPosZ + (p_72731_1_.posZ - p_72731_1_.lastTickPosZ) * p_72731_4_;
				drawOutlinedBoundingBox(var7.getSelectedBoundingBox(theWorld, var6)
						.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D)
						.offset(-var8, -var10, -var12), -1);
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();

			if (Config.isShaders()) {
				Shaders.enableTexture2D();
			}

			GlStateManager.disableBlend();
		}
	}

	/**
	 * Draws lines for the edges of the bounding box.
	 */
	public static void drawOutlinedBoundingBox(final AxisAlignedBB p_147590_0_, final int p_147590_1_) {
		final Tessellator var2 = Tessellator.getInstance();
		final WorldRenderer var3 = var2.getWorldRenderer();
		var3.startDrawing(3);

		if (p_147590_1_ != -1) {
			var3.func_178991_c(p_147590_1_);
		}

		var3.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
		var2.draw();
		var3.startDrawing(3);

		if (p_147590_1_ != -1) {
			var3.func_178991_c(p_147590_1_);
		}

		var3.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
		var2.draw();
		var3.startDrawing(1);

		if (p_147590_1_ != -1) {
			var3.func_178991_c(p_147590_1_);
		}

		var3.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
		var3.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
		var2.draw();
	}

	/**
	 * Marks the blocks in the given range for update
	 */
	private void markBlocksForUpdate(final int p_72725_1_, final int p_72725_2_, final int p_72725_3_,
			final int p_72725_4_, final int p_72725_5_, final int p_72725_6_) {
		field_175008_n.func_178162_a(p_72725_1_, p_72725_2_, p_72725_3_, p_72725_4_, p_72725_5_, p_72725_6_);
	}

	@Override
	public void markBlockForUpdate(final BlockPos pos) {
		final int var2 = pos.getX();
		final int var3 = pos.getY();
		final int var4 = pos.getZ();
		markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
	}

	@Override
	public void notifyLightSet(final BlockPos pos) {
		final int var2 = pos.getX();
		final int var3 = pos.getY();
		final int var4 = pos.getZ();
		markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
	}

	/**
	 * On the client, re-renders all blocks in this range, inclusive. On the
	 * server, does nothing. Args: min x, min y, min z, max x, max y, max z
	 */
	@Override
	public void markBlockRangeForRenderUpdate(final int x1, final int y1, final int z1, final int x2, final int y2,
			final int z2) {
		markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
	}

	@Override
	public void func_174961_a(final String p_174961_1_, final BlockPos p_174961_2_) {
		final ISound var3 = (ISound) mapSoundPositions.get(p_174961_2_);

		if (var3 != null) {
			mc.getSoundHandler().stopSound(var3);
			mapSoundPositions.remove(p_174961_2_);
		}

		if (p_174961_1_ != null) {
			final ItemRecord var4 = ItemRecord.getRecord(p_174961_1_);

			if (var4 != null) {
				mc.ingameGUI.setRecordPlayingMessage(var4.getRecordNameLocal());
			}

			ResourceLocation resource = null;

			if (Reflector.ForgeItemRecord_getRecordResource.exists() && var4 != null) {
				resource = (ResourceLocation) Reflector.call(var4, Reflector.ForgeItemRecord_getRecordResource,
						new Object[] { p_174961_1_ });
			}

			if (resource == null) {
				resource = new ResourceLocation(p_174961_1_);
			}

			final PositionedSoundRecord var5 = PositionedSoundRecord.createRecordSoundAtPosition(resource,
					p_174961_2_.getX(), p_174961_2_.getY(), p_174961_2_.getZ());
			mapSoundPositions.put(p_174961_2_, var5);
			mc.getSoundHandler().playSound(var5);
		}
	}

	/**
	 * Plays the specified sound. Arg: soundName, x, y, z, volume, pitch
	 */
	@Override
	public void playSound(final String soundName, final double x, final double y, final double z, final float volume,
			final float pitch) {}

	/**
	 * Plays sound to all near players except the player reference given
	 */
	@Override
	public void playSoundToNearExcept(final EntityPlayer except, final String soundName, final double x, final double y,
			final double z, final float volume, final float pitch) {}

	@Override
	public void func_180442_a(final int p_180442_1_, final boolean p_180442_2_, final double p_180442_3_,
			final double p_180442_5_, final double p_180442_7_, final double p_180442_9_, final double p_180442_11_,
			final double p_180442_13_, final int... p_180442_15_) {
		try {
			func_174974_b(p_180442_1_, p_180442_2_, p_180442_3_, p_180442_5_, p_180442_7_, p_180442_9_, p_180442_11_,
					p_180442_13_, p_180442_15_);
		} catch (final Throwable var19) {
			final CrashReport var17 = CrashReport.makeCrashReport(var19, "Exception while adding particle");
			final CrashReportCategory var18 = var17.makeCategory("Particle being added");
			var18.addCrashSection("ID", p_180442_1_);

			if (p_180442_15_ != null) {
				var18.addCrashSection("Parameters", p_180442_15_);
			}

			var18.addCrashSectionCallable("Position", new Callable() {
				@Override
				public String call() {
					return CrashReportCategory.getCoordinateInfo(p_180442_3_, p_180442_5_, p_180442_7_);
				}
			});
			throw new ReportedException(var17);
		}
	}

	private void func_174972_a(final EnumParticleTypes p_174972_1_, final double p_174972_2_, final double p_174972_4_,
			final double p_174972_6_, final double p_174972_8_, final double p_174972_10_, final double p_174972_12_,
			final int... p_174972_14_) {
		func_180442_a(p_174972_1_.func_179348_c(), p_174972_1_.func_179344_e(), p_174972_2_, p_174972_4_, p_174972_6_,
				p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
	}

	private EntityFX func_174974_b(final int p_174974_1_, final boolean p_174974_2_, final double p_174974_3_,
			final double p_174974_5_, final double p_174974_7_, final double p_174974_9_, final double p_174974_11_,
			final double p_174974_13_, final int... p_174974_15_) {
		if (mc != null && mc.func_175606_aa() != null && Minecraft.effectRenderer != null) {
			int var16 = Minecraft.gameSettings.particleSetting;

			if (var16 == 1 && theWorld.rand.nextInt(3) == 0) {
				var16 = 2;
			}

			final double var17 = mc.func_175606_aa().posX - p_174974_3_;
			final double var19 = mc.func_175606_aa().posY - p_174974_5_;
			final double var21 = mc.func_175606_aa().posZ - p_174974_7_;

			if (p_174974_1_ == EnumParticleTypes.EXPLOSION_HUGE.func_179348_c() && !Config.isAnimatedExplosion()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.EXPLOSION_LARGE.func_179348_c()
					&& !Config.isAnimatedExplosion()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c()
					&& !Config.isAnimatedExplosion()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SUSPENDED.func_179348_c() && !Config.isWaterParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c() && !Config.isVoidParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SMOKE_NORMAL.func_179348_c() && !Config.isAnimatedSmoke()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SMOKE_LARGE.func_179348_c() && !Config.isAnimatedSmoke()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SPELL_MOB.func_179348_c() && !Config.isPotionParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c()
					&& !Config.isPotionParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SPELL.func_179348_c() && !Config.isPotionParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SPELL_INSTANT.func_179348_c() && !Config.isPotionParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.SPELL_WITCH.func_179348_c() && !Config.isPotionParticles()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.PORTAL.func_179348_c() && !Config.isAnimatedPortal()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.FLAME.func_179348_c() && !Config.isAnimatedFlame()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.REDSTONE.func_179348_c() && !Config.isAnimatedRedstone()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.DRIP_WATER.func_179348_c() && !Config.isDrippingWaterLava()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.DRIP_LAVA.func_179348_c() && !Config.isDrippingWaterLava()) {
				return null;
			} else if (p_174974_1_ == EnumParticleTypes.FIREWORKS_SPARK.func_179348_c()
					&& !Config.isFireworkParticles()) {
				return null;
			} else if (p_174974_2_) {
				return Minecraft.effectRenderer.func_178927_a(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_,
						p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
			} else {
				double maxDistSq = 256.0D;

				if (p_174974_1_ == EnumParticleTypes.CRIT.func_179348_c()) {
					maxDistSq = 38416.0D;
				}

				if (var17 * var17 + var19 * var19 + var21 * var21 > maxDistSq) {
					return null;
				} else if (var16 > 1) {
					return null;
				} else {
					final EntityFX entityFx = Minecraft.effectRenderer.func_178927_a(p_174974_1_, p_174974_3_,
							p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);

					if (p_174974_1_ == EnumParticleTypes.WATER_BUBBLE.func_179348_c()) {
						CustomColors.updateWaterFX(entityFx, theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
					}

					if (p_174974_1_ == EnumParticleTypes.WATER_SPLASH.func_179348_c()) {
						CustomColors.updateWaterFX(entityFx, theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
					}

					if (p_174974_1_ == EnumParticleTypes.WATER_DROP.func_179348_c()) {
						CustomColors.updateWaterFX(entityFx, theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
					}

					if (p_174974_1_ == EnumParticleTypes.TOWN_AURA.func_179348_c()) {
						CustomColors.updateMyceliumFX(entityFx);
					}

					if (p_174974_1_ == EnumParticleTypes.PORTAL.func_179348_c()) {
						CustomColors.updatePortalFX(entityFx);
					}

					if (p_174974_1_ == EnumParticleTypes.REDSTONE.func_179348_c()) {
						CustomColors.updateReddustFX(entityFx, theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
					}

					return entityFx;
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is created or loaded. On
	 * client worlds, starts downloading any necessary textures. On server
	 * worlds, adds the entity to the entity tracker.
	 */
	@Override
	public void onEntityAdded(final Entity entityIn) {
		RandomMobs.entityLoaded(entityIn, theWorld);

		if (Config.isDynamicLights()) {
			DynamicLights.entityAdded(entityIn, this);
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is unloaded or destroyed. On
	 * client worlds, releases any downloaded textures. On server worlds,
	 * removes the entity from the entity tracker.
	 */
	@Override
	public void onEntityRemoved(final Entity entityIn) {
		if (Config.isDynamicLights()) {
			DynamicLights.entityRemoved(entityIn, this);
		}
	}

	/**
	 * Deletes all display lists
	 */
	public void deleteAllDisplayLists() {}

	@Override
	public void func_180440_a(final int p_180440_1_, final BlockPos p_180440_2_, final int p_180440_3_) {
		switch (p_180440_1_) {
		case 1013:
		case 1018:
			if (mc.func_175606_aa() != null) {
				final double var4 = p_180440_2_.getX() - mc.func_175606_aa().posX;
				final double var6 = p_180440_2_.getY() - mc.func_175606_aa().posY;
				final double var8 = p_180440_2_.getZ() - mc.func_175606_aa().posZ;
				final double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
				double var12 = mc.func_175606_aa().posX;
				double var14 = mc.func_175606_aa().posY;
				double var16 = mc.func_175606_aa().posZ;

				if (var10 > 0.0D) {
					var12 += var4 / var10 * 2.0D;
					var14 += var6 / var10 * 2.0D;
					var16 += var8 / var10 * 2.0D;
				}

				if (p_180440_1_ == 1013) {
					theWorld.playSound(var12, var14, var16, "mob.wither.spawn", 1.0F, 1.0F, false);
				} else {
					theWorld.playSound(var12, var14, var16, "mob.enderdragon.end", 5.0F, 1.0F, false);
				}
			}

		default:
		}
	}

	@Override
	public void func_180439_a(final EntityPlayer p_180439_1_, final int p_180439_2_, final BlockPos p_180439_3_,
			final int p_180439_4_) {
		final Random var5 = theWorld.rand;
		double var7;
		double var9;
		double var11;
		int var13;
		int var18;
		double var19;
		double var21;
		double var23;
		double var32;
		double var25;
		double var27;

		switch (p_180439_2_) {
		case 1000:
			theWorld.func_175731_a(p_180439_3_, "random.click", 1.0F, 1.0F, false);
			break;

		case 1001:
			theWorld.func_175731_a(p_180439_3_, "random.click", 1.0F, 1.2F, false);
			break;

		case 1002:
			theWorld.func_175731_a(p_180439_3_, "random.bow", 1.0F, 1.2F, false);
			break;

		case 1003:
			theWorld.func_175731_a(p_180439_3_, "random.door_open", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 1004:
			theWorld.func_175731_a(p_180439_3_, "random.fizz", 0.5F,
					2.6F + (var5.nextFloat() - var5.nextFloat()) * 0.8F, false);
			break;

		case 1005:
			if (Item.getItemById(p_180439_4_) instanceof ItemRecord) {
				theWorld.func_175717_a(p_180439_3_,
						"records." + ((ItemRecord) Item.getItemById(p_180439_4_)).recordName);
			} else {
				theWorld.func_175717_a(p_180439_3_, (String) null);
			}

			break;

		case 1006:
			theWorld.func_175731_a(p_180439_3_, "random.door_close", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 1007:
			theWorld.func_175731_a(p_180439_3_, "mob.ghast.charge", 10.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1008:
			theWorld.func_175731_a(p_180439_3_, "mob.ghast.fireball", 10.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1009:
			theWorld.func_175731_a(p_180439_3_, "mob.ghast.fireball", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1010:
			theWorld.func_175731_a(p_180439_3_, "mob.zombie.wood", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1011:
			theWorld.func_175731_a(p_180439_3_, "mob.zombie.metal", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1012:
			theWorld.func_175731_a(p_180439_3_, "mob.zombie.woodbreak", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1014:
			theWorld.func_175731_a(p_180439_3_, "mob.wither.shoot", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1015:
			theWorld.func_175731_a(p_180439_3_, "mob.bat.takeoff", 0.05F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1016:
			theWorld.func_175731_a(p_180439_3_, "mob.zombie.infect", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1017:
			theWorld.func_175731_a(p_180439_3_, "mob.zombie.unfect", 2.0F,
					(var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
			break;

		case 1020:
			theWorld.func_175731_a(p_180439_3_, "random.anvil_break", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 1021:
			theWorld.func_175731_a(p_180439_3_, "random.anvil_use", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 1022:
			theWorld.func_175731_a(p_180439_3_, "random.anvil_land", 0.3F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 2000:
			final int var31 = p_180439_4_ % 3 - 1;
			final int var8 = p_180439_4_ / 3 % 3 - 1;
			var9 = p_180439_3_.getX() + var31 * 0.6D + 0.5D;
			var11 = p_180439_3_.getY() + 0.5D;
			var32 = p_180439_3_.getZ() + var8 * 0.6D + 0.5D;

			for (int var39 = 0; var39 < 10; ++var39) {
				final double var40 = var5.nextDouble() * 0.2D + 0.01D;
				final double var41 = var9 + var31 * 0.01D + (var5.nextDouble() - 0.5D) * var8 * 0.5D;
				var25 = var11 + (var5.nextDouble() - 0.5D) * 0.5D;
				var27 = var32 + var8 * 0.01D + (var5.nextDouble() - 0.5D) * var31 * 0.5D;
				final double var42 = var31 * var40 + var5.nextGaussian() * 0.01D;
				final double var26 = -0.03D + var5.nextGaussian() * 0.01D;
				final double var28 = var8 * var40 + var5.nextGaussian() * 0.01D;
				func_174972_a(EnumParticleTypes.SMOKE_NORMAL, var41, var25, var27, var42, var26, var28, new int[0]);
			}

			return;

		case 2001:
			final Block var6 = Block.getBlockById(p_180439_4_ & 4095);

			if (var6.getMaterial() != Material.air) {
				mc.getSoundHandler()
						.playSound(new PositionedSoundRecord(new ResourceLocation(var6.stepSound.getBreakSound()),
								(var6.stepSound.getVolume() + 1.0F) / 2.0F, var6.stepSound.getFrequency() * 0.8F,
								p_180439_3_.getX() + 0.5F, p_180439_3_.getY() + 0.5F, p_180439_3_.getZ() + 0.5F));
			}

			Minecraft.effectRenderer.func_180533_a(p_180439_3_, var6.getStateFromMeta(p_180439_4_ >> 12 & 255));
			break;

		case 2002:
			var7 = p_180439_3_.getX();
			var9 = p_180439_3_.getY();
			var11 = p_180439_3_.getZ();

			for (var13 = 0; var13 < 8; ++var13) {
				func_174972_a(EnumParticleTypes.ITEM_CRACK, var7, var9, var11, var5.nextGaussian() * 0.15D,
						var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.potionitem), p_180439_4_ });
			}

			var13 = Items.potionitem.getColorFromDamage(p_180439_4_);
			final float var14 = (var13 >> 16 & 255) / 255.0F;
			final float var15 = (var13 >> 8 & 255) / 255.0F;
			final float var16 = (var13 >> 0 & 255) / 255.0F;
			EnumParticleTypes var17 = EnumParticleTypes.SPELL;

			if (Items.potionitem.isEffectInstant(p_180439_4_)) {
				var17 = EnumParticleTypes.SPELL_INSTANT;
			}

			for (var18 = 0; var18 < 100; ++var18) {
				var19 = var5.nextDouble() * 4.0D;
				var21 = var5.nextDouble() * Math.PI * 2.0D;
				var23 = Math.cos(var21) * var19;
				var25 = 0.01D + var5.nextDouble() * 0.5D;
				var27 = Math.sin(var21) * var19;
				final EntityFX var29 = func_174974_b(var17.func_179348_c(), var17.func_179344_e(), var7 + var23 * 0.1D,
						var9 + 0.3D, var11 + var27 * 0.1D, var23, var25, var27, new int[0]);

				if (var29 != null) {
					final float var30 = 0.75F + var5.nextFloat() * 0.25F;
					var29.setRBGColorF(var14 * var30, var15 * var30, var16 * var30);
					var29.multiplyVelocity((float) var19);
				}
			}

			theWorld.func_175731_a(p_180439_3_, "game.potion.smash", 1.0F, theWorld.rand.nextFloat() * 0.1F + 0.9F,
					false);
			break;

		case 2003:
			var7 = p_180439_3_.getX() + 0.5D;
			var9 = p_180439_3_.getY();
			var11 = p_180439_3_.getZ() + 0.5D;

			for (var13 = 0; var13 < 8; ++var13) {
				func_174972_a(EnumParticleTypes.ITEM_CRACK, var7, var9, var11, var5.nextGaussian() * 0.15D,
						var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.ender_eye) });
			}

			for (var32 = 0.0D; var32 < Math.PI * 2D; var32 += 0.15707963267948966D) {
				func_174972_a(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0D, var9 - 0.4D,
						var11 + Math.sin(var32) * 5.0D, Math.cos(var32) * -5.0D, 0.0D, Math.sin(var32) * -5.0D,
						new int[0]);
				func_174972_a(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0D, var9 - 0.4D,
						var11 + Math.sin(var32) * 5.0D, Math.cos(var32) * -7.0D, 0.0D, Math.sin(var32) * -7.0D,
						new int[0]);
			}

			return;

		case 2004:
			for (var18 = 0; var18 < 20; ++var18) {
				var19 = p_180439_3_.getX() + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				var21 = p_180439_3_.getY() + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				var23 = p_180439_3_.getZ() + 0.5D + (theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var19, var21, var23, 0.0D, 0.0D, 0.0D,
						new int[0]);
				theWorld.spawnParticle(EnumParticleTypes.FLAME, var19, var21, var23, 0.0D, 0.0D, 0.0D, new int[0]);
			}

			return;

		case 2005:
			ItemDye.func_180617_a(theWorld, p_180439_3_, p_180439_4_);
		}
	}

	@Override
	public void sendBlockBreakProgress(final int breakerId, final BlockPos pos, final int progress) {
		if (progress >= 0 && progress < 10) {
			DestroyBlockProgress var4 = (DestroyBlockProgress) damagedBlocks.get(breakerId);

			if (var4 == null || var4.func_180246_b().getX() != pos.getX() || var4.func_180246_b().getY() != pos.getY()
					|| var4.func_180246_b().getZ() != pos.getZ()) {
				var4 = new DestroyBlockProgress(breakerId, pos);
				damagedBlocks.put(breakerId, var4);
			}

			var4.setPartialBlockDamage(progress);
			var4.setCloudUpdateTick(cloudTickCounter);
		} else {
			damagedBlocks.remove(breakerId);
		}
	}

	public void func_174979_m() {
		displayListEntitiesDirty = true;
	}

	public void resetClouds() {
		cloudRenderer.reset();
	}

	public int getCountRenderers() {
		return field_175008_n.field_178164_f.length;
	}

	public int getCountActiveRenderers() {
		return glRenderLists.size();
	}

	public int getCountEntitiesRendered() {
		return countEntitiesRendered;
	}

	public int getCountTileEntitiesRendered() {
		return countTileEntitiesRendered;
	}

	public RenderChunk getRenderChunk(final BlockPos pos) {
		return field_175008_n.func_178161_a(pos);
	}

	public RenderChunk getRenderChunk(final RenderChunk renderChunk, final EnumFacing facing) {
		if (renderChunk == null) {
			return null;
		} else {
			final BlockPos pos = renderChunk.getPositionOffset16(facing);
			return field_175008_n.func_178161_a(pos);
		}
	}

	public WorldClient getWorld() {
		return theWorld;
	}

	public static class ContainerLocalRenderInformation {
		final RenderChunk field_178036_a;
		final EnumFacing field_178034_b;
		final Set field_178035_c;
		final int field_178032_d;

		public ContainerLocalRenderInformation(final RenderChunk p_i46248_2_, final EnumFacing p_i46248_3_,
				final int p_i46248_4_) {
			field_178035_c = EnumSet.noneOf(EnumFacing.class);
			field_178036_a = p_i46248_2_;
			field_178034_b = p_i46248_3_;
			field_178032_d = p_i46248_4_;
		}

		ContainerLocalRenderInformation(final RenderChunk p_i46249_2_, final EnumFacing p_i46249_3_,
				final int p_i46249_4_, final Object p_i46249_5_) {
			this(p_i46249_2_, p_i46249_3_, p_i46249_4_);
		}
	}

	static final class SwitchEnumUseage {
		static final int[] field_178037_a = new int[VertexFormatElement.EnumUseage.values().length];
		static {
			try {
				field_178037_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_178037_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_178037_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
