package optifine;

import java.lang.reflect.Method;

public class ReflectorMethod {

public static final int EaZy = 1960;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ReflectorClass reflectorClass;
	private String targetMethodName;
	private Class[] targetMethodParameterTypes;
	private boolean checked;
	private Method targetMethod;

	public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName) {
		this(reflectorClass, targetMethodName, (Class[]) null, false);
	}

	public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName,
			final Class[] targetMethodParameterTypes) {
		this(reflectorClass, targetMethodName, targetMethodParameterTypes, false);
	}

	public ReflectorMethod(final ReflectorClass reflectorClass, final String targetMethodName,
			final Class[] targetMethodParameterTypes, final boolean lazyResolve) {
		this.reflectorClass = null;
		this.targetMethodName = null;
		this.targetMethodParameterTypes = null;
		checked = false;
		targetMethod = null;
		this.reflectorClass = reflectorClass;
		this.targetMethodName = targetMethodName;
		this.targetMethodParameterTypes = targetMethodParameterTypes;

		if (!lazyResolve) {
			getTargetMethod();
		}
	}

	public Method getTargetMethod() {
		if (checked) {
			return targetMethod;
		} else {
			checked = true;
			final Class cls = reflectorClass.getTargetClass();

			if (cls == null) {
				return null;
			} else {
				try {
					if (targetMethodParameterTypes == null) {
						final Method[] e = Reflector.getMethods(cls, targetMethodName);

						if (e.length <= 0) {
							Config.log("(Reflector) Method not present: " + cls.getName() + "." + targetMethodName);
							return null;
						}

						if (e.length > 1) {
							Config.warn("(Reflector) More than one method found: " + cls.getName() + "."
									+ targetMethodName);

							for (final Method m : e) {
								Config.warn("(Reflector)  - " + m);
							}

							return null;
						}

						targetMethod = e[0];
					} else {
						targetMethod = Reflector.getMethod(cls, targetMethodName, targetMethodParameterTypes);
					}

					if (targetMethod == null) {
						Config.log("(Reflector) Method not present: " + cls.getName() + "." + targetMethodName);
						return null;
					} else {
						targetMethod.setAccessible(true);
						return targetMethod;
					}
				} catch (final Throwable var5) {
					var5.printStackTrace();
					return null;
				}
			}
		}
	}

	public boolean exists() {
		return checked ? targetMethod != null : getTargetMethod() != null;
	}

	public Class getReturnType() {
		final Method tm = getTargetMethod();
		return tm == null ? null : tm.getReturnType();
	}

	public void deactivate() {
		checked = true;
		targetMethod = null;
	}
}
