package shadersmod.client;

public class PropertyDefaultTrueFalse extends Property {

public static final int EaZy = 2001;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final String[] PROPERTY_VALUES = new String[] { "default", "true", "false" };
	public static final String[] USER_VALUES = new String[] { "Default", "ON", "OFF" };

	public PropertyDefaultTrueFalse(final String propertyName, final String userName, final int defaultValue) {
		super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
	}

	public boolean isDefault() {
		return getValue() == 0;
	}

	public boolean isTrue() {
		return getValue() == 1;
	}

	public boolean isFalse() {
		return getValue() == 2;
	}
}
