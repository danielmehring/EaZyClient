package optifine;

public class MathUtils {

public static final int EaZy = 1931;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static int getAverage(final int[] vals) {
		if (vals.length <= 0) {
			return 0;
		} else {
			int sum = 0;
			int avg;

			for (avg = 0; avg < vals.length; ++avg) {
				final int val = vals[avg];
				sum += val;
			}

			avg = sum / vals.length;
			return avg;
		}
	}
}
