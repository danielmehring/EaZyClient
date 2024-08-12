package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class SoundEventAccessorComposite implements ISoundEventAccessor {

public static final int EaZy = 443;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** A composite (List) of ISoundEventAccessors */
	private final List soundPool = Lists.newArrayList();
	private final Random rnd = new Random();
	private final ResourceLocation soundLocation;
	private final SoundCategory category;
	private final double eventPitch;
	private final double eventVolume;
	// private static final String __OBFID = "http://https://fuckuskid00001146";

	public SoundEventAccessorComposite(final ResourceLocation soundLocation, final double pitch, final double volume,
			final SoundCategory category) {
		this.soundLocation = soundLocation;
		eventVolume = volume;
		eventPitch = pitch;
		this.category = category;
	}

	@Override
	public int getWeight() {
		int var1 = 0;
		ISoundEventAccessor var3;

		for (final Iterator var2 = soundPool.iterator(); var2.hasNext(); var1 += var3.getWeight()) {
			var3 = (ISoundEventAccessor) var2.next();
		}

		return var1;
	}

	public SoundPoolEntry cloneEntry1() {
		final int var1 = getWeight();

		if (!soundPool.isEmpty() && var1 != 0) {
			int var2 = rnd.nextInt(var1);
			final Iterator var3 = soundPool.iterator();
			ISoundEventAccessor var4;

			do {
				if (!var3.hasNext()) {
					return SoundHandler.missing_sound;
				}

				var4 = (ISoundEventAccessor) var3.next();
				var2 -= var4.getWeight();
			}
			while (var2 >= 0);

			final SoundPoolEntry var5 = (SoundPoolEntry) var4.cloneEntry();
			var5.setPitch(var5.getPitch() * eventPitch);
			var5.setVolume(var5.getVolume() * eventVolume);
			return var5;
		} else {
			return SoundHandler.missing_sound;
		}
	}

	public void addSoundToEventPool(final ISoundEventAccessor p_148727_1_) {
		soundPool.add(p_148727_1_);
	}

	public ResourceLocation getSoundEventLocation() {
		return soundLocation;
	}

	public SoundCategory getSoundCategory() {
		return category;
	}

	@Override
	public Object cloneEntry() {
		return cloneEntry1();
	}
}
