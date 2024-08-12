package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import optifine.Config;
import shadersmod.client.ShadersRender;

public class VboRenderList extends ChunkRenderContainer {

public static final int EaZy = 845;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }


	@Override
	public void func_178001_a(final EnumWorldBlockLayer p_178001_1_) {
		if (field_178007_b) {
			final Iterator var2 = field_178009_a.iterator();

			while (var2.hasNext()) {
				final RenderChunk var3 = (RenderChunk) var2.next();
				final VertexBuffer var4 = var3.func_178565_b(p_178001_1_.ordinal());
				GlStateManager.pushMatrix();
				func_178003_a(var3);
				var3.func_178572_f();
				var4.func_177359_a();
				func_178010_a();
				var4.func_177358_a(7);
				GlStateManager.popMatrix();
			}

			OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
			GlStateManager.func_179117_G();
			field_178009_a.clear();
		}
	}

	private void func_178010_a() {
		if (Config.isShaders()) {
			ShadersRender.setupArrayPointersVbo();
		} else {
			GL11.glVertexPointer(3, GL11.GL_FLOAT, 28, 0L);
			GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 28, 12L);
			GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 28, 16L);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
			GL11.glTexCoordPointer(2, GL11.GL_SHORT, 28, 24L);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
		}
	}
}
