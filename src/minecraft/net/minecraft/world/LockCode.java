package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public class LockCode {

public static final int EaZy = 1831;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final LockCode EMPTY_CODE = new LockCode("");
	private final String lock;
	// private static final String __OBFID = "http://https://fuckuskid00002260";

	public LockCode(final String p_i45903_1_) {
		lock = p_i45903_1_;
	}

	public boolean isEmpty() {
		return lock == null || lock.isEmpty();
	}

	public String getLock() {
		return lock;
	}

	public void toNBT(final NBTTagCompound nbt) {
		nbt.setString("Lock", lock);
	}

	public static LockCode fromNBT(final NBTTagCompound nbt) {
		if (nbt.hasKey("Lock", 8)) {
			final String var1 = nbt.getString("Lock");
			return new LockCode(var1);
		} else {
			return EMPTY_CODE;
		}
	}
}
