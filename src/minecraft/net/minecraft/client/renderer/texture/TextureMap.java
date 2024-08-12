package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import optifine.Config;
import optifine.ConnectedTextures;
import optifine.CustomItems;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;
import shadersmod.client.ShadersTex;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {

public static final int EaZy = 826;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	public static final ResourceLocation field_174945_f = new ResourceLocation("missingno");
	public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
	private final List listAnimatedSprites;
	private final Map mapRegisteredSprites;
	private final Map mapUploadedSprites;
	private final String basePath;
	private final IIconCreator field_174946_m;
	private int mipmapLevels;
	private final TextureAtlasSprite missingImage;
	// private static final String __OBFID = "http://https://fuckuskid00001058";
	private TextureAtlasSprite[] iconGrid;
	private int iconGridSize;
	private int iconGridCountX;
	private int iconGridCountY;
	private double iconGridSizeU;
	private double iconGridSizeV;
	private static final boolean ENABLE_SKIP = Boolean
			.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
	private boolean skipFirst;
	public int atlasWidth;
	public int atlasHeight;

	public TextureMap(final String p_i46099_1_) {
		this(p_i46099_1_, (IIconCreator) null);
	}

	public TextureMap(final String p_i46099_1_, final boolean skipFirst) {
		this(p_i46099_1_, (IIconCreator) null, skipFirst);
	}

	public TextureMap(final String p_i46100_1_, final IIconCreator p_i46100_2_) {
		this(p_i46100_1_, p_i46100_2_, false);
	}

	public TextureMap(final String p_i46100_1_, final IIconCreator p_i46100_2_, final boolean skipFirst) {
		iconGrid = null;
		iconGridSize = -1;
		iconGridCountX = -1;
		iconGridCountY = -1;
		iconGridSizeU = -1.0D;
		iconGridSizeV = -1.0D;
		this.skipFirst = false;
		atlasWidth = 0;
		atlasHeight = 0;
		listAnimatedSprites = Lists.newArrayList();
		mapRegisteredSprites = Maps.newHashMap();
		mapUploadedSprites = Maps.newHashMap();
		missingImage = new TextureAtlasSprite("missingno");
		basePath = p_i46100_1_;
		field_174946_m = p_i46100_2_;
		this.skipFirst = skipFirst && ENABLE_SKIP;
	}

	private void initMissingImage() {
		final int size = getMinSpriteSize();
		final int[] var1 = getMissingImageData(size);
		missingImage.setIconWidth(size);
		missingImage.setIconHeight(size);
		final int[][] var2 = new int[mipmapLevels + 1][];
		var2[0] = var1;
		missingImage.setFramesTextureData(Lists.newArrayList(new int[][][] { var2 }));
		missingImage.setIndexInMap(0);
	}

	@Override
	public void loadTexture(final IResourceManager p_110551_1_) throws IOException {
		ShadersTex.resManager = p_110551_1_;

		if (field_174946_m != null) {
			func_174943_a(p_110551_1_, field_174946_m);
		}
	}

	public void func_174943_a(final IResourceManager p_174943_1_, final IIconCreator p_174943_2_) {
		mapRegisteredSprites.clear();
		p_174943_2_.func_177059_a(this);

		if (mipmapLevels >= 4) {
			mipmapLevels = detectMaxMipmapLevel(mapRegisteredSprites, p_174943_1_);
			Config.log("Mipmap levels: " + mipmapLevels);
		}

		initMissingImage();
		deleteGlTexture();
		loadTextureAtlas(p_174943_1_);
	}

	public void loadTextureAtlas(final IResourceManager p_110571_1_) {
		ShadersTex.resManager = p_110571_1_;
		Config.dbg("Multitexture: " + Config.isMultiTexture());

		if (Config.isMultiTexture()) {
			final Iterator var2 = mapUploadedSprites.values().iterator();

			while (var2.hasNext()) {
				final TextureAtlasSprite var3 = (TextureAtlasSprite) var2.next();
				var3.deleteSpriteTexture();
			}
		}

		ConnectedTextures.updateIcons(this);
		CustomItems.updateIcons(this);
		final int var21 = Minecraft.getGLMaximumTextureSize();
		final Stitcher var31 = new Stitcher(var21, var21, true, 0, mipmapLevels);
		mapUploadedSprites.clear();
		listAnimatedSprites.clear();
		int var4 = Integer.MAX_VALUE;
		Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
		final int minSpriteSize = getMinSpriteSize();
		iconGridSize = minSpriteSize;
		int var5 = 1 << mipmapLevels;
		final Iterator var6 = mapRegisteredSprites.entrySet().iterator();
		int sheetHeight;
		List listSprites1;

		while (var6.hasNext() && !skipFirst) {
			final Entry var25 = (Entry) var6.next();
			final TextureAtlasSprite var26 = (TextureAtlasSprite) var25.getValue();
			final ResourceLocation var27 = new ResourceLocation(var26.getIconName());
			final ResourceLocation var28 = completeResourceLocation(var27, 0);

			if (var26.hasCustomLoader(p_110571_1_, var27)) {
				if (!var26.load(p_110571_1_, var27)) {
					var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
					var31.addSprite(var26);
				}

				Config.dbg("Custom loader: " + var26);
			} else {
				try {
					final IResource var30 = ShadersTex.loadResource(p_110571_1_, var28);
					final BufferedImage[] var311 = new BufferedImage[1 + mipmapLevels];
					var311[0] = TextureUtil.func_177053_a(var30.getInputStream());

					if (var311 != null) {
						sheetHeight = var311[0].getWidth();

						if (sheetHeight < minSpriteSize || mipmapLevels > 0) {
							var311[0] = mipmapLevels > 0 ? TextureUtils.scaleToPowerOfTwo(var311[0], minSpriteSize)
									: TextureUtils.scaleMinTo(var311[0], minSpriteSize);
							final int listSprites = var311[0].getWidth();

							if (listSprites != sheetHeight) {
								if (!TextureUtils.isPowerOfTwo(sheetHeight)) {
									Config.log("Scaled non power of 2: " + var26.getIconName() + ", " + sheetHeight
											+ " -> " + listSprites);
								} else {
									Config.log("Scaled too small texture: " + var26.getIconName() + ", " + sheetHeight
											+ " -> " + listSprites);
								}
							}
						}
					}

					final TextureMetadataSection sheetHeight1 = (TextureMetadataSection) var30.getMetadata("texture");

					if (sheetHeight1 != null) {
						listSprites1 = sheetHeight1.getListMipmaps();
						int it;

						if (!listSprites1.isEmpty()) {
							final int tas = var311[0].getWidth();
							it = var311[0].getHeight();

							if (MathHelper.roundUpToPowerOfTwo(tas) != tas
									|| MathHelper.roundUpToPowerOfTwo(it) != it) {
								throw new RuntimeException(
										"Unable to load extra miplevels, source-texture is not power of two");
							}
						}

						final Iterator tas1 = listSprites1.iterator();

						while (tas1.hasNext()) {
							it = ((Integer) tas1.next());

							if (it > 0 && it < var311.length - 1 && var311[it] == null) {
								final ResourceLocation ss = completeResourceLocation(var27, it);

								try {
									var311[it] = TextureUtil
											.func_177053_a(ShadersTex.loadResource(p_110571_1_, ss).getInputStream());
								} catch (final IOException var251) {
									logger.error("Unable to load miplevel {} from: {}",
											new Object[] { it, ss, var251 });
								}
							}
						}
					}

					final AnimationMetadataSection listSprites2 = (AnimationMetadataSection) var30
							.getMetadata("animation");
					var26.func_180598_a(var311, listSprites2);
				} catch (final RuntimeException var261) {
					logger.error("Unable to parse metadata from " + var28, var261);
					ReflectorForge.FMLClientHandler_trackBrokenTexture(var28, var261.getMessage());
					continue;
				} catch (final IOException var271) {
					logger.error("Using missing texture, unable to load " + var28 + ", " + var271.getClass().getName());
					ReflectorForge.FMLClientHandler_trackMissingTexture(var28);
					continue;
				}

				var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
				final int var301 = Math.min(Integer.lowestOneBit(var26.getIconWidth()),
						Integer.lowestOneBit(var26.getIconHeight()));

				if (var301 < var5) {
					logger.warn("Texture {} with size {}x{} limits mip level from {} to {}",
							new Object[] { var28, var26.getIconWidth(),
                                                            var26.getIconHeight(),
                                                            MathHelper.calculateLogBaseTwo(var5),
                                                            MathHelper.calculateLogBaseTwo(var301) });
					var5 = var301;
				}

				var31.addSprite(var26);
			}
		}

		final int var252 = Math.min(var4, var5);
		int var262 = MathHelper.calculateLogBaseTwo(var252);

		if (var262 < 0) {
			var262 = 0;
		}

		if (var262 < mipmapLevels) {
			logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] {
					basePath, mipmapLevels, var262, var252});
			mipmapLevels = var262;
		}

		final Iterator var272 = mapRegisteredSprites.values().iterator();

		while (var272.hasNext() && !skipFirst) {
			final TextureAtlasSprite var281 = (TextureAtlasSprite) var272.next();

			try {
				var281.generateMipmaps(mipmapLevels);
			} catch (final Throwable var24) {
				final CrashReport var311 = CrashReport.makeCrashReport(var24, "Applying mipmap");
				final CrashReportCategory sheetWidth = var311.makeCategory("Sprite being mipmapped");
				sheetWidth.addCrashSectionCallable("Sprite name", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001059";
					@Override
					public String call() {
						return var281.getIconName();
					}
				});
				sheetWidth.addCrashSectionCallable("Sprite size", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001060";
					@Override
					public String call() {
						return var281.getIconWidth() + " x " + var281.getIconHeight();
					}
				});
				sheetWidth.addCrashSectionCallable("Sprite frames", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001061";
					@Override
					public String call() {
						return var281.getFrameCount() + " frames";
					}
				});
				sheetWidth.addCrashSection("Mipmap levels", mipmapLevels);
				throw new ReportedException(var311);
			}
		}

		missingImage.generateMipmaps(mipmapLevels);
		var31.addSprite(missingImage);
		skipFirst = false;

		try {
			var31.doStitch();
		} catch (final StitcherException var23) {
			throw var23;
		}

		logger.info("Created: {}x{} {}-atlas", new Object[] { var31.getCurrentWidth(),
                    var31.getCurrentHeight(), basePath });

		if (Config.isShaders()) {
			ShadersTex.allocateTextureMap(getGlTextureId(), mipmapLevels, var31.getCurrentWidth(),
					var31.getCurrentHeight(), var31, this);
		} else {
			TextureUtil.func_180600_a(getGlTextureId(), mipmapLevels, var31.getCurrentWidth(),
					var31.getCurrentHeight());
		}

		final HashMap var282 = Maps.newHashMap(mapRegisteredSprites);
		Iterator var302 = var31.getStichSlots().iterator();
		TextureAtlasSprite var312;

		while (var302.hasNext()) {
			var312 = (TextureAtlasSprite) var302.next();

			if (Config.isShaders()) {
				ShadersTex.setIconName(ShadersTex.setSprite(var312).getIconName());
			}

			final String sheetWidth1 = var312.getIconName();
			var282.remove(sheetWidth1);
			mapUploadedSprites.put(sheetWidth1, var312);

			try {
				if (Config.isShaders()) {
					ShadersTex.uploadTexSubForLoadAtlas(var312.getFrameTextureData(0), var312.getIconWidth(),
							var312.getIconHeight(), var312.getOriginX(), var312.getOriginY(), false, false);
				} else {
					TextureUtil.uploadTextureMipmap(var312.getFrameTextureData(0), var312.getIconWidth(),
							var312.getIconHeight(), var312.getOriginX(), var312.getOriginY(), false, false);
				}
			} catch (final Throwable var22) {
				final CrashReport listSprites3 = CrashReport.makeCrashReport(var22, "Stitching texture atlas");
				final CrashReportCategory it1 = listSprites3.makeCategory("Texture being stitched together");
				it1.addCrashSection("Atlas path", basePath);
				it1.addCrashSection("Sprite", var312);
				throw new ReportedException(listSprites3);
			}

			if (var312.hasAnimationMetadata()) {
				listAnimatedSprites.add(var312);
			}
		}

		var302 = var282.values().iterator();

		while (var302.hasNext()) {
			var312 = (TextureAtlasSprite) var302.next();
			var312.copyFrom(missingImage);
		}

		if (Config.isMultiTexture()) {
			final int sheetWidth2 = var31.getCurrentWidth();
			sheetHeight = var31.getCurrentHeight();
			listSprites1 = var31.getStichSlots();
			final Iterator it2 = listSprites1.iterator();

			while (it2.hasNext()) {
				final TextureAtlasSprite tas2 = (TextureAtlasSprite) it2.next();
				tas2.sheetWidth = sheetWidth2;
				tas2.sheetHeight = sheetHeight;
				tas2.mipmapLevels = mipmapLevels;
				final TextureAtlasSprite ss1 = tas2.spriteSingle;

				if (ss1 != null) {
					ss1.sheetWidth = sheetWidth2;
					ss1.sheetHeight = sheetHeight;
					ss1.mipmapLevels = mipmapLevels;
					tas2.bindSpriteTexture();
					final boolean texBlur = false;
					final boolean texClamp = true;
					TextureUtil.uploadTextureMipmap(ss1.getFrameTextureData(0), ss1.getIconWidth(), ss1.getIconHeight(),
							ss1.getOriginX(), ss1.getOriginY(), texBlur, texClamp);
				}
			}

			Config.getMinecraft();
			Minecraft.getTextureManager().bindTexture(locationBlocksTexture);
		}

		Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
		updateIconGrid(var31.getCurrentWidth(), var31.getCurrentHeight());

		if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
			Config.dbg("Exporting texture map to: " + basePath + "_x.png");
			TextureUtil.func_177055_a(basePath.replaceAll("/", "_"), getGlTextureId(), mipmapLevels,
					var31.getCurrentWidth(), var31.getCurrentHeight());
		}
	}

	public ResourceLocation completeResourceLocation(final ResourceLocation p_147634_1_, final int p_147634_2_) {
		return isAbsoluteLocation(p_147634_1_)
				? p_147634_2_ == 0
						? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + ".png")
						: new ResourceLocation(p_147634_1_.getResourceDomain(),
								p_147634_1_.getResourcePath() + "mipmap" + p_147634_2_ + ".png")
				: p_147634_2_ == 0
						? new ResourceLocation(p_147634_1_.getResourceDomain(),
								String.format("%s/%s%s",
										new Object[] { basePath, p_147634_1_.getResourcePath(), ".png" }))
						: new ResourceLocation(p_147634_1_.getResourceDomain(),
								String.format("%s/mipmaps/%s.%d%s", new Object[] { basePath,
										p_147634_1_.getResourcePath(), p_147634_2_, ".png" }));
	}

	public TextureAtlasSprite getAtlasSprite(final String p_110572_1_) {
		TextureAtlasSprite var2 = (TextureAtlasSprite) mapUploadedSprites.get(p_110572_1_);

		if (var2 == null) {
			var2 = missingImage;
		}

		return var2;
	}

	public void updateAnimations() {
		if (Config.isShaders()) {
			ShadersTex.updatingTex = getMultiTexID();
		}

		TextureUtil.bindTexture(getGlTextureId());
		final Iterator var1 = listAnimatedSprites.iterator();

		while (var1.hasNext()) {
			final TextureAtlasSprite it = (TextureAtlasSprite) var1.next();

			if (isTerrainAnimationActive(it)) {
				it.updateAnimation();
			}
		}

		if (Config.isMultiTexture()) {
			final Iterator it1 = listAnimatedSprites.iterator();

			while (it1.hasNext()) {
				final TextureAtlasSprite ts = (TextureAtlasSprite) it1.next();

				if (isTerrainAnimationActive(ts)) {
					final TextureAtlasSprite spriteSingle = ts.spriteSingle;

					if (spriteSingle != null) {
						if (ts == TextureUtils.iconClock || ts == TextureUtils.iconCompass) {
							spriteSingle.frameCounter = ts.frameCounter;
						}

						ts.bindSpriteTexture();
						spriteSingle.updateAnimation();
					}
				}
			}

			TextureUtil.bindTexture(getGlTextureId());
		}

		if (Config.isShaders()) {
			ShadersTex.updatingTex = null;
		}
	}

	public TextureAtlasSprite func_174942_a(final ResourceLocation p_174942_1_) {
		if (p_174942_1_ == null) {
			throw new IllegalArgumentException("Location cannot be null!");
		} else {
			TextureAtlasSprite var2 = (TextureAtlasSprite) mapRegisteredSprites.get(p_174942_1_.toString());

			if (var2 == null) {
				var2 = TextureAtlasSprite.func_176604_a(p_174942_1_);
				mapRegisteredSprites.put(p_174942_1_.toString(), var2);

				if (var2 instanceof TextureAtlasSprite && var2.getIndexInMap() < 0) {
					var2.setIndexInMap(mapRegisteredSprites.size());
				}
			}

			return var2;
		}
	}

	@Override
	public void tick() {
		updateAnimations();
	}

	public void setMipmapLevels(final int p_147633_1_) {
		mipmapLevels = p_147633_1_;
	}

	public TextureAtlasSprite func_174944_f() {
		return missingImage;
	}

	public TextureAtlasSprite getTextureExtry(final String name) {
		final ResourceLocation loc = new ResourceLocation(name);
		return (TextureAtlasSprite) mapRegisteredSprites.get(loc.toString());
	}

	public boolean setTextureEntry(final String name, final TextureAtlasSprite entry) {
		if (!mapRegisteredSprites.containsKey(name)) {
			mapRegisteredSprites.put(name, entry);

			if (entry.getIndexInMap() < 0) {
				entry.setIndexInMap(mapRegisteredSprites.size());
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean isAbsoluteLocation(final ResourceLocation loc) {
		final String path = loc.getResourcePath();
		return isAbsoluteLocationPath(path);
	}

	private boolean isAbsoluteLocationPath(final String resPath) {
		final String path = resPath.toLowerCase();
		return path.startsWith("mcpatcher/") || path.startsWith("optifine/");
	}

	public TextureAtlasSprite getSpriteSafe(final String name) {
		final ResourceLocation loc = new ResourceLocation(name);
		return (TextureAtlasSprite) mapRegisteredSprites.get(loc.toString());
	}

	private boolean isTerrainAnimationActive(final TextureAtlasSprite ts) {
		return ts != TextureUtils.iconWaterStill && ts != TextureUtils.iconWaterFlow
				? ts != TextureUtils.iconLavaStill && ts != TextureUtils.iconLavaFlow
						? ts != TextureUtils.iconFireLayer0 && ts != TextureUtils.iconFireLayer1
								? ts == TextureUtils.iconPortal ? Config.isAnimatedPortal()
										: ts != TextureUtils.iconClock && ts != TextureUtils.iconCompass
												? Config.isAnimatedTerrain() : true
								: Config.isAnimatedFire()
						: Config.isAnimatedLava()
				: Config.isAnimatedWater();
	}

	public int getCountRegisteredSprites() {
		return mapRegisteredSprites.size();
	}

	private int detectMaxMipmapLevel(final Map mapSprites, final IResourceManager rm) {
		int minSize = detectMinimumSpriteSize(mapSprites, rm, 20);

		if (minSize < 16) {
			minSize = 16;
		}

		minSize = MathHelper.roundUpToPowerOfTwo(minSize);

		if (minSize > 16) {
			Config.log("Sprite size: " + minSize);
		}

		int minLevel = MathHelper.calculateLogBaseTwo(minSize);

		if (minLevel < 4) {
			minLevel = 4;
		}

		return minLevel;
	}

	private int detectMinimumSpriteSize(final Map mapSprites, final IResourceManager rm, final int percentScale) {
		final HashMap mapSizeCounts = new HashMap();
		final Set entrySetSprites = mapSprites.entrySet();
		final Iterator countSprites = entrySetSprites.iterator();
		int count;

		while (countSprites.hasNext()) {
			final Entry setSizes = (Entry) countSprites.next();
			final TextureAtlasSprite setSizesSorted = (TextureAtlasSprite) setSizes.getValue();
			final ResourceLocation minSize = new ResourceLocation(setSizesSorted.getIconName());
			final ResourceLocation countScale = completeResourceLocation(minSize, 0);

			if (!setSizesSorted.hasCustomLoader(rm, minSize)) {
				try {
					final IResource countScaleMax = rm.getResource(countScale);

					if (countScaleMax != null) {
						final InputStream it = countScaleMax.getInputStream();

						if (it != null) {
							final Dimension size = TextureUtils.getImageSize(it, "png");

							if (size != null) {
								count = size.width;
								final int width2 = MathHelper.roundUpToPowerOfTwo(count);

								if (!mapSizeCounts.containsKey(width2)) {
									mapSizeCounts.put(width2, 1);
								} else {
									final int count1 = ((Integer) mapSizeCounts.get(width2));
									mapSizeCounts.put(width2, count1 + 1);
								}
							}
						}
					}
				} catch (final Exception var17) {
				}
			}
		}

		int countSprites1 = 0;
		final Set setSizes1 = mapSizeCounts.keySet();
		final TreeSet setSizesSorted1 = new TreeSet(setSizes1);
		int countScale1;
		int countScaleMax1;

		for (final Iterator minSize1 = setSizesSorted1.iterator(); minSize1
				.hasNext(); countSprites1 += countScaleMax1) {
			countScale1 = ((Integer) minSize1.next());
			countScaleMax1 = ((Integer) mapSizeCounts.get(countScale1));
		}

		int minSize2 = 16;
		countScale1 = 0;
		countScaleMax1 = countSprites1 * percentScale / 100;
		final Iterator it1 = setSizesSorted1.iterator();

		do {
			if (!it1.hasNext()) {
				return minSize2;
			}

			final int size1 = ((Integer) it1.next());
			count = ((Integer) mapSizeCounts.get(size1));
			countScale1 += count;

			if (size1 > minSize2) {
				minSize2 = size1;
			}
		}
		while (countScale1 <= countScaleMax1);

		return minSize2;
	}

	private int getMinSpriteSize() {
		int minSize = 1 << mipmapLevels;

		if (minSize < 8) {
			minSize = 8;
		}

		return minSize;
	}

	private int[] getMissingImageData(final int size) {
		final BufferedImage bi = new BufferedImage(16, 16, 2);
		bi.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
		final BufferedImage bi2 = TextureUtils.scaleToPowerOfTwo(bi, size);
		final int[] data = new int[size * size];
		bi2.getRGB(0, 0, size, size, data, 0, size);
		return data;
	}

	public boolean isTextureBound() {
		final int boundTexId = GlStateManager.getBoundTexture();
		final int texId = getGlTextureId();
		return boundTexId == texId;
	}

	private void updateIconGrid(final int sheetWidth, final int sheetHeight) {
		iconGridCountX = -1;
		iconGridCountY = -1;
		iconGrid = null;

		if (iconGridSize > 0) {
			iconGridCountX = sheetWidth / iconGridSize;
			iconGridCountY = sheetHeight / iconGridSize;
			iconGrid = new TextureAtlasSprite[iconGridCountX * iconGridCountY];
			iconGridSizeU = 1.0D / iconGridCountX;
			iconGridSizeV = 1.0D / iconGridCountY;
			final Iterator it = mapUploadedSprites.values().iterator();

			while (it.hasNext()) {
				final TextureAtlasSprite ts = (TextureAtlasSprite) it.next();
				final double deltaU = 0.5D / sheetWidth;
				final double deltaV = 0.5D / sheetHeight;
				final double uMin = Math.min(ts.getMinU(), ts.getMaxU()) + deltaU;
				final double vMin = Math.min(ts.getMinV(), ts.getMaxV()) + deltaV;
				final double uMax = Math.max(ts.getMinU(), ts.getMaxU()) - deltaU;
				final double vMax = Math.max(ts.getMinV(), ts.getMaxV()) - deltaV;
				final int iuMin = (int) (uMin / iconGridSizeU);
				final int ivMin = (int) (vMin / iconGridSizeV);
				final int iuMax = (int) (uMax / iconGridSizeU);
				final int ivMax = (int) (vMax / iconGridSizeV);

				for (int iu = iuMin; iu <= iuMax; ++iu) {
					if (iu >= 0 && iu < iconGridCountX) {
						for (int iv = ivMin; iv <= ivMax; ++iv) {
							if (iv >= 0 && iv < iconGridCountX) {
								final int index = iv * iconGridCountX + iu;
								iconGrid[index] = ts;
							} else {
								Config.warn("Invalid grid V: " + iv + ", icon: " + ts.getIconName());
							}
						}
					} else {
						Config.warn("Invalid grid U: " + iu + ", icon: " + ts.getIconName());
					}
				}
			}
		}
	}

	public TextureAtlasSprite getIconByUV(final double u, final double v) {
		if (iconGrid == null) {
			return null;
		} else {
			final int iu = (int) (u / iconGridSizeU);
			final int iv = (int) (v / iconGridSizeV);
			final int index = iv * iconGridCountX + iu;
			return index >= 0 && index <= iconGrid.length ? iconGrid[index] : null;
		}
	}
}
