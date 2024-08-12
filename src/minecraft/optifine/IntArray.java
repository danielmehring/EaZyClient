package optifine;

public class IntArray {

public static final int EaZy = 1922;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int[] array = null;
	private int position = 0;
	private int limit = 0;

	public IntArray(final int size) {
		array = new int[size];
	}

	public void put(final int x) {
		array[position] = x;
		++position;

		if (limit < position) {
			limit = position;
		}
	}

	public void put(final int pos, final int x) {
		array[pos] = x;

		if (limit < pos) {
			limit = pos;
		}
	}

	public void position(final int pos) {
		position = pos;
	}

	public void put(final int[] ints) {
		final int len = ints.length;

		for (int i = 0; i < len; ++i) {
			array[position] = ints[i];
			++position;
		}

		if (limit < position) {
			limit = position;
		}
	}

	public int get(final int pos) {
		return array[pos];
	}

	public int[] getArray() {
		return array;
	}

	public void clear() {
		position = 0;
		limit = 0;
	}

	public int getLimit() {
		return limit;
	}

	public int getPosition() {
		return position;
	}
}
