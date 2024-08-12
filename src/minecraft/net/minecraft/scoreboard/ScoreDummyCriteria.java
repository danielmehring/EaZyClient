package net.minecraft.scoreboard;

import java.util.List;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {

public static final int EaZy = 1524;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_96644_g;
	// private static final String __OBFID = "http://https://fuckuskid00000622";

	public ScoreDummyCriteria(final String p_i2311_1_) {
		field_96644_g = p_i2311_1_;
		IScoreObjectiveCriteria.INSTANCES.put(p_i2311_1_, this);
	}

	@Override
	public String getName() {
		return field_96644_g;
	}

	@Override
	public int func_96635_a(final List p_96635_1_) {
		return 0;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public IScoreObjectiveCriteria.EnumRenderType func_178790_c() {
		return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
	}
}
