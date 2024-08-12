package net.minecraft.network.play.server;

import net.minecraft.entity.DataWatcher;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.List;

public class S1CPacketEntityMetadata implements Packet {

public static final int EaZy = 1427;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149379_a;
	private List field_149378_b;
	// private static final String __OBFID = "http://https://fuckuskid00001326";

	public S1CPacketEntityMetadata() {}

	public S1CPacketEntityMetadata(final int p_i45217_1_, final DataWatcher p_i45217_2_, final boolean p_i45217_3_) {
		field_149379_a = p_i45217_1_;

		if (p_i45217_3_) {
			field_149378_b = p_i45217_2_.getAllWatched();
		} else {
			field_149378_b = p_i45217_2_.getChanged();
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149379_a = data.readVarIntFromBuffer();
		field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(data);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149379_a);
		DataWatcher.writeWatchedListToPacketBuffer(field_149378_b, data);
	}

	public void func_180748_a(final INetHandlerPlayClient p_180748_1_) {
		p_180748_1_.handleEntityMetadata(this);
	}

	public List func_149376_c() {
		return field_149378_b;
	}

	public int func_149375_d() {
		return field_149379_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180748_a((INetHandlerPlayClient) handler);
	}
}
