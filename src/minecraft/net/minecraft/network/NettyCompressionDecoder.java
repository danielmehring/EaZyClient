package net.minecraft.network;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

public class NettyCompressionDecoder extends ByteToMessageDecoder {

	public static final int EaZy = 1366;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final Inflater inflater;
	private int treshold;
	// private static final String __OBFID = "http://https://fuckuskid00002314";

	public NettyCompressionDecoder(final int treshold) {
		this.treshold = treshold;
		inflater = new Inflater();
	}

	@Override
	protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_)
			throws DataFormatException {
		if (p_decode_2_.readableBytes() != 0) {
			final PacketBuffer var4 = new PacketBuffer(p_decode_2_);
			final int var5 = var4.readVarIntFromBuffer();

			if (var5 == 0) {
				p_decode_3_.add(var4.readBytes(var4.readableBytes()));
			} else {
				if (var5 < treshold) {
					throw new DecoderException(
							"Badly compressed packet - size of " + var5 + " is below server threshold of " + treshold);
				}

				if (var5 > 2097152) {
					throw new DecoderException("Badly compressed packet - size of " + var5
							+ " is larger than protocol maximum of " + 2097152);
				}

				final byte[] var6 = new byte[var4.readableBytes()];
				var4.readBytes(var6);
				inflater.setInput(var6);
				final byte[] var7 = new byte[var5];
				inflater.inflate(var7);
				p_decode_3_.add(Unpooled.wrappedBuffer(var7));
				inflater.reset();
			}
		}
	}

	public void setCompressionTreshold(final int treshold) {
		this.treshold = treshold;
	}
}
