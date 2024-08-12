package optifine;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class HttpPipelineReceiver extends Thread {

public static final int EaZy = 1914;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private HttpPipelineConnection httpPipelineConnection = null;
	private static final Charset ASCII = Charset.forName("ASCII");

	public HttpPipelineReceiver(final HttpPipelineConnection httpPipelineConnection) {
		super("HttpPipelineReceiver");
		this.httpPipelineConnection = httpPipelineConnection;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			HttpPipelineRequest currentRequest = null;

			try {
				currentRequest = httpPipelineConnection.getNextRequestReceive();
				final InputStream e = httpPipelineConnection.getInputStream();
				final HttpResponse resp = readResponse(e);
				httpPipelineConnection.onResponseReceived(currentRequest, resp);
			} catch (final InterruptedException var4) {
				return;
			} catch (final Exception var5) {
				httpPipelineConnection.onExceptionReceive(currentRequest, var5);
			}
		}
	}

	private HttpResponse readResponse(final InputStream in) throws IOException {
		final String statusLine = readLine(in);
		final String[] parts = Config.tokenize(statusLine, " ");

		if (parts.length < 3) {
			throw new IOException("Invalid status line: " + statusLine);
		} else {
			final int status = Config.parseInt(parts[1], 0);
			final LinkedHashMap headers = new LinkedHashMap();

			while (true) {
				final String body = readLine(in);
				String enc;

				if (body.length() <= 0) {
					byte[] body1 = null;
					final String lenStr1 = (String) headers.get("Content-Length");

					if (lenStr1 != null) {
						final int enc1 = Config.parseInt(lenStr1, -1);

						if (enc1 > 0) {
							body1 = new byte[enc1];
							readFull(body1, in);
						}
					} else {
						enc = (String) headers.get("Transfer-Encoding");

						if (Config.equals(enc, "chunked")) {
							body1 = readContentChunked(in);
						}
					}

					return new HttpResponse(status, statusLine, headers, body1);
				}

				final int lenStr = body.indexOf(":");

				if (lenStr > 0) {
					enc = body.substring(0, lenStr).trim();
					final String val = body.substring(lenStr + 1).trim();
					headers.put(enc, val);
				}
			}
		}
	}

	private byte[] readContentChunked(final InputStream in) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len;

		do {
			final String line = readLine(in);
			final String[] parts = Config.tokenize(line, "; ");
			len = Integer.parseInt(parts[0], 16);
			final byte[] buf = new byte[len];
			readFull(buf, in);
			baos.write(buf);
			readLine(in);
		}
		while (len != 0);

		return baos.toByteArray();
	}

	private void readFull(final byte[] buf, final InputStream in) throws IOException {
		int len;

		for (int pos = 0; pos < buf.length; pos += len) {
			len = in.read(buf, pos, buf.length - pos);

			if (len < 0) {
				throw new EOFException();
			}
		}
	}

	private String readLine(final InputStream in) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int prev = -1;
		boolean hasCRLF = false;

		while (true) {
			final int bytes = in.read();

			if (bytes < 0) {
				break;
			}

			baos.write(bytes);

			if (prev == 13 && bytes == 10) {
				hasCRLF = true;
				break;
			}

			prev = bytes;
		}

		final byte[] bytes1 = baos.toByteArray();
		String str = new String(bytes1, ASCII);

		if (hasCRLF) {
			str = str.substring(0, str.length() - 2);
		}

		return str;
	}
}
