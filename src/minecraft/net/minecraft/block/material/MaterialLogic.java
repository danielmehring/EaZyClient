package net.minecraft.block.material;

public class MaterialLogic extends Material {

public static final int EaZy = 412;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000539";

	public MaterialLogic(final MapColor p_i2112_1_) {
		super(p_i2112_1_);
		setAdventureModeExempt();
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	/**
	 * Will prevent grass from growing on dirt underneath and kill any grass
	 * below it if it returns true
	 */
	@Override
	public boolean blocksLight() {
		return false;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	@Override
	public boolean blocksMovement() {
		return false;
	}
}
