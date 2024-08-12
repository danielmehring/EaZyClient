package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolution {

public static final int EaZy = 544;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final double scaledWidthD;
	private final double scaledHeightD;
	private int scaledWidth;
	private int scaledHeight;
	private int scaleFactor;
	// private static final String __OBFID = "http://https://fuckuskid00000666";

	public ScaledResolution(final Minecraft mcIn, final int p_i46324_2_, final int p_i46324_3_) {
		scaledWidth = p_i46324_2_;
		scaledHeight = p_i46324_3_;
		scaleFactor = 1;
		final boolean var4 = mcIn.isUnicode();
		int var5 = Minecraft.gameSettings.guiScale;

		if (var5 == 0) {
			var5 = 1000;
		}

		while (scaleFactor < var5 && scaledWidth / (scaleFactor + 1) >= 320
				&& scaledHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}

		if (var4 && scaleFactor % 2 != 0 && scaleFactor != 1) {
			--scaleFactor;
		}

		scaledWidthD = (double) scaledWidth / (double) scaleFactor;
		scaledHeightD = (double) scaledHeight / (double) scaleFactor;
		scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
		scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
	}

	public int getScaledWidth() {
		return scaledWidth;
	}

	public int getScaledHeight() {
		return scaledHeight;
	}

	public double getScaledWidth_double() {
		return scaledWidthD;
	}

	public double getScaledHeight_double() {
		return scaledHeightD;
	}

	public int getScaleFactor() {
		return scaleFactor;
	}
}
