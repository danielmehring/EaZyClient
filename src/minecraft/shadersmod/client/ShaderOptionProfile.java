package shadersmod.client;

import java.util.ArrayList;

import optifine.Lang;

public class ShaderOptionProfile extends ShaderOption {

public static final int EaZy = 2003;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ShaderProfile[] profiles = null;
	private ShaderOption[] options = null;

	public ShaderOptionProfile(final ShaderProfile[] profiles, final ShaderOption[] options) {
		super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles),
				detectProfileName(profiles, options, true), (String) null);
		this.profiles = profiles;
		this.options = options;
	}

	@Override
	public void nextValue() {
		super.nextValue();

		if (getValue().equals("<custom>")) {
			super.nextValue();
		}

		applyProfileOptions();
	}

	public void updateProfile() {
		final ShaderProfile prof = getProfile(getValue());

		if (prof == null || !ShaderUtils.matchProfile(prof, options, false)) {
			final String val = detectProfileName(profiles, options);
			setValue(val);
		}
	}

	private void applyProfileOptions() {
		final ShaderProfile prof = getProfile(getValue());

		if (prof != null) {
			final String[] opts = prof.getOptions();

			for (final String name : opts) {
				final ShaderOption so = getOption(name);

				if (so != null) {
					final String val = prof.getValue(name);
					so.setValue(val);
				}
			}
		}
	}

	private ShaderOption getOption(final String name) {
		for (final ShaderOption so : options) {
			if (so.getName().equals(name)) {
				return so;
			}
		}

		return null;
	}

	private ShaderProfile getProfile(final String name) {
		for (final ShaderProfile prof : profiles) {
			if (prof.getName().equals(name)) {
				return prof;
			}
		}

		return null;
	}

	@Override
	public String getNameText() {
		return Lang.get("of.shaders.profile");
	}

	@Override
	public String getValueText(final String val) {
		return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>")
				: Shaders.translate("profile." + val, val);
	}

	@Override
	public String getValueColor(final String val) {
		return val.equals("<custom>") ? "\u00a7c" : "\u00a7a";
	}

	private static String detectProfileName(final ShaderProfile[] profs, final ShaderOption[] opts) {
		return detectProfileName(profs, opts, false);
	}

	private static String detectProfileName(final ShaderProfile[] profs, final ShaderOption[] opts, final boolean def) {
		final ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
		return prof == null ? "<custom>" : prof.getName();
	}

	private static String[] getProfileNames(final ShaderProfile[] profs) {
		final ArrayList list = new ArrayList();

		for (final ShaderProfile prof : profs) {
			list.add(prof.getName());
		}

		list.add("<custom>");
		final String[] var4 = (String[]) list.toArray(new String[list.size()]);
		return var4;
	}
}
