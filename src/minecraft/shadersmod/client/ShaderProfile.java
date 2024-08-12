package shadersmod.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShaderProfile {

public static final int EaZy = 2015;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String name = null;
	private final Map<String, String> mapOptionValues = new HashMap();
	private final Set<String> disabledPrograms = new HashSet();

	public ShaderProfile(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addOptionValue(final String option, final String value) {
		mapOptionValues.put(option, value);
	}

	public void addOptionValues(final ShaderProfile prof) {
		if (prof != null) {
			mapOptionValues.putAll(prof.mapOptionValues);
		}
	}

	public void applyOptionValues(final ShaderOption[] options) {
		for (final ShaderOption so : options) {
			final String key = so.getName();
			final String val = mapOptionValues.get(key);

			if (val != null) {
				so.setValue(val);
			}
		}
	}

	public String[] getOptions() {
		final Set keys = mapOptionValues.keySet();
		final String[] opts = (String[]) keys.toArray(new String[keys.size()]);
		return opts;
	}

	public String getValue(final String key) {
		return mapOptionValues.get(key);
	}

	public void addDisabledProgram(final String program) {
		disabledPrograms.add(program);
	}

	public Collection<String> getDisabledPrograms() {
		return new HashSet(disabledPrograms);
	}

	public void addDisabledPrograms(final Collection<String> programs) {
		disabledPrograms.addAll(programs);
	}

	public boolean isProgramDisabled(final String program) {
		return disabledPrograms.contains(program);
	}
}
