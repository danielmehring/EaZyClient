package net.minecraft.client.resources;

public class Language implements Comparable {

public static final int EaZy = 883;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String languageCode;
	private final String region;
	private final String name;
	private final boolean bidirectional;
	// private static final String __OBFID = "http://https://fuckuskid00001095";

	public Language(final String p_i1303_1_, final String p_i1303_2_, final String p_i1303_3_,
			final boolean p_i1303_4_) {
		languageCode = p_i1303_1_;
		region = p_i1303_2_;
		name = p_i1303_3_;
		bidirectional = p_i1303_4_;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", new Object[] { name, region });
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		return this == p_equals_1_ ? true
				: !(p_equals_1_ instanceof Language) ? false
						: languageCode.equals(((Language) p_equals_1_).languageCode);
	}

	@Override
	public int hashCode() {
		return languageCode.hashCode();
	}

	public int compareTo(final Language p_compareTo_1_) {
		return languageCode.compareTo(p_compareTo_1_.languageCode);
	}

	@Override
	public int compareTo(final Object p_compareTo_1_) {
		return this.compareTo((Language) p_compareTo_1_);
	}
}
