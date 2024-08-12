package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C0BPacketEntityAction implements Packet {

public static final int EaZy = 1385;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149517_a;
	private C0BPacketEntityAction.Action field_149515_b;
	private int field_149516_c;
	// private static final String __OBFID = "http://https://fuckuskid00001366";

	public C0BPacketEntityAction() {}

	public C0BPacketEntityAction(final Entity p_i45937_1_, final C0BPacketEntityAction.Action p_i45937_2_) {
		this(p_i45937_1_, p_i45937_2_, 0);
	}

	public C0BPacketEntityAction(final Entity p_i45938_1_, final C0BPacketEntityAction.Action p_i45938_2_,
			final int p_i45938_3_) {
		field_149517_a = p_i45938_1_.getEntityId();
		field_149515_b = p_i45938_2_;
		field_149516_c = p_i45938_3_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149517_a = data.readVarIntFromBuffer();
		field_149515_b = (C0BPacketEntityAction.Action) data.readEnumValue(C0BPacketEntityAction.Action.class);
		field_149516_c = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149517_a);
		data.writeEnumValue(field_149515_b);
		data.writeVarIntToBuffer(field_149516_c);
	}

	public void func_180765_a(final INetHandlerPlayServer p_180765_1_) {
		p_180765_1_.processEntityAction(this);
	}

	public C0BPacketEntityAction.Action func_180764_b() {
		return field_149515_b;
	}

	public int func_149512_e() {
		return field_149516_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180765_a((INetHandlerPlayServer) handler);
	}

	public static enum Action {
		START_SNEAKING("START_SNEAKING", 0), STOP_SNEAKING("STOP_SNEAKING", 1), STOP_SLEEPING("STOP_SLEEPING",
				2), START_SPRINTING("START_SPRINTING", 3), STOP_SPRINTING("STOP_SPRINTING",
						4), RIDING_JUMP("RIDING_JUMP", 5), OPEN_INVENTORY("OPEN_INVENTORY", 6);

		private Action(final String p_i45936_1_, final int p_i45936_2_) {}
	}
}
