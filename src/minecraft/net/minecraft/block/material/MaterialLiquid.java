package net.minecraft.block.material;

public class MaterialLiquid extends Material {

public static final int EaZy = 411;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000541";

	public MaterialLiquid(final MapColor p_i2114_1_) {
		super(p_i2114_1_);
		setReplaceable();
		setNoPushMobility();
	}

	/**
	 * Returns if blocks of these materials are liquids.
	 */
	@Override
	public boolean isLiquid() {
		return true;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	@Override
	public boolean blocksMovement() {
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}
}
