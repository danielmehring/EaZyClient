package optifine;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class PlayerItemRenderer {

public static final int EaZy = 1947;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int attachTo = 0;
	private float scaleFactor = 0.0F;
	private ModelRenderer modelRenderer = null;

	public PlayerItemRenderer(final int attachTo, final float scaleFactor, final ModelRenderer modelRenderer) {
		this.attachTo = attachTo;
		this.scaleFactor = scaleFactor;
		this.modelRenderer = modelRenderer;
	}

	public ModelRenderer getModelRenderer() {
		return modelRenderer;
	}

	public void render(final ModelBiped modelBiped, final float scale) {
		final ModelRenderer attachModel = PlayerItemModel.getAttachModel(modelBiped, attachTo);

		if (attachModel != null) {
			attachModel.postRender(scale);
		}

		modelRenderer.render(scale * scaleFactor);
	}
}
