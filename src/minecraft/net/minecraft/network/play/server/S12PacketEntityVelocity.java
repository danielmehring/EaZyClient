package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S12PacketEntityVelocity implements Packet {

public static final int EaZy = 1420;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int entityID;
	public int x;
	public int y;
	public int z;
	// private static final String __OBFID = "http://https://fuckuskid00001328";

	public S12PacketEntityVelocity() {}

	public S12PacketEntityVelocity(final Entity pentity) {
		this(pentity.getEntityId(), pentity.motionX, pentity.motionY, pentity.motionZ);
	}

	public S12PacketEntityVelocity(final int pID, double pX, double pY, double pZ) {
		entityID = pID;
		final double var8 = 3.9D;

		if (pX < -var8) {
			pX = -var8;
		}

		if (pY < -var8) {
			pY = -var8;
		}

		if (pZ < -var8) {
			pZ = -var8;
		}

		if (pX > var8) {
			pX = var8;
		}

		if (pY > var8) {
			pY = var8;
		}

		if (pZ > var8) {
			pZ = var8;
		}

		x = (int) (pX * 8000.0D);
		y = (int) (pY * 8000.0D);
		z = (int) (pZ * 8000.0D);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		entityID = data.readVarIntFromBuffer();
		x = data.readShort();
		y = data.readShort();
		z = data.readShort();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(entityID);
		data.writeShort(x);
		data.writeShort(y);
		data.writeShort(z);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleEntityVelocity(this);
	}

	public int entityID() {
		return entityID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
