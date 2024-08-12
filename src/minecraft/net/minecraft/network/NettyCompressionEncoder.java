package net.minecraft.network;

import java.util.zip.Deflater;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyCompressionEncoder extends MessageToByteEncoder {

public static final int EaZy = 1367;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final byte[] buffer = new byte[8192];
	private final Deflater deflater;
	private int treshold;
	// private static final String __OBFID = "http://https://fuckuskid00002313";

	public NettyCompressionEncoder(final int treshold) {
		this.treshold = treshold;
		deflater = new Deflater();
	}

	protected void compress(final ChannelHandlerContext ctx, final ByteBuf input, final ByteBuf output) {
		final int var4 = input.readableBytes();
		final PacketBuffer var5 = new PacketBuffer(output);

		if (var4 < treshold) {
			var5.writeVarIntToBuffer(0);
			var5.writeBytes(input);
		} else {
			final byte[] var6 = new byte[var4];
			input.readBytes(var6);
			var5.writeVarIntToBuffer(var6.length);
			deflater.setInput(var6, 0, var4);
			deflater.finish();

			while (!deflater.finished()) {
				final int var7 = deflater.deflate(buffer);
				var5.writeBytes(buffer, 0, var7);
			}

			deflater.reset();
		}
	}

	public void setCompressionTreshold(final int treshold) {
		this.treshold = treshold;
	}

	@Override
	protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_,
			final ByteBuf p_encode_3_) {
		compress(p_encode_1_, (ByteBuf) p_encode_2_, p_encode_3_);
	}
}
