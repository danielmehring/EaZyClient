package optifine;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl {

public static final int EaZy = 1906;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private GameSettings.Options option = null;

	public GuiOptionButtonOF(final int id, final int x, final int y, final GameSettings.Options option,
			final String text) {
		super(id, x, y, option, text);
		this.option = option;
	}

	@Override
	public GameSettings.Options getOption() {
		return option;
	}
}
