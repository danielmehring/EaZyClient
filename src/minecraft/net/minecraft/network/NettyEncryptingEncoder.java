package net.minecraft.network;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyEncryptingEncoder extends MessageToByteEncoder {

public static final int EaZy = 1369;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final NettyEncryptionTranslator encryptionCodec;
	// private static final String __OBFID = "http://https://fuckuskid00001239";

	public NettyEncryptingEncoder(final Cipher cipher) {
		encryptionCodec = new NettyEncryptionTranslator(cipher);
	}

	protected void encode(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_)
			throws ShortBufferException {
		encryptionCodec.cipher(p_encode_2_, p_encode_3_);
	}

	@Override
	protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_)
			throws ShortBufferException {
		this.encode(p_encode_1_, (ByteBuf) p_encode_2_, p_encode_3_);
	}
}
