package net.minecraft.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import io.netty.util.internal.ThreadLocalRandom;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {

public static final int EaZy = 447;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The marker used for logging */
	private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
	private static final Logger logger = LogManager.getLogger();

	/** A reference to the sound handler. */
	private final SoundHandler sndHandler;

	/** Reference to the GameSettings object. */
	private final GameSettings options;

	/** A reference to the sound system. */
	private SoundManager.SoundSystemStarterThread sndSystem;

	/** Set to true when the SoundManager has been initialised. */
	private boolean loaded;

	/** A counter for how long the sound manager has been running */
	private int playTime = 0;

	/**
	 * Identifiers of all currently playing sounds. Type: HashBiMap<String,
	 * ISound>
	 */
	private final Map playingSounds = HashBiMap.create();

	/**
	 * Inverse map of currently playing sounds, automatically mirroring changes
	 * in original map
	 */
	private final Map invPlayingSounds;

	/** A HashMap<String, SoundPoolEntry> of the playing sounds. */
	private final Map playingSoundPoolEntries;

	/**
	 * Contains sounds mapped by category. Type: Multimap<SoundCategory, String>
	 */
	private final Multimap categorySounds;

	/** A subset of playingSounds, this contains only ITickableSounds */
	private final List tickableSounds;

	/** Contains sounds to play in n ticks. Type: HashMap<ISound, Integer> */
	private final Map delayedSounds;

	/**
	 * The future time in which to stop this sound. Type: HashMap<String,
	 * Integer>
	 */
	private final Map playingSoundsStopTime;
	// private static final String __OBFID = "http://https://fuckuskid00001141";

	public SoundManager(final SoundHandler p_i45119_1_, final GameSettings p_i45119_2_) {
		invPlayingSounds = ((BiMap) playingSounds).inverse();
		playingSoundPoolEntries = Maps.newHashMap();
		categorySounds = HashMultimap.create();
		tickableSounds = Lists.newArrayList();
		delayedSounds = Maps.newHashMap();
		playingSoundsStopTime = Maps.newHashMap();
		sndHandler = p_i45119_1_;
		options = p_i45119_2_;

		try {
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (final SoundSystemException var4) {
			logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", var4);
		}
	}

	public void reloadSoundSystem() {
		unloadSoundSystem();
		loadSoundSystem();
	}

	/**
	 * Tries to add the paulscode library and the relevant codecs. If it fails,
	 * the master volume will be set to zero.
	 */
	private synchronized void loadSoundSystem() {
		if (!loaded) {
			try {
				new Thread(new Runnable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001142";
					@Override
					public void run() {
						SoundSystemConfig.setLogger(new SoundSystemLogger() {
							// private static final String __OBFID =
							// "http://https://fuckuskid00002378";
							@Override
							public void message(final String p_message_1_, final int p_message_2_) {
								if (!p_message_1_.isEmpty()) {
									SoundManager.logger.info(p_message_1_);
								}
							}

							@Override
							public void importantMessage(final String p_importantMessage_1_,
									final int p_importantMessage_2_) {
								if (!p_importantMessage_1_.isEmpty()) {
									SoundManager.logger.warn(p_importantMessage_1_);
								}
							}

							@Override
							public void errorMessage(final String p_errorMessage_1_, final String p_errorMessage_2_,
									final int p_errorMessage_3_) {
								if (!p_errorMessage_2_.isEmpty()) {
									SoundManager.logger.error("Error in class \'" + p_errorMessage_1_ + "\'");
									SoundManager.logger.error(p_errorMessage_2_);
								}
							}
						});
						sndSystem = SoundManager.this.new SoundSystemStarterThread(null);
						loaded = true;
						sndSystem.setMasterVolume(options.getSoundLevel(SoundCategory.MASTER));
						SoundManager.logger.info(SoundManager.LOG_MARKER, "Sound engine started");
					}
				}, "Sound Library Loader").start();
			} catch (final RuntimeException var2) {
				logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
				options.setSoundLevel(SoundCategory.MASTER, 0.0F);
				options.saveOptions();
			}
		}
	}

	/**
	 * Returns the sound level (between 0.0 and 1.0) for a category, but 1.0 for
	 * the master sound category
	 */
	private float getSoundCategoryVolume(final SoundCategory p_148595_1_) {
		return p_148595_1_ != null && p_148595_1_ != SoundCategory.MASTER ? options.getSoundLevel(p_148595_1_) : 1.0F;
	}

	/**
	 * Adjusts volume for currently playing sounds in this category
	 */
	public void setSoundCategoryVolume(final SoundCategory p_148601_1_, final float p_148601_2_) {
		if (loaded) {
			if (p_148601_1_ == SoundCategory.MASTER) {
				sndSystem.setMasterVolume(p_148601_2_);
			} else {
				final Iterator var3 = categorySounds.get(p_148601_1_).iterator();

				while (var3.hasNext()) {
					final String var4 = (String) var3.next();
					final ISound var5 = (ISound) playingSounds.get(var4);
					final float var6 = getNormalizedVolume(var5, (SoundPoolEntry) playingSoundPoolEntries.get(var5),
							p_148601_1_);

					if (var6 <= 0.0F) {
						stopSound(var5);
					} else {
						sndSystem.setVolume(var4, var6);
					}
				}
			}
		}
	}

	/**
	 * Cleans up the Sound System
	 */
	public void unloadSoundSystem() {
		if (loaded) {
			stopAllSounds();
			sndSystem.cleanup();
			loaded = false;
		}
	}

	/**
	 * Stops all currently playing sounds
	 */
	public void stopAllSounds() {
		if (loaded) {
			final Iterator var1 = playingSounds.keySet().iterator();

			while (var1.hasNext()) {
				final String var2 = (String) var1.next();
				sndSystem.stop(var2);
			}

			playingSounds.clear();
			delayedSounds.clear();
			tickableSounds.clear();
			categorySounds.clear();
			playingSoundPoolEntries.clear();
			playingSoundsStopTime.clear();
		}
	}

	public void updateAllSounds() {
		++playTime;
		Iterator var1 = tickableSounds.iterator();
		String var3;

		while (var1.hasNext()) {
			final ITickableSound var2 = (ITickableSound) var1.next();
			var2.update();

			if (var2.isDonePlaying()) {
				stopSound(var2);
			} else {
				var3 = (String) invPlayingSounds.get(var2);
				sndSystem.setVolume(var3, getNormalizedVolume(var2, (SoundPoolEntry) playingSoundPoolEntries.get(var2),
						sndHandler.getSound(var2.getSoundLocation()).getSoundCategory()));
				sndSystem.setPitch(var3, getNormalizedPitch(var2, (SoundPoolEntry) playingSoundPoolEntries.get(var2)));
				sndSystem.setPosition(var3, var2.getXPosF(), var2.getYPosF(), var2.getZPosF());
			}
		}

		var1 = playingSounds.entrySet().iterator();
		ISound var4;

		while (var1.hasNext()) {
			final Entry var9 = (Entry) var1.next();
			var3 = (String) var9.getKey();
			var4 = (ISound) var9.getValue();

			if (!sndSystem.playing(var3)) {
				final int var5 = ((Integer) playingSoundsStopTime.get(var3));

				if (var5 <= playTime) {
					final int var6 = var4.getRepeatDelay();

					if (var4.canRepeat() && var6 > 0) {
						delayedSounds.put(var4, playTime + var6);
					}

					var1.remove();
					logger.debug(LOG_MARKER, "Removed channel {} because it\'s not playing anymore",
							new Object[] { var3 });
					sndSystem.removeSource(var3);
					playingSoundsStopTime.remove(var3);
					playingSoundPoolEntries.remove(var4);

					try {
						categorySounds.remove(sndHandler.getSound(var4.getSoundLocation()).getSoundCategory(), var3);
					} catch (final RuntimeException var8) {
					}

					if (var4 instanceof ITickableSound) {
						tickableSounds.remove(var4);
					}
				}
			}
		}

		final Iterator var10 = delayedSounds.entrySet().iterator();

		while (var10.hasNext()) {
			final Entry var11 = (Entry) var10.next();

			if (playTime >= ((Integer) var11.getValue())) {
				var4 = (ISound) var11.getKey();

				if (var4 instanceof ITickableSound) {
					((ITickableSound) var4).update();
				}

				playSound(var4);
				var10.remove();
			}
		}
	}

	/**
	 * Returns true if the sound is playing or still within time
	 */
	public boolean isSoundPlaying(final ISound p_148597_1_) {
		if (!loaded) {
			return false;
		} else {
			final String var2 = (String) invPlayingSounds.get(p_148597_1_);
			return var2 == null ? false
					: sndSystem.playing(var2) || playingSoundsStopTime.containsKey(var2)
							&& ((Integer) playingSoundsStopTime.get(var2)) <= playTime;
		}
	}

	public void stopSound(final ISound p_148602_1_) {
		if (loaded) {
			final String var2 = (String) invPlayingSounds.get(p_148602_1_);

			if (var2 != null) {
				sndSystem.stop(var2);
			}
		}
	}

	public void playSound(final ISound p_148611_1_) {
		if (loaded) {
			if (sndSystem.getMasterVolume() <= 0.0F) {
				logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero",
						new Object[] { p_148611_1_.getSoundLocation() });
			} else {
				final SoundEventAccessorComposite var2 = sndHandler.getSound(p_148611_1_.getSoundLocation());

				if (var2 == null) {
					logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}",
							new Object[] { p_148611_1_.getSoundLocation() });
				} else {
					final SoundPoolEntry var3 = (SoundPoolEntry) var2.cloneEntry();

					if (var3 == SoundHandler.missing_sound) {
						logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}",
								new Object[] { var2.getSoundEventLocation() });
					} else {
						final float var4 = p_148611_1_.getVolume();
						float var5 = 16.0F;

						if (var4 > 1.0F) {
							var5 *= var4;
						}

						final SoundCategory var6 = var2.getSoundCategory();
						final float var7 = getNormalizedVolume(p_148611_1_, var3, var6);
						final double var8 = getNormalizedPitch(p_148611_1_, var3);
						final ResourceLocation var10 = var3.getSoundPoolEntryLocation();

						if (var7 == 0.0F) {
							logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.",
									new Object[] { var10 });
						} else {
							final boolean var11 = p_148611_1_.canRepeat() && p_148611_1_.getRepeatDelay() == 0;
							final String var12 = MathHelper.func_180182_a(ThreadLocalRandom.current()).toString();

							if (var3.isStreamingSound()) {
								sndSystem.newStreamingSource(false, var12, getURLForSoundResource(var10),
										var10.toString(), var11, p_148611_1_.getXPosF(), p_148611_1_.getYPosF(),
										p_148611_1_.getZPosF(), p_148611_1_.getAttenuationType().getTypeInt(), var5);
							} else {
								sndSystem.newSource(false, var12, getURLForSoundResource(var10), var10.toString(),
										var11, p_148611_1_.getXPosF(), p_148611_1_.getYPosF(), p_148611_1_.getZPosF(),
										p_148611_1_.getAttenuationType().getTypeInt(), var5);
							}

							logger.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[] {
									var3.getSoundPoolEntryLocation(), var2.getSoundEventLocation(), var12 });
							sndSystem.setPitch(var12, (float) var8);
							sndSystem.setVolume(var12, var7);
							sndSystem.play(var12);
							playingSoundsStopTime.put(var12, playTime + 20);
							playingSounds.put(var12, p_148611_1_);
							playingSoundPoolEntries.put(p_148611_1_, var3);

							if (var6 != SoundCategory.MASTER) {
								categorySounds.put(var6, var12);
							}

							if (p_148611_1_ instanceof ITickableSound) {
								tickableSounds.add(p_148611_1_);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Normalizes pitch from parameters and clamps to [0.5, 2.0]
	 */
	private float getNormalizedPitch(final ISound p_148606_1_, final SoundPoolEntry p_148606_2_) {
		return (float) MathHelper.clamp_double(p_148606_1_.getPitch() * p_148606_2_.getPitch(), 0.5D, 2.0D);
	}

	/**
	 * Normalizes volume level from parameters. Range [0.0, 1.0]
	 */
	private float getNormalizedVolume(final ISound p_148594_1_, final SoundPoolEntry p_148594_2_,
			final SoundCategory p_148594_3_) {
		return (float) MathHelper.clamp_double(p_148594_1_.getVolume() * p_148594_2_.getVolume(), 0.0D, 1.0D)
				* getSoundCategoryVolume(p_148594_3_);
	}

	/**
	 * Pauses all currently playing sounds
	 */
	public void pauseAllSounds() {
		final Iterator var1 = playingSounds.keySet().iterator();

		while (var1.hasNext()) {
			final String var2 = (String) var1.next();
			logger.debug(LOG_MARKER, "Pausing channel {}", new Object[] { var2 });
			sndSystem.pause(var2);
		}
	}

	/**
	 * Resumes playing all currently playing sounds (after pauseAllSounds)
	 */
	public void resumeAllSounds() {
		final Iterator var1 = playingSounds.keySet().iterator();

		while (var1.hasNext()) {
			final String var2 = (String) var1.next();
			logger.debug(LOG_MARKER, "Resuming channel {}", new Object[] { var2 });
			sndSystem.play(var2);
		}
	}

	/**
	 * Adds a sound to play in n tick
	 */
	public void playDelayedSound(final ISound p_148599_1_, final int p_148599_2_) {
		delayedSounds.put(p_148599_1_, playTime + p_148599_2_);
	}

	private static URL getURLForSoundResource(final ResourceLocation p_148612_0_) {
		final String var1 = String.format("%s:%s:%s",
				new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
		final URLStreamHandler var2 = new URLStreamHandler() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001143";
			@Override
			protected URLConnection openConnection(final URL p_openConnection_1_) {
				return new URLConnection(p_openConnection_1_) {
					// private static final String __OBFID =
					// "http://https://fuckuskid00001144";
					@Override
					public void connect() {}

					@Override
					public InputStream getInputStream() throws IOException {

						return Minecraft.getResourceManager().getResource(p_148612_0_).getInputStream();
					}
				};
			}
		};

		try {
			return new URL((URL) null, var1, var2);
		} catch (final MalformedURLException var4) {
			throw new Error("TODO: Sanely handle url exception! :D");
		}
	}

	/**
	 * Sets the listener of sounds
	 */
	public void setListener(final EntityPlayer p_148615_1_, final float p_148615_2_) {
		if (loaded && p_148615_1_ != null) {
			final float var3 = p_148615_1_.prevRotationPitch
					+ (p_148615_1_.rotationPitch - p_148615_1_.prevRotationPitch) * p_148615_2_;
			final float var4 = p_148615_1_.prevRotationYaw
					+ (p_148615_1_.rotationYaw - p_148615_1_.prevRotationYaw) * p_148615_2_;
			final double var5 = p_148615_1_.prevPosX + (p_148615_1_.posX - p_148615_1_.prevPosX) * p_148615_2_;
			final double var7 = p_148615_1_.prevPosY + (p_148615_1_.posY - p_148615_1_.prevPosY) * p_148615_2_
					+ p_148615_1_.getEyeHeight();
			final double var9 = p_148615_1_.prevPosZ + (p_148615_1_.posZ - p_148615_1_.prevPosZ) * p_148615_2_;
			final float var11 = MathHelper.cos((var4 + 90.0F) * 0.017453292F);
			final float var12 = MathHelper.sin((var4 + 90.0F) * 0.017453292F);
			final float var13 = MathHelper.cos(-var3 * 0.017453292F);
			final float var14 = MathHelper.sin(-var3 * 0.017453292F);
			final float var15 = MathHelper.cos((-var3 + 90.0F) * 0.017453292F);
			final float var16 = MathHelper.sin((-var3 + 90.0F) * 0.017453292F);
			final float var17 = var11 * var13;
			final float var19 = var12 * var13;
			final float var20 = var11 * var15;
			final float var22 = var12 * var15;
			sndSystem.setListenerPosition((float) var5, (float) var7, (float) var9);
			sndSystem.setListenerOrientation(var17, var14, var19, var20, var16, var22);
		}
	}

	class SoundSystemStarterThread extends SoundSystem {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001145";

		private SoundSystemStarterThread() {}

		@Override
		public boolean playing(final String p_playing_1_) {
			synchronized (SoundSystemConfig.THREAD_SYNC) {
				if (soundLibrary == null) {
					return false;
				} else {
					final Source var3 = soundLibrary.getSources().get(p_playing_1_);
					return var3 == null ? false : var3.playing() || var3.paused() || var3.preLoad;
				}
			}
		}

		SoundSystemStarterThread(final Object p_i45118_2_) {
			this();
		}
	}
}
