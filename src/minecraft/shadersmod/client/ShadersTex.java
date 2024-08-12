package shadersmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import shadersmod.common.SMCLog;

public class ShadersTex {

public static final int EaZy = 2019;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final int initialBufferSize = 1048576;
	public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
	public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
	public static int[] intArray = new int[1048576];
	public static final int defBaseTexColor = 0;
	public static final int defNormTexColor = -8421377;
	public static final int defSpecTexColor = 0;
	public static Map<Integer, MultiTexID> multiTexMap = new HashMap();
	public static TextureMap updatingTextureMap = null;
	public static TextureAtlasSprite updatingSprite = null;
	public static MultiTexID updatingTex = null;
	public static MultiTexID boundTex = null;
	public static int updatingPage = 0;
	public static String iconName = null;
	public static IResourceManager resManager = null;
	static ResourceLocation resLocation = null;
	static int imageSize = 0;

	public static IntBuffer getIntBuffer(final int size) {
		if (intBuffer.capacity() < size) {
			final int bufferSize = roundUpPOT(size);
			byteBuffer = BufferUtils.createByteBuffer(bufferSize * 4);
			intBuffer = byteBuffer.asIntBuffer();
		}

		return intBuffer;
	}

	public static int[] getIntArray(final int size) {
		if (intArray == null) {
			intArray = new int[1048576];
		}

		if (intArray.length < size) {
			intArray = new int[roundUpPOT(size)];
		}

		return intArray;
	}

	public static int roundUpPOT(final int x) {
		int i = x - 1;
		i |= i >> 1;
		i |= i >> 2;
		i |= i >> 4;
		i |= i >> 8;
		i |= i >> 16;
		return i + 1;
	}

	public static int log2(int x) {
		int log = 0;

		if ((x & -65536) != 0) {
			log += 16;
			x >>= 16;
		}

		if ((x & 65280) != 0) {
			log += 8;
			x >>= 8;
		}

		if ((x & 240) != 0) {
			log += 4;
			x >>= 4;
		}

		if ((x & 6) != 0) {
			log += 2;
			x >>= 2;
		}

		if ((x & 2) != 0) {
			++log;
		}

		return log;
	}

	public static IntBuffer fillIntBuffer(final int size, final int value) {
		getIntArray(size);
		getIntBuffer(size);
		Arrays.fill(intArray, 0, size, value);
		intBuffer.put(intArray, 0, size);
		return intBuffer;
	}

	public static int[] createAIntImage(final int size) {
		final int[] aint = new int[size * 3];
		Arrays.fill(aint, 0, size, 0);
		Arrays.fill(aint, size, size * 2, -8421377);
		Arrays.fill(aint, size * 2, size * 3, 0);
		return aint;
	}

	public static int[] createAIntImage(final int size, final int color) {
		final int[] aint = new int[size * 3];
		Arrays.fill(aint, 0, size, color);
		Arrays.fill(aint, size, size * 2, -8421377);
		Arrays.fill(aint, size * 2, size * 3, 0);
		return aint;
	}

	public static MultiTexID getMultiTexID(final AbstractTexture tex) {
		MultiTexID multiTex = tex.multiTex;

		if (multiTex == null) {
			final int baseTex = tex.getGlTextureId();
			multiTex = multiTexMap.get(baseTex);

			if (multiTex == null) {
				multiTex = new MultiTexID(baseTex, GL11.glGenTextures(), GL11.glGenTextures());
				multiTexMap.put(baseTex, multiTex);
			}

			tex.multiTex = multiTex;
		}

		return multiTex;
	}

	public static void deleteTextures(final AbstractTexture atex, final int texid) {
		final MultiTexID multiTex = atex.multiTex;

		if (multiTex != null) {
			atex.multiTex = null;
			multiTexMap.remove(multiTex.base);
			GlStateManager.func_179150_h(multiTex.norm);
			GlStateManager.func_179150_h(multiTex.spec);

			if (multiTex.base != texid) {
				SMCLog.warning("Error : MultiTexID.base mismatch: " + multiTex.base + ", texid: " + texid);
				GlStateManager.func_179150_h(multiTex.base);
			}
		}
	}

	public static void bindNSTextures(final int normTex, final int specTex) {
		if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
			GlStateManager.setActiveTexture(33986);
			GlStateManager.func_179144_i(normTex);
			GlStateManager.setActiveTexture(33987);
			GlStateManager.func_179144_i(specTex);
			GlStateManager.setActiveTexture(33984);
		}
	}

	public static void bindNSTextures(final MultiTexID multiTex) {
		bindNSTextures(multiTex.norm, multiTex.spec);
	}

	public static void bindTextures(final int baseTex, final int normTex, final int specTex) {
		if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
			GlStateManager.setActiveTexture(33986);
			GlStateManager.func_179144_i(normTex);
			GlStateManager.setActiveTexture(33987);
			GlStateManager.func_179144_i(specTex);
			GlStateManager.setActiveTexture(33984);
		}

		GlStateManager.func_179144_i(baseTex);
	}

	public static void bindTextures(final MultiTexID multiTex) {
		boundTex = multiTex;

		if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
			if (Shaders.configNormalMap) {
				GlStateManager.setActiveTexture(33986);
				GlStateManager.func_179144_i(multiTex.norm);
			}

			if (Shaders.configSpecularMap) {
				GlStateManager.setActiveTexture(33987);
				GlStateManager.func_179144_i(multiTex.spec);
			}

			GlStateManager.setActiveTexture(33984);
		}

		GlStateManager.func_179144_i(multiTex.base);
	}

	public static void bindTexture(final ITextureObject tex) {
		tex.getGlTextureId();

		if (tex instanceof TextureMap) {
			Shaders.atlasSizeX = ((TextureMap) tex).atlasWidth;
			Shaders.atlasSizeY = ((TextureMap) tex).atlasHeight;
			bindTextures(tex.getMultiTexID());
		} else {
			Shaders.atlasSizeX = 0;
			Shaders.atlasSizeY = 0;
			bindTextures(tex.getMultiTexID());
		}
	}

	public static void bindTextureMapForUpdateAndRender(final TextureManager tm, final ResourceLocation resLoc) {
		final TextureMap tex = (TextureMap) tm.getTexture(resLoc);
		Shaders.atlasSizeX = tex.atlasWidth;
		Shaders.atlasSizeY = tex.atlasHeight;
		bindTextures(updatingTex = tex.getMultiTexID());
	}

	public static void bindTextures(final int baseTex) {
		final MultiTexID multiTex = multiTexMap.get(baseTex);
		bindTextures(multiTex);
	}

	public static void initDynamicTexture(final int texID, final int width, final int height,
			final DynamicTexture tex) {
		final MultiTexID multiTex = tex.getMultiTexID();
		final int[] aint = tex.getTextureData();
		final int size = width * height;
		Arrays.fill(aint, size, size * 2, -8421377);
		Arrays.fill(aint, size * 2, size * 3, 0);
		TextureUtil.allocateTexture(multiTex.base, width, height);
		TextureUtil.func_147954_b(false, false);
		TextureUtil.setTextureClamped(false);
		TextureUtil.allocateTexture(multiTex.norm, width, height);
		TextureUtil.func_147954_b(false, false);
		TextureUtil.setTextureClamped(false);
		TextureUtil.allocateTexture(multiTex.spec, width, height);
		TextureUtil.func_147954_b(false, false);
		TextureUtil.setTextureClamped(false);
		GlStateManager.func_179144_i(multiTex.base);
	}

	public static void updateDynamicTexture(final int texID, final int[] src, final int width, final int height,
			final DynamicTexture tex) {
		final MultiTexID multiTex = tex.getMultiTexID();
		GlStateManager.func_179144_i(multiTex.base);
		updateDynTexSubImage1(src, width, height, 0, 0, 0);
		GlStateManager.func_179144_i(multiTex.norm);
		updateDynTexSubImage1(src, width, height, 0, 0, 1);
		GlStateManager.func_179144_i(multiTex.spec);
		updateDynTexSubImage1(src, width, height, 0, 0, 2);
		GlStateManager.func_179144_i(multiTex.base);
	}

	public static void updateDynTexSubImage1(final int[] src, final int width, final int height, final int posX,
			final int posY, final int page) {
		final int size = width * height;
		final IntBuffer intBuf = getIntBuffer(size);
		intBuf.clear();
		final int offset = page * size;

		if (src.length >= offset + size) {
			intBuf.put(src, offset, size).position(0).limit(size);
			GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA,
					GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
			intBuf.clear();
		}
	}

	public static ITextureObject createDefaultTexture() {
		final DynamicTexture tex = new DynamicTexture(1, 1);
		tex.getTextureData()[0] = -1;
		tex.updateDynamicTexture();
		return tex;
	}

	public static void allocateTextureMap(final int texID, final int mipmapLevels, final int width, final int height,
			final Stitcher stitcher, final TextureMap tex) {
		SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
		updatingTextureMap = tex;
		tex.atlasWidth = width;
		tex.atlasHeight = height;
		final MultiTexID multiTex = getMultiTexID(tex);
		updatingTex = multiTex;
		TextureUtil.func_180600_a(multiTex.base, mipmapLevels, width, height);

		if (Shaders.configNormalMap) {
			TextureUtil.func_180600_a(multiTex.norm, mipmapLevels, width, height);
		}

		if (Shaders.configSpecularMap) {
			TextureUtil.func_180600_a(multiTex.spec, mipmapLevels, width, height);
		}

		GlStateManager.func_179144_i(texID);
	}

	public static TextureAtlasSprite setSprite(final TextureAtlasSprite tas) {
		updatingSprite = tas;
		return tas;
	}

	public static String setIconName(final String name) {
		iconName = name;
		return name;
	}

	public static void uploadTexSubForLoadAtlas(final int[][] data, final int width, final int height,
			final int xoffset, final int yoffset, final boolean linear, final boolean clamp) {
		TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
		final boolean border = false;
		int[][] aaint;

		if (Shaders.configNormalMap) {
			aaint = readImageAndMipmaps(iconName + "_n", width, height, data.length, border, -8421377);
			GlStateManager.func_179144_i(updatingTex.norm);
			TextureUtil.uploadTextureMipmap(aaint, width, height, xoffset, yoffset, linear, clamp);
		}

		if (Shaders.configSpecularMap) {
			aaint = readImageAndMipmaps(iconName + "_s", width, height, data.length, border, 0);
			GlStateManager.func_179144_i(updatingTex.spec);
			TextureUtil.uploadTextureMipmap(aaint, width, height, xoffset, yoffset, linear, clamp);
		}

		GlStateManager.func_179144_i(updatingTex.base);
	}

	public static int[][] readImageAndMipmaps(final String name, final int width, final int height, final int numLevels,
			final boolean border, final int defColor) {
		int[][] aaint = new int[numLevels][];
		int[] aint;
		aaint[0] = aint = new int[width * height];
		boolean goodImage = false;
		final BufferedImage image = readImage(
				updatingTextureMap.completeResourceLocation(new ResourceLocation(name), 0));

		if (image != null) {
			final int imageWidth = image.getWidth();
			image.getHeight();

			if (imageWidth + (border ? 16 : 0) == width) {
				goodImage = true;
				image.getRGB(0, 0, imageWidth, imageWidth, aint, 0, imageWidth);
			}
		}

		if (!goodImage) {
			Arrays.fill(aint, defColor);
		}

		GlStateManager.func_179144_i(updatingTex.spec);
		aaint = genMipmapsSimple(aaint.length - 1, width, aaint);
		return aaint;
	}

	public static BufferedImage readImage(final ResourceLocation resLoc) {
		try {
			final IResource e = resManager.getResource(resLoc);

			if (e == null) {
				return null;
			} else {
				final InputStream istr = e.getInputStream();

				if (istr == null) {
					return null;
				} else {
					final BufferedImage image = ImageIO.read(istr);
					istr.close();
					return image;
				}
			}
		} catch (final IOException var4) {
			return null;
		}
	}

	public static int[][] genMipmapsSimple(final int maxLevel, final int width, final int[][] data) {
		for (int level = 1; level <= maxLevel; ++level) {
			if (data[level] == null) {
				final int cw = width >> level;
				final int pw = cw * 2;
				final int[] aintp = data[level - 1];
				final int[] aintc = data[level] = new int[cw * cw];

				for (int y = 0; y < cw; ++y) {
					for (int x = 0; x < cw; ++x) {
						final int ppos = y * 2 * pw + x * 2;
						aintc[y * cw + x] = blend4Simple(aintp[ppos], aintp[ppos + 1], aintp[ppos + pw],
								aintp[ppos + pw + 1]);
					}
				}
			}
		}

		return data;
	}

	public static void uploadTexSub(final int[][] data, final int width, final int height, final int xoffset,
			final int yoffset, final boolean linear, final boolean clamp) {
		TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);

		if (Shaders.configNormalMap || Shaders.configSpecularMap) {
			if (Shaders.configNormalMap) {
				GlStateManager.func_179144_i(updatingTex.norm);
				uploadTexSub1(data, width, height, xoffset, yoffset, 1);
			}

			if (Shaders.configSpecularMap) {
				GlStateManager.func_179144_i(updatingTex.spec);
				uploadTexSub1(data, width, height, xoffset, yoffset, 2);
			}

			GlStateManager.func_179144_i(updatingTex.base);
		}
	}

	public static void uploadTexSub1(final int[][] src, final int width, final int height, final int posX,
			final int posY, final int page) {
		final int size = width * height;
		final IntBuffer intBuf = getIntBuffer(size);
		final int numLevel = src.length;
		int level = 0;
		int lw = width;
		int lh = height;
		int px = posX;

		for (int py = posY; lw > 0 && lh > 0 && level < numLevel; ++level) {
			final int lsize = lw * lh;
			final int[] aint = src[level];
			intBuf.clear();

			if (aint.length >= lsize * (page + 1)) {
				intBuf.put(aint, lsize * page, lsize).position(0).limit(lsize);
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, level, px, py, lw, lh, GL12.GL_BGRA,
						GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
			}

			lw >>= 1;
			lh >>= 1;
			px >>= 1;
			py >>= 1;
		}

		intBuf.clear();
	}

	public static int blend4Alpha(final int c0, final int c1, final int c2, final int c3) {
		int a0 = c0 >>> 24 & 255;
		int a1 = c1 >>> 24 & 255;
		int a2 = c2 >>> 24 & 255;
		int a3 = c3 >>> 24 & 255;
		final int as = a0 + a1 + a2 + a3;
		final int an = (as + 2) / 4;
		int dv;

		if (as != 0) {
			dv = as;
		} else {
			dv = 4;
			a0 = 1;
			a1 = 1;
			a2 = 1;
			a3 = 1;
		}

		final int frac = (dv + 1) / 2;
		final int color = an << 24
				| ((c0 >>> 16 & 255) * a0 + (c1 >>> 16 & 255) * a1 + (c2 >>> 16 & 255) * a2 + (c3 >>> 16 & 255) * a3
						+ frac) / dv << 16
				| ((c0 >>> 8 & 255) * a0 + (c1 >>> 8 & 255) * a1 + (c2 >>> 8 & 255) * a2 + (c3 >>> 8 & 255) * a3 + frac)
						/ dv << 8
				| ((c0 >>> 0 & 255) * a0 + (c1 >>> 0 & 255) * a1 + (c2 >>> 0 & 255) * a2 + (c3 >>> 0 & 255) * a3 + frac)
						/ dv << 0;
		return color;
	}

	public static int blend4Simple(final int c0, final int c1, final int c2, final int c3) {
		final int color = ((c0 >>> 24 & 255) + (c1 >>> 24 & 255) + (c2 >>> 24 & 255) + (c3 >>> 24 & 255) + 2) / 4 << 24
				| ((c0 >>> 16 & 255) + (c1 >>> 16 & 255) + (c2 >>> 16 & 255) + (c3 >>> 16 & 255) + 2) / 4 << 16
				| ((c0 >>> 8 & 255) + (c1 >>> 8 & 255) + (c2 >>> 8 & 255) + (c3 >>> 8 & 255) + 2) / 4 << 8
				| ((c0 >>> 0 & 255) + (c1 >>> 0 & 255) + (c2 >>> 0 & 255) + (c3 >>> 0 & 255) + 2) / 4 << 0;
		return color;
	}

	public static void genMipmapAlpha(final int[] aint, final int offset, final int width, final int height) {
		Math.min(width, height);
		int o2 = offset;
		int w2 = width;
		int h2 = height;
		int o1 = 0;
		int w1 = 0;
		int level;
		int p2;
		int y;
		int x;

		for (level = 0; w2 > 1 && h2 > 1; o2 = o1) {
			o1 = o2 + w2 * h2;
			w1 = w2 / 2;
			final int var16 = h2 / 2;

			for (p2 = 0; p2 < var16; ++p2) {
				y = o1 + p2 * w1;
				x = o2 + p2 * 2 * w2;

				for (int x1 = 0; x1 < w1; ++x1) {
					aint[y + x1] = blend4Alpha(aint[x + x1 * 2], aint[x + x1 * 2 + 1], aint[x + w2 + x1 * 2],
							aint[x + w2 + x1 * 2 + 1]);
				}
			}

			++level;
			w2 = w1;
			h2 = var16;
		}

		while (level > 0) {
			--level;
			w2 = width >> level;
			h2 = height >> level;
			o2 = o1 - w2 * h2;
			p2 = o2;

			for (y = 0; y < h2; ++y) {
				for (x = 0; x < w2; ++x) {
					if (aint[p2] == 0) {
						aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 16777215;
					}

					++p2;
				}
			}

			o1 = o2;
			w1 = w2;
		}
	}

	public static void genMipmapSimple(final int[] aint, final int offset, final int width, final int height) {
		Math.min(width, height);
		int o2 = offset;
		int w2 = width;
		int h2 = height;
		int o1 = 0;
		int w1 = 0;
		int level;
		int p2;
		int y;
		int x;

		for (level = 0; w2 > 1 && h2 > 1; o2 = o1) {
			o1 = o2 + w2 * h2;
			w1 = w2 / 2;
			final int var16 = h2 / 2;

			for (p2 = 0; p2 < var16; ++p2) {
				y = o1 + p2 * w1;
				x = o2 + p2 * 2 * w2;

				for (int x1 = 0; x1 < w1; ++x1) {
					aint[y + x1] = blend4Simple(aint[x + x1 * 2], aint[x + x1 * 2 + 1], aint[x + w2 + x1 * 2],
							aint[x + w2 + x1 * 2 + 1]);
				}
			}

			++level;
			w2 = w1;
			h2 = var16;
		}

		while (level > 0) {
			--level;
			w2 = width >> level;
			h2 = height >> level;
			o2 = o1 - w2 * h2;
			p2 = o2;

			for (y = 0; y < h2; ++y) {
				for (x = 0; x < w2; ++x) {
					if (aint[p2] == 0) {
						aint[p2] = aint[o1 + y / 2 * w1 + x / 2] & 16777215;
					}

					++p2;
				}
			}

			o1 = o2;
			w1 = w2;
		}
	}

	public static boolean isSemiTransparent(final int[] aint, final int width, final int height) {
		final int size = width * height;

		if (aint[0] >>> 24 == 255 && aint[size - 1] == 0) {
			return true;
		} else {
			for (int i = 0; i < size; ++i) {
				final int alpha = aint[i] >>> 24;

				if (alpha != 0 && alpha != 255) {
					return true;
				}
			}

			return false;
		}
	}

	public static void updateSubTex1(final int[] src, final int width, final int height, final int posX,
			final int posY) {
		int level = 0;
		int cw = width;
		int ch = height;
		int cx = posX;

		for (int cy = posY; cw > 0 && ch > 0; cy /= 2) {
			GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, level, cx, cy, 0, 0, cw, ch);
			++level;
			cw /= 2;
			ch /= 2;
			cx /= 2;
		}
	}

	public static void setupTexture(final MultiTexID multiTex, final int[] src, final int width, final int height,
			final boolean linear, final boolean clamp) {
		final int mmfilter = linear ? 9729 : 9728;
		final int wraptype = clamp ? 10496 : 10497;
		final int size = width * height;
		final IntBuffer intBuf = getIntBuffer(size);
		intBuf.clear();
		intBuf.put(src, 0, size).position(0).limit(size);
		GlStateManager.func_179144_i(multiTex.base);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wraptype);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wraptype);
		intBuf.put(src, size, size).position(0).limit(size);
		GlStateManager.func_179144_i(multiTex.norm);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wraptype);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wraptype);
		intBuf.put(src, size * 2, size).position(0).limit(size);
		GlStateManager.func_179144_i(multiTex.spec);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mmfilter);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wraptype);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wraptype);
		GlStateManager.func_179144_i(multiTex.base);
	}

	public static void updateSubImage(final MultiTexID multiTex, final int[] src, final int width, final int height,
			final int posX, final int posY, final boolean linear, final boolean clamp) {
		final int size = width * height;
		final IntBuffer intBuf = getIntBuffer(size);
		intBuf.clear();
		intBuf.put(src, 0, size);
		intBuf.position(0).limit(size);
		GlStateManager.func_179144_i(multiTex.base);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);

		if (src.length == size * 3) {
			intBuf.clear();
			intBuf.put(src, size, size).position(0);
			intBuf.position(0).limit(size);
		}

		GlStateManager.func_179144_i(multiTex.norm);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);

		if (src.length == size * 3) {
			intBuf.clear();
			intBuf.put(src, size * 2, size);
			intBuf.position(0).limit(size);
		}

		GlStateManager.func_179144_i(multiTex.spec);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, posX, posY, width, height, GL12.GL_BGRA,
				GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuf);
		GlStateManager.setActiveTexture(33984);
	}

	public static ResourceLocation getNSMapLocation(final ResourceLocation location, final String mapName) {
		final String basename = location.getResourcePath();
		final String[] basenameParts = basename.split(".png");
		final String basenameNoFileType = basenameParts[0];
		return new ResourceLocation(location.getResourceDomain(), basenameNoFileType + "_" + mapName + ".png");
	}

	public static void loadNSMap(final IResourceManager manager, final ResourceLocation location, final int width,
			final int height, final int[] aint) {
		if (Shaders.configNormalMap) {
			loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
		}

		if (Shaders.configSpecularMap) {
			loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
		}
	}

	public static void loadNSMap1(final IResourceManager manager, final ResourceLocation location, final int width,
			final int height, final int[] aint, final int offset, final int defaultColor) {
		boolean good = false;

		try {
			final IResource ex = manager.getResource(location);
			final BufferedImage bufferedimage = ImageIO.read(ex.getInputStream());

			if (bufferedimage != null && bufferedimage.getWidth() == width && bufferedimage.getHeight() == height) {
				bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
				good = true;
			}
		} catch (final IOException var10) {
		}

		if (!good) {
			Arrays.fill(aint, offset, offset + width * height, defaultColor);
		}
	}

	public static int loadSimpleTexture(final int textureID, final BufferedImage bufferedimage, final boolean linear,
			final boolean clamp, final IResourceManager resourceManager, final ResourceLocation location,
			final MultiTexID multiTex) {
		final int width = bufferedimage.getWidth();
		final int height = bufferedimage.getHeight();
		final int size = width * height;
		final int[] aint = getIntArray(size * 3);
		bufferedimage.getRGB(0, 0, width, height, aint, 0, width);
		loadNSMap(resourceManager, location, width, height, aint);
		setupTexture(multiTex, aint, width, height, linear, clamp);
		return textureID;
	}

	public static void mergeImage(final int[] aint, final int dstoff, final int srcoff, final int size) {}

	public static int blendColor(final int color1, final int color2, final int factor1) {
		final int factor2 = 255 - factor1;
		return ((color1 >>> 24 & 255) * factor1 + (color2 >>> 24 & 255) * factor2) / 255 << 24
				| ((color1 >>> 16 & 255) * factor1 + (color2 >>> 16 & 255) * factor2) / 255 << 16
				| ((color1 >>> 8 & 255) * factor1 + (color2 >>> 8 & 255) * factor2) / 255 << 8
				| ((color1 >>> 0 & 255) * factor1 + (color2 >>> 0 & 255) * factor2) / 255 << 0;
	}

	public static void loadLayeredTexture(final LayeredTexture tex, final IResourceManager manager, final List list) {
		int width = 0;
		int height = 0;
		int size = 0;
		int[] image = null;
		final Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			final String s = (String) iterator.next();

			if (s != null) {
				try {
					final ResourceLocation ex = new ResourceLocation(s);
					final InputStream inputstream = manager.getResource(ex).getInputStream();
					final BufferedImage bufimg = ImageIO.read(inputstream);

					if (size == 0) {
						width = bufimg.getWidth();
						height = bufimg.getHeight();
						size = width * height;
						image = createAIntImage(size, 0);
					}

					final int[] aint = getIntArray(size * 3);
					bufimg.getRGB(0, 0, width, height, aint, 0, width);
					loadNSMap(manager, ex, width, height, aint);

					for (int i = 0; i < size; ++i) {
						final int alpha = aint[i] >>> 24 & 255;
						image[size * 0 + i] = blendColor(aint[size * 0 + i], image[size * 0 + i], alpha);
						image[size * 1 + i] = blendColor(aint[size * 1 + i], image[size * 1 + i], alpha);
						image[size * 2 + i] = blendColor(aint[size * 2 + i], image[size * 2 + i], alpha);
					}
				} catch (final IOException var15) {
					var15.printStackTrace();
				}
			}
		}

		setupTexture(tex.getMultiTexID(), image, width, height, false, false);
	}

	static void updateTextureMinMagFilter() {

		final TextureManager texman = Minecraft.getTextureManager();
		final ITextureObject texObj = texman.getTexture(TextureMap.locationBlocksTexture);

		if (texObj != null) {
			final MultiTexID multiTex = texObj.getMultiTexID();
			GlStateManager.func_179144_i(multiTex.base);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
					Shaders.texMinFilValue[Shaders.configTexMinFilB]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
					Shaders.texMagFilValue[Shaders.configTexMagFilB]);
			GlStateManager.func_179144_i(multiTex.norm);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
					Shaders.texMinFilValue[Shaders.configTexMinFilN]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
					Shaders.texMagFilValue[Shaders.configTexMagFilN]);
			GlStateManager.func_179144_i(multiTex.spec);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
					Shaders.texMinFilValue[Shaders.configTexMinFilS]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
					Shaders.texMagFilValue[Shaders.configTexMagFilS]);
			GlStateManager.func_179144_i(0);
		}
	}

	public static IResource loadResource(final IResourceManager manager, final ResourceLocation location)
			throws IOException {
		resManager = manager;
		resLocation = location;
		return manager.getResource(location);
	}

	public static int[] loadAtlasSprite(final BufferedImage bufferedimage, final int startX, final int startY,
			final int w, final int h, final int[] aint, final int offset, final int scansize) {
		imageSize = w * h;
		bufferedimage.getRGB(startX, startY, w, h, aint, offset, scansize);
		loadNSMap(resManager, resLocation, w, h, aint);
		return aint;
	}

	public static int[][] getFrameTexData(final int[][] src, final int width, final int height, final int frameIndex) {
		final int numLevel = src.length;
		final int[][] dst = new int[numLevel][];

		for (int level = 0; level < numLevel; ++level) {
			final int[] sr1 = src[level];

			if (sr1 != null) {
				final int frameSize = (width >> level) * (height >> level);
				final int[] ds1 = new int[frameSize * 3];
				dst[level] = ds1;
				final int srcSize = sr1.length / 3;
				int srcPos = frameSize * frameIndex;
				final byte dstPos = 0;
				System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
				srcPos += srcSize;
				int var13 = dstPos + frameSize;
				System.arraycopy(sr1, srcPos, ds1, var13, frameSize);
				srcPos += srcSize;
				var13 += frameSize;
				System.arraycopy(sr1, srcPos, ds1, var13, frameSize);
			}
		}

		return dst;
	}

	public static int[][] prepareAF(final TextureAtlasSprite tas, final int[][] src, final int width,
			final int height) {
		return src;
	}

	public static void fixTransparentColor(final TextureAtlasSprite tas, final int[] aint) {}
}
