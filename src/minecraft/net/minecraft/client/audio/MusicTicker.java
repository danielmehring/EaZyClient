package net.minecraft.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class MusicTicker implements IUpdatePlayerListBox {

public static final int EaZy = 438;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Random rand = new Random();
	private final Minecraft mc;
	private ISound currentMusic;
	private int timeUntilNextMusic = 100;
	// private static final String __OBFID = "http://https://fuckuskid00001138";

	public MusicTicker(final Minecraft mcIn) {
		mc = mcIn;
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		final MusicTicker.MusicType var1 = mc.getAmbientMusicType();

		if (currentMusic != null) {
			if (!var1.getMusicLocation().equals(currentMusic.getSoundLocation())) {
				mc.getSoundHandler().stopSound(currentMusic);
				timeUntilNextMusic = MathHelper.getRandomIntegerInRange(rand, 0, var1.getMinDelay() / 2);
			}

			if (!mc.getSoundHandler().isSoundPlaying(currentMusic)) {
				currentMusic = null;
				timeUntilNextMusic = Math.min(
						MathHelper.getRandomIntegerInRange(rand, var1.getMinDelay(), var1.getMaxDelay()),
						timeUntilNextMusic);
			}
		}

		if (currentMusic == null && timeUntilNextMusic-- <= 0) {
			currentMusic = PositionedSoundRecord.createPositionedSoundRecord(var1.getMusicLocation());
			mc.getSoundHandler().playSound(currentMusic);
			timeUntilNextMusic = Integer.MAX_VALUE;
		}
	}

	public static enum MusicType {
		MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600), GAME("GAME", 1,
				new ResourceLocation("minecraft:music.game"), 12000, 24000), CREATIVE("CREATIVE", 2,
						new ResourceLocation("minecraft:music.game.creative"), 1200, 3600), CREDITS("CREDITS", 3,
								new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE,
								Integer.MAX_VALUE), NETHER("NETHER", 4,
										new ResourceLocation("minecraft:music.game.nether"), 1200, 3600), END_BOSS(
												"END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"),
												0, 0), END("END", 6, new ResourceLocation("minecraft:music.game.end"),
														6000, 24000);
		private final ResourceLocation musicLocation;
		private final int minDelay;
		private final int maxDelay;

		private MusicType(final String p_i45111_1_, final int p_i45111_2_, final ResourceLocation location,
				final int p_i45111_4_, final int p_i45111_5_) {
			musicLocation = location;
			minDelay = p_i45111_4_;
			maxDelay = p_i45111_5_;
		}

		public ResourceLocation getMusicLocation() {
			return musicLocation;
		}

		public int getMinDelay() {
			return minDelay;
		}

		public int getMaxDelay() {
			return maxDelay;
		}
	}
}
