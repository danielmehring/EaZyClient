package net.minecraft.scoreboard;

public class ScoreObjective {

public static final int EaZy = 1526;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Scoreboard theScoreboard;
	private final String name;

	/** The ScoreObjectiveCriteria for this objetive */
	private final IScoreObjectiveCriteria objectiveCriteria;
	private IScoreObjectiveCriteria.EnumRenderType field_178768_d;
	private String displayName;
	// private static final String __OBFID = "http://https://fuckuskid00000614";

	public ScoreObjective(final Scoreboard p_i2307_1_, final String p_i2307_2_,
			final IScoreObjectiveCriteria p_i2307_3_) {
		theScoreboard = p_i2307_1_;
		name = p_i2307_2_;
		objectiveCriteria = p_i2307_3_;
		displayName = p_i2307_2_;
		field_178768_d = p_i2307_3_.func_178790_c();
	}

	public Scoreboard getScoreboard() {
		return theScoreboard;
	}

	public String getName() {
		return name;
	}

	public IScoreObjectiveCriteria getCriteria() {
		return objectiveCriteria;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(final String p_96681_1_) {
		displayName = p_96681_1_;
		theScoreboard.func_96532_b(this);
	}

	public IScoreObjectiveCriteria.EnumRenderType func_178766_e() {
		return field_178768_d;
	}

	public void func_178767_a(final IScoreObjectiveCriteria.EnumRenderType p_178767_1_) {
		field_178768_d = p_178767_1_;
		theScoreboard.func_96532_b(this);
	}
}
