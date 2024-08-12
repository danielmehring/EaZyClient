package shadersmod.client;

import optifine.Config;

public class ShaderUtils {

public static final int EaZy = 2023;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static ShaderOption getShaderOption(final String name, final ShaderOption[] opts) {
		if (opts == null) {
			return null;
		} else {
			for (final ShaderOption so : opts) {
				if (so.getName().equals(name)) {
					return so;
				}
			}

			return null;
		}
	}

	public static ShaderProfile detectProfile(final ShaderProfile[] profs, final ShaderOption[] opts,
			final boolean def) {
		if (profs == null) {
			return null;
		} else {
			for (final ShaderProfile prof : profs) {
				if (matchProfile(prof, opts, def)) {
					return prof;
				}
			}

			return null;
		}
	}

	public static boolean matchProfile(final ShaderProfile prof, final ShaderOption[] opts, final boolean def) {
		if (prof == null) {
			return false;
		} else if (opts == null) {
			return false;
		} else {
			final String[] optsProf = prof.getOptions();

			for (final String opt : optsProf) {
				final ShaderOption so = getShaderOption(opt, opts);

				if (so != null) {
					final String optVal = def ? so.getValueDefault() : so.getValue();
					final String profVal = prof.getValue(opt);

					if (!Config.equals(optVal, profVal)) {
						return false;
					}
				}
			}

			return true;
		}
	}
}
