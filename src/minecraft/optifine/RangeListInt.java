package optifine;

public class RangeListInt {

public static final int EaZy = 1954;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private RangeInt[] ranges = new RangeInt[0];

	public void addRange(final RangeInt ri) {
		ranges = (RangeInt[]) Config.addObjectToArray(ranges, ri);
	}

	public boolean isInRange(final int val) {
		for (final RangeInt ri : ranges) {
			if (ri.isInRange(val)) {
				return true;
			}
		}

		return false;
	}

	public int getCountRanges() {
		return ranges.length;
	}

	public RangeInt getRange(final int i) {
		return ranges[i];
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("[");

		for (int i = 0; i < ranges.length; ++i) {
			final RangeInt ri = ranges[i];

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(ri.toString());
		}

		sb.append("]");
		return sb.toString();
	}
}
