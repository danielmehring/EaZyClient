package net.minecraft.scoreboard;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ServerScoreboard extends Scoreboard {

public static final int EaZy = 1528;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final MinecraftServer scoreboardMCServer;
	private final Set field_96553_b = Sets.newHashSet();
	private ScoreboardSaveData field_96554_c;
	// private static final String __OBFID = "http://https://fuckuskid00001424";

	public ServerScoreboard(final MinecraftServer p_i1501_1_) {
		scoreboardMCServer = p_i1501_1_;
	}

	@Override
	public void func_96536_a(final Score p_96536_1_) {
		super.func_96536_a(p_96536_1_);

		if (field_96553_b.contains(p_96536_1_.getObjective())) {
			scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(p_96536_1_));
		}

		func_96551_b();
	}

	@Override
	public void func_96516_a(final String p_96516_1_) {
		super.func_96516_a(p_96516_1_);
		scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(p_96516_1_));
		func_96551_b();
	}

	@Override
	public void func_178820_a(final String p_178820_1_, final ScoreObjective p_178820_2_) {
		super.func_178820_a(p_178820_1_, p_178820_2_);
		scoreboardMCServer.getConfigurationManager()
				.sendPacketToAllPlayers(new S3CPacketUpdateScore(p_178820_1_, p_178820_2_));
		func_96551_b();
	}

	/**
	 * 0 is tab menu, 1 is sidebar, 2 is below name
	 */
	@Override
	public void setObjectiveInDisplaySlot(final int p_96530_1_, final ScoreObjective p_96530_2_) {
		final ScoreObjective var3 = getObjectiveInDisplaySlot(p_96530_1_);
		super.setObjectiveInDisplaySlot(p_96530_1_, p_96530_2_);

		if (var3 != p_96530_2_ && var3 != null) {
			if (func_96552_h(var3) > 0) {
				scoreboardMCServer.getConfigurationManager()
						.sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
			} else {
				func_96546_g(var3);
			}
		}

		if (p_96530_2_ != null) {
			if (field_96553_b.contains(p_96530_2_)) {
				scoreboardMCServer.getConfigurationManager()
						.sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
			} else {
				func_96549_e(p_96530_2_);
			}
		}

		func_96551_b();
	}

	@Override
	public boolean func_151392_a(final String p_151392_1_, final String p_151392_2_) {
		if (super.func_151392_a(p_151392_1_, p_151392_2_)) {
			final ScorePlayerTeam var3 = getTeam(p_151392_2_);
			scoreboardMCServer.getConfigurationManager()
					.sendPacketToAllPlayers(new S3EPacketTeams(var3, Arrays.asList(new String[] { p_151392_1_ }), 3));
			func_96551_b();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes the given username from the given ScorePlayerTeam. If the player
	 * is not on the team then an IllegalStateException is thrown.
	 */
	@Override
	public void removePlayerFromTeam(final String p_96512_1_, final ScorePlayerTeam p_96512_2_) {
		super.removePlayerFromTeam(p_96512_1_, p_96512_2_);
		scoreboardMCServer.getConfigurationManager()
				.sendPacketToAllPlayers(new S3EPacketTeams(p_96512_2_, Arrays.asList(new String[] { p_96512_1_ }), 4));
		func_96551_b();
	}

	@Override
	public void func_96522_a(final ScoreObjective p_96522_1_) {
		super.func_96522_a(p_96522_1_);
		func_96551_b();
	}

	@Override
	public void func_96532_b(final ScoreObjective p_96532_1_) {
		super.func_96532_b(p_96532_1_);

		if (field_96553_b.contains(p_96532_1_)) {
			scoreboardMCServer.getConfigurationManager()
					.sendPacketToAllPlayers(new S3BPacketScoreboardObjective(p_96532_1_, 2));
		}

		func_96551_b();
	}

	@Override
	public void func_96533_c(final ScoreObjective p_96533_1_) {
		super.func_96533_c(p_96533_1_);

		if (field_96553_b.contains(p_96533_1_)) {
			func_96546_g(p_96533_1_);
		}

		func_96551_b();
	}

	/**
	 * This packet will notify the players that this team is created, and that
	 * will register it on the client
	 */
	@Override
	public void broadcastTeamCreated(final ScorePlayerTeam p_96523_1_) {
		super.broadcastTeamCreated(p_96523_1_);
		scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(p_96523_1_, 0));
		func_96551_b();
	}

	/**
	 * This packet will notify the players that this team is removed
	 */
	@Override
	public void broadcastTeamRemoved(final ScorePlayerTeam p_96538_1_) {
		super.broadcastTeamRemoved(p_96538_1_);
		scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(p_96538_1_, 2));
		func_96551_b();
	}

	@Override
	public void func_96513_c(final ScorePlayerTeam p_96513_1_) {
		super.func_96513_c(p_96513_1_);
		scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(p_96513_1_, 1));
		func_96551_b();
	}

	public void func_96547_a(final ScoreboardSaveData p_96547_1_) {
		field_96554_c = p_96547_1_;
	}

	protected void func_96551_b() {
		if (field_96554_c != null) {
			field_96554_c.markDirty();
		}
	}

	public List func_96550_d(final ScoreObjective p_96550_1_) {
		final ArrayList var2 = Lists.newArrayList();
		var2.add(new S3BPacketScoreboardObjective(p_96550_1_, 0));

		for (int var3 = 0; var3 < 19; ++var3) {
			if (getObjectiveInDisplaySlot(var3) == p_96550_1_) {
				var2.add(new S3DPacketDisplayScoreboard(var3, p_96550_1_));
			}
		}

		final Iterator var5 = getSortedScores(p_96550_1_).iterator();

		while (var5.hasNext()) {
			final Score var4 = (Score) var5.next();
			var2.add(new S3CPacketUpdateScore(var4));
		}

		return var2;
	}

	public void func_96549_e(final ScoreObjective p_96549_1_) {
		final List var2 = func_96550_d(p_96549_1_);
		final Iterator var3 = scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();

		while (var3.hasNext()) {
			final EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			final Iterator var5 = var2.iterator();

			while (var5.hasNext()) {
				final Packet var6 = (Packet) var5.next();
				var4.playerNetServerHandler.sendPacket(var6);
			}
		}

		field_96553_b.add(p_96549_1_);
	}

	public List func_96548_f(final ScoreObjective p_96548_1_) {
		final ArrayList var2 = Lists.newArrayList();
		var2.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));

		for (int var3 = 0; var3 < 19; ++var3) {
			if (getObjectiveInDisplaySlot(var3) == p_96548_1_) {
				var2.add(new S3DPacketDisplayScoreboard(var3, p_96548_1_));
			}
		}

		return var2;
	}

	public void func_96546_g(final ScoreObjective p_96546_1_) {
		final List var2 = func_96548_f(p_96546_1_);
		final Iterator var3 = scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();

		while (var3.hasNext()) {
			final EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			final Iterator var5 = var2.iterator();

			while (var5.hasNext()) {
				final Packet var6 = (Packet) var5.next();
				var4.playerNetServerHandler.sendPacket(var6);
			}
		}

		field_96553_b.remove(p_96546_1_);
	}

	public int func_96552_h(final ScoreObjective p_96552_1_) {
		int var2 = 0;

		for (int var3 = 0; var3 < 19; ++var3) {
			if (getObjectiveInDisplaySlot(var3) == p_96552_1_) {
				++var2;
			}
		}

		return var2;
	}
}
