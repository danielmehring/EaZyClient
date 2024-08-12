package optifine;

import java.util.Map;

public class FileUploadThread extends Thread {

public static final int EaZy = 1900;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String urlString;
	private final Map headers;
	private final byte[] content;
	private final IFileUploadListener listener;

	public FileUploadThread(final String urlString, final Map headers, final byte[] content,
			final IFileUploadListener listener) {
		this.urlString = urlString;
		this.headers = headers;
		this.content = content;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			HttpUtils.post(urlString, headers, content);
			listener.fileUploadFinished(urlString, content, (Throwable) null);
		} catch (final Exception var2) {
			listener.fileUploadFinished(urlString, content, var2);
		}
	}

	public String getUrlString() {
		return urlString;
	}

	public byte[] getContent() {
		return content;
	}

	public IFileUploadListener getListener() {
		return listener;
	}
}
