package optifine;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class HttpPipelineConnection {

public static final int EaZy = 1913;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String host;
	private int port;
	private Proxy proxy;
	private final List<HttpPipelineRequest> listRequests;
	private final List<HttpPipelineRequest> listRequestsSend;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private HttpPipelineSender httpPipelineSender;
	private HttpPipelineReceiver httpPipelineReceiver;
	private int countRequests;
	private boolean responseReceived;
	private long keepaliveTimeoutMs;
	private int keepaliveMaxCount;
	private long timeLastActivityMs;
	private boolean terminated;
	public static final int TIMEOUT_CONNECT_MS = 5000;
	public static final int TIMEOUT_READ_MS = 5000;
	private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");

	public HttpPipelineConnection(final String host, final int port) {
		this(host, port, Proxy.NO_PROXY);
	}

	public HttpPipelineConnection(final String host, final int port, final Proxy proxy) {
		this.host = null;
		this.port = 0;
		this.proxy = Proxy.NO_PROXY;
		listRequests = new LinkedList();
		listRequestsSend = new LinkedList();
		socket = null;
		inputStream = null;
		outputStream = null;
		httpPipelineSender = null;
		httpPipelineReceiver = null;
		countRequests = 0;
		responseReceived = false;
		keepaliveTimeoutMs = 5000L;
		keepaliveMaxCount = 1000;
		timeLastActivityMs = System.currentTimeMillis();
		terminated = false;
		this.host = host;
		this.port = port;
		this.proxy = proxy;
		httpPipelineSender = new HttpPipelineSender(this);
		httpPipelineSender.start();
		httpPipelineReceiver = new HttpPipelineReceiver(this);
		httpPipelineReceiver.start();
	}

	public synchronized boolean addRequest(final HttpPipelineRequest pr) {
		if (isClosed()) {
			return false;
		} else {
			this.addRequest(pr, listRequests);
			this.addRequest(pr, listRequestsSend);
			++countRequests;
			return true;
		}
	}

	private void addRequest(final HttpPipelineRequest pr, final List<HttpPipelineRequest> list) {
		list.add(pr);
		notifyAll();
	}

	public synchronized void setSocket(final Socket s) throws IOException {
		if (!terminated) {
			if (socket != null) {
				throw new IllegalArgumentException("Already connected");
			} else {
				socket = s;
				socket.setTcpNoDelay(true);
				inputStream = socket.getInputStream();
				outputStream = new BufferedOutputStream(socket.getOutputStream());
				onActivity();
				notifyAll();
			}
		}
	}

	public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
		while (outputStream == null) {
			checkTimeout();
			this.wait(1000L);
		}

		return outputStream;
	}

	public synchronized InputStream getInputStream() throws IOException, InterruptedException {
		while (inputStream == null) {
			checkTimeout();
			this.wait(1000L);
		}

		return inputStream;
	}

	public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
		if (listRequestsSend.size() <= 0 && outputStream != null) {
			outputStream.flush();
		}

		return getNextRequest(listRequestsSend, true);
	}

	public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
		return getNextRequest(listRequests, false);
	}

	private HttpPipelineRequest getNextRequest(final List<HttpPipelineRequest> list, final boolean remove)
			throws InterruptedException {
		while (list.size() <= 0) {
			checkTimeout();
			this.wait(1000L);
		}

		onActivity();

		if (remove) {
			return list.remove(0);
		} else {
			return list.get(0);
		}
	}

	private void checkTimeout() {
		if (socket != null) {
			long timeoutMs = keepaliveTimeoutMs;

			if (listRequests.size() > 0) {
				timeoutMs = 5000L;
			}

			final long timeNowMs = System.currentTimeMillis();

			if (timeNowMs > timeLastActivityMs + timeoutMs) {
				terminate(new InterruptedException("Timeout " + timeoutMs));
			}
		}
	}

	private void onActivity() {
		timeLastActivityMs = System.currentTimeMillis();
	}

	public synchronized void onRequestSent(final HttpPipelineRequest pr) {
		if (!terminated) {
			onActivity();
		}
	}

	public synchronized void onResponseReceived(final HttpPipelineRequest pr, final HttpResponse resp) {
		if (!terminated) {
			responseReceived = true;
			onActivity();

			if (listRequests.size() > 0 && listRequests.get(0) == pr) {
				listRequests.remove(0);
				pr.setClosed(true);
				String location = resp.getHeader("Location");

				if (resp.getStatus() / 100 == 3 && location != null && pr.getHttpRequest().getRedirects() < 5) {
					try {
						location = normalizeUrl(location, pr.getHttpRequest());
						final HttpRequest listener1 = HttpPipeline.makeRequest(location,
								pr.getHttpRequest().getProxy());
						listener1.setRedirects(pr.getHttpRequest().getRedirects() + 1);
						final HttpPipelineRequest hpr2 = new HttpPipelineRequest(listener1, pr.getHttpListener());
						HttpPipeline.addRequest(hpr2);
					} catch (final IOException var6) {
						pr.getHttpListener().failed(pr.getHttpRequest(), var6);
					}
				} else {
					final HttpListener listener = pr.getHttpListener();
					listener.finished(pr.getHttpRequest(), resp);
				}

				checkResponseHeader(resp);
			} else {
				throw new IllegalArgumentException("Response out of order: " + pr);
			}
		}
	}

	private String normalizeUrl(final String url, final HttpRequest hr) {
		if (patternFullUrl.matcher(url).matches()) {
			return url;
		} else if (url.startsWith("//")) {
			return "http:" + url;
		} else {
			String server = hr.getHost();

			if (hr.getPort() != 80) {
				server = server + ":" + hr.getPort();
			}

			if (url.startsWith("/")) {
				return "http://" + server + url;
			} else {
				final String file = hr.getFile();
				final int pos = file.lastIndexOf("/");
				return pos >= 0 ? "http://" + server + file.substring(0, pos + 1) + url
						: "http://" + server + "/" + url;
			}
		}
	}

	private void checkResponseHeader(final HttpResponse resp) {
		final String connStr = resp.getHeader("Connection");

		if (connStr != null && !connStr.toLowerCase().equals("keep-alive")) {
			terminate(new EOFException("Connection not keep-alive"));
		}

		final String keepAliveStr = resp.getHeader("Keep-Alive");

		if (keepAliveStr != null) {
			final String[] parts = Config.tokenize(keepAliveStr, ",;");

			for (final String part : parts) {
				final String[] tokens = split(part, '=');

				if (tokens.length >= 2) {
					int max;

					if (tokens[0].equals("timeout")) {
						max = Config.parseInt(tokens[1], -1);

						if (max > 0) {
							keepaliveTimeoutMs = max * 1000;
						}
					}

					if (tokens[0].equals("max")) {
						max = Config.parseInt(tokens[1], -1);

						if (max > 0) {
							keepaliveMaxCount = max;
						}
					}
				}
			}
		}
	}

	private String[] split(final String str, final char separator) {
		final int pos = str.indexOf(separator);

		if (pos < 0) {
			return new String[] { str };
		} else {
			final String str1 = str.substring(0, pos);
			final String str2 = str.substring(pos + 1);
			return new String[] { str1, str2 };
		}
	}

	public synchronized void onExceptionSend(final HttpPipelineRequest pr, final Exception e) {
		terminate(e);
	}

	public synchronized void onExceptionReceive(final HttpPipelineRequest pr, final Exception e) {
		terminate(e);
	}

	private synchronized void terminate(final Exception e) {
		if (!terminated) {
			terminated = true;
			terminateRequests(e);

			if (httpPipelineSender != null) {
				httpPipelineSender.interrupt();
			}

			if (httpPipelineReceiver != null) {
				httpPipelineReceiver.interrupt();
			}

			try {
				if (socket != null) {
					socket.close();
				}
			} catch (final IOException var3) {
			}

			socket = null;
			inputStream = null;
			outputStream = null;
		}
	}

	private void terminateRequests(final Exception e) {
		if (listRequests.size() > 0) {
			HttpPipelineRequest pr;

			if (!responseReceived) {
				pr = listRequests.remove(0);
				pr.getHttpListener().failed(pr.getHttpRequest(), e);
				pr.setClosed(true);
			}

			while (listRequests.size() > 0) {
				pr = listRequests.remove(0);
				HttpPipeline.addRequest(pr);
			}
		}
	}

	public synchronized boolean isClosed() {
		return terminated ? true : countRequests >= keepaliveMaxCount;
	}

	public int getCountRequests() {
		return countRequests;
	}

	public synchronized boolean hasActiveRequests() {
		return listRequests.size() > 0;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public Proxy getProxy() {
		return proxy;
	}
}
