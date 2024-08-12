package shadersmod.client;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.IResourceManager;

public class DefaultTexture extends AbstractTexture {

public static final int EaZy = 1988;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public DefaultTexture() {
		loadTexture((IResourceManager) null);
	}

	@Override
	public void loadTexture(final IResourceManager resourcemanager) {
		final int[] aint = ShadersTex.createAIntImage(1, -1);
		ShadersTex.setupTexture(getMultiTexID(), aint, 1, 1, false, false);
	}
}
