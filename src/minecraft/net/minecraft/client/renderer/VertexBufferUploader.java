package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends WorldVertexBufferUploader {

public static final int EaZy = 850;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private VertexBuffer field_178179_a = null;

	@Override
	public int draw(final WorldRenderer p_178177_1_, final int p_178177_2_) {
		p_178177_1_.reset();
		field_178179_a.func_177360_a(p_178177_1_.func_178966_f(), p_178177_1_.func_178976_e());
		return p_178177_2_;
	}

	public void func_178178_a(final VertexBuffer p_178178_1_) {
		field_178179_a = p_178178_1_;
	}
}
