package net.minecraft.client.model;

public class ModelSign extends ModelBase {

public static final int EaZy = 602;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The board on a sign that has the writing on it. */
	public ModelRenderer signBoard = new ModelRenderer(this, 0, 0);

	/** The stick a sign stands on. */
	public ModelRenderer signStick;
	// private static final String __OBFID = "http://https://fuckuskid00000854";

	public ModelSign() {
		signBoard.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
		signStick = new ModelRenderer(this, 0, 14);
		signStick.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
	}

	/**
	 * Renders the sign model through TileEntitySignRenderer
	 */
	public void renderSign() {
		signBoard.render(0.0625F);
		signStick.render(0.0625F);
	}
}
