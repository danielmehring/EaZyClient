package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ListChunkFactory implements IRenderChunkFactory {

public static final int EaZy = 701;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	@Override
	public RenderChunk func_178602_a(final World worldIn, final RenderGlobal p_178602_2_, final BlockPos p_178602_3_,
			final int p_178602_4_) {
		return new ListedRenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
	}
}
