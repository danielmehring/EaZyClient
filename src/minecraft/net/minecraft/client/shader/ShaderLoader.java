package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

import com.google.common.collect.Maps;

public class ShaderLoader {

public static final int EaZy = 909;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ShaderLoader.ShaderType shaderType;
	private final String shaderFilename;
	private final int shader;
	private int shaderAttachCount = 0;
	// private static final String __OBFID = "http://https://fuckuskid00001043";

	private ShaderLoader(final ShaderLoader.ShaderType type, final int shaderId, final String filename) {
		shaderType = type;
		shader = shaderId;
		shaderFilename = filename;
	}

	public void attachShader(final ShaderManager manager) {
		++shaderAttachCount;
		OpenGlHelper.glAttachShader(manager.getProgram(), shader);
	}

	public void deleteShader(final ShaderManager manager) {
		--shaderAttachCount;

		if (shaderAttachCount <= 0) {
			OpenGlHelper.glDeleteShader(shader);
			shaderType.getLoadedShaders().remove(shaderFilename);
		}
	}

	public String getShaderFilename() {
		return shaderFilename;
	}

	public static ShaderLoader loadShader(final IResourceManager resourceManager, final ShaderLoader.ShaderType type,
			final String filename) throws IOException {
		ShaderLoader var3 = (ShaderLoader) type.getLoadedShaders().get(filename);

		if (var3 == null) {
			final ResourceLocation var4 = new ResourceLocation(
					"shaders/program/" + filename + type.getShaderExtension());
			final BufferedInputStream var5 = new BufferedInputStream(
					resourceManager.getResource(var4).getInputStream());
			final byte[] var6 = func_177064_a(var5);
			final ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
			var7.put(var6);
			var7.position(0);
			final int var8 = OpenGlHelper.glCreateShader(type.getShaderMode());
			OpenGlHelper.glShaderSource(var8, var7);
			OpenGlHelper.glCompileShader(var8);

			if (OpenGlHelper.glGetShaderi(var8, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
				final String var9 = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(var8, 32768));
				final JsonException var10 = new JsonException(
						"Couldn\'t compile " + type.getShaderName() + " program: " + var9);
				var10.func_151381_b(var4.getResourcePath());
				throw var10;
			}

			var3 = new ShaderLoader(type, var8, filename);
			type.getLoadedShaders().put(filename, var3);
		}

		return var3;
	}

	protected static byte[] func_177064_a(final BufferedInputStream p_177064_0_) throws IOException {
		byte[] var1;

		try {
			var1 = IOUtils.toByteArray(p_177064_0_);
		}
		finally {
			p_177064_0_.close();
		}

		return var1;
	}

	public static enum ShaderType {
		VERTEX("VERTEX", 0, "vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER), FRAGMENT("FRAGMENT", 1, "fragment",
				".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
		private final String shaderName;
		private final String shaderExtension;
		private final int shaderMode;
		private final Map loadedShaders = Maps.newHashMap();

		private ShaderType(final String p_i45090_1_, final int p_i45090_2_, final String p_i45090_3_,
				final String p_i45090_4_, final int p_i45090_5_) {
			shaderName = p_i45090_3_;
			shaderExtension = p_i45090_4_;
			shaderMode = p_i45090_5_;
		}

		public String getShaderName() {
			return shaderName;
		}

		protected String getShaderExtension() {
			return shaderExtension;
		}

		protected int getShaderMode() {
			return shaderMode;
		}

		protected Map getLoadedShaders() {
			return loadedShaders;
		}
	}
}
