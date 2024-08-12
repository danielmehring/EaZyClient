package optifine;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.World;

import java.util.Properties;

public class CustomSkyLayer {

public static final int EaZy = 1896;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String source = null;
	private int startFadeIn = -1;
	private int endFadeIn = -1;
	private int startFadeOut = -1;
	private int endFadeOut = -1;
	private int blend = 1;
	private boolean rotate = false;
	private float speed = 1.0F;
	private float[] axis;
	private RangeListInt days;
	private int daysLoop;
	public int textureId;
	public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };

	public CustomSkyLayer(final Properties props, final String defSource) {
		axis = DEFAULT_AXIS;
		days = null;
		daysLoop = 8;
		textureId = -1;
		final ConnectedParser cp = new ConnectedParser("CustomSky");
		source = props.getProperty("source", defSource);
		startFadeIn = parseTime(props.getProperty("startFadeIn"));
		endFadeIn = parseTime(props.getProperty("endFadeIn"));
		startFadeOut = parseTime(props.getProperty("startFadeOut"));
		endFadeOut = parseTime(props.getProperty("endFadeOut"));
		blend = Blender.parseBlend(props.getProperty("blend"));
		rotate = parseBoolean(props.getProperty("rotate"), true);
		speed = parseFloat(props.getProperty("speed"), 1.0F);
		axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
		days = cp.parseRangeListInt(props.getProperty("days"));
		daysLoop = cp.parseInt(props.getProperty("daysLoop"), 8);
	}

	private int parseTime(final String str) {
		if (str == null) {
			return -1;
		} else {
			final String[] strs = Config.tokenize(str, ":");

			if (strs.length != 2) {
				Config.warn("Invalid time: " + str);
				return -1;
			} else {
				final String hourStr = strs[0];
				final String minStr = strs[1];
				int hour = Config.parseInt(hourStr, -1);
				final int min = Config.parseInt(minStr, -1);

				if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
					hour -= 6;

					if (hour < 0) {
						hour += 24;
					}

					final int time = hour * 1000 + (int) (min / 60.0D * 1000.0D);
					return time;
				} else {
					Config.warn("Invalid time: " + str);
					return -1;
				}
			}
		}
	}

	private boolean parseBoolean(final String str, final boolean defVal) {
		if (str == null) {
			return defVal;
		} else if (str.toLowerCase().equals("true")) {
			return true;
		} else if (str.toLowerCase().equals("false")) {
			return false;
		} else {
			Config.warn("Unknown boolean: " + str);
			return defVal;
		}
	}

	private float parseFloat(final String str, final float defVal) {
		if (str == null) {
			return defVal;
		} else {
			final float val = Config.parseFloat(str, Float.MIN_VALUE);

			if (val == Float.MIN_VALUE) {
				Config.warn("Invalid value: " + str);
				return defVal;
			} else {
				return val;
			}
		}
	}

	private float[] parseAxis(final String str, final float[] defVal) {
		if (str == null) {
			return defVal;
		} else {
			final String[] strs = Config.tokenize(str, " ");

			if (strs.length != 3) {
				Config.warn("Invalid axis: " + str);
				return defVal;
			} else {
				final float[] fs = new float[3];

				for (int ax = 0; ax < strs.length; ++ax) {
					fs[ax] = Config.parseFloat(strs[ax], Float.MIN_VALUE);

					if (fs[ax] == Float.MIN_VALUE) {
						Config.warn("Invalid axis: " + str);
						return defVal;
					}

					if (fs[ax] < -1.0F || fs[ax] > 1.0F) {
						Config.warn("Invalid axis values: " + str);
						return defVal;
					}
				}

				final float var9 = fs[0];
				final float ay = fs[1];
				final float az = fs[2];

				if (var9 * var9 + ay * ay + az * az < 1.0E-5F) {
					Config.warn("Invalid axis values: " + str);
					return defVal;
				} else {
					final float[] as = new float[] { az, ay, -var9 };
					return as;
				}
			}
		}
	}

	public boolean isValid(final String path) {
		if (source == null) {
			Config.warn("No source texture: " + path);
			return false;
		} else {
			source = TextureUtils.fixResourcePath(source, TextureUtils.getBasePath(path));

			if (startFadeIn >= 0 && endFadeIn >= 0 && endFadeOut >= 0) {
				final int timeFadeIn = normalizeTime(endFadeIn - startFadeIn);

				if (startFadeOut < 0) {
					startFadeOut = normalizeTime(endFadeOut - timeFadeIn);

					if (timeBetween(startFadeOut, startFadeIn, endFadeIn)) {
						startFadeOut = endFadeIn;
					}
				}

				final int timeOn = normalizeTime(startFadeOut - endFadeIn);
				final int timeFadeOut = normalizeTime(endFadeOut - startFadeOut);
				final int timeOff = normalizeTime(startFadeIn - endFadeOut);
				final int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;

				if (timeSum != 24000) {
					Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + timeSum);
					return false;
				} else if (speed < 0.0F) {
					Config.warn("Invalid speed: " + speed);
					return false;
				} else if (daysLoop <= 0) {
					Config.warn("Invalid daysLoop: " + daysLoop);
					return false;
				} else {
					return true;
				}
			} else {
				Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
				return false;
			}
		}
	}

	private int normalizeTime(int timeMc) {
		while (timeMc >= 24000) {
			timeMc -= 24000;
		}

		while (timeMc < 0) {
			timeMc += 24000;
		}

		return timeMc;
	}

	public void render(final int timeOfDay, final float celestialAngle, final float rainBrightness) {
		float brightness = rainBrightness * getFadeBrightness(timeOfDay);
		brightness = Config.limit(brightness, 0.0F, 1.0F);

		if (brightness >= 1.0E-4F) {
			GlStateManager.func_179144_i(textureId);
			Blender.setupBlend(blend, brightness);
			GlStateManager.pushMatrix();

			if (rotate) {
				GlStateManager.rotate(celestialAngle * 360.0F * speed, axis[0], axis[1], axis[2]);
			}

			final Tessellator tess = Tessellator.getInstance();
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
			renderSide(tess, 4);
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			renderSide(tess, 1);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			renderSide(tess, 0);
			GlStateManager.popMatrix();
			GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			renderSide(tess, 5);
			GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			renderSide(tess, 2);
			GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			renderSide(tess, 3);
			GlStateManager.popMatrix();
		}
	}

	private float getFadeBrightness(final int timeOfDay) {
		int timeFadeOut;
		int timeDiff;

		if (timeBetween(timeOfDay, startFadeIn, endFadeIn)) {
			timeFadeOut = normalizeTime(endFadeIn - startFadeIn);
			timeDiff = normalizeTime(timeOfDay - startFadeIn);
			return (float) timeDiff / (float) timeFadeOut;
		} else if (timeBetween(timeOfDay, endFadeIn, startFadeOut)) {
			return 1.0F;
		} else if (timeBetween(timeOfDay, startFadeOut, endFadeOut)) {
			timeFadeOut = normalizeTime(endFadeOut - startFadeOut);
			timeDiff = normalizeTime(timeOfDay - startFadeOut);
			return 1.0F - (float) timeDiff / (float) timeFadeOut;
		} else {
			return 0.0F;
		}
	}

	private void renderSide(final Tessellator tess, final int side) {
		final WorldRenderer wr = tess.getWorldRenderer();
		final double tx = side % 3 / 3.0D;
		final double ty = side / 3 / 2.0D;
		wr.startDrawingQuads();
		wr.addVertexWithUV(-100.0D, -100.0D, -100.0D, tx, ty);
		wr.addVertexWithUV(-100.0D, -100.0D, 100.0D, tx, ty + 0.5D);
		wr.addVertexWithUV(100.0D, -100.0D, 100.0D, tx + 0.3333333333333333D, ty + 0.5D);
		wr.addVertexWithUV(100.0D, -100.0D, -100.0D, tx + 0.3333333333333333D, ty);
		tess.draw();
	}

	public boolean isActive(final World world, final int timeOfDay) {
		if (timeBetween(timeOfDay, endFadeOut, startFadeIn)) {
			return false;
		} else {
			if (days != null) {
				final long time = world.getWorldTime();
				long timeShift;

				for (timeShift = time - startFadeIn; timeShift < 0L; timeShift += 24000 * daysLoop) {
				}

				final int day = (int) (timeShift / 24000L);
				final int dayOfLoop = day % daysLoop;

				if (!days.isInRange(dayOfLoop)) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean timeBetween(final int timeOfDay, final int timeStart, final int timeEnd) {
		return timeStart <= timeEnd ? timeOfDay >= timeStart && timeOfDay <= timeEnd
				: timeOfDay >= timeStart || timeOfDay <= timeEnd;
	}
}
