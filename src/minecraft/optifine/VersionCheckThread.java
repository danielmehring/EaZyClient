package optifine;

import net.minecraft.client.ClientBrandRetriever;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionCheckThread extends Thread {

public static final int EaZy = 1971;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	@Override
	public void run() {
		HttpURLConnection conn = null;

		try {
			Config.dbg("Checking for new version");
			final URL e = new URL("http://optifine.net/version/1.8/HD_U.txt");
			conn = (HttpURLConnection) e.openConnection();

			if (Config.getGameSettings().snooperEnabled) {
				conn.setRequestProperty("OF-MC-Version", "1.8");
				conn.setRequestProperty("OF-MC-Brand", "" + ClientBrandRetriever.getClientModName());
				conn.setRequestProperty("OF-Edition", "HD_U");
				conn.setRequestProperty("OF-Release", "H6");
				conn.setRequestProperty("OF-Java-Version", "" + System.getProperty("java.version"));
				conn.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
				conn.setRequestProperty("OF-OpenGL-Version", "" + Config.openGlVersion);
				conn.setRequestProperty("OF-OpenGL-Vendor", "" + Config.openGlVendor);
			}

			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.connect();

			try {
				final InputStream in = conn.getInputStream();
				final String verStr = Config.readInputStream(in);
				in.close();
				final String[] verLines = Config.tokenize(verStr, "\n\r");

				if (verLines.length < 1) {
					return;
				}

				final String newVer = verLines[0].trim();
				Config.dbg("Version found: " + newVer);

				if (Config.compareRelease(newVer, "H6") > 0) {
					Config.setNewRelease(newVer);
					return;
				}
			}
			finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		} catch (final Exception var11) {
			Config.dbg(var11.getClass().getName() + ": " + var11.getMessage());
		}
	}
}
