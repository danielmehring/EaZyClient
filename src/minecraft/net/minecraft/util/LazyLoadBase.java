package net.minecraft.util;

public abstract class LazyLoadBase {

public static final int EaZy = 1627;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Object value;
	private boolean isLoaded = false;
	// private static final String __OBFID = "http://https://fuckuskid00002263";

	public Object getValue() {
		if (!isLoaded) {
			isLoaded = true;
			value = load();
		}

		return value;
	}

	protected abstract Object load();
}
