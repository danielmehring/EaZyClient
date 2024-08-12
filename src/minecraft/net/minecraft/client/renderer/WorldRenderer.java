package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.util.QuadComparator;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

import optifine.Config;
import optifine.TextureUtils;
import shadersmod.client.SVertexBuilder;

public class WorldRenderer {

public static final int EaZy = 852;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ByteBuffer byteBuffer;
	public IntBuffer rawIntBuffer;
	public FloatBuffer rawFloatBuffer;
	public int vertexCount;
	private double field_178998_e;
	private double field_178995_f;
	private int field_178996_g;
	private int field_179007_h;
	public int rawBufferIndex;

	/** Boolean for whether this renderer needs to be updated or not */
	private boolean needsUpdate;
	public int drawMode;
	private double xOffset;
	private double yOffset;
	private double zOffset;
	private int field_179003_o;
	private int field_179012_p;
	private VertexFormat vertexFormat;
	private boolean isDrawing;
	private int bufferSize;
	private EnumWorldBlockLayer blockLayer = null;
	private boolean[] drawnIcons = new boolean[256];
	private TextureAtlasSprite[] quadSprites = null;
	private TextureAtlasSprite[] quadSpritesPrev = null;
	private TextureAtlasSprite quadSprite = null;
	public SVertexBuilder sVertexBuilder;

	public WorldRenderer(int p_i46275_1_) {
		if (Config.isShaders()) {
			p_i46275_1_ *= 2;
		}

		bufferSize = p_i46275_1_;
		byteBuffer = GLAllocation.createDirectByteBuffer(p_i46275_1_ * 4);
		rawIntBuffer = byteBuffer.asIntBuffer();
		rawFloatBuffer = byteBuffer.asFloatBuffer();
		vertexFormat = new VertexFormat();
		vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT,
				VertexFormatElement.EnumUseage.POSITION, 3));
		SVertexBuilder.initVertexBuilder(this);
	}

	private void growBuffer(int p_178983_1_) {
		if (Config.isShaders()) {
			p_178983_1_ *= 2;
		}

		LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + bufferSize * 4
				+ " bytes, new size " + (bufferSize * 4 + p_178983_1_) + " bytes.");
		bufferSize += p_178983_1_ / 4;
		final ByteBuffer var2 = GLAllocation.createDirectByteBuffer(bufferSize * 4);
		rawIntBuffer.position(0);
		var2.asIntBuffer().put(rawIntBuffer);
		byteBuffer = var2;
		rawIntBuffer = byteBuffer.asIntBuffer();
		rawFloatBuffer = byteBuffer.asFloatBuffer();

		if (quadSprites != null) {
			final TextureAtlasSprite[] sprites = quadSprites;
			final int quadSize = getBufferQuadSize();
			quadSprites = new TextureAtlasSprite[quadSize];
			System.arraycopy(sprites, 0, quadSprites, 0, Math.min(sprites.length, quadSprites.length));
			quadSpritesPrev = null;
		}
	}

	public WorldRenderer.State getVertexState(final float p_178971_1_, final float p_178971_2_,
			final float p_178971_3_) {
		final int[] var4 = new int[rawBufferIndex];
		final PriorityQueue var5 = new PriorityQueue(rawBufferIndex,
				new QuadComparator(rawFloatBuffer, (float) (p_178971_1_ + xOffset), (float) (p_178971_2_ + yOffset),
						(float) (p_178971_3_ + zOffset), vertexFormat.func_177338_f() / 4));
		final int var6 = vertexFormat.func_177338_f();
		TextureAtlasSprite[] quadSpritesSorted = null;
		final int quadStep = vertexFormat.func_177338_f() / 4 * 4;

		if (quadSprites != null) {
			quadSpritesSorted = new TextureAtlasSprite[vertexCount / 4];
		}

		int var7;

		for (var7 = 0; var7 < rawBufferIndex; var7 += var6) {
			var5.add(var7);
		}

		for (var7 = 0; !var5.isEmpty(); var7 += var6) {
			final int var8 = ((Integer) var5.remove());
			int indexQuad;

			for (indexQuad = 0; indexQuad < var6; ++indexQuad) {
				var4[var7 + indexQuad] = rawIntBuffer.get(var8 + indexQuad);
			}

			if (quadSpritesSorted != null) {
				indexQuad = var8 / quadStep;
				final int indexQuadSorted = var7 / quadStep;
				quadSpritesSorted[indexQuadSorted] = quadSprites[indexQuad];
			}
		}

		rawIntBuffer.clear();
		rawIntBuffer.put(var4);

		if (quadSprites != null) {
			System.arraycopy(quadSpritesSorted, 0, quadSprites, 0, quadSpritesSorted.length);
		}

		return new WorldRenderer.State(var4, rawBufferIndex, vertexCount, new VertexFormat(vertexFormat),
				quadSpritesSorted);
	}

	public void setVertexState(final WorldRenderer.State p_178993_1_) {
		if (p_178993_1_.func_179013_a().length > rawIntBuffer.capacity()) {
			growBuffer(2097152);
		}

		rawIntBuffer.clear();
		rawIntBuffer.put(p_178993_1_.func_179013_a());
		rawBufferIndex = p_178993_1_.getRawBufferIndex();
		vertexCount = p_178993_1_.getVertexCount();
		vertexFormat = new VertexFormat(p_178993_1_.func_179016_d());

		if (p_178993_1_.stateQuadSprites != null) {
			if (quadSprites == null) {
				quadSprites = quadSpritesPrev;
			}

			if (quadSprites == null || quadSprites.length < getBufferQuadSize()) {
				quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
			}

			System.arraycopy(p_178993_1_.stateQuadSprites, 0, quadSprites, 0, p_178993_1_.stateQuadSprites.length);
		} else {
			if (quadSprites != null) {
				quadSpritesPrev = quadSprites;
			}

			quadSprites = null;
		}
	}

	public void reset() {
		vertexCount = 0;
		rawBufferIndex = 0;
		vertexFormat.clear();
		vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT,
				VertexFormatElement.EnumUseage.POSITION, 3));

		if (blockLayer != null && Config.isMultiTexture()) {
			if (quadSprites == null) {
				quadSprites = quadSpritesPrev;
			}

			if (quadSprites == null || quadSprites.length < getBufferQuadSize()) {
				quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
			}
		} else {
			if (quadSprites != null) {
				quadSpritesPrev = quadSprites;
			}

			quadSprites = null;
		}

		quadSprite = null;
	}

	public void startDrawingQuads() {
		startDrawing(7);
	}

	public void startDrawing(final int p_178964_1_) {
		if (isDrawing) {
			throw new IllegalStateException("Already building!");
		} else {
			isDrawing = true;
			reset();
			drawMode = p_178964_1_;
			needsUpdate = false;
		}
	}

	public void setTextureUV(final double p_178992_1_, final double p_178992_3_) {
		if (!vertexFormat.func_177347_a(0) && !vertexFormat.func_177347_a(1)) {
			final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT,
					VertexFormatElement.EnumUseage.UV, 2);
			vertexFormat.func_177349_a(var5);
		}

		field_178998_e = p_178992_1_;
		field_178995_f = p_178992_3_;
	}

	public void setColorOpaque(final int red, final int blue, final int green) {
		setColorRGBA(red, blue, green, 255);
	}

	public void setColorRGBA(int red, int blue, int green, int alpha) {
		if (!needsUpdate) {
			if (red > 255) {
				red = 255;
			}
			if (blue > 255) {
				blue = 255;
			}
			if (green > 255) {
				green = 255;
			}
			if (alpha > 255) {
				alpha = 255;
			}
			if (red < 0) {
				red = 0;
			}
			if (blue < 0) {
				blue = 0;
			}
			if (green < 0) {
				green = 0;
			}
			if (alpha < 0) {
				alpha = 0;
			}
			if (!vertexFormat.func_177346_d()) {
				final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE,
						VertexFormatElement.EnumUseage.COLOR, 4);
				vertexFormat.func_177349_a(var5);
			}
			field_179007_h = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN
					? alpha << 24 | green << 16 | blue << 8 | red : red << 24 | blue << 16 | green << 8 | alpha;
		}
	}

	public void setColorOpaque_I(final int rgb) {
		final int red = rgb >> 16 & 255;
		final int blue = rgb >> 8 & 255;
		final int green = rgb & 255;
		setColorOpaque(red, blue, green);
	}

	public void setColorRGBA_I(final int rgb, final int alpha) {
		final int red = rgb >> 16 & 255;
		final int blue = rgb >> 8 & 255;
		final int green = rgb & 255;
		setColorRGBA(red, blue, green, alpha);
	}
	
	public void setColorRGBA(final int rgba) {
		final int alpha = rgba >> 24 & 255;
		final int red = rgba >> 16 & 255;
		final int blue = rgba >> 8 & 255;
		final int green = rgba & 255;
		setColorRGBA(red, blue, green, alpha);
	}

	public void func_178963_b(final int p_178963_1_) {
		if (!vertexFormat.func_177347_a(1)) {
			if (!vertexFormat.func_177347_a(0)) {
				vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT,
						VertexFormatElement.EnumUseage.UV, 2));
			}

			final VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT,
					VertexFormatElement.EnumUseage.UV, 2);
			vertexFormat.func_177349_a(var2);
		}

		field_178996_g = p_178963_1_;
	}

	public void func_178986_b(final float p_178986_1_, final float p_178986_2_, final float p_178986_3_) {
		setPosition((int) (p_178986_1_ * 255.0F), (int) (p_178986_2_ * 255.0F), (int) (p_178986_3_ * 255.0F));
	}

	public void func_178960_a(final float p_178960_1_, final float p_178960_2_, final float p_178960_3_,
			final float p_178960_4_) {
		func_178961_b((int) (p_178960_1_ * 255.0F), (int) (p_178960_2_ * 255.0F), (int) (p_178960_3_ * 255.0F),
				(int) (p_178960_4_ * 255.0F));
	}

	/**
	 * Sets a new position for the renderer and setting it up so it can be
	 * reloaded with the new data for that position
	 */
	public void setPosition(final int p_78913_1_, final int p_78913_2_, final int p_78913_3_) {
		func_178961_b(p_78913_1_, p_78913_2_, p_78913_3_, 255);
	}

	public void func_178962_a(final int p_178962_1_, final int p_178962_2_, final int p_178962_3_,
			final int p_178962_4_) {
		final int var5 = (vertexCount - 4) * (vertexFormat.func_177338_f() / 4) + vertexFormat.func_177344_b(1) / 4;
		final int var6 = vertexFormat.func_177338_f() >> 2;
		rawIntBuffer.put(var5, p_178962_1_);
		rawIntBuffer.put(var5 + var6, p_178962_2_);
		rawIntBuffer.put(var5 + var6 * 2, p_178962_3_);
		rawIntBuffer.put(var5 + var6 * 3, p_178962_4_);
	}

	public void func_178987_a(final double p_178987_1_, final double p_178987_3_, final double p_178987_5_) {
		if (rawBufferIndex >= bufferSize - vertexFormat.func_177338_f()) {
			growBuffer(2097152);
		}

		final int var7 = vertexFormat.func_177338_f() / 4;
		final int var8 = (vertexCount - 4) * var7;

		for (int var9 = 0; var9 < 4; ++var9) {
			final int var10 = var8 + var9 * var7;
			final int var11 = var10 + 1;
			final int var12 = var11 + 1;
			rawIntBuffer.put(var10, Float.floatToRawIntBits(
					(float) (p_178987_1_ + xOffset) + Float.intBitsToFloat(rawIntBuffer.get(var10))));
			rawIntBuffer.put(var11, Float.floatToRawIntBits(
					(float) (p_178987_3_ + yOffset) + Float.intBitsToFloat(rawIntBuffer.get(var11))));
			rawIntBuffer.put(var12, Float.floatToRawIntBits(
					(float) (p_178987_5_ + zOffset) + Float.intBitsToFloat(rawIntBuffer.get(var12))));
		}
	}

	/**
	 * Takes in the pass the call list is being requested for. Args: renderPass
	 */
	public int getGLCallListForPass(final int p_78909_1_) {
		return ((vertexCount - p_78909_1_) * vertexFormat.func_177338_f() + vertexFormat.func_177340_e()) / 4;
	}

	public void func_178978_a(final float p_178978_1_, final float p_178978_2_, final float p_178978_3_,
			final int p_178978_4_) {
		final int var5 = getGLCallListForPass(p_178978_4_);
		int var6 = rawIntBuffer.get(var5);
		int var7;
		int var8;
		int var9;

		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
			var7 = (int) ((var6 & 255) * p_178978_1_);
			var8 = (int) ((var6 >> 8 & 255) * p_178978_2_);
			var9 = (int) ((var6 >> 16 & 255) * p_178978_3_);
			var6 &= -16777216;
			var6 |= var9 << 16 | var8 << 8 | var7;
		} else {
			var7 = (int) ((field_179007_h >> 24 & 255) * p_178978_1_);
			var8 = (int) ((field_179007_h >> 16 & 255) * p_178978_2_);
			var9 = (int) ((field_179007_h >> 8 & 255) * p_178978_3_);
			var6 &= 255;
			var6 |= var7 << 24 | var8 << 16 | var9 << 8;
		}

		if (needsUpdate) {
			var6 = -1;
		}

		rawIntBuffer.put(var5, var6);
	}

	private void func_178988_b(final int p_178988_1_, final int p_178988_2_) {
		final int var3 = getGLCallListForPass(p_178988_2_);
		final int var4 = p_178988_1_ >> 16 & 255;
		final int var5 = p_178988_1_ >> 8 & 255;
		final int var6 = p_178988_1_ & 255;
		final int var7 = p_178988_1_ >> 24 & 255;
		func_178972_a(var3, var4, var5, var6, var7);
	}

	public void func_178994_b(final float p_178994_1_, final float p_178994_2_, final float p_178994_3_,
			final int p_178994_4_) {
		final int var5 = getGLCallListForPass(p_178994_4_);
		final int var6 = MathHelper.clamp_int((int) (p_178994_1_ * 255.0F), 0, 255);
		final int var7 = MathHelper.clamp_int((int) (p_178994_2_ * 255.0F), 0, 255);
		final int var8 = MathHelper.clamp_int((int) (p_178994_3_ * 255.0F), 0, 255);
		func_178972_a(var5, var6, var7, var8, 255);
	}

	public void func_178972_a(final int p_178972_1_, final int p_178972_2_, final int p_178972_3_,
			final int p_178972_4_, final int p_178972_5_) {
		if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
			rawIntBuffer.put(p_178972_1_, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
		} else {
			rawIntBuffer.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
		}
	}

	public void func_178961_b(int p_178961_1_, int p_178961_2_, int p_178961_3_, int p_178961_4_) {
		if (!needsUpdate) {
			if (p_178961_1_ > 255) {
				p_178961_1_ = 255;
			}

			if (p_178961_2_ > 255) {
				p_178961_2_ = 255;
			}

			if (p_178961_3_ > 255) {
				p_178961_3_ = 255;
			}

			if (p_178961_4_ > 255) {
				p_178961_4_ = 255;
			}

			if (p_178961_1_ < 0) {
				p_178961_1_ = 0;
			}

			if (p_178961_2_ < 0) {
				p_178961_2_ = 0;
			}

			if (p_178961_3_ < 0) {
				p_178961_3_ = 0;
			}

			if (p_178961_4_ < 0) {
				p_178961_4_ = 0;
			}

			if (!vertexFormat.func_177346_d()) {
				final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE,
						VertexFormatElement.EnumUseage.COLOR, 4);
				vertexFormat.func_177349_a(var5);
			}

			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
				field_179007_h = p_178961_4_ << 24 | p_178961_3_ << 16 | p_178961_2_ << 8 | p_178961_1_;
			} else {
				field_179007_h = p_178961_1_ << 24 | p_178961_2_ << 16 | p_178961_3_ << 8 | p_178961_4_;
			}
		}
	}

	public void func_178982_a(final byte p_178982_1_, final byte p_178982_2_, final byte p_178982_3_) {
		setPosition(p_178982_1_ & 255, p_178982_2_ & 255, p_178982_3_ & 255);
	}

	public void addVertexWithUV(final double p_178985_1_, final double p_178985_3_, final double p_178985_5_,
			double p_178985_7_, double p_178985_9_) {
		if (quadSprite != null && quadSprites != null) {
			p_178985_7_ = quadSprite.toSingleU((float) p_178985_7_);
			p_178985_9_ = quadSprite.toSingleV((float) p_178985_9_);
			quadSprites[vertexCount / 4] = quadSprite;
		}

		setTextureUV(p_178985_7_, p_178985_9_);
		addVertex(p_178985_1_, p_178985_3_, p_178985_5_);
	}

	public void setVertexFormat(final VertexFormat p_178967_1_) {
		vertexFormat = new VertexFormat(p_178967_1_);

		if (Config.isShaders()) {
			SVertexBuilder.endSetVertexFormat(this);
		}
	}

	public void func_178981_a(final int[] p_178981_1_) {
		if (Config.isShaders()) {
			SVertexBuilder.beginAddVertexData(this, p_178981_1_);
		}

		final int var2 = vertexFormat.func_177338_f() / 4;
		vertexCount += p_178981_1_.length / var2;
		rawIntBuffer.position(rawBufferIndex);
		rawIntBuffer.put(p_178981_1_);
		rawBufferIndex += p_178981_1_.length;

		if (Config.isShaders()) {
			SVertexBuilder.endAddVertexData(this);
		}
	}

	public void addVertex(final double p_178984_1_, final double p_178984_3_, final double p_178984_5_) {
		if (Config.isShaders()) {
			SVertexBuilder.beginAddVertex(this);
		}

		if (rawBufferIndex >= bufferSize - vertexFormat.func_177338_f()) {
			growBuffer(2097152);
		}

		final List var7 = vertexFormat.func_177343_g();
		final int listSize = var7.size();

		for (int i = 0; i < listSize; ++i) {
			final VertexFormatElement var9 = (VertexFormatElement) var7.get(i);
			final int var10 = var9.func_177373_a() >> 2;
			final int var11 = rawBufferIndex + var10;

			switch (WorldRenderer.SwitchEnumUseage.field_178959_a[var9.func_177375_c().ordinal()]) {
				case 1:
					rawIntBuffer.put(var11, Float.floatToRawIntBits((float) (p_178984_1_ + xOffset)));
					rawIntBuffer.put(var11 + 1, Float.floatToRawIntBits((float) (p_178984_3_ + yOffset)));
					rawIntBuffer.put(var11 + 2, Float.floatToRawIntBits((float) (p_178984_5_ + zOffset)));
					break;

				case 2:
					rawIntBuffer.put(var11, field_179007_h);
					break;

				case 3:
					if (var9.func_177369_e() == 0) {
						rawIntBuffer.put(var11, Float.floatToRawIntBits((float) field_178998_e));
						rawIntBuffer.put(var11 + 1, Float.floatToRawIntBits((float) field_178995_f));
					} else {
						rawIntBuffer.put(var11, field_178996_g);
					}

					break;

				case 4:
					rawIntBuffer.put(var11, field_179003_o);
			}
		}

		rawBufferIndex += vertexFormat.func_177338_f() >> 2;
		++vertexCount;

		if (Config.isShaders()) {
			SVertexBuilder.endAddVertex(this);
		}
	}

	public void func_178991_c(final int p_178991_1_) {
		final int var2 = p_178991_1_ >> 16 & 255;
		final int var3 = p_178991_1_ >> 8 & 255;
		final int var4 = p_178991_1_ & 255;
		setPosition(var2, var3, var4);
	}

	public void func_178974_a(final int p_178974_1_, final int p_178974_2_) {
		final int var3 = p_178974_1_ >> 16 & 255;
		final int var4 = p_178974_1_ >> 8 & 255;
		final int var5 = p_178974_1_ & 255;
		func_178961_b(var3, var4, var5, p_178974_2_);
	}

	/**
	 * Marks the current renderer data as dirty and needing to be updated.
	 */
	public void markDirty() {
		needsUpdate = true;
	}

	public void func_178980_d(final float p_178980_1_, final float p_178980_2_, final float p_178980_3_) {
		if (!vertexFormat.func_177350_b()) {
			final VertexFormatElement var7 = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE,
					VertexFormatElement.EnumUseage.NORMAL, 3);
			vertexFormat.func_177349_a(var7);
			vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE,
					VertexFormatElement.EnumUseage.PADDING, 1));
		}

		final byte var71 = (byte) (int) (p_178980_1_ * 127.0F);
		final byte var5 = (byte) (int) (p_178980_2_ * 127.0F);
		final byte var6 = (byte) (int) (p_178980_3_ * 127.0F);
		field_179003_o = var71 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
	}

	public void func_178975_e(final float p_178975_1_, final float p_178975_2_, final float p_178975_3_) {
		final byte var4 = (byte) (int) (p_178975_1_ * 127.0F);
		final byte var5 = (byte) (int) (p_178975_2_ * 127.0F);
		final byte var6 = (byte) (int) (p_178975_3_ * 127.0F);
		final int var7 = vertexFormat.func_177338_f() >> 2;
		final int var8 = (vertexCount - 4) * var7 + vertexFormat.func_177342_c() / 4;
		field_179003_o = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
		rawIntBuffer.put(var8, field_179003_o);
		rawIntBuffer.put(var8 + var7, field_179003_o);
		rawIntBuffer.put(var8 + var7 * 2, field_179003_o);
		rawIntBuffer.put(var8 + var7 * 3, field_179003_o);
	}

	public void setTranslation(final double p_178969_1_, final double p_178969_3_, final double p_178969_5_) {
		xOffset = p_178969_1_;
		yOffset = p_178969_3_;
		zOffset = p_178969_5_;
	}

	public int draw() {
		if (!isDrawing) {
			throw new IllegalStateException("Not building!");
		} else {
			isDrawing = false;

			if (vertexCount > 0) {
				byteBuffer.position(0);
				byteBuffer.limit(rawBufferIndex * 4);
			}

			field_179012_p = rawBufferIndex * 4;
			return field_179012_p;
		}
	}

	public int func_178976_e() {
		return field_179012_p;
	}

	public ByteBuffer func_178966_f() {
		return byteBuffer;
	}

	public VertexFormat func_178973_g() {
		return vertexFormat;
	}

	public int func_178989_h() {
		return vertexCount;
	}

	public int getDrawMode() {
		return drawMode;
	}

	public void func_178968_d(final int p_178968_1_) {
		for (int var2 = 0; var2 < 4; ++var2) {
			func_178988_b(p_178968_1_, var2 + 1);
		}
	}

	public void func_178990_f(final float p_178990_1_, final float p_178990_2_, final float p_178990_3_) {
		for (int var4 = 0; var4 < 4; ++var4) {
			func_178994_b(p_178990_1_, p_178990_2_, p_178990_3_, var4 + 1);
		}
	}

	public void putSprite(final TextureAtlasSprite sprite) {
		if (quadSprites != null) {
			final int countQuads = vertexCount / 4;
			quadSprites[countQuads - 1] = sprite;
		}
	}

	public void setSprite(final TextureAtlasSprite sprite) {
		if (quadSprites != null) {
			quadSprite = sprite;
		}
	}

	public boolean isMultiTexture() {
		return quadSprites != null;
	}

	public void drawMultiTexture() {
		if (quadSprites != null) {
			final int maxTextureIndex = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();

			if (drawnIcons.length <= maxTextureIndex) {
				drawnIcons = new boolean[maxTextureIndex + 1];
			}

			Arrays.fill(drawnIcons, false);
			int texSwitch = 0;
			int grassOverlayIndex = -1;
			final int countQuads = vertexCount / 4;

			for (int i = 0; i < countQuads; ++i) {
				final TextureAtlasSprite icon = quadSprites[i];

				if (icon != null) {
					final int iconIndex = icon.getIndexInMap();

					if (!drawnIcons[iconIndex]) {
						if (icon == TextureUtils.iconGrassSideOverlay) {
							if (grassOverlayIndex < 0) {
								grassOverlayIndex = i;
							}
						} else {
							i = drawForIcon(icon, i) - 1;
							++texSwitch;

							if (blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
								drawnIcons[iconIndex] = true;
							}
						}
					}
				}
			}

			if (grassOverlayIndex >= 0) {
				drawForIcon(TextureUtils.iconGrassSideOverlay, grassOverlayIndex);
				++texSwitch;
			}

			if (texSwitch > 0) {
			}
		}
	}

	private int drawForIcon(final TextureAtlasSprite sprite, final int startQuadPos) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.glSpriteTextureId);
		int firstRegionEnd = -1;
		int lastPos = -1;
		final int countQuads = vertexCount / 4;

		for (int i = startQuadPos; i < countQuads; ++i) {
			final TextureAtlasSprite ts = quadSprites[i];

			if (ts == sprite) {
				if (lastPos < 0) {
					lastPos = i;
				}
			} else if (lastPos >= 0) {
				this.draw(lastPos, i);

				if (blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
					return i;
				}

				lastPos = -1;

				if (firstRegionEnd < 0) {
					firstRegionEnd = i;
				}
			}
		}

		if (lastPos >= 0) {
			this.draw(lastPos, countQuads);
		}

		if (firstRegionEnd < 0) {
			firstRegionEnd = countQuads;
		}

		return firstRegionEnd;
	}

	private void draw(final int startQuadVertex, final int endQuadVertex) {
		final int vxQuadCount = endQuadVertex - startQuadVertex;

		if (vxQuadCount > 0) {
			final int startVertex = startQuadVertex * 4;
			final int vxCount = vxQuadCount * 4;
			GL11.glDrawArrays(drawMode, startVertex, vxCount);
		}
	}

	public void setBlockLayer(final EnumWorldBlockLayer blockLayer) {
		this.blockLayer = blockLayer;

		if (blockLayer == null) {
			if (quadSprites != null) {
				quadSpritesPrev = quadSprites;
			}

			quadSprites = null;
			quadSprite = null;
		}
	}

	private int getBufferQuadSize() {
		final int quadSize = rawIntBuffer.capacity() * 4 / (vertexFormat.func_177338_f() * 4);
		return quadSize;
	}

	public void checkAndGrow() {
		if (rawBufferIndex >= bufferSize - vertexFormat.func_177338_f()) {
			growBuffer(2097152);
		}
	}

	public boolean isColorDisabled() {
		return needsUpdate;
	}

	public class State {
		private final int[] field_179019_b;
		private final int field_179020_c;
		private final int field_179017_d;
		private final VertexFormat field_179018_e;
		public TextureAtlasSprite[] stateQuadSprites;

		public State(final int[] buf, final int bufIndex, final int vertCount, final VertexFormat vertFormat,
				final TextureAtlasSprite[] quadSprites) {
			field_179019_b = buf;
			field_179020_c = bufIndex;
			field_179017_d = vertCount;
			field_179018_e = vertFormat;
			stateQuadSprites = quadSprites;
		}

		public State(final int[] p_i46274_2_, final int p_i46274_3_, final int p_i46274_4_,
				final VertexFormat p_i46274_5_) {
			field_179019_b = p_i46274_2_;
			field_179020_c = p_i46274_3_;
			field_179017_d = p_i46274_4_;
			field_179018_e = p_i46274_5_;
		}

		public int[] func_179013_a() {
			return field_179019_b;
		}

		public int getRawBufferIndex() {
			return field_179020_c;
		}

		public int getVertexCount() {
			return field_179017_d;
		}

		public VertexFormat func_179016_d() {
			return field_179018_e;
		}
	}

	static final class SwitchEnumUseage {
		static final int[] field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];

		static {
			try {
				field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
