package net.minecraft.scoreboard;

import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class GoalColor implements IScoreObjectiveCriteria {

public static final int EaZy = 1519;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_178794_j;
	// private static final String __OBFID = "http://https://fuckuskid00001961";

	public GoalColor(final String p_i45549_1_, final EnumChatFormatting p_i45549_2_) {
		field_178794_j = p_i45549_1_ + p_i45549_2_.getFriendlyName();
		IScoreObjectiveCriteria.INSTANCES.put(field_178794_j, this);
	}

	@Override
	public String getName() {
		return field_178794_j;
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
