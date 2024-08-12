package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor {

public static final int EaZy = 442;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final SoundPoolEntry entry;
	private final int weight;
	// private static final String __OBFID = "http://https://fuckuskid00001153";

	SoundEventAccessor(final SoundPoolEntry entry, final int weight) {
		this.entry = entry;
		this.weight = weight;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public SoundPoolEntry cloneEntry() {
		return new SoundPoolEntry(entry);
	}

	public Object cloneEntry1() {
		return cloneEntry();
	}
}
