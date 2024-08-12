package optifine;

import java.util.Comparator;

public class CustomItemsComparator implements Comparator {

public static final int EaZy = 1894;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	@Override
	public int compare(final Object o1, final Object o2) {
		final CustomItemProperties p1 = (CustomItemProperties) o1;
		final CustomItemProperties p2 = (CustomItemProperties) o2;
		return p1.weight != p2.weight ? p2.weight - p1.weight
				: !Config.equals(p1.basePath, p2.basePath) ? p1.basePath.compareTo(p2.basePath)
						: p1.name.compareTo(p2.name);
	}
}
