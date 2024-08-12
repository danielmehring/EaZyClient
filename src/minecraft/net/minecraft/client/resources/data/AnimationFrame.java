package net.minecraft.client.resources.data;

public class AnimationFrame {

public static final int EaZy = 855;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final int frameIndex;
	private final int frameTime;
	// private static final String __OBFID = "http://https://fuckuskid00001104";

	public AnimationFrame(final int p_i1307_1_) {
		this(p_i1307_1_, -1);
	}

	public AnimationFrame(final int p_i1308_1_, final int p_i1308_2_) {
		frameIndex = p_i1308_1_;
		frameTime = p_i1308_2_;
	}

	public boolean hasNoTime() {
		return frameTime == -1;
	}

	public int getFrameTime() {
		return frameTime;
	}

	public int getFrameIndex() {
		return frameIndex;
	}
}
