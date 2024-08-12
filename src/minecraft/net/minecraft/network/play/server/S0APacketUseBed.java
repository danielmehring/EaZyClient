package net.minecraft.network.play.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class S0APacketUseBed implements Packet {

public static final int EaZy = 1412;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int playerID;
	private BlockPos field_179799_b;
	// private static final String __OBFID = "http://https://fuckuskid00001319";

	public S0APacketUseBed() {}

	public S0APacketUseBed(final EntityPlayer p_i45964_1_, final BlockPos p_i45964_2_) {
		playerID = p_i45964_1_.getEntityId();
		field_179799_b = p_i45964_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		playerID = data.readVarIntFromBuffer();
		field_179799_b = data.readBlockPos();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(playerID);
		data.writeBlockPos(field_179799_b);
	}

	public void func_180744_a(final INetHandlerPlayClient p_180744_1_) {
		p_180744_1_.handleUseBed(this);
	}

	public EntityPlayer getPlayer(final World worldIn) {
		return (EntityPlayer) worldIn.getEntityByID(playerID);
	}

	public BlockPos func_179798_a() {
		return field_179799_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180744_a((INetHandlerPlayClient) handler);
	}
}
