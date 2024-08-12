package net.minecraft.block;

import net.minecraft.util.BlockPos;

public class BlockEventData {

public static final int EaZy = 299;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockPos field_180329_a;
	private final Block field_151344_d;

	/** Different for each blockID */
	private final int eventID;
	private final int eventParameter;
	// private static final String __OBFID = "http://https://fuckuskid00000131";

	public BlockEventData(final BlockPos p_i45756_1_, final Block p_i45756_2_, final int p_i45756_3_,
			final int p_i45756_4_) {
		field_180329_a = p_i45756_1_;
		eventID = p_i45756_3_;
		eventParameter = p_i45756_4_;
		field_151344_d = p_i45756_2_;
	}

	public BlockPos func_180328_a() {
		return field_180329_a;
	}

	/**
	 * Get the Event ID (different for each BlockID)
	 */
	public int getEventID() {
		return eventID;
	}

	public int getEventParameter() {
		return eventParameter;
	}

	public Block getBlock() {
		return field_151344_d;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof BlockEventData)) {
			return false;
		} else {
			final BlockEventData var2 = (BlockEventData) p_equals_1_;
			return field_180329_a.equals(var2.field_180329_a) && eventID == var2.eventID
					&& eventParameter == var2.eventParameter && field_151344_d == var2.field_151344_d;
		}
	}

	@Override
	public String toString() {
		return "TE(" + field_180329_a + ")," + eventID + "," + eventParameter + "," + field_151344_d;
	}
}
