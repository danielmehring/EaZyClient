package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public class Frustrum implements ICamera {

public static final int EaZy = 710;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ClippingHelper clippingHelper;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	// private static final String __OBFID = "http://https://fuckuskid00000976";

	public Frustrum() {
		this(ClippingHelperImpl.getInstance());
	}

	public Frustrum(final ClippingHelper p_i46196_1_) {
		clippingHelper = p_i46196_1_;
	}

	@Override
	public void setPosition(final double p_78547_1_, final double p_78547_3_, final double p_78547_5_) {
		xPosition = p_78547_1_;
		yPosition = p_78547_3_;
		zPosition = p_78547_5_;
	}

	/**
	 * Calls the clipping helper. Returns true if the box is inside all 6
	 * clipping planes, otherwise returns false.
	 */
	public boolean isBoxInFrustum(final double p_78548_1_, final double p_78548_3_, final double p_78548_5_,
			final double p_78548_7_, final double p_78548_9_, final double p_78548_11_) {
		return clippingHelper.isBoxInFrustum(p_78548_1_ - xPosition, p_78548_3_ - yPosition, p_78548_5_ - zPosition,
				p_78548_7_ - xPosition, p_78548_9_ - yPosition, p_78548_11_ - zPosition);
	}

	/**
	 * Returns true if the bounding box is inside all 6 clipping planes,
	 * otherwise returns false.
	 */
	@Override
	public boolean isBoundingBoxInFrustum(final AxisAlignedBB p_78546_1_) {
		return isBoxInFrustum(p_78546_1_.minX, p_78546_1_.minY, p_78546_1_.minZ, p_78546_1_.maxX, p_78546_1_.maxY,
				p_78546_1_.maxZ);
	}
}
