package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S49PacketUpdateEntityNBT implements Packet {

public static final int EaZy = 1472;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_179766_a;
	private NBTTagCompound field_179765_b;
	// private static final String __OBFID = "http://https://fuckuskid00002301";

	public S49PacketUpdateEntityNBT() {}

	public S49PacketUpdateEntityNBT(final int p_i45979_1_, final NBTTagCompound p_i45979_2_) {
		field_179766_a = p_i45979_1_;
		field_179765_b = p_i45979_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179766_a = data.readVarIntFromBuffer();
		field_179765_b = data.readNBTTagCompoundFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_179766_a);
		data.writeNBTTagCompoundToBuffer(field_179765_b);
	}

	public void func_179762_a(final INetHandlerPlayClient p_179762_1_) {
		p_179762_1_.func_175097_a(this);
	}

	public NBTTagCompound func_179763_a() {
		return field_179765_b;
	}

	public Entity func_179764_a(final World worldIn) {
		return worldIn.getEntityByID(field_179766_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179762_a((INetHandlerPlayClient) handler);
	}
}
