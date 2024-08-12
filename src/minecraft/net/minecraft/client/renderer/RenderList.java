package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import optifine.Config;

public class RenderList extends ChunkRenderContainer {

public static final int EaZy = 809;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }


	@Override
	public void func_178001_a(final EnumWorldBlockLayer p_178001_1_) {
		if (field_178007_b) {
			if (field_178009_a.isEmpty()) {
				return;
			}

			final Iterator var2 = field_178009_a.iterator();

			while (var2.hasNext()) {
				final RenderChunk var3 = (RenderChunk) var2.next();
				final ListedRenderChunk var4 = (ListedRenderChunk) var3;
				GlStateManager.pushMatrix();
				func_178003_a(var3);
				GL11.glCallList(var4.func_178600_a(p_178001_1_, var4.func_178571_g()));
				GlStateManager.popMatrix();
			}

			if (Config.isMultiTexture()) {
				GlStateManager.bindCurrentTexture();
			}

			GlStateManager.func_179117_G();
			field_178009_a.clear();
		}
	}
}
