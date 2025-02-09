package optifine;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;

import java.util.HashMap;

import shadersmod.client.Shaders;

public class CrashReporter {

public static final int EaZy = 1888;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static void onCrashReport(final CrashReport crashReport, final CrashReportCategory category) {
		try {
			final GameSettings e = Config.getGameSettings();

			if (e == null) {
				return;
			}

			if (!e.snooperEnabled) {
				return;
			}

			final Throwable cause = crashReport.getCrashCause();

			if (cause == null) {
				return;
			}

			if (cause.getClass() == Throwable.class) {
				return;
			}

			if (cause.getClass().getName().contains(".fml.client.SplashProgress")) {
				return;
			}

			extendCrashReport(category);
			final String url = "http://optifine.net/crashReport";
			final String reportStr = makeReport(crashReport);
			final byte[] content = reportStr.getBytes("ASCII");
			final IFileUploadListener listener = new IFileUploadListener() {
				@Override
				public void fileUploadFinished(final String url, final byte[] content, final Throwable exception) {}
			};
			final HashMap headers = new HashMap();
			headers.put("OF-Version", Config.getVersion());
			headers.put("OF-Summary", makeSummary(crashReport));
			final FileUploadThread fut = new FileUploadThread(url, headers, content, listener);
			fut.setPriority(10);
			fut.start();
			Thread.sleep(1000L);
		} catch (final Exception var10) {
			Config.dbg(var10.getClass().getName() + ": " + var10.getMessage());
		}
	}

	private static String makeReport(final CrashReport crashReport) {
		final StringBuffer sb = new StringBuffer();
		sb.append("OptiFineVersion: " + Config.getVersion() + "\n");
		sb.append("Summary: " + makeSummary(crashReport) + "\n");
		sb.append("\n");
		sb.append(crashReport.getCompleteReport());
		sb.append("\n");
		return sb.toString();
	}

	private static String makeSummary(final CrashReport crashReport) {
		final Throwable t = crashReport.getCrashCause();

		if (t == null) {
			return "Unknown";
		} else {
			final StackTraceElement[] traces = t.getStackTrace();
			String firstTrace = "unknown";

			if (traces.length > 0) {
				firstTrace = traces[0].toString().trim();
			}

			final String sum = t.getClass().getName() + ": " + t.getMessage() + " (" + crashReport.getDescription()
					+ ")" + " [" + firstTrace + "]";
			return sum;
		}
	}

	public static void extendCrashReport(final CrashReportCategory cat) {
		cat.addCrashSection("OptiFine Version", Config.getVersion());

		if (Config.getGameSettings() != null) {
			cat.addCrashSection("Render Distance Chunks", "" + Config.getChunkViewDistance());
			cat.addCrashSection("Mipmaps", "" + Config.getMipmapLevels());
			cat.addCrashSection("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
			cat.addCrashSection("Antialiasing", "" + Config.getAntialiasingLevel());
			cat.addCrashSection("Multitexture", "" + Config.isMultiTexture());
		}

		cat.addCrashSection("Shaders", "" + Shaders.getShaderPackName());
		cat.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
		cat.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
		cat.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
		cat.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
	}
}
