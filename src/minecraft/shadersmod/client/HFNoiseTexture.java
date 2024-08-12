package shadersmod.client;

import net.minecraft.client.renderer.GlStateManager;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class HFNoiseTexture {

public static final int EaZy = 1995;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int texID = GL11.glGenTextures();
	public int textureUnit = 15;

	public HFNoiseTexture(final int width, final int height) {
		final byte[] image = genHFNoiseImage(width, height);
		final ByteBuffer data = BufferUtils.createByteBuffer(image.length);
		data.put(image);
		data.flip();
		GlStateManager.func_179144_i(texID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE,
				data);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GlStateManager.func_179144_i(0);
	}

	public int getID() {
		return texID;
	}

	public void destroy() {
		GlStateManager.func_179150_h(texID);
		texID = 0;
	}

	private int random(int seed) {
		seed ^= seed << 13;
		seed ^= seed >> 17;
		seed ^= seed << 5;
		return seed;
	}

	private byte random(final int x, final int y, final int z) {
		final int seed = (this.random(x) + this.random(y * 19)) * this.random(z * 23) - z;
		return (byte) (this.random(seed) % 128);
	}

	private byte[] genHFNoiseImage(final int width, final int height) {
		final byte[] image = new byte[width * height * 3];
		int index = 0;

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				for (int z = 1; z < 4; ++z) {
					image[index++] = this.random(x, y, z);
				}
			}
		}

		return image;
	}
}
