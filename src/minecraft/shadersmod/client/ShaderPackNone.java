package shadersmod.client;

import java.io.InputStream;

public class ShaderPackNone implements IShaderPack {

public static final int EaZy = 2012;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	@Override
	public void close() {}

	@Override
	public InputStream getResourceAsStream(final String resName) {
		return null;
	}

	@Override
	public boolean hasDirectory(final String name) {
		return false;
	}

	@Override
	public String getName() {
		return Shaders.packNameNone;
	}
}
