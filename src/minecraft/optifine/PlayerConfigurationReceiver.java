package optifine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PlayerConfigurationReceiver implements IFileDownloadListener {

public static final int EaZy = 1942;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String player = null;

	public PlayerConfigurationReceiver(final String player) {
		this.player = player;
	}

	@Override
	public void fileDownloadFinished(final String url, final byte[] bytes, final Throwable exception) {
		if (bytes != null) {
			try {
				final String e = new String(bytes, "ASCII");
				final JsonParser jp = new JsonParser();
				final JsonElement je = jp.parse(e);
				final PlayerConfigurationParser pcp = new PlayerConfigurationParser(player);
				final PlayerConfiguration pc = pcp.parsePlayerConfiguration(je);

				if (pc != null) {
					pc.setInitialized(true);
					PlayerConfigurations.setPlayerConfiguration(player, pc);
				}
			} catch (final Exception var9) {
				Config.dbg("Error parsing configuration: " + url + ", " + var9.getClass().getName() + ": "
						+ var9.getMessage());
			}
		}
	}
}
