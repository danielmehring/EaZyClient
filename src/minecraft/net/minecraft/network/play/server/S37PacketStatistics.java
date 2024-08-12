package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class S37PacketStatistics implements Packet {

public static final int EaZy = 1454;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private Map field_148976_a;
	// private static final String __OBFID = "http://https://fuckuskid00001283";

	public S37PacketStatistics() {}

	public S37PacketStatistics(final Map p_i45173_1_) {
		field_148976_a = p_i45173_1_;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleStatistics(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		final int var2 = data.readVarIntFromBuffer();
		field_148976_a = Maps.newHashMap();

		for (int var3 = 0; var3 < var2; ++var3) {
			final StatBase var4 = StatList.getOneShotStat(data.readStringFromBuffer(32767));
			final int var5 = data.readVarIntFromBuffer();

			if (var4 != null) {
				field_148976_a.put(var4, var5);
			}
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_148976_a.size());
		final Iterator var2 = field_148976_a.entrySet().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();
			data.writeString(((StatBase) var3.getKey()).statId);
			data.writeVarIntToBuffer(((Integer) var3.getValue()));
		}
	}

	public Map func_148974_c() {
		return field_148976_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
