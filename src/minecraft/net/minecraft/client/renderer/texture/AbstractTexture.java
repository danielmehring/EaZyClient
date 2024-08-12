package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import shadersmod.client.MultiTexID;
import shadersmod.client.ShadersTex;

public abstract class AbstractTexture implements ITextureObject {

public static final int EaZy = 812;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected int glTextureId = -1;
	protected boolean field_174940_b;
	protected boolean field_174941_c;
	protected boolean field_174938_d;
	protected boolean field_174939_e;
	// private static final String __OBFID = "http://https://fuckuskid00001047";
	public MultiTexID multiTex;

	public void func_174937_a(final boolean p_174937_1_, final boolean p_174937_2_) {
		field_174940_b = p_174937_1_;
		field_174941_c = p_174937_2_;
		int var5;
		short var6;

		if (p_174937_1_) {
			var5 = p_174937_2_ ? 9987 : 9729;
			var6 = 9729;
		} else {
			var5 = p_174937_2_ ? 9986 : 9728;
			var6 = 9728;
		}

		GlStateManager.func_179144_i(getGlTextureId());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var5);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var6);
	}

	@Override
	public void func_174936_b(final boolean p_174936_1_, final boolean p_174936_2_) {
		field_174938_d = field_174940_b;
		field_174939_e = field_174941_c;
		func_174937_a(p_174936_1_, p_174936_2_);
	}

	@Override
	public void func_174935_a() {
		func_174937_a(field_174938_d, field_174939_e);
	}

	@Override
	public int getGlTextureId() {
		if (glTextureId == -1) {
			glTextureId = TextureUtil.glGenTextures();
		}

		return glTextureId;
	}

	public void deleteGlTexture() {
		ShadersTex.deleteTextures(this, glTextureId);

		if (glTextureId != -1) {
			TextureUtil.deleteTexture(glTextureId);
			glTextureId = -1;
		}
	}

	@Override
	public MultiTexID getMultiTexID() {
		return ShadersTex.getMultiTexID(this);
	}
}
