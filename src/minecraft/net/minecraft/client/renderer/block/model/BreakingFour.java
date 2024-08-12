package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class BreakingFour extends BakedQuad {

public static final int EaZy = 679;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final TextureAtlasSprite texture;
	// private static final String __OBFID = "http://https://fuckuskid00002492";

	public BreakingFour(final BakedQuad p_i46217_1_, final TextureAtlasSprite p_i46217_2_) {
		super(Arrays.copyOf(p_i46217_1_.func_178209_a(), p_i46217_1_.func_178209_a().length),
				p_i46217_1_.field_178213_b, FaceBakery.func_178410_a(p_i46217_1_.func_178209_a()));
		texture = p_i46217_2_;
		func_178217_e();
	}

	private void func_178217_e() {
		for (int var1 = 0; var1 < 4; ++var1) {
			func_178216_a(var1);
		}
	}

	private void func_178216_a(final int p_178216_1_) {
		final int step = field_178215_a.length / 4;
		final int var2 = step * p_178216_1_;
		final float var3 = Float.intBitsToFloat(field_178215_a[var2]);
		final float var4 = Float.intBitsToFloat(field_178215_a[var2 + 1]);
		final float var5 = Float.intBitsToFloat(field_178215_a[var2 + 2]);
		float var6 = 0.0F;
		float var7 = 0.0F;

		switch (BreakingFour.SwitchEnumFacing.field_178419_a[face.ordinal()]) {
			case 1:
				var6 = var3 * 16.0F;
				var7 = (1.0F - var5) * 16.0F;
				break;

			case 2:
				var6 = var3 * 16.0F;
				var7 = var5 * 16.0F;
				break;

			case 3:
				var6 = (1.0F - var3) * 16.0F;
				var7 = (1.0F - var4) * 16.0F;
				break;

			case 4:
				var6 = var3 * 16.0F;
				var7 = (1.0F - var4) * 16.0F;
				break;

			case 5:
				var6 = var5 * 16.0F;
				var7 = (1.0F - var4) * 16.0F;
				break;

			case 6:
				var6 = (1.0F - var5) * 16.0F;
				var7 = (1.0F - var4) * 16.0F;
		}

		field_178215_a[var2 + 4] = Float.floatToRawIntBits(texture.getInterpolatedU(var6));
		field_178215_a[var2 + 4 + 1] = Float.floatToRawIntBits(texture.getInterpolatedV(var7));
	}

	static final class SwitchEnumFacing {
		static final int[] field_178419_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002491";

		static {
			try {
				field_178419_a[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_178419_a[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_178419_a[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178419_a[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178419_a[EnumFacing.WEST.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178419_a[EnumFacing.EAST.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
