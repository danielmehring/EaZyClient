package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public abstract class ShaderUniformBase {

public static final int EaZy = 2020;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String name;
	private int program = -1;
	private int location = -1;

	public ShaderUniformBase(final String name) {
		this.name = name;
	}

	public void setProgram(final int program) {
		if (this.program != program) {
			this.program = program;
			location = ARBShaderObjects.glGetUniformLocationARB(program, name);
			onProgramChanged();
		}
	}

	protected abstract void onProgramChanged();

	public String getName() {
		return name;
	}

	public int getProgram() {
		return program;
	}

	public int getLocation() {
		return location;
	}

	public boolean isDefined() {
		return location >= 0;
	}
}
