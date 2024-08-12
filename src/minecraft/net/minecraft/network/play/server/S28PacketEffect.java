package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S28PacketEffect implements Packet {

public static final int EaZy = 1439;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int soundType;
	private BlockPos field_179747_b;

	/** can be a block/item id or other depending on the soundtype */
	private int soundData;

	/** If true the sound is played across the server */
	private boolean serverWide;
	// private static final String __OBFID = "http://https://fuckuskid00001307";

	public S28PacketEffect() {}

	public S28PacketEffect(final int p_i45978_1_, final BlockPos p_i45978_2_, final int p_i45978_3_,
			final boolean p_i45978_4_) {
		soundType = p_i45978_1_;
		field_179747_b = p_i45978_2_;
		soundData = p_i45978_3_;
		serverWide = p_i45978_4_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		soundType = data.readInt();
		field_179747_b = data.readBlockPos();
		soundData = data.readInt();
		serverWide = data.readBoolean();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(soundType);
		data.writeBlockPos(field_179747_b);
		data.writeInt(soundData);
		data.writeBoolean(serverWide);
	}

	public void func_180739_a(final INetHandlerPlayClient p_180739_1_) {
		p_180739_1_.handleEffect(this);
	}

	public boolean isSoundServerwide() {
		return serverWide;
	}

	public int getSoundType() {
		return soundType;
	}

	public int getSoundData() {
		return soundData;
	}

	public BlockPos func_179746_d() {
		return field_179747_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180739_a((INetHandlerPlayClient) handler);
	}
}
