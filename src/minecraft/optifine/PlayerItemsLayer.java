package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PlayerItemsLayer implements LayerRenderer {

public static final int EaZy = 1948;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private RenderPlayer renderPlayer = null;

	public PlayerItemsLayer(final RenderPlayer renderPlayer) {
		this.renderPlayer = renderPlayer;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase entityLiving, final float limbSwing, final float limbSwingAmount,
			final float partialTicks, final float ticksExisted, final float headYaw, final float rotationPitch,
			final float scale) {
		renderEquippedItems(entityLiving, scale, partialTicks);
	}

	protected void renderEquippedItems(final EntityLivingBase entityLiving, final float scale,
			final float partialTicks) {
		if (Config.isShowCapes()) {
			if (entityLiving instanceof AbstractClientPlayer) {
				final AbstractClientPlayer player = (AbstractClientPlayer) entityLiving;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableRescaleNormal();
				final ModelBiped modelBipedMain = (ModelBiped) renderPlayer.getMainModel();
				PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	public static void register(final Map renderPlayerMap) {
		final Set keys = renderPlayerMap.keySet();
		boolean registered = false;
		final Iterator it = keys.iterator();

		while (it.hasNext()) {
			final Object key = it.next();
			final Object renderer = renderPlayerMap.get(key);

			if (renderer instanceof RenderPlayer) {
				final RenderPlayer renderPlayer = (RenderPlayer) renderer;
				renderPlayer.addLayer(new PlayerItemsLayer(renderPlayer));
				registered = true;
			}
		}

		if (!registered) {
			Config.warn("PlayerItemsLayer not registered");
		}
	}
}
