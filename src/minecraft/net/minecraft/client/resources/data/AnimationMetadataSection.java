package net.minecraft.client.resources.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class AnimationMetadataSection implements IMetadataSection {

public static final int EaZy = 856;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List animationFrames;
	private final int frameWidth;
	private final int frameHeight;
	private final int frameTime;
	private final boolean field_177220_e;
	// private static final String __OBFID = "http://https://fuckuskid00001106";

	public AnimationMetadataSection(final List p_i46088_1_, final int p_i46088_2_, final int p_i46088_3_,
			final int p_i46088_4_, final boolean p_i46088_5_) {
		animationFrames = p_i46088_1_;
		frameWidth = p_i46088_2_;
		frameHeight = p_i46088_3_;
		frameTime = p_i46088_4_;
		field_177220_e = p_i46088_5_;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameCount() {
		return animationFrames.size();
	}

	public int getFrameTime() {
		return frameTime;
	}

	public boolean func_177219_e() {
		return field_177220_e;
	}

	private AnimationFrame getAnimationFrame(final int p_130072_1_) {
		return (AnimationFrame) animationFrames.get(p_130072_1_);
	}

	public int getFrameTimeSingle(final int p_110472_1_) {
		final AnimationFrame var2 = getAnimationFrame(p_110472_1_);
		return var2.hasNoTime() ? frameTime : var2.getFrameTime();
	}

	public boolean frameHasTime(final int p_110470_1_) {
		return !((AnimationFrame) animationFrames.get(p_110470_1_)).hasNoTime();
	}

	public int getFrameIndex(final int p_110468_1_) {
		return ((AnimationFrame) animationFrames.get(p_110468_1_)).getFrameIndex();
	}

	public Set getFrameIndexSet() {
		final HashSet var1 = Sets.newHashSet();
		final Iterator var2 = animationFrames.iterator();

		while (var2.hasNext()) {
			final AnimationFrame var3 = (AnimationFrame) var2.next();
			var1.add(var3.getFrameIndex());
		}

		return var1;
	}
}
