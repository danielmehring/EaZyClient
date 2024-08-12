package optifine;

public class ReflectorClass {

public static final int EaZy = 1956;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String targetClassName;
	private boolean checked;
	private Class targetClass;

	public ReflectorClass(final String targetClassName) {
		this(targetClassName, false);
	}

	public ReflectorClass(final String targetClassName, final boolean lazyResolve) {
		this.targetClassName = null;
		checked = false;
		targetClass = null;
		this.targetClassName = targetClassName;

		if (!lazyResolve) {
			getTargetClass();
		}
	}

	public ReflectorClass(final Class targetClass) {
		targetClassName = null;
		checked = false;
		this.targetClass = null;
		this.targetClass = targetClass;
		targetClassName = targetClass.getName();
		checked = true;
	}

	public Class getTargetClass() {
		if (checked) {
			return targetClass;
		} else {
			checked = true;

			try {
				targetClass = Class.forName(targetClassName);
			} catch (final ClassNotFoundException var2) {
				Config.log("(Reflector) Class not present: " + targetClassName);
			} catch (final Throwable var3) {
				var3.printStackTrace();
			}

			return targetClass;
		}
	}

	public boolean exists() {
		return getTargetClass() != null;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public boolean isInstance(final Object obj) {
		return getTargetClass() == null ? false : getTargetClass().isInstance(obj);
	}

	public ReflectorField makeField(final String name) {
		return new ReflectorField(this, name);
	}

	public ReflectorMethod makeMethod(final String name) {
		return new ReflectorMethod(this, name);
	}

	public ReflectorMethod makeMethod(final String name, final Class[] paramTypes) {
		return new ReflectorMethod(this, name, paramTypes);
	}

	public ReflectorMethod makeMethod(final String name, final Class[] paramTypes, final boolean lazyResolve) {
		return new ReflectorMethod(this, name, paramTypes, lazyResolve);
	}
}
