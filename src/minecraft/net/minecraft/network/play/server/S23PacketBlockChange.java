package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class S23PacketBlockChange implements Packet {

public static final int EaZy = 1434;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179828_a;
	private IBlockState field_148883_d;
	// private static final String __OBFID = "http://https://fuckuskid00001287";

	public S23PacketBlockChange() {}

	public S23PacketBlockChange(final World worldIn, final BlockPos p_i45988_2_) {
		field_179828_a = p_i45988_2_;
		field_148883_d = worldIn.getBlockState(p_i45988_2_);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179828_a = data.readBlockPos();
		field_148883_d = (IBlockState) Block.BLOCK_STATE_IDS.getByValue(data.readVarIntFromBuffer());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179828_a);
		data.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(field_148883_d));
	}

	public void func_180727_a(final INetHandlerPlayClient p_180727_1_) {
		p_180727_1_.handleBlockChange(this);
	}

	public IBlockState func_180728_a() {
		return field_148883_d;
	}

	public BlockPos func_179827_b() {
		return field_179828_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180727_a((INetHandlerPlayClient) handler);
	}
}
