package optifine;

public class RangeInt {

public static final int EaZy = 1953;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final int min;
	private final int max;

	public RangeInt(final int min, final int max) {
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
	}

	public boolean isInRange(final int val) {
		return val < min ? false : val <= max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public String toString() {
		return "min: " + min + ", max: " + max;
	}
}
