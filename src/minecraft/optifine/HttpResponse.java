package optifine;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

public static final int EaZy = 1918;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int status = 0;
	private String statusLine = null;
	private Map<String, String> headers = new LinkedHashMap();
	private byte[] body = null;

	public HttpResponse(final int status, final String statusLine, final Map headers, final byte[] body) {
		this.status = status;
		this.statusLine = statusLine;
		this.headers = headers;
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public Map getHeaders() {
		return headers;
	}

	public String getHeader(final String key) {
		return headers.get(key);
	}

	public byte[] getBody() {
		return body;
	}
}
