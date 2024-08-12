package shadersmod.client;

import java.io.InputStream;

public class ShaderPackDefault implements IShaderPack {

public static final int EaZy = 2010;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	@Override
	public void close() {}

	@Override
	public InputStream getResourceAsStream(final String resName) {
		return ShaderPackDefault.class.getResourceAsStream(resName);
	}

	@Override
	public String getName() {
		return Shaders.packNameDefault;
	}

	@Override
	public boolean hasDirectory(final String name) {
		return false;
	}
}
