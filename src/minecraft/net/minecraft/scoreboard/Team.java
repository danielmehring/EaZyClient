package net.minecraft.scoreboard;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class Team {

public static final int EaZy = 1529;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000621";

	/**
	 * Same as ==
	 */
	public boolean isSameTeam(final Team other) {
		return other == null ? false : this == other;
	}

	/**
	 * Retrieve the name by which this team is registered in the scoreboard
	 */
	public abstract String getRegisteredName();

	public abstract String formatString(String var1);

	public abstract boolean func_98297_h();

	public abstract boolean getAllowFriendlyFire();

	public abstract Team.EnumVisible func_178770_i();

	public abstract Collection getMembershipCollection();

	public abstract Team.EnumVisible func_178771_j();

	public static enum EnumVisible {
		ALWAYS("ALWAYS", 0, "always", 0), NEVER("NEVER", 1, "never", 1), HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2,
				"hideForOtherTeams", 2), HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);
		private static Map field_178828_g = Maps.newHashMap();
		public final String field_178830_e;
		public final int field_178827_f;

		public static String[] func_178825_a() {
			return (String[]) field_178828_g.keySet().toArray(new String[field_178828_g.size()]);
		}

		public static Team.EnumVisible func_178824_a(final String p_178824_0_) {
			return (Team.EnumVisible) field_178828_g.get(p_178824_0_);
		}

		private EnumVisible(final String p_i45550_1_, final int p_i45550_2_, final String p_i45550_3_,
				final int p_i45550_4_) {
			field_178830_e = p_i45550_3_;
			field_178827_f = p_i45550_4_;
		}

		static {
			final Team.EnumVisible[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final Team.EnumVisible var3 = var0[var2];
				field_178828_g.put(var3.field_178830_e, var3);
			}
		}
	}
}
