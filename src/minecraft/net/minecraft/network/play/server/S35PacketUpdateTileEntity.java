package net.minecraft.network.play.server;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S35PacketUpdateTileEntity implements Packet {

public static final int EaZy = 1452;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179824_a;

	/** Used only for vanilla tile entities */
	private int metadata;
	private NBTTagCompound nbt;
	// private static final String __OBFID = "http://https://fuckuskid00001285";

	public S35PacketUpdateTileEntity() {}

	public S35PacketUpdateTileEntity(final BlockPos p_i45990_1_, final int p_i45990_2_,
			final NBTTagCompound p_i45990_3_) {
		field_179824_a = p_i45990_1_;
		metadata = p_i45990_2_;
		nbt = p_i45990_3_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179824_a = data.readBlockPos();
		metadata = data.readUnsignedByte();
		nbt = data.readNBTTagCompoundFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179824_a);
		data.writeByte((byte) metadata);
		data.writeNBTTagCompoundToBuffer(nbt);
	}

	public void func_180725_a(final INetHandlerPlayClient p_180725_1_) {
		p_180725_1_.handleUpdateTileEntity(this);
	}

	public BlockPos func_179823_a() {
		return field_179824_a;
	}

	public int getTileEntityType() {
		return metadata;
	}

	public NBTTagCompound getNbtCompound() {
		return nbt;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180725_a((INetHandlerPlayClient) handler);
	}
}
