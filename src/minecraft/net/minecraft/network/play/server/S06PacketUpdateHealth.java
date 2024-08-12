package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S06PacketUpdateHealth implements Packet {

public static final int EaZy = 1408;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float health;
	private int foodLevel;
	private float saturationLevel;
	// private static final String __OBFID = "http://https://fuckuskid00001332";

	public S06PacketUpdateHealth() {}

	public S06PacketUpdateHealth(final float healthIn, final int foodLevelIn, final float saturationIn) {
		health = healthIn;
		foodLevel = foodLevelIn;
		saturationLevel = saturationIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		health = data.readFloat();
		foodLevel = data.readVarIntFromBuffer();
		saturationLevel = data.readFloat();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeFloat(health);
		data.writeVarIntToBuffer(foodLevel);
		data.writeFloat(saturationLevel);
	}

	public void func_180750_a(final INetHandlerPlayClient p_180750_1_) {
		p_180750_1_.handleUpdateHealth(this);
	}

	public float getHealth() {
		return health;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public float getSaturationLevel() {
		return saturationLevel;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180750_a((INetHandlerPlayClient) handler);
	}
}
