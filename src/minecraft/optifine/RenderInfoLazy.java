package optifine;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;

public class RenderInfoLazy {

public static final int EaZy = 1962;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private RenderChunk renderChunk;
	private RenderGlobal.ContainerLocalRenderInformation renderInfo;

	public RenderChunk getRenderChunk() {
		return renderChunk;
	}

	public void setRenderChunk(final RenderChunk renderChunk) {
		this.renderChunk = renderChunk;
		renderInfo = null;
	}

	public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
		if (renderInfo == null) {
			renderInfo = new RenderGlobal.ContainerLocalRenderInformation(renderChunk, (EnumFacing) null, 0);
		}

		return renderInfo;
	}
}
