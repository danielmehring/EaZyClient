package net.minecraft.scoreboard;

import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public interface IScoreObjectiveCriteria {
	Map INSTANCES = Maps.newHashMap();
	IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
	IScoreObjectiveCriteria field_178791_c = new ScoreDummyCriteria("trigger");
	IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
	IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
	IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
	IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
	IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[] {
			new GoalColor("teamkill.", EnumChatFormatting.BLACK),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_BLUE),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_GREEN),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_AQUA),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_RED),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_PURPLE),
			new GoalColor("teamkill.", EnumChatFormatting.GOLD), new GoalColor("teamkill.", EnumChatFormatting.GRAY),
			new GoalColor("teamkill.", EnumChatFormatting.DARK_GRAY),
			new GoalColor("teamkill.", EnumChatFormatting.BLUE), new GoalColor("teamkill.", EnumChatFormatting.GREEN),
			new GoalColor("teamkill.", EnumChatFormatting.AQUA), new GoalColor("teamkill.", EnumChatFormatting.RED),
			new GoalColor("teamkill.", EnumChatFormatting.LIGHT_PURPLE),
			new GoalColor("teamkill.", EnumChatFormatting.YELLOW),
			new GoalColor("teamkill.", EnumChatFormatting.WHITE) };
	IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[] {
			new GoalColor("killedByTeam.", EnumChatFormatting.BLACK),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_BLUE),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GREEN),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_AQUA),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_RED),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_PURPLE),
			new GoalColor("killedByTeam.", EnumChatFormatting.GOLD),
			new GoalColor("killedByTeam.", EnumChatFormatting.GRAY),
			new GoalColor("killedByTeam.", EnumChatFormatting.DARK_GRAY),
			new GoalColor("killedByTeam.", EnumChatFormatting.BLUE),
			new GoalColor("killedByTeam.", EnumChatFormatting.GREEN),
			new GoalColor("killedByTeam.", EnumChatFormatting.AQUA),
			new GoalColor("killedByTeam.", EnumChatFormatting.RED),
			new GoalColor("killedByTeam.", EnumChatFormatting.LIGHT_PURPLE),
			new GoalColor("killedByTeam.", EnumChatFormatting.YELLOW),
			new GoalColor("killedByTeam.", EnumChatFormatting.WHITE) };

	String getName();

	int func_96635_a(List var1);

	boolean isReadOnly();

	IScoreObjectiveCriteria.EnumRenderType func_178790_c();

	public static enum EnumRenderType {
		INTEGER("INTEGER", 0, "integer"), HEARTS("HEARTS", 1, "hearts");
		private static final Map field_178801_c = Maps.newHashMap();
		private final String field_178798_d;

		private EnumRenderType(final String p_i45548_1_, final int p_i45548_2_, final String p_i45548_3_) {
			field_178798_d = p_i45548_3_;
		}

		public String func_178796_a() {
			return field_178798_d;
		}

		public static IScoreObjectiveCriteria.EnumRenderType func_178795_a(final String p_178795_0_) {
			final IScoreObjectiveCriteria.EnumRenderType var1 = (IScoreObjectiveCriteria.EnumRenderType) field_178801_c
					.get(p_178795_0_);
			return var1 == null ? INTEGER : var1;
		}

		static {
			final IScoreObjectiveCriteria.EnumRenderType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final IScoreObjectiveCriteria.EnumRenderType var3 = var0[var2];
				field_178801_c.put(var3.func_178796_a(), var3);
			}
		}
	}
}
