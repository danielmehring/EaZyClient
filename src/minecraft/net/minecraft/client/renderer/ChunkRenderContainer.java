package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class ChunkRenderContainer {

public static final int EaZy = 707;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private double field_178008_c;
	private double field_178005_d;
	private double field_178006_e;
	protected List field_178009_a = Lists.newArrayListWithCapacity(17424);
	protected boolean field_178007_b;

	public void func_178004_a(final double p_178004_1_, final double p_178004_3_, final double p_178004_5_) {
		field_178007_b = true;
		field_178009_a.clear();
		field_178008_c = p_178004_1_;
		field_178005_d = p_178004_3_;
		field_178006_e = p_178004_5_;
	}

	public void func_178003_a(final RenderChunk p_178003_1_) {
		final BlockPos var2 = p_178003_1_.func_178568_j();
		GlStateManager.translate((float) (var2.getX() - field_178008_c), (float) (var2.getY() - field_178005_d),
				(float) (var2.getZ() - field_178006_e));
	}

	public void func_178002_a(final RenderChunk p_178002_1_, final EnumWorldBlockLayer p_178002_2_) {
		field_178009_a.add(p_178002_1_);
	}

	public abstract void func_178001_a(EnumWorldBlockLayer var1);
}
