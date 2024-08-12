package optifine;

import java.lang.reflect.Field;

public class ReflectorField {

public static final int EaZy = 1958;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ReflectorClass reflectorClass = null;
	private String targetFieldName = null;
	private boolean checked = false;
	private Field targetField = null;

	public ReflectorField(final ReflectorClass reflectorClass, final String targetFieldName) {
		this.reflectorClass = reflectorClass;
		this.targetFieldName = targetFieldName;
		getTargetField();
	}

	public Field getTargetField() {
		if (checked) {
			return targetField;
		} else {
			checked = true;
			final Class cls = reflectorClass.getTargetClass();

			if (cls == null) {
				return null;
			} else {
				try {
					targetField = cls.getDeclaredField(targetFieldName);
					targetField.setAccessible(true);
				} catch (final NoSuchFieldException var3) {
					Config.log("(Reflector) Field not present: " + cls.getName() + "." + targetFieldName);
				} catch (final SecurityException var4) {
					var4.printStackTrace();
				} catch (final Throwable var5) {
					var5.printStackTrace();
				}

				return targetField;
			}
		}
	}

	public Object getValue() {
		return Reflector.getFieldValue((Object) null, this);
	}

	public void setValue(final Object value) {
		Reflector.setFieldValue((Object) null, this, value);
	}

	public boolean exists() {
		return checked ? targetField != null : getTargetField() != null;
	}
}
