package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.io.IOException;

public class C02PacketUseEntity implements Packet {

	public static final int EaZy = 1379;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int entityId;
	private C02PacketUseEntity.Action action;
	private Vec3 field_179713_c;
	// private static final String __OBFID = "http://https://fuckuskid00001357";

	public C02PacketUseEntity() {}

	public C02PacketUseEntity(final Entity p_i45251_1_, final C02PacketUseEntity.Action p_i45251_2_) {
		entityId = p_i45251_1_.getEntityId();
		action = p_i45251_2_;
	}

	public C02PacketUseEntity(final Entity p_i45944_1_, final Vec3 p_i45944_2_) {
		this(p_i45944_1_, C02PacketUseEntity.Action.INTERACT_AT);
		field_179713_c = p_i45944_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		entityId = data.readVarIntFromBuffer();
		action = (C02PacketUseEntity.Action) data.readEnumValue(C02PacketUseEntity.Action.class);

		if (action == C02PacketUseEntity.Action.INTERACT_AT) {
			field_179713_c = new Vec3(data.readFloat(), data.readFloat(), data.readFloat());
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(entityId);
		data.writeEnumValue(action);

		if (action == C02PacketUseEntity.Action.INTERACT_AT) {
			data.writeFloat((float) field_179713_c.xCoord);
			data.writeFloat((float) field_179713_c.yCoord);
			data.writeFloat((float) field_179713_c.zCoord);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processUseEntity(this);
	}

	public Entity getEntityFromWorld(final World worldIn) {
		return worldIn.getEntityByID(entityId);
	}

	public C02PacketUseEntity.Action getAction() {
		return action;
	}

	public Vec3 func_179712_b() {
		return field_179713_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}

	public static enum Action {
		INTERACT("INTERACT", 0), ATTACK("ATTACK", 1), INTERACT_AT("INTERACT_AT", 2);

		private Action(final String p_i45943_1_, final int p_i45943_2_) {}
	}
}
