package shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import optifine.StrUtils;

public class ShaderPackZip implements IShaderPack {

public static final int EaZy = 2014;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected File packFile;
	protected ZipFile packZipFile;

	public ShaderPackZip(final String name, final File file) {
		packFile = file;
		packZipFile = null;
	}

	@Override
	public void close() {
		if (packZipFile != null) {
			try {
				packZipFile.close();
			} catch (final Exception var2) {
			}

			packZipFile = null;
		}
	}

	@Override
	public InputStream getResourceAsStream(final String resName) {
		try {
			if (packZipFile == null) {
				packZipFile = new ZipFile(packFile);
			}

			final String excp = StrUtils.removePrefix(resName, "/");
			final ZipEntry entry = packZipFile.getEntry(excp);
			return entry == null ? null : packZipFile.getInputStream(entry);
		} catch (final Exception var4) {
			return null;
		}
	}

	@Override
	public boolean hasDirectory(final String resName) {
		try {
			if (packZipFile == null) {
				packZipFile = new ZipFile(packFile);
			}

			final String e = StrUtils.removePrefix(resName, "/");
			final ZipEntry entry = packZipFile.getEntry(e);
			return entry != null;
		} catch (final IOException var4) {
			return false;
		}
	}

	@Override
	public String getName() {
		return packFile.getName();
	}
}
