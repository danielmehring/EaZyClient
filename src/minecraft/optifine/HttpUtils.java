package optifine;

import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

public static final int EaZy = 1919;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final String SERVER_URL = "http://s.optifine.net";
	public static final String POST_URL = "http://optifine.net";

	public static byte[] get(final String urlStr) throws IOException {
		HttpURLConnection conn = null;

		try {
			final URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection(Minecraft.getMinecraft().getProxy());
			conn.setDoInput(true);
			conn.setDoOutput(false);
			conn.connect();

			if (conn.getResponseCode() / 100 != 2) {
				if (conn.getErrorStream() != null) {
					Config.readAll(conn.getErrorStream());
				}

				throw new IOException("HTTP response: " + conn.getResponseCode());
			} else {
				final InputStream in = conn.getInputStream();
				final byte[] bytes = new byte[conn.getContentLength()];
				int pos = 0;

				do {
					final int len = in.read(bytes, pos, bytes.length - pos);

					if (len < 0) {
						throw new IOException("Input stream closed: " + urlStr);
					}

					pos += len;
				}
				while (pos < bytes.length);

				final byte[] len1 = bytes;
				return len1;
			}
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String post(final String urlStr, final Map headers, final byte[] content) throws IOException {
		HttpURLConnection conn = null;

		try {
			final URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection(Minecraft.getMinecraft().getProxy());
			conn.setRequestMethod("POST");

			if (headers != null) {
				final Set os = headers.keySet();
				final Iterator in = os.iterator();

				while (in.hasNext()) {
					final String isr = (String) in.next();
					final String br = "" + headers.get(isr);
					conn.setRequestProperty(isr, br);
				}
			}

			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Content-Length", "" + content.length);
			conn.setRequestProperty("Content-Language", "en-US");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			final OutputStream os1 = conn.getOutputStream();
			os1.write(content);
			os1.flush();
			os1.close();
			final InputStream in1 = conn.getInputStream();
			final InputStreamReader isr1 = new InputStreamReader(in1, "ASCII");
			final BufferedReader br1 = new BufferedReader(isr1);
			final StringBuffer sb = new StringBuffer();
			String line;

			while ((line = br1.readLine()) != null) {
				sb.append(line);
				sb.append('\r');
			}

			br1.close();
			final String var11 = sb.toString();
			return var11;
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
