package optifine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpPipelineSender extends Thread {

public static final int EaZy = 1916;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private HttpPipelineConnection httpPipelineConnection = null;
	private static Charset ASCII = Charset.forName("ASCII");

	public HttpPipelineSender(final HttpPipelineConnection httpPipelineConnection) {
		super("HttpPipelineSender");
		this.httpPipelineConnection = httpPipelineConnection;
	}

	@Override
	public void run() {
		HttpPipelineRequest hpr = null;

		try {
			connect();

			while (!Thread.interrupted()) {
				hpr = httpPipelineConnection.getNextRequestSend();
				final HttpRequest e = hpr.getHttpRequest();
				final OutputStream out = httpPipelineConnection.getOutputStream();
				writeRequest(e, out);
				httpPipelineConnection.onRequestSent(hpr);
			}
		} catch (final InterruptedException var4) {
			return;
		} catch (final Exception var5) {
			httpPipelineConnection.onExceptionSend(hpr, var5);
		}
	}

	private void connect() throws IOException {
		final String host = httpPipelineConnection.getHost();
		final int port = httpPipelineConnection.getPort();
		final Proxy proxy = httpPipelineConnection.getProxy();
		final Socket socket = new Socket(proxy);
		socket.connect(new InetSocketAddress(host, port), 5000);
		httpPipelineConnection.setSocket(socket);
	}

	private void writeRequest(final HttpRequest req, final OutputStream out) throws IOException {
		write(out, req.getMethod() + " " + req.getFile() + " " + req.getHttp() + "\r\n");
		final Map headers = req.getHeaders();
		final Set keySet = headers.keySet();
		final Iterator it = keySet.iterator();

		while (it.hasNext()) {
			final String key = (String) it.next();
			final String val = req.getHeaders().get(key);
			write(out, key + ": " + val + "\r\n");
		}

		write(out, "\r\n");
	}

	private void write(final OutputStream out, final String str) throws IOException {
		final byte[] bytes = str.getBytes(ASCII);
		out.write(bytes);
	}
}
