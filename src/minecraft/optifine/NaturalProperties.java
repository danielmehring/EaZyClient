package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.IdentityHashMap;
import java.util.Map;

public class NaturalProperties {

public static final int EaZy = 1936;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int rotation = 1;
	public boolean flip = false;
	private final Map[] quadMaps = new Map[8];

	public NaturalProperties(final String type) {
		if (type.equals("4")) {
			rotation = 4;
		} else if (type.equals("2")) {
			rotation = 2;
		} else if (type.equals("F")) {
			flip = true;
		} else if (type.equals("4F")) {
			rotation = 4;
			flip = true;
		} else if (type.equals("2F")) {
			rotation = 2;
			flip = true;
		} else {
			Config.warn("NaturalTextures: Unknown type: " + type);
		}
	}

	public boolean isValid() {
		return rotation != 2 && rotation != 4 ? flip : true;
	}

	public synchronized BakedQuad getQuad(final BakedQuad quadIn, final int rotate, final boolean flipU) {
		int index = rotate;

		if (flipU) {
			index = rotate | 4;
		}

		if (index > 0 && index < quadMaps.length) {
			Object map = quadMaps[index];

			if (map == null) {
				map = new IdentityHashMap(1);
				quadMaps[index] = (Map) map;
			}

			BakedQuad quad = (BakedQuad) ((Map) map).get(quadIn);

			if (quad == null) {
				quad = makeQuad(quadIn, rotate, flipU);
				((Map) map).put(quadIn, quad);
			}

			return quad;
		} else {
			return quadIn;
		}
	}

	private BakedQuad makeQuad(final BakedQuad quad, final int rotate, final boolean flipU) {
		int[] vertexData = quad.func_178209_a();
		final int tintIndex = quad.func_178211_c();
		final EnumFacing face = quad.getFace();
		final TextureAtlasSprite sprite = quad.getSprite();

		if (!isFullSprite(quad)) {
			return quad;
		} else {
			vertexData = transformVertexData(vertexData, rotate, flipU);
			final BakedQuad bq = new BakedQuad(vertexData, tintIndex, face, sprite);
			return bq;
		}
	}

	private int[] transformVertexData(final int[] vertexData, final int rotate, final boolean flipU) {
		final int[] vertexData2 = vertexData.clone();
		int v2 = 4 - rotate;

		if (flipU) {
			v2 += 3;
		}

		v2 %= 4;
		final int step = vertexData2.length / 4;

		for (int v = 0; v < 4; ++v) {
			final int pos = v * step;
			final int pos2 = v2 * step;
			vertexData2[pos2 + 4] = vertexData[pos + 4];
			vertexData2[pos2 + 4 + 1] = vertexData[pos + 4 + 1];

			if (flipU) {
				--v2;

				if (v2 < 0) {
					v2 = 3;
				}
			} else {
				++v2;

				if (v2 > 3) {
					v2 = 0;
				}
			}
		}

		return vertexData2;
	}

	private boolean isFullSprite(final BakedQuad quad) {
		final TextureAtlasSprite sprite = quad.getSprite();
		final float uMin = sprite.getMinU();
		final float uMax = sprite.getMaxU();
		final float uSize = uMax - uMin;
		final float uDelta = uSize / 256.0F;
		final float vMin = sprite.getMinV();
		final float vMax = sprite.getMaxV();
		final float vSize = vMax - vMin;
		final float vDelta = vSize / 256.0F;
		final int[] vertexData = quad.func_178209_a();
		final int step = vertexData.length / 4;

		for (int i = 0; i < 4; ++i) {
			final int pos = i * step;
			final float u = Float.intBitsToFloat(vertexData[pos + 4]);
			final float v = Float.intBitsToFloat(vertexData[pos + 4 + 1]);

			if (!equalsDelta(u, uMin, uDelta) && !equalsDelta(u, uMax, uDelta)) {
				return false;
			}

			if (!equalsDelta(v, vMin, vDelta) && !equalsDelta(v, vMax, vDelta)) {
				return false;
			}
		}

		return true;
	}

	private boolean equalsDelta(final float x1, final float x2, final float deltaMax) {
		final float deltaAbs = MathHelper.abs(x1 - x2);
		return deltaAbs < deltaMax;
	}
}
