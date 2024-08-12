package shadersmod.client;

import java.util.Arrays;
import java.util.List;

import optifine.Config;
import optifine.StrUtils;

public abstract class ShaderOption {

public static final int EaZy = 2002;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String name = null;
	private String description = null;
	private String value = null;
	private String[] values = null;
	private String valueDefault = null;
	private String[] paths = null;
	private boolean enabled = true;
	private boolean visible = true;
	public static final String COLOR_GREEN = "\u00a7a";
	public static final String COLOR_RED = "\u00a7c";
	public static final String COLOR_BLUE = "\u00a79";

	public ShaderOption(final String name, final String description, final String value, final String[] values,
			final String valueDefault, final String path) {
		this.name = name;
		this.description = description;
		this.value = value;
		this.values = values;
		this.valueDefault = valueDefault;

		if (path != null) {
			paths = new String[] { path };
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionText() {
		String desc = Config.normalize(description);
		desc = StrUtils.removePrefix(desc, "//");
		desc = Shaders.translate("option." + getName() + ".comment", desc);
		return desc;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public boolean setValue(final String value) {
		final int index = getIndex(value, values);

		if (index < 0) {
			return false;
		} else {
			this.value = value;
			return true;
		}
	}

	public String getValueDefault() {
		return valueDefault;
	}

	public void resetValue() {
		value = valueDefault;
	}

	public void nextValue() {
		int index = getIndex(value, values);

		if (index >= 0) {
			index = (index + 1) % values.length;
			value = values[index];
		}
	}

	private static int getIndex(final String str, final String[] strs) {
		for (int i = 0; i < strs.length; ++i) {
			final String s = strs[i];

			if (s.equals(str)) {
				return i;
			}
		}

		return -1;
	}

	public String[] getPaths() {
		return paths;
	}

	public void addPaths(final String[] newPaths) {
		final List pathList = Arrays.asList(paths);

		for (final String newPath2 : newPaths) {
			final String newPath = newPath2;

			if (!pathList.contains(newPath)) {
				paths = (String[]) Config.addObjectToArray(paths, newPath);
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isChanged() {
		return !Config.equals(value, valueDefault);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

	public boolean isValidValue(final String val) {
		return getIndex(val, values) >= 0;
	}

	public String getNameText() {
		return Shaders.translate("option." + name, name);
	}

	public String getValueText(final String val) {
		return val;
	}

	public String getValueColor(final String val) {
		return "";
	}

	public boolean matchesLine(final String line) {
		return false;
	}

	public boolean checkUsed() {
		return false;
	}

	public boolean isUsedInLine(final String line) {
		return false;
	}

	public String getSourceLine() {
		return null;
	}

	public String[] getValues() {
		return values.clone();
	}

	@Override
	public String toString() {
		return "" + name + ", value: " + value + ", valueDefault: " + valueDefault + ", paths: "
				+ Config.arrayToString(paths);
	}
}
