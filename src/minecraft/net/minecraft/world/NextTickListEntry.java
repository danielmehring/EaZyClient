package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable {

public static final int EaZy = 1833;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The id number for the next tick entry */
	private static long nextTickEntryID;
	private final Block field_151352_g;
	public final BlockPos field_180282_a;

	/** Time this tick is scheduled to occur at */
	public long scheduledTime;
	public int priority;

	/** The id of the tick entry */
	private final long tickEntryID;
	// private static final String __OBFID = "http://https://fuckuskid00000156";

	public NextTickListEntry(final BlockPos p_i45745_1_, final Block p_i45745_2_) {
		tickEntryID = nextTickEntryID++;
		field_180282_a = p_i45745_1_;
		field_151352_g = p_i45745_2_;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof NextTickListEntry)) {
			return false;
		} else {
			final NextTickListEntry var2 = (NextTickListEntry) p_equals_1_;
			return field_180282_a.equals(var2.field_180282_a) && Block.isEqualTo(field_151352_g, var2.field_151352_g);
		}
	}

	@Override
	public int hashCode() {
		return field_180282_a.hashCode();
	}

	/**
	 * Sets the scheduled time for this tick entry
	 */
	public NextTickListEntry setScheduledTime(final long p_77176_1_) {
		scheduledTime = p_77176_1_;
		return this;
	}

	public void setPriority(final int p_82753_1_) {
		priority = p_82753_1_;
	}

	public int compareTo(final NextTickListEntry p_compareTo_1_) {
		return scheduledTime < p_compareTo_1_.scheduledTime ? -1
				: scheduledTime > p_compareTo_1_.scheduledTime ? 1
						: priority != p_compareTo_1_.priority ? priority - p_compareTo_1_.priority
								: tickEntryID < p_compareTo_1_.tickEntryID ? -1
										: tickEntryID > p_compareTo_1_.tickEntryID ? 1 : 0;
	}

	@Override
	public String toString() {
		return Block.getIdFromBlock(field_151352_g) + ": " + field_180282_a + ", " + scheduledTime + ", " + priority
				+ ", " + tickEntryID;
	}

	public Block func_151351_a() {
		return field_151352_g;
	}

	@Override
	public int compareTo(final Object p_compareTo_1_) {
		return this.compareTo((NextTickListEntry) p_compareTo_1_);
	}
}
