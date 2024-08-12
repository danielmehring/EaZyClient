package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.OpenGlHelper;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class VertexBuffer {

public static final int EaZy = 847;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_177365_a;
	private final VertexFormat field_177363_b;
	private int field_177364_c;
	// private static final String __OBFID = "http://https://fuckuskid00002402";

	public VertexBuffer(final VertexFormat p_i46098_1_) {
		field_177363_b = p_i46098_1_;
		field_177365_a = OpenGlHelper.func_176073_e();
	}

	public void func_177359_a() {
		OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, field_177365_a);
	}

	public void func_177360_a(final ByteBuffer p_177360_1_, final int p_177360_2_) {
		func_177359_a();
		OpenGlHelper.func_176071_a(OpenGlHelper.field_176089_P, p_177360_1_, 35044);
		func_177361_b();
		field_177364_c = p_177360_2_ / field_177363_b.func_177338_f();
	}

	public void func_177358_a(final int p_177358_1_) {
		GL11.glDrawArrays(p_177358_1_, 0, field_177364_c);
	}

	public void func_177361_b() {
		OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
	}

	public void func_177362_c() {
		if (field_177365_a >= 0) {
			OpenGlHelper.func_176074_g(field_177365_a);
			field_177365_a = -1;
		}
	}
}
