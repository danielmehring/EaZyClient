package shadersmod.client;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.MathHelper;

public class ClippingHelperShadow extends ClippingHelper {

public static final int EaZy = 1987;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static ClippingHelperShadow instance = new ClippingHelperShadow();
	float[] frustumTest = new float[6];
	float[][] shadowClipPlanes = new float[10][4];
	int shadowClipPlaneCount;
	float[] matInvMP = new float[16];
	float[] vecIntersection = new float[4];

	/**
	 * Returns true if the box is inside all 6 clipping planes, otherwise
	 * returns false.
	 */
	@Override
	public boolean isBoxInFrustum(final double x1, final double y1, final double z1, final double x2, final double y2,
			final double z2) {
		for (int index = 0; index < shadowClipPlaneCount; ++index) {
			final float[] plane = shadowClipPlanes[index];

			if (dot4(plane, x1, y1, z1) <= 0.0D && dot4(plane, x2, y1, z1) <= 0.0D && dot4(plane, x1, y2, z1) <= 0.0D
					&& dot4(plane, x2, y2, z1) <= 0.0D && dot4(plane, x1, y1, z2) <= 0.0D
					&& dot4(plane, x2, y1, z2) <= 0.0D && dot4(plane, x1, y2, z2) <= 0.0D
					&& dot4(plane, x2, y2, z2) <= 0.0D) {
				return false;
			}
		}

		return true;
	}

	private double dot4(final float[] plane, final double x, final double y, final double z) {
		return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
	}

	private double dot3(final float[] vecA, final float[] vecB) {
		return (double) vecA[0] * (double) vecB[0] + (double) vecA[1] * (double) vecB[1]
				+ (double) vecA[2] * (double) vecB[2];
	}

	public static ClippingHelper getInstance() {
		instance.init();
		return instance;
	}

	private void normalize3(final float[] plane) {
		float length = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);

		if (length == 0.0F) {
			length = 1.0F;
		}

		plane[0] /= length;
		plane[1] /= length;
		plane[2] /= length;
	}

	private void assignPlane(final float[] plane, final float a, final float b, final float c, final float d) {
		final float length = (float) Math.sqrt(a * a + b * b + c * c);
		plane[0] = a / length;
		plane[1] = b / length;
		plane[2] = c / length;
		plane[3] = d / length;
	}

	private void copyPlane(final float[] dst, final float[] src) {
		dst[0] = src[0];
		dst[1] = src[1];
		dst[2] = src[2];
		dst[3] = src[3];
	}

	private void cross3(final float[] out, final float[] a, final float[] b) {
		out[0] = a[1] * b[2] - a[2] * b[1];
		out[1] = a[2] * b[0] - a[0] * b[2];
		out[2] = a[0] * b[1] - a[1] * b[0];
	}

	private float length(final float x, final float y, final float z) {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	private float distance(final float x1, final float y1, final float z1, final float x2, final float y2,
			final float z2) {
		return length(x1 - x2, y1 - y2, z1 - z2);
	}

	private void makeShadowPlane(final float[] shadowPlane, final float[] positivePlane, final float[] negativePlane,
			final float[] vecSun) {
		cross3(vecIntersection, positivePlane, negativePlane);
		cross3(shadowPlane, vecIntersection, vecSun);
		normalize3(shadowPlane);
		final float dotPN = (float) dot3(positivePlane, negativePlane);
		final float dotSN = (float) dot3(shadowPlane, negativePlane);
		final float disSN = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN,
				negativePlane[1] * dotSN, negativePlane[2] * dotSN);
		final float disPN = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN,
				negativePlane[1] * dotPN, negativePlane[2] * dotPN);
		final float k1 = disSN / disPN;
		final float dotSP = (float) dot3(shadowPlane, positivePlane);
		final float disSP = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP,
				positivePlane[1] * dotSP, positivePlane[2] * dotSP);
		final float disNP = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotPN,
				positivePlane[1] * dotPN, positivePlane[2] * dotPN);
		final float k2 = disSP / disNP;
		shadowPlane[3] = positivePlane[3] * k1 + negativePlane[3] * k2;
	}

	public void init() {
		final float[] matPrj = field_178625_b;
		final float[] matMdv = field_178626_c;
		final float[] matMP = clippingMatrix;
		System.arraycopy(Shaders.faProjection, 0, matPrj, 0, 16);
		System.arraycopy(Shaders.faModelView, 0, matMdv, 0, 16);
		SMath.multiplyMat4xMat4(matMP, matMdv, matPrj);
		assignPlane(frustum[0], matMP[3] - matMP[0], matMP[7] - matMP[4], matMP[11] - matMP[8], matMP[15] - matMP[12]);
		assignPlane(frustum[1], matMP[3] + matMP[0], matMP[7] + matMP[4], matMP[11] + matMP[8], matMP[15] + matMP[12]);
		assignPlane(frustum[2], matMP[3] + matMP[1], matMP[7] + matMP[5], matMP[11] + matMP[9], matMP[15] + matMP[13]);
		assignPlane(frustum[3], matMP[3] - matMP[1], matMP[7] - matMP[5], matMP[11] - matMP[9], matMP[15] - matMP[13]);
		assignPlane(frustum[4], matMP[3] - matMP[2], matMP[7] - matMP[6], matMP[11] - matMP[10], matMP[15] - matMP[14]);
		assignPlane(frustum[5], matMP[3] + matMP[2], matMP[7] + matMP[6], matMP[11] + matMP[10], matMP[15] + matMP[14]);
		final float[] vecSun = Shaders.shadowLightPositionVector;
		final float test0 = (float) dot3(frustum[0], vecSun);
		final float test1 = (float) dot3(frustum[1], vecSun);
		final float test2 = (float) dot3(frustum[2], vecSun);
		final float test3 = (float) dot3(frustum[3], vecSun);
		final float test4 = (float) dot3(frustum[4], vecSun);
		final float test5 = (float) dot3(frustum[5], vecSun);
		shadowClipPlaneCount = 0;

		if (test0 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[0]);

			if (test0 > 0.0F) {
				if (test2 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[0], frustum[2], vecSun);
				}

				if (test3 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[0], frustum[3], vecSun);
				}

				if (test4 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[0], frustum[4], vecSun);
				}

				if (test5 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[0], frustum[5], vecSun);
				}
			}
		}

		if (test1 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[1]);

			if (test1 > 0.0F) {
				if (test2 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[1], frustum[2], vecSun);
				}

				if (test3 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[1], frustum[3], vecSun);
				}

				if (test4 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[1], frustum[4], vecSun);
				}

				if (test5 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[1], frustum[5], vecSun);
				}
			}
		}

		if (test2 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[2]);

			if (test2 > 0.0F) {
				if (test0 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[2], frustum[0], vecSun);
				}

				if (test1 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[2], frustum[1], vecSun);
				}

				if (test4 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[2], frustum[4], vecSun);
				}

				if (test5 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[2], frustum[5], vecSun);
				}
			}
		}

		if (test3 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[3]);

			if (test3 > 0.0F) {
				if (test0 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[3], frustum[0], vecSun);
				}

				if (test1 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[3], frustum[1], vecSun);
				}

				if (test4 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[3], frustum[4], vecSun);
				}

				if (test5 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[3], frustum[5], vecSun);
				}
			}
		}

		if (test4 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[4]);

			if (test4 > 0.0F) {
				if (test0 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[4], frustum[0], vecSun);
				}

				if (test1 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[4], frustum[1], vecSun);
				}

				if (test2 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[4], frustum[2], vecSun);
				}

				if (test3 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[4], frustum[3], vecSun);
				}
			}
		}

		if (test5 >= 0.0F) {
			copyPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[5]);

			if (test5 > 0.0F) {
				if (test0 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[5], frustum[0], vecSun);
				}

				if (test1 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[5], frustum[1], vecSun);
				}

				if (test2 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[5], frustum[2], vecSun);
				}

				if (test3 < 0.0F) {
					makeShadowPlane(shadowClipPlanes[shadowClipPlaneCount++], frustum[5], frustum[3], vecSun);
				}
			}
		}
	}
}
