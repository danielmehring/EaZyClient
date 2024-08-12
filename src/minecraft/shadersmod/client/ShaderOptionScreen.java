package shadersmod.client;

public class ShaderOptionScreen extends ShaderOption {

public static final int EaZy = 2005;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public ShaderOptionScreen(final String name) {
		super(name, (String) null, (String) null, new String[] { null }, (String) null, (String) null);
	}

	@Override
	public String getNameText() {
		return Shaders.translate("screen." + getName(), getName());
	}

	@Override
	public String getDescriptionText() {
		return Shaders.translate("screen." + getName() + ".comment", (String) null);
	}
}
