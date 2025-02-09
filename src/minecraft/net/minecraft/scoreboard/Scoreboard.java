package net.minecraft.scoreboard;

import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Scoreboard {

public static final int EaZy = 1522;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Map of objective names to ScoreObjective objects. */
	private final Map scoreObjectives = Maps.newHashMap();
	private final Map scoreObjectiveCriterias = Maps.newHashMap();
	private final Map field_96544_c = Maps.newHashMap();

	/** Index 0 is tab menu, 1 is sidebar, and 2 is below name */
	private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];

	/** Map of teamnames to ScorePlayerTeam instances */
	private final Map teams = Maps.newHashMap();

	/** Map of usernames to ScorePlayerTeam objects. */
	private final Map teamMemberships = Maps.newHashMap();
	private static String[] field_178823_g = null;
	// private static final String __OBFID = "http://https://fuckuskid00000619";

	/**
	 * Returns a ScoreObjective for the objective name
	 */
	public ScoreObjective getObjective(final String p_96518_1_) {
		return (ScoreObjective) scoreObjectives.get(p_96518_1_);
	}

	public ScoreObjective addScoreObjective(final String p_96535_1_, final IScoreObjectiveCriteria p_96535_2_) {
		ScoreObjective var3 = getObjective(p_96535_1_);

		if (var3 != null) {
			throw new IllegalArgumentException("An objective with the name \'" + p_96535_1_ + "\' already exists!");
		} else {
			var3 = new ScoreObjective(this, p_96535_1_, p_96535_2_);
			Object var4 = scoreObjectiveCriterias.get(p_96535_2_);

			if (var4 == null) {
				var4 = Lists.newArrayList();
				scoreObjectiveCriterias.put(p_96535_2_, var4);
			}

			((List) var4).add(var3);
			scoreObjectives.put(p_96535_1_, var3);
			func_96522_a(var3);
			return var3;
		}
	}

	public Collection func_96520_a(final IScoreObjectiveCriteria p_96520_1_) {
		final Collection var2 = (Collection) scoreObjectiveCriterias.get(p_96520_1_);
		return var2 == null ? Lists.newArrayList() : Lists.newArrayList(var2);
	}

	public boolean func_178819_b(final String p_178819_1_, final ScoreObjective p_178819_2_) {
		final Map var3 = (Map) field_96544_c.get(p_178819_1_);

		if (var3 == null) {
			return false;
		} else {
			final Score var4 = (Score) var3.get(p_178819_2_);
			return var4 != null;
		}
	}

	public Score getValueFromObjective(final String p_96529_1_, final ScoreObjective p_96529_2_) {
		Object var3 = field_96544_c.get(p_96529_1_);

		if (var3 == null) {
			var3 = Maps.newHashMap();
			field_96544_c.put(p_96529_1_, var3);
		}

		Score var4 = (Score) ((Map) var3).get(p_96529_2_);

		if (var4 == null) {
			var4 = new Score(this, p_96529_2_, p_96529_1_);
			((Map) var3).put(p_96529_2_, var4);
		}

		return var4;
	}

	/**
	 * Returns an array of Score objects, sorting by Score.getScorePoints()
	 */
	public Collection getSortedScores(final ScoreObjective p_96534_1_) {
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = field_96544_c.values().iterator();

		while (var3.hasNext()) {
			final Map var4 = (Map) var3.next();
			final Score var5 = (Score) var4.get(p_96534_1_);

			if (var5 != null) {
				var2.add(var5);
			}
		}

		Collections.sort(var2, Score.scoreComparator);
		return var2;
	}

	public Collection getScoreObjectives() {
		return scoreObjectives.values();
	}

	public Collection getObjectiveNames() {
		return field_96544_c.keySet();
	}

	public void func_178822_d(final String p_178822_1_, final ScoreObjective p_178822_2_) {
		Map var3;

		if (p_178822_2_ == null) {
			var3 = (Map) field_96544_c.remove(p_178822_1_);

			if (var3 != null) {
				func_96516_a(p_178822_1_);
			}
		} else {
			var3 = (Map) field_96544_c.get(p_178822_1_);

			if (var3 != null) {
				final Score var4 = (Score) var3.remove(p_178822_2_);

				if (var3.size() < 1) {
					final Map var5 = (Map) field_96544_c.remove(p_178822_1_);

					if (var5 != null) {
						func_96516_a(p_178822_1_);
					}
				} else if (var4 != null) {
					func_178820_a(p_178822_1_, p_178822_2_);
				}
			}
		}
	}

	public Collection func_96528_e() {
		final Collection var1 = field_96544_c.values();
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = var1.iterator();

		while (var3.hasNext()) {
			final Map var4 = (Map) var3.next();
			var2.addAll(var4.values());
		}

		return var2;
	}

	public Map func_96510_d(final String p_96510_1_) {
		Object var2 = field_96544_c.get(p_96510_1_);

		if (var2 == null) {
			var2 = Maps.newHashMap();
		}

		return (Map) var2;
	}

	public void func_96519_k(final ScoreObjective p_96519_1_) {
		scoreObjectives.remove(p_96519_1_.getName());

		for (int var2 = 0; var2 < 19; ++var2) {
			if (getObjectiveInDisplaySlot(var2) == p_96519_1_) {
				setObjectiveInDisplaySlot(var2, (ScoreObjective) null);
			}
		}

		final List var5 = (List) scoreObjectiveCriterias.get(p_96519_1_.getCriteria());

		if (var5 != null) {
			var5.remove(p_96519_1_);
		}

		final Iterator var3 = field_96544_c.values().iterator();

		while (var3.hasNext()) {
			final Map var4 = (Map) var3.next();
			var4.remove(p_96519_1_);
		}

		func_96533_c(p_96519_1_);
	}

	/**
	 * 0 is tab menu, 1 is sidebar, 2 is below name
	 */
	public void setObjectiveInDisplaySlot(final int p_96530_1_, final ScoreObjective p_96530_2_) {
		objectiveDisplaySlots[p_96530_1_] = p_96530_2_;
	}

	/**
	 * 0 is tab menu, 1 is sidebar, 2 is below name
	 */
	public ScoreObjective getObjectiveInDisplaySlot(final int p_96539_1_) {
		return objectiveDisplaySlots[p_96539_1_];
	}

	/**
	 * Retrieve the ScorePlayerTeam instance identified by the passed team name
	 */
	public ScorePlayerTeam getTeam(final String p_96508_1_) {
		return (ScorePlayerTeam) teams.get(p_96508_1_);
	}

	public ScorePlayerTeam createTeam(final String p_96527_1_) {
		ScorePlayerTeam var2 = getTeam(p_96527_1_);

		if (var2 != null) {
			throw new IllegalArgumentException("A team with the name \'" + p_96527_1_ + "\' already exists!");
		} else {
			var2 = new ScorePlayerTeam(this, p_96527_1_);
			teams.put(p_96527_1_, var2);
			broadcastTeamCreated(var2);
			return var2;
		}
	}

	/**
	 * Removes the team from the scoreboard, updates all player memberships and
	 * broadcasts the deletion to all players
	 */
	public void removeTeam(final ScorePlayerTeam p_96511_1_) {
		teams.remove(p_96511_1_.getRegisteredName());
		final Iterator var2 = p_96511_1_.getMembershipCollection().iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();
			teamMemberships.remove(var3);
		}

		func_96513_c(p_96511_1_);
	}

	public boolean func_151392_a(final String p_151392_1_, final String p_151392_2_) {
		if (!teams.containsKey(p_151392_2_)) {
			return false;
		} else {
			final ScorePlayerTeam var3 = getTeam(p_151392_2_);

			if (getPlayersTeam(p_151392_1_) != null) {
				removePlayerFromTeams(p_151392_1_);
			}

			teamMemberships.put(p_151392_1_, var3);
			var3.getMembershipCollection().add(p_151392_1_);
			return true;
		}
	}

	public boolean removePlayerFromTeams(final String p_96524_1_) {
		final ScorePlayerTeam var2 = getPlayersTeam(p_96524_1_);

		if (var2 != null) {
			removePlayerFromTeam(p_96524_1_, var2);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes the given username from the given ScorePlayerTeam. If the player
	 * is not on the team then an IllegalStateException is thrown.
	 */
	public void removePlayerFromTeam(final String p_96512_1_, final ScorePlayerTeam p_96512_2_) {
		if (getPlayersTeam(p_96512_1_) != p_96512_2_) {
			throw new IllegalStateException(
					"Player is either on another team or not on any team. Cannot remove from team \'"
							+ p_96512_2_.getRegisteredName() + "\'.");
		} else {
			teamMemberships.remove(p_96512_1_);
			p_96512_2_.getMembershipCollection().remove(p_96512_1_);
		}
	}

	/**
	 * Retrieve all registered ScorePlayerTeam names
	 */
	public Collection getTeamNames() {
		return teams.keySet();
	}

	/**
	 * Retrieve all registered ScorePlayerTeam instances
	 */
	public Collection getTeams() {
		return teams.values();
	}

	/**
	 * Gets the ScorePlayerTeam object for the given username.
	 */
	public ScorePlayerTeam getPlayersTeam(final String p_96509_1_) {
		return (ScorePlayerTeam) teamMemberships.get(p_96509_1_);
	}

	public void func_96522_a(final ScoreObjective p_96522_1_) {}

	public void func_96532_b(final ScoreObjective p_96532_1_) {}

	public void func_96533_c(final ScoreObjective p_96533_1_) {}

	public void func_96536_a(final Score p_96536_1_) {}

	public void func_96516_a(final String p_96516_1_) {}

	public void func_178820_a(final String p_178820_1_, final ScoreObjective p_178820_2_) {}

	/**
	 * This packet will notify the players that this team is created, and that
	 * will register it on the client
	 */
	public void broadcastTeamCreated(final ScorePlayerTeam p_96523_1_) {}

	/**
	 * This packet will notify the players that this team is removed
	 */
	public void broadcastTeamRemoved(final ScorePlayerTeam p_96538_1_) {}

	public void func_96513_c(final ScorePlayerTeam p_96513_1_) {}

	/**
	 * Returns 'list' for 0, 'sidebar' for 1, 'belowName for 2, otherwise null.
	 */
	public static String getObjectiveDisplaySlot(final int p_96517_0_) {
		switch (p_96517_0_) {
			case 0:
				return "list";

			case 1:
				return "sidebar";

			case 2:
				return "belowName";

			default:
				if (p_96517_0_ >= 3 && p_96517_0_ <= 18) {
					final EnumChatFormatting var1 = EnumChatFormatting.func_175744_a(p_96517_0_ - 3);

					if (var1 != null && var1 != EnumChatFormatting.RESET) {
						return "sidebar.team." + var1.getFriendlyName();
					}
				}

				return null;
		}
	}

	/**
	 * Returns 0 for (case-insensitive) 'list', 1 for 'sidebar', 2 for
	 * 'belowName', otherwise -1.
	 */
	public static int getObjectiveDisplaySlotNumber(final String p_96537_0_) {
		if (p_96537_0_.equalsIgnoreCase("list")) {
			return 0;
		} else if (p_96537_0_.equalsIgnoreCase("sidebar")) {
			return 1;
		} else if (p_96537_0_.equalsIgnoreCase("belowName")) {
			return 2;
		} else {
			if (p_96537_0_.startsWith("sidebar.team.")) {
				final String var1 = p_96537_0_.substring("sidebar.team.".length());
				final EnumChatFormatting var2 = EnumChatFormatting.getValueByName(var1);

				if (var2 != null && var2.func_175746_b() >= 0) {
					return var2.func_175746_b() + 3;
				}
			}

			return -1;
		}
	}

	public static String[] func_178821_h() {
		if (field_178823_g == null) {
			field_178823_g = new String[19];

			for (int var0 = 0; var0 < 19; ++var0) {
				field_178823_g[var0] = getObjectiveDisplaySlot(var0);
			}
		}

		return field_178823_g;
	}
}
