package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;

public class S22PacketMultiBlockChange implements Packet {

public static final int EaZy = 1433;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ChunkCoordIntPair field_148925_b;
	private S22PacketMultiBlockChange.BlockUpdateData[] field_179845_b;
	// private static final String __OBFID = "http://https://fuckuskid00001290";

	public S22PacketMultiBlockChange() {}

	public S22PacketMultiBlockChange(final int p_i45181_1_, final short[] p_i45181_2_, final Chunk p_i45181_3_) {
		field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
		field_179845_b = new S22PacketMultiBlockChange.BlockUpdateData[p_i45181_1_];

		for (int var4 = 0; var4 < field_179845_b.length; ++var4) {
			field_179845_b[var4] = new S22PacketMultiBlockChange.BlockUpdateData(p_i45181_2_[var4], p_i45181_3_);
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148925_b = new ChunkCoordIntPair(data.readInt(), data.readInt());
		field_179845_b = new S22PacketMultiBlockChange.BlockUpdateData[data.readVarIntFromBuffer()];

		for (int var2 = 0; var2 < field_179845_b.length; ++var2) {
			field_179845_b[var2] = new S22PacketMultiBlockChange.BlockUpdateData(data.readShort(),
					(IBlockState) Block.BLOCK_STATE_IDS.getByValue(data.readVarIntFromBuffer()));
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(field_148925_b.chunkXPos);
		data.writeInt(field_148925_b.chunkZPos);
		data.writeVarIntToBuffer(field_179845_b.length);
		final S22PacketMultiBlockChange.BlockUpdateData[] var2 = field_179845_b;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final S22PacketMultiBlockChange.BlockUpdateData var5 = var2[var4];
			data.writeShort(var5.func_180089_b());
			data.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var5.func_180088_c()));
		}
	}

	public void func_180729_a(final INetHandlerPlayClient p_180729_1_) {
		p_180729_1_.handleMultiBlockChange(this);
	}

	public S22PacketMultiBlockChange.BlockUpdateData[] func_179844_a() {
		return field_179845_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180729_a((INetHandlerPlayClient) handler);
	}

	public class BlockUpdateData {
		private final short field_180091_b;
		private final IBlockState field_180092_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002302";

		public BlockUpdateData(final short p_i45984_2_, final IBlockState p_i45984_3_) {
			field_180091_b = p_i45984_2_;
			field_180092_c = p_i45984_3_;
		}

		public BlockUpdateData(final short p_i45985_2_, final Chunk p_i45985_3_) {
			field_180091_b = p_i45985_2_;
			field_180092_c = p_i45985_3_.getBlockState(func_180090_a());
		}

		public BlockPos func_180090_a() {
			return new BlockPos(
					field_148925_b.getBlock(field_180091_b >> 12 & 15, field_180091_b & 255, field_180091_b >> 8 & 15));
		}

		public short func_180089_b() {
			return field_180091_b;
		}

		public IBlockState func_180088_c() {
			return field_180092_c;
		}
	}
}
