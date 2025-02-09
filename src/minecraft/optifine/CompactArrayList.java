package optifine;

import java.util.ArrayList;

public class CompactArrayList {

public static final int EaZy = 1883;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ArrayList list;
	private int initialCapacity;
	private float loadFactor;
	private int countValid;

	public CompactArrayList() {
		this(10, 0.75F);
	}

	public CompactArrayList(final int initialCapacity) {
		this(initialCapacity, 0.75F);
	}

	public CompactArrayList(final int initialCapacity, final float loadFactor) {
		list = null;
		this.initialCapacity = 0;
		this.loadFactor = 1.0F;
		countValid = 0;
		list = new ArrayList(initialCapacity);
		this.initialCapacity = initialCapacity;
		this.loadFactor = loadFactor;
	}

	public void add(final int index, final Object element) {
		if (element != null) {
			++countValid;
		}

		list.add(index, element);
	}

	public boolean add(final Object element) {
		if (element != null) {
			++countValid;
		}

		return list.add(element);
	}

	public Object set(final int index, final Object element) {
		final Object oldElement = list.set(index, element);

		if (element != oldElement) {
			if (oldElement == null) {
				++countValid;
			}

			if (element == null) {
				--countValid;
			}
		}

		return oldElement;
	}

	public Object remove(final int index) {
		final Object oldElement = list.remove(index);

		if (oldElement != null) {
			--countValid;
		}

		return oldElement;
	}

	public void clear() {
		list.clear();
		countValid = 0;
	}

	public void compact() {
		if (countValid <= 0 && list.size() <= 0) {
			clear();
		} else if (list.size() > initialCapacity) {
			final float currentLoadFactor = countValid * 1.0F / list.size();

			if (currentLoadFactor <= loadFactor) {
				int dstIndex = 0;
				int i;

				for (i = 0; i < list.size(); ++i) {
					final Object wr = list.get(i);

					if (wr != null) {
						if (i != dstIndex) {
							list.set(dstIndex, wr);
						}

						++dstIndex;
					}
				}

				for (i = list.size() - 1; i >= dstIndex; --i) {
					list.remove(i);
				}
			}
		}
	}

	public boolean contains(final Object elem) {
		return list.contains(elem);
	}

	public Object get(final int index) {
		return list.get(index);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int size() {
		return list.size();
	}

	public int getCountValid() {
		return countValid;
	}
}
