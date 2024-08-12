package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class PositionedSound implements ISound {

public static final int EaZy = 439;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final ResourceLocation positionedSoundLocation;
	protected float volume = 1.0F;
	protected float pitch = 1.0F;
	protected float xPosF;
	protected float yPosF;
	protected float zPosF;
	protected boolean repeat = false;

	/** The number of ticks between repeating the sound */
	protected int repeatDelay = 0;
	protected ISound.AttenuationType attenuationType;
	// private static final String __OBFID = "http://https://fuckuskid00001116";

	protected PositionedSound(final ResourceLocation soundResource) {
		attenuationType = ISound.AttenuationType.LINEAR;
		positionedSoundLocation = soundResource;
	}

	@Override
	public ResourceLocation getSoundLocation() {
		return positionedSoundLocation;
	}

	@Override
	public boolean canRepeat() {
		return repeat;
	}

	@Override
	public int getRepeatDelay() {
		return repeatDelay;
	}

	@Override
	public float getVolume() {
		return volume;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public float getXPosF() {
		return xPosF;
	}

	@Override
	public float getYPosF() {
		return yPosF;
	}

	@Override
	public float getZPosF() {
		return zPosF;
	}

	@Override
	public ISound.AttenuationType getAttenuationType() {
		return attenuationType;
	}
}
