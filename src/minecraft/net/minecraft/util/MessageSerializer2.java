package net.minecraft.util;

import net.minecraft.network.PacketBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageSerializer2 extends MessageToByteEncoder {

public static final int EaZy = 1635;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001256";

	protected void encode(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_,
			final ByteBuf p_encode_3_) {
		final int var4 = p_encode_2_.readableBytes();
		final int var5 = PacketBuffer.getVarIntSize(var4);

		if (var5 > 3) {
			throw new IllegalArgumentException("unable to fit " + var4 + " into " + 3);
		} else {
			final PacketBuffer var6 = new PacketBuffer(p_encode_3_);
			var6.ensureWritable(var5 + var4);
			var6.writeVarIntToBuffer(var4);
			var6.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), var4);
		}
	}

	@Override
	protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_,
			final ByteBuf p_encode_3_) {
		this.encode(p_encode_1_, (ByteBuf) p_encode_2_, p_encode_3_);
	}
}
