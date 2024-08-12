package optifine;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

import java.nio.ByteBuffer;
import java.util.Properties;

import org.lwjgl.opengl.GL11;

public class TextureAnimation {

public static final int EaZy = 1966;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String srcTex = null;
	private String dstTex = null;
	ResourceLocation dstTexLoc = null;
	private int dstTextId = -1;
	private int dstX = 0;
	private int dstY = 0;
	private int frameWidth = 0;
	private int frameHeight = 0;
	private TextureAnimationFrame[] frames = null;
	private int activeFrame = 0;
	byte[] srcData = null;
	private ByteBuffer imageData = null;

	public TextureAnimation(final String texFrom, final byte[] srcData, final String texTo,
			final ResourceLocation locTexTo, final int dstX, final int dstY, final int frameWidth,
			final int frameHeight, final Properties props, final int durDef) {
		srcTex = texFrom;
		dstTex = texTo;
		dstTexLoc = locTexTo;
		this.dstX = dstX;
		this.dstY = dstY;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		final int frameLen = frameWidth * frameHeight * 4;

		if (srcData.length % frameLen != 0) {
			Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth
					+ ", frameHeight: " + frameHeight);
		}

		this.srcData = srcData;
		int numFrames = srcData.length / frameLen;

		if (props.get("tile.0") != null) {
			for (int durationDefStr = 0; props.get("tile." + durationDefStr) != null; ++durationDefStr) {
				numFrames = durationDefStr + 1;
			}
		}

		final String var21 = (String) props.get("duration");
		final int durationDef = Config.parseInt(var21, durDef);
		frames = new TextureAnimationFrame[numFrames];

		for (int i = 0; i < frames.length; ++i) {
			final String indexStr = (String) props.get("tile." + i);
			final int index = Config.parseInt(indexStr, i);
			final String durationStr = (String) props.get("duration." + i);
			final int duration = Config.parseInt(durationStr, durationDef);
			final TextureAnimationFrame frm = new TextureAnimationFrame(index, duration);
			frames[i] = frm;
		}
	}

	public boolean nextFrame() {
		if (frames.length <= 0) {
			return false;
		} else {
			if (activeFrame >= frames.length) {
				activeFrame = 0;
			}

			final TextureAnimationFrame frame = frames[activeFrame];
			++frame.counter;

			if (frame.counter < frame.duration) {
				return false;
			} else {
				frame.counter = 0;
				++activeFrame;

				if (activeFrame >= frames.length) {
					activeFrame = 0;
				}

				return true;
			}
		}
	}

	public int getActiveFrameIndex() {
		if (frames.length <= 0) {
			return 0;
		} else {
			if (activeFrame >= frames.length) {
				activeFrame = 0;
			}

			final TextureAnimationFrame frame = frames[activeFrame];
			return frame.index;
		}
	}

	public int getFrameCount() {
		return frames.length;
	}

	public boolean updateTexture() {
		if (dstTextId < 0) {
			final ITextureObject frameLen = TextureUtils.getTexture(dstTexLoc);

			if (frameLen == null) {
				return false;
			}

			dstTextId = frameLen.getGlTextureId();
		}

		if (imageData == null) {
			imageData = GLAllocation.createDirectByteBuffer(srcData.length);
			imageData.put(srcData);
			srcData = null;
		}

		if (!nextFrame()) {
			return false;
		} else {
			final int frameLen1 = frameWidth * frameHeight * 4;
			final int imgNum = getActiveFrameIndex();
			final int offset = frameLen1 * imgNum;

			if (offset + frameLen1 > imageData.capacity()) {
				return false;
			} else {
				imageData.position(offset);
				GlStateManager.func_179144_i(dstTextId);
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, dstX, dstY, frameWidth, frameHeight, GL11.GL_RGBA,
						GL11.GL_UNSIGNED_BYTE, imageData);
				return true;
			}
		}
	}

	public String getSrcTex() {
		return srcTex;
	}

	public String getDstTex() {
		return dstTex;
	}

	public ResourceLocation getDstTexLoc() {
		return dstTexLoc;
	}
}
