package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class PositionedSoundRecord extends PositionedSound {

public static final int EaZy = 440;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001120";

	public static PositionedSoundRecord createPositionedSoundRecord(final ResourceLocation soundResource,
			final float pitch) {
		return new PositionedSoundRecord(soundResource, 0.25F, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F,
				0.0F);
	}

	public static PositionedSoundRecord createPositionedSoundRecord(final ResourceLocation soundResource) {
		return new PositionedSoundRecord(soundResource, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F,
				0.0F);
	}

	public static PositionedSoundRecord createRecordSoundAtPosition(final ResourceLocation soundResource,
			final float xPosition, final float yPosition, final float zPosition) {
		return new PositionedSoundRecord(soundResource, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xPosition,
				yPosition, zPosition);
	}

	public PositionedSoundRecord(final ResourceLocation soundResource, final float volume, final float pitch,
			final float xPosition, final float yPosition, final float zPosition) {
		this(soundResource, volume, pitch, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
	}

	private PositionedSoundRecord(final ResourceLocation soundResource, final float volume, final float pitch,
			final boolean repeat, final int repeatDelay, final ISound.AttenuationType attenuationType,
			final float xPosition, final float yPosition, final float zPosition) {
		super(soundResource);
		this.volume = volume;
		this.pitch = pitch;
		xPosF = xPosition;
		yPosF = yPosition;
		zPosF = zPosition;
		this.repeat = repeat;
		this.repeatDelay = repeatDelay;
		this.attenuationType = attenuationType;
	}
}
