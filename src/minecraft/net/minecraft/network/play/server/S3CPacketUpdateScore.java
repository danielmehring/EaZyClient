package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3CPacketUpdateScore implements Packet {

public static final int EaZy = 1459;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String name = "";
	private String objective = "";
	private int value;
	private S3CPacketUpdateScore.Action action;
	// private static final String __OBFID = "http://https://fuckuskid00001335";

	public S3CPacketUpdateScore() {}

	public S3CPacketUpdateScore(final Score scoreIn) {
		name = scoreIn.getPlayerName();
		objective = scoreIn.getObjective().getName();
		value = scoreIn.getScorePoints();
		action = S3CPacketUpdateScore.Action.CHANGE;
	}

	public S3CPacketUpdateScore(final String nameIn) {
		name = nameIn;
		objective = "";
		value = 0;
		action = S3CPacketUpdateScore.Action.REMOVE;
	}

	public S3CPacketUpdateScore(final String nameIn, final ScoreObjective objectiveIn) {
		name = nameIn;
		objective = objectiveIn.getName();
		value = 0;
		action = S3CPacketUpdateScore.Action.REMOVE;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		name = data.readStringFromBuffer(40);
		action = (S3CPacketUpdateScore.Action) data.readEnumValue(S3CPacketUpdateScore.Action.class);
		objective = data.readStringFromBuffer(16);

		if (action != S3CPacketUpdateScore.Action.REMOVE) {
			value = data.readVarIntFromBuffer();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(name);
		data.writeEnumValue(action);
		data.writeString(objective);

		if (action != S3CPacketUpdateScore.Action.REMOVE) {
			data.writeVarIntToBuffer(value);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleUpdateScore(this);
	}

	public String func_149324_c() {
		return name;
	}

	public String func_149321_d() {
		return objective;
	}

	public int func_149323_e() {
		return value;
	}

	public S3CPacketUpdateScore.Action func_180751_d() {
		return action;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}

	public static enum Action {
		CHANGE("CHANGE", 0), REMOVE("REMOVE", 1);

		private Action(final String p_i45957_1_, final int p_i45957_2_) {}
	}
}
