package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;

import org.lwjgl.opengl.GL11;

public class Lagometer {

public static final int EaZy = 1927;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Minecraft mc;
	private static GameSettings gameSettings;
	public static boolean active = false;
	public static Lagometer.TimerNano timerTick = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerScheduledExecutables = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerChunkUpload = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerChunkUpdate = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerVisibility = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerTerrain = new Lagometer.TimerNano();
	public static Lagometer.TimerNano timerServer = new Lagometer.TimerNano();
	private static long[] timesFrame = new long[512];
	private static long[] timesTick = new long[512];
	private static long[] timesScheduledExecutables = new long[512];
	private static long[] timesChunkUpload = new long[512];
	private static long[] timesChunkUpdate = new long[512];
	private static long[] timesVisibility = new long[512];
	private static long[] timesTerrain = new long[512];
	private static long[] timesServer = new long[512];
	private static boolean[] gcs = new boolean[512];
	private static int numRecordedFrameTimes = 0;
	private static long prevFrameTimeNano = -1L;
	private static long renderTimeNano = 0L;
	private static long memTimeStartMs = System.currentTimeMillis();
	private static long memStart = getMemoryUsed();
	private static long memLast = memStart;
	private static long memTimeDiffMs = 1L;
	private static long memDiff = 0L;
	private static int memMbSec = 0;

	public static boolean updateMemoryAllocation() {
		final long timeNowMs = System.currentTimeMillis();
		final long memNow = getMemoryUsed();
		boolean gc = false;

		if (memNow < memLast) {
			final double memDiffMb = memDiff / 1000000.0D;
			final double timeDiffSec = memTimeDiffMs / 1000.0D;
			final int mbSec = (int) (memDiffMb / timeDiffSec);

			if (mbSec > 0) {
				memMbSec = mbSec;
			}

			memTimeStartMs = timeNowMs;
			memStart = memNow;
			memTimeDiffMs = 0L;
			memDiff = 0L;
			gc = true;
		} else {
			memTimeDiffMs = timeNowMs - memTimeStartMs;
			memDiff = memNow - memStart;
		}

		memLast = memNow;
		return gc;
	}

	private static long getMemoryUsed() {
		final Runtime r = Runtime.getRuntime();
		return r.totalMemory() - r.freeMemory();
	}

	public static void updateLagometer() {
		if (mc == null) {
			mc = Minecraft.getMinecraft();
			gameSettings = Minecraft.gameSettings;
		}

		if (gameSettings.showDebugInfo && gameSettings.ofLagometer) {
			active = true;
			final long timeNowNano = System.nanoTime();

			if (prevFrameTimeNano == -1L) {
				prevFrameTimeNano = timeNowNano;
			} else {
				final int frameIndex = numRecordedFrameTimes & timesFrame.length - 1;
				++numRecordedFrameTimes;
				final boolean gc = updateMemoryAllocation();
				timesFrame[frameIndex] = timeNowNano - prevFrameTimeNano - renderTimeNano;
				timesTick[frameIndex] = timerTick.timeNano;
				timesScheduledExecutables[frameIndex] = timerScheduledExecutables.timeNano;
				timesChunkUpload[frameIndex] = timerChunkUpload.timeNano;
				timesChunkUpdate[frameIndex] = timerChunkUpdate.timeNano;
				timesVisibility[frameIndex] = timerVisibility.timeNano;
				timesTerrain[frameIndex] = timerTerrain.timeNano;
				timesServer[frameIndex] = timerServer.timeNano;
				gcs[frameIndex] = gc;
				timerTick.reset();
				timerScheduledExecutables.reset();
				timerVisibility.reset();
				timerChunkUpdate.reset();
				timerChunkUpload.reset();
				timerTerrain.reset();
				timerServer.reset();
				prevFrameTimeNano = System.nanoTime();
			}
		} else {
			active = false;
			prevFrameTimeNano = -1L;
		}
	}

	public static void showLagometer(final ScaledResolution scaledResolution) {
		if (gameSettings != null) {
			if (gameSettings.ofLagometer) {
				final long timeRenderStartNano = System.nanoTime();
				GlStateManager.clear(256);
				GlStateManager.matrixMode(5889);
				GlStateManager.pushMatrix();
				GlStateManager.enableColorMaterial();
				GlStateManager.loadIdentity();
				GlStateManager.ortho(0.0D, Minecraft.displayWidth, Minecraft.displayHeight, 0.0D, 1000.0D, 3000.0D);
				GlStateManager.matrixMode(5888);
				GlStateManager.pushMatrix();
				GlStateManager.loadIdentity();
				GlStateManager.translate(0.0F, 0.0F, -2000.0F);
				GL11.glLineWidth(1.0F);
				GlStateManager.disableTexture2D();
				final Tessellator tess = Tessellator.getInstance();
				final WorldRenderer tessellator = tess.getWorldRenderer();
				tessellator.startDrawing(1);
				int y60;
				int y30;
				float lumMem;

				for (y60 = 0; y60 < timesFrame.length; ++y60) {
					y30 = (y60 - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
					y30 += 155;
					lumMem = Minecraft.displayHeight;
					if (gcs[y60]) {
						renderTime(y60, timesFrame[y60], y30, y30 / 2, 0, lumMem, tessellator);
					} else {
						renderTime(y60, timesFrame[y60], y30, y30, y30, lumMem, tessellator);
						lumMem -= renderTime(y60, timesServer[y60], y30 / 2, y30 / 2, y30 / 2, lumMem, tessellator);
						lumMem -= renderTime(y60, timesTerrain[y60], 0, y30, 0, lumMem, tessellator);
						lumMem -= renderTime(y60, timesVisibility[y60], y30, y30, 0, lumMem, tessellator);
						lumMem -= renderTime(y60, timesChunkUpdate[y60], y30, 0, 0, lumMem, tessellator);
						lumMem -= renderTime(y60, timesChunkUpload[y60], y30, 0, y30, lumMem, tessellator);
						lumMem -= renderTime(y60, timesScheduledExecutables[y60], 0, 0, y30, lumMem, tessellator);
						renderTime(y60, timesTick[y60], 0, y30, y30, lumMem, tessellator);
					}
				}

				renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, Minecraft.displayHeight, tessellator);
				renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, Minecraft.displayHeight, tessellator);
				tess.draw();
				GlStateManager.enableTexture2D();
				y60 = Minecraft.displayHeight - 80;
				y30 = Minecraft.displayHeight - 160;
				mc.fontRendererObj.drawString("30", 2, y30 + 1, -8947849);
				mc.fontRendererObj.drawString("30", 1, y30, -3881788);
				mc.fontRendererObj.drawString("60", 2, y60 + 1, -8947849);
				mc.fontRendererObj.drawString("60", 1, y60, -3881788);
				GlStateManager.matrixMode(5889);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
				GlStateManager.popMatrix();
				GlStateManager.enableTexture2D();
				lumMem = 1.0F - (float) ((System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
				lumMem = Config.limit(lumMem, 0.0F, 1.0F);
				final int var14 = (int) (170.0F + lumMem * 85.0F);
				final int memColG = (int) (100.0F + lumMem * 55.0F);
				final int memColB = (int) (10.0F + lumMem * 10.0F);
				final int colMem = var14 << 16 | memColG << 8 | memColB;
				final int posX = 512 / scaledResolution.getScaleFactor() + 2;
				final int posY = Minecraft.displayHeight / scaledResolution.getScaleFactor() - 8;
				Gui.drawRect(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
				mc.fontRendererObj.drawString(" " + memMbSec + " MB/s", posX, posY, colMem);
				renderTimeNano = System.nanoTime() - timeRenderStartNano;
			}
		}
	}

	private static long renderTime(final int frameNum, final long time, final int r, final int g, final int b,
			final float baseHeight, final WorldRenderer tessellator) {
		final long heightTime = time / 200000L;

		if (heightTime < 3L) {
			return 0L;
		} else {
			tessellator.func_178961_b(r, g, b, 255);
			tessellator.addVertex(frameNum + 0.5F, baseHeight - heightTime + 0.5F, 0.0D);
			tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
			return heightTime;
		}
	}

	private static long renderTimeDivider(final int frameStart, final int frameEnd, final long time, final int r,
			final int g, final int b, final float baseHeight, final WorldRenderer tessellator) {
		final long heightTime = time / 200000L;

		if (heightTime < 3L) {
			return 0L;
		} else {
			tessellator.func_178961_b(r, g, b, 255);
			tessellator.addVertex(frameStart + 0.5F, baseHeight - heightTime + 0.5F, 0.0D);
			tessellator.addVertex(frameEnd + 0.5F, baseHeight - heightTime + 0.5F, 0.0D);
			return heightTime;
		}
	}

	public static boolean isActive() {
		return active;
	}

	public static class TimerNano {
		public long timeStartNano = 0L;
		public long timeNano = 0L;

		public void start() {
			if (Lagometer.active) {
				if (timeStartNano == 0L) {
					timeStartNano = System.nanoTime();
				}
			}
		}

		public void end() {
			if (Lagometer.active) {
				if (timeStartNano != 0L) {
					timeNano += System.nanoTime() - timeStartNano;
					timeStartNano = 0L;
				}
			}
		}

		private void reset() {
			timeNano = 0L;
			timeStartNano = 0L;
		}
	}
}
