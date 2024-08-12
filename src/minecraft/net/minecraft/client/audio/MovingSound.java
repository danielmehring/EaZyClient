package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class MovingSound extends PositionedSound implements ITickableSound {

public static final int EaZy = 435;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected boolean donePlaying = false;
	// private static final String __OBFID = "http://https://fuckuskid00001117";

	protected MovingSound(final ResourceLocation location) {
		super(location);
	}

	@Override
	public boolean isDonePlaying() {
		return donePlaying;
	}
}
