package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniformInt extends ShaderUniformBase {

public static final int EaZy = 2022;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int value = -1;

	public ShaderUniformInt(final String name) {
		super(name);
	}

	@Override
	protected void onProgramChanged() {
		value = -1;
	}

	public void setValue(final int value) {
		if (getLocation() >= 0) {
			if (this.value != value) {
				ARBShaderObjects.glUniform1iARB(getLocation(), value);
				Shaders.checkGLError(getName());
				this.value = value;
			}
		}
	}

	public int getValue() {
		return value;
	}
}
