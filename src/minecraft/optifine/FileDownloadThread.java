package optifine;

import net.minecraft.client.Minecraft;

public class FileDownloadThread extends Thread {

public static final int EaZy = 1899;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String urlString = null;
	private IFileDownloadListener listener = null;

	public FileDownloadThread(final String urlString, final IFileDownloadListener listener) {
		this.urlString = urlString;
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			final byte[] e = HttpPipeline.get(urlString, Minecraft.getMinecraft().getProxy());
			listener.fileDownloadFinished(urlString, e, (Throwable) null);
		} catch (final Exception var2) {
			listener.fileDownloadFinished(urlString, (byte[]) null, var2);
		}
	}

	public String getUrlString() {
		return urlString;
	}

	public IFileDownloadListener getListener() {
		return listener;
	}
}
