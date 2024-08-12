package optifine;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {

public static final int EaZy = 1917;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String host = null;
	private int port = 0;
	private Proxy proxy;
	private String method;
	private String file;
	private String http;
	private Map<String, String> headers;
	private byte[] body;
	private int redirects;
	public static final String METHOD_GET = "GET";
	public static final String METHOD_HEAD = "HEAD";
	public static final String METHOD_POST = "POST";
	public static final String HTTP_1_0 = "HTTP/1.0";
	public static final String HTTP_1_1 = "HTTP/1.1";

	public HttpRequest(final String host, final int port, final Proxy proxy, final String method, final String file,
			final String http, final Map<String, String> headers, final byte[] body) {
		this.proxy = Proxy.NO_PROXY;
		this.method = null;
		this.file = null;
		this.http = null;
		this.headers = new LinkedHashMap();
		this.body = null;
		redirects = 0;
		this.host = host;
		this.port = port;
		this.proxy = proxy;
		this.method = method;
		this.file = file;
		this.http = http;
		this.headers = headers;
		this.body = body;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getMethod() {
		return method;
	}

	public String getFile() {
		return file;
	}

	public String getHttp() {
		return http;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public byte[] getBody() {
		return body;
	}

	public int getRedirects() {
		return redirects;
	}

	public void setRedirects(final int redirects) {
		this.redirects = redirects;
	}

	public Proxy getProxy() {
		return proxy;
	}
}
