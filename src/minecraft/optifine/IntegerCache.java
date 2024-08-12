package optifine;

public class IntegerCache {

public static final int EaZy = 1923;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Integer[] cache = makeCache(4096);

	private static Integer[] makeCache(final int size) {
		final Integer[] arr = new Integer[size];

		for (int i = 0; i < size; ++i) {
			arr[i] = i;
		}

		return arr;
	}

	public static Integer valueOf(final int value) {
		return value >= 0 && value < 4096 ? cache[value] : value;
	}
}
