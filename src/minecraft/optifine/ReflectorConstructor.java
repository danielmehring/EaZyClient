package optifine;

import java.lang.reflect.Constructor;

public class ReflectorConstructor {

public static final int EaZy = 1957;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ReflectorClass reflectorClass = null;
	private Class[] parameterTypes = null;
	private boolean checked = false;
	private Constructor targetConstructor = null;

	public ReflectorConstructor(final ReflectorClass reflectorClass, final Class[] parameterTypes) {
		this.reflectorClass = reflectorClass;
		this.parameterTypes = parameterTypes;
		getTargetConstructor();
	}

	public Constructor getTargetConstructor() {
		if (checked) {
			return targetConstructor;
		} else {
			checked = true;
			final Class cls = reflectorClass.getTargetClass();

			if (cls == null) {
				return null;
			} else {
				try {
					targetConstructor = findConstructor(cls, parameterTypes);

					if (targetConstructor == null) {
						Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: "
								+ Config.arrayToString(parameterTypes));
					}

					if (targetConstructor != null) {
						targetConstructor.setAccessible(true);
					}
				} catch (final Throwable var3) {
					var3.printStackTrace();
				}

				return targetConstructor;
			}
		}
	}

	private static Constructor findConstructor(final Class cls, final Class[] paramTypes) {
		final Constructor[] cs = cls.getDeclaredConstructors();

		for (final Constructor c : cs) {
			final Class[] types = c.getParameterTypes();

			if (Reflector.matchesTypes(paramTypes, types)) {
				return c;
			}
		}

		return null;
	}

	public boolean exists() {
		return checked ? targetConstructor != null : getTargetConstructor() != null;
	}

	public void deactivate() {
		checked = true;
		targetConstructor = null;
	}
}
