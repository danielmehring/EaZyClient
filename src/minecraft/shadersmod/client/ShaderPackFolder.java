package shadersmod.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import optifine.StrUtils;

public class ShaderPackFolder implements IShaderPack {

public static final int EaZy = 2011;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected File packFile;

	public ShaderPackFolder(final String name, final File file) {
		packFile = file;
	}

	@Override
	public void close() {}

	@Override
	public InputStream getResourceAsStream(final String resName) {
		try {
			final String excp = StrUtils.removePrefixSuffix(resName, "/", "/");
			final File resFile = new File(packFile, excp);
			return !resFile.exists() ? null : new BufferedInputStream(new FileInputStream(resFile));
		} catch (final Exception var4) {
			return null;
		}
	}

	@Override
	public boolean hasDirectory(final String name) {
		final File resFile = new File(packFile, name.substring(1));
		return !resFile.exists() ? false : resFile.isDirectory();
	}

	@Override
	public String getName() {
		return packFile.getName();
	}
}
