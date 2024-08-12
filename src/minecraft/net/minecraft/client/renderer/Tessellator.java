package net.minecraft.client.renderer;

public class Tessellator {

public static final int EaZy = 811;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final WorldRenderer worldRenderer;
	private final WorldVertexBufferUploader field_178182_b = new WorldVertexBufferUploader();

	/** The static instance of the Tessellator. */
	private static final Tessellator instance = new Tessellator(2097152);

	public static Tessellator getInstance() {
		return instance;
	}

	public Tessellator(final int p_i1250_1_) {
		worldRenderer = new WorldRenderer(p_i1250_1_);
	}

	/**
	 * Draws the data set up in this tessellator and resets the state to prepare
	 * for new drawing.
	 */
	public int draw() {
		return field_178182_b.draw(worldRenderer, worldRenderer.draw());
	}

	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}
}
