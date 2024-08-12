package optifine;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl {

public static final int EaZy = 1907;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private GameSettings.Options option = null;

	public GuiOptionSliderOF(final int id, final int x, final int y, final GameSettings.Options option) {
		super(id, x, y, option);
		this.option = option;
	}

	@Override
	public GameSettings.Options getOption() {
		return option;
	}
}
