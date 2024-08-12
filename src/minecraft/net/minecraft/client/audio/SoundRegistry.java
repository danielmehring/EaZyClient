package net.minecraft.client.audio;

import net.minecraft.util.RegistrySimple;

import java.util.Map;

import com.google.common.collect.Maps;

public class SoundRegistry extends RegistrySimple {

public static final int EaZy = 449;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Map field_148764_a;
	// private static final String __OBFID = "http://https://fuckuskid00001151";

	/**
	 * Creates the Map we will use to map keys to their registered values.
	 */
	@Override
	protected Map createUnderlyingMap() {
		field_148764_a = Maps.newHashMap();
		return field_148764_a;
	}

	public void registerSound(final SoundEventAccessorComposite p_148762_1_) {
		putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
	}

	/**
	 * Reset the underlying sound map (Called on resource manager reload)
	 */
	public void clearMap() {
		field_148764_a.clear();
	}
}
