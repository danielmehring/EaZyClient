package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.IChatComponent;

public class StatCrafting extends StatBase {

public static final int EaZy = 1562;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Item field_150960_a;
	// private static final String __OBFID = "http://https://fuckuskid00001470";

	public StatCrafting(final String p_i45910_1_, final String p_i45910_2_, final IChatComponent p_i45910_3_,
			final Item p_i45910_4_) {
		super(p_i45910_1_ + p_i45910_2_, p_i45910_3_);
		field_150960_a = p_i45910_4_;
		final int var5 = Item.getIdFromItem(p_i45910_4_);

		if (var5 != 0) {
			IScoreObjectiveCriteria.INSTANCES.put(p_i45910_1_ + var5, func_150952_k());
		}
	}

	public Item func_150959_a() {
		return field_150960_a;
	}
}
