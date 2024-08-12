package net.minecraft.network;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class NettyEncryptingDecoder extends MessageToMessageDecoder {

public static final int EaZy = 1368;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final NettyEncryptionTranslator decryptionCodec;
	// private static final String __OBFID = "http://https://fuckuskid00001238";

	public NettyEncryptingDecoder(final Cipher cipher) {
		decryptionCodec = new NettyEncryptionTranslator(cipher);
	}

	protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_)
			throws ShortBufferException {
		p_decode_3_.add(decryptionCodec.decipher(p_decode_1_, p_decode_2_));
	}

	@Override
	protected void decode(final ChannelHandlerContext p_decode_1_, final Object p_decode_2_, final List p_decode_3_)
			throws ShortBufferException {
		this.decode(p_decode_1_, (ByteBuf) p_decode_2_, p_decode_3_);
	}
}
