package shadersmod.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import optifine.Config;
import optifine.StrUtils;

public class ShaderPackParser {

public static final int EaZy = 2013;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
	private static final Set<String> setConstNames = makeSetConstNames();

	public static ShaderOption[] parseShaderPackOptions(final IShaderPack shaderPack, final String[] programNames,
			final List<Integer> listDimensions) {
		if (shaderPack == null) {
			return new ShaderOption[0];
		} else {
			final HashMap mapOptions = new HashMap();
			collectShaderOptions(shaderPack, "/shaders", programNames, mapOptions);
			final Iterator options = listDimensions.iterator();

			while (options.hasNext()) {
				final int sos = ((Integer) options.next());
				final String comp = "/shaders/world" + sos;
				collectShaderOptions(shaderPack, comp, programNames, mapOptions);
			}

			final Collection options1 = mapOptions.values();
			final ShaderOption[] sos1 = (ShaderOption[]) options1.toArray(new ShaderOption[options1.size()]);
			final Comparator comp1 = new Comparator() {
				public int compare(final ShaderOption o1, final ShaderOption o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}

				@Override
				public int compare(final Object x0, final Object x1) {
					return this.compare((ShaderOption) x0, (ShaderOption) x1);
				}
			};
			Arrays.sort(sos1, comp1);
			return sos1;
		}
	}

	private static void collectShaderOptions(final IShaderPack shaderPack, final String dir,
			final String[] programNames, final Map<String, ShaderOption> mapOptions) {
		for (final String programName2 : programNames) {
			final String programName = programName2;

			if (!programName.isEmpty()) {
				final String vsh = dir + "/" + programName + ".vsh";
				final String fsh = dir + "/" + programName + ".fsh";
				collectShaderOptions(shaderPack, vsh, mapOptions);
				collectShaderOptions(shaderPack, fsh, mapOptions);
			}
		}
	}

	private static void collectShaderOptions(final IShaderPack sp, final String path,
			final Map<String, ShaderOption> mapOptions) {
		final String[] lines = getLines(sp, path);

		for (final String line : lines) {
			final ShaderOption so = getShaderOption(line, path);

			if (so != null && (!so.checkUsed() || isOptionUsed(so, lines))) {
				final String key = so.getName();
				final ShaderOption so2 = mapOptions.get(key);

				if (so2 != null) {
					if (!Config.equals(so2.getValueDefault(), so.getValueDefault())) {
						Config.warn("Ambiguous shader option: " + so.getName());
						Config.warn(" - in " + Config.arrayToString(so2.getPaths()) + ": " + so2.getValueDefault());
						Config.warn(" - in " + Config.arrayToString(so.getPaths()) + ": " + so.getValueDefault());
						so2.setEnabled(false);
					}

					if (so2.getDescription() == null || so2.getDescription().length() <= 0) {
						so2.setDescription(so.getDescription());
					}

					so2.addPaths(so.getPaths());
				} else {
					mapOptions.put(key, so);
				}
			}
		}
	}

	private static boolean isOptionUsed(final ShaderOption so, final String[] lines) {
		for (final String line : lines) {
			if (so.isUsedInLine(line)) {
				return true;
			}
		}

		return false;
	}

	private static String[] getLines(final IShaderPack sp, final String path) {
		try {
			final String e = loadFile(path, sp, 0);

			if (e == null) {
				return new String[0];
			} else {
				final ByteArrayInputStream is = new ByteArrayInputStream(e.getBytes());
				final String[] lines = Config.readLines(is);
				return lines;
			}
		} catch (final IOException var5) {
			Config.dbg(var5.getClass().getName() + ": " + var5.getMessage());
			return new String[0];
		}
	}

	private static ShaderOption getShaderOption(final String line, final String path) {
		ShaderOption so = null;

		if (so == null) {
			so = ShaderOptionSwitch.parseOption(line, path);
		}

		if (so == null) {
			so = ShaderOptionVariable.parseOption(line, path);
		}

		if (so != null) {
			return so;
		} else {
			if (so == null) {
				so = ShaderOptionSwitchConst.parseOption(line, path);
			}

			if (so == null) {
				so = ShaderOptionVariableConst.parseOption(line, path);
			}

			return so != null && setConstNames.contains(so.getName()) ? so : null;
		}
	}

	private static Set<String> makeSetConstNames() {
		final HashSet set = new HashSet();
		set.add("shadowMapResolution");
		set.add("shadowDistance");
		set.add("shadowIntervalSize");
		set.add("generateShadowMipmap");
		set.add("generateShadowColorMipmap");
		set.add("shadowHardwareFiltering");
		set.add("shadowHardwareFiltering0");
		set.add("shadowHardwareFiltering1");
		set.add("shadowtex0Mipmap");
		set.add("shadowtexMipmap");
		set.add("shadowtex1Mipmap");
		set.add("shadowcolor0Mipmap");
		set.add("shadowColor0Mipmap");
		set.add("shadowcolor1Mipmap");
		set.add("shadowColor1Mipmap");
		set.add("shadowtex0Nearest");
		set.add("shadowtexNearest");
		set.add("shadow0MinMagNearest");
		set.add("shadowtex1Nearest");
		set.add("shadow1MinMagNearest");
		set.add("shadowcolor0Nearest");
		set.add("shadowColor0Nearest");
		set.add("shadowColor0MinMagNearest");
		set.add("shadowcolor1Nearest");
		set.add("shadowColor1Nearest");
		set.add("shadowColor1MinMagNearest");
		set.add("wetnessHalflife");
		set.add("drynessHalflife");
		set.add("eyeBrightnessHalflife");
		set.add("centerDepthHalflife");
		set.add("sunPathRotation");
		set.add("ambientOcclusionLevel");
		set.add("superSamplingLevel");
		set.add("noiseTextureResolution");
		return set;
	}

	public static ShaderProfile[] parseProfiles(final Properties props, final ShaderOption[] shaderOptions) {
		final String PREFIX_PROFILE = "profile.";
		final ArrayList list = new ArrayList();
		final Set keys = props.keySet();
		final Iterator profs = keys.iterator();

		while (profs.hasNext()) {
			final String key = (String) profs.next();

			if (key.startsWith(PREFIX_PROFILE)) {
				final String name = key.substring(PREFIX_PROFILE.length());
				props.getProperty(key);
				final HashSet parsedProfiles = new HashSet();
				final ShaderProfile p = parseProfile(name, props, parsedProfiles, shaderOptions);

				if (p != null) {
					list.add(p);
				}
			}
		}

		if (list.size() <= 0) {
			return null;
		} else {
			final ShaderProfile[] profs1 = (ShaderProfile[]) list.toArray(new ShaderProfile[list.size()]);
			return profs1;
		}
	}

	private static ShaderProfile parseProfile(final String name, final Properties props,
			final Set<String> parsedProfiles, final ShaderOption[] shaderOptions) {
		final String PREFIX_PROFILE = "profile.";
		final String key = PREFIX_PROFILE + name;

		if (parsedProfiles.contains(key)) {
			Config.warn("[Shaders] Profile already parsed: " + name);
			return null;
		} else {
			parsedProfiles.add(name);
			final ShaderProfile prof = new ShaderProfile(name);
			final String val = props.getProperty(key);
			final String[] parts = Config.tokenize(val, " ");

			for (final String part : parts) {
				if (part.startsWith(PREFIX_PROFILE)) {
					final String tokens = part.substring(PREFIX_PROFILE.length());
					final ShaderProfile option = parseProfile(tokens, props, parsedProfiles, shaderOptions);

					if (prof != null) {
						prof.addOptionValues(option);
						prof.addDisabledPrograms(option.getDisabledPrograms());
					}
				} else {
					final String[] var16 = Config.tokenize(part, ":=");
					String var17;

					if (var16.length == 1) {
						var17 = var16[0];
						boolean value = true;

						if (var17.startsWith("!")) {
							value = false;
							var17 = var17.substring(1);
						}

						final String so = "program.";

						if (!value && var17.startsWith("program.")) {
							final String var20 = var17.substring(so.length());

							if (!Shaders.isProgramPath(var20)) {
								Config.warn("Invalid program: " + var20 + " in profile: " + prof.getName());
							} else {
								prof.addDisabledProgram(var20);
							}
						} else {
							final ShaderOption so1 = ShaderUtils.getShaderOption(var17, shaderOptions);

							if (!(so1 instanceof ShaderOptionSwitch)) {
								Config.warn("[Shaders] Invalid option: " + var17);
							} else {
								prof.addOptionValue(var17, String.valueOf(value));
								so1.setVisible(true);
							}
						}
					} else if (var16.length != 2) {
						Config.warn("[Shaders] Invalid option value: " + part);
					} else {
						var17 = var16[0];
						final String var18 = var16[1];
						final ShaderOption var19 = ShaderUtils.getShaderOption(var17, shaderOptions);

						if (var19 == null) {
							Config.warn("[Shaders] Invalid option: " + part);
						} else if (!var19.isValidValue(var18)) {
							Config.warn("[Shaders] Invalid value: " + part);
						} else {
							var19.setVisible(true);
							prof.addOptionValue(var17, var18);
						}
					}
				}
			}

			return prof;
		}
	}

	public static Map<String, ShaderOption[]> parseGuiScreens(final Properties props,
			final ShaderProfile[] shaderProfiles, final ShaderOption[] shaderOptions) {
		final HashMap map = new HashMap();
		parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
		return map.isEmpty() ? null : map;
	}

	private static boolean parseGuiScreen(final String key, final Properties props,
			final Map<String, ShaderOption[]> map, final ShaderProfile[] shaderProfiles,
			final ShaderOption[] shaderOptions) {
		final String val = props.getProperty(key);

		if (val == null) {
			return false;
		} else {
			final ArrayList list = new ArrayList();
			final HashSet setNames = new HashSet();
			final String[] opNames = Config.tokenize(val, " ");

			for (final String opName : opNames) {
				if (opName.equals("<empty>")) {
					list.add((Object) null);
				} else if (setNames.contains(opName)) {
					Config.warn("[Shaders] Duplicate option: " + opName + ", key: " + key);
				} else {
					setNames.add(opName);

					if (opName.equals("<profile>")) {
						if (shaderProfiles == null) {
							Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + opName
									+ ", key: " + key);
						} else {
							final ShaderOptionProfile so = new ShaderOptionProfile(shaderProfiles, shaderOptions);
							list.add(so);
						}
					} else if (opName.equals("*")) {
						final ShaderOptionRest var14 = new ShaderOptionRest("<rest>");
						list.add(var14);
					} else if (opName.startsWith("[") && opName.endsWith("]")) {
						final String var16 = StrUtils.removePrefixSuffix(opName, "[", "]");

						if (!var16.matches("^[a-zA-Z0-9_]+$")) {
							Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
						} else if (!parseGuiScreen("screen." + var16, props, map, shaderProfiles, shaderOptions)) {
							Config.warn("[Shaders] Invalid screen: " + opName + ", key: " + key);
						} else {
							final ShaderOptionScreen optionScreen = new ShaderOptionScreen(var16);
							list.add(optionScreen);
						}
					} else {
						final ShaderOption var15 = ShaderUtils.getShaderOption(opName, shaderOptions);

						if (var15 == null) {
							Config.warn("[Shaders] Invalid option: " + opName + ", key: " + key);
							list.add((Object) null);
						} else {
							var15.setVisible(true);
							list.add(var15);
						}
					}
				}
			}

			final ShaderOption[] var13 = (ShaderOption[]) list.toArray(new ShaderOption[list.size()]);
			map.put(key, var13);
			return true;
		}
	}

	public static BufferedReader resolveIncludes(final BufferedReader reader, final String filePath,
			final IShaderPack shaderPack, final int includeLevel) throws IOException {
		String fileDir = "/";
		final int pos = filePath.lastIndexOf("/");

		if (pos >= 0) {
			fileDir = filePath.substring(0, pos);
		}

		final CharArrayWriter caw = new CharArrayWriter();

		while (true) {
			String car = reader.readLine();

			if (car == null) {
				final CharArrayReader car1 = new CharArrayReader(caw.toCharArray());
				return new BufferedReader(car1);
			}

			final Matcher m = PATTERN_INCLUDE.matcher(car);

			if (m.matches()) {
				final String fileInc = m.group(1);
				final boolean absolute = fileInc.startsWith("/");
				final String filePathInc = absolute ? "/shaders" + fileInc : fileDir + "/" + fileInc;
				car = loadFile(filePathInc, shaderPack, includeLevel);

				if (car == null) {
					throw new IOException("Included file not found: " + filePath);
				}
			}

			caw.write(car);
			caw.write("\n");
		}
	}

	private static String loadFile(final String filePath, final IShaderPack shaderPack, int includeLevel)
			throws IOException {
		if (includeLevel >= 10) {
			throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
		} else {
			++includeLevel;
			final InputStream in = shaderPack.getResourceAsStream(filePath);

			if (in == null) {
				return null;
			} else {
				final InputStreamReader isr = new InputStreamReader(in, "ASCII");
				BufferedReader br = new BufferedReader(isr);
				br = resolveIncludes(br, filePath, shaderPack, includeLevel);
				final CharArrayWriter caw = new CharArrayWriter();

				while (true) {
					final String line = br.readLine();

					if (line == null) {
						return caw.toString();
					}

					caw.write(line);
					caw.write("\n");
				}
			}
		}
	}
}
