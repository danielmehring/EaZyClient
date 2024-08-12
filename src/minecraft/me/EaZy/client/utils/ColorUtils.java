package me.EaZy.client.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColorUtils {

	public static final int EaZy = 207;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static int transparency(final int color, final double alpha) {
		final Color c = new Color(color);
		final float r = 0.003921569F * c.getRed();
		final float g = 0.003921569F * c.getGreen();
		final float b = 0.003921569F * c.getBlue();
		return new Color(r, g, b, (float) alpha).getRGB();
	}

	public static Color rainbow(final long offset, final float fade) {
		final float hue = (System.nanoTime() + offset) / 1.0E10F % 1.0F;
		final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
		final Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade,
				c.getAlpha() / 255.0F);
	}

	public static Color invertedColor(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}

	public static Color averageColor(String path) throws IOException {
		BufferedImage in = ImageIO.read(new File(path));

		BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = newImage.createGraphics();
		g.drawImage(in, 0, 0, null);
		g.dispose();
		int w = in.getWidth();
		int h = in.getHeight();
		long sumr = 0, sumg = 0, sumb = 0;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Color pixel = new Color(in.getRGB(x, y));
				sumr += pixel.getRed();
				sumg += pixel.getGreen();
				sumb += pixel.getBlue();
			}
		}
		long num = w * h;
		return new Color((int) (sumr / num), (int) (sumg / num), (int) (sumb / num));
	}
}
