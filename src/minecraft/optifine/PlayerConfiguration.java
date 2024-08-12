package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class PlayerConfiguration {

public static final int EaZy = 1940;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
	private boolean initialized = false;

	public void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer player, final float scale,
			final float partialTicks) {
		if (initialized) {
			for (final PlayerItemModel model : playerItemModels) {
				model.render(modelBiped, player, scale, partialTicks);
			}
		}
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(final boolean initialized) {
		this.initialized = initialized;
	}

	public PlayerItemModel[] getPlayerItemModels() {
		return playerItemModels;
	}

	public void addPlayerItemModel(final PlayerItemModel playerItemModel) {
		playerItemModels = (PlayerItemModel[]) Config.addObjectToArray(playerItemModels, playerItemModel);
	}
}
