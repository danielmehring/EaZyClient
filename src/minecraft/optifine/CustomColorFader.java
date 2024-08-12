package optifine;

import net.minecraft.util.Vec3;

public class CustomColorFader {

public static final int EaZy = 1889;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Vec3 color = null;
	private long timeUpdate = System.currentTimeMillis();

	public Vec3 getColor(final double x, final double y, final double z) {
		if (color == null) {
			color = new Vec3(x, y, z);
			return color;
		} else {
			final long timeNow = System.currentTimeMillis();
			final long timeDiff = timeNow - timeUpdate;

			if (timeDiff == 0L) {
				return color;
			} else {
				timeUpdate = timeNow;

				if (Math.abs(x - color.xCoord) < 0.004D && Math.abs(y - color.yCoord) < 0.004D
						&& Math.abs(z - color.zCoord) < 0.004D) {
					return color;
				} else {
					double k = timeDiff * 0.001D;
					k = Config.limit(k, 0.0D, 1.0D);
					final double dx = x - color.xCoord;
					final double dy = y - color.yCoord;
					final double dz = z - color.zCoord;
					final double xn = color.xCoord + dx * k;
					final double yn = color.yCoord + dy * k;
					final double zn = color.zCoord + dz * k;
					color = new Vec3(xn, yn, zn);
					return color;
				}
			}
		}
	}
}
