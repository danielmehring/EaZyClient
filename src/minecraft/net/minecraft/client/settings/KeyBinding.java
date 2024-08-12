package net.minecraft.client.settings;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class KeyBinding implements Comparable {

public static final int EaZy = 903;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final List keybindArray = Lists.newArrayList();
	private static final IntHashMap hash = new IntHashMap();
	private static final Set keybindSet = Sets.newHashSet();
	private final String keyDescription;
	private final int keyCodeDefault;
	private final String keyCategory;
	private int keyCode;

	/** because _303 wanted me to call it that(Caironater) */
	public boolean pressed;
	private int pressTime;
	// private static final String __OBFID = "http://https://fuckuskid00000628";

	public static void onTick(final int keyCode) {
		if (keyCode != 0) {
			final KeyBinding var1 = (KeyBinding) hash.lookup(keyCode);

			if (var1 != null) {
				++var1.pressTime;
			}
		}
	}

	public static void setKeyBindState(final int keyCode, final boolean pressed) {
		if (keyCode != 0) {
			final KeyBinding var2 = (KeyBinding) hash.lookup(keyCode);

			if (var2 != null) {
				var2.pressed = pressed;
			}
		}
	}

	public static void unPressAllKeys() {
		final Iterator var0 = keybindArray.iterator();

		while (var0.hasNext()) {
			final KeyBinding var1 = (KeyBinding) var0.next();
			var1.unpressKey();
		}
	}

	public static void resetKeyBindingArrayAndHash() {
		hash.clearMap();
		final Iterator var0 = keybindArray.iterator();

		while (var0.hasNext()) {
			final KeyBinding var1 = (KeyBinding) var0.next();
			hash.addKey(var1.keyCode, var1);
		}
	}

	public static Set getKeybinds() {
		return keybindSet;
	}

	public KeyBinding(final String description, final int keyCode, final String category) {
		keyDescription = description;
		this.keyCode = keyCode;
		keyCodeDefault = keyCode;
		keyCategory = category;
		keybindArray.add(this);
		hash.addKey(keyCode, this);
		keybindSet.add(category);
	}

	public boolean getIsKeyPressed() {
		return pressed;
	}

	public String getKeyCategory() {
		return keyCategory;
	}

	public boolean isPressed() {
		if (pressTime == 0) {
			return false;
		} else {
			--pressTime;
			return true;
		}
	}

	private void unpressKey() {
		pressTime = 0;
		pressed = false;
	}

	public String getKeyDescription() {
		return keyDescription;
	}

	public int getKeyCodeDefault() {
		return keyCodeDefault;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(final int keyCode) {
		this.keyCode = keyCode;
	}

	public int compareTo(final KeyBinding p_compareTo_1_) {
		int var2 = I18n.format(keyCategory, new Object[0])
				.compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));

		if (var2 == 0) {
			var2 = I18n.format(keyDescription, new Object[0])
					.compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
		}

		return var2;
	}

	@Override
	public int compareTo(final Object p_compareTo_1_) {
		return this.compareTo((KeyBinding) p_compareTo_1_);
	}
}
