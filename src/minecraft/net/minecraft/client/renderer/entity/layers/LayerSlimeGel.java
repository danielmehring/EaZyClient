package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;

public class LayerSlimeGel implements LayerRenderer {

public static final int EaZy = 732;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderSlime slimeRenderer;
	private final ModelBase slimeModel = new ModelSlime(0);
	// private static final String __OBFID = "http://https://fuckuskid00002412";

	public LayerSlimeGel(final RenderSlime p_i46111_1_) {
		slimeRenderer = p_i46111_1_;
	}

	public void doRenderLayer(final EntitySlime p_177159_1_, final float p_177159_2_, final float p_177159_3_,
			final float p_177159_4_, final float p_177159_5_, final float p_177159_6_, final float p_177159_7_,
			final float p_177159_8_) {
		if (!p_177159_1_.isInvisible()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 771);
			slimeModel.setModelAttributes(slimeRenderer.getMainModel());
			slimeModel.render(p_177159_1_, p_177159_2_, p_177159_3_, p_177159_5_, p_177159_6_, p_177159_7_,
					p_177159_8_);
			GlStateManager.disableBlend();
			GlStateManager.disableNormalize();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		this.doRenderLayer((EntitySlime) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
