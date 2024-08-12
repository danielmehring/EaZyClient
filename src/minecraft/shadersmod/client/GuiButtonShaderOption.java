package shadersmod.client;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonShaderOption extends GuiButton {

public static final int EaZy = 1991;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ShaderOption shaderOption = null;

	public GuiButtonShaderOption(final int buttonId, final int x, final int y, final int widthIn, final int heightIn,
			final ShaderOption shaderOption, final String text) {
		super(buttonId, x, y, widthIn, heightIn, text);
		this.shaderOption = shaderOption;
	}

	public ShaderOption getShaderOption() {
		return shaderOption;
	}
}
