package net.minecraft.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

import java.util.Iterator;

public class ChatComponentScore extends ChatComponentStyle {

public static final int EaZy = 1595;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_179999_b;
	private final String field_180000_c;
	private String field_179998_d = "";
	// private static final String __OBFID = "http://https://fuckuskid00002309";

	public ChatComponentScore(final String p_i45997_1_, final String p_i45997_2_) {
		field_179999_b = p_i45997_1_;
		field_180000_c = p_i45997_2_;
	}

	public String func_179995_g() {
		return field_179999_b;
	}

	public String func_179994_h() {
		return field_180000_c;
	}

	public void func_179997_b(final String p_179997_1_) {
		field_179998_d = p_179997_1_;
	}

	/**
	 * Gets the text of this component, without any special formatting codes
	 * added, for chat. TODO: why is this two different methods?
	 */
	@Override
	public String getUnformattedTextForChat() {
		final MinecraftServer var1 = MinecraftServer.getServer();

		if (var1 != null && var1.func_175578_N() && StringUtils.isNullOrEmpty(field_179998_d)) {
			final Scoreboard var2 = var1.worldServerForDimension(0).getScoreboard();
			final ScoreObjective var3 = var2.getObjective(field_180000_c);

			if (var2.func_178819_b(field_179999_b, var3)) {
				final Score var4 = var2.getValueFromObjective(field_179999_b, var3);
				func_179997_b(String.format("%d", new Object[] { var4.getScorePoints() }));
			} else {
				field_179998_d = "";
			}
		}

		return field_179998_d;
	}

	public ChatComponentScore func_179996_i() {
		final ChatComponentScore var1 = new ChatComponentScore(field_179999_b, field_180000_c);
		var1.func_179997_b(field_179998_d);
		var1.setChatStyle(getChatStyle().createShallowCopy());
		final Iterator var2 = getSiblings().iterator();

		while (var2.hasNext()) {
			final IChatComponent var3 = (IChatComponent) var2.next();
			var1.appendSibling(var3.createCopy());
		}

		return var1;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof ChatComponentScore)) {
			return false;
		} else {
			final ChatComponentScore var2 = (ChatComponentScore) p_equals_1_;
			return field_179999_b.equals(var2.field_179999_b) && field_180000_c.equals(var2.field_180000_c)
					&& super.equals(p_equals_1_);
		}
	}

	@Override
	public String toString() {
		return "ScoreComponent{name=\'" + field_179999_b + '\'' + "objective=\'" + field_180000_c + '\'' + ", siblings="
				+ siblings + ", style=" + getChatStyle() + '}';
	}

	/**
	 * Creates a copy of this component. Almost a deep copy, except the style is
	 * shallow-copied.
	 */
	@Override
	public IChatComponent createCopy() {
		return func_179996_i();
	}
}
