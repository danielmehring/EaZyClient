package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigurations {

public static final int EaZy = 1943;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Map mapConfigurations = null;

	public static void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer player,
			final float scale, final float partialTicks) {
		final PlayerConfiguration cfg = getPlayerConfiguration(player);

		if (cfg != null) {
			cfg.renderPlayerItems(modelBiped, player, scale, partialTicks);
		}
	}

	public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer player) {
		final String name = player.getNameClear();

		if (name == null) {
			return null;
		} else {
			PlayerConfiguration pc = (PlayerConfiguration) getMapConfigurations().get(name);

			if (pc == null) {
				pc = new PlayerConfiguration();
				getMapConfigurations().put(name, pc);
				final PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
				final String url = "http://s.optifine.net/users/" + name + ".cfg";
				final FileDownloadThread fdt = new FileDownloadThread(url, pcl);
				fdt.start();
			}

			return pc;
		}
	}

	public static synchronized void setPlayerConfiguration(final String player, final PlayerConfiguration pc) {
		getMapConfigurations().put(player, pc);
	}

	private static Map getMapConfigurations() {
		if (mapConfigurations == null) {
			mapConfigurations = new HashMap();
		}

		return mapConfigurations;
	}
}
