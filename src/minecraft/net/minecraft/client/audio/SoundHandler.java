package net.minecraft.client.audio;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SoundHandler implements IResourceManagerReloadListener, IUpdatePlayerListBox {

public static final int EaZy = 444;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final Gson field_147699_c = new GsonBuilder()
			.registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
	private static final ParameterizedType field_147696_d = new ParameterizedType() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001148";
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class, SoundList.class };
		}

		@Override
		public Type getRawType() {
			return Map.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};
	public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"),
			0.0D, 0.0D, false);
	private final SoundRegistry sndRegistry = new SoundRegistry();
	private final SoundManager sndManager;
	private final IResourceManager mcResourceManager;
	// private static final String __OBFID = "http://https://fuckuskid00001147";

	public SoundHandler(final IResourceManager manager, final GameSettings p_i45122_2_) {
		mcResourceManager = manager;
		sndManager = new SoundManager(this, p_i45122_2_);
	}

	@Override
	public void onResourceManagerReload(final IResourceManager p_110549_1_) {
		sndManager.reloadSoundSystem();
		sndRegistry.clearMap();
		final Iterator var2 = p_110549_1_.getResourceDomains().iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();

			try {
				final List var4 = p_110549_1_.getAllResources(new ResourceLocation(var3, "sounds.json"));
				final Iterator var5 = var4.iterator();

				while (var5.hasNext()) {
					final IResource var6 = (IResource) var5.next();

					try {
						final Map var7 = getSoundMap(var6.getInputStream());
						final Iterator var8 = var7.entrySet().iterator();

						while (var8.hasNext()) {
							final Entry var9 = (Entry) var8.next();
							loadSoundResource(new ResourceLocation(var3, (String) var9.getKey()),
									(SoundList) var9.getValue());
						}
					} catch (final RuntimeException var10) {
						logger.warn("Invalid sounds.json", var10);
					}
				}
			} catch (final IOException var11) {
			}
		}
	}

	protected Map getSoundMap(final InputStream p_175085_1_) {
		Map var2;

		try {
			var2 = (Map) field_147699_c.fromJson(new InputStreamReader(p_175085_1_), field_147696_d);
		}
		finally {
			IOUtils.closeQuietly(p_175085_1_);
		}

		return var2;
	}

	private void loadSoundResource(final ResourceLocation p_147693_1_, final SoundList p_147693_2_) {
		final boolean var4 = !sndRegistry.containsKey(p_147693_1_);
		SoundEventAccessorComposite var3;

		if (!var4 && !p_147693_2_.canReplaceExisting()) {
			var3 = (SoundEventAccessorComposite) sndRegistry.getObject(p_147693_1_);
		} else {
			if (!var4) {
				logger.debug("Replaced sound event location {}", new Object[] { p_147693_1_ });
			}

			var3 = new SoundEventAccessorComposite(p_147693_1_, 1.0D, 1.0D, p_147693_2_.getSoundCategory());
			sndRegistry.registerSound(var3);
		}

		final Iterator var5 = p_147693_2_.getSoundList().iterator();

		while (var5.hasNext()) {
			final SoundList.SoundEntry var6 = (SoundList.SoundEntry) var5.next();
			final String var7 = var6.getSoundEntryName();
			final ResourceLocation var8 = new ResourceLocation(var7);
			final String var9 = var7.contains(":") ? var8.getResourceDomain() : p_147693_1_.getResourceDomain();
			Object var10;

			switch (SoundHandler.SwitchType.field_148765_a[var6.getSoundEntryType().ordinal()]) {
				case 1:
					final ResourceLocation var11 = new ResourceLocation(var9,
							"sounds/" + var8.getResourcePath() + ".ogg");
					InputStream var12 = null;

					try {
						var12 = mcResourceManager.getResource(var11).getInputStream();
					} catch (final FileNotFoundException var18) {
						logger.warn("File {} does not exist, cannot add it to event {}",
								new Object[] { var11, p_147693_1_ });
						continue;
					} catch (final IOException var19) {
						logger.warn("Could not load sound file " + var11 + ", cannot add it to event " + p_147693_1_,
								var19);
						continue;
					}
					finally {
						IOUtils.closeQuietly(var12);
					}

					var10 = new SoundEventAccessor(new SoundPoolEntry(var11, var6.getSoundEntryPitch(),
							var6.getSoundEntryVolume(), var6.isStreaming()), var6.getSoundEntryWeight());
					break;

				case 2:
					var10 = new ISoundEventAccessor() {
						final ResourceLocation field_148726_a = new ResourceLocation(var9, var6.getSoundEntryName());

						// private static final String __OBFID =
						// "http://https://fuckuskid00001149";
						@Override
						public int getWeight() {
							final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite) sndRegistry
									.getObject(field_148726_a);
							return var1 == null ? 0 : var1.getWeight();
						}

						public SoundPoolEntry getEntry() {
							final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite) sndRegistry
									.getObject(field_148726_a);
							return (SoundPoolEntry) (var1 == null ? SoundHandler.missing_sound : var1.cloneEntry());
						}

						@Override
						public Object cloneEntry() {
							return getEntry();
						}
					};

					break;
				default:
					throw new IllegalStateException("IN YOU FACE");
			}

			var3.addSoundToEventPool((ISoundEventAccessor) var10);
		}
	}

	public SoundEventAccessorComposite getSound(final ResourceLocation p_147680_1_) {
		return (SoundEventAccessorComposite) sndRegistry.getObject(p_147680_1_);
	}

	/**
	 * Play a sound
	 */
	public void playSound(final ISound p_147682_1_) {
		sndManager.playSound(p_147682_1_);
	}

	/**
	 * Plays the sound in n ticks
	 */
	public void playDelayedSound(final ISound p_147681_1_, final int p_147681_2_) {
		sndManager.playDelayedSound(p_147681_1_, p_147681_2_);
	}

	public void setListener(final EntityPlayer p_147691_1_, final float p_147691_2_) {
		sndManager.setListener(p_147691_1_, p_147691_2_);
	}

	public void pauseSounds() {
		sndManager.pauseAllSounds();
	}

	public void stopSounds() {
		sndManager.stopAllSounds();
	}

	public void unloadSounds() {
		sndManager.unloadSoundSystem();
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		sndManager.updateAllSounds();
	}

	public void resumeSounds() {
		sndManager.resumeAllSounds();
	}

	public void setSoundLevel(final SoundCategory p_147684_1_, final float volume) {
		if (p_147684_1_ == SoundCategory.MASTER && volume <= 0.0F) {
			stopSounds();
		}

		sndManager.setSoundCategoryVolume(p_147684_1_, volume);
	}

	public void stopSound(final ISound p_147683_1_) {
		sndManager.stopSound(p_147683_1_);
	}

	/**
	 * Returns a random sound from one or more categories
	 */
	public SoundEventAccessorComposite getRandomSoundFromCategories(final SoundCategory... p_147686_1_) {
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = sndRegistry.getKeys().iterator();

		while (var3.hasNext()) {
			final ResourceLocation var4 = (ResourceLocation) var3.next();
			final SoundEventAccessorComposite var5 = (SoundEventAccessorComposite) sndRegistry.getObject(var4);

			if (ArrayUtils.contains(p_147686_1_, var5.getSoundCategory())) {
				var2.add(var5);
			}
		}

		if (var2.isEmpty()) {
			return null;
		} else {
			return (SoundEventAccessorComposite) var2.get(new Random().nextInt(var2.size()));
		}
	}

	public boolean isSoundPlaying(final ISound p_147692_1_) {
		return sndManager.isSoundPlaying(p_147692_1_);
	}

	static final class SwitchType {
		static final int[] field_148765_a = new int[SoundList.SoundEntry.Type.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001150";

		static {
			try {
				field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
