package net.minecraft.block.material;

public class MaterialPortal extends Material {

public static final int EaZy = 413;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000545";

	public MaterialPortal(final MapColor p_i2118_1_) {
		super(p_i2118_1_);
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
