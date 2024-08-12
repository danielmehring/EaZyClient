package optifine;

public class HttpPipelineRequest {

public static final int EaZy = 1915;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private HttpRequest httpRequest = null;
	private HttpListener httpListener = null;
	private boolean closed = false;

	public HttpPipelineRequest(final HttpRequest httpRequest, final HttpListener httpListener) {
		this.httpRequest = httpRequest;
		this.httpListener = httpListener;
	}

	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public HttpListener getHttpListener() {
		return httpListener;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean closed) {
		this.closed = closed;
	}
}
