package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniformFloat4 extends ShaderUniformBase {

public static final int EaZy = 2021;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float[] values = new float[4];

	public ShaderUniformFloat4(final String name) {
		super(name);
	}

	@Override
	protected void onProgramChanged() {
		values[0] = 0.0F;
		values[1] = 0.0F;
		values[2] = 0.0F;
		values[3] = 0.0F;
	}

	public void setValue(final float f0, final float f1, final float f2, final float f3) {
		if (getLocation() >= 0) {
			if (values[0] != f0 || values[1] != f1 || values[2] != f2 || values[3] != f3) {
				ARBShaderObjects.glUniform4fARB(getLocation(), f0, f1, f2, f3);
				Shaders.checkGLError(getName());
				values[0] = f0;
				values[1] = f1;
				values[2] = f2;
				values[3] = f3;
			}
		}
	}

	public float[] getValues() {
		return values;
	}
}
